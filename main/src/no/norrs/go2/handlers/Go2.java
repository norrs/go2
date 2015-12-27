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
    public Go2() {


    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws IOException,
            ServletException {

        Logger.getLogger(getClass().toString()).log(Level.WARNING, "JODA");

        // strip leading / and lower case it
        String pathInfo = request.getPathInfo().substring(1).toLowerCase();

        int slashIndex = pathInfo.indexOf("/");
        boolean askToRedirect = false;
        if (slashIndex != -1) {
            String pathOptions = pathInfo.substring(slashIndex);
            pathInfo = pathInfo.substring(0, slashIndex);
            if (pathOptions.contains("view") || pathOptions.contains("v")) {
                askToRedirect = true;
            }
        }


        Jedis jedis = new Jedis("localhost");
        String redirectTarget = jedis.get(pathInfo);

        if (redirectTarget != null && !askToRedirect) {
            response.setContentType("text/plain; charset=utf-8");
            response.setStatus(HttpServletResponse.SC_TEMPORARY_REDIRECT);
            response.sendRedirect(redirectTarget);
        } else if(askToRedirect) {
            response.setContentType("text/html; charset=utf-8");
            response.setStatus(HttpServletResponse.SC_FOUND);

            /* Create a data-model */
            Map root = new HashMap();
            root.put("pathInfo", pathInfo);
            root.put("redirectTarget", redirectTarget);
            /* Get the template (uses cache internally) */
            Template temp = TemplateConfig.getInstance().getTemplate("redirect.ftl");

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

            /* Get the template (uses cache internally) */
            Template temp = TemplateConfig.getInstance().getTemplate("not_found.ftl");

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