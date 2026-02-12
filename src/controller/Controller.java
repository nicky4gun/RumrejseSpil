package controller;

import models.exceptions.CriticalStatusException;
import models.Status;
import services.EventService;
import services.Logger;

import java.util.Random;
import java.util.Scanner;

public class Controller {
    private final Status status;
    private final Logger logger;
    private final EventService eventService;
    private final Random rand = new Random();

    public Controller(Status status, Logger logger, EventService eventService) {
        this.status = status;
        this.logger = logger;
        this.eventService = eventService;
    }

    public void showStatus() {
        System.out.println(status.printStatus());
    }

    public void showCase(Scanner sc) throws CriticalStatusException {
        boolean running = true;

        while (running) {
            int randInt = rand.nextInt(3) + 1;

            switch (randInt) {
                case 1 -> eventService.eventStorm(sc);
                case 2 -> eventService.eventTrade(sc);
                case 3 -> eventService.eventEngine(sc);
                default -> {System.out.println("This is not an event!"); running = false;}
            }
        }
    }
}
