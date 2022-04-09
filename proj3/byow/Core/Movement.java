package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

public class Movement {
    Position p;
    TETile[][] world;
    GenerateWorld map;
    int WIDTH;
    int HEIGHT;

    Movement(GenerateWorld map) {
        this.map = map;
        this.p = map.avatar;
        this.world = map.world;
        this.WIDTH = map.WIDTH;
        this.HEIGHT = map.HEIGHT;
    }

    /**
     * 1. ensure the target position is not wall
     * 2. if so, move to the desired tile*/
    public void moveTo(Position start, Position target) {
        if (!world[target.x][target.y].equals(Tileset.WALL)) {
            if (world[target.x][target.y].equals(Tileset.FLOWER)) {
                //Find the number the floor.
                int index = map.myQuiz.findNum(target);
                Menu menu = new Menu(WIDTH, HEIGHT);
                menu.popQuiz(index, map);
            } else if (world[target.x][target.y].equals(Tileset.SAND)) {
                map.score += 1;
            }
            world[start.x][start.y] = Tileset.FLOOR;
            world[target.x][target.y] = Tileset.AVATAR;
            map.avatar = target;
            this.p = target;
        }
    }
    /**1. according to the input, moveTo different positions
     2. if the input is "L", map.sightVersion = !map.sightVersion;
     Switch the Version of full view/ partial view*/
    public void findTarget(String n) {
        if (n.equals("W") || n.equals("w")) {
            moveTo(p, p.shift(0, 1));
        } else if (n.equals("S") || n.equals("s")) {
            moveTo(p, p.shift(0, -1));
        } else if (n.equals("A") || n.equals("a")) {
            moveTo(p, p.shift(-1, 0));
        } else if (n.equals("D") || n.equals("d")) {
            moveTo(p, p.shift(1, 0));
        } else if (n.equals("Y") || n.equals("y")) {
            if (!map.fixed) {
                map.sightVersion = !map.sightVersion;
            }
        } else if (n.equals("1") || n.equals("2") || n.equals("3")) {
            map.myQuiz.processAnswer(map, n);
            map.needAnswer = false;
            map.unanswered -= 1;
        }
    }

    /**1. if has :Q, then stop and return true. Then during the saving,
     * ":Q" will be sliced out and not be stored.
     2. else, call findTarget*/
    public boolean process(String n) {
        for (int i = 0; i < n.length(); i++) {
            String curr = Character.toString(n.charAt(i));
            if (curr.equals(":")) {
                if ((i + 1) < n.length()) {
                    String next = Character.toString(n.charAt(i + 1));
                    if (next.equals("Q") || next.equals("q")) {
                        return true;
                    }
                }
            } else {
                findTarget(curr);
            }
        }
        return false;
    }
}
