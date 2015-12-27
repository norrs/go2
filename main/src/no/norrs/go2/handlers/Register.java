package no.norrs.go2.handlers;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import no.norrs.go2.TemplateConfig;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class Register
        extends HttpServlet {

    public Register() {

    }

    public void doPost( HttpServletRequest request,
                       HttpServletResponse response) throws IOException,
                                                            ServletException {

        Jedis jedis = new Jedis("localhost");

        String shortName = request.getParameter("shortName").trim().toLowerCase();
        String target = request.getParameter("redirectTarget");
        jedis.set(shortName, target);


        response.setContentType("text/html; charset=utf-8");
        response.setStatus(HttpServletResponse.SC_CREATED);

               /* Create a data-model */
        Map root = new HashMap();
        root.put("pathInfo", shortName);
        root.put("redirectTarget", target);

        /* Get the template (uses cache internally) */
        Template temp = TemplateConfig.getInstance().getTemplate("redirect.ftl");


        Writer out = new OutputStreamWriter(response.getOutputStream());
        try {
            temp.process(root, out);
        } catch (TemplateException e) {
            e.printStackTrace();
            throw new IOException(e);
        }

    }
}
