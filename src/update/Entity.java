/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package update;

import java.util.LinkedList;

/**
 *
 * @author eric
 */
public abstract class Entity {
	//The thing that makes all the decisions about what to do
	//Contains the memory and the instructions, and executes the instructions
	//on the memory
	private AssemblyExecutor brain;
	//the instructions
	protected final int[] instruc;
	//What the instructions act on.
	//Values in the memory will be altered by the instructions, and then methods can 
	//choose to do things based off of whats in the memory.
	//For example, there might be a index in the memory that corresponds to 
	//speed moving left, so the update method will decide to go left when it sees
	//this value.
	protected final int[] memory;
	public Entity(int instrucSize, int memSize){
		instruc = new int[instrucSize*4];
		memory = new int[memSize];
		brain = new AssemblyExecutor(instruc,memory);
	}
	public int[] pos = null;
	public abstract Action update(Tile parent);
	public String renderGetInfo(){return "@default";}
	protected void executeInstructions(){
		brain.execute();
	}
	protected void putInstruction(int index, int ... args){
		index *= 4;
		for(int i = 0; i < args.length; i++){
			instruc[index + i] = args[i];
		}
	}
	public int getX(){return pos[0];}
	public int getY(){return pos[1];}
	
}
