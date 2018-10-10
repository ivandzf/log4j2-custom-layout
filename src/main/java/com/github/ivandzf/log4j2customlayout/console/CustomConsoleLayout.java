package com.github.ivandzf.log4j2customlayout.console;

import com.github.ivandzf.log4j2customlayout.message.CustomMessage;
import com.github.ivandzf.log4j2customlayout.utils.JsonUtils;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.Node;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginConfiguration;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.layout.AbstractStringLayout;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * log4j2-custom-layout
 *
 * @author Divananda Zikry Fadilla (30 September 2018)
 * Email: divanandazf@gmail.com
 * <p>
 * Documentation here !!
 */
@Plugin(name = "CustomConsoleLayout", category = Node.CATEGORY, elementType = Layout.ELEMENT_TYPE)
public class CustomConsoleLayout extends AbstractStringLayout {

    private final String newFieldName;

    public CustomConsoleLayout(Configuration config, Charset aCharset, String newFieldName) {
        super(config, aCharset, null, null);
        this.newFieldName = newFieldName;
    }

    @PluginFactory
    public static CustomConsoleLayout createLayout(@PluginConfiguration final Configuration config,
                                                   @PluginAttribute(value = "charset", defaultString = "US-ASCII") final Charset charset,
                                                   @PluginAttribute(value = "newFieldName") final String newFieldName) {
        return new CustomConsoleLayout(config, charset, newFieldName);
    }

    @Override
    public String toSerializable(LogEvent event) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss:SS").format(new Date()));
        stringBuilder.append(" ");
        stringBuilder.append(event.getLevel());
        stringBuilder.append(" [");
        stringBuilder.append(event.getThreadName());
        stringBuilder.append("] ");
        stringBuilder.append(event.getLoggerName());
        stringBuilder.append(" - ");

        CustomMessage customMessage = JsonUtils.generateCustomMessage(event.getMessage().getFormattedMessage());
        if (customMessage != null) {
            if (newFieldName != null) {
                String[] newFieldNames = newFieldName.split(",");
                for (String fieldName : newFieldNames) {
                    stringBuilder.append(customMessage.getNewField().get(fieldName)).append(" ");
                }
            } else {
                stringBuilder.append(customMessage.getMessage());
            }
        } else {
            stringBuilder.append(event.getMessage().getFormattedMessage());
        }

        stringBuilder.append("\n");
        return stringBuilder.toString();
    }
}
