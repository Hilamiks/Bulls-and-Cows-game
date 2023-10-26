package bullscows;

import java.util.Scanner;

public class Main {

    private static String targetNumber;

    private static String userNumber = "";

    static Scanner scanner = new Scanner(System.in);

    private static int turnCounter = 0;

    private static int range = 0;

    public static void main(String[] args) {
        setTargetNumber();
        while(!userNumber.equals(targetNumber)){
            takeGuess();
            checkGuess();
        }
        System.out.println("Congratulations! You guessed the secret code.");
    }

    private static void setTargetNumber() {
        System.out.println("Input the length of the secret code: ");
        int secretSize = scanner.nextInt();
        scanner.nextLine();
        if (secretSize > 36){
            System.out.println("Error: can't generate a secret number with a length of "+secretSize);
            targetNumber = userNumber;
        } else {
            System.out.println("Input the number of possible symbols in the code: ");
            range = scanner.nextInt();
            scanner.nextLine();
            if (range < secretSize) {
                System.out.println("Error: range too small for the size of the secret code");
                targetNumber = userNumber;
            } else {
                targetNumber = rng(secretSize, range);
                StringBuilder asteriskCode = new StringBuilder(secretSize);
                for (int i = 0; i < secretSize; i++) {
                    asteriskCode.append('*');
                }
                StringBuilder rangeDisplay = new StringBuilder();
                rangeDisplay.append(" (0-");
                if (range < 10) {
                    rangeDisplay.append(range).append(").");
                } else {
                    rangeDisplay.append(9).append(", a-")
                            .append((char)(range+86)).append(").");

                }
                System.out.println("The secret is prepared: "+asteriskCode+rangeDisplay);
                System.out.println("Okay, let's start a game!");
            }
        }
    }

    private static String rng(int size, int range) {
        StringBuilder pseudoRandomString = new StringBuilder();
        while(pseudoRandomString.length() < size) {
            String digit = ""+randomSymbol();
            if (pseudoRandomString.indexOf(digit) == -1){
                pseudoRandomString.append(digit);
            }
        }
        System.out.println(pseudoRandomString.toString());
        return pseudoRandomString.toString();
    }

    private static StringBuilder randomSymbol() {
        StringBuilder c = new StringBuilder();
        int rand = (int)(Math.random()*(range));
        if (rand<10) {
            return c.append(rand);
        } else {
            char d = (char)(rand+87);
            return c.append(d);
        }
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
                    d[j]='_';
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
                d[i] = '_';
            }
        }
        return bulls;
    }


}
