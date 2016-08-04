/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package update;

import java.io.Serializable;

/**
 *
 * @author eric
 */
public interface Action extends Serializable{
	public void act(Tile[][] tiles);
}
