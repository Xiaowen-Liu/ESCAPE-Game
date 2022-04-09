package byow.lab12;
import org.junit.Test;
import static org.junit.Assert.*;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.lang.reflect.Array;
import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    private static final long SEED = 2873123;
    private static final Random RANDOM = new Random(SEED);

    public static void main(String[] args) {
        initial();

        Position x = new Position(3, 40);
        drawWorld(2,  x, 2);
        //Hexaworld(middleLeft, 2);
        ter.renderFrame(world);


    }
    private static void drawWorld(int s, Position p, int tessSize) {
        addHexColumn(s, p, tessSize);
        for (int i = 1; i < tessSize; i++) {
            p = getUpRight(p, s);
            addHexColumn(s, p, tessSize + i);
        }
        for (int i = tessSize - 2; i >= 0; i--) {
            p = getBottomRight(p, s);
            addHexColumn(s, p, tessSize + i);
        }

    }

    private static Position getUpRight(Position p, int size) {
        return p.shift(2*size -1, size);
    }

    private static Position getBottomRight(Position p, int size) {
        return p.shift(2*size -1, -size);
    }
    private static final int WIDTH = 50;
    private static final int HEIGHT = 50;
    private static TETile[][] world;
    private static TERenderer ter;
    private static void initial() {
        // initialize the tile rendering engine with a window of size WIDTH x HEIGHT
        ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        // initialize tiles
        world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
    }
    private static int findLongest(int s) {
        return s + (s-1)*2;
    }


    public static void addHexagon (int s, TETile tile, Position p) {
        //enter the leftmost position with width = wid, height = height
        if (s < 2) {
            return;
        }
        lowerHex(s,tile, p);
        Position t = p.shift(0, s-1);
        upperHex(s, tile, t);

    }
    public static void addHexColumn (int s, Position p, int num) {
        if (num < 1) {
            return;
        }
        addHexagon (s, randomTile(), p);
        if (num > 1) {
            Position bottom = getBottomOne(p, s);
            addHexColumn(s, bottom, num-1);
        }


    }
    public static Position getBottomOne(Position p, int size) {
        return p.shift(0, -2 * size);
    }


    private static int[] lowerHex(int s, TETile tile, Position p) {
        int factor = 0;
        int height = p.y;
        int wid = p.x;
        for (int level = height; level < s + height; level += 1) {
            for (int location = wid; location < wid + s + 2 * factor ; location += 1) {
                world[location][level] = tile;
            }
            wid -= 1;
            factor += 1;
        }
        int[] finalPosi = new int[] {wid, height};
        return finalPosi;
    }

    private static int[] upperHex(int s, TETile tile, Position p) {
        int factor = 0;
        int height = p.y;
        int wid = p.x;
        for (int level = s + height; level > height; level -= 1) {
            for (int location = wid; location < wid + s + 2*factor ; location += 1) {
                world[location][level] = tile;
            }
            wid -= 1;
            factor += 1;
        }
        int[] finalPosi = new int[] {wid, height};
        return finalPosi;
    }
    /**private static int[] Hexaworld(int[] middleLeft, int s){
        if (middleLeft == null) {
            return null;
        }
        int[] left = null;
        int[] right = null;
        if (middleLeft[0] - findLongest(s) >= 0) {
            left = addHexagon(s, middleLeft[0] - findLongest(s), middleLeft[1]);
        }
        if (middleLeft[0] + findLongest(s) <= WIDTH) {
            right = addHexagon(s, middleLeft[0] + findLongest(s), middleLeft[1]);
        }
        Hexaworld(right, s);
        Hexaworld(left, s);
        return null;
    }
     */
    private static class Position {
        int x;
        int y;

        Position(int x, int y) {
            this.x = x;
            this.y = y;
        }
        public Position shift(int dx, int dy) {
            return new Position(x + dx, y + dy);
        }
    }

    private static TETile randomTile() {
        int tileNum = RANDOM.nextInt(6);
        switch (tileNum) {
            case 0: return Tileset.WALL;
            case 1: return Tileset.FLOWER;
            case 2: return Tileset.GRASS;
            case 3: return Tileset.SAND;
            case 4: return Tileset.MOUNTAIN;
            case 5: return Tileset.AVATAR;
            case 6: return Tileset.TREE;
            default: return Tileset.NOTHING;
        }
    }
}
