/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package update;

import java.io.Serializable;
import java.util.LinkedList;

/**
 *
 * @author eric
 */
public class UpdateGame implements Runnable, Serializable{
	public final int MAP_WIDTH = 10;
	public final int MAP_HEIGHT = 10;
	//entities[x][y] gives the entity at square (x,y)
	private Tile[][] tiles;
	private LinkedList<Action> actionStack;
	
	private boolean isRunning = false;
	
	public UpdateGame(){
		tiles = new Tile[MAP_WIDTH][MAP_HEIGHT];
		
		for(int i = 0; i < tiles.length; i++){
			for(int j = 0; j < tiles[i].length; j++){
				tiles[i][j] = new Tile(i,j);
			}
		}
		
		actionStack = new LinkedList<Action>();
	}
	
	public void run(){
		isRunning = true;
		while(isRunning){
			//update each individual tile
			//in this stage, no tile should be able to act on another tile,
			//unless it pushes an action onto 'actionStack'
			for(int i = 0; i < tiles.length; i++){
				for(int j = 0; j < tiles[i].length; j++){
					LinkedList<Action> tileActions = tiles[i][j].update();
					if(tileActions != null){
						while(tileActions.size() > 0){
							Action action = tileActions.pop();
							if(action != null){
								actionStack.push(action);
							}
						}
					}
				}
			}
			//excecuting all the actions
			while(actionStack.size() > 0){
				actionStack.pop().act(tiles);
			}
			try{
				Thread.sleep(100);
			}catch(InterruptedException e){e.printStackTrace();}
		}
	}
	public void addAction(Action action){
		actionStack.push(action);
	}
	public Tile[][] renderGetTiles(){
		return tiles;
	}
	
	public void stop(){
		isRunning = false;
	}
}
