package byow.Core;



public class Room {
    Position leftMiddle;
    Position rightMiddle;
    Position topMiddle;
    Position bottomMiddle;
    Position posi;

    Room(Position leftBottom, int width, int height) {
        this.leftMiddle = new Position(leftBottom.x, leftBottom.y + height / 2);
        this.rightMiddle = new Position(leftBottom.x + width - 1, leftBottom.y + height / 2);
        this.topMiddle = new Position(leftBottom.x + width / 2, leftBottom.y + height - 1);
        this.bottomMiddle = new Position(leftBottom.x + width / 2, leftBottom.y);
        this.posi = leftBottom;
    }

  //change the input hashSet(actually is always empty)
  // by adding all positions into the set
    public void allMiddle(Room theRoom, Position[] result) {
        result[0] = theRoom.leftMiddle;
        result[1] = theRoom.rightMiddle;
        result[2] = theRoom.topMiddle;
        result[3] = theRoom.bottomMiddle;
    }

}
