package com.yaowei.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * @author Chong Yao Wei
 */
@Path("/")
public class IndexAPI {
    @GET
    @Produces(MediaType.TEXT_HTML)
    public String index() {
        return "<h1>Hello from IndexAPI</h1>";
    }
}
