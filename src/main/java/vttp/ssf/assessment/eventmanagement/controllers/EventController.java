package vttp.ssf.assessment.eventmanagement.controllers;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import vttp.ssf.assessment.eventmanagement.models.Event;
import vttp.ssf.assessment.eventmanagement.services.DatabaseService;

@Controller
public class EventController {

	@Autowired
	DatabaseService databaseService;

	// Task 5
	// get list of events
	@GetMapping("/events/listing")
	public String displayEvents(Model model) { 

		// call service layer 
		List<Event> events = databaseService.getAllEvents();

		model.addAttribute("events", events);

		// return "events-list.html"
		return "events-list";

	}

}
