package com.github.ivandzf.log4j2customlayout.json;

import com.github.ivandzf.log4j2customlayout.config.LogEnvironment;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.Node;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginConfiguration;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.impl.ThrowableProxy;
import org.apache.logging.log4j.core.layout.AbstractStringLayout;
import com.github.ivandzf.log4j2customlayout.message.CustomMessage;
import com.github.ivandzf.log4j2customlayout.utils.JsonUtils;
import org.apache.logging.log4j.util.Strings;

import java.nio.charset.Charset;

/**
 * log4j2-custom-layout
 *
 * @author Divananda Zikry Fadilla (08 October 2018)
 * Email: divanandazf@gmail.com
 * <p>
 * Documentation here !!
 */
@Plugin(name = "CustomJsonLayout", category = Node.CATEGORY, elementType = Layout.ELEMENT_TYPE)
public class CustomJsonLayout extends AbstractStringLayout {

    private final Gson gson = JsonUtils.getGson();
    private final boolean locationInfo;
    private final boolean hideEnvironmentWhenNull;

    public CustomJsonLayout(Configuration config, Charset aCharset, Serializer headerSerializer, Serializer footerSerializer, boolean locationInfo, boolean hideEnvironmentWhenNull) {
        super(config, aCharset, headerSerializer, footerSerializer);
        this.locationInfo = locationInfo;
        this.hideEnvironmentWhenNull = hideEnvironmentWhenNull;
    }

    @PluginFactory
    public static CustomJsonLayout createLayout(@PluginConfiguration final Configuration config,
                                                @PluginAttribute(value = "charset", defaultString = "US-ASCII") final Charset charset,
                                                @PluginAttribute(value = "locationInfo", defaultBoolean = false) final boolean locationInfo,
                                                @PluginAttribute(value = "hideEnvironmentWhenNull", defaultBoolean = true) final boolean hideEnvironmentWhenNull) {
        return new CustomJsonLayout(config, charset, null, null, locationInfo, hideEnvironmentWhenNull);
    }

    @Override
    public String toSerializable(LogEvent event) {
        if (hideEnvironmentWhenNull) {
            if (!LogEnvironment.isPort()||!LogEnvironment.isIpAddress()||!LogEnvironment.isApplicationName())
                return Strings.EMPTY;
        }

        JsonObject jsonObject = new JsonObject();

        // Application Name
        if (LogEnvironment.isApplicationName()) {
            jsonObject.addProperty("applicationName", LogEnvironment.getApplicationName());
        }

        // Ip Address
        if (LogEnvironment.isIpAddress()) {
            jsonObject.addProperty("ipAddress", LogEnvironment.getIpAddress());
        }

        // Port running
        if (LogEnvironment.isPort()) {
            jsonObject.addProperty("port", LogEnvironment.getPort());
        }

        // Log Information
        jsonObject.addProperty("level", event.getLevel().name());
        jsonObject.addProperty("thread", event.getThreadName());
        jsonObject.addProperty("threadId", event.getThreadId());
        jsonObject.addProperty("loggerName", event.getLoggerName());

        // Log Location Information
        if (locationInfo) {
            final StackTraceElement source = event.getSource();

            JsonObject sourceObject = new JsonObject();
            sourceObject.addProperty("class", source.getClassName());
            sourceObject.addProperty("method", source.getMethodName());
            sourceObject.addProperty("file", source.getFileName());
            sourceObject.addProperty("line", source.getLineNumber());

            jsonObject.add("source", sourceObject);
        }

        // Message
        if (JsonUtils.isJsonObjectValid(event.getMessage().getFormattedMessage())) {
            CustomMessage customMessage;
            try {
                customMessage = gson.fromJson(event.getMessage().getFormattedMessage(), CustomMessage.class);
            } catch (JsonParseException e) {
                throw new JsonParseException("message is not custom message object");
            }

            jsonObject.addProperty("message", customMessage.getMessage());
            customMessage.getNewField().forEach((k, v) -> {
                if (v instanceof String) {
                    jsonObject.addProperty(k, (String) v);
                } else if (v instanceof Number) {
                    jsonObject.addProperty(k, (Number) v);
                } else if (v instanceof Character) {
                    jsonObject.addProperty(k, (Character) v);
                } else if (v instanceof Boolean) {
                    jsonObject.addProperty(k, (Boolean) v);
                } else {
                    jsonObject.addProperty(k, gson.toJson(v));
                }
            });
        } else {
            jsonObject.addProperty("message", event.getMessage().getFormattedMessage());
        }

        // Exceptions
        if (event.getThrownProxy() != null) {
            final ThrowableProxy thrownProxy = event.getThrownProxy();
            final Throwable throwable = thrownProxy.getThrowable();

            final String exceptionsClass = throwable.getClass().getCanonicalName();
            if (exceptionsClass != null) {
                jsonObject.addProperty("exception", exceptionsClass);
            }

            final String exceptionsMessage = throwable.getMessage();
            if (exceptionsMessage != null) {
                jsonObject.addProperty("cause", exceptionsMessage);
            }

            final String stackTrace = thrownProxy.getExtendedStackTraceAsString("");
            if (stackTrace != null) {
                jsonObject.addProperty("stacktrace", stackTrace);
            }
        }

        return gson.toJson(jsonObject).concat("\r\n");
    }

}
