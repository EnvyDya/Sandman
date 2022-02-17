package com.sandman.game.sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.sandman.game.Sandman;

/**
 * 
 * Classe permettant définir un objet interactif
 *
 */
public abstract class InteractiveTileObject extends Sprite{
	protected World world;
	protected Body body;
	protected Fixture fixture;
	protected boolean gel;
	
	public InteractiveTileObject(World world, Rectangle bounds) {
		this.world = world;
		defineObject(bounds);
		gel = false;
	}

	//Constructeur pour les sprites
	public InteractiveTileObject(TextureRegion region,World world,Rectangle bounds) {
		super(region);
		this.world = world; 
		defineObject(bounds);
		gel = false;
	}

	//Creer la hitbox de l'objet
	public void defineObject(Rectangle bounds){
		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();
		PolygonShape shape = new PolygonShape();
		
		//Création du body
		bdef.type = BodyDef.BodyType.StaticBody;
 	    bdef.position.set((bounds.getX() + bounds.getWidth()/2)/Sandman.PPM, (bounds.getY() + bounds.getHeight()/2)/Sandman.PPM);
 	    body = world.createBody(bdef);
 	  
 	    //Création de la fixture
 	    shape.setAsBox((bounds.getWidth()/2)/Sandman.PPM, (bounds.getHeight()/2)/Sandman.PPM);
 	    fdef.shape = shape;
 	    fixture = body.createFixture(fdef);
	}
	
	/**
	 * Fonction à définir sur l'intéractivité de notre tile, c'est ici que seront définis les comportements en cas d'arrêt du temps
	 */
	public abstract void onClick();
	
	public boolean isFrozen() {
		return gel;
	}
}
