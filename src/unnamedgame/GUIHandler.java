package unnamedgame;

import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import javax.swing.*;

/**
 *
 * @author eric
 */
public class GUIHandler {
	private HashMap<String, Screen> screens;
	private Screen activeScreen;
	private ScreenRenderer screenRenderer;
	private final int refreshTime = 1000/120;
	public static int frameWidth = 800;
	public static int frameHeight = 800;
    private final JFrame frame;
	private final JPanel parentPanel;
	//private final JPanel mainMenu;
	//private final GameGUI game;
//	private JButton startButton;
//	private JButton testButton = new JButton("TEST");
    public GUIHandler(){
		screens = new HashMap<>();
		activeScreen = null;
		screenRenderer = new ScreenRenderer(null);
		frame = new JFrame("TITLE");
		frame.setSize(frameWidth, frameHeight);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		parentPanel = new JPanel();
		parentPanel.setLayout(new CardLayout());
		frame.add(parentPanel);
		parentPanel.add(screenRenderer);
		
//		mainMenu = new JPanel();
//		mainMenu.setLayout(new GridBagLayout());
//		GridBagConstraints c = new GridBagConstraints();
//		
//		{//making the main menu buttons
//			startButton = new JButton("Start");
//			startButton.addActionListener((ActionEvent e) -> {
//				((CardLayout)(parentPanel.getLayout())).show(parentPanel, "GAME");
//			});
//			c.fill = GridBagConstraints.BOTH;
//			c.gridx = 0; c.gridy = 0;
//			c.weightx = c.weighty = 1;
//			mainMenu.add(startButton, c);
//			mainMenu.setEnabled(true);
//			c.gridx = c.gridy = 1;
//			c.weightx = c.weighty = 1;
//			JPanel p = new JPanel();
//			p.setBackground(Color.yellow);
//			mainMenu.add(testButton, c);
//		}
//		parentPanel.add(mainMenu, "MAINMENU");
		
//		mainMenu = new MainMenuGUI(parentPanel);
//		parentPanel.add(mainMenu);
//
//		game = new GameGUI();
//		parentPanel.add(game, "GAME");
	}
	
	//Deactivates the current screen, then replaces it with the new screen, then activates the new screen
	public void setActiveScreen(String name){
		if(activeScreen != null){
			activeScreen.makeInactive();
		}
		activeScreen = screens.get(name);
		if(activeScreen != null){
			activeScreen.makeActive();
		}
		screenRenderer.setScreen(activeScreen);
	}
	
	public void addScreen(Screen screen, String name){
		screens.put(name, screen);
	}
	
	public void startRunning(){
		frame.setVisible(true);
		while(true){
			frame.repaint();
			try{
				Thread.sleep(refreshTime);
			}catch(InterruptedException e){e.printStackTrace();}
		}
	}
}