package no.norrs.go2;


import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;

import java.io.IOException;

public class TemplateConfig {
    private static Configuration instance = null;

    protected TemplateConfig() {
    }

    public static Configuration getInstance() throws IOException {
        if (instance == null) {
            /* ------------------------------------------------------------------------ */
            /* You should do this ONLY ONCE in the whole application life-cycle:        */
            /* Create and adjust the configuration singleton */
            instance = new Configuration(Configuration.VERSION_2_3_23);



            instance.setClassForTemplateLoading(TemplateConfig.class, "/main/resources/no/norrs/go2/");
            instance.setDefaultEncoding("UTF-8");
            //instance.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
            instance.setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER);

        }
        return instance;
    }
}
