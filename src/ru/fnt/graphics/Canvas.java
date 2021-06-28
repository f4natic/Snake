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

public class Canvas extends JPanel implements ActionListener {

    private Snake snake;
    private Food food;
    private Timer timer;
    private int delay = 500;
    int i = 5;

    public Canvas(Run run) {
        run.resize();
        snake = new Snake();
        food = new Food();

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
            }

            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        snake.draw(g2);
        food.draw(g2);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        snake.move();
        snake.absorb(food, timer, delay);
        checkWorlBounds();
        repaint();
    }

    public boolean checkWorlBounds() {
        Cell cell = snake.getHead();
        Point position = cell.getPosition();
        if(
                position.coordX < 5 || position.coordX > Run.WIDTH - 5 ||
                        position.coordY < 5 || position.coordY > Run.HEIGHT - 5 ||
                        snake.selfIntersection()
        ) {
            JOptionPane.showConfirmDialog(null,
                    "Вы проирали!",
                    "=(",
                    JOptionPane.CLOSED_OPTION,
                    JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
        return false;
    }
}