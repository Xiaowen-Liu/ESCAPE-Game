package byow.Core;

import java.util.ArrayList;



public class Quiz {
    ArrayList<Position> flowers;
    ArrayList<String[]> cs61b;
    ArrayList<String[]> curr;
    ArrayList<String[]> berkeley;
    ArrayList<ArrayList<String[]>> quizzes;

    public static void main(String[] args) {
        Quiz x = new Quiz();
        Position yo = new Position(0, 0);
        x.addFlower(yo);
        Position hi = new Position(1, 1);
        x.addFlower(hi);
        Position really = new Position(3, 3);
        x.addFlower(really);
        System.out.println(x.findNum(new Position(3, 3)));
    }

    Quiz() {
        flowers = new ArrayList<Position>();
        quizzes = new ArrayList<ArrayList<String[]>>();


        setCs61b();
        setBerkeley();
        this.curr = cs61b;
    }

    Quiz(int quizData) {
        flowers = new ArrayList<Position>();
        quizzes = new ArrayList<ArrayList<String[]>>();

        setCs61b();
        setBerkeley();

        this.curr  = quizzes.get(quizData);
    }

    public void setBerkeley() {
        berkeley = new ArrayList<String[]>();
        String[] first = new String[5];
        first[0] = "How tall is the Campanile?";
        first[1] = "307 feet";
        first[2] = "308 feet";
        first[3] = "309 feet";
        first[4] = "1";
        berkeley.add(first);

        String[] second = new String[5];
        second[0] = "What is the name of our university?";
        second[1] = "Berkelee";
        second[2] = "Berkeley";
        second[3] = "Berkaley";
        second[4] = "2";
        berkeley.add(second);

        String[] third = new String[5];
        third[0] = "What is the mascot of our university";
        third[1] = "Oski";
        third[2]  = "Bruin";
        third[3]  = "Slug";
        third[4]  = "1";
        berkeley.add(third);

        quizzes.add(berkeley);


    }

    public void setCs61b() {
        cs61b = new ArrayList<String[]>();
        String[] first = new String[5];
        first[0] = "What is the name of project 2?";
        first[1] = "getlet";
        first[2] = "gitbig";
        first[3] = "gitlet";
        first[4] = "3";
        cs61b.add(first);


        String[] second = new String[5];
        second[0] = "What is the name of our professor?";
        second[1] = "Josh Hug";
        second[2] = "Josh Bug";
        second[3] = "John Hug";
        second[4] = "1";
        cs61b.add(second);


        String[] third = new String[5];
        third[0] = "When does our final exam happen? (regular time)";
        third[1] = "?! We have finals?";
        third[2]  = "5/11, 8 - 11 am";
        third[3]  = "11/5 8 - 11 am";
        third[4]  = "2";
        cs61b.add(third);

        quizzes.add(cs61b);
    }

    public void addFlower(Position p) {
        flowers.add(p);
    }

    public int findNum(Position p) {
        int x = 0;
        for (Position i: flowers) {
            if (p.same(i)) {
                x = flowers.indexOf(i);
            }
        }
        return x;
    }

    public void checkAnswer(GenerateWorld map, String input) {
        int number = map.quizNum;
        String answer = curr.get(number)[4];
        Menu x = new Menu(map.WIDTH, map.HEIGHT);
        if (input.equals(answer)) {
            x.correctAns();
            map.score += 1;
        } else {
            x.wrongAns();
        }
    }

    public void processAnswer(GenerateWorld map, String input) {
        int number = map.quizNum;
        String answer = curr.get(number)[4];
        if (input.equals(answer)) {
            map.score += 1;
        }
    }
}
