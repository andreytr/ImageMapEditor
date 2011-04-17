package com.github.andreytr.imagemapeditor.wiki;

import com.github.andreytr.imagemapeditor.model.MapItem;
import com.github.andreytr.imagemapeditor.model.shapes.AbstractShape;
import com.github.andreytr.imagemapeditor.model.shapes.RectangleShape;

import java.util.List;

/**
 * User: andreytr
 * Date: 27.03.2011
 * Time: 22:53:37
 */
public class ConfluenceMapEngine extends AbstractEngine {

    private String pageName;
    private String imageName;

    public ConfluenceMapEngine(String pageName, String imageName) {
        this.pageName = pageName;
        this.imageName = imageName;
    }

    @Override
    public String getImageMapText(List<MapItem> mapItems) {
        StringBuilder builder = new StringBuilder();
        builder.append("{imagemap:name=[" + pageName + "^" + imageName + "]}\n");
        for(MapItem mapItem: mapItems) {
            builder.append("    " + getItem(mapItem) + "\n");
        }
        builder.append("{imagemap}");
        return builder.toString();
    }

    private String getItem(MapItem mapItem) {
        return "{map:link=" + getLink(mapItem) +
                   "|shape=" + getShapeName(mapItem.getShape()) +
                   "|coords=" + getCoordinate(mapItem.getShape()) +
               "}";
    }

    private String getLink(MapItem mapItem) {
        String s = mapItem.getLink();
        if (s == null) {
            return "";
        }
        return s.startsWith("http://") || s.startsWith("https://") ? s: "[" + s + "]";
    }

    private String getCoordinate(AbstractShape shape) {
        if (shape instanceof  RectangleShape) {
            RectangleShape rect = (RectangleShape)shape;
            return rect.getX() + "," + rect.getY() + "," + (rect.getX() + rect.getWidth()) + "," + (rect.getY() + rect.getHeight());    
        }
        return "unknown";
    }

    private String getShapeName(AbstractShape shape) {
        if (shape instanceof RectangleShape) {
            return "rect";
        }
        return "unknown";
    }
}
