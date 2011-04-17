package com.github.andreytr.imagemapeditor.model.shapes;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.LinkedList;

/**
 * User: andreytr
 * Date: 26.03.2011
 * Time: 22:51:48
 */
public class RectangleShape extends AbstractShape {

    private int pointRadius = 5;
    private LinkedList<Point2D> points;
    private Point2D currentPoint;
    private boolean entered = false;

    public RectangleShape(int x, int y, int width, int height) {
        points = new LinkedList<Point2D>();
        points.addLast(new Point2D.Double(x, y));
        points.addLast(new Point2D.Double(x + width, y));
        points.addLast(new Point2D.Double(x + width, y + height));
        points.addLast(new Point2D.Double(x, y + height));
        currentPoint = points.get(2);
    }

    public RectangleShape(int x, int y) {
        this(x, y, 0, 0);
    }

    public int getX() {
        return (int)getRectangle(0).getX();
    }

    public int getY() {
        return (int)getRectangle(0).getY();
    }

    public int getWidth() {
        return (int)getRectangle(0).getWidth();
    }

    public int getHeight() {
        return (int)getRectangle(0).getHeight();
    }

    @Override
    public boolean findPoint(int x, int y) {
        entered = getRectangle(pointRadius).contains(x, y);
        for(Point2D point:points) {
            Rectangle2D.Double rect = getRectangleForPoint(point);
            if (rect.contains(x, y)) {
                currentPoint = point;
                return true;
            }
        }
        return false;
    }

    @Override
    public void setPoint(int x, int y) {
        currentPoint.setLocation(x, y);
        int i = points.indexOf(currentPoint);

        Point2D prev = points.get(i - 1 < 0 ? points.size() -1: i - 1);
        Point2D next = points.get(i + 1 >= points.size() ? 0 : i + 1);

        if ( i % 2 == 0) {
            prev.setLocation(x, prev.getY());
            next.setLocation(next.getX(), y);
        }
        else {
            prev.setLocation(prev.getX(), y);
            next.setLocation(x, next.getY());
        }
    }

    @Override
    public boolean entered(int x, int y) {
        entered = getRectangle(pointRadius).contains(x, y);
        return entered;
    }

    @Override
    public void draw(Graphics2D g2, Color drawColor, Color fillColor) {
        Rectangle2D rect = getRectangle(0);

        g2.setColor(fillColor);
        g2.fill(rect);
        g2.setColor(drawColor);
        g2.draw(rect);
        
        if (entered) {
            for(Point2D r: points) {
                Rectangle2D.Double pointRect = getRectangleForPoint(r);

                g2.setColor(Color.WHITE);
                g2.fill(pointRect);
                g2.setColor(drawColor);
                g2.draw(pointRect);
            }
        }
    }

    private Rectangle2D getRectangle(int d) {
        Point2D first = points.get(0);
        Point2D second = points.get(2);

        double x = first.getX() > second.getX() ? second.getX() : first.getX();
        double y = first.getY() > second.getY() ? second.getY() : first.getY();
        double dx = Math.abs(first.getX() - second.getX());
        double dy = Math.abs(first.getY() - second.getY());
        return new Rectangle2D.Double(x - d, y - d, dx + 2*d, dy + 2*d);
    }

    private Rectangle2D.Double getRectangleForPoint(Point2D point) {
        return new Rectangle2D.Double(point.getX() - pointRadius, point.getY() - pointRadius, pointRadius * 2, pointRadius * 2);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RectangleShape that = (RectangleShape) o;

        if (entered != that.entered) return false;
        if (currentPoint != null ? !currentPoint.equals(that.currentPoint) : that.currentPoint != null) return false;
        if (points != null ? !points.equals(that.points) : that.points != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = points != null ? points.hashCode() : 0;
        result = 31 * result + (currentPoint != null ? currentPoint.hashCode() : 0);
        result = 31 * result + (entered ? 1 : 0);
        return result;
    }
}
