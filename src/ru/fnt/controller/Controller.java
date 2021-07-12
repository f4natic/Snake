package ru.fnt.controller;

import ru.fnt.Run;
import ru.fnt.base.Directions;
import ru.fnt.model.Snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Controller implements KeyListener {

    private boolean keyLock = false;
    private boolean paused = false;
    private Timer timer;
    private JPanel panel;
    private JLabel pauseLabel;
    private Directions direction;

    public Controller(Timer timer, JPanel panel) {
        this.timer = timer;
        this.panel = panel;

        direction = Directions.RIGHT;

        pauseLabel = new JLabel();
        pauseLabel.setBounds(Run.SIDE/2-Run.SIDE/4, Run.SIDE/2-25, Run.SIDE, 50);
        pauseLabel.setText("PAUSE");
        pauseLabel.setFont(new Font("SansSerif", Font.BOLD|Font.ITALIC, 36));
        pauseLabel.setForeground(Color.LIGHT_GRAY);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(keyLock) return;
        if(e.getKeyCode() == KeyEvent.VK_UP) {
            if(direction == Directions.DOWN) return;
            direction = Directions.UP;
            keyLock = true;
        }
        if(e.getKeyCode() == KeyEvent.VK_DOWN) {
            if(direction == Directions.UP) return;
            direction = Directions.DOWN;
            keyLock = true;
        }
        if(e.getKeyCode() == KeyEvent.VK_LEFT) {
            if(direction == Directions.RIGHT) return;
            direction = Directions.LEFT;
            keyLock = true;
        }
        if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if(direction == Directions.LEFT) return;
            direction = Directions.RIGHT;
            keyLock = true;
        }
        if(e.getKeyCode() == KeyEvent.VK_SPACE) {
            if(paused) {
                panel.remove(pauseLabel);
                timer.start();
                paused = false;
                panel.repaint();
            }else {
                panel.add(pauseLabel);
                timer.stop();
                paused = true;
                panel.repaint();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keyLock = false;
    }

    public Directions getDirection() {
        return direction;
    }

}
