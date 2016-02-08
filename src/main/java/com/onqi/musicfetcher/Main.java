package com.onqi.musicfetcher;

import org.eclipse.jetty.server.Server;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.jetty.JettyHttpContainerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.net.URI;

public class Main {
    public static void main(String[] args) throws Exception {
        ResourceConfig app = new ResourceConfig()
                .packages("con.onqi.musicfetcher")
                .register(JacksonFeature.class);

        Server server = JettyHttpContainerFactory.createServer(URI.create("http://localhost:8080/"),app);

        server.start();
        server.join();
    }

}
