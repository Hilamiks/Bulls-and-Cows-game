package bullscows;

import java.util.Scanner;

public class Main {

    private static String targetNumber;

    private static String userNumber = "";

    static Scanner scanner = new Scanner(System.in);

    private static int turnCounter = 0;

    public static void main(String[] args) {
        setTargetNumber();
        while(!userNumber.equals(targetNumber)){
            takeGuess();
            checkGuess();
        }
        System.out.println("Congratulations! You guessed the secret code.");
    }

    private static void setTargetNumber() {
        System.out.println("Please, enter the secret codes' length: ");
        int secretSize = scanner.nextInt();
        scanner.nextLine();
        if (secretSize > 10){
            System.out.println("Error: can't generate a secret number with a length of "+secretSize);
        } else {
            targetNumber = rng(secretSize);
            System.out.println("Okay, let's start a game!");
        }
    }

    private static String rng(int size) {
        StringBuilder pseudoRandomString = new StringBuilder();
        String firstDigit = ""+randomDigit();
        while(firstDigit.equals("0")) {
            firstDigit = ""+randomDigit();
        }
        pseudoRandomString.append(firstDigit);
        while(pseudoRandomString.length() < size) {
            String digit = ""+randomDigit();
            if (pseudoRandomString.indexOf(digit) == -1){
                pseudoRandomString.append(digit);
            }
        }
        System.out.println(pseudoRandomString.toString());
        return pseudoRandomString.toString();
    }

    private static StringBuilder randomDigit() {
        StringBuilder c = new StringBuilder();
        return c.append((int)(Math.random()*10));
    }

    private static void takeGuess() {
        turnCounter++;
        System.out.println("Turn "+turnCounter+". Answer: ");
        userNumber = scanner.nextLine();
    }

    private static void checkGuess() {
        char[] c = targetNumber.toCharArray();
        char[] d = userNumber.toCharArray();
        int bulls = checkBulls(c, d);
        int cows = checkCows(c, d);
        String grade = "Grade: ";
        if (bulls != 0) {
            grade = grade.concat(bulls + " bull(s)");
        }
        if (bulls != 0 && cows != 0) {
            grade = grade.concat(" and ");
        }
        if (cows != 0) {
            grade = grade.concat(cows + " cow(s)");
        }
        if (bulls == 0 && cows == 0) {
            grade = grade.concat("None");
        }
        grade = grade.concat(".");
        System.out.println(grade);
    }

    private static int checkCows(char[] c, char[] d) {
        int cows = 0;
        for (int i = 0; i < targetNumber.length(); i++) {
            for (int j = 0; j < userNumber.length(); j++) {
                if (c[i] == d[j] && i != j) {
                    cows++;
                    d[j]='x';
                }
            }
        }
        return cows;
    }

    private static int checkBulls(char[] c, char[] d) {
        int bulls = 0;
        for (int i = 0; i < targetNumber.length(); i++) {
            if (c[i] == d[i]) {
                bulls++;
                d[i] = 'x';
            }
        }
        return bulls;
    }


}
