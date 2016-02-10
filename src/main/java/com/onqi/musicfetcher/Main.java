package com.onqi.musicfetcher;

import com.google.inject.servlet.GuiceFilter;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;
import org.glassfish.jersey.servlet.ServletContainer;
import org.slf4j.bridge.SLF4JBridgeHandler;

import javax.servlet.DispatcherType;
import java.util.EnumSet;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        java.util.logging.LogManager.getLogManager().reset();
        SLF4JBridgeHandler.install();

        Server server = new Server(8080);

        WebAppContext bb = new WebAppContext();
        bb.setServer(server);

        bb.addFilter(GuiceFilter.class, "/*", EnumSet.allOf(DispatcherType.class));

        ServletHolder holder = new ServletHolder(ServletContainer.class);
        holder.setInitParameter("javax.ws.rs.Application", "com.onqi.musicfetcher.Application");

        bb.addServlet(holder, "/*");
        bb.setResourceBase("/");

        server.setHandler(bb);

        registerShutdownTask(server);

        try {
            System.out.println(">>> STARTING EMBEDDED JETTY SERVER, PRESS ANY KEY TO STOP");
            server.start();
            server.join();
        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(100);
        }
    }

    private static void registerShutdownTask(Server server) {
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
        Runnable shutdownTask = () -> {
            try {
                if (System.in.available() != 0) {
                    server.stop();
                    executorService.shutdown();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        executorService.scheduleAtFixedRate(shutdownTask, 0, 5, TimeUnit.SECONDS);
    }
}
