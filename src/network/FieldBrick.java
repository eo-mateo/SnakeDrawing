package network;

public class FieldBrick {
    int x;
    int y;
    static int BRICK_WIDTH = 10;

    public In content;

    public FieldBrick () {
        content = In.empty;
    }

    void clearBrick() {}
    void placeApple() {}
    void placeSnake() {}

}
