package byow.Core;

import java.util.Random;

public class Position {
    int x;
    int y;
/**
    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(80, 30);

        GenerateWorld map = new GenerateWorld(80, 30, "");
        long seed = Long.parseLong("588");
        Random random = new Random(seed);

        map.fillBoardWithNothing();
        map.createRooms(random);
        map.createHallways();

        ter.renderFrame(map.world);




         *  MousePointer pointer = new MousePointer(map);
         *         Position before = pointer.MouseDetect();
         *         //System.out.println(pointer.Description(new Position(0,0)));
        while(true){
            Position curr = pointer.MouseDetect();
                if(!before.Same(curr) && curr.inRange(map)) {
                    ter.renderFrame(map.world);
                    pointer.drawUID();
                    //System.out.println(curr);
                    //System.out.println(pointer.Description(curr));
                    before = curr;
                    break;
        }

    }

        MousePointer pointer = new MousePointer(map);
        //Position before = pointer.MouseDetect();
        while (!StdDraw.hasNextKeyTyped()) {
            Position curr = pointer.MouseDetect();
            /**if (map.haveName) {
             playerName(map.userName);
             ter.renderFrame(map.world);
             }
            if (curr.inRange(map)) {
                System.out.println("Curr's x is " + curr.x + " y is " + curr.y);
                //System.out.println("Before's x is " + before.x + " y is " + before.y);
                ter.renderFrame(map.world);
                pointer.drawUID(curr);
                //System.out.println(curr);
                //System.out.println(pointer.Description(curr));

            }
        }
    }

    */



    Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    //return a new position by changing x / y in the original position
    public Position shift(int dx, int dy) {
        //new position after shifting
        return new Position(this.x + dx, this.y + dy);
    }

    // change the input position into a freshly new Position
    // with randomly created X and Y. (according to RANDOM)
    public void randomPosi(int lowerX, int upperX,
                               int lowerH, int upperH, Random random, Position p) {
        double widX = random.nextDouble();
        double widY = lowerX + (upperX - lowerX) * widX;

        double heightX = random.nextDouble();
        double heightY = lowerH + (upperH - lowerH) * heightX;

        p.x = (int) widY;
        p.y = (int) heightY;

    }

    //Check if two positions are the same (used in drawing HUD)
    public boolean same(Position original, Position after) {
        return (original.x == after.x) && (original.y == after.y);
    }

    public boolean same(Position after) {
        return (this.x == after.x) && (this.y == after.y);
    }

    //Check if the position is within the map
    // (used in drawing HUD --> check mouse position)
    public boolean inRange(GenerateWorld map) {
        return (this.x < map.WIDTH) && (this.x >= 0) && (this.y < map.HEIGHT) && (this.y >= 0);
    }


}
