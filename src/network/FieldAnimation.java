package network;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static java.lang.Math.sqrt;
import static network.Network.LAYERS_NUMBER;

public class FieldAnimation {

    /**
     * @param args the command line arguments
     */
    public TestPane testPane;
    static public boolean terminateInstance;


    public static void main(String[] args) {
        new FieldAnimation();
    }

    public FieldAnimation() {

        terminateInstance = false;

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
//                try {
//                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
//                    ex.printStackTrace();
//                }

                JFrame frame = new JFrame("Snake");
                testPane = new TestPane();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.add(testPane);

                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
        return;
    }

    public static class TestPane extends JPanel {

        static int x = 0;
        static int y = 0;

        int maxAllowedMoves = 100;
        Field field = new Field();
        static Snake snake = new Snake();

        boolean gameOver = false;
        double timeElapsed = 0d;
        double fitness = 0d;

        public TestPane() {
            Timer timer = new Timer(Field.SPEED_RATE, null);
            timer.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    snakeControlListening();
                    snake.moveSnake();

                    if (snake.snakeOutOfField() || snake.snakeHitSnake() || maxAllowedMoves == 0) {// TODO snake hitting snake
                        System.out.println(":::::::::::::::::::::::::KONIEC:::::::::::::::::::::::::");
                        gameOver = true;
                        FieldAnimation.terminateInstance = true;
                        timer.stop();
                        return;
                    }

                    if (snake.ifEatingTime(field)) {
                        snake.eatFruit();
                    }

                    field.updateSnakeOnField(snake);

                    printTxtField();
                 //   Network.network.

                    if (Field.fruitNo == 0) { // SNAKE HAS EATEN THE FRUIT - REPLENISH
                        System.out.println("Dodaję owoc...");
                        field.addFruit(snake);
                    }

                    timeElapsed += timer.getDelay();
                    fitness = snake.height/sqrt(sqrt(timeElapsed/Field.SPEED_RATE)) - maxAllowedMoves;
                            System.out.println("SNAKE HEIGHT: " + snake.height + " TIME: " + timeElapsed/Field.SPEED_RATE + " FITNESS: "+fitness);

                    repaint();
                    maxAllowedMoves--;
                }
            });
            if (!gameOver)
                timer.start();
            else {
                timer.stop();
                return;
            }
        }

        private void printTxtField() {
            System.out.println("\n");
            for (int i = 0; i < field.fieldBrick.length; i++) {
                for (int j = 0; j < field.fieldBrick[i].length; j++) {
                    if (field.fieldBrick[i][j].content == In.empty)
                        System.out.print("O ");
                    if (field.fieldBrick[i][j].content == In.fruit)
                        System.out.print("F ");
                    if (field.fieldBrick[i][j].content == In.snake)
                        System.out.print("X ");
                }
                System.out.println();
            }
            System.out.println("\n");
        }

        public void setSnakeControl() {}
        protected void snakeControlListening() {


            Network.network.countOutput(Network.network.neuron, field.fieldBrick);

            System.out.println("======= out[0]: "+Network.output[0]+" out[1]: "+Network.output[1]+" out[2]: "+Network.output[2]+" out[3]: "+Network.output[3]);

          // [0] RIGHT
                if((Network.output[0] > Network.output[1] &&
                        Network.output[0] > Network.output[2] &&
                        Network.output[0] > Network.output[3] ) && (snake.direction != Direction.LEFT)) {
                    snake.direction = Direction.RIGHT;
                    System.out.println("======= Dir: RIGHT");
                }

                // [1] LEFT
            if((Network.output[1] > Network.output[0] &&
                    Network.output[1] > Network.output[2] &&
                    Network.output[1] > Network.output[3] ) && (snake.direction != Direction.RIGHT)) {
                snake.direction = Direction.LEFT;
                System.out.println("======= Dir: LEFT");
            }

            // [2] UP
            if((Network.output[2] > Network.output[1] &&
                    Network.output[2] > Network.output[0] &&
                    Network.output[2] > Network.output[3] ) && (snake.direction != Direction.DOWN)) {
                snake.direction = Direction.UP;
                System.out.println("======= Dir: UP");
            }

            // [3] DOWN
            if((Network.output[3] > Network.output[1] &&
                    Network.output[3] > Network.output[2] &&
                    Network.output[3] > Network.output[0] ) && (snake.direction != Direction.UP)) {
                snake.direction = Direction.DOWN;
                System.out.println("======= Dir: DOWN");
            }


       /*     if ((MyPanel.c == 'l') && (snake.direction != Direction.LEFT))
                snake.direction = Direction.RIGHT;

            if ((MyPanel.c == 'j') && (snake.direction != Direction.RIGHT)) {
                snake.direction = Direction.LEFT;
            }

            if ((MyPanel.c == 'i') && (snake.direction != Direction.DOWN)) {
                snake.direction = Direction.UP;
            }

            if ((MyPanel.c == 'k') && (snake.direction != Direction.UP)) {
                snake.direction = Direction.DOWN;
            } */
            System.out.println("Kierunek: "+snake.direction.toString());



        }


        @Override
        public Dimension getPreferredSize() {
            return new Dimension(Field.FIELD_WIDTH, Field.FIELD_HEIGHT);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setColor(Color.getHSBColor((float) Math.random() * 255, 100, 100));

//           for(int i=0;i<snake.height;i++) {
//               g2d.fillRect(   snake.part[i].x*FieldBrick.BRICK_WIDTH,
//                               snake.part[i].y*FieldBrick.BRICK_WIDTH,
//                               FieldBrick.BRICK_WIDTH,
//                               FieldBrick.BRICK_WIDTH);
//           }

            for (int i = 0; i < field.fieldBrick.length; i++) {

                for (int j = 0; j < field.fieldBrick[i].length; j++) {

                    if (field.fieldBrick[i][j].content == In.snake) {
                        g2d.fillRect(j * FieldBrick.BRICK_WIDTH, // było odwrotnioe i j
                                i * FieldBrick.BRICK_WIDTH,
                                FieldBrick.BRICK_WIDTH,
                                FieldBrick.BRICK_WIDTH);
                    }
                }
            }

            g2d.setColor(Color.black);

            for (int i = 0; i < field.fieldBrick.length; i++) {

                for (int j = 0; j < field.fieldBrick[i].length; j++) {

                    if (field.fieldBrick[i][j].content == In.fruit) {
                        g2d.fillRect(j * FieldBrick.BRICK_WIDTH, // było odwrotnie: j <-> i
                                i * FieldBrick.BRICK_WIDTH,
                                FieldBrick.BRICK_WIDTH,
                                FieldBrick.BRICK_WIDTH);
                    }
                }
            }
            g2d.dispose();
        }

    }

}