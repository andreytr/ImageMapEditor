package com.github.andreytr.imagemapeditor.view;

import com.github.andreytr.imagemapeditor.model.MapModel;
import com.github.andreytr.imagemapeditor.wiki.ConfluenceMapEngine;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * User: andreytr
 * Date: 16.04.2011
 * Time: 18:27:35
 */
public class MapEditorPanel extends JPanel {

    private MapModel model;
    private MapPanel mapPanel;
    private MapTextArea textArea;

    public MapEditorPanel(BufferedImage image, ConfluenceMapEngine engine) {
        setLayout(new BorderLayout());
        buildItems(image, engine);
        buildLayout();
    }

    private void buildItems(BufferedImage image, ConfluenceMapEngine engine) {
        model    = new MapModel();
        mapPanel = new MapPanel(image, model);
        textArea = new MapTextArea(engine);

        model.addObserver(mapPanel);
        model.addObserver(textArea);
        model.setChanged();
    }

    private void buildLayout() {
        JScrollPane textAreaScrollPane = new JScrollPane(textArea);
        textAreaScrollPane.setMinimumSize(new Dimension(0, 150));

        JSplitPane mainPanel = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        mainPanel.setOneTouchExpandable(true);
        mainPanel.setContinuousLayout(true);
        mainPanel.setTopComponent(new JScrollPane(mapPanel));
        mainPanel.setBottomComponent(textAreaScrollPane);
        mainPanel.setResizeWeight(1);
        add(mainPanel);
    }
}
