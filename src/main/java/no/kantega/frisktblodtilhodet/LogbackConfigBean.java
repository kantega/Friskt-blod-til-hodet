package no.kantega.frisktblodtilhodet;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;
import java.io.File;

public class LogbackConfigBean implements InitializingBean, ServletContextAware {
    private File loggingDirectory;
    private ServletContext servletContext;

    public void afterPropertiesSet() throws Exception {
        System.setProperty("loggingDirectory", loggingDirectory.getAbsolutePath());
        LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();

        try {
            JoranConfigurator configurator = new JoranConfigurator();
            configurator.setContext(lc);
            lc.stop();
            lc.putProperty("loggingDirectory", loggingDirectory.getAbsolutePath());
            configurator.doConfigure(servletContext.getResource("/WEB-INF/classes/logback.xml"));
        } catch (JoranException je) {
            je.printStackTrace();
        }
    }

    public void setLoggingDirectory(File loggingDirectory) {
        this.loggingDirectory = loggingDirectory;
    }

    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }
}