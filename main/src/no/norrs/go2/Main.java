package no.norrs.go2;

import no.norrs.go2.handlers.Go2;
import no.norrs.go2.handlers.Register;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.*;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.util.resource.Resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    public static void main(String[] args) throws Exception {
        Server server = new Server();
        ServerConnector serverConnector = new ServerConnector(server);

        String port = System.getenv("PORT");
        if (port != null) {
            serverConnector.setPort(Integer.valueOf(port));
        } else {
            serverConnector.setPort(8080);
        }

        server.setConnectors(new Connector[]
                {serverConnector});

        HandlerCollection handlerCollection = new HandlerCollection();

        ServletContextHandler servletCtxHandler = new ServletContextHandler(server, "/");

        ContextHandlerCollection contextCollection = new ContextHandlerCollection();


        // Atmosphere Config

        /*ServletHolder jersey = new ServletHolder(ServletContainer.class);
        jersey.setServlet(new ServletContainer());
        jersey.setInitParameter("javax.ws.rs.Application", UriTemplateApplication.class.getName());
        jersey.setInitOrder(0);*/
        servletCtxHandler.setContextPath("/*");
        servletCtxHandler.addServlet(Go2.class, "/*");
        servletCtxHandler.addServlet(Register.class, "/register");
        servletCtxHandler.addServlet(StatusZServlet.class, "/statusz");

        // File handler
        ContextHandler filesCtx = new ContextHandler();
        filesCtx.setContextPath("/s/");
        ResourceHandler rh = new ResourceHandler();
        //rh.setWelcomeFiles(new String[]{"hud.html"});
        try {
            rh.setBaseResource(Resource.newResource("main/resources/no/norrs/go2/static/", true));
        } catch (IOException e) {
            System.out.println("stacktrace here");
            e.printStackTrace();
        }
        filesCtx.setHandler(rh);
        contextCollection.addHandler(filesCtx);

        handlerCollection.addHandler(contextCollection);
        handlerCollection.addHandler(servletCtxHandler);
        handlerCollection.addHandler(new DefaultHandler());
        server.setHandler(handlerCollection);

        server.start();
        server.dumpStdErr();
        server.join();
    }

}

