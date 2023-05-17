package network;

public class Field {

    static int FIELD_WIDTH = 200;
    static int FIELD_HEIGHT = 200;
    static int SPEED_RATE = 200; // [ms] repaint interval
    static int FIELD_ROWS = FIELD_HEIGHT/FieldBrick.BRICK_WIDTH;
    static int FIELD_COLS = FIELD_WIDTH/FieldBrick.BRICK_WIDTH;
    static int FIELD_CELLS = (FIELD_ROWS * FIELD_COLS);

    static int fruitNo;

    FieldBrick fieldBrick [][];

    public static void main(String[] args) {
  //      fieldBrick[10] = new FieldBrick()

    }

    public Field() {
        fruitNo = 0;
        fieldBrick = new FieldBrick[(int)(FIELD_ROWS)][];

        for (int i=0; i<fieldBrick.length; i++) {
            fieldBrick[i] = new FieldBrick[FIELD_COLS];
            for(int j=0;j<fieldBrick[i].length;j++) {
                fieldBrick[i][j] = new FieldBrick();
            }
        }
    }

    public void addFruit(Snake snake){
        int j=0;
        while(j==0) {
            int p=(int)(Math.random()*FIELD_COLS);
            int r=(int)(Math.random()*FIELD_ROWS);
            if(fieldBrick[p][r].content == In.empty) {
                fieldBrick[p][r].content = In.fruit;
                System.out.println("Tworzę owoce... X: "+p+"; Y: "+r);
                this.fruitNo=1;
                j=1;
            }
        }
    }

    public void updateSnakeOnField(Snake snake){
        for(int i=0;i<this.fieldBrick.length;i++)
            for(int j=0;j<this.fieldBrick[i].length;j++)
                if (this.fieldBrick[i][j].content!=In.fruit)
                    this.fieldBrick[i][j].content = In.empty;
//
        for (int i=0;i<snake.part.length;i++) {
            if (!(snake.part[0].y<0||snake.part[0].y>=this.fieldBrick.length||snake.part[0].x<0||snake.part[0].x>=this.fieldBrick[0].length)) {
                if(snake.part[i].x!=-1) {
                    this.fieldBrick[snake.part[i].y][snake.part[i].x].content = In.snake;
                }
            }
        }
//        int i=0;
//        while((snake.part[i].x!=-1)) {
//                this.fieldBrick[snake.part[i].x][snake.part[i].y].content = In.snake;
//                i++;
//            this.fieldBrick[snake.part[i+1].x][snake.part[i+1].y].content = In.empty;


//        }
    }


}
