package vttp.ssf.assessment.eventmanagement.controllers;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;
import vttp.ssf.assessment.eventmanagement.models.Event;
import vttp.ssf.assessment.eventmanagement.models.Registration;
import vttp.ssf.assessment.eventmanagement.services.DatabaseService;

@Controller
public class RegistrationController {
    
    @Autowired
    DatabaseService databaseService;

    // Task 6
    // GET /events/register/<event id>
    @GetMapping("/events/register/{eventId}")
    public String registerForEventPage(@PathVariable String eventId, Model model) { 

        // get event from redis using event id
        Event event = databaseService.getEventFromRepo(Integer.parseInt(eventId));

        model.addAttribute("event", event);



        // create new Registration object 
        Registration registration = new Registration(); 

        model.addAttribute("registration", registration);

        // return event-register.html
        return "event-register";

    }

    // Task 7
    // handler method for POST "/registration/register" request
    @PostMapping("/registration/register/{eventId}")
    public String processRegistration(@PathVariable String eventId, @Valid @ModelAttribute Registration registration, BindingResult bindingResult, Model model) {

        // get event from redis using event id
        Event event = databaseService.getEventFromRepo(Integer.parseInt(eventId));

        model.addAttribute("event", event);



        if (bindingResult.hasErrors()) {
            return "event-register";

        }

        // if user is not 21 and above 
        if (!databaseService.checkAge(registration.getBirthDate())) {

            String errorMessage = "You are not 21 years old or above.";

            model.addAttribute("errorMessage", errorMessage);

            return "ErrorRegistration";

        }

        // if ticket count exceeds max size + participants 
        else if (!databaseService.checkParticipants(event, registration)) {

            String errorMessage = "Your request for tickets exceeded the event size.";

            model.addAttribute("errorMessage", errorMessage);

            return "ErrorRegistration";

        } else {

            return "SuccessRegistration";

        }

    }


}
