package network;


public class Snake {

    int height;
    boolean living;
    boolean eating;
    Direction direction;


    public Part[] part;// = new Coordinates[Field.FIELD_CELLS];

    public Snake (){
        part = new Part[Field.FIELD_CELLS];
        for (int i =0; i< part.length;i++) {
            part[i] = new Part();
            part[i].x = -1;
            part[i].y = -1;
        }
        part[0].x = (int)(Field.FIELD_COLS/2);
        part[0].y = (int)(Field.FIELD_ROWS/2);
        part[1].x = part[0].x-1;
        part[1].y = part[0].y;
        part[2].x = part[0].x-2;
        part[2].y = part[0].y;

        this.direction = Direction.UP;
        this.eating = false;
        this.living = true;
        this.height = 3;

    }
    public static void main(String[] args) {
        Snake snake = new Snake();
    }
    public void moveSnake() {

        Part _part[] = createCachePart();

        // FIRST MOVES TOWARDS THE DIRECTION
        if (this.direction == Direction.UP)
            _part[0].y-=1;
        if (this.direction == Direction.DOWN)
            _part[0].y+=1;
        if (this.direction == Direction.LEFT)
            _part[0].x-=1;
        if (this.direction == Direction.RIGHT)
            _part[0].x+=1;

        // THE REST FOLLOWS
//        int i=1;
//        while((_part[i].x!=-1)) {
//            _part[i].x=part[i-1].x;
//            _part[i].y=part[i-1].y;
//            i++;
//        }
        int lastBelongingToSnake = 0;
        for (int j=1; j<part.length;j++) {
            if(_part[j].x!=-1) {
                _part[j].y=part[j-1].y;
                _part[j].x=part[j-1].x;
                lastBelongingToSnake = j;
            }
        }
        if(this.eating == true) { // if there's an eating, extra last part appears
            _part[lastBelongingToSnake+1].y = part[lastBelongingToSnake].y;
            _part[lastBelongingToSnake+1].x = part[lastBelongingToSnake].x;
            lastBelongingToSnake++;
            this.eating = false;
        }

        _part[lastBelongingToSnake+1].x=-1;
        _part[lastBelongingToSnake+1].y=-1;

        updateFromCachePart(_part);
    }

    private void updateFromCachePart(Part[] _part) {
        int i;
        for (i=0 ; i<part.length; i++) {
            part[i].x = _part[i].x;
            part[i].y = _part[i].y;
        }
    }

    private Part[] createCachePart() {
        Part _part[] = new Part[part.length];

        for (int i=0 ; i<part.length; i++) {
                _part[i] = new Part();
                _part[i].x = part[i].x;
                _part[i].y = part[i].y;
            }
        return _part;
    }

    public boolean ifEatingTime(Field field) {
//        if ((this.direction == Direction.LEFT)&&(part[0].x>0)) {
//            System.out.println("LEFT x>0");
         /*   if(field.fieldBrick[part[0].y][part[0].x].content==In.fruit) { // było x-1
                System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAMMMM!!!");
                return true;
            }
//        }
//        if ((this.direction == Direction.RIGHT)&&(part[0].x<Field.FIELD_COLS-1)) {
//            System.out.println("RIGHT x<max");
            if(field.fieldBrick[part[0].y][part[0].x].content==In.fruit) {// TO JEDYNIE DZIAŁA było x+1
                return true;
            }
//        }
//        if ((this.direction == Direction.DOWN)&&(part[0].y>0)) {
//            System.out.println("pole: "+(part[0].y)+" "+part[0].x+" = "+field.fieldBrick[part[0].y][part[0].x].content.toString());
            if(field.fieldBrick[part[0].y][part[0].x].content==In.fruit) { // było y-1
                return true;
            } */
//        }
//        if ((this.direction == Direction.UP)&&(part[0].y<Field.FIELD_ROWS-1)) {
//            System.out.println("UP y<max");

            if(!(part[0].y<0||part[0].y>=field.fieldBrick.length||part[0].x<0||part[0].x>=field.fieldBrick[0].length))
                if(field.fieldBrick[part[0].y][part[0].x].content==In.fruit) { // było y+1
                return true;
                }
//        }

        return false;
    }
    public void eatFruit() {


        // CONSUMED FRUIT BECOMES PART NO.0
//        if (this.direction == Direction.UP) {
//            _part[0].y-=1;
//        }
//        if (this.direction == Direction.DOWN) {
//            _part[0].y+=1;
//        }
//        if (this.direction == Direction.LEFT) {
//            _part[0].x-=1;
//        }
//        if (this.direction == Direction.RIGHT) {
//            _part[0].x+=1;
//        }

/*
        // MOVE REMAINING PARTS BY ONE
        int i=0;
        while((part[i].x!=-1)||(part[i].y!=-1)) {
            _part[i + 1].x = part[i].x;
            _part[i + 1].y = part[i].y;
            i++;
        } */

        Field.fruitNo--;
        this.height++;
        this.eating = true;

    };

    public boolean snakeOutOfField() {
        if(
                this.part[0].x<0
                        || this.part[0].x>Field.FIELD_COLS
                        || this.part[0].y<0
                        || this.part[0].y>Field.FIELD_ROWS )
            return true;

        else return false;
    }

    public boolean snakeHitSnake() {
        for(int i=1;i<this.part.length; i++) {
            if(       this.part[0].x==this.part[i].x
                    &&this.part[0].y==this.part[i].y) {
                return true;
            }
        }
        return false;
    }


}
