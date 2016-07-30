/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unnamedgame;
 
import java.io.File;
import java.io.IOException;
import render.RenderGame;
import update.AssemblyExecutor;
import update.Tile;
import update.UpdateGame;
import update.actions.PutEntityAction;
import update.entities.TestEntity;
import update.Compiler;
/**
 *
 * @author eric
 */
public class UnnamedGame {
	/*
	 * Some notes about organization:
	 * Any method that starts with "render" should only be called for rendering purposes.
	 *
	 */
    public static void main(String[] args){
//		AssemblyExecutor run = new AssemblyExecutor(new int[1000], new int[50]);
//		File f = new File("res/code/Hello.txt");
//		Compiler c = new Compiler(run);
//		c.setFile(f);
//		c.compile();
//		for(int i:run.getInstructions()){
//			System.out.print(i + " ");
//		}
//		System.out.println();
//		run.execute();
//
//		for(int i:run.getMemory()){
//			System.out.println(i);
//		}
//		System.out.println();
//
//		System.exit(0);

		UpdateGame updater = new UpdateGame();
		//new Thread(updater).start();
		updater.addAction(new PutEntityAction(new TestEntity(), new int[]{0,0}));
//		updater.addAction((Tile[][] tiles) -> {
//			tiles[1][1].setEntity(new TestEntity());
//		});
		
		RenderGame renderer = new RenderGame(updater);
		//new Thread(renderer).start();
		
		Screen gameScreen = new GameScreen(updater, renderer);
		GUIHandler gui = new GUIHandler();
		gui.addScreen(gameScreen, "game");
		gui.setActiveScreen("game");
		gui.startRunning();
    }
    
}
