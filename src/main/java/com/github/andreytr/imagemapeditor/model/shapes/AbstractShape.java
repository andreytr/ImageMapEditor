package com.github.andreytr.imagemapeditor.model.shapes;

import java.awt.*;

/**
 * User: andreytr
 * Date: 26.03.2011
 * Time: 22:50:39
 */
abstract public class AbstractShape {

    abstract public void draw(Graphics2D g2, Color drawColor, Color fillColor);
    abstract public boolean findPoint(int x, int y);
    abstract public void setPoint(int x, int y);
    abstract public boolean entered(int x, int y);
}
