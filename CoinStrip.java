/*
 * Name: Geoffrey Jing
 * Email: jingg@carleton.edu
 * Description: Lab 1 Silver Dollar Game: Implements a silver dollar game 
 */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;

public class CoinStrip implements TwoPlayerGame {

    /* A private instance variable for keeping track of coins here.
     * The lab writeup suggests using an array of integers, where each element
     * indicates the position of a coin: 
     */
    private int[] coins;
    // private field for indicating length of coin strip
    private int lenStrip;

    /**
     * Randomly choose a number of coins:
     * 50% chance of 3, 25% chance of 4, 12.5% chance of 5, etc.
     * Hint: StdRandom.bernoulli() returns true 50% of the time
     * 
     * Randomly position the coins.
     */
    public CoinStrip() {
        // use StdRandom to randomly determine the number of coins
        // then intialize coins to hold that many coin positions:
        // coins = new int[number_of_coins]
        boolean coin = StdRandom.bernoulli();
        int count = 3;
        while (!coin) {
            coin = StdRandom.bernoulli();
            count++;
        }
        coins = new int[count];

        // initiating variable for length of coin strip
        lenStrip = 100;

        // then randomly position the coins 
        // (StdRandom.uniform(1, x) will give you a random integer between 1 and x)
        coins[0] = (StdRandom.uniform(0, lenStrip - (coins.length - 1))); 
        for (int i = 1; i < coins.length; i++) {
            coins[i] = (StdRandom.uniform((coins[i - 1] + 1), lenStrip - (coins.length - (i + 1))));
        }
        // code below checks to ensure the game is not over when the coins are first 
        // randomly assigned to different locations
        boolean test = false;
        for (int i = 1; i < coins.length; i++) {
            if (i != coins[i]) {
                test = true;
            }
        }
        while (!test) {
            int lastIndex = coins.length - 1;
            coins[lastIndex] = (StdRandom.uniform(
                (coins[lastIndex] + 1), lenStrip - (coins.length - (lastIndex))));
            for (int i = 1; i < coins.length; i++) {
                if (i != coins[i]) {
                    test = true;
                }
            }
        }
    }

    /**
     * Returns the number of coins on the CoinStrip game board.
     * @return the number of coints on the CoinStrip game board.
     */
    public int getNumCoins() {
        /* your code here */
        return coins.length;
    }

    /**
     * Returns the 0-indexed location of a specific coin. Coins are
     * also zero-indexed. So, if the CoinStrip had 3 coins, the coins
     * would be indexed 0, 1, or 2.
     * @param coinNum the 0-indexed coin number
     * @return the 0-indexed location of the specified coin on the CoinStrip
     */
    public int getCoinPosition(int coinNum) {
        /* your code here */
        return coins[coinNum];
    }

    /**
     * Checks whether the specified move follows the rules
     * @param resource which coin is moving (0-indexed)
     * @param units    the number of spaces to move the coin
     * @return true if the move follows the rules, false otherwise
     */
    @Override
    public boolean isValidMove(int resource, int units) {
        // Remember that Java uses && for boolean AND, and || for boolean OR
        /* your code here */
        if (units <= 0 || resource >= coins.length) {
            return false;
        }
        if (resource > 0) {
            if ((coins[resource] - units) <= coins[resource - 1]) {
                return false;
            }
        } else {
            if (coins[resource] - units < 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Makes the specified move
     * Assumes the move is valid
     * @param resource which coin is moving (0-indexed)
     * @param units    the number of spaces to move the coin
     */
    @Override
    public void makeMove(int resource, int units) {
        /* your code here */
        coins[resource] -= units;
    }

    /**
     * @return true if the game is over (coins are all the way to the left)
     */
    @Override
    public boolean isGameOver() {
        /* your code here */
        for (int i = 0; i < coins.length; i++) {
            if (i != coins[i]) {
                return false;
            }
        }
        return true;
    }

    /**
     * @return A String representation of the game board with the coins 0-indexed
     */
    @Override
    public String toString() {
        // Hint: build up the string version of the board using concatenation (+)
        String board = "";
        for (int i = 0; i < lenStrip; i++) {
            boolean temp = true;
            for (int j = 0; j < coins.length; j++) {
                if (i == coins[j]) {
                    board += "[*]";
                    temp = false;
                }
            }
            if (temp) {
                board += "[]";
            }
        }
        return board;
    }

    public static void main(String[] args) {
        CoinStrip game = new CoinStrip();
        int turn = 0;
  
        // repeatedly take turns until the game is over
        while (!game.isGameOver()) {
            System.out.println(game);
  
            System.out.print("Enter player " + turn + "'s move: ");
            int coin = StdIn.readInt();
            int spaces = StdIn.readInt();
  
            // repeatedly ask for a move until a valid move is entered
            while (!game.isValidMove(coin, spaces)) {
                System.out.println("\nInvalid move! Try again");
                System.out.println(game);
  
                System.out.print("Enter player " + turn + "'s move: ");
                coin = StdIn.readInt();
                spaces = StdIn.readInt();
            }
  
            game.makeMove(coin, spaces);
            turn = (turn + 1) % 2;  // will flip turn between 0 and 1
        }

        System.out.println(game);
        System.out.println("Player " + (turn + 1) % 2 + " wins!");
    }
}
