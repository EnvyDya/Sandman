package com.sandman.game.tools;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.sandman.game.Sandman;
import com.sandman.game.Scene.Level;

public class Hud extends Sprite{
	
	//TODO: A modifier pour convenir au HUD de notre jeu
	private World world;
	private Level level;
	private Body body;
	private Fixture fixture;
	
	public Hud(World world, Level level) {
		super(new TextureRegion(new Texture("images/clock.png")));
		this.world = world;
		this.level = level;
		
		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();
		PolygonShape shape = new PolygonShape();
		
		//Création du body
		bdef.type = BodyType.StaticBody;
 	    bdef.position.set(0, 0);
 	    body = world.createBody(bdef);
 	    body.setActive(false);
	    
	    
 	    //Création de la fixture
 	    shape.setAsBox(70/Sandman.PPM, 70/Sandman.PPM);
 	    fdef.shape = shape;
 	    fixture = body.createFixture(fdef);
	}
	
	public void update() {
 	    setBounds((level.getCamera().position.x)+5.7f, (level.getCamera().position.y)/Sandman.PPM - .7f, 150/Sandman.PPM, 150/Sandman.PPM);
	}
}
