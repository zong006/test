package vttp.ssf.assessment.eventmanagement.services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.JsonValue;
import vttp.ssf.assessment.eventmanagement.models.Event;
import vttp.ssf.assessment.eventmanagement.models.Registration;
import vttp.ssf.assessment.eventmanagement.repositories.RedisRepository;

@Service
public class DatabaseService {

    @Autowired
    RedisRepository redisRepository; 
    
    // Task 1

    // return list of events in console
    public List<Event> readFile(String fileName) throws IOException { 

        // list to hold events 
        List<Event> events = new ArrayList<>(); 

        // String path = "./data/" + fileName;
        String path = fileName;

        // // read "events.json" using a stream 
        // InputStream inputStream = getClass().getClassLoader().getResourceAsStream(path);

        ClassPathResource resource = new ClassPathResource(path);

        InputStream inputStream = resource.getInputStream();

        
        if (inputStream == null) {
            throw new FileNotFoundException("events.json not found");
        }

        // use JsonReader to read stream 
        JsonReader jReader = Json.createReader(inputStream);

        // read JsonArray of objects 
        // [{}, {}, {}]
        JsonArray jArray = jReader.readArray(); 

        for (JsonValue jvEvent : jArray) {

            // cast JsonValue into JsonObject 
            JsonObject joEvent = (JsonObject) jvEvent; 

            // extract information 
            Integer eventId = joEvent.getInt("eventId");
            String eventName = joEvent.getString("eventName");
            Integer eventSize = joEvent.getInt("eventSize");
            Long eventDate = joEvent.getJsonNumber("eventDate").longValue();
            Integer participants = joEvent.getInt("participants");

            // create Event POJO 
            Event event = new Event(eventId, eventName, eventSize, eventDate, participants);

            // add event to list (for printing to console)
            events.add(event);

            // save event to redis 
            redisRepository.saveRecord(event);

        }

        return events;
    }

    // task 5 
    public List<Event> getAllEvents() { 

        List<Object> eventsValues = redisRepository.getAllEventsFromRedis();

        // list to hold all event POJOs
        List<Event> events = new ArrayList<>();

        // convert each JSON strings into Event POJO using a loop 
        for (Object joEvent : eventsValues) {

            String jsEvent = joEvent.toString(); 
            Event event = redisRepository.JsonStringToPOJO(jsEvent);
            events.add(event);

        }

        return events;

    }

    // task 5 
	// helper method 
	// convert long into date 
	public Date LongToDate(Long dateLong) {

		Date newDate = new Date(dateLong);

		return newDate;

	}

    // task 6 
    // helper method 
    // get event with ID 
    public Event getEventFromRepo(Integer index) {

        return redisRepository.getEvent(index);

    }

    // task 7 
    // helper method 
    // check if user is 21 years old and above 
    public Boolean checkAge(LocalDate dob) {

        LocalDate now = LocalDate.now(); 

        int age = Period.between(dob, now).getYears(); 

        if (age >= 21) {
            return true; 

        }

        return false;

    }

    // task 7 
    // helper method 
    // check if r.numTicketsRequested + e.participants < e.eventSize
    public Boolean checkParticipants(Event event, Registration registration) {
        
        int numTicketsRequested = registration.getNumTicketsRequested();
        int participants = event.getParticipants(); 
        int eventSize = event.getEventSize(); 

        if (numTicketsRequested + participants <= eventSize) {

            // increment num participants in redis 
            redisRepository.incrementParticipants(event, numTicketsRequested + participants);

            return true;

        }

        return false;

    }

}
