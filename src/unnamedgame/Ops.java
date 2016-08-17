/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unnamedgame;

/**
 *
 * @author eric
 */
public class Ops {
	public static boolean matrixContainsPoint(Object[][] mat, int[] point){
		return point[0] >= 0 && point[0] < mat.length && point[1] >= 0 && point[1] < mat[point[0]].length;
	}
	public static boolean matrixContainsPoint(Object[][] mat, int x, int y){
		return x >= 0 && x < mat.length && y >= 0 && y < mat[x].length;
	}
	public static boolean isInBounds(int width, int height, int[] point){
		return point[0] >= 0 && point[0] < width && point[1] >= 0 && point[1] < height;
	}
	public static boolean isInBounds(int width, int height, int x, int y){
		return x >= 0 && x < width && y >= 0 && y < height;
	}
	
	public static float roundFloat(float number, int scale) {
		int pow = 10;
		for (int i = 1; i < scale; i++)
			pow *= 10;
		float tmp = number * pow;
		return (float) (int) ((tmp - (int) tmp) >= 0.5f ? tmp + 1 : tmp) / pow;
	}
}
