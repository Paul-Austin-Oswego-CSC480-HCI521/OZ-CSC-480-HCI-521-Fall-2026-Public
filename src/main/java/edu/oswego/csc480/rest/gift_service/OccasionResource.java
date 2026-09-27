package edu.oswego.csc480.rest.gift_service;

import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;

@Path("user/{uid}/gift/{gid}/occasion")
public class OccasionResource {

    private @PathParam("uid") Integer uid;
    private @PathParam("gid") Integer gid;


    //TODO:         GET POST PUT DELETE




}
