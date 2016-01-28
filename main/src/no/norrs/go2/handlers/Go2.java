package no.norrs.go2.handlers;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import no.norrs.go2.TemplateConfig;
import redis.clients.jedis.Jedis;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Go2 extends HttpServlet {
    private final int redisPort;
    private final String redisHost;

    public Go2(String redis) {
        String[] tmp = redis.split(":");
        this.redisHost = tmp[0];
        if (tmp.length == 2) {
            this.redisPort = Integer.valueOf(tmp[1]);
        } else {
            this.redisPort = 6379;
        }
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws IOException,
            ServletException {

        // strip leading / and lower case it
        String pathInfo = request.getPathInfo().substring(1).toLowerCase();
        Logger.getLogger(getClass().toString()).log(Level.WARNING, "pathInfo: " + pathInfo);

        int slashIndex = pathInfo.indexOf("/");
        boolean askToRedirect = false;
        if (slashIndex != -1) {
            String pathOptions = pathInfo.substring(slashIndex);
            pathInfo = pathInfo.substring(0, slashIndex);
            if (pathOptions.contains("view") || pathOptions.contains("v")) {
                askToRedirect = true;
            }
        }


        Jedis jedis = new Jedis(redisHost, redisPort);
        String redirectTarget = jedis.get(pathInfo);

        if (pathInfo.equals("")) {

            Template temp = TemplateConfig.freemarker().getTemplate("index.ftl");

            Writer out = new OutputStreamWriter(response.getOutputStream());
            try {
                Map root = new HashMap();
                root.put("globals", TemplateConfig.globalTemplateVariables());
                Logger.getLogger(getClass().toString()).log(Level.SEVERE, "size: " +TemplateConfig.globalTemplateVariables().size());
                temp.process(root, out);
            } catch (TemplateException e) {
                e.printStackTrace();
                throw new IOException(e);
            }
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("text/html; charset=utf-8");
        } else if (redirectTarget != null && !askToRedirect) {
            response.setContentType("text/plain; charset=utf-8");
            response.setStatus(HttpServletResponse.SC_FOUND);
            response.sendRedirect(redirectTarget);
        } else if (redirectTarget != null && askToRedirect) {
            response.setContentType("text/html; charset=utf-8");
            response.setStatus(HttpServletResponse.SC_FOUND);

            /* Create a data-model */
            Map root = new HashMap();
            root.put("pathInfo", pathInfo);
            root.put("redirectTarget", redirectTarget);
            root.put("globals", TemplateConfig.globalTemplateVariables());
            /* Get the template (uses cache internally) */
            Template temp = TemplateConfig.freemarker().getTemplate("redirect.ftl");

            //response.getWriter().append("Could not find any redirect on: ").append(pathInfo);

            Writer out = new OutputStreamWriter(response.getOutputStream());
            try {
                temp.process(root, out);
            } catch (TemplateException e) {
                e.printStackTrace();
                throw new IOException(e);
            }

        } else {
            response.setContentType("text/html; charset=utf-8");
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);

            /* Create a data-model */
            Map root = new HashMap();
            root.put("pathInfo", pathInfo);
            root.put("globals", TemplateConfig.globalTemplateVariables());

            /* Get the template (uses cache internally) */
            Template temp = TemplateConfig.freemarker().getTemplate("not_found.ftl");

            //response.getWriter().append("Could not find any redirect on: ").append(pathInfo);

            Writer out = new OutputStreamWriter(response.getOutputStream());
            try {
                temp.process(root, out);
            } catch (TemplateException e) {
                e.printStackTrace();
                throw new IOException(e);
            }
        }
    }
}