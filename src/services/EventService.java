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

            switch (input) {
                case 1 -> {
                    int damage = rand.nextInt(100) + 1;
                    System.out.println("Ow the storm did "+ damage + " damage to the ship");

                    status.setIntegrity(status.getIntegrity() - damage);

                    System.out.println(status.printStatus());
                    logger.addLog("Event 1: Storm chosen, damage: " + damage);
                    checkCriticalStatus();

                    return;
                }

                case 2 -> {
                    int fuel = 20;
                    System.out.println("Captain we got around the storm but we lost " + fuel + " to the ship");
                    status.setFuel(status.getFuel() - fuel);

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

                        if (scrap <= 0) {
                            throw new IllegalArgumentException("Invalid amount. Please enter a positive number.");
                        }

                        if (scrap > status.getScrapMetal()) {
                            throw new InvalidTradeException("You do not have enough scrap metal to trade");
                        }

                        int fuelAmount = scrap * 5;
                        int newFuel = status.getFuel() + fuelAmount;

                        if (newFuel > 100) {
                            throw new IllegalArgumentException("Trade would exceed maximum fuel capacity (100)");
                        }

                        status.setScrapMetal(status.getScrapMetal() - scrap);
                        status.setFuel(newFuel);

                        System.out.println(status.printStatus());
                        logger.addLog("Event 2: Traded " + scrap + " scrap for " + fuelAmount + " fuel");
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
                        System.out.println("You declined the trade");

                        System.out.println(status.printStatus());
                        logger.addLog("Event 2: Trade declined");
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
                    if (status.getRepairKitUsed()) {
                        System.out.println("You have already used the repair kit before, Try option 2");
                        logger.addLog("Event 3: Mortor could not be repaired because repair kit was already used");
                    }

                    if (status.getIntegrity() < 100) {
                        int newIntegrity = status.getIntegrity() + 20;
                        status.setIntegrity(newIntegrity);
                        System.out.println("Repair kit used, integrity +20");

                    } else {
                        System.out.println("The motor is already at full integrity, the repair kit had no effect");
                        System.out.println("Repair kit used");
                    }

                    status.setRepairKitUsed(true);

                    System.out.println(status.printStatus());
                    logger.addLog("Event3: Motor is now working because we used the repair kit ");
                    checkCriticalStatus();
                    return;
                }

                case 2 -> {
                    try {
                        int chance = rand.nextInt(100);

                        if (chance < 40) {
                            System.out.println("It worked the motor has started working again");
                            logger.addLog("Event 3: Mortor is now working because we turned it on and off");

                        } else if (chance < 70) {
                            int motorDamage = 10;
                            status.setIntegrity(status.getIntegrity() - motorDamage);
                            System.out.println("It took some time but it did work, integrity -10 ");
                            logger.addLog("Event 3: Motor is now working, but it took some damage");

                        } else {
                            System.out.println("Critical failure! The motor is not working and we are lost in space");
                            logger.addLog("Event 3: Motor failure resulted in critical status");
                            throw new CriticalStatusException("We are lost in space captain");
                        }

                        System.out.println(status.printStatus());
                        checkCriticalStatus();
                        return;

                    } finally {
                        logger.addLog("Event 3: Motor restart attempt completed.");
                        System.out.println("Systems check complete.\n");
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

            switch (input) {
                case 1 -> {
                    int damage = rand.nextInt(50) + 1;
                    System.out.println("Ow the alien ship did " + damage + " damage to the ship but at least we got rid of them");

                    status.setIntegrity(status.getIntegrity() - damage);

                    System.out.println(status.printStatus());
                    logger.addLog("Event 4: Attacked alien ship, damage taken: " + damage);
                    checkCriticalStatus();

                    return;
                }

                case 2 -> {
                    if (rand.nextInt(100) < 50) {
                        System.out.println("The aliens were friendly and gave us some scrap metal");
                        int scrapGain = rand.nextInt(5) + 1;
                        status.setScrapMetal(status.getScrapMetal() + scrapGain);

                        System.out.println(status.printStatus());
                        logger.addLog("Event 4: Communicated with aliens, gained " + scrapGain + " scrap metal");
                        checkCriticalStatus();

                    } else {
                        int damage = rand.nextInt(30) + 1;
                        System.out.println("The aliens were hostile and did " + damage + " damage to the ship ");

                        status.setIntegrity(status.getIntegrity() - damage);

                        System.out.println(status.printStatus());
                        logger.addLog("Event 4: Communicated with aliens, took " + damage + " damage");
                        checkCriticalStatus();
                    }

                    return;
                }

                default -> System.out.println("Not an option! Try again");
            }

        }
    }

    public void eventAsteroid(Scanner sc) throws CriticalStatusException {
        System.out.println("""
                EVENT 5 - Asteroid Field!
                Captain we have a problem, there is an asteroid field in front of us and we need to know what to do?
                
                Choose an option:
                (1) Try to navigate through the asteroid field
                (2) Go around the asteroid field""");

        while (true) {
            int input = readInt("\nChoice: ", sc);

            switch (input) {
                case 1 -> {
                    int damage = rand.nextInt(40) + 1;
                    System.out.println("Ow the asteroids did " + damage + " damage to the ship but at least we got through them");

                    status.setIntegrity(status.getIntegrity() - damage);

                    System.out.println(status.printStatus());
                    logger.addLog("Event 5: Navigated asteroid field, damage taken: " + damage);
                    checkCriticalStatus();

                    return;
                }

                case 2 -> {
                    int fuelLoss = 15;
                    System.out.println("We went around the asteroid field, but lost " + fuelLoss + " fuel in the process");
                    status.setFuel(status.getFuel() - fuelLoss);

                    System.out.println(status.printStatus());
                    logger.addLog("Event 5: Avoided asteroid field, fuel loss: " + fuelLoss);
                    checkCriticalStatus();

                    return;
                }

                default -> System.out.println("Not an option! Try again");
            }
        }
    }

    public void eventPirate (Scanner sc) throws CriticalStatusException {
        System.out.println("""
                EVENT 6 - Space Pirates!
                Captain we have a problem, there are space pirates attacking us and we need to know what to do?
                
                Choose an option:
                (1) Fight the space pirates
                (2) Try to escape from the space pirates""");

        while (true) {
            int input = readInt("\nChoice: ", sc);

            switch (input) {
                case 1 -> {
                    int damage = rand.nextInt(60) + 1;
                    System.out.println("Ow the space pirates did " + damage + " damage to the ship but at least we got rid of them");

                    status.setIntegrity(status.getIntegrity() - damage);

                    System.out.println(status.printStatus());
                    logger.addLog("Event 6: Fought space pirates, damage taken: " + damage);
                    checkCriticalStatus();

                    return;
                }

                case 2 -> {
                    if (rand.nextInt(100) < 50) {
                        System.out.println("We successfully escaped from the space pirates!");

                        System.out.println(status.printStatus());
                        logger.addLog("Event 6: Successfully escaped from space pirates");
                        checkCriticalStatus();

                    } else {
                        int damage = rand.nextInt(40) + 1;
                        System.out.println("We failed to escape and the space pirates did " + damage + " damage to the ship ");

                        status.setIntegrity(status.getIntegrity() - damage);

                        System.out.println(status.printStatus());
                        logger.addLog("Event 6: Failed to escape from space pirates, took " + damage + " damage");
                        checkCriticalStatus();
                    }

                    return;
                }

                default -> System.out.println("Not an option! Try again");
            }
        }
    }

    public void eventDerelict (Scanner sc) throws CriticalStatusException {
        System.out.println("""
                EVENT 7 - Derelict Ship!
                Captain we have a problem, there is a derelict ship in front of us and we need to know what to do?
                
                Choose an option:
                (1) Board the derelict ship
                (2) Ignore the derelict ship""");

        while (true) {
            int input = readInt("\nChoice: ", sc);

            switch (input) {
                case 1 -> {
                    if (rand.nextInt(100) < 50) {
                        System.out.println("We found some useful scrap metal on the derelict ship!");
                        int scrapGain = rand.nextInt(10) + 1;
                        status.setScrapMetal(status.getScrapMetal() + scrapGain);

                        System.out.println(status.printStatus());
                        logger.addLog("Event 7: Boarded derelict ship, gained " + scrapGain + " scrap metal");
                        checkCriticalStatus();

                    } else {
                        int damage = rand.nextInt(30) + 1;
                        System.out.println("The derelict ship was unstable and did " + damage + " damage to our ship ");

                        status.setIntegrity(status.getIntegrity() - damage);

                        System.out.println(status.printStatus());
                        logger.addLog("Event 7: Boarded derelict ship, took " + damage + " damage");
                        checkCriticalStatus();
                    }

                    return;
                }

                case 2 -> {
                    System.out.println("We ignored the derelict ship and continued on our way.");

                    System.out.println(status.printStatus());
                    logger.addLog("Event 7: Ignored derelict ship");
                    checkCriticalStatus();

                    return;
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