package byow.Core;

import byow.TileEngine.TETile;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;


public class MousePointer {
    Position p;
    TETile[][] world;
    int WIDTH;
    int HEIGHT;
    GenerateWorld map;


    MousePointer(GenerateWorld map) {
        this.world = map.world;
        this.WIDTH = map.WIDTH;
        this.HEIGHT = map.HEIGHT;
        this.map = map;
    }

    //1. Position of the mouse
    public Position mouseDetect() {
        double x = StdDraw.mouseX();
        double y = StdDraw.mouseY();
        this.p = new Position((int) x, (int) y);
        return p;
    }

    //Return description of the tile
    public String description(Position posi) {
        TETile mouse = map.currVersion()[posi.x][posi.y];
        return mouse.description();
    }

     /**1. Draw the tile type & the uerName
     2. show()
     */
    public void drawUID(Position curr) {
        Font font = new Font("Monaco", Font.BOLD, 15);
        StdDraw.setFont(font);
        StdDraw.setPenColor(Color.WHITE);
        String output = description(curr);
        StdDraw.text(4, HEIGHT - 1.25, output);
        if (map.haveName) {
            StdDraw.text(WIDTH - 10, HEIGHT - 1.25, "Your Name: " + map.userName);
        }
        String score = "Score: " + map.score;
        StdDraw.text(0.5 * WIDTH, HEIGHT - 1.25, score);
        String hint;
        if (!map.fixed) {
            hint = "Press 'y' to switch the view.";
        } else {
            hint = String.valueOf(map.time);
        }
        StdDraw.text(0.5 * WIDTH, HEIGHT - 3, hint);
        StdDraw.show();
        //StdDraw.pause(1000);
    }
}

