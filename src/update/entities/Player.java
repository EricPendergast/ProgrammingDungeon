package update.entities;

import unnamedgame.GUIHandler;
import update.Action;
import update.Entity;
import update.Tile;
import update.UpdateGame;
import update.actions.DisplaceEntityAction;

import java.awt.event.KeyEvent;
import java.util.LinkedList;

/**
 * Created by eric on 8/9/16.
 */
public class Player extends Entity{
	public Player(){
		super(0,0);
	}
	public void update(Tile parent, LinkedList<Action> actions){
		int[] displacement = new int[2];
		int speed = 1;
		
		//Makes the player move if W has been pressed.
		if(isKeyHeld(KeyEvent.VK_W)){
			displacement[1] = speed;
		}else if(isKeyHeld(KeyEvent.VK_S)){
			displacement[1] = -speed;
		}
		
		if(isKeyHeld(KeyEvent.VK_A)){
			displacement[0] = -speed;
		}else if(isKeyHeld(KeyEvent.VK_D)){
			displacement[0] = speed;
		}
		
		//This syncs the player's tile position with its render position
		actions.add(new DisplaceEntityAction(this, displacement));
		
	}
	//Returns whether or not the given key was pressed within the last tick, or if it is being held down
	//The "System.nanoTime() - GUIHandler.keyTimes[keycode]" gives the
	//time since the last time the key was pressed. If this value is less than the
	//time between ticks, it should register as a key press. This part ensures that
	//if the key is pressed and released in between ticks, it will still register as a key press.
	//The part after the "||" makes it so that it will still register as a key press if the key is being held down
	protected boolean isKeyHeld(int keycode){
		return System.nanoTime() - GUIHandler.keyTimes[keycode] < UpdateGame.tickTime || GUIHandler.keys[keycode];
	}
	//Returns whether or not the key was first pressed within the last tick
	//This is for if you want a key press to register as single press and not repeat if held down
	protected boolean isKeyInitPress(int keycode){
		return System.nanoTime() - GUIHandler.keyTimes[keycode] < UpdateGame.tickTime;
	}
	public String renderGetInfo(){
		return "@human@humanOrangeShirt@humanBluePants@humanShoes";
	}
	
	public float renderGetX(){
		return super.getX();
	}
	public float renderGetY(){
		return super.getY();
	}
}
