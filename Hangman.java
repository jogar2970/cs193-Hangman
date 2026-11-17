import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Hangman {
    private static final String WORD_FILE = "words.txt";
    private static final int MAX_MISTAKES = 6;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<String> wordList = loadWordBank(WORD_FILE);

        if (wordList.isEmpty()) {
            System.out.println("Error: Word bank is empty or " + WORD_FILE + " could not be found.");
            return;
        }

        boolean playAgain = true;
        System.out.println("=== Welcome to Java Terminal Hangman! ===");

        while (playAgain) {
            playGame(scanner, wordList);
            
            System.out.print("\nWould you like to play another round? (y/n): ");
            String choice = scanner.nextLine().trim().toLowerCase();
            playAgain = choice.startsWith("y");
        }

        System.out.println("Thanks for playing Hangman!");
        scanner.close();
    }

    private static List<String> loadWordBank(String filename) {
        List<String> words = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim().toLowerCase();
                if (!line.isEmpty()) {
                    words.add(line);
                }
            }
        } catch (IOException e) {
            words.add("programming");
        }
        return words;
    }

    private static void playGame(Scanner scanner, List<String> wordList) {
        Random random = new Random();
        String secretWord = wordList.get(random.nextInt(wordList.size()));
        
        char[] currentBoard = new char[secretWord.length()];
        for (int i = 0; i < currentBoard.length; i++) {
            currentBoard[i] = '_';
        }

        List<Character> incorrectGuesses = new ArrayList<>();
        boolean hasWon = false;

        while (incorrectGuesses.size() < MAX_MISTAKES && !hasWon) {
            System.out.println("\n-----------------------------------");
            System.out.print("Word: ");
            printBoard(currentBoard);
            System.out.println("Incorrect guesses (" + incorrectGuesses.size() + "/" + MAX_MISTAKES + "): " + incorrectGuesses);
            
            System.out.print("Guess a letter: ");
            String input = scanner.nextLine().trim().toLowerCase();

            if (input.length() != 1 || !Character.isLetter(input.charAt(0))) {
                System.out.println("Please enter a single valid alphabetical letter.");
                continue;
            }

            char guess = input.charAt(0);

            if (containsChar(currentBoard, guess) || incorrectGuesses.contains(guess)) {
                System.out.println("You already guessed '" + guess + "'. Try a different letter.");
                continue;
            }

            if (secretWord.indexOf(guess) >= 0) {
                System.out.println("Good job! '" + guess + "' is in the word.");
                for (int i = 0; i < secretWord.length(); i++) {
                    if (secretWord.charAt(i) == guess) {
                        currentBoard[i] = guess;
                    }
                }
            } else {
                System.out.println("Incorrect! '" + guess + "' is not in the word.");
                incorrectGuesses.add(guess);
            }

            if (!String.valueOf(currentBoard).contains("_")) {
                hasWon = true;
            }
        }

        System.out.println("\n===================================");
        if (hasWon) {
            System.out.println("Congratulations! You guessed the word: " + secretWord);
        } else {
            System.out.println("Game Over! You ran out of guesses.");
            System.out.println("The secret word was: " + secretWord);
        }
        System.out.println("===================================");
    }

    private static void printBoard(char[] board) {
        for (char c : board) {
            System.out.print(c + " ");
        }
        System.out.println();
    }

    private static boolean containsChar(char[] array, char c) {
        for (char value : array) {
            if (value == c) return true;
        }
        return false;
    }
}
