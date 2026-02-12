package services;

import models.Status;
import models.exceptions.CriticalStatusException;
import models.exceptions.InvalidTradeException;

import java.util.Random;
import java.util.Scanner;

public class EventService {
    private final Status status;
    private final Logger logger;
    private final Random rand = new Random();

    public EventService(Status status, Logger logger) {
        this.status = status;
        this.logger = logger;
    }

    private void checkCriticalStatus() throws CriticalStatusException {
        if (status.getFuel() <= 0) {
            throw new CriticalStatusException("Ship ran out of fuel!");
        }

        if (status.getFuel() <= 10) {
            throw new CriticalStatusException("Low of Fuel!");
        }

        if (status.getIntegrity() <= 20) {
            throw new CriticalStatusException("Ship almost destroyed");
        }

        if (status.getIntegrity() <= 0) {
            throw new CriticalStatusException("Ship destroyed!");
        }

        if (status.getShield() < 0) {
            throw new CriticalStatusException("Shield offline!");
        }

        // Engine failure twice
    }

    public void eventStorm(Scanner sc) throws CriticalStatusException {
        boolean running = true;
        System.out.println("EVENT 1 - Storm Ahead!");

        while (running) {
            System.out.println("Captain we have a problem, there is a storm a head of us and we need to know what to do: \n");

            System.out.println("(1) its not a big deal (go in to  the storm )");
            System.out.println("(2) lets go around the storm (you prepare the ship for a detour)");
            int input = readInt("Choice: ", sc);

            switch (input) {
                case 1 -> {
                    int damage = rand.nextInt(100)+1;
                    System.out.println("OW the storm did "+ damage + " to the ship but at least");
                    int newIntegrity = status.getIntegrity() - damage;

                    if (status.getIntegrity() <= 0) {
                        System.out.println("Ship dead");
                    }

                    status.setIntegrity(newIntegrity);

                    System.out.println(status.printStatus());
                    logger.addLog("Event 1: Storm chosen, damage: " + damage);
                    checkCriticalStatus();


                    return;
                }

                case 2 -> {
                    int fuel = 20;
                    System.out.println("Captain we got around the storm but we lost "+fuel+" to the ship  ");
                    int newFuel = status.getFuel() - fuel;
                    status.setFuel(newFuel);

                    System.out.println(status.printStatus());
                    logger.addLog("Event 1: Storm avoided, fuel loss: " + fuel);
                    checkCriticalStatus();

                    return;
                }

                default -> System.out.println("Not an option! Try again");
            }
        }
    }

    public void eventTrade(Scanner sc) throws CriticalStatusException {
        boolean running = true;
        System.out.println("EVENT 2 - Trade Opportunity!");


        System.out.println("Captain we have a problem, there is a trade for you: \n");
        System.out.println("(1) Trade scrap Metal for fuel (1 scrap for 5 fuel) \n");
        System.out.println("(2) Buy shield for scrap Metal (Level 1 shield for 5 scrap) \n");
        System.out.println("(3) Decline (you dont trade)");

        while (running) {

            int input = readInt("Choice: ", sc);

            switch (input) {
                case 1 -> {
                    System.out.println("How much do you want to trade \n");
                    int scrap = readInt("Choice: ", sc);
                    int remaingScrap = status.getScrapMetal() - scrap;
                    status.setScrapMetal(remaingScrap);

                    int  newFuel = (scrap * 5);

                    if (status.getScrapMetal() <= 0 || newFuel <= 0) {
                        throw new IllegalArgumentException("You dont have enough scrap metal to trade!");
                    }

                    if ( status.getFuel() > 100) {
                        throw new IllegalArgumentException("to much fuel can't get over 100 in fuel");
                    }

                    status.setFuel(newFuel);

                    System.out.println(status.printStatus());
                    logger.addLog("Event 2: Traded " + scrap + " scrap for " + newFuel + " fuel");
                    checkCriticalStatus();

                    return;
                }

                case 2 -> {
                    int amount = 5;

                    if (status.getScrapMetal() < amount) {
                        throw new InvalidTradeException("You do not have enough scrap metal to trade");
                    }

                    status.setScrapMetal(status.getScrapMetal() - amount);
                    status.setShield(status.getShield() + 1);

                    System.out.println(status.printStatus());
                    logger.addLog("Event 2: Shield upgrade to level 1");
                    checkCriticalStatus();
                    return;
                }

                case 3 -> {
                    return;
                }

                default -> System.out.println("Not an option! Try again");
            }
        }
    }

    public void eventEngine(Scanner sc) {
        boolean running = true;

        while (running) {
            int input = readInt("Choice: ", sc);

            switch (input) {
                case 1 -> {
                    System.out.println("Hello3!");
                    running = false;
                }
                default -> System.out.println("Not an option! Try again");
            }
        }
    }

    public int readInt(String prompt, Scanner sc) {
        while (true) {
            System.out.print(prompt);

            String input = sc.nextLine().trim();

            if (input.isEmpty()) {
                System.out.println("input is empty");
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
