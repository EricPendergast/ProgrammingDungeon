package unnamedgame;

import java.awt.Graphics2D;

/**
 * A screen is an object representing one state of the application.
 * For example, there may be a main menu screen, a game screen, an options menu screen, and so on
 */
public interface Screen {
    /**
     * This is how the screen gets displayed. The object that has the jframe will call this method on its graphics
     * object and the screen will render itself to the jframe
     */
    void drawGraphics(Graphics2D g2);
	
	/**
     * Lets the screen know that it should no longer be doing stuff because it isn't being displayed any more.
     */
    void makeInactive();
	
	/**
     * Lets the screen know that it is currently being displayed and should start running
     */
    void makeActive();
}
