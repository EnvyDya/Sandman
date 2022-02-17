package com.sandman.game.sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
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
	protected TiledMap map;
	protected TiledMapTile tile;
	protected Rectangle bounds;
	protected Body body;
	protected Fixture fixture;
	
	public InteractiveTileObject(World world, TiledMap map, Rectangle bounds) {
		this.world = world;
		this.map = map;
		this.bounds = bounds;
		
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
}
