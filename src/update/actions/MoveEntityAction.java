/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package update.actions;

import update.Action;
import update.Entity;
import update.Tile;

/**
 *
 * @author eric
 */

public class MoveEntityAction implements Action{
	private final Entity entity;
	private final int[] newPos;
	public MoveEntityAction(Entity e, int[] np){
		entity = e;
		newPos = np;
	}
	//tries to move 'entity' to 'newPos'
	//it will succeed only if 'entity' is at its self-reported position in tiles
	//and the space 'entity' is trying to move to does not contain another entity
	@Override
	public void act(Tile[][] tiles) {
		try{
			//the tile that contains 'entity'
			Tile tileWithEntity = tiles[entity.getX()][entity.getY()];
			//the tile that 'entity' will move to
			Tile tileToMoveTo = tiles[newPos[0]][newPos[1]];

			if(tileWithEntity.getEntity() == entity && tileToMoveTo.getEntity() == null){
				tileToMoveTo.setEntity(entity);
				tileWithEntity.setEntity(null);
			}
		}catch(ArrayIndexOutOfBoundsException e){
			e.printStackTrace();
		}
	}
}
