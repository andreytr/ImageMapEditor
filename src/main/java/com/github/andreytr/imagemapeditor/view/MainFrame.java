package com.github.andreytr.imagemapeditor.view;

import com.github.andreytr.imagemapeditor.view.dialogs.AboutDialog;
import com.github.andreytr.imagemapeditor.wiki.ConfluenceMapEngine;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * User: andreytr
 * Date: 26.03.2011
 * Time: 22:30:47
 */
public class MainFrame extends JFrame {

    private JTabbedPane tabbedPane;

    public MainFrame() throws IOException {
        setTitle("ImageMapEditor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setJMenuBar(buildMenu());

        tabbedPane = new JTabbedPane();
        tabbedPane.setFocusable(false);
        add(tabbedPane);
    }

    private JMenuBar buildMenu() {
        JMenuBar bar = new JMenuBar();
        bar.add(buildFileMenu());
        bar.add(buildHelpMenu());
        return bar;
    }

    private JMenu buildFileMenu() {
        JMenu menu = new JMenu("File");
        menu.add(buildFileOpenMenuItem());
        menu.add(buildUrlOPenMenuItem());
        menu.addSeparator();
        menu.add(closeTabMenuItem());
        menu.addSeparator();
        menu.add(buildExitItem());
        return menu;
    }

    private JMenu buildHelpMenu() {
        JMenu menu = new JMenu("Help");
        menu.add(buildAboutItem());
        return menu;
    }

    private JMenuItem buildFileOpenMenuItem() {
        JMenuItem item = new JMenuItem("Open file");
        item.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                openImageFile();
            }
        });
        return item;
    }

    private JMenuItem buildUrlOPenMenuItem() {
        JMenuItem item = new JMenuItem("Open URL");
        item.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                openImageUrl();
            }
        });
        return item;
    }

    private JMenuItem closeTabMenuItem() {
        JMenuItem item = new JMenuItem("Close");
        item.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                if (tabbedPane.getSelectedIndex() > -1) {
                    tabbedPane.remove(tabbedPane.getSelectedIndex());
                }
            }
        });
        return item;
    }

    private JMenuItem buildExitItem() {
        JMenuItem item = new JMenuItem("Exit");
        item.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        return item;
    }

    private JMenuItem buildAboutItem() {
        JMenuItem item = new JMenuItem("About");
        item.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                AboutDialog dialog = new AboutDialog(MainFrame.this);
                dialog.setVisible(true);
            }
        });
        return item;
    }

    private void openImage(BufferedImage image, String pageName, String imageName) {
        ConfluenceMapEngine engine = new ConfluenceMapEngine(pageName, imageName);

        String title = imageName;
        if (pageName != null && !pageName.equals("")) {
            title = pageName + " - " + imageName;
        }
        tabbedPane.addTab(title, new MapEditorPanel(image, engine));
        tabbedPane.setSelectedIndex(tabbedPane.getTabCount() - 1);
    }

    private void openImageFile() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                if (f == null) {
                    return false;
                }
                if (f.isDirectory()) {
                    return true;
                }
                String name = f.getName();
                return name.endsWith(".png") ||
                       name.endsWith(".jpg") ||
                       name.endsWith(".jpeg") ||
                       name.endsWith(".gif");
            }

            @Override
            public String getDescription() {
                return "Image files (*.png, *.jpg, *.jpeg, *.gif)";
            }
        });
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION ) {
            File selectedFile = chooser.getSelectedFile();
            String pageName = getPageName();
            if (pageName == null) {
                return;
            }
            
            try {
                openImage(ImageIO.read(selectedFile), pageName, selectedFile.getName());
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private void openImageUrl() {
        String url = JOptionPane.showInputDialog(this, "Enter image URL", "http://", JOptionPane.QUESTION_MESSAGE);
        if (url == null || url.trim().equals("")) {
            return;
        }
        String imageName = url.lastIndexOf("/") > -1 ? url.substring(url.lastIndexOf("/") + 1) : "";
        String pageName = getPageName();
        if (pageName == null) {
            return;
        }
        try {
            openImage(ImageIO.read(new URL(url)), pageName, imageName);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public String getPageName() {
        String s = JOptionPane.showInputDialog(this.getRootPane(), "Enter page name");
        if (s != null && s.trim().equals("")) {
            JOptionPane.showMessageDialog(this.getRootPane(), "Page name must be not empty!", "Warning", JOptionPane.WARNING_MESSAGE);
            return null;
        }
        return s;
    }
}
