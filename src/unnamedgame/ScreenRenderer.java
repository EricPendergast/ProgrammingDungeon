package unnamedgame;

import javax.swing.JComponent;
import java.awt.*;

/**
 * Created by eric on 7/30/16.
 */
public class ScreenRenderer extends JComponent{
	public final static double scale = 10;
	private Screen screen;
	public ScreenRenderer(Screen s){
		screen = s;
	}
	
	public void setScreen(Screen s){
		screen = s;
	}
	
	@Override
	public void paintComponent(Graphics g){
		Graphics2D g2 = (Graphics2D)g;
		
		g2.scale(scale,scale);
		if(screen != null){
			screen.drawGraphics(g2);
		}
	}
}
