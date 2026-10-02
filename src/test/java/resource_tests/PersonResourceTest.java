package resource_tests;

import edu.oswego.csc480.entities.Person;
import edu.oswego.csc480.entities.User;
import edu.oswego.csc480.repositories.UserRepository;
import edu.oswego.csc480.rest.gift_service.PersonResource;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PersonResourceTest {
    private UserRepository userRepository;
    private PersonResource resource;

    @BeforeEach
    public void setUp() throws Exception {
        userRepository = mock(UserRepository.class);
        resource = new PersonResource();
        java.lang.reflect.Field field = PersonResource.class.getDeclaredField("userRepository");
        field.setAccessible(true);
        field.set(resource, userRepository);
    }

    //get all people for a user 200 success
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
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        Response response = resource.getPeople(1);
        assertEquals(200, response.getStatus());
        assertEquals(people, response.getEntity());
        verify(userRepository).findById(1);
    }

    //get all people for a user 404 NO USER fail
    @Test
    public void getPeople_404() {
        when(userRepository.findById(1)).thenReturn(Optional.empty());
        Response response = resource.getPeople(1);
        assertEquals(404, response.getStatus());
        verify(userRepository).findById(1);
    }

    //get single person for a user 200 success
    @Test
    public void getPerson_200() {
        User user = new User();
        Person person = new Person();
        person.setId(1);
        ArrayList<Person> people = new ArrayList<>();
        people.add(person);
        user.setPeople(people);
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        Response response = resource.getPerson(1, 1);
        assertEquals(200, response.getStatus());
        assertEquals(person, response.getEntity());
        verify(userRepository).findById(1);
    }

    //get single person for a user 404 NO USER fail
    @Test
    public void getPerson_404_1() {
        when(userRepository.findById(1)).thenReturn(Optional.empty());
        Response response = resource.getPerson(1, 1);
        assertEquals(404, response.getStatus());
        verify(userRepository).findById(1);
    }

    //get single person for a user 404 PERSON NO EXIST fail
    @Test
    public void getPerson_404_2() {
        User user = new User();
        Person person = new Person();
        person.setId(1);
        ArrayList<Person> people = new ArrayList<>();
        people.add(person);
        user.setPeople(people);
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        Response response = resource.getPerson(1, 999);
        assertEquals(404, response.getStatus());
        verify(userRepository).findById(1);
    }

    //create person 201 success
    @Test
    public void createPerson_201() {
        User user= new User();
        ArrayList<Person> people = new ArrayList<>();
        user.setPeople(people);
        Person person = new Person();
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);
        Response response = resource.createPerson(1, person);
        assertEquals(201, response.getStatus());
        assertEquals(person, response.getEntity());
        assertEquals(user, person.getUser());
        assertTrue(user.getPeople().contains(person));
        verify(userRepository).findById(1);
        verify(userRepository).save(user);
    }

    //create person 400 NO PERSON fail
    @Test
    public void createPerson_400() {
        Response response = resource.createPerson(1, null);
        assertEquals(400, response.getStatus());
        verify(userRepository, never()).findById(any());
        verify(userRepository, never()).save(any(User.class));
    }

    //create person 404 NO USER fail
    @Test
    public void createPerson_404() {
        Person person = new Person();
        when(userRepository.findById(1)).thenReturn(Optional.empty());
        Response response = resource.createPerson(1, person);
        assertEquals(404, response.getStatus());
        verify(userRepository).findById(1);
        verify(userRepository, never()).save(any(User.class));
    }

    //update person 200 success
    @Test
    public void updatePerson_200() {
        User user = new User();
        Person existingPerson = new Person();
        existingPerson.setId(1);
        ArrayList<Person> people = new ArrayList<>();
        people.add(existingPerson);
        user.setPeople(people);
        Person updatedPerson = new Person();
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);
        Response response = resource.updatePerson(1, 1, updatedPerson);
        assertEquals(200, response.getStatus());
        assertEquals(updatedPerson, response.getEntity());
        assertEquals(1, updatedPerson.getId());
        assertEquals(user, updatedPerson.getUser());
        assertFalse(user.getPeople().contains(existingPerson));
        assertTrue(user.getPeople().contains(updatedPerson));
        verify(userRepository).findById(1);
        verify(userRepository).save(user);
    }

    //update person 404 NO USER fail
    @Test
    public void updatePerson_404_1() {
        Person updatedPerson = new Person();
        when(userRepository.findById(1)).thenReturn(Optional.empty());
        Response response = resource.updatePerson(1, 1, updatedPerson);
        assertEquals(404, response.getStatus());
        verify(userRepository).findById(1);
        verify(userRepository, never()).save(any(User.class));
    }

    //update person 404 NO PERSON fail
    @Test
    public void updatePerson_404_2() {
        User user = new User();
        Person existingPerson = new Person();
        existingPerson.setId(1);
        ArrayList<Person> people = new ArrayList<>();
        people.add(existingPerson);
        user.setPeople(people);
        Person updatedPerson = new Person();
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        Response response = resource.updatePerson(1, 999, updatedPerson);
        assertEquals(404, response.getStatus());
        verify(userRepository).findById(1);
        verify(userRepository, never()).save(any(User.class));
    }

    //delete person 204 success
    @Test
    public void deletePerson_204() {
        User user = new User();
        Person person = new Person();
        person.setId(1);
        ArrayList<Person> people = new ArrayList<>();
        people.add(person);
        user.setPeople(people);
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);
        Response response = resource.deletePerson(1, 1);
        assertEquals(204, response.getStatus());
        assertFalse(user.getPeople().contains(person));
        verify(userRepository).findById(1);
        verify(userRepository).save(user);
    }

    //delete person 404 NO USER fail
    @Test
    public void deletePerson_404_1() {
        when(userRepository.findById(1)).thenReturn(Optional.empty());
        Response response = resource.deletePerson(1,1);
        assertEquals(404, response.getStatus());
        verify(userRepository).findById(1);
        verify(userRepository, never()).save(any(User.class));
    }

    //delete person 404 NO PERSON fail
    @Test
    public void deletePerson_404_2() {
        User user = new User();
        Person existingPerson = new Person();
        existingPerson.setId(1);
        ArrayList<Person> people = new ArrayList<>();
        people.add(existingPerson);
        user.setPeople(people);
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        Response response = resource.deletePerson(1, 999);
        assertEquals(404, response.getStatus());
        verify(userRepository).findById(1);
        verify(userRepository, never()).save(any(User.class));
    }
}
