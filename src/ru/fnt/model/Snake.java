package ru.fnt.model;

import ru.fnt.base.Cell;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import ru.fnt.base.Directions;
import ru.fnt.util.Point;

import javax.swing.*;

public class Snake {

    private List<Cell> snake;
    private Directions directions = Directions.RIGHT;

    public Snake() {
        snake = new ArrayList<>();
        addCell();
        addCell();
    }

    public Cell getHead() {
        return snake.get(0);
    }

    public void addCell() {
        if(snake.isEmpty()) {
            Cell cell = new Cell();
            cell.setColor(0, 255, 0);
            cell.setRectangle(new Point(15, 15));
            snake.add(cell);
        }else {
            Cell cell = new Cell();
            cell.setColor(128, 128, 128);
            Point position = snake.get(snake.size()-1).getPosition();
            cell.setRectangle(position);
            snake.add(cell);
        }
    }

    public void move() {
        Cell head = snake.get(0);
        Point lastHeadPos = head.getPosition();
        move(snake.get(0), directions);
        for(int i = 1; i < snake.size(); i++) {
            Cell c = snake.get(i);
            Point lastBodyPos = c.getPosition();
            c.moveTo(lastHeadPos);
            lastHeadPos = lastBodyPos;
        }
    }

    private void move(Cell c, Directions directions) {
        Point p = c.getPosition();
        switch (directions){
            case UP: {
                c.moveTo(new Point(p.coordX, p.coordY - 10));
                break;
            }
            case DOWN: {
                c.moveTo(new Point(p.coordX, p.coordY + 10));
                break;
            }
            case LEFT: {
                c.moveTo(new Point(p.coordX - 10, p.coordY));
                break;
            }
            case RIGHT: {
                c.moveTo(new Point(p.coordX + 10, p.coordY));
                break;
            }
        }
    }

    public void setDirections(Directions directions) {
        if(
                (this.directions == Directions.RIGHT && directions == Directions.LEFT) ||
                        (this.directions == Directions.LEFT && directions == Directions.RIGHT) ||
                        (this.directions == Directions.UP && directions == Directions.DOWN) ||
                        (this.directions == Directions.DOWN && directions == Directions.UP)
        ) {
            return;
        }
        this.directions = directions;
    }

    public boolean absorb(Food food) {
        if(food.isEatable()) {
            Cell h = snake.get(0);
            Cell f = food.getCell();
            if(h.intersects(f)) {
                food.toggle();
                addCell();
                food.setNewRandPosition(snake);
                food.toggle();
                return true;
            }
        }
        return false;
    }

    public boolean selfIntersection() {
        for(int i = 1; i < snake.size(); i++) {
            Cell c = snake.get(i);
            if(c.intersects(snake.get(0))){
                return true;
            }
        }
        return false;
    }

    public int snakeSize() {
        return snake.size() - 2;
    }

    public void draw(Graphics2D g) {
        for (Cell c : snake) {
            c.draw(g);
        }
    }
}