package no.norrs.go2;


import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TemplateConfig {
    private static Map<String, String> templateConfigVariables = null;
    private static Configuration freemarkerConfig = null;

    protected TemplateConfig() {
    }

    public static Configuration freemarker() throws IOException {
        if (freemarkerConfig == null) {
            /* ------------------------------------------------------------------------ */
            /* You should do this ONLY ONCE in the whole application life-cycle:        */
            /* Create and adjust the configuration singleton */
            freemarkerConfig = new Configuration(Configuration.VERSION_2_3_23);


            freemarkerConfig.setClassForTemplateLoading(TemplateConfig.class, "/main/resources/no/norrs/go2/");
            freemarkerConfig.setDefaultEncoding("UTF-8");
            //freemarkerConfig.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
            freemarkerConfig.setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER);


        }
        return freemarkerConfig;
    }

    public static Map<String, String> globalTemplateVariables() {
        if (templateConfigVariables == null) {
            String propFileName = System.getenv("GO2_CONFIG_FILE");
            if (propFileName == null) {
                propFileName = "config.properties";
            }

            try {
                Properties prop = new Properties();


                //InputStream inputStream = TemplateConfig.class.getClassLoader().getResourceAsStream(propFileName);
                InputStream inputStream = new FileInputStream(propFileName);
                try {
                    prop.load(inputStream);
                    Map<String, String> map = new HashMap<String, String>();
                    for (final String name: prop.stringPropertyNames()) {
                        map.put(name, prop.getProperty(name));
                    }
                    Logger.getLogger(TemplateConfig.class.getName()).log(Level.SEVERE, "Loaded: " + propFileName + ", with " + prop.size() + " config values");
                    templateConfigVariables = map;
                } catch(IOException ioException) {
                    Logger.getLogger(TemplateConfig.class.getName()).log(Level.SEVERE, "Could not find configuration file: " + propFileName, ioException);

                    templateConfigVariables = getDefaultConfig();
                }

            } catch (IOException e) {
                Logger.getLogger(TemplateConfig.class.getName()).log(Level.SEVERE, "Could not load configuration file: " + propFileName);
                templateConfigVariables = getDefaultConfig();
            }
        }
        return templateConfigVariables;
    }

    protected static Map<String, String> getDefaultConfig() {
        Map<String, String> defaultConfig = new HashMap<String, String>();
        defaultConfig.put("main_vhost", "localhost");
        return defaultConfig;
    }


}
