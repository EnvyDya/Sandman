package com.sandman.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;

public class Water extends InteractiveTileObject{

	public Water(World world, TiledMap map, Rectangle bounds) {
		super(world, map, bounds);
		fixture.setUserData(this);
		
	}

	@Override
	public void onClick() {
		// TODO Auto-generated method stub
		if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT))
		Gdx.app.log("Water", "Click");
		
	}
	
}
