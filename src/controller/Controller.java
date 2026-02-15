package controller;

import models.exceptions.CriticalStatusException;
import models.Status;
import models.exceptions.InvalidTradeException;
import services.EventService;
import services.Logger;

import java.util.Random;
import java.util.Scanner;

public class Controller {
    private final Status status;
    private final EventService eventService;
    private final Logger logger;
    private final Random rand = new Random();
    private boolean debugMode;

    public Controller(Status status, EventService eventService, Logger logger) {
        this.status = status;
        this.eventService = eventService;
        this.logger = logger;
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
                case 1 -> eventStorm(sc);
                case 2 -> eventTrade(sc);
                case 3 -> eventEngine(sc);
                case 4 -> eventAlien(sc);
                case 5 -> eventAstroid(sc);
                case 6 -> eventPirate(sc);
                case 7 -> eventDerelict(sc);
                default -> {System.out.println("This is not an event!");}
            }

            eventCount++;
        }
    }

    public void eventStorm(Scanner sc) throws CriticalStatusException {
        System.out.println("""
                EVENT 1 - Storm Ahead!
                Captain we have a problem, there is a storm ahead of us and we need to know what to do?
                
                Choose an option:
                (1) Its not a big deal (go into the storm)
                (2) Lets go around the storm (you prepare the ship for a detour)""");

        while (true) {
            int input = readInt("\nChoice: ", sc);

            String result = eventService.eventStorm(input);
            System.out.println(result);

            if (input == 1 || input == 2) {
                System.out.println(status.printStatus());
                eventService.checkCriticalStatus();
                break;
            }
        }
    }

    public void eventTrade(Scanner sc) throws CriticalStatusException {
        System.out.println("""
                EVENT 2 - Trade Opportunity!
                Captain we have a problem, there is a trade for you
                
                Choose an option:
                (1) Trade scrap Metal for fuel (1 scrap for 5 fuel)
                (2) Buy shield for scrap Metal (Level 1 shield for 5 scrap)
                (3) Decline (you dont trade)""");

        while (true) {
            try {
                int input = readInt("\nChoice: ", sc);

                switch (input) {
                    case 1 -> {
                        System.out.println("How much do you want to trade? \n");
                        int scrap = readInt("Choice: ", sc);

                        String result = eventService.tradeScrapForFuel(scrap);

                        System.out.println(result);

                        System.out.println(status.printStatus());
                        eventService.checkCriticalStatus();
                        return;
                    }

                    case 2 -> {
                        String result = eventService.buyShield();

                        System.out.println(result);

                        System.out.println(status.printStatus());
                        eventService.checkCriticalStatus();
                        return;
                    }

                    case 3 -> {
                        String result = eventService.declineTrade();
                        System.out.println(result);

                        System.out.println(status.printStatus());
                        eventService.checkCriticalStatus();
                        return;
                    }

                    default -> System.out.println("Not an option! Try again");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid: " + e.getMessage());

            } catch (InvalidTradeException e) {
                System.out.println("Trade error: " + e.getMessage());
            }
        }
    }

    public void eventEngine(Scanner sc) throws CriticalStatusException {
        System.out.println("""
                EVENT 3 - Engine Failure!
                Captain oure ships motor has stopped working we need to use a repair kid or we can always try turning it on and off but this my not work
                
                Choose an option:
                (1) We do have a repair kit use that (this will make the motor start)
                (2) We dont have a repair kit (there is a 40 procent that turning it on and off will work )""");

        while (true) {
            int input = readInt("\nChoice: ", sc);

            switch (input) {
                case 1 -> {
                    String result = eventService.useRepairKit();

                    if (result == null) {
                        System.out.println("You have already used the repair kit before, Try option 2");
                        continue;
                    }

                    System.out.println(result);
                    System.out.println(status.printStatus());
                    eventService.checkCriticalStatus();
                    return;
                }

                case 2 -> {
                    try {
                        String result = eventService.attemptRestart();
                        System.out.println(result);
                        return;

                    } finally {
                        System.out.println("Systems check complete.");

                        System.out.println(status.printStatus());
                        eventService.checkCriticalStatus();
                        logger.addLog("Event 3: Motor restart attempt completed.");
                    }
                }

                default -> System.out.println("Not an option! Try again");
            }
        }
    }

    public void eventAlien(Scanner sc) throws CriticalStatusException {
        System.out.println("""
                EVENT 4 - Alien Encounter!
                Captain we have a problem, there is an alien ship in front of us and we need to know what to do?
                
                Choose an option:
                (1) Attack the alien ship
                (2) Try to communicate with the alien ship""");

        while (true) {
            int input = readInt("\nChoice: ", sc);

            String result = eventService.eventAlien(input);
            System.out.println(result);

            if (input == 1 || input == 2) {
                System.out.println(status.printStatus());
                eventService.checkCriticalStatus();
                break;
            }
        }
    }

    public void eventAstroid(Scanner sc) throws CriticalStatusException {
        System.out.println("""
                EVENT 5 - Asteroid Field!
                Captain we have a problem, there is an asteroid field in front of us and we need to know what to do?
                
                Choose an option:
                (1) Try to navigate through the asteroid field
                (2) Go around the asteroid field""");

        while (true) {
            int input = readInt("\nChoice: ", sc);

            String result = eventService.eventAsteroid(input);
            System.out.println(result);

            if (input == 1 || input == 2) {
                System.out.println(status.printStatus());
                eventService.checkCriticalStatus();
                break;
            }
        }
    }

    public void eventPirate(Scanner sc) throws CriticalStatusException {
        System.out.println("""
                EVENT 6 - Space Pirates!
                Captain we have a problem, there are space pirates attacking us and we need to know what to do?
                
                Choose an option:
                (1) Fight the space pirates
                (2) Try to escape from the space pirates""");

        while (true) {
            int input = readInt("\nChoice: ", sc);

            String result = eventService.eventPirate(input);
            System.out.println(result);

            if (input == 1 || input == 2) {
                System.out.println(status.printStatus());
                eventService.checkCriticalStatus();
                break;
            }
        }
    }

    public void eventDerelict(Scanner sc) throws CriticalStatusException {
        System.out.println("""
                EVENT 7 - Derelict Ship!
                Captain we have a problem, there is a derelict ship in front of us and we need to know what to do?
                
                Choose an option:
                (1) Board the derelict ship
                (2) Ignore the derelict ship""");

        while (true) {
            int input = readInt("\nChoice: ", sc);

            String result = eventService.eventDerelict(input);
            System.out.println(result);

            if (input == 1 || input == 2) {
                System.out.println(status.printStatus());
                eventService.checkCriticalStatus();
                break;
            }
        }
    }

    public int readInt(String prompt, Scanner sc) {
        while (true) {
            System.out.print(prompt);

            String input = sc.nextLine().trim();

            if (input.isEmpty()) {
                System.out.println("Input is empty");
                continue;
            }

            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number");
            }

        }
    }
}
