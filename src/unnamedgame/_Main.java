/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unnamedgame;
 
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import render.RenderGame;
import update.UpdateGame;
import update.actions.PutEntityAction;
import update.entities.Player;
import update.entities.TestEntity;

/**
 *
 * @author eric
 */
public class _Main{
	/*
	 * Some notes about organization:
	 * Any method that starts with "render" should only be called for rendering purposes.
	 *
	 */
    public static void main(String[] args){
		if(false){
			try{
				TestEntity e = new TestEntity();
				FileOutputStream fileOut = new FileOutputStream("res/saves/testSave.txt");
				ObjectOutputStream objOut = new ObjectOutputStream(fileOut);
				objOut.writeObject(e);
				
			}catch(IOException e){e.printStackTrace();}
		}
//		AssemblyExecutor run = new AssemblyExecutor(new int[1000], new int[15]);
//		File f = new File("res/code/Hello.txt");
//		Compiler c = new Compiler(run);
//		c.debugMode = true;
//		c.readCodeFromFile(f);
//		c.compile();
//		for(int i:run.getInstructions()){
//			System.out.print(i + " ");
//		}
//		System.out.println();
//		run.execute();
//
//		System.out.println(run.dumpMemory());
//		System.out.println();
//
//		System.exit(0);
		
		if(true){
			UpdateGame updater = SaveManager.readSaveFile(new File("res/saves/testSave.txt"));
			if(updater == null){
				updater = new UpdateGame();
				updater.addAction(new PutEntityAction(new TestEntity(), new int[]{0, 0}));
				updater.addPlayer(new Player(), new int[]{0,1});
			}
			RenderGame renderer = new RenderGame(updater);
			
			Screen gameScreen = new GameScreen(updater, renderer);
			
			GUIHandler gui = new GUIHandler();
			gui.addScreen(gameScreen, "game screen");
			gui.setActiveScreen("game screen");
			gui.startRunning();
			
			
			updater.stop();
			renderer.stop();
			
			SaveManager.writeSaveFile(updater, new File("res/saves/testSave.txt"));
			System.out.println("END OF PROGRAM");
		}
    }
    
}
