/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package render;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;
import javax.imageio.ImageIO;
import update.Tile;

/**
 *
 * @author eric
 */
public class TileRenderer {
	public static final HashMap<String, BufferedImage> TEXTURES = new HashMap<>();
	static{//loading all textures
		File textureRegistry = new File("res/texture-registry.txt");
		try{
			Scanner reader = new Scanner(textureRegistry);

			while(reader.hasNextLine()){
				String line = reader.nextLine();
				if(line.charAt(0) != '#') {
					String textureName = line.substring(0, line.indexOf(" "));
					File textureFile = new File(line.substring(line.indexOf(" ") + 1));
					BufferedImage img = null;

					try {
						img = ImageIO.read(textureFile);
						TEXTURES.put(textureName, img);
					} catch (IOException e) {
						System.err.println("Missing texture file at " + textureFile.getAbsolutePath());
					}
				}
			}
		}catch(FileNotFoundException e){
			System.err.println("Missing texture registry file at " + textureRegistry.getAbsolutePath());
		}
	}
	//Renders the given tile
	public void renderTile(Tile tile, int x, int y, int tileSize, Graphics2D g2){
		String renderInfo = tile.renderGetInfo();
		while(!renderInfo.isEmpty()){
			//cut off first @
			renderInfo = renderInfo.substring(1);
			
			int endTex = renderInfo.indexOf("@");
			endTex = endTex==-1 ? renderInfo.length() : endTex;
			//System.out.println(renderInfo);
			String textureName = renderInfo.substring(0, endTex);
			renderInfo = renderInfo.substring(endTex);
			try{
				//System.out.println(x * tileSize + " " + y*tileSize + " " + TEXTURES.get(textureName) + " " + textureName);
				g2.drawImage(TEXTURES.get(textureName), (x)*tileSize, (y)*tileSize, null);
			}catch(NullPointerException e){
				System.err.println("Failed to retrieve texture \"" + textureName + "\"");
			}
		}
//		System.out.println(index + " "+ renderInfo.length());
//		String textureName = renderInfo.substring(1, renderInfo.length());
//		g2.drawImage(TEXTURES.get(textureName), 0, 0, null);
	}
}
