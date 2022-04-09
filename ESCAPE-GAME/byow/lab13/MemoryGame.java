package byow.lab13;

import byow.Core.RandomUtils;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.Color;
import java.awt.Font;
import java.util.Random;

import static java.awt.Color.WHITE;

public class MemoryGame {
    /** The width of the window of this game. */
    private int width;
    /** The height of the window of this game. */
    private int height;
    /** The current round the user is on. */
    private int round;
    /** The Random object used to randomly generate Strings. */
    private Random rand;
    /** Whether or not the game is over. */
    private boolean gameOver;
    /** Whether or not it is the player's turn. Used in the last section of the
     * spec, 'Helpful UI'. */
    private boolean playerTurn;
    /** The characters we generate random Strings from. */
    private static final char[] CHARACTERS = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    /** Encouraging phrases. Used in the last section of the spec, 'Helpful UI'. */
    private static final String[] ENCOURAGEMENT = {"You can do this!", "I believe in you!",
                                                   "You got this!", "You're a star!", "Go Bears!",
                                                   "Too easy for you!", "Wow, so impressive!"};

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please enter a seed");
            return;
        }

        long seed = Long.parseLong(args[0]);
        MemoryGame game = new MemoryGame(40, 40, seed);
        //game.flashSequence("yeehaw");
        game.startGame();
    }

    public MemoryGame(int width, int height, long seed) {
        /* Sets up StdDraw so that it has a width by height grid of 16 by 16 squares as its canvas
         * Also sets up the scale so the top left is (0,0) and the bottom right is (width, height)
         */
        this.width = width;
        this.height = height;
        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();

        //TODO: Initialize random number generator
        this.rand = new Random(seed);
    }

    public String generateRandomString(int n) {
        //TODO: Generate random string of letters of length n
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < n; i ++) {
            int index = rand.nextInt(CHARACTERS.length);
            result.append(CHARACTERS[index]);
        }
        return result.toString();
    }

    public void drawFrame(String s) {
        //TODO: Take the string and display it in the center of the screen
        //TODO: If game is not over, display relevant game information at the top of the screen
        StdDraw.clear(Color.black);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setPenColor(WHITE);
        StdDraw.text(0.5 * width, 0.5 * height, s);

        if (!gameOver) {
            Font head = new Font("Monaco", Font.BOLD, 20);
            StdDraw.setFont(head);
            StdDraw.text(0.1 * width, 0.95 * height, "Round: " + round);
            if (playerTurn) {
                StdDraw.text(0.45 * width,0.95 * height,"Type!");
            } else {
                StdDraw.text(0.45 * width,0.95 * height,"Watch!");
            }
            StdDraw.text(0.8 * width,0.95 * height, ENCOURAGEMENT[rand.nextInt(ENCOURAGEMENT.length)]);
            StdDraw.line(0, 0.9*height, width, 0.9*height);
        }

        StdDraw.show();
    }



    public void flashSequence(String letters) {
        //TODO: Display each character in letters,
        // making sure to blank the screen between letters
        for (int i = 0; i < letters.length(); i ++) {
            char curr = letters.charAt(i);
            drawFrame(Character.toString(curr));
            StdDraw.pause(1000);
            drawFrame("");
            StdDraw.pause(500);
        }
    }

    public String solicitNCharsInput(int n) {
        //TODO: Read n letters of player input
        String result = "";
        while(result.length() != n) {
            if (StdDraw.hasNextKeyTyped()) {
                char curr = StdDraw.nextKeyTyped();
                result += curr;
                drawFrame(result);
            }
        }
        return result;
    }

    public void startGame() {

        round = 1;
        gameOver = false;
        while (!gameOver) {
            drawFrame("Round:" + round);
            StdDraw.pause(1000);
            String target = generateRandomString(round);
            flashSequence(target);
            playerTurn = true;
            drawFrame("");
            String result = solicitNCharsInput(round);
            playerTurn = false;
            if (!target.equals(result)) {
                gameOver = true;
                drawFrame("Game Over! You made it to round:" + round);
            }
            round += 1;
        }
    }

}
