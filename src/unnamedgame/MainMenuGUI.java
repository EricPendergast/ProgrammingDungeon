/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unnamedgame;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author eric
 */
public class MainMenuGUI extends JPanel{
	private JPanel parentPanel;
	private JButton startButton;
	
	public MainMenuGUI(JPanel parent){
		this.parentPanel = parent;
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		{//making the main menu buttons
			startButton = new JButton("Start");
			startButton.addActionListener((ActionEvent e) -> {
				((CardLayout)(parentPanel.getLayout())).show(parentPanel, "GAME");
			});
			c.fill = GridBagConstraints.BOTH;
			c.gridx = 0; c.gridy = 0;
			c.weightx = c.weighty = 1;
			this.add(startButton, c);
			this.setEnabled(true);
			c.gridx = c.gridy = 1;
			c.weightx = c.weighty = 1;
			JPanel p = new JPanel();
			p.setBackground(Color.yellow);
		}
	}
}
