package com.github.andreytr.imagemapeditor.model;

import com.github.andreytr.imagemapeditor.model.shapes.AbstractShape;

/**
 * User: andreytr
 * Date: 27.03.2011
 * Time: 22:48:23
 */
public class MapItem {

    private String link;
    private String title;
    private AbstractShape shape;
    private boolean blankTarget;

    public MapItem(String link, String title, AbstractShape shape, boolean blankTarget) {
        this.link = link;
        this.title = title;
        this.shape = shape;
        this.blankTarget = blankTarget;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLink() {
        return link;
    }

    public String getTitle() {
        return title;
    }

    public AbstractShape getShape() {
        return shape;
    }

    public boolean isBlankTarget() {
        return blankTarget;
    }

    @Override
    public String toString() {
        return title != null ? title: link;
    }
}
