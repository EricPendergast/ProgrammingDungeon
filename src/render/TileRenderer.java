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
import java.text.ParseException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
				if(!line.isEmpty() && line.charAt(0) != '#') {
					//Matcher match = Pattern.compile("^([a-zA-Z_0-9]) ([^|]+)\\|([0-9]+) ([0-9]+) ([0-9]+) ([0-9]+)").matcher(line);
					Matcher match = Pattern.compile("([a-zA-Z_0-9]+) ([^|]+)\\|([0-9]+) ([0-9]+) ([0-9]+) ([0-9]+)").matcher(line);
					
					if(match.find()){
						String textureName = match.group(1);
						File textureFile = new File(match.group(2));
						try {
							BufferedImage img = ImageIO.read(textureFile);
							TEXTURES.put(textureName, img.getSubimage(
									Integer.parseInt(match.group(3)),
									Integer.parseInt(match.group(4)),
									Integer.parseInt(match.group(5)),
									Integer.parseInt(match.group(6))));
						} catch (IOException e) {
							System.err.println("Missing texture file at " + textureFile.getAbsolutePath());
						} catch (NumberFormatException e){
							System.err.println("Error in " + textureRegistry + "\nLine: " + line);
							e.printStackTrace();
						}
					}else{
						System.err.println("Error in " + textureRegistry + "\nLine: " + line);
					}
				}
			}
		}catch(FileNotFoundException e){
			System.err.println("Missing texture registry file at " + textureRegistry.getAbsolutePath());
		}
	}
	//Renders the given tile
	public void renderTile(Tile tile, int x, int y, int tileSize, Graphics2D g2){
		//This for loop iterates through every texture, along with its parameters
		for(String texInfo : tile.renderGetInfo().split("@")){
			float xOffset = 0;
			float yOffset = 0;
			String texName;

			//Finding the name of the texture
			Matcher match = Pattern.compile("^([0-9_a-zA-Z]+)(#|$)").matcher(texInfo);
			if(match.find()){
				texName = match.group(1);
				//System.out.println("Name " + texName + " " + texInfo);
			}else
				continue;
			
			
			{//Finding the x and y offset
				match.usePattern(Pattern.compile("#x(-?[0-9.]+)"));
				match.reset();
				if(match.find()){
					try{
						xOffset = Float.parseFloat(match.group(1));
					}catch(NumberFormatException e){}
				}
				
				match.usePattern(Pattern.compile("#y(-?[0-9.]+)"));
				match.reset();
				if(match.find()){
					try{
						yOffset = Float.parseFloat(match.group(1));
					}catch(NumberFormatException e){}
				}
			}
			{//Drawing the image
				try{
					g2.drawImage(TEXTURES.get(texName), (int)((x+xOffset)*tileSize), -(int)((y+yOffset)*tileSize), null);
				}catch(NullPointerException e){
					System.err.println("Failed to retrieve texture \"" + texName + "\"");
				}
			}
		}
//		String renderInfo = tile.renderGetInfo();
//		while(!renderInfo.isEmpty()){
//
//			//cut off first @
//			renderInfo = renderInfo.substring(1);
//
//			int endTex = renderInfo.indexOf("@");
//			endTex = endTex==-1 ? renderInfo.length() : endTex;
//			//System.out.println(renderInfo);
//			String textureName = renderInfo.substring(0, endTex);
//			renderInfo = renderInfo.substring(endTex);
//			try{
//				//System.out.println(x * tileSize + " " + y*tileSize + " " + TEXTURES.get(textureName) + " " + textureName);
//				g2.drawImage(TEXTURES.get(textureName), (x)*tileSize, -(y)*tileSize, null);
//			}catch(NullPointerException e){
//				System.err.println("Failed to retrieve texture \"" + textureName + "\"");
//			}
//		}
//		System.out.println(index + " "+ renderInfo.length());
//		String textureName = renderInfo.substring(1, renderInfo.length());
//		g2.drawImage(TEXTURES.get(textureName), 0, 0, null);
	}
}
