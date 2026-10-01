package edu.oswego.csc480.rest.gift_service;

import edu.oswego.csc480.entities.Gift;
import edu.oswego.csc480.entities.Person;
import edu.oswego.csc480.entities.User;
import edu.oswego.csc480.repositories.UserRepository;
import edu.oswego.csc480.rest.gift_service.GiftResource;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GiftsResourceTest {
  private UserRepository UserRepository;
  private GiftResource resource;

  @BeforeEach
  public void setUp() throws Exception {
    userRepository = mock(UserRepository.class);
    resource = new GiftResource();
    
    java.lang.reflect.Field repoField = 
        GiftResource.class.getDeclaredField("uRepo");
    repoField.setAccessible(true);
    repoField.set(resource, userRepository);
    
    java.lang.reflect.Field field = 
      GiftResource.class.getDeclaredField("uid");
    field.setAccessible(true);
    field.set(resource, 1);
  }

  //get all gifts for all people for user 
  @Test
  public void getAllGifts_200() {
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
    gift1.setId(1);
    Gift gift2 = new Gift();
    gift2.setId(2);
    Gift gift3 = new Gift();
    gift3.setId(3);

    ArrayList<Gift> gifts1 = new ArrayList<>();
    gifts1.add(gift1);
    gifts1.add(gift2);
    ArrayList<Gift> gifts2 = new ArrayList<>();
    gifts2.add(gift3);
    
    person1.setGifts(gifts1);
    person2.setGifts(gifts2);
    when(userRepository.findById(1)).thenReturn(Optional.of(user));
    Response response = resource.getEveryGiftFromUser();
    assertEquals(200, response.getStatus());
    ArrayList<Gift> expectedGifts = new ArrayList<>();
    expectedGifts.add(gift1);
    expectedGifts.add(gift2);
    expectedGifts.add(gift3);

    assertEquals(expectedGifts, response.getEntity());
    verify(userRepository).findById(1);
  }

  //get all gifts no user 404
  @Test
  public void getAllGifts_404_1() {
    when(userRepository.findById(1)).thenReturn(Optional.empty());
    Response response = resource.getEveryGiftFromUser();
    assertEquals(404, response.getStatus());
    verify(userRepository).findById(1);
  }

  //get all gifts user, no people 404
  @Test
  public void getAllGifts_404_2() {
    User user = new User();
    user.setPeople(new ArrayList<>());
    when(userRepository.findById(1)).thenReturn(Optional.of(user));
    Response response = resource.getEveryGiftFromUser();
    assertEquals(404, response.getStatus());
    verify(userRepository).findById(1);
  }

  //get gift from specific person 
  @Test
  public void getAllGifts_OnePerson_200() {
    User user = new User();
    Person person = new Person();
    person.setId(1);
    Gift gift1 = new Gift();
    gift1.setId(10);
    Gift gift2 = new Gift();
    gift2.setId(20);

    ArrayList<Gift> gifts = new ArrayList<>();
    gifts.add(gift1);
    gifts.add(gift2);
    person.setGifts(gifts);

    ArrayList<Person> people = new ArrayList<>();
    people.add(person);
    user.setPeople(people);

    when(userRepository.findById(1)).thenReturn(Optional.of(user));
    Response response = resource.getEveryGiftFromSpecificPerson(1);
    assertEquals(200, response.getStatus());
    assertEquals(gifts, response.getEntity());
    verify(userRepository).findById(1);
  }

  //get gift for person 404 no user
  @Test
  public void getAllGifts_OnePerson_404_1() {
    when(userRepository.findById(1)).thenReturn(Optional.empty());
    Response response = resource.getEveryGiftFromSpecificPerson(1);
    assertEquals(404, response.getStatus());
    verify(userRepository).findById(1);
  }

  //get gift for person 404 no person
  @Test
  public void getAllGifts_OnePerson_404_2() {
    User user = new User();
    Person person = new Person();
    person.setId(1);
    ArrayList<Person> people = new ArrayList<>();
    people.add(person);
    user.setPeople(people);
    when(userRepository.findById(1)).thenReturn(Optional.of(user));
    Response response = resource.getEveryGiftFromSpecificPerson(999);
    assertEquals(404, response.getStatus());
    verify(userRepository).findById(1);
  }

  //get single gift 200
  @Test
  public void getOneGift_200() {
    User user = new User();
    Person person = new Person();
    person.setId(1);
    Gift gift1 = new Gift();
    gift1.setId(10);
    Gift gift2 = new Gift();
    gift2.setId(20);

    ArrayList<Gift> gifts = new ArrayList<>();
    gifts.add(gift1);
    gifts.add(gift2);
    person.setGifts(gifts);

    ArrayList<Person> people = new ArrayList<>();
    people.add(person);
    user.setPeople(people);

    when(userRepository.findById(1)).thenReturn(Optional.of(user));
    Response response = resource.getGiftDirect(10);

    assertEquals(200, response.getStatus());
    assertEquals(gift, response.getEntity());
    verify(userRepository).findById(1);
  }

  //get single gift 404 no user
  @Test
  public void getOneGift_404_1() {
    when(userRepository.findById(1)).thenReturn(Optional.empty());
    Response response = resource.getGiftDirect(10);
    assertEquals(404, response.getStatus());
    verify(userRepository).findById(1);
  }

  //get single gift 404 no gift
  @Test 
  public void getOneGift_404_2() {
    User user = new User();
    Person person = new Person();
    person.setId(1);
    Gift gift = new Gift();
    gift.setId(10);
    ArrayList<Gift> gifts = new ArrayList<>();
    gifts.add(gift);
    person.setGifts(gifts);
    ArrayList<Person> people = new ArrayList<>();
    people.add(person);
    user.setPeople(people);

    when(userRepository.findById(1)).thenReturn(Optional.of(user));
    Response response = resource.getGiftDirect(999);
    assertEquals(404, response.getStatus());
    verify(userRepository).findById(1);
  }

  //create gift 201
  @Test
  public void createGift_201() {
    User user = new User();
    Person person = new Person();
    person.setId(1);
    person.setGifts(new ArrayList<>());

    ArrayList<Person> people = new ArrayList<>();
    people.add(person);
    user.setPeople(people);

    Gift gift = new Gift();
    gift.setId(10);
    gift.setName("Phone");
    gift.setType("Electronics");

    when(userRepository.findById(1)).thenReturn(Optional.of(user));
    when(userRepository.save(user)).thenReturn(user);

    Response response = resource.postNewGiftToPerson(1, gift);
    assertEquals(201, response.getStatus());
    assertTrue(person.getGifts().contains(gift));
    assertEquals(person, gift.getPerson());
    verify(userRepository).findById(1);
    verify(userRepository).save(user);
  }

  
    

  
}

