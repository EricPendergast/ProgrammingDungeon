/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package update.actions;

import unnamedgame.Ops;
import update.Action;
import update.Entity;
import update.Tile;

/**
 *
 * @author eric
 */
//This is an action that takes an entity and a position, and puts that entity into that position
public class PutEntityAction implements Action{
	private Entity entity;
	private int[] pos;
	public PutEntityAction(Entity e, int[] p){
		entity = e;
		pos = p;
	}
	public void act(Tile[][] tiles){
		try{
			tiles[pos[0]][pos[1]].setEntity(entity);
		}catch(ArrayIndexOutOfBoundsException e){
			e.printStackTrace();
		}
	}
}
