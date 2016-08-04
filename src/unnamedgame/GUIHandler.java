package unnamedgame;

import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import javax.swing.*;

/**
 * This is where all the swing stuff is handled.
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
	
	private boolean isRunning = false;
    public GUIHandler(){
		screens = new HashMap<>();
		activeScreen = null;
		screenRenderer = new ScreenRenderer(null);
		frame = new JFrame("TITLE");
		frame.setSize(frameWidth, frameHeight);
		frame.setResizable(false);
		//adding the action listeners
		{
			frame.addWindowListener(new WindowListener(){
				public void windowOpened(WindowEvent windowEvent){}
				public void windowClosing(WindowEvent windowEvent){
					stop();
				}
				public void windowClosed(WindowEvent windowEvent){}
				public void windowIconified(WindowEvent windowEvent){}
				public void windowDeiconified(WindowEvent windowEvent){}
				public void windowActivated(WindowEvent windowEvent){}
				public void windowDeactivated(WindowEvent windowEvent){}
			});
		}
		
		
		parentPanel = new JPanel();
		parentPanel.setLayout(new CardLayout());
		frame.add(parentPanel);
		parentPanel.add(screenRenderer);
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
		isRunning = true;
		frame.setVisible(true);
		while(isRunning){
			frame.repaint();
			try{
				Thread.sleep(refreshTime);
			}catch(InterruptedException e){e.printStackTrace();}
		}
	}
	
	public void stop(){
		isRunning = false;
	}
}