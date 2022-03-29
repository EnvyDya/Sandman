package com.sandman.game.tools;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.sandman.game.Sandman;
import com.sandman.game.Scene.Level;

public class Needle extends Sprite{
		private World world;
		private Level level;
		private Body body;
		private Fixture fixture;
		
		public Needle(World world, Level level) {
			super(new TextureRegion(new Texture("images/needle.png")));
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
	 	    shape.setAsBox(70/Sandman.PPM, 750/Sandman.PPM);
	 	    fdef.shape = shape;
	 	    fixture = body.createFixture(fdef);
	 	    this.setOriginCenter();
	 	    this.setOrigin(getOriginX()/Sandman.PPM - .5f, getOriginY()/Sandman.PPM - 5.7f);
		}
		
		public void update() {
	 	    setBounds((level.getCamera().position.x)+11.1f, (level.getCamera().position.y)/Sandman.PPM + 2.5f, 6/Sandman.PPM, 75/Sandman.PPM);
	 	    if(level.getPerso().getGel()) {
	 	    	rotate(1.2f);
	 	    }else {
	 	    	this.setRotation(0);
	 	    }
	 	    
		}
}
