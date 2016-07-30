package unnamedgame;

import render.RenderGame;
import update.UpdateGame;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * Created by eric on 7/30/16.
 */
public class GameScreen implements Screen{
    private BufferedImage draw;
    private Thread gameThread = null, renderThread = null;
    private UpdateGame update;
    private RenderGame render;
    public GameScreen(UpdateGame u, RenderGame r){
        update = u;
        render = r;
    }
    
    public void drawGraphics(Graphics2D g2){
        g2.drawImage(render.getScreenImage(), 0, 0, null);
    }
    @Override
    public void makeInactive(){
        if(gameThread != null){
            update.stop();
            gameThread = null;
        }
        if(renderThread != null){
            render.stop();
            renderThread = null;
        }
    }
    
    @Override
    public void makeActive(){
        if(gameThread == null){
            gameThread = new Thread(update);
            gameThread.start();
        }
        
        if(renderThread == null){
            renderThread = new Thread(render);
            renderThread.start();
        }
    }
}
