package update;

import java.util.HashMap;

/**
 *
 * @author eric
 */
//has an array for memory and has an array of instructions
//executes the insructions to act on the memory
public class BytecodeExecutor {
	//moves the value at index arg1 to index arg0
	public static final int mov = 1;
	//moves the VALUE OF arg1 to the INDEX arg0
	public static final int movv = 100;
	//Skips the next line if arg0 is true, where zero is false and nonzero is true
	public static final int jif = 101;
	public static final int jifv = 102;
	//adds arg0 to the instructon pointer
	public static final int jmp = 2;
	//skips the next instruction if arg0 == arg1
	public static final int je = 3;
	//skips the next line of instruction if arg0 < arg1
	public static final int jl = 4;
	//skips the next line of instruction if arg0 > arg1
	public static final int jg = 5;
	//skips the next line of instruction if arg0 <= arg1
	public static final int jle = 6;
	//skips the next line of instruction if arg0 >= arg1
	public static final int jge = 7;
	
	public static final int jevl = 8;
	public static final int jlvl = 9;
	public static final int jgvl = 10;
	public static final int jlevl = 11;
	public static final int jgevl = 12;
	
	public static final int jevv = 13;
	public static final int jlvv = 14;
	public static final int jgvv = 15;
	public static final int jlevv = 16;
	public static final int jgevv = 17;
	//add the value at index arg1 and the value at index arg2 and puts it in index arg0
	public static final int add = 18;
	//subtracts the value at index arg1 by the value at index arg2 and puts it in index arg0
	public static final int sub = 19;
	//multiplies the value at index arg1 by the value at index arg2 and puts it in index arg0
	public static final int mlt = 20;
	//divides the value at index arg1 by the value at index arg2 and puts it in index arg0
	public static final int div = 21;
	//puts arg1%arg2 into index arg0
	public static final int mod = 22;
	//these four are the same as the above four, except that arg1 is a value,
	//while arg2 is still a location
	//vl stands for "value, location"
	public static final int addvl = 23;
	public static final int subvl = 24;
	public static final int mltvl = 25;
	public static final int divvl = 26;
	public static final int modvl = 27;
	//arg1 is a location, arg2 is a value
	//lv stands for "location, value"
	public static final int addlv = 28;
	public static final int sublv = 29;
	public static final int mltlv = 30;
	public static final int divlv = 31;
	public static final int modlv = 32;
	//arg1 is a value, arg2 is a value
	//vv stands for "value, value"
	public static final int addvv = 33;
	public static final int subvv = 34;
	public static final int mltvv = 35;
	public static final int divvv = 36;
	public static final int modvv = 37;
	
	public static final int jelv = 38;
	public static final int jllv = 39;
	public static final int jglv = 40;
	public static final int jlelv = 41;
	public static final int jgelv = 42;
	
	//These six operations are treated just like arithmetic operaions.
	//For example: "l arg0 arg1 arg2" will put 0 into arg0 if arg1 IS NOT less
	//than arg2. And it will put 1 into arg0 if arg1 IS less than arg2.
	//less than
	public static final int l  = 43;
	//greater than
	public static final int g = 44;
	//less than or equal to
	public static final int le = 45;
	//greater than or equal to
	public static final int ge = 46;
	//equal to
	public static final int e = 47;
	//not equal to
	public static final int ne = 48;
	
	public static final int lvl  = 49;
	public static final int gvl = 50;
	public static final int levl = 51;
	public static final int gevl = 52;
	public static final int evl = 53;
	public static final int nevl = 54;
	
	public static final int llv  = 55;
	public static final int glv = 56;
	public static final int lelv = 57;
	public static final int gelv = 58;
	public static final int elv = 59;
	public static final int nelv = 60;
	
	public static final int lvv  = 61;
	public static final int gvv = 62;
	public static final int levv = 63;
	public static final int gevv = 64;
	public static final int evv = 65;
	public static final int nevv = 66;
	
	public static final int andll = 67;
	public static final int orll = 68;
	public static final int andvl = 69;
	public static final int orvl = 70;
	public static final int andlv = 71;
	public static final int orlv = 72;
	public static final int andvv = 73;
	public static final int orvv = 74;
	
	public static final int notl = 75;
	public static final int notv = 76;
	
	//TODO notl and notv
	//does nothing
	//nop means "no operation"
	public static final int nop = 0;
	
	
	public static final HashMap<String,Integer> possibleInstructions = new HashMap<>();
	static{
		possibleInstructions.put("movl", mov);
		possibleInstructions.put("movv", movv);
		possibleInstructions.put("jifl", jif);
		possibleInstructions.put("jifv", jifv);
		possibleInstructions.put("jmp", jmp);
		
//		possibleInstructions.put("je", je);//////////////
//		possibleInstructions.put("jl", jl);
//		possibleInstructions.put("jg", jg);
//		possibleInstructions.put("jle", jle);
//		possibleInstructions.put("jge", jge);
//		possibleInstructions.put("jell", je);//////////////
//		possibleInstructions.put("jlll", jl);
//		possibleInstructions.put("jgll", jg);
//		possibleInstructions.put("jlell", jle);
//		possibleInstructions.put("jgell", jge);
//		possibleInstructions.put("jelv", jelv);//////////
//		possibleInstructions.put("jllv", jllv);
//		possibleInstructions.put("jglv", jglv);
//		possibleInstructions.put("jlelv", jlelv);
//		possibleInstructions.put("jgelv", jgelv);
//		possibleInstructions.put("jevl", jevl);//////////
//		possibleInstructions.put("jlvl", jlvl);
//		possibleInstructions.put("jgvl", jgvl);
//		possibleInstructions.put("jlevl", jlevl);
//		possibleInstructions.put("jgevl", jgevl);
//		possibleInstructions.put("jevv", jevv);//////////
//		possibleInstructions.put("jlvv", jlvv);
//		possibleInstructions.put("jgvv", jgvv);
//		possibleInstructions.put("jlevv", jlevv);
//		possibleInstructions.put("jgevv", jgevv);
		
//		possibleInstructions.put("add", add);/////////////
//		possibleInstructions.put("sub", sub);
//		possibleInstructions.put("mlt", mlt);
//		possibleInstructions.put("div", div);
//		possibleInstructions.put("mod", mod);
		possibleInstructions.put("addll", add);/////////////
		possibleInstructions.put("subll", sub);
		possibleInstructions.put("mltll", mlt);
		possibleInstructions.put("divll", div);
		possibleInstructions.put("modll", mod);
		possibleInstructions.put("addvl", addvl);//////////
		possibleInstructions.put("subvl", subvl);
		possibleInstructions.put("mltvl", mltvl);
		possibleInstructions.put("divvl", divvl);
		possibleInstructions.put("modvl", modvl);
		possibleInstructions.put("addlv", addlv);//////////
		possibleInstructions.put("sublv", sublv);
		possibleInstructions.put("mltlv", mltlv);
		possibleInstructions.put("divlv", divlv);
		possibleInstructions.put("modlv", modlv);
		possibleInstructions.put("addvv", addvv);//////////
		possibleInstructions.put("subvv", subvv);
		possibleInstructions.put("mltvv", mltvv);
		possibleInstructions.put("divvv", divvv);
		possibleInstructions.put("modvv", modvv);
		possibleInstructions.put("lll", l);//////////////////
		possibleInstructions.put("gll", g);
		possibleInstructions.put("lell", le);
		possibleInstructions.put("gell", ge);
		possibleInstructions.put("ell", e);
		possibleInstructions.put("nell", ne);
		possibleInstructions.put("lvl", lvl);//////////////////
		possibleInstructions.put("gvl", gvl);
		possibleInstructions.put("levl", levl);
		possibleInstructions.put("gevl", gevl);
		possibleInstructions.put("evl", evl);
		possibleInstructions.put("nevl", nevl);
		possibleInstructions.put("llv", llv);//////////////////
		possibleInstructions.put("glv", glv);
		possibleInstructions.put("lelv", lelv);
		possibleInstructions.put("gelv", gelv);
		possibleInstructions.put("elv", elv);
		possibleInstructions.put("nelv", nelv);
		possibleInstructions.put("lvv", lvv);//////////////////
		possibleInstructions.put("gvv", gvv);
		possibleInstructions.put("levv", levv);
		possibleInstructions.put("gevv", gevv);
		possibleInstructions.put("evv", evv);
		possibleInstructions.put("nevv", nevv);
		
		possibleInstructions.put("andll", andll);
		possibleInstructions.put("orll", orll);
		possibleInstructions.put("andlv", andlv);
		possibleInstructions.put("orlv", orlv);
		possibleInstructions.put("andvl", andvl);
		possibleInstructions.put("orvl", orvl);
		possibleInstructions.put("andvv", andvv);
		possibleInstructions.put("orvv", orvv);
		
		possibleInstructions.put("notl", notl);
		possibleInstructions.put("notv", notv);
		
		possibleInstructions.put("nop", nop);
	}
//	private int[] instructions;
//	private int[] memory;
//
//	public AssemblyExecutor(int[] i, int[] m){
//		instructions = i;
//		memory = m;
//	}
	//executes the insructions and acts on the memory
	public static void execute(int[] instructions, int[] memory){
		//the instruction pointer, 
		//ie the index of the command that is currently being executed
		int ip = 0;
		//ip+3 is checked because ip, ip+1, ip+2, and ip+3 are all part of
		//the same instruction, so in case the size of the array isn't divisible
		//by four, there won't be an array index out of bounds exception
		while(ip+3 < instructions.length){
			//System.out.println("Instruction " + ip);
			//each instruction consists of 4 ints, so the ip
			//has to be incremented by four to get to the next instruction
			ip += 4*executeInstruction(instructions[ip], instructions[ip+1], instructions[ip+2], instructions[ip+3], memory);
			
		}
	}
	//executes the instruction, and then returns how many instructions the IP should move
	private static int executeInstruction(int instruction, int arg0, int arg1, int arg2, int[] memory){
		
		switch(instruction){
			case mov:
				memory[arg0] = memory[arg1];
				break;
			case movv:
				memory[arg0] = arg1;
				break;
			case jmp:
				return arg0;
				//break;
			case jif:
				if(memory[arg0] != 0){
					return 2;
				}
				break;
			case jifv:
				if(arg0 != 0){
					return 2;
				}
				break;
			case add:
				memory[arg0] = memory[arg1] + memory[arg2];
				break;
			case sub:
				memory[arg0] = memory[arg1] - memory[arg2];
				break;
			case mlt:
				memory[arg0] = memory[arg1] * memory[arg2];
				break;
			case div:
				memory[arg0] = memory[arg1] / memory[arg2];
				break;
			case mod:
				memory[arg0] = memory[arg1] % memory[arg2];
				break;
				
			case addvl:
				memory[arg0] = arg1 + memory[arg2];
				break;
			case subvl:
				memory[arg0] = arg1 - memory[arg2];
				break;
			case mltvl:
				memory[arg0] = arg1 * memory[arg2];
				break;
			case divvl:
				memory[arg0] = arg1 / memory[arg2];
				break;
			case modvl:
				memory[arg0] = arg1 % memory[arg2];
				break;
				
			case addlv:
				memory[arg0] = memory[arg1] + arg2;
				break;
			case sublv:
				memory[arg0] = memory[arg1] - arg2;
				break;
			case mltlv:
				memory[arg0] = memory[arg1] * arg2;
				break;
			case divlv:
				memory[arg0] = memory[arg1] / arg2;
				break;
			case modlv:
				memory[arg0] = memory[arg1] % arg2;
				break;
			
			case addvv:
				memory[arg0] = arg1 + arg2;
				break;
			case subvv:
				memory[arg0] = arg1 - arg2;
				break;
			case mltvv:
				memory[arg0] = arg1 * arg2;
				break;
			case divvv:
				memory[arg0] = arg1 / arg2;
				break;
			case modvv:
				memory[arg0] = arg1 % arg2;
				break;
			///////////////////////////////
			case l:
				memory[arg0] = memory[arg1] < memory[arg2] ? 1 : 0;
				break;
			case g:
				memory[arg0] = memory[arg1] > memory[arg2] ? 1 : 0;
				break;
			case le:
				memory[arg0] = memory[arg1] <= memory[arg2] ? 1 : 0;
				break;
			case ge:
				memory[arg0] = memory[arg1] >= memory[arg2] ? 1 : 0;
				break;
			case e:
				memory[arg0] = memory[arg1] == memory[arg2] ? 1 : 0;
				break;
			case ne:
				memory[arg0] = memory[arg1] != memory[arg2] ? 1 : 0;
				break;
				
			case lvl:
				memory[arg0] = arg1 < memory[arg2] ? 1 : 0;
				break;
			case gvl:
				memory[arg0] = arg1 > memory[arg2] ? 1 : 0;
				break;
			case levl:
				memory[arg0] = arg1 <= memory[arg2] ? 1 : 0;
				break;
			case gevl:
				memory[arg0] = arg1 >= memory[arg2] ? 1 : 0;
				break;
			case evl:
				memory[arg0] = arg1 == memory[arg2] ? 1 : 0;
				break;
			case nevl:
				memory[arg0] = arg1 != memory[arg2] ? 1 : 0;
				break;
				
			case llv:
				memory[arg0] = memory[arg1] < arg2 ? 1 : 0;
				break;
			case glv:
				memory[arg0] = memory[arg1] > arg2 ? 1 : 0;
				break;
			case lelv:
				memory[arg0] = memory[arg1] <= arg2 ? 1 : 0;
				break;
			case gelv:
				memory[arg0] = memory[arg1] >= arg2 ? 1 : 0;
				break;
			case elv:
				memory[arg0] = memory[arg1] == arg2 ? 1 : 0;
				break;
			case nelv:
				memory[arg0] = memory[arg1] != arg2 ? 1 : 0;
				break;
				
			case lvv:
				memory[arg0] = arg1 < arg2 ? 1 : 0;
				break;
			case gvv:
				memory[arg0] = arg1 > arg2 ? 1 : 0;
				break;
			case levv:
				memory[arg0] = arg1 <= arg2 ? 1 : 0;
				break;
			case gevv:
				memory[arg0] = arg1 >= arg2 ? 1 : 0;
				break;
			case evv:
				memory[arg0] = arg1 == arg2 ? 1 : 0;
				break;
			case nevv:
				memory[arg0] = arg1 != arg2 ? 1 : 0;
				break;
				
			case andll:
				memory[arg0] = memory[arg1] != 0 && memory[arg2] != 0 ? 1 : 0;
				break;
			case orll:
				memory[arg0] = memory[arg1] != 0 || memory[arg2] != 0 ? 1 : 0;
				break;
				
			case andvl:
				memory[arg0] = arg1 != 0 && memory[arg2] != 0 ? 1 : 0;
				break;
			case orvl:
				memory[arg0] = arg1 != 0 || memory[arg2] != 0 ? 1 : 0;
				break;
				
			case andlv:
				memory[arg0] = memory[arg1] != 0 && arg2 != 0 ? 1 : 0;
				break;
			case orlv:
				memory[arg0] = memory[arg1] != 0 || arg2 != 0 ? 1 : 0;
				break;
				
			case andvv:
				memory[arg0] = arg1 != 0 && arg2 != 0 ? 1 : 0;
				break;
			case orvv:
				memory[arg0] = arg1 != 0 || arg2 != 0 ? 1 : 0;
				break;
			
			case notl:
				memory[arg0] = memory[arg1] == 0 ? 1 : 0;
				break;
			case notv:
				memory[arg0] = arg1 == 0 ? 1 : 0;
			
			case nop:
				//do nothing
				break;
				
		}
		//jump ahead one instruction
		return 1;
	}
	
//	public void setInstructions(int[] i){instructions = i;}
//	public int[] getInstructions(){return instructions;}
//	public void setMemory(int[] m){memory = m;}
//	public int[] getMemory(){return memory;}
//
//	public void putByte(int index, int b){
//		instructions[index] = b;
//	}
//	public void putInstruction(int index, int ... args){
//		index *= 4;
//		for(int i = 0; i < args.length; i++){
//			instructions[index + i] = args[i];
//		}
//	}
//
//	public String dumpMemory(){
//		String ret = "";
//		for(int i = 0; i < memory.length; i++){
//			ret += i + ":\t" + memory[i] + "\n";
//		}
//		return ret;
//	}
}



/*

case je:
				if(memory[arg0] == memory[arg1]){
					return 2;
				}
				break;
			case jl:
				if(memory[arg0] < memory[arg1]){
					return 2;
				}
				break;
			case jg:
				System.out.println(memory[arg0] + " " + memory[arg1]);
				if(memory[arg0] > memory[arg1]){
					return 2;
				}
				break;
			case jle:
				if(memory[arg0] <= memory[arg1]){
					return 2;
				}
				break;
			case jge:
				if(memory[arg0] >= memory[arg1]){
					return 2;
				}
				break;
			case jevl:
				if(arg0 == memory[arg1]){
					return 2;
				}
				break;
			case jlvl:
				if(arg0 < memory[arg1]){
					return 2;
				}
				break;
			case jgvl:
				if(arg0 > memory[arg1]){
					return 2;
				}
				break;
			case jlevl:
				if(arg0 <= memory[arg1]){
					return 2;
				}
				break;
			case jgevl:
				if(arg0 >= memory[arg1]){
					return 2;
				}
				break;
			case jevv:
				if(arg0==arg1){
					return 2;
				}
				break;
			case jlvv:
				if(arg0<arg1){
					return 2;
				}
				break;
			case jgvv:
				if(arg0 > arg1){
					return 2;
				}
				break;
			case jlevv:
				if(arg0 <= arg1){
					return 2;
				}
				break;
			case jgevv:
				if(arg0 >= arg1){
					return 2;
				}
				break;
			case jelv:
				if(memory[arg0] == arg1){
					return 2;
				}
				break;
			case jllv:
				if(memory[arg0] < arg1){
					return 2;
				}
				break;
			case jglv:
				if(memory[arg0] > arg1){
					return 2;
				}
				break;
			case jlelv:
				if(memory[arg0] <= arg1){
					return 2;
				}
				break;
			case jgelv:
				if(memory[arg0] >= arg1){
					return 2;
				}
				break;
*/