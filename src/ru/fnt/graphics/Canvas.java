package ru.fnt.graphics;

import ru.fnt.controller.Controller;
import ru.fnt.Run;
import ru.fnt.base.Cell;
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
    private JLabel infoLabel;
    private int baseDelay = 500;
    private int delay = baseDelay;
    private boolean paused = false;
    private boolean keyLock = false;
    private Rectangle2D[][] field;

    private Controller controller;

    public Canvas(Run run) {
        run.resize();
        setLayout(null);
        timer = new Timer(delay, this);
        controller = new Controller(timer, this);
        snake = new Snake(controller);
        food = new Food();
        infoLabel = new JLabel();
        infoLabel.setText("Score:" + score + " Speed: " + ((100*(baseDelay-delay)/baseDelay))  + "%");
        infoLabel.setBounds(0, Run.SIDE+5, Run.SIDE, 15);
        add(infoLabel);

        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        addKeyListener(controller);

        field = new Rectangle2D[30][30];
        for(int i = 0; i < 300; i+=10) {
            for(int j = 0; j < 300; j+=10) {
                field[i/10][j/10] = new Rectangle2D.Double(i, j, 10, 10);
            }
        }

        timer.start();
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
            delay = (int)(delay - (Math.sqrt(delay / 2D) * 1.1)/delay);
            timer.setDelay(delay);
            score = snake.snakeSize() * 5 - 2 * 5;
        }
        checkWorlBounds();
        infoLabel.setText("Score:" + score + " Speed: " + ((100*(baseDelay-delay)/baseDelay))  + "%");
        repaint();
    }

    public boolean checkWorlBounds() {
        Cell cell = snake.getHead();
        Point position = cell.getPosition();
        if(
            position.coordX < 5 || position.coordX > Run.SIDE - 5 ||
            position.coordY < 5 || position.coordY > Run.SIDE - 5 ||
            snake.selfIntersection()
        ) {
            JOptionPane.showConfirmDialog(null,
                    "You SCORE:"+score,
                    "END",
                    JOptionPane.CANCEL_OPTION);
            System.exit(0);
        }
        return false;
    }
}