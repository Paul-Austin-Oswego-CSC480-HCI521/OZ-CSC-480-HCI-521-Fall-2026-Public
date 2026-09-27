package edu.oswego.csc480.rest.gift_service;

import edu.oswego.csc480.repositories.UserRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;

@Path("user/{uid}/gift/{gid}/status")
public class GiftStatusResource {

    @Inject
    private UserRepository uRepo;

    private @PathParam("uid") Integer uid;
    private @PathParam("gid") Integer gid;


    //TODO:           GET POST PUT DELETE
















}
