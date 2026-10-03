package library.services;
import java.util.Scanner;

public class InputHelper {

    public final static String NUMBERS = "0123456789";

    static Scanner scanner = new Scanner(System.in);

    public static String getInput(String prompt) {
        System.out.println(prompt);
        return scanner.nextLine();
    }

    public static int getIntInput(String prompt) {
        String input;
        while (true) {
            input = getInput(prompt);
            if ("".equals(input)) {
                continue;
            }

            if (checkIfInteger(input)) {
                break;
            }
            System.out.println("Not an Integer");
        }
        return Integer.parseInt(input);
    }

    public static boolean checkIfInteger(String string) {
        for (int i = 0; i < string.length(); i ++) {
            if (!Character.isDigit(string.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static void printLines(String[] lines) {
        for (String line: lines) {
            System.out.println(line);
        }
    }


}
