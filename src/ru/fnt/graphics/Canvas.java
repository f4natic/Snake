package ru.fnt.graphics;

import ru.fnt.Run;
import ru.fnt.base.Cell;
import ru.fnt.base.Directions;
import ru.fnt.model.Food;
import ru.fnt.model.Snake;
import ru.fnt.util.Point;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;

public class Canvas extends JPanel implements ActionListener {

    private Snake snake;
    private Food food;
    private Timer timer;
    private int score = 0;
    private JLabel label;
    private int baseDelay = 500;
    private int delay = 500;
    private boolean paused = false;
    private Rectangle2D[][] field;

    public Canvas(Run run) {
        run.resize();
        setLayout(null);
        snake = new Snake();
        food = new Food();
        label = new JLabel();
        label.setText("Score:" + String.valueOf(score) + " Speed: " + ((100*(baseDelay-delay)/baseDelay))  + "%");
        label.setBounds(0, Run.SIDE+5, Run.SIDE, 15);
        add(label);

        timer = new Timer(delay, this);
        timer.start();

        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
            }

            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if(e.getKeyCode() == KeyEvent.VK_UP) {
                    snake.setDirections(Directions.UP);
                }
                if(e.getKeyCode() == KeyEvent.VK_DOWN) {
                    snake.setDirections(Directions.DOWN);
                }
                if(e.getKeyCode() == KeyEvent.VK_LEFT) {
                    snake.setDirections(Directions.LEFT);
                }
                if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    snake.setDirections(Directions.RIGHT);
                }
                if(e.getKeyCode() == KeyEvent.VK_SPACE) {
                    if(paused) {
                        timer.start();
                        paused = false;
                    }else {
                        timer.stop();
                        paused = true;
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
            }
        });

        field = new Rectangle2D[30][30];
        for(int i = 0; i < 300; i+=10) {
            for(int j = 0; j < 300; j+=10) {
                field[i/10][j/10] = new Rectangle2D.Double(i, j, 10, 10);
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.lightGray);
        for(int i = 0; i < 30; i++) {
            for(int j = 0; j < 30; j++) {
                g2.draw(field[i][j]);
            }
        }
        snake.draw(g2);
        food.draw(g2);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        snake.move();
        if(snake.absorb(food)) {
            delay = (int)(delay - Math.sqrt(delay / 2) * 1.1);
            timer.setDelay(delay);
            score = snake.snakeSize() * 5;
        }
        checkWorlBounds();
        label.setText("Score:" + String.valueOf(score) + " Speed: " + ((100*(baseDelay-delay)/baseDelay))  + "%");
        repaint();
    }

    public boolean checkWorlBounds() {
        Cell cell = snake.getHead();
        Point position = cell.getPosition();
        if(
                position.coordX < 5 || position.coordX > Run.SIDE - 5 ||
                        position.coordY < 5 || position.coordY > Run.SIDE - 5 ||
                        position.coordY < 5 || position.coordY > Run.SIDE - 5 ||
                        snake.selfIntersection()
        ) {
            JOptionPane.showConfirmDialog(null,
                    "You SCORE:"+score,
                    "END",
                    JOptionPane.CLOSED_OPTION,
                    JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        }
        return false;
    }
}