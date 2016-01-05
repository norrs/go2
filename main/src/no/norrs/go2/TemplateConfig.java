package no.norrs.go2;


import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TemplateConfig {
    private static Map<String, String> templateConfigVariables = null;
    private static Configuration freemarkerConfig = null;
    private static TemplateConfig instance = null;

    protected TemplateConfig() {
    }

    public static TemplateConfig getInstance() throws IOException {
        if (instance == null) {
            instance = new TemplateConfig();
            /* ------------------------------------------------------------------------ */
            /* You should do this ONLY ONCE in the whole application life-cycle:        */
            /* Create and adjust the configuration singleton */
            freemarkerConfig = new Configuration(Configuration.VERSION_2_3_23);


            freemarkerConfig.setClassForTemplateLoading(TemplateConfig.class, "/main/resources/no/norrs/go2/");
            freemarkerConfig.setDefaultEncoding("UTF-8");
            //freemarkerConfig.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
            freemarkerConfig.setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER);

            templateConfigVariables = TemplateConfig.getTemplateConfigVariables();
        }
        return instance;
    }

    private static Map<String, String> getTemplateConfigVariables() {
        String propFileName = System.getenv("CONFIG_FILE");
        if (propFileName == null) {
            propFileName = "config.properties";
        }


        try {
            Properties prop = new Properties();


            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
            if (inputStream != null) {
                prop.load(inputStream);
            }
            Map<String, String> map = new HashMap<String, String>();
            for (final String name: prop.stringPropertyNames()) {
                map.put(name, prop.getProperty(name));
            }
            return map;

        } catch (IOException e) {
            Logger.getLogger(getClass().getName()).log(Level.CONFIG, "Could not load configuration file: " + propFileName);
        }
        return null;
    }


}
