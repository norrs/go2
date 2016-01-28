package no.norrs.go2;

import no.norrs.go2.handlers.Go2;
import no.norrs.go2.handlers.Register;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.*;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    private final static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public Main(Options options) throws Exception {
        Server server = new Server();
        ServerConnector serverConnector = new ServerConnector(server);

        serverConnector.setPort(options.port);

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

        servletCtxHandler.addServlet(new ServletHolder(new Go2(options.redis)), "/*");
        servletCtxHandler.addServlet(new ServletHolder(new Register(options.redis)), "/register");
        servletCtxHandler.addServlet(StatusZServlet.class, "/statusz");

        // File handler
        ContextHandler filesCtx = new ContextHandler();
        filesCtx.setContextPath("/s/");
        ResourceHandler rh = new ResourceHandler();
        //rh.setWelcomeFiles(new String[]{"hud.html"});
        rh.setBaseResource(Resource.newClassPathResource("main/resources/no/norrs/go2/static/", true, false));
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

    public static void main(String[] args) {
        String logLevelApp = System.getProperty("org.slf4j.simpleLogger.log.no.norrs", "trace");
        System.setProperty("org.slf4j.simpleLogger.log.no.norrs", logLevelApp);
        System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "info");

        Options options = new Options();
        CmdLineParser parser = new CmdLineParser(options);

        try {
            parser.parseArgument(args);

            if (options.help) {
                parser.printSingleLineUsage(System.out);
                System.out.println();
                System.out.println();
                parser.printUsage(System.out);
            } else {
                new Main(options);
            }
        } catch (CmdLineException ex) {
            System.out.flush();
            System.err.println();
            parser.printSingleLineUsage(System.err);
            System.err.println();
            System.err.println();
            parser.printUsage(System.err);
            System.exit(1);
        }  catch (Exception e) {
            LOGGER.error(" --- Exception ---", e);
            System.exit(3);
        }
        System.exit(0);
    }


}

