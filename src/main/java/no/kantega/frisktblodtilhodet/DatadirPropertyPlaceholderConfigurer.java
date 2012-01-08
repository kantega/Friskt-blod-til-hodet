package no.kantega.frisktblodtilhodet;

import org.simplericity.datadirlocator.DataDirectoryLocator;
import org.simplericity.datadirlocator.DefaultDataDirectoryLocator;
import org.simplericity.datadirlocator.spring.DataDirectoryPropertyReplacer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.core.Ordered;
import org.springframework.core.PriorityOrdered;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * Based on org.simplericity.datadirlocator.spring.DataDirectoryPropertyReplacer
 */
public class DatadirPropertyPlaceholderConfigurer implements BeanFactoryPostProcessor, InitializingBean, ServletContextAware, PriorityOrdered {
    private final Logger log = LoggerFactory.getLogger(DatadirPropertyPlaceholderConfigurer.class);

    private DataDirectoryLocator dataDirectoryLocator;

    private boolean create = true;

    private String dataDirProperty = "dataDir";
    private String dataDirBeanName = "dataDir";
    private ServletContext servletContext;
    private int order = Ordered.HIGHEST_PRECEDENCE;
    private String defaultDirectory;

    public static final String SERVLET_CONTEXT_ATTR = DataDirectoryPropertyReplacer.class.getName() +"_contextattr";

    private String configurationFileName;

    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {

        PropertyPlaceholderConfigurer configurer = new PropertyPlaceholderConfigurer();

        log.info("Determining data directory where configuration and persistent files will be stored");


        File dataDir = dataDirectoryLocator.locateDataDirectory();

        if(!dataDir.exists() && create) {
            log.info("Data directory " + dataDir.getAbsolutePath() + " does not exist, so we'll create it");
            dataDir.mkdirs();
        }

        Properties p = new Properties();
        try {
            p.load(new FileReader(new File(dataDir, configurationFileName)));
        } catch (IOException e) {
            log.error("Error loading configuration.", e);
        }
        p.setProperty(dataDirProperty, dataDir.getAbsolutePath());

        configurer.setProperties(p);
        configurer.setIgnoreUnresolvablePlaceholders(true);
        configurer.postProcessBeanFactory(configurableListableBeanFactory);


        servletContext.setAttribute(SERVLET_CONTEXT_ATTR, dataDir);

        if(dataDirBeanName != null) {
            configurableListableBeanFactory.registerSingleton(dataDirBeanName, dataDir);
        }

    }

    public void setDataDirectoryLocator(DataDirectoryLocator dataDirectoryLocator) {
        this.dataDirectoryLocator = dataDirectoryLocator;
    }

    public void setCreate(boolean create) {
        this.create = create;
    }

    public void setDataDirProperty(String dataDirProperty) {
        this.dataDirProperty = dataDirProperty;
    }

    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    public void afterPropertiesSet() throws Exception {
        // Set up the default data directory locator if none is set
        if(this.dataDirectoryLocator  == null) {
            this.dataDirectoryLocator = createDefaultDataDirectoryLocator();
        }
    }

    private DataDirectoryLocator createDefaultDataDirectoryLocator() {
        DefaultDataDirectoryLocator locator = new DefaultDataDirectoryLocator();
        locator.setServletContext(this.servletContext);
        if(defaultDirectory != null) {
            locator.setDefaultDir(defaultDirectory);
        }
        return locator;
    }

    public int getOrder() {
        return this.order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public void setDefaultDirectory(String defaultDirectory) {
        this.defaultDirectory = defaultDirectory;
    }


    public void setDataDirBeanName(String dataDirBeanName) {
        this.dataDirBeanName = dataDirBeanName;
    }

    public void setConfigurationFileName(String configurationFileName) {
        this.configurationFileName = configurationFileName;
    }
}
