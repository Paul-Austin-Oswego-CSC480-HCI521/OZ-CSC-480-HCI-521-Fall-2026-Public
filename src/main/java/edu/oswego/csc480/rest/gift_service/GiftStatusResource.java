package edu.oswego.csc480.rest.gift_service;

import edu.oswego.csc480.repositories.UserRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import static jakarta.ws.rs.core.Response.Status;

@Path("user/{uid}/gift/{gid}/status")
@Produces(MediaType.APPLICATION_JSON)
public class GiftStatusResource {

    @Inject
    private UserRepository uRepo;

    private @PathParam("uid") Integer uid;
    private @PathParam("gid") Integer gid;


    //TODO:           GET POST PUT DELETE

    @GET
    public Response getStatus(){

    }

    @POST
    public Response createStatus(){
        // THIS SHOULD NOT BE NECESSARY, ADD THE STATUS OBJECT DIRECTLY IN THE POST REQUEST TO GIFT!!!!
    }

    @PUT
    public Response updateStatus(){

    }

    @DELETE
    public Response deleteStatus(){
        // DO NOT USE
        // THIS ITEM GETS DELETED WHEN GIFT GETS DELETED. NO GIFT SHOULD BE LACKING OF ITS OWN STATUS.
        return Response.status(Status.METHOD_NOT_ALLOWED).build();
    }













}
