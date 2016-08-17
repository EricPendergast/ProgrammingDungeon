/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package update;

import java.io.File;
import java.io.Serializable;
import java.util.LinkedList;

/**
 *
 * @author eric
 */
public abstract class Entity implements Serializable{
	//The thing that makes all the decisions about what to do
	//Contains the memory and the instructions, and executes the instructions
	//on the memory
//	private AssemblyExecutor brain;
	
	private Compiler compiler;
	
	//the instructions
	protected int[] instruc;
	//What the instructions act on.
	//Values in the memory will be altered by the instructions, and then methods can 
	//choose to do things based off of whats in the memory.
	//For example, there might be a index in the memory that corresponds to 
	//speed moving left, so the update method will decide to go left when it sees
	//this value.
	protected int[] memory;
	public Entity(int instrucSize, int memSize){
		instruc = new int[instrucSize*4];
		memory = new int[memSize];
		//brain = new AssemblyExecutor(instruc,memory);
		compiler = new Compiler();
	}
	public int[] pos = null;
	public abstract void update(Tile parent, LinkedList<Action> actions);
	public String renderGetInfo(){return "@default";}
	protected void executeInstructions(){
		BytecodeExecutor.execute(instruc, memory);
	}
	protected void putInstruction(int index, int ... args){
		index *= 4;
		for(int i = 0; i < args.length; i++){
			instruc[index + i] = args[i];
		}
	}
//	protected String dumpMemory(){
//		return brain.dumpMemory();
//	}
	protected void putByte(int index, int b){
		instruc[index] = b;
	}
	protected String dumpMemory(){
		String ret = "";
		for(int i = 0; i < memory.length; i++){
			ret += i + ":\t" + memory[i] + "\n";
		}
		return ret;
	}
	public int getX(){return pos[0];}
	public int getY(){return pos[1];}
	protected void compile(){
		instruc = compiler.compile();
	}
	protected void setCode(String code){
		compiler.setCode(code);
	}
	protected void setCodeFromFile(File file){
		compiler.setCodeFromFile(file);
	}
	protected void setCompilerDebugMode(boolean b){
		compiler.debugMode = b;
	}
}
