package unnamedgame;

import java.awt.Graphics2D;

/**
 * Created by eric on 7/30/16.
 */
public interface Screen {
    void drawGraphics(Graphics2D g2);
    void makeInactive();
    void makeActive();
}
