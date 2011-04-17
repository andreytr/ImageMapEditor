package com.github.andreytr.imagemapeditor.view;

import com.github.andreytr.imagemapeditor.model.MapItem;
import com.github.andreytr.imagemapeditor.model.MapModel;
import com.github.andreytr.imagemapeditor.model.shapes.AbstractShape;
import com.github.andreytr.imagemapeditor.model.shapes.RectangleShape;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Observable;
import java.util.Observer;

/**
 * User: andreytr
 * Date: 26.03.2011
 * Time: 22:34:53
 */
public class MapPanel extends JPanel implements Observer {

    private MapModel model;
    private Image image;

    private AbstractShape currentShape;

    public MapPanel(Image image, MapModel mapModel) {
        this.model = mapModel;
        this.image = image;
        setPreferredSize(new Dimension(image.getWidth(null), image.getHeight(null)));

        MouseListener mouseListener = new MouseListener();
        addMouseListener(mouseListener);
        addMouseMotionListener(mouseListener);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Color fillColor = new Color(0, 0, 25, 50);
        Color drawColor = Color.BLUE;

        Graphics2D g2 = (Graphics2D)g;
        g2.setColor(Color.WHITE);
        g2.fill(new Rectangle.Double(0.0, 0.0, getWidth(), getHeight()));
        g2.drawImage(image, 0, 0, null);

        g2.setColor(Color.BLUE);
        g2.setStroke(new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        for(MapItem mapItem: model.getItems()) {
            mapItem.getShape().draw(g2, drawColor, fillColor);
        }

        if (currentShape != null) {
            currentShape.draw(g2, drawColor, fillColor);
        }
    }

    private AbstractShape createShape(int x, int y) {
        return new RectangleShape(x, y);
    }

    private class MouseListener extends MouseAdapter {
        private AbstractShape entered = null;
        boolean findExisted = false;

        @Override
        public void mouseClicked(MouseEvent e) {
            //double left click on shape
            if (e.getButton() == MouseEvent.BUTTON1 && entered != null && e.getClickCount() > 1) {
                MapItem mapItem = model.getItem(entered);
                String link = getShapeURL(mapItem.getLink());
                if (link != null) {
                    mapItem.setLink(link);
                    model.setChanged();
                }
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
            //right lick on shape
            if (e.getButton() == MouseEvent.BUTTON3 && entered != null) {
                model.removeShape(entered);
            }
            //left click
            else if (e.getButton() == MouseEvent.BUTTON1) {
                for(AbstractShape shape: model.getShapeList()) {
                    if (shape.findPoint(e.getX(), e.getY())) {
                        currentShape = shape;
                        findExisted  = true;
                    }
                }

                if (currentShape == null && entered == null) {
                    findExisted = false;
                    currentShape = createShape(e.getX(), e.getY());
                }
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if (currentShape != null) {
                if (findExisted == false) {
                    String s = getShapeURL("");
                    if (s != null) {
                        model.addItem(new MapItem(s, "", currentShape, false));
                    }
                }
                else {
                    model.setChanged();
                }
            }
            currentShape = null;
            MapPanel.this.repaint();
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            entered = null;
            for(AbstractShape shape: model.getShapeList()) {
                shape.entered(-1, -1);
            }
            for(AbstractShape shape: model.getShapeList()) {
                if (shape.entered(e.getX(), e.getY())) {
                    entered = shape;
                    break;
                }
            }
            setToolTipText(model.getLink(entered));
            MapPanel.this.repaint();
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            if (currentShape != null) {
                currentShape.setPoint(e.getX(), e.getY());
            }
            MapPanel.this.repaint();
        }
        
    }

    public void update(Observable o, Object arg) {
        repaint();
    }

    public String getShapeURL(String url) {
        String s = JOptionPane.showInputDialog(this.getRootPane(), "Enter page url", url);
        if (s != null && s.trim().equals("")) {
            JOptionPane.showMessageDialog(this.getRootPane(), "Url must be not empty!", "Warning", JOptionPane.WARNING_MESSAGE);
            return null;
        }
        return s;
    }
}
