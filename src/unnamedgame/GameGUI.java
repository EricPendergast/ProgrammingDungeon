/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unnamedgame;

import java.awt.*;
import javax.swing.*;
import render.RenderGame;

/**
 *
 * @author eric
 */
public class GameGUI extends JComponent{
	public GameGUI(){
		this.setLayout(new BorderLayout());
		this.add(new ViewComponent(), BorderLayout.CENTER);
		
	}
	public void paintComponent(Graphics g){
		super.paintComponent(g);
//		if(RenderGame.screen != null){
//			((Graphics2D)g).drawImage(RenderGame.screen, 0, 0, null);
//		}
	}
}

class ViewComponent extends JComponent{
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		if(RenderGame.screen != null){
			((Graphics2D)g).drawImage(RenderGame.screen, 0, 0, null);
		}
	}
}