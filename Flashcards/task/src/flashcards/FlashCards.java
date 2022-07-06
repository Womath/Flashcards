package flashcards;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class FlashCards {
    private Map<String, String> map = new LinkedHashMap<>();
    private Map<String, Integer> statistics = new LinkedHashMap<>();
    private ArrayList<String> logList = new ArrayList<>();

    public void addToLog(String s) {
        logList.add(s);
    }

    public void addCard(String card, String definition) {
        map.put(card, definition);
        statistics.put(card, 0);
    }

    public void addCard(String card, String definition, int number) {
        map.put(card, definition);
        statistics.put(card, number);
    }

    public void removeCard(String card) {
        map.remove(card);
        statistics.remove(card);
    }

    public boolean searchCard(String card) {
        return map.containsKey(card);
    }

    public boolean searchDefinition(String definition) {
        return map.containsValue(definition);
    }

    public void replaceCard(String card, String definition) {
        map.replace(card, definition);
    }

    public void replaceCard(String card, int number) {
        statistics.replace(card, number);
    }

    public void importFile() {
        System.out.println("File name:");
        addToLog("File name:");
        Scanner scanner = new Scanner(System.in);
        String fileName = scanner.nextLine();
        addToLog("> " + fileName);
        File file = new File(fileName);
        int loadedCards = 0;
        try (Scanner fileScanner = new Scanner(file)) {
            while (fileScanner.hasNextLine()) {
                String card = fileScanner.nextLine();
                String definition = fileScanner.nextLine();
                if (fileScanner.hasNextInt()) {
                    int number = Integer.parseInt(fileScanner.nextLine());
                    if (searchCard(card)) {
                        replaceCard(card, definition);
                        replaceCard(card, number);
                    } else {
                        addCard(card, definition, number);
                    }
                } else {
                    if (searchCard(card)) {
                        replaceCard(card, definition);
                    } else {
                        addCard(card, definition);
                    }
                }
                loadedCards++;
            }
            System.out.println(loadedCards + " cards have been loaded.");
            addToLog(loadedCards + " cards have been loaded.");
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
            addToLog("File not found.");
        }
    }

    public void importFile(String fileName) {
        File file = new File(fileName);
        int loadedCards = 0;
        try (Scanner fileScanner = new Scanner(file)) {
            while (fileScanner.hasNextLine()) {
                String card = fileScanner.nextLine();
                String definition = fileScanner.nextLine();
                if (fileScanner.hasNextInt()) {
                    int number = Integer.parseInt(fileScanner.nextLine());
                    if (searchCard(card)) {
                        replaceCard(card, definition);
                        replaceCard(card, number);
                    } else {
                        addCard(card, definition, number);
                    }
                } else {
                    if (searchCard(card)) {
                        replaceCard(card, definition);
                    } else {
                        addCard(card, definition);
                    }
                }
                loadedCards++;
            }
            System.out.println(loadedCards + " cards have been loaded.");
            addToLog(loadedCards + " cards have been loaded.");
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
            addToLog("File not found.");
        }
    }

    public void exportFile() throws IOException {
        System.out.println("File name:");
        addToLog("File name:");
        Scanner scanner = new Scanner(System.in);
        String fileName = scanner.nextLine();
        addToLog("> " + fileName);
        FileWriter fileWriter = new FileWriter(fileName);
        int cardsSaved = 0;
        for (var export : map.entrySet()) {
            fileWriter.write(export.getKey());
            fileWriter.write("\n");
            fileWriter.write(export.getValue());
            fileWriter.write("\n");
            fileWriter.write(statistics.get(export.getKey()).toString());
            fileWriter.write("\n");
            cardsSaved++;
        }
        fileWriter.close();
        System.out.println(cardsSaved + " cards have been saved.");
        addToLog(cardsSaved + " cards have been saved.");
    }

    public void exportFile(String fileName) throws IOException {
        FileWriter fileWriter = new FileWriter(fileName);
        int cardsSaved = 0;
        for (var export : map.entrySet()) {
            fileWriter.write(export.getKey());
            fileWriter.write("\n");
            fileWriter.write(export.getValue());
            fileWriter.write("\n");
            fileWriter.write(statistics.get(export.getKey()).toString());
            fileWriter.write("\n");
            cardsSaved++;
        }
        fileWriter.close();
        System.out.println(cardsSaved + " cards have been saved.");
        addToLog(cardsSaved + " cards have been saved.");
    }

    public void ask(int number) {
        int counter = 0;
        for (var entry : map.entrySet()) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Print the definition of \"" + entry.getKey() + "\":");
            addToLog("Print the definition of \"" + entry.getKey() + "\":");
            String guess = scanner.nextLine();
            addToLog("> " + guess);

            if (map.containsValue(guess)) {
                if (guess.equalsIgnoreCase(entry.getValue())) {
                    System.out.println("Correct!");
                    addToLog("Correct!");
                } else {
                    for (var findKey : map.entrySet()) {
                        if (guess.equalsIgnoreCase(findKey.getValue())) {
                            System.out.println("Wrong. The right answer is \"" + entry.getValue() + "\", but your definition is correct for \"" + findKey.getKey() + "\".");
                            addToLog("Wrong. The right answer is \"" + entry.getValue() + "\", but your definition is correct for \"" + findKey.getKey() + "\".");
                            statistics.replace(entry.getKey(), (statistics.get(entry.getKey()) + 1));
                        }
                    }
                }
            } else {
                System.out.println("Wrong. The right answer is \"" + entry.getValue() + "\".");
                addToLog("Wrong. The right answer is \"" + entry.getValue() + "\".");
                statistics.replace(entry.getKey(), (statistics.get(entry.getKey()) + 1));
            }

            counter++;

            if (counter == number) {
                break;
            }
        }
    }

    public void exportLog() throws IOException {
        System.out.println("File name:");
        addToLog("File name:");
        Scanner scanner = new Scanner(System.in);
        String fileName = scanner.nextLine();
        addToLog("> " + fileName);
        addToLog("The log has been saved.");
        FileWriter fileWriter = new FileWriter(fileName);
        for (String s : logList) {
            fileWriter.write(s);
            fileWriter.write("\n");
        }
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        fileWriter.write(formatter.format(date).toString());
        fileWriter.close();
        System.out.println("The log has been saved.");

    }

    public void resetLog() {
        for (var entry : statistics.entrySet()) {
            statistics.replace(entry.getKey(), 0);
        }
        System.out.println("Card statistics have been reset.");
        addToLog("Card statistics have been reset.");
    }

    public void getHardestCard() {
        int hardest = 0;
        for (var entry : statistics.entrySet()) {
            hardest = Math.max(hardest, entry.getValue());
        }

        if (hardest == 0) {
            System.out.println("There are no cards with errors.");
            addToLog("There are no cards with errors.");
        } else {
            int counter = 0;
            for (var entry : statistics.entrySet()) {
                if (entry.getValue() == hardest) {
                    counter++;
                }
            }

            StringBuilder stringBuilder = new StringBuilder();
            if (counter == 1) {
                System.out.print("The hardest card is ");
                stringBuilder.append("The hardest card is ");
                for (var entry : statistics.entrySet()) {
                    if (entry.getValue() == hardest) {
                        System.out.print("\"" + entry.getKey() + "\".");
                        stringBuilder.append("\"").append(entry.getKey()).append("\".");
                    }
                }
                System.out.println(" You have " + hardest + " errors answering it.");
                stringBuilder.append(" You have ").append(hardest).append(" errors answering it.");
                addToLog(stringBuilder.toString());
            } else {
                System.out.print("The hardest cards are ");
                stringBuilder.append("The hardest cards are ");
                for (var entry : statistics.entrySet()) {
                    if (entry.getValue() == hardest) {
                        if (counter > 1) {
                            System.out.print("\"" + entry.getKey() + "\", ");
                            stringBuilder.append("\"").append(entry.getKey()).append("\", ");
                        } else {
                            System.out.print("\"" + entry.getKey() + "\".");
                            stringBuilder.append("\"").append(entry.getKey()).append("\".");
                        }
                        counter--;
                    }
                }
                System.out.println(" You have " + hardest + " errors answering them.");
                stringBuilder.append(" You have ").append(hardest).append(" errors answering them.");
                addToLog(stringBuilder.toString());
            }
        }
    }
}
