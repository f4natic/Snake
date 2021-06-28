package ru.fnt.model;

import ru.fnt.base.Cell;
import ru.fnt.util.Point;

import java.awt.*;
import java.util.Random;
import java.util.List;

public class Food {
    private Cell food;
    private boolean isAte = true;
    private Random rnd;

    public Food() {
        food = new Cell();
        food.setColor(237, 28, 36);
        food.setRectangle(new Point(55, 55));
        rnd = new Random();
    }

    public void setPosition(Point point) {
        food.setRectangle(point);
    }

    public void toggle() {
        if(isAte) {
            isAte = false;
        }else {
            isAte = true;
        }
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
        int x = rnd.nextInt(296);
        int y =  rnd.nextInt(296);

        if(
                !(x % 5 == 0)&&(x % 10 == 0) &&
                !(x % 5 == 0)&&(x % 10 == 0)
        ){
            x = rnd.nextInt(296);
            y =  rnd.nextInt(296);
        }

        Point p = new Point(x, y);
        food.moveTo(p);

        for(Cell c : snake) {
            if(c.intersects(food)){
                setNewRandPosition(snake);
            }
        }
    }
}