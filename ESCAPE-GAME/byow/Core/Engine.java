package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;


import java.awt.*;
import java.io.File;


import java.util.Arrays;
import java.util.Random;



public class Engine {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;
    //public static final File CWD = new File(System.getProperty("user.dir"));
    public static final File RECORD = new File("record.txt");



    public static void main(String[] args) {
    /**
    Engine one = new Engine();
    TETile[][] first = one.interactWithInputString("n4500510816322537991saas");
    Engine two = new Engine();
    TETile[][] second = two.interactWithInputString("n4500510816322537991saas");
    System.out.println(first.equals(second));
     */
        Engine engine = new Engine();
        TERenderer ter = new TERenderer();
        ter.initialize(80, 30);

        String x = "n4500510816322537991s";
        GenerateWorld map1 = engine.giveMap(x);
        GenerateWorld map2 = engine.giveMap(x);

        boolean result = Arrays.deepEquals(map1.world, map2.world);
        for (int i = 0; i < WIDTH; i++) {
            boolean hi = Arrays.equals(map1.world[i], map2.world[i]);
            if (!hi) {
                System.out.println(i);
                for (int j = 0; j < HEIGHT; j++) {
                    boolean y = map1.world[i][j].equals(map2.world[i][j]);
                    if (!y) {
                        System.out.println(j);
                        System.out.println(map1.world[i][j].description());
                        map1.world[i][j] = Tileset.FLOWER;
                    }

                }
            }
        }
        //TETile[][] world1 = engine.interactWithInputString(x);
        //TETile[][] world2 = engine.interactWithInputString(x);
        //boolean result = Arrays.deepEquals(map1, map2);
        ter.renderFrame(map1.world);
        System.out.println(result);
    }



    private String checkName(Menu menu) {
        String name = "";
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char curr = StdDraw.nextKeyTyped();
                if (Character.toString(curr).equals(":")) {
                    break;
                } else {
                    name += Character.toString(curr);
                    menu.name(name);
                }
            }
        }
        //System.out.println(name);
        return name;
    }






    /**
     * Method used for exploring a fresh world. This method should handle all inputs,
     * including inputs from the main menu.
     */
    public void interactWithKeyboard() {
        //find the seed and generate new world
        ter.initialize(WIDTH, HEIGHT);

        //Draw the Starting Menu
        Menu menu = new Menu(WIDTH, HEIGHT);
        menu.headings("Null");

        String seedString = "";
        boolean haveLoad = false;
        String userName = "";
        GenerateWorld map = null;
        String save = null;

        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char curr = StdDraw.nextKeyTyped();
                if (Character.toString(curr).equals("E") || Character.toString(curr).equals("e")) {
                    menu.name("");
                    userName = checkName(menu);
                    menu.headings(userName);
                } else if ((Character.toString(curr).equals("L")
                        || Character.toString(curr).equals("l"))) {
                    if (!RECORD.exists()) {
                        return;
                    } else {
                        haveLoad = true;
                        save = Utils.readContentsAsString(RECORD);
                        //System.out.println(save);
                        map = giveMap(save);
                        break;
                    }
                } else if ((Character.toString(curr).equals("N")
                        || Character.toString(curr).equals("n"))) {
                    menu.seed("");
                } else {
                    if (!(Character.toString(curr).equals("S")
                            || Character.toString(curr).equals("s"))) {
                        seedString += Character.toString(curr);
                        menu.seed(seedString);
                    } else {
                        break;
                    }
                }
            }
        }
        //System.out.println(seedString);
       //Generate the World
        Random random;
        if (!haveLoad) {
            long seed = Long.parseLong(seedString);
            random = new Random(seed);
            map = createWorld(random, userName);
            ter.renderFrame(map.world);
        }

        //playerName(userName);
        //for test use
        /**
        long seed = Long.parseLong("12345");
        Random random = new Random(seed);
        GenerateWorld map = createWorld(random);
        */
        //allow movement
        Movement x = new Movement(map);
        String action;
        if (haveLoad) {
            action = save;
        } else {
            action = "E" + userName +  ":" + "N" + seedString + "S";

        }
        processAction(action, map, x, menu);

    }

    /**
     * Process WASD for keyboard
     * */
    private void processAction(String action, GenerateWorld map,
                               Movement x, Menu menu) {
        TimeWatch time = new TimeWatch(map);
        while (true) {
            //Apply Heading
            applyHeading(map);
            if (map.startTime) {
                map.startTime = false;
                time.startWatch(10);
            }
            if (StdDraw.hasNextKeyTyped()) {
                char curr = StdDraw.nextKeyTyped();
                if (!Character.toString(curr).equals(":")) {
                    x.process(Character.toString(curr));
                    action += curr;
                    if (map.needAnswer) {
                        while (true) {
                            if (StdDraw.hasNextKeyTyped()) {
                                char answer = StdDraw.nextKeyTyped();
                                //System.out.println(answer);
                                if ((Character.toString(answer).equals("1")
                                        || Character.toString(answer).equals("2")
                                        || Character.toString(answer).equals("3"))) {
                                    map.myQuiz.checkAnswer(map, Character.toString(answer));
                                    action += answer;
                                    map.unanswered -= 1;
                                    map.needAnswer = false;
                                    headingOnce(map);
                                    ter.renderFrame(map.currVersion());
                                    if (map.unanswered == 0) {
                                        map.extraTime = menu.bonusTime(map.score);
                                    }
                                    if (map.extraTime) {
                                        extra(map, map.myRandom);
                                    }
                                    break;
                                }
                            }
                        }
                    }
                    if (map.time == 0 && map.fixed) {
                        menu.finalCheck(map.score);
                    }
                    applyHeading(map);
                    ter.renderFrame(map.currVersion());
                } else {
                    while (true) {
                        if (StdDraw.hasNextKeyTyped()) {
                            char quit = StdDraw.nextKeyTyped();
                            try {
                                if (!(Character.toString(quit).equals("Q")
                                        ||  Character.toString(quit).equals("q"))) {
                                    break;
                                } else {
                                    Utils.writeContents(RECORD, action);
                                    throw new IndexOutOfBoundsException("LET'S QUIT");
                                }
                            } catch (IndexOutOfBoundsException exception) {
                                System.exit(0);
                            }
                        }
                    }
                }
            }
        }

    }



    /**
     * Method used for autograding and testing your code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The engine should
     * behave exactly as if the user typed these characters into the engine using
     * interafctWithKeyboard.
     *
     * Recall that strings ending in ":q" should cause the game to quite save. For example,
     * if we do interactWithInputString("n123sss:q"), we expect the game to run the first
     * 7 commands (n123sss) and then quit and save. If we then do
     * interactWithInputString("l"), we should be back in the exact same state.
     *
     * In other words, both of these calls:
     *   - interactWithInputString("n123sss:q")
     *   - interactWithInputString("lww")
     *
     * should yield the exact same world state as:
     *   - interactWithInputString("n123sssww")
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */


    /**Split the userName from the rest*/
    private int countColon(String input) {
        int count = 0;
        if (input.charAt(0) == ("E").charAt(0) || input.charAt(0) == ("e").charAt(0)) {
            for (int i = 0; i < input.length(); i++) {
                if (Character.toString(input.charAt(i)).equals(":")) {
                    //System.out.println(input.charAt(i));
                    break;
                } else {
                    count += 1;
                    Character s = input.charAt(i);
                    //System.out.println(s);
                }
            }
        }
        return count;
    }
    public TETile[][] interactWithInputString(String input) {

        // passed in as an argument, and return a 2D tile representation of the
        // world that would have been drawn if the same inputs had been given
        // to interactWithKeyboard().
        //
        // See proj3.byow.InputDemo for a demo of how you can make a nice clean interface
        // that works for many different input types.
        GenerateWorld map = giveMap(input);
        if (map != null) {
            return map.currVersion();
        } else {
            return null;
        }
    }

    private GenerateWorld giveMap(String input) {
        //ter.initialize(WIDTH, HEIGHT);
        String myInput = "";
        String name = "";

        //collect the name
        if (input.charAt(0) == ("E").charAt(0) || input.charAt(0) == ("e").charAt(0)) {
            for (int i = 1; i < input.length(); i++) {
                if (Character.toString(input.charAt(i)).equals(":")) {
                    myInput = input.substring(i + 1);
                    break;
                } else {
                    name += Character.toString(input.charAt(i));
                }
            }
            //check it's loading
        } else if ((input.charAt(0) == ("L").charAt(0)) || input.charAt(0) == ("l").charAt(0)) {
            if (!RECORD.exists()) {
                System.exit(0);
                return null;
            }
            String trueInput = Utils.readContentsAsString(RECORD);
            int indexOfColon = countColon(trueInput);
            if (indexOfColon != 0) {
                name = trueInput.substring(1, indexOfColon);
                myInput = trueInput.substring(indexOfColon + 1) + input.substring(1);
            } else {
                myInput = trueInput + input.substring(1);
            }
        } else {
            myInput = input;
        }

        //collect the seed string
        int len = myInput.length();
        String numString = "";
        int count = 1;
        for (int i = 1; i < len; i++) {
            if (!(myInput.charAt(i) == ("S").charAt(0)
                    || myInput.charAt(i) == ("s").charAt(0))) {
                numString += myInput.charAt(i);
                count += 1;
            } else {
                break;
            }
        }

        //collect the action string
        String action = myInput.substring(count + 1, len);

        //create the map of rooms & hallways & avatar
        long seed = Long.parseLong(numString);
        Random random = new Random(seed);
        GenerateWorld map = createWorld(random, name);

        //Process movement strings
        Movement x = new Movement(map);
        boolean hasQue = x.process(action);
        if (hasQue) {
            String toWrite = myInput.substring(0, len - 2);
            Utils.writeContents(RECORD, "E" + name + ":" + toWrite);
        }
        //ter.renderFrame(map.currVersion());
        //applyHeading(map);
        return map;

    }


    public GenerateWorld createWorld(Random random, String userName) {
        GenerateWorld map = new GenerateWorld(WIDTH, HEIGHT, userName, random);
        map.fillBoardWithNothing();
        map.createRooms(random);
        map.createHallways();
        map.createAvatar();
        map.createFlower(random);
        return map;
    }

    private void applyHeading(GenerateWorld map) {
        //draw the HUD
        MousePointer pointer = new MousePointer(map);
        Position before = pointer.mouseDetect();
        ter.renderFrame(map.currVersion());
        int prev = 1000;
        if (before.inRange(map)) {
            pointer.drawUID(before);
        }

        //update if there's any movement
        while (!StdDraw.hasNextKeyTyped()) {
            Position curr = pointer.mouseDetect();
            /**
             if (map.fixed) {
             if (map.time != 0) {
             StdDraw.pause(1000);
             map.time -= 1;
             } else {
             Menu x = new Menu(WIDTH, HEIGHT);
             x.finalCheck(map.score);
             }
             } */
            if (!map.fixed) {
                if ((curr.inRange(map) && !before.same(curr))) {
                    ter.renderFrame(map.currVersion());
                    pointer.drawUID(curr);
                    before = curr;
                }
            } else {
                if (!(map.time == prev)) {
                    if ((curr.inRange(map) && !before.same(curr))) {
                        ter.renderFrame(map.currVersion());
                        pointer.drawUID(curr);
                        before = curr;
                    } else {
                        ter.renderFrame(map.currVersion());
                        pointer.drawUID(new Position(WIDTH - 1, HEIGHT - 1));
                    }
                }
            }
        }
    }



    private void headingOnce(GenerateWorld map) {
        //draw the HUD
        MousePointer pointer = new MousePointer(map);
        Position before = pointer.mouseDetect();
        ter.renderFrame(map.currVersion());
        if (before.inRange(map)) {
            pointer.drawUID(before);
        }
    }

    private void extra(GenerateWorld map, Random random) {
        map.createSand(random);
        map.sightVersion = true;
        map.fixed = true;
        map.time = 10;
        map.startTime = true;
    }



}
