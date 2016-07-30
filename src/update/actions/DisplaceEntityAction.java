package update.actions;

import unnamedgame.Ops;
import update.Action;
import update.Entity;
import update.Tile;

/**
 *
 * @author eric
 */

public class DisplaceEntityAction implements Action{
	private final Entity entity;
	private final int[] displacement;
	public DisplaceEntityAction(Entity e, int[] d){
		entity = e;
		displacement = d;
	}
	//tries to displace 'entity' by 'displacement'
	//i.e. it will move 'entity' right by displacement[0] and up by displacement[1]
	//it will succeed only if 'entity' is at its self-reported position in tiles
	//and the space 'entity' is trying to move to does not contain another entity
	@Override
	public void act(Tile[][] tiles) {
//		if(!(Ops.matrixContainsPoint(tiles, entity.getX(), entity.getY()) || Ops.matrixContainsPoint(tiles, entity.getX() + displacement[0], entity.getY() + displacement[1]))){
//			return;
//		}
		try{
			//the tile that contains 'entity'
			Tile tileWithEntity = tiles[entity.getX()][entity.getY()];
			//the tile that 'entity' will move to
			Tile tileToMoveTo = tiles[entity.getX() + displacement[0]][entity.getY() + displacement[1]];

			if(tileWithEntity.getEntity() == entity && tileToMoveTo.getEntity() == null){
				tileToMoveTo.setEntity(entity);
				tileWithEntity.setEntity(null);
			}
		}catch(ArrayIndexOutOfBoundsException e){
			e.printStackTrace();
		}
	}
}
