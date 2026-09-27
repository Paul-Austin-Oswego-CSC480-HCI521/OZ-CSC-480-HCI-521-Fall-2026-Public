package edu.oswego.csc480.rest.gift_service;

import edu.oswego.csc480.entities.Gift;
import edu.oswego.csc480.entities.Occasion;
import edu.oswego.csc480.entities.User;
import edu.oswego.csc480.repositories.UserRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;

import java.util.Optional;

import static jakarta.ws.rs.core.Response.Status;

@Path("user/{uid}/gift/{gid}/occasion")
public class OccasionResource {

    private @PathParam("uid") Integer uid;
    private @PathParam("gid") Integer gid;

    @Inject
    private UserRepository uRepo;


    //TODO:         GET POST PUT DELETE

    @GET
    public Response getOccasion(){
        Optional<User> user = uRepo.findById(uid);

        if (user.isEmpty()) return Response.status(Status.NOT_FOUND).build();

        Gift gift = getGift(user.get(), gid);

        if (gift == null) return Response.status(Status.NOT_FOUND).build();

        Occasion occasion = gift.getOccasion();

        // occasion could be null, in the case no occasion was given unlike giftStatus

        return Response.ok(occasion).build();

    }

    @POST
    public Response postOccasion(Occasion occasion){
        Optional<User> user = uRepo.findById(uid);

        if (user.isEmpty()) return Response.status(Status.NOT_FOUND).build();

        Gift gift = getGift(user.get(), gid);

        gift.setOccasion(occasion);

        User nuser = uRepo.save(user.get());


        gift = getGift(nuser, gid);

        if (gift == null) return Response.status(Status.INTERNAL_SERVER_ERROR).build();

        return Response.created(UriBuilder.fromPath("user/{uid}/gift/{gid}/occasion").build(uid, gid)).build();

    }

    @PUT
    public Response putOccasion(Occasion occasion){
        Optional<User> user = uRepo.findById(uid);
        if (user.isEmpty()) return Response.status(Status.NOT_FOUND).build();
        Gift gift = getGift(user.get(), gid);
        if (gift == null) return Response.status(Status.INTERNAL_SERVER_ERROR).build();

        gift.setOccasion(occasion);

        User nuser = uRepo.save(user.get());
        gift = getGift(nuser, gid);

        return Response.ok(gift).build();

    }





    private Gift getGift(User user, Integer gid){
        return user.getPeople().stream()
                .flatMap(p -> p.getGifts().stream())
                .filter(g->g.getId().equals(gid))
                .findFirst()
                .orElse(null);
    }


}
