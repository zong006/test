package vttp.ssf.assessment.eventmanagement.repositories;

import java.io.StringReader;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.json.JsonReader;
import vttp.ssf.assessment.eventmanagement.models.Event;

@Repository
public class RedisRepository {

	@Autowired
	@Qualifier("redis-string")
	RedisTemplate<String, String> redisTemplate; 

	// Task 2
	// save one event to Redis 
	public void saveRecord(Event event) {
		
		// get event id --> hash key 
		String hashKey = event.getEventId().toString();

		// convert event POJO into JSON string 
		String eventStringData = POJOToJsonString(event);

		// save new event in "events" hash
		redisTemplate.opsForHash().put("events", hashKey, eventStringData);

	}

	// task 2
    // helper method 
    // Event POJO --> JSON Formatted String (saving to Redis)
    public String POJOToJsonString(Event event) { 

        JsonObjectBuilder builder = Json.createObjectBuilder()
                                        .add("eventId", event.getEventId())
										.add("eventName", event.getEventName())
										.add("eventSize", event.getEventSize())
										.add("eventDate", event.getEventDate())
										.add("participants", event.getParticipants());

        JsonObject articleObject = builder.build(); 
        return articleObject.toString(); 

    }


	// Task 3
	public Integer getNumberOfEvents() { 

		return redisTemplate.opsForHash().size("events").intValue();

	}


	// Task 4
	public Event getEvent(Integer index) {

		String eventJsonString = (String) redisTemplate.opsForHash().get("events", index.toString());

		Event event = JsonStringToPOJO(eventJsonString);

		return event; 

	}

	// task 4
	// helper method 
	// JSON Formatted String --> Event POJO 
	public Event JsonStringToPOJO(String eventRaw) {

		StringReader sr = new StringReader(eventRaw);
		JsonReader jr = Json.createReader(sr);
		JsonObject jo = jr.readObject();

		// map JSON fields into Event POJO 
		Event event = new Event(
			jo.getInt("eventId"),
			jo.getString("eventName"),
			jo.getInt("eventSize"),
			jo.getJsonNumber("eventDate").longValue(),
			jo.getInt("participants")

		); 

		return event;

    }

	// task 5 
	// get all events from redis 
	public List<Object> getAllEventsFromRedis() { 

        return redisTemplate.opsForHash().values("events");

    }

	// task 7 
	// increment num participants based on tickets bought 
	public void incrementParticipants(Event event, Integer newParticipantTotal) { 

		event.setParticipants(newParticipantTotal);
		saveRecord(event);

	}

}
