package com.sandman.game.sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.sandman.game.Sandman;

public class Perso extends Sprite {
    public World world;
    public Body b2body;
    
    public Perso(World world) {
    	this.world = world;
    	definePerso();
    }
    
    public void definePerso() {
    	BodyDef bdef = new BodyDef();
    	bdef.position.set(32/Sandman.PPM, 60/Sandman.PPM);
    	bdef.type = BodyDef.BodyType.DynamicBody;
    	b2body = world.createBody(bdef);
    	
    	FixtureDef fdef = new FixtureDef();
    	CircleShape shape = new CircleShape();
    	shape.setRadius(15/Sandman.PPM);
    	
    	fdef.shape = shape;
    	b2body.createFixture(fdef);
    	
    }
}
