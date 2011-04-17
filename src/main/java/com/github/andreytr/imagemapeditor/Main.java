package com.github.andreytr.imagemapeditor;

import com.github.andreytr.imagemapeditor.view.MainFrame;
import com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel;

import javax.swing.*;
import java.awt.*;

/**
 * User: andreytr
 * Date: 26.03.2011
 * Time: 22:27:05
 */
public class Main {
    private static final int HEIGHT = 600;
    private static final int WEIGHT = 800;

    public static void main(String[] args) throws Exception {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();

        UIManager.setLookAndFeel(new NimbusLookAndFeel());
        MainFrame frame = new MainFrame();
        frame.setSize(WEIGHT, HEIGHT);
        frame.setLocation(((int)screenSize.getWidth() - WEIGHT) / 2, ((int)screenSize.getHeight() - HEIGHT) / 2);
        frame.setVisible(true);
    }
}
