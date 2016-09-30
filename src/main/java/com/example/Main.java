package com.example;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.jboss.resteasy.plugins.server.servlet.HttpServletDispatcher;
import org.jboss.resteasy.plugins.server.servlet.ResteasyBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {

        Server server = new Server(8080);

        ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        rootContext.register(AppConfig.class);
        //rootContext.register(DataConfig.class);
        contextHandler.addEventListener(new ResteasyBootstrap());
        // TODO: Update to use RESTEasy fix in future
        // contextHandler.addEventListener(new SpringContextLoaderListener());
        contextHandler.addEventListener(new CustomLoaderListener(rootContext));
        contextHandler.addServlet(HttpServletDispatcher.class, "/*");
        server.setHandler(contextHandler);
        logger.info("Starting...");
        try {
            server.start();
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage(), e);
            try {
                server.stop();
                return;
            } catch (Exception em) {
                logger.error(em.getLocalizedMessage(), em);
                return;
            }
        }
        try {
            server.join();
        } catch (InterruptedException e) {
            logger.error(e.getLocalizedMessage(), e);
        }
        try {
                server.stop();
                return;
            } catch (Exception em) {
                logger.error(em.getLocalizedMessage(), em);
                System.exit(0);
                return;
            }
    }
}
