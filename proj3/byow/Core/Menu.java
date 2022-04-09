package byow.Core;


import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;



public class Menu {
    int WIDTH;
    int HEIGHT;


    Menu(int width, int height) {
        this.WIDTH = width;
        this.HEIGHT = height;
    }


    public void headings(String userName) {
        StdDraw.clear(Color.black);
        StdDraw.setPenColor(Color.WHITE);
        Font font = new Font("Monaco", Font.BOLD, 40);
        StdDraw.setFont(font);
        String s = "CS61B: THE GAME";
        StdDraw.text(0.5 * WIDTH, 0.7 * HEIGHT, s);

        Font smallFont = new Font("Monaco", Font.BOLD, 20);
        StdDraw.setFont(smallFont);

        StdDraw.text(0.5 * WIDTH, 0.5 * HEIGHT, "New Game (N)");
        StdDraw.text(0.5 * WIDTH, 0.4 * HEIGHT, "Load Game (L)");
        StdDraw.text(0.5 * WIDTH, 0.3 * HEIGHT, "Quit (Q)");
        StdDraw.text(0.5 * WIDTH, 0.2 * HEIGHT, "Enter your Name (E)");
        StdDraw.text(WIDTH - 10, HEIGHT - 2, "Your Name: " + userName);
        StdDraw.text(18, HEIGHT - 2, "Enter your name before starting a new game.");
        StdDraw.show();
    }

    public void seed(String input) {
        StdDraw.clear(Color.black);
        StdDraw.setPenColor(Color.WHITE);
        Font font = new Font("Monaco", Font.BOLD, 40);
        StdDraw.setFont(font);
        // Draw the heading
        String s = "Your Seed: ";
        StdDraw.text(0.5 * WIDTH, 0.6 * HEIGHT, s);

        //Draw the seed
        StdDraw.text(0.5 * WIDTH, 0.4 * HEIGHT, input);


        //Hint
        Font smallFont = new Font("Monaco", Font.BOLD, 20);
        StdDraw.setFont(smallFont);
        String hint = "Press 's' when you finish entering your name.";
        StdDraw.text(0.5 * WIDTH, 0.8 * HEIGHT, hint);

        StdDraw.show();
    }

    public void name(String input) {
        StdDraw.clear(Color.black);
        StdDraw.setPenColor(Color.WHITE);
        Font font = new Font("Monaco", Font.BOLD, 40);
        StdDraw.setFont(font);
        // Draw the heading
        String s = "Your Name: ";
        StdDraw.text(0.5 * WIDTH, 0.6 * HEIGHT, s);

        //Draw the seed
        StdDraw.text(0.5 * WIDTH, 0.4 * HEIGHT, input);

        //Hint:
        Font smallFont = new Font("Monaco", Font.BOLD, 20);
        StdDraw.setFont(smallFont);
        String hint = "Press ':' when you finish entering your name.";
        StdDraw.text(0.5 * WIDTH, 0.8 * HEIGHT, hint);

        StdDraw.show();
    }


    public void popQuiz(int num, GenerateWorld map) {
        StdDraw.clear(Color.black);
        StdDraw.setPenColor(Color.WHITE);
        Font font = new Font("Monaco", Font.BOLD, 20);
        StdDraw.setFont(font);
        String question;
        String answerOne;
        String answerTwo;
        String answerThree;

        Quiz x = map.myQuiz;

        // Draw the heading
        if (num == 0) {
            question = x.curr.get(0)[0];
            answerOne = x.curr.get(0)[1];
            answerTwo = x.curr.get(0)[2];
            answerThree = x.curr.get(0)[3];
        } else if (num == 1) {
            question = x.curr.get(1)[0];
            answerOne = x.curr.get(1)[1];
            answerTwo = x.curr.get(1)[2];
            answerThree = x.curr.get(1)[3];
        } else {
            question = x.curr.get(2)[0];
            answerOne = x.curr.get(2)[1];
            answerTwo = x.curr.get(2)[2];
            answerThree = x.curr.get(2)[3];
        }

        StdDraw.text(0.5 * WIDTH, 0.9 * HEIGHT, "Enter the correct number.");
        StdDraw.text(0.5 * WIDTH, 0.7 * HEIGHT, "Q:  " + question);
        StdDraw.text(0.5 * WIDTH, 0.5 * HEIGHT, "[1]  " + answerOne);
        StdDraw.text(0.5 * WIDTH, 0.4 * HEIGHT, "[2]  " + answerTwo);
        StdDraw.text(0.5 * WIDTH, 0.3 * HEIGHT, "[3]  " + answerThree);

        map.needAnswer = true;
        map.quizNum = num;

        StdDraw.show();
    }

    public void correctAns() {
        StdDraw.clear(Color.black);
        StdDraw.setPenColor(Color.WHITE);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.text(0.5 * WIDTH, 0.6 * HEIGHT, "You're correct!");
        StdDraw.text(0.5 * WIDTH, 0.4 * HEIGHT, "wins 1 point!");
        StdDraw.show();
        StdDraw.pause(2000);
    }
    public void wrongAns() {
        StdDraw.clear(Color.black);
        StdDraw.setPenColor(Color.WHITE);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.text(0.5 * WIDTH, 0.6 * HEIGHT, "You're wrong!");
        StdDraw.text(0.5 * WIDTH, 0.4 * HEIGHT, "wins 0 point!");
        StdDraw.show();
        StdDraw.pause(2000);
    }

    public boolean bonusTime(int score) {

        StdDraw.pause(500);
        StdDraw.clear(Color.black);
        StdDraw.setPenColor(Color.WHITE);
        Font font = new Font("Monaco", Font.BOLD, 40);
        StdDraw.setFont(font);
        // Draw the heading

        if (score < 3) {
            String s = "Your score is lower than 3!";
            StdDraw.text(0.5 * WIDTH, 0.6 * HEIGHT, s);

            //Draw the seed
            String hint = "It's time to win more points!";
            StdDraw.text(0.5 * WIDTH, 0.4 * HEIGHT, hint);
            StdDraw.show();
            StdDraw.pause(2000);
        } else {

            String s = "You've earned " + score + " points! You win!";
            StdDraw.text(0.5 * WIDTH, 0.6 * HEIGHT, s);
            String hint = "The game will end in 5 seconds.";
            StdDraw.text(0.5 * WIDTH, 0.4 * HEIGHT, hint);

            StdDraw.show();
            StdDraw.pause(5000);
            System.exit(0);
        }
        return true;


    }

    public void finalCheck(int score) {
        StdDraw.clear(Color.black);
        StdDraw.setPenColor(Color.WHITE);
        Font font = new Font("Monaco", Font.BOLD, 40);
        StdDraw.setFont(font);
        // Draw the heading
        String s;
        String y;
        if (score < 3) {
            s = "Your score is lower than 3!";
            y = "You lose！";
        } else {
            s = "You've earned " + score + " points!";
            y = "You win！";

        }
        StdDraw.text(0.5 * WIDTH, 0.6 * HEIGHT, s);
        StdDraw.text(0.5 * WIDTH, 0.4 * HEIGHT, y);
        StdDraw.show();
        StdDraw.pause(2000);
        System.exit(0);
    }





/**
    public void LoadMenu() {
        StdDraw.clear(Color.black);
        StdDraw.setPenColor(Color.WHITE);
        Font hintFont = new Font("Monaco", Font.BOLD, 20);
        StdDraw.setFont(hintFont);
        String hint = "Press any of the 3 keys to load.";
        StdDraw.text(0.5 * WIDTH, 0.8 * HEIGHT, hint);


        Font slotFont = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(slotFont);
        StdDraw.text( 0.5 * WIDTH, 0.6 * HEIGHT, "Slot 1 -- Press L");
        StdDraw.text( 0.5 * WIDTH, 0.4 * HEIGHT, "Slot 2 -- Press O");
        StdDraw.text(0.5 * WIDTH, 0.2 * HEIGHT, "Slot 3 -- Press P");

        StdDraw.show();
    }
 */


}
