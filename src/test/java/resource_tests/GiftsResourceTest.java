package edu.oswego.csc480.rest.gift_service;

import edu.oswego.csc480.entities.Gift;
import edu.oswego.csc480.entities.Person;
import edu.oswego.csc480.entities.User;
import edu.oswego.csc480.repositories.UserRepository;
import edu.oswego.csc480.rest.gift_service.GiftResource;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GiftsResourceTest {
  private UserRepository UserRepository;
  private GiftResource giftResource;

  @BeforeEach
  public void setUp() throws Exception {
    userRepository = mock(UserRepository.class);
    resource = new GiftResource();
    java.lang.reflect.Field field = 
      GiftResource.class.getDeclaredField("userRepository");
    field.setAccessible(true);
    field.set(resource, userRepository);
  }

  //get all gifts for all people for user 
  @Test
  public void getPeople_200() {
    User user = new User();

    Person person1 = new Person();
    person1.setId(1);
    Person person2 = new Person();
    person2.setId(2);
    ArrayList<Person> people = new ArrayList<>();
    people.add(person1);
    people.add(person2);
    user.setPeople(people);

    Gift gift1 = new Gift();
    Gift gift2 = new Gift();
    Gift gift3 = new Gift();

    ArrayList<Gift> gifts1 = new ArrayList<>();
    ArrayList<Gift> gifts2 = new ArrayList<>();
    gifts1.add(gift1);
    gifts1.add(gift2);
    gifts2.add(gift3);
    person1.setGifts(gifts1);
    person2.setGifts(gifts2);

    Response response = resource.getEveryGiftFromUser();
    assertEquals(200, response.getStatus());


  }




}
