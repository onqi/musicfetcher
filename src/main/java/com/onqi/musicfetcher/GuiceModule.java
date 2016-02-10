package com.onqi.musicfetcher;

import com.google.inject.servlet.ServletModule;
import com.onqi.musicfetcher.service.LoginService;

class GuiceModule extends ServletModule {
    @Override
    protected void configureServlets() {
        bind(LoginService.class);
    }
}
