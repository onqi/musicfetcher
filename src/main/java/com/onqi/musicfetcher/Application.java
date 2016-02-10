package com.onqi.musicfetcher;

import com.google.inject.Guice;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.server.TracingConfig;
import org.jvnet.hk2.guice.bridge.api.GuiceBridge;
import org.jvnet.hk2.guice.bridge.api.GuiceIntoHK2Bridge;

import javax.inject.Inject;

class Application extends ResourceConfig {
    @Inject
    public Application(ServiceLocator locator) {
        System.out.println("Registering injectables...");
        packages(getClass().getPackage().getName());
        register(JacksonFeature.class);
        register(new LoggingFilter());

        property(ServerProperties.TRACING, TracingConfig.ALL.toString());
        initGuiceBridge(locator);
    }

    private void initGuiceBridge(ServiceLocator serviceLocator) {

        GuiceBridge.getGuiceBridge().initializeGuiceBridge(serviceLocator);

        GuiceIntoHK2Bridge guiceBridge = serviceLocator.getService(GuiceIntoHK2Bridge.class);
        guiceBridge.bridgeGuiceInjector(Guice.createInjector(new GuiceModule()));
    }
}
