package byow.Core;


import byow.TileEngine.TETile;

import java.util.Random;


/** Collect position information of each room */
public class Sight {

    Position avatar;
    TETile[][] world;

    int WIDTH;
    int HEIGHT;
    Random random;


    /**
    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(80, 30);
        GenerateWorld x = new GenerateWorld(80, 30, "");
        x.fillBoardWithNothing();


        long seed = Long.parseLong("3217772");
        Random random = new Random(seed);

        //create the map of rooms & hallways & avatar
        Engine engine = new Engine();
        GenerateWorld map = engine.createWorld(random, "");


        Sight trial = new Sight(x.world, x.avatar, x.WIDTH,x.HEIGHT);
        trial.verticalSquare(x.world, 3, map.avatar, map.world);
        //x.world[map.avatar.shift(0, 5).x][map.avatar.shift(0, 5).y] = Tileset.TREE;


        ter.renderFrame(x.world);
    }
     */

    Sight(TETile[][] world, Position avatar, int width, int height, Random random) {
        this.world = world;
        this.avatar = avatar;
        this.WIDTH = width;
        this.HEIGHT = height;
        this.random = random;

    }
    /**1. Generate a new world
     * 2. call verticalSquare to draw partial view around the avatar
     * 3. return this partial-view world*/
    public TETile[][] sightVersion() {
        GenerateWorld x = new GenerateWorld(WIDTH, HEIGHT, "", this.random);
        x.fillBoardWithNothing();
        verticalSquare(x.world, 4, avatar, world);
        return x.world;
    }


   /** Similar to hexagon in lab 12, draw a square around the avatar */
    public void verticalSquare(TETile[][] newWorld, int numRow, Position p, TETile[][] background) {

        for (int i = 0; i < numRow; i++) {
            drawRow(newWorld, p.shift(-i, numRow - i), i * 2 + 1, background);
        }
        drawRow(newWorld, p.shift(-numRow, 0), numRow * 2 + 1, background);
        for (int i = 0; i < numRow; i++) {
            drawRow(newWorld, p.shift(-i, -numRow + i), i * 2 + 1, background);
        }
    }
    /**1. only draw the tile if it's within the range the map
    2. according to the original world, draw the tiles*/
    public void drawRow(TETile[][] newWorld, Position p, int length, TETile[][] map) {
        for (int dx = 0; dx < length; dx++) {
            if (!(((p.x + dx) < 0) || ((p.x + dx) >= WIDTH) || (p.y < 0) || (p.y >= HEIGHT))) {
                newWorld[p.x + dx][p.y] = map[p.x + dx][p.y];
            }
        }
    }


}
