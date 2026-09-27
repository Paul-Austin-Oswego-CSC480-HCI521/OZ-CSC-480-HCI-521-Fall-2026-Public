package edu.oswego.csc480.rest.gift_service;

import edu.oswego.csc480.entities.Gift;
import edu.oswego.csc480.entities.Occasion;
import edu.oswego.csc480.entities.User;
import edu.oswego.csc480.repositories.UserRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;
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

        Gift gift = user.get().getPeople().stream()
                .flatMap(p -> p.getGifts().stream())
                .filter(g->g.getId().equals(gid))
                .findFirst()
                .orElse(null);

        if (gift == null) return Response.status(Status.NOT_FOUND).build();

        Occasion occasion = gift.getOccasion();

        // occasion could be null, in the case no occasion was given unlike giftStatus

        return Response.ok(occasion).build();

    }

    @POST
    public Response postOccasion(Occasion occasion){
        Optional<User> user = uRepo.findById(uid);

        if (user.isEmpty()) return Response.status(Status.NOT_FOUND).build();

        Gift gift = user.get().getPeople().stream()
                .flatMap(p -> p.getGifts().stream())
                .filter(g->g.getId().equals(gid))
                .findFirst()
                .orElse(null);

        gift.setOccasion(occasion);

        User nuser = uRepo.save(user.get());


        gift = nuser.getPeople().stream()
                .flatMap(p -> p.getGifts().stream())
                .filter(g->g.getId().equals(gid))
                .findFirst()
                .orElse(null);

        if (gift == null) return Response.status(Status.INTERNAL_SERVER_ERROR).build();

        return Response.ok(gift.getOccasion()).build();

    }









}
