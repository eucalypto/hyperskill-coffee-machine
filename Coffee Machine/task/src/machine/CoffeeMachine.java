package machine;

import java.util.Arrays;
import java.util.Scanner;

public class CoffeeMachine {

    STATE state = STATE.OFF;
    int storedWater = 400;
    int storedMilk = 540;
    int storedCoffee = 120;
    int storedCups = 9;
    int money = 550;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        CoffeeMachine coffeeMachine = new CoffeeMachine();
        coffeeMachine.turnOn();

        while (coffeeMachine.state != STATE.OFF) {
            coffeeMachine.receiveInput(scanner.next());
        }
    }

    public void receiveInput(String input) {
        switch (state) {
            case AWAITING_ACTION:
                processAction(input);
                break;
            case AWAITING_BUYING_CHOICE:
                processBuyingChoice(input);
                break;
            case FILLING_WATER:
                fillWater(input);
                break;
            case FILLING_MILK:
                fillMilk(input);
                break;
            case FILLING_COFFEE:
                fillCoffee(input);
                break;
            case FILLING_CUPS:
                fillCups(input);
                break;
        }
    }

    private void processAction(String choice) {
        switch (choice) {
            case "buy":
                goToAwaitingBuyingChoice();
                break;
            case "fill":
                goToFillingWater();
                break;
            case "take":
                take();
                goToAwaitingAction();
                break;
            case "exit":
                turnOff();
                break;
            case "remaining":
                printStatus();
                goToAwaitingAction();
                break;
        }
    }

    private void fillCups(String cupsAmount) {
        storedCups += Integer.parseInt(cupsAmount);
        goToAwaitingAction();
    }

    private void fillCoffee(String coffeeAmount) {
        storedCoffee += Integer.parseInt(coffeeAmount);
        goToFillingCups();
    }

    private void fillWater(String waterAmount) {
        storedWater += Integer.parseInt(waterAmount);
        goToFillingMilk();
    }

    private void fillMilk(String milkAmount) {
        storedMilk += Integer.parseInt(milkAmount);
        goToFillingCoffee();
    }


    public void turnOn() {
        goToAwaitingAction();
    }

    public void turnOff() {
        state = STATE.OFF;
    }


    void goToAwaitingAction() {
        System.out.println("Write action (buy, fill, take, remaining, exit):");
        state = STATE.AWAITING_ACTION;
    }

    void goToAwaitingBuyingChoice() {
        System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:");
        state = STATE.AWAITING_BUYING_CHOICE;
    }

    void goToFillingWater() {
        System.out.println("Write how many ml of water do you want to add:");
        state = STATE.FILLING_WATER;
    }

    void goToFillingMilk() {
        System.out.println("Write how many ml of milk do you want to add:");
        state = STATE.FILLING_MILK;
    }

    void goToFillingCoffee() {
        System.out.println("Write how many grams of coffee beans do you want to add:");
        state = STATE.FILLING_COFFEE;
    }

    void goToFillingCups() {
        System.out.println("Write how many disposable cups of coffee do you want to add:");
        state = STATE.FILLING_CUPS;
    }

    private void printStatus() {
        System.out.println("The coffee machine has:");
        System.out.println(storedWater + " of water");
        System.out.println(storedMilk + " of milk");
        System.out.println(storedCoffee + " of coffee beans");
        System.out.println(storedCups + " of disposable cups");
        System.out.println("$" + money + " of money");
        System.out.println();
    }

    private void take() {
        System.out.println("I gave you $" + money);
        System.out.println();
        money = 0;
    }

    private void processBuyingChoice(String choice) {
        switch (choice) {
            case "1":
                buy(250, 0, 16, 4);
                break;
            case "2":
                buy(350, 75, 20, 7);
                break;
            case "3":
                buy(200, 100, 12, 6);
                break;
            case "back":
                break;
        }

        goToAwaitingAction();
    }

    private void buy(int water, int milk, int coffee, int cost) {
        if (!enoughResourcesForACup(water, milk, coffee)) {
            return;
        }
        System.out.println("I have enough resources, making you a coffee!");
        storedWater -= water;
        storedMilk -= milk;
        storedCoffee -= coffee;
        storedCups--;
        money += cost;
    }

    private boolean enoughResourcesForACup(int water, int milk, int coffee) {
        if (water > storedWater) {
            System.out.println("Sorry, not enough water!");
            return false;
        }

        if (milk > storedMilk) {
            System.out.println("Sorry, not enough milk!");
            return false;
        }

        if (coffee > storedCoffee) {
            System.out.println("Sorry, not enough coffee!");
            return false;
        }

        if (storedCups < 1) {
            System.out.println("Sorry, not enough cups!");
            return false;
        }

        return true;
    }

    private void zbacon(Scanner scanner) {
        System.out.println("Write how many ml of water the coffee machine has: ");
        int storedWater = scanner.nextInt();

        System.out.println("Write how many ml of milk the coffee machine has: ");
        int storedMilk = scanner.nextInt();

        System.out.println("Write how many grams of coffee beans the coffee machine has: ");
        int storedCoffee = scanner.nextInt();


        System.out.println("Write how many cups of coffee you will need:");
        int neededCups = scanner.nextInt();

        int necessaryWater = 200 * neededCups;
        int necessaryMilk = 50 * neededCups;
        int necessaryCoffee = 15 * neededCups;

        int waterLimit = storedWater / 200;
        int milkLimit = storedMilk / 50;
        int coffeeLimit = storedCoffee / 15;

        int availableCups = Arrays.stream(new int[]{waterLimit, milkLimit, coffeeLimit}).min().getAsInt();

        if (availableCups > neededCups) {
            System.out.println("Yes, I can make that amount of coffee (and even " + (availableCups - neededCups) + " more than that)");
        } else if (availableCups == neededCups) {
            System.out.println("Yes, I can make that amount of coffee");
        } else {
            System.out.println("No, I can make only " + availableCups + " cup(s) of coffee\n");
        }


        // String output = String.join("\n",
        //         "For " + cups + " cups of coffee you will need:",
        //         necessary_water + " ml of water",
        //         necessary_milk + " ml of milk",
        //         necessary_coffee + " g of coffee beans"
        // );
        // System.out.println(output);


        // System.out.println("Starting to make a coffee");
        // System.out.println("Grinding coffee beans");
        // System.out.println("Boiling water");
        // System.out.println("Mixing boiled water with crushed coffee beans");
        // System.out.println("Pouring coffee into the cup");
        // System.out.println("Pouring some milk into the cup");
        // System.out.println("Coffee is ready!");
    }

    enum STATE {
        OFF, AWAITING_ACTION, AWAITING_BUYING_CHOICE, FILLING_WATER, FILLING_MILK, FILLING_COFFEE, FILLING_CUPS;
    }
}
