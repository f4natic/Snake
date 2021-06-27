package ru.fnt.model;

import ru.fnt.base.Cell;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import ru.fnt.base.Directions;
import ru.fnt.util.Rotation;
import ru.fnt.util.Point;

public class Snake {

    private List<Cell> snake;
    private List<Rotation> rotations;
    private Directions directions = Directions.RIGHT;

    public Snake() {
        snake = new ArrayList<>();
        rotations = new ArrayList<>();
        addCell();
        addCell();
        addCell();
    }

    public void addCell() {
        if(snake.isEmpty()) {
            Cell cell = new Cell();
            cell.setRectangle(new Point(55, 55));
            snake.add(cell);
        }
        Cell cell = new Cell();
        Point position = snake.get(snake.size()-1).getPosition();
        cell.setRectangle(new Point(position.coordX - 10, position.coordY));
        snake.add(cell);
    }

    public void move() {
        for(Cell c:snake) {
            Point p = c.getPosition();
            switch (directions){
                case UP: {
                    c.moveTo(new Point(p.coordX, p.coordY - 5));
                    break;
                }
                case DOWN: {
                    c.moveTo(new Point(p.coordX, p.coordY + 5));
                    break;
                }
                case LEFT: {
                    c.moveTo(new Point(p.coordX - 5, p.coordY));
                    break;
                }
                case RIGHT: {
                    c.moveTo(new Point(p.coordX + 5, p.coordY));
                    break;
                }
            }
        }
    }

    public void setDirections(Directions directions) {
        Cell c = snake.get(0);
        Point point = c.getPosition();
        rotations.add(new Rotation(point, directions));
        this.directions = directions;
    }

    public void draw(Graphics2D g) {
        for (Cell c : snake) {
            c.draw(g);
        }
    }
}