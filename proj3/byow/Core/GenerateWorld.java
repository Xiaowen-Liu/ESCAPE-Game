package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.ArrayList;
import java.util.Random;

public class GenerateWorld {
    int WIDTH;
    int HEIGHT;
    TETile[][] world;
    //TETile[][] curr;
    Boolean sightVersion;
    ArrayList<Room> roomList;
    Position avatar;
    String userName;
    boolean haveName = false;
    Quiz myQuiz;
    boolean needAnswer = false;
    int quizNum = -1;
    int score = 0;
    int unanswered;
    boolean extraTime;
    Position[] sands;
    Random myRandom;
    boolean fixed;
    int time;
    boolean startTime;

   /** public static void main(String[] args) {
        long seed = Long.parseLong("1553");
        Random random = new Random(seed);
        GenerateWorld x =  new GenerateWorld(80, 70, "hi");
        ArrayList<Integer> y = new  ArrayList<Integer>();
        for (int i = 0; i < 1000; i++) {
            int num = x.randomLength(random, 2, 0);
            y.add(num);
        }
        System.out.println(y.contains(2));

    } */


    GenerateWorld(int width, int height, String userName, Random random) {
        world = new TETile[width][height];
        this.WIDTH = width;
        this.HEIGHT = height;
        //this.curr = world;
        this.sightVersion = false;
        if (!userName.equals("")) {
            this.userName = userName;
            haveName = true;
        }
        this.myRandom = random;
    }

    //return current version of the world
    public TETile[][] currVersion() {
        if (sightVersion) {
            return seeSight();
        } else {
            return world;
        }
    }

    //the partial view version
    public TETile[][] seeSight() {
        Sight x = new Sight(world, avatar, WIDTH, HEIGHT, myRandom);
        return x.sightVersion();
    }

    public void fillBoardWithNothing() {
        TETile[][] tiles = this.world;
        int height = tiles[0].length;
        int width = tiles.length;
        for (int x = 0; x < width; x += 1) {
            for (int y = 0; y < height; y += 1) {
                tiles[x][y] = Tileset.NOTHING;
            }
        }
    }

    public void createRooms(Random random) {
        this.roomList = new ArrayList<Room>(10);
        drawWorld(this.world, WIDTH - 4, HEIGHT - 4, random);


    }
    public void createHallways() {
        for (int i = 0; i < roomList.size() - 1; i += 1) {
            Room first = roomList.get(i);
            Room second = roomList.get(i + 1);
            GenerateHallway x = new GenerateHallway();
            x.hallway(world, first, second);
        }

    }


    /// Generate Room Helper Functions
    public int randomDivide(Random random) {
        double x = random.nextDouble();
        double y = 3 + (11 - 1) * x;
        return (int) y;
    }
    public int randomCount(Random random) {
        double x = random.nextDouble();
        double y = 1 + (4 - 1) * x;
        return (int) y;
    }
    public int randomLength(Random random, int upper, int lower) {
        double x = random.nextDouble();
        double y = lower + (upper - lower) * x;
        return (int) y;
    }

    //draw a room
    public void singleRectRoom(Position p, int width, int height,
                               TETile type, TETile[][] tiles) {
        //draw the wall
        for (int i = 0; i < height; i += 1) {
            drawRow(tiles, p.shift(0, i), Tileset.WALL, width);
        }
        //draw the floor and replace the wall with floor tiles
        for (int i = 0; i < height - 2; i += 1) {
            drawRow(tiles, p.shift(1, i + 1), type, width - 2);
        }
        Room curr = new Room(p, width, height);
        //add to room List
        roomList.add(curr);
    }

    public void drawRow(TETile[][] tiles, Position p, TETile tile, int length) {
        for (int dx = 0; dx < length; dx++) {
            tiles[p.x + dx][p.y] = tile;
        }
    }


    public void drawWorld(TETile[][] tiles, int width, int height,
                          Random random) {
        int columnNum = randomDivide(random);
        for (int i = 0; i < columnNum; i += 1) {
            int minX = (width / columnNum) * i;
            int maxX = (width / columnNum) * (i + 1);
            int toDivide = randomCount(random);
            processColumn(tiles, minX, maxX, random, toDivide, height);

        }

    }

    // Chop the column into several areas according to given num
    private void processColumn(TETile[][] tiles, int minX, int maxX, Random random,
                               int toDivide, int height) {

        for (int i = 0; i < toDivide; i += 1) {
            //Find the info of this new room
            int minHeight = (height / toDivide) * i;
            int maxHeight = (height / toDivide) * (i + 1);
            Position p = new Position(0, 0);
            p.randomPosi(minX + 1, maxX - 4, minHeight + 1,
                    maxHeight - 4, random, p);
            int recWidth = randomLength(random,  maxX - p.x - 1, 4);
            int recHeight = randomLength(random, maxHeight - p.y - 1, 4);
            //draw the room
            singleRectRoom(p, recWidth, recHeight, Tileset.FLOOR, tiles);
        }
    }

    //draw an avatar in the leftmost bottom corner in the 1st room
    public void createAvatar() {
        Room target = roomList.get(0);
        Position p = target.bottomMiddle.shift(0, 1);
        world[p.x][p.y] = Tileset.AVATAR;
        this.avatar = p;
    }

    //Create 3 NPC in the world
    public void createFlower(Random random) {
        int totalNum = roomList.size();
        int quizData = randomLength(random, 2, 0);
        myQuiz = new Quiz(quizData);
        Position[] x = addThree(random, totalNum, Tileset.FLOWER);
        for (Position i: x) {
            myQuiz.addFlower(i);
        }
        unanswered = myQuiz.curr.size();
    }

    public Position[] addThree(Random random, int totalNum, TETile tile) {
        Position[] x = new Position[3];
        //first NPC
        int one = randomLength(random, totalNum, 1);
        Room first = roomList.get(one);
        Position p1 = first.leftMiddle.shift(1, 0);
        while (true) {
            if (world[p1.x][p1.y].equals(Tileset.AVATAR)) {
                one = randomLength(random, totalNum, 1);
                first = roomList.get(one);
                p1 = first.leftMiddle.shift(1, 0);
            } else {
                world[p1.x][p1.y] = tile;
                break;
            }
        }
        x[0] = p1;


        //Second NPC
        int two = randomLength(random, totalNum, 1);
        Room second = roomList.get(two);
        Position p2 = second.topMiddle.shift(0, -1);
        while (true) {
            if (world[p2.x][p2.y].equals(Tileset.AVATAR)) {
                two = randomLength(random, totalNum, 1);
                second = roomList.get(two);
                p2 = second.topMiddle.shift(0, -1);
            } else {
                world[p2.x][p2.y] = tile;
                break;
            }
        }
        x[1] = p2;

        //Third NPC
        int three = randomLength(random, totalNum, 1);
        Room third = roomList.get(three);
        Position p3 = third.rightMiddle.shift(-1, 0);
        while (true) {
            if (world[p2.x][p2.y].equals(Tileset.AVATAR)) {
                three = randomLength(random, totalNum, 1);
                third = roomList.get(three);
                p3 = third.rightMiddle.shift(-1, 0);
            } else {
                world[p3.x][p3.y] = tile;
                break;
            }
        }
        x[2] = p3;

        return x;
    }



    public void createSand(Random random) {
        int totalNum = roomList.size();
        Position[] x = addThree(random, totalNum, Tileset.SAND);
        sands = x;
    }
}
