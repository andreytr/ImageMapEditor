package com.github.andreytr.imagemapeditor.view;

import com.github.andreytr.imagemapeditor.model.MapItem;
import com.github.andreytr.imagemapeditor.wiki.AbstractEngine;

import javax.swing.*;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * User: andreytr
 * Date: 16.04.2011
 * Time: 17:06:11
 */
public class MapTextArea extends JTextArea implements Observer {

    private AbstractEngine engine;

    public MapTextArea(AbstractEngine engine) {
        this.engine = engine;
    }

    public void update(Observable o, Object arg) {
        if (arg == null || !(arg instanceof List)) {
            setText("");
        }
        setText(engine.getImageMapText((List<MapItem>)arg));
    }
}
