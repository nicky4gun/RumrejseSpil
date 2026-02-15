package services;

import models.Status;
import models.exceptions.CriticalStatusException;
import models.exceptions.InvalidTradeException;

import java.util.Random;

public class EventService {
    private final Status status;
    private final Logger logger;
    private final Random rand = new Random();

    public EventService(Status status, Logger logger) {
        this.status = status;
        this.logger = logger;
    }

    public void checkCriticalStatus() throws CriticalStatusException {
        if (status.getFuel() <= 0) {
            throw new CriticalStatusException("Ship ran out of fuel!");
        }

        if (status.getFuel() <= 10) {
            throw new CriticalStatusException("Low of Fuel!");
        }

        if (status.getIntegrity() <= 0) {
            throw new CriticalStatusException("Ship destroyed!");
        }

        if (status.getIntegrity() <= 20) {
            throw new CriticalStatusException("Ship almost destroyed");
        }

        if (status.getShield() < 0) {
            throw new CriticalStatusException("Shield offline!");
        }
    }

    public String eventStorm(int input) {
        switch (input) {
            case 1 -> {
                int damage = rand.nextInt(100) + 1;
                status.setIntegrity(status.getIntegrity() - damage);

                logger.addLog("Event 1: Storm chosen, damage: " + damage);
                return "Ow the storm did " + damage + " damage to the ship";
            }

            case 2 -> {
                int fuel = 20;
                status.setFuel(status.getFuel() - fuel);

                logger.addLog("Event 1: Storm avoided, fuel loss: " + fuel);
                return "Captain we got around the storm but we lost " + fuel + " to the ship";
            }

            default -> {return "Not an option! Try again";}
        }
    }

    public String tradeScrapForFuel(int scrap) {
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

        logger.addLog("Event 2: Traded " + scrap + " scrap for " + fuelAmount + " fuel");
        return "Traded " + scrap + " scrap for " + fuelAmount + " fuel";
    }

    public String buyShield() {
        int amount = 5;

        if (status.getScrapMetal() < amount) {
            throw new InvalidTradeException("You do not have enough scrap metal to trade");
        }

        status.setScrapMetal(status.getScrapMetal() - amount);
        status.setShield(status.getShield() + 1);

        logger.addLog("Event 2: Shield upgrade to level 1");
        return "Shield upgraded to level 1 for " + amount + " scrap metal";
    }

    public String declineTrade() {
        logger.addLog("Event 2: Trade declined");
        return "You declined the trade";
    }

    public String useRepairKit() {
        if (status.getRepairKitUsed()) {
            logger.addLog("Event 3: Mortor could not be repaired because repair kit was already used");
            return null;
        }

        if (status.getIntegrity() < 100) {
            int newIntegrity = status.getIntegrity() + 20;
            status.setIntegrity(newIntegrity);
            status.setRepairKitUsed(true);

            logger.addLog("Event 3: Motor is now working because we used the repair kit");
            return "Repair kit used, integrity + 20";

        } else {
            logger.addLog("Event 3: Repair kit had no effect because motor is already at full integrity");
            return "The motor is already at full integrity, the repair kit had no effect";
        }
    }

    public String attemptRestart() throws CriticalStatusException {
        int chance = rand.nextInt(100);

        if (chance < 40) {
            logger.addLog("Event 3: Mortor is now working because we turned it on and off");
            checkCriticalStatus();
            return "It worked the motor has started working again";

        } else if (chance < 70) {
            int motorDamage = 10;
            status.setIntegrity(status.getIntegrity() - motorDamage);

            logger.addLog("Event 3: Motor is now working, but it took some damage");
            return "It took some time but it did work, integrity -10";

        } else {
            logger.addLog("Event 3: Motor failure resulted in critical status");
            throw new CriticalStatusException("Critical failure! The motor is not working and we are lost in space");
        }


    }

    public String eventAlien(int input) {
        switch (input) {
            case 1 -> {
                int damage = rand.nextInt(50) + 1;
                status.setIntegrity(status.getIntegrity() - damage);

                logger.addLog("Event 4: Attacked alien ship, damage taken: " + damage);
                return "Ow the alien ship did " + damage + " damage to the ship but at least we got rid of them";
            }

            case 2 -> {
                if (rand.nextInt(100) < 50) {
                    int scrapGain = rand.nextInt(5) + 1;
                    status.setScrapMetal(status.getScrapMetal() + scrapGain);

                    logger.addLog("Event 4: Communicated with aliens, gained " + scrapGain + " scrap metal");
                    return "The aliens were friendly and gave us some scrap metal";

                } else {
                    int damage = rand.nextInt(30) + 1;
                    status.setIntegrity(status.getIntegrity() - damage);

                    logger.addLog("Event 4: Communicated with aliens, took " + damage + " damage");
                    return "The aliens were hostile and did " + damage + " damage to the ship ";
                }
            }

            default -> {return "Not an option! Try again";}
        }
    }

    public String eventAsteroid(int input) {
        switch (input) {
            case 1 -> {
                int damage = rand.nextInt(40) + 1;
                status.setIntegrity(status.getIntegrity() - damage);

                logger.addLog("Event 5: Navigated asteroid field, damage taken: " + damage);
                return "Ow the asteroids did " + damage + " damage to the ship but at least we got through them";
            }

            case 2 -> {
                int fuelLoss = 15;
                status.setFuel(status.getFuel() - fuelLoss);

                logger.addLog("Event 5: Avoided asteroid field, fuel loss: " + fuelLoss);
                return "We went around the asteroid field, but lost " + fuelLoss + " fuel in the process";
            }

            default -> {return "Not an option! Try again";}
        }
    }

    public String eventPirate(int input) {
        switch (input) {
            case 1 -> {
                int damage = rand.nextInt(60) + 1;
                status.setIntegrity(status.getIntegrity() - damage);

                logger.addLog("Event 6: Fought space pirates, damage taken: " + damage);
                return "Ow the space pirates did " + damage + " damage to the ship but at least we got rid of them";
            }

            case 2 -> {
                if (rand.nextInt(100) < 50) {
                    logger.addLog("Event 6: Successfully escaped from space pirates");
                    return "We successfully escaped from the space pirates!";

                } else {
                    int damage = rand.nextInt(40) + 1;
                    status.setIntegrity(status.getIntegrity() - damage);

                    logger.addLog("Event 6: Failed to escape from space pirates, took " + damage + " damage");
                    return "We failed to escape and the space pirates did " + damage + " damage to the ship ";
                }
            }

            default -> {return "Not an option! Try again";}
        }
    }

    public String eventDerelict(int input) throws CriticalStatusException {
        switch (input) {
            case 1 -> {
                if (rand.nextInt(100) < 50) {
                    int scrapGain = rand.nextInt(10) + 1;
                    status.setScrapMetal(status.getScrapMetal() + scrapGain);

                    logger.addLog("Event 7: Boarded derelict ship, gained " + scrapGain + " scrap metal");
                    return "We found some useful scrap metal on the derelict ship!";

                } else {
                    int damage = rand.nextInt(30) + 1;
                    status.setIntegrity(status.getIntegrity() - damage);

                    logger.addLog("Event 7: Boarded derelict ship, took " + damage + " damage");
                    return "The derelict ship was unstable and did " + damage + " damage to our ship ";
                }
            }

            case 2 -> {
                logger.addLog("Event 7: Ignored derelict ship");
                checkCriticalStatus();

                return "We ignored the derelict ship and continued on our way.";
            }

            default -> {return "Not an option! Try again";}
        }
    }
}