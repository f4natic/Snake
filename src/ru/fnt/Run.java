package ru.fnt;

import ru.fnt.graphics.Canvas;

import javax.swing.*;
import java.awt.*;

public class Run {

    private JFrame frame;
    private static int WIDTH = 300;
    private static int HEIGHT = 300;

    public static void main(String[] args) {
        System.out.println("Initialize...");
        new Run().initialize();
    }

    public void initialize() {
        EventQueue.invokeLater(()->{
            frame = new JFrame();
            frame.setTitle("Snake");
//            frame.setResizable(false);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);

            frame.add(new Canvas(this));
            frame.repaint();
        });
    }

    public void resize() {
        Insets insets = frame.getInsets();
        int w = WIDTH + insets.left + insets.right;
        int h = HEIGHT + insets.top + insets.bottom;
        Dimension dimension = new Dimension(w, h);
        frame.setSize(dimension);
    }
}
