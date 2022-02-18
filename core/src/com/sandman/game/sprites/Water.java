package com.sandman.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.sandman.game.Sandman;

public class Water extends InteractiveTileObject{

	public Water(World world, Rectangle bounds) {
		super(new TextureRegion(new Texture(Gdx.files.internal("iceFilter.png"))),world, bounds, BodyDef.BodyType.StaticBody);
		fixture.setUserData(this);
		
		//definit la taille et la position de la texture
		setBounds(0, 0, bounds.width/Sandman.PPM, bounds.height/Sandman.PPM);	
        setPosition(bounds.x/Sandman.PPM, bounds.y/Sandman.PPM);
        body.setActive(false);
	}
	
	/**
	 * Fonction de transformation de l'eau en glace
	 */
	public void onClick() {
		gel = !gel;
		body.setActive(gel);
	}
	
}