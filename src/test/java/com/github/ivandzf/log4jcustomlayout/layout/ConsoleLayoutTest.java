package com.github.ivandzf.log4jcustomlayout.layout;

import com.github.ivandzf.log4j2customlayout.console.CustomConsoleLayout;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.ConsoleAppender;
import org.apache.logging.log4j.core.config.Configuration;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * log4j-custom-layout
 *
 * @author Divananda Zikry Fadilla (02 October 2018)
 * Email: divanandazf@gmail.com
 * <p>
 * Documentation here !!
 */
@RunWith(JUnit4.class)
public class ConsoleLayoutTest {

    private Logger logger;

    @Before
    public void init() {
        logger = LogManager.getLogger(ConsoleLayoutTest.class);
    }

    @Test
    public void consoleLayoutLoggingEvent() {
        logger.info("Test console {}", this.getClass().getSimpleName());
        LoggerContext context = LoggerContext.getContext(false);
        Configuration config = context.getConfiguration();
        ConsoleAppender appender = config.getAppender("customConsole");
        Assert.assertEquals(appender.getLayout().getClass(), CustomConsoleLayout.class);
    }

}
