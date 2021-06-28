package ru.fnt.base;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import ru.fnt.util.Point;

public class Cell {

    private Rectangle2D rect = null;
    private Color color = null;
    private final int SIDE = 10;

    public void setRectangle(Point point) {
        double x = point.coordX - SIDE / 2;
        double y = point.coordY - SIDE / 2;
        rect = new Rectangle2D.Double(x, y, SIDE, SIDE);
    }

    public Rectangle2D getRectangle() {
        return rect;
    }

    public void setColor(int r, int g ,int b) {
        color = new Color(r, g, b);
    }

    public void moveTo(Point point) {
        double x = point.coordX - SIDE / 2;
        double y = point.coordY - SIDE / 2;
        rect = new Rectangle2D.Double(x, y, SIDE, SIDE);
    }

    public Point getPosition() {
        return new Point(rect.getCenterX(), rect.getCenterY());
    }

    public void draw(Graphics2D g) {
        if(rect == null) {
            return;
        }
        g.setColor(color);
        g.draw(rect);
        g.fill(rect);
    }

    public boolean intersects(Cell cell) {
        Rectangle2D rect2 = cell.getRectangle();
        if(rect.intersects(rect2)){
            return true;
        }
        return false;
    }
}