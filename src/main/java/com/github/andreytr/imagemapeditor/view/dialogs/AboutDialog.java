package com.github.andreytr.imagemapeditor.view.dialogs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

/**
 * User: andreytr
 * Date: 16.04.2011
 * Time: 21:58:49
 */
public class AboutDialog extends JDialog{
   public AboutDialog(JFrame owner){
	   super(owner,"About",true);

	   JLabel label = new JLabel(
	   "<html>" +
	   "<h2 align=\"center\">Image Map Editor</h2>"+
	   "<h3 align=\"center\">Image map editor for Confluence</h3>"+
	   "<p align=\"center\">v.0.1, 16.04.2011, https://github.com/andreytr</p>"+
	   "</html>");

	   label.setHorizontalAlignment(JLabel.CENTER);

	   JPanel buttonPanel = new JPanel();
	   JButton okButton = new JButton("OK");
	   okButton.addActionListener(new ActionListener(){
		  public void actionPerformed(ActionEvent event){
			 AboutDialog.this.setVisible(false);
		  }
	   });

	   buttonPanel.add(okButton);
	   buttonPanel.setBorder(BorderFactory.createMatteBorder(1,0,1,0,Color.LIGHT_GRAY));
	   add(label,BorderLayout.CENTER);
	   add(buttonPanel, BorderLayout.SOUTH);

	   getRootPane().setDefaultButton(okButton);
	   getRootPane().registerKeyboardAction(new ActionListener(){
		   public void actionPerformed(ActionEvent ae){
			   setVisible(false);
		   }
	   },
	   KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
   }

   public void setVisible(boolean b){
	   if (b){
		   pack();
		   setSize(getWidth()+60,getHeight()+10);
	   }
       toCenter();
	   super.setVisible(b);
   }

    private void toCenter(){
        int width = getOwner().getSize().width;
        int height = getOwner().getSize().height;
        Point location = getOwner().getLocation();
        setLocation((int)location.getX() + (width - getWidth())/2, (int)location.getY() + (height - getHeight())/2);
	}
}
