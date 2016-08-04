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
public class Tile implements Serializable{
	private Entity entity;
	//private Block block;
	//private SecondaryEntity sEntity;
	private int[] pos;
	public Tile(int x, int y){
		pos = new int[] {x,y};
		entity = null;
		//block = null;
		//sEntity = null;
		
	}
	
	public LinkedList<Action> update(){
		LinkedList<Action> actions = new LinkedList<>();
		if(entity != null){
			actions.add(entity.update(this));
		}
		return actions;
	}
	
	public Entity getEntity(){return entity;}
	public void setEntity(Entity e){
		entity = e;
		if(entity != null){
			if(entity.pos == null){
				entity.pos = new int[2];
			}
			entity.pos[0] = this.pos[0];
			entity.pos[1] = this.pos[1];
		}
	}
	
	//Documentation for what this should return is in the RenderGame class
	public String renderGetInfo(){
		String renderInfo = "";
		//TODO render block
		//TODO render secondary entity
		if(entity != null){
			renderInfo += entity.renderGetInfo();
		}
		return renderInfo;
	}
}
