# cs193-Hangman
# Java Terminal Hangman

A lightweight, command-line Hangman game built in Java.

## The Problem
Many people are looking for a quick, distraction-free way to pass the time during short breaks. Complex modern games and applications often require heavy setups that many people may not have access to. This project provides a fun, retro word-puzzle experience directly in the terminal.

## How It Works
The program randomly selects a secret word from an internal dictionary file or word bank. It presents the user with hidden blanks representing each letter. The user guesses letters one by one through the command line, tracking correct matches and remaining incorrect guesses until they either win or lose.

## Tech Stack
- **Language:** Java (JDK 17+)
- **Libraries/Built-in Classes:** 
  - `java.util.Scanner` (for reading user input)
  - `java.util.Random` (for random word selection)
  - `java.io.BufferedReader` & `FileReader` (for loading word lists)

## Setup
To get the project up and running locally, follow these steps:

```bash
git clone [https://github.com/your-username/java-hangman.git](https://github.com/your-username/java-hangman.git)
cd java-hangman
javac Hangman.java
