package com.sandman.game.tools;

import java.util.ArrayList;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.World;
import com.sandman.game.sprites.ButtonObject;
import com.sandman.game.sprites.Cafard;
import com.sandman.game.sprites.Drop;
import com.sandman.game.sprites.Egg;
import com.sandman.game.sprites.Knife;

public class B2WorldCreatorCuisine extends B2WorldCreator {

	//Liste des entités
	private ArrayList<Egg> eggs;
	private ArrayList<Drop> drop;
	private Knife knife;
	private Cafard cafard;
	private ButtonObject bouton1;
	
	//Compteurs des spawn Oeufs
	private float timerEgg1;
	private float timerEgg2;
	private float timerEgg3;

	//Compteur spawn goute
	private float timergoute;
    
  public B2WorldCreatorCuisine(World world, TiledMap map) {
	  super(world, map);

	  //Ajout du couteau
	  knife = new Knife(world, 728, 128);
	  interactiveTiles.add(knife);

	  //Ajout du cafard
	  cafard = new Cafard(world, 1520, -50);
	  interactiveTiles.add(cafard);

	  //Ajout Bouton Feu
	  bouton1 = new ButtonObject(world, 400, 183);
	  interactiveTiles.add(bouton1);

	  //Init Eggs
	  eggs = new ArrayList<Egg>();
	  timerEgg1 = 0.8f;
	  timerEgg2 = 1f;
	  timerEgg3 = 0.5f;

	  //Init Drop
	  drop = new ArrayList<Drop>();
	  timergoute = 3f;

  }
  
  @Override
  public void update(float dt) {

    //Mise à jour des timers
	timerEgg1 -= dt;
	timerEgg2 -= dt;
	timerEgg3 -= dt;
	timergoute -= dt;

    gestionEgg();
	gestionGoute();
  }

  private void gestionGoute() {
	  //Ajout des Drops
		if(timergoute <= 0) {
			timergoute = 3f;
			
			//Drop spawn
			Drop nd = new Drop(world, 224,304);
			drop.add(nd);
			interactiveTiles.add(nd);
		}
		
		//Suppression des Drops
		for(int i = 0; i<drop.size(); i++) {
			Drop d = drop.get(i);
			if(d.getY() < -10) {
				interactiveTiles.remove(d);
				drop.remove(d);
				i--;
			}
		}
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

  public ArrayList<Drop> getDrop() {
	  return drop;
  }

  public ButtonObject getBouton1() {
	  return bouton1;
  }
    
}
