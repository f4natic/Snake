package ru.fnt;

import ru.fnt.graphics.Canvas;

import javax.swing.*;
import java.awt.*;

public class Run {

    private JFrame frame;
    public static int SIDE = 300;

    public static void main(String[] args) {
        System.out.println("Initialize...");
        new Run().initialize();
    }

    public void initialize() {
        EventQueue.invokeLater(()->{
            frame = new JFrame();
            frame.setTitle("Snake");
            frame.setResizable(false);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);

            frame.add(new Canvas(this));
            frame.repaint();
        });
    }

    public void resize() {
        Insets insets = frame.getInsets();
        int w = SIDE + insets.left + insets.right;
        int h = SIDE + insets.top + insets.bottom + 25;
        Dimension dimension = new Dimension(w, h);
        frame.setSize(dimension);
    }
}
