package com.sandman.game.tools;

import java.util.ArrayList;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.sandman.game.Sandman;
import com.sandman.game.Scene.Level;
import com.sandman.game.sprites.InteractiveTileObject;

public abstract class B2WorldCreator{
	protected World world;
	private MenuButton bouton;
	
	//Liste de toutes nos interactives tiles
    public ArrayList<InteractiveTileObject> interactiveTiles = new ArrayList<InteractiveTileObject>();

	public B2WorldCreator(World world, TiledMap map,Level level) {
		   this.world = world;
		   BodyDef bdef = new BodyDef();
	       PolygonShape shape = new PolygonShape();
	       FixtureDef fdef = new FixtureDef();
	       Body body;

		   bouton = new MenuButton(world, level);
		   interactiveTiles.add(bouton);

	       //Cree les box2D du sol
			for (MapLayer layer : map.getLayers()) {
				if(layer.getName().equals("Sol")){
					for(MapObject object : layer.getObjects().getByType(RectangleMapObject.class)) {
						Rectangle rect = ((RectangleMapObject) object).getRectangle();
						
						bdef.type = BodyDef.BodyType.StaticBody;
						bdef.position.set((rect.getX() + rect.getWidth()/2)/Sandman.PPM, (rect.getY() + rect.getHeight()/2)/Sandman.PPM);
						
						body = world.createBody(bdef);					
						shape.setAsBox((rect.getWidth()/2)/Sandman.PPM, (rect.getHeight()/2)/Sandman.PPM);
						fdef.shape = shape;
						body.createFixture(fdef);
					}
					break;
				}
			}
	       
			
	}

	public MenuButton getBouton() {
		return bouton;
	}
	
	public abstract void update(float dt);

	//TODO Faire libération de mémoire si besoin
}
