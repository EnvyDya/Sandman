package com.sandman.game.sprites;

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

public class Bed extends Sprite{
	private Body body;
	private Fixture fixture;
	
    //Constructeur
    public Bed(World world, float posX, float posY){
    	super(new TextureRegion(new Texture("images/bedsprite.png")));
		
		
		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();
		PolygonShape shape = new PolygonShape();
		
		//Création du body
		bdef.type = BodyType.StaticBody;
 	    bdef.position.set(posX, posY);
 	    body = world.createBody(bdef);
 	    body.setActive(true);
	    
	    
 	    //Création de la fixture
 	    shape.setAsBox(40/2/Sandman.PPM, 15/2/Sandman.PPM);
 	    fdef.shape = shape;
 	    fixture = body.createFixture(fdef);
 	    fixture.setUserData(this);
 	    
 	   setBounds(0, 0, 40/Sandman.PPM, 20/Sandman.PPM);	
       setPosition(posX - 40/2/Sandman.PPM, posY - 15/2/Sandman.PPM);
    }

    public void update(float dt){
	}

    
}
