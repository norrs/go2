package no.norrs.go2;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet that essentially only checks that there are free handler threads to
 * handle requests.
 */
public class StatusZServlet
        extends HttpServlet {
    public void doGet( HttpServletRequest request,
                       HttpServletResponse response ) throws IOException, ServletException {
        response.setContentType("text/plain; charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println("OK");
    }
}
