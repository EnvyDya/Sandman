package com.sandman.game.sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;

public class Water extends InteractiveTileObject{

	public Water(World world, TiledMap map, Rectangle bounds) {
		super(world, map, bounds);
		fixture.setUserData(this);
	}
	
	/**
	 * Fonction de transformation de l'eau en glace
	 */
	public void onClick() {
		System.out.println("Je deviens de la glace");
	}
	
}
