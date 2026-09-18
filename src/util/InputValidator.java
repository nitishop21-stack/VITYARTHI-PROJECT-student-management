package util;

import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Utility class for robust command-line user input parsing and validation.
 * Prevents Scanner crashes, infinite loops on bad input, and buffer issues.
 */
public class InputValidator {
    private static final Scanner scanner = new Scanner(System.in);
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    /**
     * Reads a non-empty string from the console.
     */
    public static String readString(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            if (input != null && !input.trim().isEmpty()) {
                return input.trim();
            }
            System.out.println("  [!] Input cannot be empty. Please enter a valid value.");
        }
    }

    /**
     * Reads an integer within a specified range [min, max].
     */
    public static int readInt(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine();
            try {
                int value = Integer.parseInt(line.trim());
                if (value >= min && value <= max) {
                    return value;
                }
                System.out.printf("  [!] Value must be between %d and %d. Please try again.\n", min, max);
            } catch (NumberFormatException e) {
                System.out.println("  [!] Invalid number format. Please enter an integer.");
            }
        }
    }

    /**
     * Reads a double within a specified range [min, max].
     */
    public static double readDouble(String prompt, double min, double max) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine();
            try {
                double value = Double.parseDouble(line.trim());
                if (value >= min && value <= max) {
                    return value;
                }
                System.out.printf("  [!] Value must be between %.1f and %.1f. Please try again.\n", min, max);
            } catch (NumberFormatException e) {
                System.out.println("  [!] Invalid number format. Please enter a valid decimal number.");
            }
        }
    }

    /**
     * Reads and validates an email address.
     */
    public static String readEmail(String prompt) {
        while (true) {
            String email = readString(prompt);
            if (EMAIL_PATTERN.matcher(email).matches()) {
                return email;
            }
            System.out.println("  [!] Invalid email format (example: student@university.edu). Please try again.");
        }
    }

    /**
     * Reads and validates a contact phone number (7 to 15 digits).
     */
    public static String readPhoneNumber(String prompt) {
        while (true) {
            String phone = readString(prompt);
            String digitsOnly = phone.replaceAll("[^0-9]", "");
            if (digitsOnly.length() >= 7 && digitsOnly.length() <= 15) {
                return phone;
            }
            System.out.println("  [!] Contact number must contain between 7 and 15 digits. Please try again.");
        }
    }

    /**
     * Reads a Yes/No confirmation from the user.
     */
    public static boolean readConfirmation(String prompt) {
        while (true) {
            System.out.print(prompt + " (y/n): ");
            String line = scanner.nextLine();
            if (line != null) {
                String trimmed = line.trim().toLowerCase();
                if (trimmed.equals("y") || trimmed.equals("yes")) {
                    return true;
                }
                if (trimmed.equals("n") || trimmed.equals("no")) {
                    return false;
                }
            }
            System.out.println("  [!] Please enter 'y' for Yes or 'n' for No.");
        }
    }
}
