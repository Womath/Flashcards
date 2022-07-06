package flashcards;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        FlashCards deck = new FlashCards();

        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-import") && (i % 2 == 0)) {
                deck.importFile(args[i + 1]);
            }
        }

        while (true) {
            System.out.println("Input the action (add, remove, import, export, ask, exit, log, hardest card, reset stats):");
            deck.addToLog("Input the action (add, remove, import, export, ask, exit, log, hardest card, reset stats):");
            String input = scanner.nextLine();
            deck.addToLog("> " + input);
            switch (input.toLowerCase()) {
                case "add" -> {
                    System.out.println("The card:");
                    deck.addToLog("The card:");
                    String card = scanner.nextLine();
                    deck.addToLog("> " + card);
                    if (deck.searchCard(card)) {
                        System.out.println("The card \"" + card + "\" already exists.");
                        deck.addToLog("The card \"" + card + "\" already exists.");
                        break;
                    }
                    System.out.println("The definition of the card:");
                    deck.addToLog("The definition of the card:");
                    String definition = scanner.nextLine();
                    deck.addToLog("> " + definition);
                    if (deck.searchDefinition(definition)) {
                        System.out.println("The definition \"" + definition + "\" already exists.");
                        deck.addToLog("The definition \"" + definition + "\" already exists.");
                        break;
                    }
                    deck.addCard(card, definition);
                    System.out.println("The pair (\"" + card + "\":\"" + definition + "\") has been added.");
                    deck.addToLog("The pair (\"" + card + "\":\"" + definition + "\") has been added.");
                }
                case "remove" -> {
                    System.out.println("Which card?");
                    deck.addToLog("Which card?");
                    String removableCard = scanner.nextLine();
                    deck.addToLog("> " + removableCard);
                    if (deck.searchCard(removableCard)) {
                        deck.removeCard(removableCard);
                        System.out.println("The card has been removed.");
                        deck.addToLog("The card has been removed.");
                    } else {
                        System.out.println("Can't remove \"" + removableCard + "\": there is no such card.");
                        deck.addToLog("Can't remove \"" + removableCard + "\": there is no such card.");
                    }
                }
                case "import" -> deck.importFile();
                case "export" -> deck.exportFile();
                case "ask" -> {
                    System.out.println("How many times to ask?");
                    deck.addToLog("How many times to ask?");
                    int number = Integer.parseInt(scanner.nextLine());
                    deck.addToLog("> " + number);
                    deck.ask(number);
                }
                case "exit" -> {
                    System.out.println("Bye bye!");
                    for (int i = 0; i < args.length; i++) {
                        if (args[i].equals("-export") && (i % 2 == 0)) {
                            deck.exportFile(args[i + 1]);
                        }
                    }
                    System.exit(0);
                }
                case "log" -> deck.exportLog();
                case "hardest card" -> deck.getHardestCard();
                case "reset stats" -> deck.resetLog();
                default -> {
                    System.out.println("Wrong input!");
                    deck.addToLog("Wrong input!");
                }
            }
        }
    }
}
