import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Hangman {
    private static final String WORD_FILE = "words.txt";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<String> wordList = loadWordBank(WORD_FILE);

        if (wordList.isEmpty()) {
            System.out.println("Error: Word bank is empty or " + WORD_FILE + " could not be found.");
            return;
        }

        boolean playAgain = true;
        System.out.println("=== Welcome to Java Terminal Hangman (Difficulty Edition) ===");

        while (playAgain) {
            playGameWithDifficulty(scanner, wordList);

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

    private static void playGameWithDifficulty(Scanner scanner, List<String> wordList) {
        int maxMistakes = 6;
        String difficultyName = "Medium";
        List<String> filteredWords = new ArrayList<>();

        while (true) {
            System.out.println("\nSelect Difficulty:");
            System.out.println("1. Easy (Up to 5 letters, 8 mistakes allowed)");
            System.out.println("2. Medium (6-7 letters, 6 mistakes allowed)");
            System.out.println("3. Hard (8+ letters, 4 mistakes allowed)");
            System.out.print("Enter choice (1-3): ");

            String choice = scanner.nextLine().trim();
            if (choice.equals("1")) {
                difficultyName = "Easy";
                maxMistakes = 8;
                for (String w : wordList) {
                    if (w.length() <= 5) {
                        filteredWords.add(w);
                    }
                }
                break;
            } else if (choice.equals("2")) {
                difficultyName = "Medium";
                maxMistakes = 6;
                for (String w : wordList) {
                    if (w.length() >= 6 && w.length() <= 7) {
                        filteredWords.add(w);
                    }
                }
                break;
            } else if (choice.equals("3")) {
                difficultyName = "Hard";
                maxMistakes = 4;
                for (String w : wordList) {
                    if (w.length() >= 8) {
                        filteredWords.add(w);
                    }
                }
                break;
            } else {
                System.out.println("Invalid choice. Please enter 1, 2, or 3.");
            }
        }

        if (filteredWords.isEmpty()) {
            filteredWords = wordList;
        }

        Random random = new Random();
        String secretWord = filteredWords.get(random.nextInt(filteredWords.size()));

        char[] currentBoard = new char[secretWord.length()];
        for (int i = 0; i < currentBoard.length; i++) {
            currentBoard[i] = '_';
        }

        List<Character> incorrectGuesses = new ArrayList<>();
        boolean hasWon = false;

        System.out.println("\n--- Starting " + difficultyName + " Round (Max Mistakes: " + maxMistakes + ") ---");

        while (incorrectGuesses.size() < maxMistakes && !hasWon) {
            System.out.println("\n-----------------------------------");
            System.out.print("Word: ");
            printBoard(currentBoard);
            System.out.println("Incorrect guesses (" + incorrectGuesses.size() + "/" + maxMistakes + "): " + incorrectGuesses);

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
            if (value == c) {
                return true;
            }
        }
        return false;
    }
}
