/*
 * Test cases for the CoinStrip class
 * (c) Aaron Bauer 2021
 */

import java.util.Arrays;

public class CoinStripTest {
    public static final String SUCCESS = "PASSED";
    private static int passed; // number of passing tests
    private static int testCount; // total number of tests

    private static boolean noStartingWin() {
        testCount++;
        System.out.print("Testing if CoinStrip constructor always creates a game that isn't over... ");
        for (int i = 0; i < 10000; i++) {
            CoinStrip cs = new CoinStrip();
            if (cs.isGameOver()) {
                System.out.println("FAILED\nCoinStrip constructor created an instance for which isGameOver() returned true:\n"
                                   + cs.toString());
                return false;
            }
        }
        passed++;
        System.out.println(SUCCESS);
        return true;
    }

    private static boolean noValidMoves() {
        testCount++;
        System.out.print("Testing if CoinStrip constructor always creates a game with a valid move... ");
        for (int i = 0; i < 10000; i++) {
            CoinStrip cs = new CoinStrip();
            boolean hasValidMove = false;
            for (int j = 0; j < cs.getNumCoins(); j++) {
                hasValidMove = hasValidMove || cs.isValidMove(j, 1);
            }
            if (!hasValidMove) {
                System.out.println("FAILED\nCoinStrip constructor created an instance for which there is no valid move:\n"
                                   + cs.toString());
                return false;
            }
        }
        passed++;
        System.out.println(SUCCESS);
        return true;
    }

    private static boolean tooFewCoins() {
        testCount++;
        System.out.print("Testing if CoinStrip constructor always creates a game with at least 3 coins... ");
        for (int i = 0; i < 10000; i++) {
            CoinStrip cs = new CoinStrip();
            if (cs.getNumCoins() < 3) {
                System.out.println("FAILED\nCoinStrip constructor created an instance with fewer than 3 coins:\n"
                                   + cs.toString());
                return false;
            }
        }
        passed++;
        System.out.println(SUCCESS);
        return true;
    }

    private static boolean randomCoins() {
        testCount++;
        int coinAmount = -1;
        int[] coinPos = null;
        boolean randAmount = false;
        boolean randPos = false;
        System.out.print("Testing if CoinStrip constructor creates games with random coin amounts and positions... ");
        for (int i = 0; i < 10000 && (!randAmount || !randPos); i++) {
            CoinStrip cs = new CoinStrip();
            if (coinAmount == -1) {
                coinAmount = cs.getNumCoins();
            }
            if (cs.getNumCoins() != coinAmount) {
                randAmount = true;
            }
            coinAmount = cs.getNumCoins();

            if (coinPos == null) {
                coinPos = new int[]{cs.getCoinPosition(0), cs.getCoinPosition(1), cs.getCoinPosition(2)};
            }
            if (!Arrays.equals(coinPos, new int[]{cs.getCoinPosition(0), cs.getCoinPosition(1), cs.getCoinPosition(2)})) {
                randPos = true;
            }
        }
        if (!randAmount) {
            System.out.println("FAILED\nNumber of coins is always " + coinAmount);
            return false;
        }
        if (!randPos) {
            System.out.println("FAILED\nCoins positions are always " + Arrays.toString(coinPos));
            return false;
        }
        passed++;
        System.out.println(SUCCESS);
        return true;
    }

    private static boolean illegalMoves() {
        testCount++;
        System.out.print("Testing if CoinStrip allows illegal moves... ");
        CoinStrip cs = new CoinStrip();
        // no going right
        if (cs.isValidMove(0, -1)) {
            System.out.println("FAILED\nCoinStrip allows move to the right");
            return false;
        }
        // mandatory progress
        if (cs.isValidMove(0, 0)) {
            System.out.println("FAILED\nCoinStrip allows move of zero spaces");
            return false;
        }
        // no double occupancy, no jumps
        int[] coinPos = new int[]{cs.getCoinPosition(0), cs.getCoinPosition(1), cs.getCoinPosition(2)};
        if (coinPos[0] == coinPos[1]) {
            System.out.println("FAILED\nCoinStrip constructor produced a game with two coins in the same position");
            return false;
        }
        String failMsg = "FAILED\nCoinStrip allows one coin to jump over another";
        if (coinPos[0] < coinPos[1]) {
            if (cs.isValidMove(1, coinPos[1] - coinPos[0])) {
                System.out.println(failMsg);
                return false;
            }
            if (cs.isValidMove(1, coinPos[1])) {
                System.out.println(failMsg);
                return false;
            }
        } else {
            if (cs.isValidMove(0, coinPos[0])) {
                System.out.println(failMsg);
                return false;
            }
        }
        passed++;
        System.out.println(SUCCESS);
        return true;
    }

    public static void main(String[] args) {
        noStartingWin();
        System.out.println();
        noValidMoves();
        System.out.println();
        tooFewCoins();
        System.out.println();
        randomCoins();
        System.out.println();
        illegalMoves();
        System.out.println("\n\n" + passed + " out of " + testCount + " tests passed.");
    }
}
