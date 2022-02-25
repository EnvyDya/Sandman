package com.sandman.game.tools;

import java.util.ArrayList;

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
import com.sandman.game.sprites.Boulder;
import com.sandman.game.sprites.Feuille;
import com.sandman.game.sprites.InteractiveTileObject;
import com.sandman.game.sprites.Tondeuse;
import com.sandman.game.sprites.Water;

public class B2WorldCreator {
	World world;
	
	//Liste de toutes nos interactives tiles
    public ArrayList<InteractiveTileObject> interactiveTiles = new ArrayList<InteractiveTileObject>();

	//Liste des entités
	private Tondeuse t;
	private ArrayList<Feuille> feuille;
	private ArrayList<Boulder> boulder;
	
	//Compteurs des spawn feuilles/pierres
	private float timerFeuilles;
	private float timerBoulder;
    
	public B2WorldCreator(World world, TiledMap map) {
		   this.world = world;
		   BodyDef bdef = new BodyDef();
	       PolygonShape shape = new PolygonShape();
	       FixtureDef fdef = new FixtureDef();
	       Body body;
	       
	       feuille = new ArrayList<Feuille>();
	       timerFeuilles = 3f;
	       
	       boulder = new ArrayList<Boulder>();
	       timerBoulder = 2f;
	       
	       //Cree les box2D du sol
	       for(MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)) {
	    	   Rectangle rect = ((RectangleMapObject) object).getRectangle();
	    	   
	    	   bdef.type = BodyDef.BodyType.StaticBody;
	    	   bdef.position.set((rect.getX() + rect.getWidth()/2)/Sandman.PPM, (rect.getY() + rect.getHeight()/2)/Sandman.PPM);
	    	   
	    	   body = world.createBody(bdef);
	    	   
	    	   shape.setAsBox((rect.getWidth()/2)/Sandman.PPM, (rect.getHeight()/2)/Sandman.PPM);
	    	   fdef.shape = shape;
	    	   body.createFixture(fdef);
	       }
	    
		    //Cree les box2D de l'eau
		    for(MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {
		 	   Rectangle rect = ((RectangleMapObject) object).getRectangle();
		 	   
		 	   Water w = new Water(world, rect);
		 	   interactiveTiles.add(w);
		    }
			
			//Ajout de la tondeuse
			t = new Tondeuse(world);
			interactiveTiles.add(t);
			
			//Ajout de la feuille
			Feuille f = new Feuille(world);
			feuille.add(f);
			interactiveTiles.add(f);
			
			//Ajout pierre
			Boulder b = new Boulder(world);
			boulder.add(b);
			interactiveTiles.add(b);
	}
	
	public void update(float dt) {
		timerFeuilles -= dt;
		if(timerFeuilles <= 0) {
			timerFeuilles = 3f;
			Feuille nf = new Feuille(world);
			feuille.add(nf);
			interactiveTiles.add(nf);
		}
		
		timerBoulder -= dt;
		if(timerBoulder <= 0) {
			timerBoulder = 2f;
			Boulder nb = new Boulder(world);
			boulder.add(nb);
			interactiveTiles.add(nb);
		}
		
		for(int i = 0; i<feuille.size(); i++) {
			Feuille f = feuille.get(i);
			if(f.getY() < -10) {
				interactiveTiles.remove(f);
				feuille.remove(f);
				i--;
			}
		}
		
		for(int i = 0; i<boulder.size(); i++) {
			Boulder b = boulder.get(i);
			if(b.getY() < -10) {
				interactiveTiles.remove(b);
				boulder.remove(b);
				i--;
			}
		}
	}

	public Tondeuse getTondeuse() {
		return t;
	}
	
	public ArrayList<Feuille> getFeuille() {
		return feuille;
	}
	
	public ArrayList<Boulder> getBoulder() {
		return boulder;
	}

	//TODO Faire libération de mémoire si besoin
}
