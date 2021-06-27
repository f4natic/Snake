package ru.fnt.graphics;

import ru.fnt.Run;
import ru.fnt.base.Directions;
import ru.fnt.model.Snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Canvas extends JPanel implements ActionListener {

    private Snake snake;

    public Canvas(Run run) {
        run.resize();
        snake = new Snake();

        Timer timer = new Timer(500, this);
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
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        snake.move();
        repaint();
    }
}
