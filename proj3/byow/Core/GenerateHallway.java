package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;




public class GenerateHallway {
    public static final int WIDTH = 80;
    public static final int HEIGHT = 50;
/**
    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        TETile[][] world = new TETile[WIDTH][HEIGHT];
        ArrayList<Room> roomList = new ArrayList<Room>(10);
        GenerateWorld x = new GenerateWorld(80, 50, "");
        x.fillBoardWithNothing();
        //Position anchor = new Position(12, 34); // Picked arbitrarily
        //190001 is a good test number
        //s在p的左上角
        //Position p = new Position(10, 18);
        //Position s = new Position(3, 30);

        //s在p的右上角
        //Position p = new Position(6, 18);
        //Position s = new Position(13, 30);

        //s在p的右方
        //Position p = new Position(20, 42);
        //Position s = new Position(30, 30);

        //s在p的右下角
        Position p = new Position(54, 9);
        Position s = new Position(63, 22);

        //s在p的右下角
        //Position s = new Position(10, 18);
        //Position p = new Position(3, 30);

        //s在p的左下角
        //Position s = new Position(6, 18);
        //Position p = new Position(13, 30);

        //s在p的左侧
        //Position s = new Position(6, 18);
        //Position p = new Position(20, 18);

        //s在p的上方
        //Position s = new Position(13, 30);
        //Position p = new Position(13, 18);

        //s在p的下方
        //Position p = new Position(10, 30);
        //Position s = new Position(15, 18);


        //draw two rooms:
        x.SingleRectRoom(s, 7, 4, Tileset.FLOOR, x.world, roomList);
        x.SingleRectRoom(p, 5, 18, Tileset.FLOOR, x.world, roomList);
        //Position rightMiddle = new Position(p.x + 6 - 1, p.y + 5/2);
        //Position leftMiddle = new Position(s.x + 6 - 1, p.y + 9/2);
        //drawColumn(world, p, Tileset.WALL, 5);
        GenerateHallway y = new GenerateHallway();
        for (int i = 0; i < roomList.size() - 1; i += 1) {
            Room first = roomList.get(i);
            Room second = roomList.get(i + 1);
            y.Hallway(x.world, first, second);
        }

        ter.renderFrame(x.world);


    }
    */


    //Build hallway between the given 2 rooms
    public void hallway(TETile[][] tiles, Room first, Room second) {
        //Find the position of one single tile on each room for connection
        // so that the hallway has the shortest distance
        Position[] allFirst = new Position[4];
        first.allMiddle(first, allFirst);
        Position[] allSecond = new Position[4];
        second.allMiddle(second, allSecond);
        double smallest = Double.POSITIVE_INFINITY;
        Position[] result = new Position[2];

        for (Position i : allFirst) {
            for (Position j : allSecond) {
                double length = (Math.pow((i.x - j.x), 2)  + (Math.pow((i.y - j.y), 2)));
                if (length < smallest) {
                    smallest = length;
                    result[0] = i;
                    result[1] = j;
                }
            }
        }

        //draw hallway according to points
        Position start = result[0];
        Position end = result[1];
        singleHallway(tiles, start, end);

    }
    //draw one side wall of the hallway
    public void outerWall(TETile[][] tiles, Position start, Position end) {
        if (start.y < end.y) {
            //end 在上方
            if (start.x > end.x) {
                //outer就是右侧，dx = 1
                drawColumn(tiles, start.shift(1, 0), Tileset.WALL,
                        Math.abs(end.y - start.y) + 1, end.y - start.y);
                drawRow(tiles, start.shift(1, end.y - start.y + 1), Tileset.WALL,
                        Math.abs(end.x - start.x) + 1, end.x - start.x);
            } else {
                drawColumn(tiles, start.shift(-1, 0), Tileset.WALL,
                        Math.abs(end.y - start.y) + 1, end.y - start.y);
                drawRow(tiles, start.shift(-1, end.y - start.y + 1), Tileset.WALL,
                        Math.abs(end.x - start.x) + 2, end.x - start.x);
            }
        } else {
            //end在下方
            if (start.x > end.x) {
                drawColumn(tiles, start.shift(1, -1), Tileset.WALL,
                        Math.abs(end.y - start.y) + 1, end.y - start.y);
                drawRow(tiles, start.shift(1, end.y - start.y - 1), Tileset.WALL,
                        Math.abs(end.x - start.x) + 1, end.x - start.x);
            } else {
                drawColumn(tiles, start.shift(1, 0), Tileset.WALL,
                        Math.abs(end.y - start.y) + 1, end.y - start.y);
                drawRow(tiles, start.shift(1, end.y - start.y + 1), Tileset.WALL,
                        Math.abs(end.x - start.x) + 1, end.x - start.x);
            }

        }
    }
    //draw the other
    public void innerWall(TETile[][] tiles, Position start, Position end) {
        if (start.y < end.y) {
            if (start.x > end.x) {
                drawColumn(tiles, start.shift(-1, 0), Tileset.WALL,
                        Math.abs(end.y - start.y) + 1, end.y - start.y);
                drawRow(tiles, start.shift(-1, end.y - start.y - 1), Tileset.WALL,
                        Math.abs(end.x - start.x) + 1, end.x - start.x);
            } else {
                drawColumn(tiles, start.shift(1, 0), Tileset.WALL,
                        Math.abs(end.y - start.y) + 1, end.y - start.y);
                drawRow(tiles, start.shift(1, end.y - start.y - 1), Tileset.WALL,
                        Math.abs(end.x - start.x) + 1, end.x - start.x);
            }
        } else {
            if (start.x > end.x) {
                drawColumn(tiles, start.shift(-1, 0), Tileset.WALL,
                        Math.abs(end.y - start.y) + 1, end.y - start.y);
                drawRow(tiles, start.shift(-1, end.y - start.y + 1),
                        Tileset.WALL,
                        Math.abs(end.x - start.x) + 1, end.x - start.x);
            } else {
                drawColumn(tiles, start.shift(-1, -1), Tileset.WALL,
                        Math.abs(end.y - start.y) + 1, end.y - start.y);
                drawRow(tiles, start.shift(-1, end.y - start.y - 1),
                        Tileset.WALL, Math.abs(end.x - start.x) + 1, end.x - start.x);
            }

        }
    }
    //draw the hallway between 2 positions
    public void singleHallway(TETile[][] tiles, Position start, Position end) {

        int startX = start.x;
        int startY = start.y;
        int endX = end.x;
        int endY = end.y;
        //outer column
        outerWall(tiles, start, end);

        //inner column
        innerWall(tiles, start, end);

        //draw the floor
        drawColumn(tiles, start, Tileset.FLOOR, Math.abs(endY - startY), endY - startY);
        drawRow(tiles, start.shift(0, endY - startY), Tileset.FLOOR,
                Math.abs(endX - startX) + 1, endX - startX);
        //check if the hallway makes the room open
        checkEmpty(tiles, start, end);


    }

    private boolean inRange(Position p) {
        return (p.x < WIDTH) && (p.x >= 0) && (p.y < HEIGHT) && (p.y >= 0);
    }
    //If any of the rooms is not fully surrounded by walls, replace that tile by WALL tile
    public void checkEmpty(TETile[][] tiles, Position start, Position end) {
        int startX = start.x;
        int startY = start.y;
        int endX = end.x;
        int endY = end.y;


        //for end
        if (inRange(end.shift(-1, 0))) {
            if (tiles[endX - 1][endY].equals(Tileset.NOTHING)) {
                tiles[endX - 1][endY] = Tileset.WALL;
            }
        }
        if (inRange(end.shift(1, 0))) {
            if (tiles[endX + 1][endY].equals(Tileset.NOTHING)) {
                tiles[endX + 1][endY] = Tileset.WALL;
            }
        }
        if (inRange(end.shift(0, 1))) {
            if (tiles[endX][endY + 1].equals(Tileset.NOTHING)) {
                tiles[endX][endY + 1] = Tileset.WALL;
            }
        }
        if (inRange(end.shift(0, -1))) {
            if (tiles[endX][endY - 1].equals(Tileset.NOTHING)) {
                tiles[endX][endY - 1] = Tileset.WALL;
            }
        }

        //for start
        if (inRange(start.shift(0, 1))) {
            if (tiles[startX][startY + 1].equals(Tileset.NOTHING)) {
                tiles[startX][startY + 1] = Tileset.WALL;
            }
        }
        if (inRange(start.shift(0, -1))) {
            if (tiles[startX][startY - 1].equals(Tileset.NOTHING)) {
                tiles[startX][startY - 1] = Tileset.WALL;
            }
        }
        if (inRange(start.shift(1, 0))) {
            if (tiles[startX + 1][startY].equals(Tileset.NOTHING)) {
                tiles[startX + 1][startY] = Tileset.WALL;
            }
        }
        if (inRange(start.shift(-1, 0))) {
            if (tiles[startX - 1][startY].equals(Tileset.NOTHING)) {
                tiles[startX - 1][startY] = Tileset.WALL;
            }
        }
    }


    public void drawColumn(TETile[][] tiles, Position p, TETile tile, int length, int direction) {
        if (direction > 0) {
            for (int dy = 0; dy < length; dy++) {
                if (!tiles[p.x][p.y + dy].equals(Tileset.FLOOR)) {
                    tiles[p.x][p.y + dy] = tile;
                }
            }
        } else {
            for (int dy = 0; dy < length; dy++) {
                if (!tiles[p.x][p.y - dy].equals(Tileset.FLOOR)) {
                    tiles[p.x][p.y - dy] = tile;
                }
            }
        }

    }
    public void drawRow(TETile[][] tiles, Position p, TETile tile, int length, int direction) {
        if (direction > 0) {
            for (int dx = 0; dx < length; dx++) {
                if (!tiles[p.x + dx][p.y].equals(Tileset.FLOOR)) {
                    tiles[p.x + dx][p.y] = tile;
                }
            }
        } else {
            for (int dx = 0; dx < length; dx++) {
                if (!tiles[p.x - dx][p.y].equals(Tileset.FLOOR)) {
                    tiles[p.x - dx][p.y] = tile;
                }
            }
        }

    }

}
