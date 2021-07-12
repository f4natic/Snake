package ru.fnt.model;

import ru.fnt.Run;
import ru.fnt.base.Cell;
import ru.fnt.util.Point;

import java.awt.*;
import java.util.Random;
import java.util.List;

public class Food {
    private Cell food;
    private boolean isAte = true;
    private Random rnd;
    private int[] position;

    public Food() {
        food = new Cell();
        food.setColor(Color.GREEN);
        food.setRectangle(new Point(155, 155));
        food.setRectangle(new Point(155, 155));
        rnd = new Random();
        position = new int[Run.SIDE/10];
        for(int i = 0; i < position.length; i++) {
            if(i == 0) {
                position[i] = 5;
            }else {
                position[i] = position[i-1] + 10;
            }
        }
    }

    public void setPosition(Point point) {
        food.setRectangle(point);
    }

    public void toggle() {
        isAte = !isAte;
    }

    public boolean isEatable() {
        return isAte;
    }

    public void draw(Graphics2D g) {
        if(isAte){
            food.draw(g);
        }
    }

    public Cell getCell(){
        return food;
    }

    public void setNewRandPosition(List<Cell> snake) {
        Point p = new Point(position[rnd.nextInt(Run.SIDE/10)], position[rnd.nextInt(Run.SIDE/10)]);
        food.moveTo(p);
        for(Cell c : snake) {
            if(c.intersects(food)){
                setNewRandPosition(snake);
            }
        }
    }
}