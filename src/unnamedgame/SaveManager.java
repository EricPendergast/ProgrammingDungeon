package unnamedgame;

import update.UpdateGame;

import java.io.*;

/**
 * Created by eric on 8/4/16.
 */
public class SaveManager{
	public static UpdateGame readSaveFile(File saveFile){
		UpdateGame save = null;
		try{
			if(!saveFile.exists()){
				return null;
			}
			FileInputStream fileOut = new FileInputStream(saveFile);
			ObjectInputStream objOut = new ObjectInputStream(fileOut);
			save = (UpdateGame)(objOut.readObject());
		}catch(IOException | ClassNotFoundException | ClassCastException e){
			e.printStackTrace();
			return null;
		}
		
		return save;
	}
	public static void writeSaveFile(UpdateGame save, File saveFile){
		try{
			if(!saveFile.exists()){
				saveFile.createNewFile();
			}
			FileOutputStream fileOut = new FileOutputStream(saveFile);
			ObjectOutputStream objOut = new ObjectOutputStream(fileOut);
			objOut.writeObject(save);
		}catch(IOException e){e.printStackTrace();}
	}
}
