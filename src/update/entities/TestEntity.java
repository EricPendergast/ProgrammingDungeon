/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package update.entities;
import static update.AssemblyExecutor.*;

import java.util.LinkedList;
import update.Action;
import update.Entity;
import update.Tile;
import update.actions.DisplaceEntityAction;
/**
 *
 * @author eric
 */
public class TestEntity extends Entity{
	static final int isAtRight = 0;
	static final int isMovingLeft = 1;
	public TestEntity(){
		super(3,2);
		//put into instructions:
		//if memory[isAtRight] > 0
		//		memory[isMovingLeft] = 1
		putInstruction(0,jglv,isAtRight,0);
		putInstruction(1,jmp,1);
		putInstruction(2,movv,isMovingLeft,1);
	}
	
	//keeps track of how many times update(Tile parent) has been called
	int counter = 0;
	@Override
	public Action update(Tile parent) {
		super.executeInstructions();
		if(getX() > 5){
			memory[isAtRight] = 1;
			System.out.println("greater");
		}else{
			System.out.println("less");
		}
		counter++;
		if(counter % 10 == 0 && memory[isMovingLeft] == 0){
			return new DisplaceEntityAction(this, new int[]{1,0});
		}else{
			return null;
		}
	}
	
}
