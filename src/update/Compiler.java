/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package update;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 *
 * @author eric
 */
public class Compiler {
	public boolean debugMode = true;
	private String code;
	//This int keeps track of the newest open spot in memory
	//It is used to allocate all the variables
	//The first variable will be allocated to where openMemory points, 
	//'openMemory' will be incremented, and so on for the rest of the variables
	private int openMemory;
	//The label counter
	//Every time there is a code block, a label must be created at the start and end
	//so the program can easily find out where to jump to when there is a conditional
	//or a loop
	private int lblCounter;
	private AssemblyExecutor run;
	
	public Compiler(AssemblyExecutor r){
		run = r;
	}
	//When compiling, the code goes through stages.
	//The first stage is the original source code the user created.
	//The next stage is when all the whitespace is trimmed. This makes the code syntax
	//more predictable and makes compiling easier.
	//The next stage is when all the variables are replaced with their raw
	//memory location. So, for example, a variable "i" might be changed to "$43$", where 43 is
	//the index in memory that "i" points to.
	//The next stage is when the code is turned into a series of statements 
	//separated by semicolons. Essentially, all if's and while's are turned into
	//jumps and assignments.
	//
	public void compile(){
		/*
		 * This stage is when all the whitespace is trimmed. This makes the code 
		 * syntax more predictable and makes compiling easier.
		 */
		trimWhitespace();
		/*
		 * This stage is when all the variables are replaced with their raw 
		 * memory location. So, for example, a variable "i" might be changed to 
		 * "$43$", where 43 is the index in memory that "i" points to. This stage
		 * can be thought of as the part where the memory is allocated.
		 */
		initVariables();
		/*
		 * In this stage, the code blocks are labeled, which means that each brace
		 * has a number that matches it with its corresponding opening/closing
		 * brace. Example: "{{statement;}}" would be changed to {0;{1;statement;}1;}0;
		 * Note that a semicolon is added after each brace and number. This is
		 * so that every brace is now a statement. This will be important in the
		 * next stage. Also all the ifs and whiles are changed to statements
		 * because, for example, "if(something){}" will be changed to
		 * "if(something){0;}0;", where "if(something){0;" is one statement.
		 * After this stage, every statement is separated by semicolons.
		 */
		labelCodeBlocks();
		if(debugMode){
			System.out.println("LABEL_CODE_BLOCKS:\n" + this);
		}
		/*
		 * Converts if statements and while statements to a series of jump,
		 * conditional jump, and assignment operations.
		 */
		convertIfsWhiles();
		if(debugMode){
			System.out.println("CONVERT_IFS_WHILES:\n" + this);
		}
		/*
		 * In this stage, all assignment operations, such as "$5$=5*(6+7);", are
		 * replaced with a series of statements where each one only has two numbers
		 * and an operation. So "$5$=5*(6+7);" will be turned into "$6$=6+7;$5$=5*$6;"
		 * IMPORTANT: After this stage, every single statement can be turned into
		 * a single bytecode instruction.
		 */
		convertArithmetic();
		if(debugMode){
			System.out.println("CONVERT_ARITHMETIC:\n" + this);
		}
		/*
		 * Turns the code into an assembly-like language that will be easier to
		 * convert to bytecode. Every statement will be in the form: 
		 * <operation name> <arg0> <arg1> <arg2>. So, for example, "$4$=5+$6$;"
		 * will be changed to "add 4 5 $6$;". Also all jump statements are changed
		 * to jump in terms of lines of code, rather than labels in the code. This
		 * means that all "<bracket><number>;" statements mean nothing, so they
		 * are all replaced with nop's
		 */
		convertToAssembly();
		if(debugMode){
			System.out.println("CONVERT_TO_ASSEMBLY:\n" + this);
		}
		
		convertToByteCode();
		if(debugMode){
			System.out.println("CONVERT_TO_ASSEMBLY:\n" + this);
		}
		writeByteCode();
	}
	
	private void trimWhitespace(){
		//remove spaces before and after all parenthesis and brackets
		//code = code.replaceAll("\\s*([]{}()\\[;])\\s*", "$1HI");
		//remove whitespace at the start and end of the string
		//code = code.replaceAll("^\\s*|\\s*$", "");
		
		//replace all series of whitespace with a single space
		code = code.replaceAll("\\s+", " ");
		//removes the space between numbers/letters and non letters/numbers
		code = code.replaceAll("(([0-9a-zA-Z]) ([^0-9a-zA-Z]))|(([^0-9a-zA-Z]) ([0-9a-zA-Z]))|(([^0-9a-zA-Z]) ([^0-9a-zA-Z]))", "$2$3$5$6$8$9");
		//this is called again to get any overlapping occurences
		code = code.replaceAll("(([0-9a-zA-Z]) ([^0-9a-zA-Z]))|(([^0-9a-zA-Z]) ([0-9a-zA-Z]))|(([^0-9a-zA-Z]) ([^0-9a-zA-Z]))", "$2$3$5$6$8$9");
		
		if(debugMode){
			System.out.println(code);
		}
	}
	//finds all the initialized variables (which are all at the start),
	//and replaces every occurence of the variable in the code with "$" + memorylocation +"$"
	//all variable declarations should all be in the form "int <variable name>;"
	private void initVariables(){
		//as long as there is a proper variable declaration, keep allocating variables
		while(code.matches("int [a-zA-Z][a-zA-Z0-9]*;.*")){
			//getting the name of the variable
			String var = code.substring(code.indexOf(" ")+1, code.indexOf(";"));
			for(int i = 0; i < 2; i++){
				code = code.replaceAll("([^0-9a-zA-Z])" + var + "([^0-9a-zA-Z])", "$1\\$"+openMemory+"\\$$2");
			}
			openMemory++;
			
			code = code.substring(code.indexOf(";")+1);
			if(debugMode){
				System.out.println(code + " " + var);
			}
		}
		//makes sure that there wasn't an incorrect variable declaration
		//this statement will be true if the while loop ended prematurely
		if(code.matches("int .*")){
			System.out.println("Incorrect variable name syntax.");
		}
	}
	//labels each opening and closing brace with a number the same as its pair
	//For example: "{15;do stuff;}15;"
	private void labelCodeBlocks(){
		for(int i = 0; i < code.length(); i++){
			if(code.charAt(i) == '{'){
				int openBrace = i;
				int closingBrace = indexOfClosingBrace(openBrace, code);
				String statement = code.substring(openBrace, closingBrace);
				statement = "{" + lblCounter + ";" + statement.substring(1, statement.length()-1) + "}" + lblCounter + ";";
				lblCounter++;
				code = code.substring(0, openBrace) + statement + code.substring(closingBrace);
				
			}
		}
	}
	//Converts if's and while's to a series of easily executable statements, so that
	//now every line is a statement separated by a semicolon.
	//There are only assignment, jump, jump if true, and label statements
	private void convertIfsWhiles(){
		//replacing all <var>++ and -- operations with <var> = <var>+or-1
		code = code.replaceAll("(^|[;{}])(\\$[0-9]+\\$)([+-])\\3", "$1$2=$2$31");
		
		//System.out.println(code);
		//Each statement is separated by a semicolon, no exceptions
		//'lastHalf' initially contains all the code, and each statement is pulled
		//off as a whole, changed, and then put on to 'firstHalf'.
		//For while loops, a jump statement needs to be put at the end of the loop,
		//so lastHalf will be changed whenever there is a while loop
		String firstHalf = "";
		String lastHalf = code;
		for(int i = 0; i < lastHalf.length(); i++){
			//if it has found the start of the next statement
			if(lastHalf.charAt(i) == ';'){
				String statement = lastHalf.substring(0, i+1);
				
				if(statement.matches("^((while)).*")){
					String braceID = statement.substring(statement.indexOf("{")+1, statement.indexOf(";"));
					//System.out.println("S" + braceID);
					//                                 1         2      3   4
					statement = statement.replaceAll("^(while)\\((.*)\\)(\\{([0-9]+)\\;)", "$3\\$"+openMemory+"\\$=$2;jif \\$"+openMemory+"\\$;jmp}$4;");
					lastHalf = lastHalf.replaceFirst("\\;(\\}("+braceID+")\\;)", ";jmp{$2;$1");
				}
				//System.out.println(statement);
				lastHalf = lastHalf.substring(i+1);
				firstHalf += statement;
				i = 0;
			}
			code = firstHalf;
		}
		
		
		//TODO replacing all <var>+=<var2> operations with <var> = <var>+<var2>
	}
	
	private void convertArithmetic(){
		String firstHalf = "";
		String lastHalf = code;
		for(int i = 0; i < lastHalf.length(); i++){
			if(lastHalf.charAt(i) == ';'){
				String statement = lastHalf.substring(0, i+1);
				
				if(statement.matches("\\$[0-9]\\$=.*")){
					statement = parseArithmetic(statement);
				}
				
				lastHalf = lastHalf.substring(i+1);
				firstHalf += statement;
				i = 0;
			}
		}
		
		code = firstHalf;
	}
	//Takes a line of code in the form $<memory location>$=<arithmetic expression>;  with no spaces
	//Returns a list of statements such that when executed in order, they will 
	//yeild the same result as original the arithmetic expression.
	//And each statement will be one bytecode expression, 
	//i.e. it will be <memory location>=<something><operation><something>;
	public String parseArithmetic(String code){
		String allOps = "[*/+\\-%\\&|]|==|!=";
		String[] priorities = {"[*/%]", "[+-]", "==|!=|<|>|<=|>=", "[|&]"};
		//String[] avoids = {"", "[^*/%$0-9]", "[^*/\\-+%$0-9]", 
		if(code.matches("\\$[0-9]+\\$=[0-9$]+\\;")){
			return code.replaceAll("(.*)(\\;)", "$1+0$2");
		}
		final String md = "*/";
		final String as = "+-";
		//as 'code' is simplified, instructions will be appended on to the end of 'parsed'
		String parsed = "";
		//arithmetic memory
		int arithMem = openMemory;
		//This loop will keep running until the arithmetic operation ('code') is 
		//condensed down to two numbers and an operation
		while(!code.matches("[0-9$]+=([0-9$]+("+allOps+")[0-9$]+)\\;")){
			code = code.replaceAll("\\(([0-9$]+)\\)", "$1");
			
			String operation = "";
			String avoid = "";
			if(code.matches(".*[^*/$0-9][0-9$]+[*/%][0-9$]+.*")){
				//System.out.println("mult");
				operation = "[*/%]";
				avoid = "";
			}else if(code.matches(".*[0-9$]+[+-][0-9$]+[^*/%$0-9].*")){
				//System.out.println("add");
				operation = "[+-]";
				//it can only do an addition if it finds it in the form <a>+<b><not multiplication or division>
				//'avoid' is the list of characters that can't be directly after
				//an addition/subtraction. It can't be * or / because that means
				//* or / must be done before the + or -.
				//It can't be a $0-9 because that means the regex ended prematurely,
				//and a number will be cut off
				avoid = "[^*/%$0-9]";
			}else if(code.matches(".*[0-9$]+(\\||&|==|!=)[0-9$]+[^*/\\-%+$0-9].*")){
				//System.out.println("logic");
				operation = "\\||&|==|!=";
				avoid = "[^*/\\-+%$0-9]";
			}else{
				break;
			}
			//                      12       3                       4          5
			code = code.replaceFirst("(([0-9$]+("+operation+")[0-9$]+))("+avoid+")(.*)", "\\$"+arithMem+"\\$$4$5\\$"+arithMem+"\\$=$2;");
			code = code.replaceAll("\\(([0-9$]+)\\)", "$1");
			arithMem++;
			parsed += code.substring(code.indexOf(";")+1, code.length());
			code = code.substring(0, code.indexOf(";")+1);
			//System.out.println(parsed + " " + code);
			
		}
		return parsed + code;
	}
	
	private void convertToAssembly(){
		String[] operations = {"\\+","-","\\*","/","%","&","\\|","==","!=","<",">","<=",">="};
		String[] opNames = {"add","sub","mlt","div","mod","and","or","e","ne","l","g","le","ge"};
		//replacing all assignment operations with their assembly equivalent
		for(int j = 0; j < operations.length; j++){
			for(int i = 0; i < 2; i++){
				code = code.replaceAll("(^|\\;)\\$([0-9]+)\\$=([$0-9]+)"+operations[j]+"([$0-9]+)\\;", "$1"+opNames[j]+" $2 $3 $4;");
			}
		}
		
		//The following code looks for all the unconditional jump statements and
		//determines what statement number they are jumping to, then rewrites
		//the jump statement to be "jmp <lines forward>;"
		{
			//the number of statements that have been looked at
			int numStatements = 0;
			String firstHalf = "";
			String lastHalf = code;
			for(int i = 0; i < lastHalf.length(); i++){
				if(lastHalf.charAt(i) == ';'){
					String statement = lastHalf.substring(0, i+1);

					if(statement.matches("jmp[{}][0-9a-zA-Z]+\\;")){
						String searchFor = statement.substring(3, statement.length()-1);
						int indexJumpTo = 0;
						
						String[] statements = code.split(";");
						for(String s : statements){
							if((s).equals(searchFor)){
								break;
							}
							indexJumpTo++;
						}
						//Replaces the "jmp <label>" statement with "jmp <lines forward>".
						//'indexJumpTo' is the number of lines from the start that execution should jump to
						//'numStatements' is how many statements from the start that
						//execution is jumping from, so to get the relative position change,
						//we do (indexJumpTo - numStatements)
						statement = "jmp " + (indexJumpTo - numStatements) + ";";
					}

					lastHalf = lastHalf.substring(i+1);
					firstHalf += statement;
					i = 0;
					numStatements++;
				}
			}
			code = firstHalf;
		}
		
		code = code.replaceAll("[{}][0-9a-zA-Z]+;", "nop;");
		
	}
	private void convertToByteCode(){
		String firstHalf = "";
		String lastHalf = code;
		for(int i = 0; i < lastHalf.length(); i++){
			if(lastHalf.charAt(i) == ';'){
				String statement = lastHalf.substring(0, i);
				
				String[] instruction = statement.split(" ");
				/* 
				 * This code block is so that the instruction knows which number
				 * should be treated as a memory location, and which should be
				 * treated as a number
				 */ 
				{	
					int j = 4;
					if(instruction[0].equals("jif")) 
						j = 1;
					else if(instruction[0].matches("add|sub|mlt|div|mod|and|or|e|ne|l|g|le|ge"))
						j = 2;
					for(; j < instruction.length; j++){
						if(instruction[j].matches("\\$[0-9]+\\$")){
							instruction[0]+="l";
						}else{
							instruction[0]+="v";
						}
						instruction[j] = instruction[j].replaceAll("\\$([0-9]+)\\$", "$1");
					}
				}
//				if(instruction[0].matches("(add)|(sub)|(mlt)|(div)|(mod)|(and)|(or)|(e)|(ne)")){
//					if(instruction.length != 4){
//						System.out.println("Error: incorrect number or arguments in assignment operation.");
//					}
//					if(instruction[2].matches("\\$[0-9]+\\$")){
//						instruction[0]+="l";
//					}else{
//						instruction[0]+="v";
//					}
//					if(instruction[3].matches("\\$[0-9]+\\$")){
//						instruction[0]+="l";
//					}else{
//						instruction[0]+="v";
//					}
//					for(int j = 1; j < 4; j++){
//						instruction[j] = instruction[j].replaceAll("\\$([0-9]+)\\$", "$1");
//					}
//				}
				//replacing the instruction type name with its bytecode id
				System.out.println(""+instruction[0]);
				instruction[0] = AssemblyExecutor.possibleInstructions.get(instruction[0]).toString();
				
				statement = "";
				for(int j = 0; j < 4; j++){
					if(j >= instruction.length){
						statement += " " + 0;
					}else{
						statement += " " + instruction[j];
					}
				}
				//System.out.println(statement);
				
				lastHalf = lastHalf.substring(i+1);
				firstHalf += statement;
				i = 0;
			}
		}
		code = firstHalf;
	}
	
	private void writeByteCode(){
		String[] byteCode = code.trim().split(" ");
		for(int i = 0; i < byteCode.length; i++){
			run.putByte(i, Integer.parseInt(byteCode[i]));
		}
	}
	//finds the index of the closing brace that corresponds to the open brace at index
	//'openBrace' in 'code'
	private int indexOfClosingBrace(int openBrace, String code){
		//This is incremented whenever the loop finds an open brace, and decremented
		//whenever it finds a closing brace.
		//So when it reaches zero, it will have reached the closing brace
		//corresponding to the original opening brace
		int numOpenBraces = 1;
		int i = 0;
		for(i = openBrace + 1; numOpenBraces > 0 && i < code.length(); i++){
			if(code.charAt(i) == '{'){
				numOpenBraces++;
			}else if(code.charAt(i) == '}'){
				numOpenBraces--;
			}
		}
		//if numOpenBraces is not zero, that means the loop never found the closing
		//brace an reached the end of the string
		if(numOpenBraces > 0){
			return -1;
		}else{
			return i;
		}
	}
	public void setFile(File file){
		try{
			byte[] bytes = Files.readAllBytes(file.toPath());
			this.code = new String(bytes);
		}catch(IOException e){
			System.err.println("File not found " + file);
			e.printStackTrace();
		}
		
	}
	public String toString(){
		return code.replaceAll("\\;", ";\n");
	}
	public void println(){
		System.out.println(this);
	}
}
