package vttp.ssf.assessment.eventmanagement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import vttp.ssf.assessment.eventmanagement.models.Event;
import vttp.ssf.assessment.eventmanagement.repositories.RedisRepository;
import vttp.ssf.assessment.eventmanagement.services.DatabaseService;

@SpringBootApplication
public class EventmanagementApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(EventmanagementApplication.class, args);
	}

	// Task 1

	@Autowired
	DatabaseService databaseService;

	// testing task 3 + 4
	@Autowired
	RedisRepository redisRepository;

	@Override
	public void run(String... args) throws Exception {

		// executes DatabaseService.readFile(String fileName)
		List<Event> events = databaseService.readFile("events.json");

		// print list of events in console 
		for (Event e : events) {

			System.out.println(e);

		}

		// // testing task 3
		// System.out.println("number of event entries in redis: " + redisRepository.getNumberOfEvents());

		// testing task 4
		System.out.println("testing task 4:");
		Event e = redisRepository.getEvent(1);
		System.out.println(e);

	}
	

}
