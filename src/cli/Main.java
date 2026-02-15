package cli;

import controller.Controller;
import models.exceptions.CriticalStatusException;
import models.Status;
import services.EventService;
import services.Logger;


import java.util.Scanner;


public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Logger logger = new Logger("logs.txt");
        Status status = new Status(100, 100, 10, 0, false);
        EventService eventService = new EventService(status, logger);
        Controller controller = new Controller(status, eventService, logger);

        runGame(sc, logger, controller);
        sc.close();
    }

    public static void runGame(Scanner sc, Logger logger, Controller controller) {
        try {
            printGameTitle();
            String name = getNameFromUser(sc);
            String ship = getShipNameFromUser(sc);

            logger.addLog("Start: " + name + " arrived on " + ship);

            controller.showStatus();
            controller.startGame(sc);

            printGameSucces();
            logger.addLog("End: Mission Accomplished for " + name + " on " + ship);

        } catch (CriticalStatusException e) {
            System.out.println("GAME OVER: " + e.getMessage());
            System.out.println("Better luck next time Captain!");
            logger.addLog("End: Mission Failed - " + e.getMessage());

        } finally {
            logger.printLog();
        }
    }

    private static void printGameTitle() {
        System.out.println("""
                ==============================
                Space Exception Rescue Mission
                ==============================""");
    }

    private static void printGameSucces() {
        System.out.println("""
                Mission Accomplished!
                You have survived your mission""");
    }

    private static String getNameFromUser(Scanner sc) {
        boolean running = true;
        String name = "";

        while (running) {
            System.out.print("Hello Captain!\n");
            System.out.print("What is your name?: ");

            name = sc.nextLine().trim();

            if (name.isEmpty()) {
                System.out.println("That is not a name, Please give one!");
                continue;
            }

            running = false;
        }

        System.out.println("The name you picked was " + name);

        return name;
    }

    private static String getShipNameFromUser(Scanner sc) {
        boolean running = true;
        String ship = "";

        while (running) {
            System.out.print("Enter your ships name: ");
            ship = sc.nextLine().trim();

            if (ship.isEmpty()) {
                System.out.println("You have to give the ship a name captain");
                continue;
            }

            running = false;
        }

        System.out.println("Your ship is named: " + ship);
        return ship;
    }
}