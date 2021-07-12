package ru.fnt.model;

import ru.fnt.base.Cell;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import ru.fnt.base.Directions;
import ru.fnt.controller.Controller;
import ru.fnt.util.Point;

import javax.swing.*;

public class Snake {

    private List<Cell> snake;
    private Controller controller;
    private Directions direction = Directions.RIGHT;

    public Snake(Controller controller) {
        this.controller = controller;

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
            cell.setColor(Color.RED);
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
        Directions directions = controller.getDirection();
        if(
            direction == Directions.RIGHT && directions == Directions.LEFT ||
            direction == Directions.LEFT && directions == Directions.RIGHT ||
            direction == Directions.UP && directions == Directions.DOWN ||
            direction == Directions.DOWN && directions == Directions.UP
        ) {
            directions = direction;
        }
        Cell head = snake.get(0);
        Point lastHeadPos = head.getPosition();
        move(snake.get(0), directions);
        for(int i = 1; i < snake.size(); i++) {
            Cell c = snake.get(i);
            Point lastBodyPos = c.getPosition();
            c.moveTo(lastHeadPos);
            lastHeadPos = lastBodyPos;
        }
        direction = directions;
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
        return snake.size();
    }

    public void draw(Graphics2D g) {
        for (Cell c : snake) {
            c.draw(g);
        }
    }
}