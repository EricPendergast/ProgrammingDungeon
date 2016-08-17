/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package update;

import update.actions.PutEntityAction;
import update.entities.Player;

import java.io.Serializable;
import java.util.LinkedList;

/**
 *
 * @author eric
 */
public class UpdateGame implements Runnable, Serializable{
	public final int MAP_WIDTH = 10;
	public final int MAP_HEIGHT = 10;
	public static final int tickTime = (int)1E6*100;
	//entities[x][y] gives the entity at square (x,y)
	private Tile[][] tiles;
	private LinkedList<Action> actionStack;
	
	private boolean isRunning = false;
	
	private Player player;
	public UpdateGame(){
		player = null;
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
					tiles[i][j].update(actionStack);
				}
			}
			//executing all the actions
			while(actionStack.size() > 0){
				Action a = actionStack.pop();
				
				try{
					a.act(tiles);
				}catch(Exception e){
					System.out.println("Action " + a + " threw an exception");
				}
			}
			try{
				Thread.sleep((long)(tickTime/1E6));
			}catch(InterruptedException e){e.printStackTrace();}
		}
	}
	public void addAction(Action action){
		actionStack.push(action);
	}
	/*
	 * In order for the renderer to know where the player is, the player must  be added
	 * to 'tiles' through this method.
	 */
	public void addPlayer(Player p, int[] pos){
		player = p;
		addAction(new PutEntityAction(p, pos));
	}
	public Tile[][] renderGetTiles(){
		return tiles;
	}
	public Player renderGetPlayer(){
		return player;
	}
	public void stop(){
		isRunning = false;
	}
}
