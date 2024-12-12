package vttp.ssf.assessment.eventmanagement.models;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class Event {

    // task 0

    private Integer eventId; 
    private String eventName; 
    private Integer eventSize; 
    private Long eventDate; 
    private Integer participants;

    private Date newDate;
    
    // constructors
    public Event() {
    }

    public Event(Integer eventId, String eventName, Integer eventSize, Long eventDate, Integer participants) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.eventSize = eventSize;
        this.eventDate = eventDate;
        this.participants = participants;

        this.newDate = convertDate(eventDate);

    }


    // getter setters 
    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public Integer getEventSize() {
        return eventSize;
    }

    public void setEventSize(Integer eventSize) {
        this.eventSize = eventSize;
    }

    public Long getEventDate() {
        return eventDate;
    }

    public void setEventDate(Long eventDate) {
        this.eventDate = eventDate;
    }

    public Integer getParticipants() {
        return participants;
    }

    public void setParticipants(Integer participants) {
        this.participants = participants;
    }


    // new date in Date format
    public Date getNewDate() {
        return newDate;
    }

    public void setNewDate(Date newDate) {
        this.newDate = newDate;
    }


    // toString()
    @Override
    public String toString() {
        return eventId + "," + eventName + "," + eventSize + "," + eventDate + "," + participants;
    }

    // convert int date to Date 
    public Date convertDate(Long dateOld) {
        return new Date(dateOld);

    }
      
}
