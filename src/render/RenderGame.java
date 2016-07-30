/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package render;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import unnamedgame.GUIHandler;
import update.Tile;
import update.UpdateGame;

/**
 *
 * @author eric
 */
public class RenderGame implements Runnable{
	
	//The image that the game gui will render to the screen whenever it chooses to
	private BufferedImage screen;
	//The image that this class draws on while rendering everything
	//This is swapped with 'screen' when it is done being rendered
	private BufferedImage buffer;
	
	//The number of tiles wide the display is
	public static final int SCREEN_TILE_WIDTH = 10;
	//The number of tiles high the display is
	public static final int SCREEN_TILE_HEIGHT = 10;
	//The number of pixels wide and tall each tile is
	public static final int TILE_SIZE = 16;
	
	//Handles the rendering for each tile
	private TileRenderer tileRenderer;
	
	private boolean isRunning = false;
	//A pointer to the object that contains all the world information.
	//This should only be read from, not written to.
	private UpdateGame updater;
	//This contains the render info from each tile from the previous tick.
	//This is used to prevent an unchanged tile from being re-rendered each tick.
	private final String[][] prevTileRenderInfo;
	
	public RenderGame(UpdateGame u){
		updater = u;
		tileRenderer = new TileRenderer();
		prevTileRenderInfo = new String[updater.MAP_WIDTH][updater.MAP_HEIGHT];
		for(int i = 0; i < prevTileRenderInfo.length; i++){
			for(int j = 0; j < prevTileRenderInfo[i].length; j++){
				prevTileRenderInfo[i][j] = "";
			}
		}
	}
	@Override
	public void run() {
		isRunning = true;
		screen = new BufferedImage(SCREEN_TILE_HEIGHT*TILE_SIZE, SCREEN_TILE_WIDTH*TILE_SIZE, BufferedImage.TYPE_INT_RGB);
		buffer = new BufferedImage(SCREEN_TILE_HEIGHT*TILE_SIZE, SCREEN_TILE_WIDTH*TILE_SIZE, BufferedImage.TYPE_INT_RGB);
		while(isRunning){
			//Write to buffer
			{
				Graphics2D g2 = (Graphics2D)buffer.getGraphics();
				g2.clearRect(0, 0, buffer.getWidth(), buffer.getHeight());
				//This is where all the interesting stuff happens
				drawGraphics(g2);
				g2.setColor(Color.WHITE);
				g2.drawLine(0, 0, 800, 800);
			}
			//Swap buffer with screen
			{
				BufferedImage temp = screen;
				screen = buffer;
				buffer = temp;
			}
			try{
				Thread.sleep(1000/60);
			}catch(InterruptedException e){e.printStackTrace();}
		}
	}
	
	/*render info strings documentation:
	They are all in the format of "@<texture>#<modifier><value>#...@..."
	Each texture will be rendered in the order they appear, with the modifiers
	applied.
	List of modifiers:
		#a<number of frames> - (a)nimation, its value is the number of frames.
			if this option is here, the tile renderer will look for texture files
			named <number>@<texture>
	*/
	public void drawGraphics(Graphics2D g2){
		//Point offset = new Point(0,0);
		Tile[][] tiles = updater.renderGetTiles();
		for(int i = 0; i < tiles.length; i++){
			for(int j = 0; j < tiles[i].length; j++){
				tileRenderer.renderTile(tiles[i][j], i, j, TILE_SIZE, g2);
			}
		}
	}
	
	public BufferedImage getScreenImage(){
		return screen;
	}
	public void stop(){
		isRunning = false;
	}
}
