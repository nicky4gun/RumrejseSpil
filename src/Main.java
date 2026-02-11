import Exceptions.CriticalStatusException;
import Exceptions.InvalidTradeException;

import java.util.Scanner;


public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Logger logger = new Logger("logs.txt");
        Status status = new Status(100, 100, 10, 0, false, "Fully operational");
        Controller controller = new Controller(status, logger);

        runGame(sc, logger, controller);
    }

    public static void runGame(Scanner sc, Logger logger, Controller controller) {
        boolean running = true;

        while (running) {
            try {
                printIntroBanner();
                String name = getNameFromUser(sc);
                String ship = getShipNameFromUser(sc);

                logger.addLog("Start: " + name + " arrived on " + ship);

                controller.showStatus();
                controller.showCase(sc);

            } catch (IllegalArgumentException e) {
                System.out.println("It can not be null ");
            } catch (CriticalStatusException e) {
                System.out.println("Critical: " + e.getMessage());
            } catch (InvalidTradeException e) {
                System.out.println(e.getMessage());
            }

            logger.printLog();
            running = false;
        }
    }

    private static void printIntroBanner() {
        System.out.println("""
                ==============================
                Space Exception Rescue Mission
                ==============================""");
    }

    private static String getNameFromUser(Scanner sc) {
        boolean running = true;
        String name = "";

        while (running) {
            System.out.print("Hello Captain looks at your name sign: ");
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

        // System.out.println("ah i see your name is " + name + " since you are the captain you need to name our ship:");

        while (running) {
            System.out.print("Enter name for your ship: ");
            ship = sc.nextLine().trim();

            if (ship.isEmpty()) {
                System.out.println("You have to give the ship a name captain");
                continue;
            }

            running = false;
        }

        System.out.println("Your ship is named: " + ship);
        // System.out.println("good name captain " + name + " the name " + ship + " is a good name; ");

        return ship;
    }
}