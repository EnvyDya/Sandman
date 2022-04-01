package com.sandman.game.tools;

import java.util.ArrayList;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.World;
import com.sandman.game.sprites.Cafard;
import com.sandman.game.sprites.Egg;
import com.sandman.game.sprites.Knife;

public class B2WorldCreatorCuisine extends B2WorldCreator {

	//Liste des entités
	private ArrayList<Egg> eggs;
	private Knife knife;
	private Cafard cafard;
	
	//Compteurs des spawn Oeufs
	private float timerEgg1;
	private float timerEgg2;
	private float timerEgg3;
    
  public B2WorldCreatorCuisine(World world, TiledMap map) {
	  super(world, map);

	  //Ajout du couteau
	  knife = new Knife(world, 728, 128);
	  interactiveTiles.add(knife);

	  //Ajout du cafard
	  cafard = new Cafard(world, 1520, -50);
	  interactiveTiles.add(cafard);

	  eggs = new ArrayList<Egg>();
	  timerEgg1 = 0.8f;
	  timerEgg2 = 1f;
	  timerEgg3 = 0.5f;

  }
  
  @Override
  public void update(float dt) {

    //Mise à jour des timers
	timerEgg1 -= dt;
	timerEgg2 -= dt;
	timerEgg3 -= dt;

    gestionEgg();
  }

  private void gestionEgg() {
    //Ajout des oeufs
	Egg nf;
	//3 Oeufs spawn
		if(timerEgg1 <= 0) {
			timerEgg1 = 2f;

			nf = new Egg(world, 48,320);
			eggs.add(nf);
			interactiveTiles.add(nf);
		}
		if(timerEgg2 <= 0){
			timerEgg2 = 2f;

			nf = new Egg(world, 80,320);
			eggs.add(nf);
			interactiveTiles.add(nf);
		}
		if(timerEgg3 <= 0){
			timerEgg3 = 2f;

      		nf = new Egg(world, 112,320);
			eggs.add(nf);
			interactiveTiles.add(nf);
		}
		
		//Suppression des oeufs
		for(int i = 0; i<eggs.size(); i++) {
			Egg f = eggs.get(i);
			if(f.getY() < -10) {
				interactiveTiles.remove(f);
				eggs.remove(f);
				i--;
			}
		}
  }

  public ArrayList<Egg> getEggs() {
      return eggs;
  }

  public Knife getKnife() {
	  return knife;
  }

  public Cafard getCafard() {
	  return cafard;
  }
    
}
