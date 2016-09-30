package com.example;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Named
@Path("/")
@Produces("application/json;charset=utf-8")
public class SomeResource {
    private final HelloWorld helloWorld;

    @Inject
    public SomeResource(HelloWorld helloWorld) {
        this.helloWorld = helloWorld;
    }

    @GET
    public String updateResource() {
        return helloWorld.getString();
    }

    @GET
    @Path("hello")
    public HelloWorld getHelloWorld() {
        // As resteasy-jackson-provider is registered in the classpath, this object will be serialized
        // and sent over the wire as a normal mapper.writeValueAsString call behind the scenes
        return helloWorld;
    }

    @GET
    @Path("test")
    public String nopedog() {
        return "nopedog.jpeg";
    }
}
