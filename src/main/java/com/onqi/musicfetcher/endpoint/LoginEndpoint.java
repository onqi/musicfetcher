package com.onqi.musicfetcher.endpoint;

import com.onqi.musicfetcher.service.LoginService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/login")
public class LoginEndpoint {
    private LoginService service;
    @Inject
    public LoginEndpoint(LoginService service) {
        this.service = service;
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String login(@QueryParam("username")String username){
        service.login(username);
        return "AAAA";
    }
}
