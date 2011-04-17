package com.github.andreytr.imagemapeditor.model;

import com.github.andreytr.imagemapeditor.model.shapes.AbstractShape;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * User: andreytr
 * Date: 16.04.2011
 * Time: 17:00:01
 */
public class MapModel extends Observable {

    private List<MapItem> items;

    public MapModel() {
        items = new ArrayList<MapItem>();
    }

    public void addItem(MapItem mapItem) {
        items.add(mapItem);
        setChanged();
    }

    public void addAllItems(List<MapItem> itemList) {
        items.addAll(itemList);
        setChanged();
    }

    public List<MapItem> getItems() {
        return items;
    }

    public List<AbstractShape> getShapeList() {
        List<AbstractShape> result = new ArrayList<AbstractShape>();
        for(MapItem item: getItems()) {
            result.add(item.getShape());
        }
        return result;
    }

    public void setChanged() {
        super.setChanged();
        notifyObservers(items);
    }

    public void removeShape(AbstractShape shape) {
        for(MapItem item: items) {
            if (item.getShape().equals(shape)) {
                items.remove(item);
                break;
            }
        }
        setChanged();
    }

    public String getLink(AbstractShape shape) {
        MapItem mapItem = getItem(shape);
        return mapItem != null ? mapItem.getLink() : null;
    }

    public MapItem getItem(AbstractShape shape) {
        if (shape != null) {
            for(MapItem item: items) {
                if (item.getShape().equals(shape)) {
                    return item;
                }
            }
        }
        return null;
    }
}
