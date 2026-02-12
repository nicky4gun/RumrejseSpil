package controller;

import models.exceptions.CriticalStatusException;
import models.Status;
import services.EventService;

import java.util.Random;
import java.util.Scanner;

public class Controller {
    private final Status status;
    private final EventService eventService;
    private final Random rand = new Random();

    public Controller(Status status, EventService eventService) {
        this.status = status;
        this.eventService = eventService;
    }

    public void showStatus() {
        System.out.println(status.printStatus());
    }

    public void startGame(Scanner sc) throws CriticalStatusException {
        int eventCount = 0;
        int maxEvents = 7;

        while (eventCount < maxEvents) {
            int randInt = rand.nextInt(7) + 1;

            switch (randInt) {
                case 1 -> eventService.eventStorm(sc);
                case 2 -> eventService.eventTrade(sc);
                case 3 -> eventService.eventEngine(sc);
                case 4 -> eventService.eventAlien(sc);
                case 5 -> eventService.eventAsteroid(sc);
                case 6 -> eventService.eventPirate(sc);
                case 7 -> eventService.eventDerelict(sc);
                default -> {System.out.println("This is not an event!");}
            }

            eventCount++;
        }
    }
}
