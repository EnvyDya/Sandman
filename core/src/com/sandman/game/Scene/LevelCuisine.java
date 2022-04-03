package com.sandman.game.Scene;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.sandman.game.Sandman;
import com.sandman.game.sprites.Bed;
import com.sandman.game.sprites.ButtonObject;
import com.sandman.game.sprites.Cafard;
import com.sandman.game.sprites.Drop;
import com.sandman.game.sprites.Egg;
import com.sandman.game.sprites.Fire;
import com.sandman.game.sprites.Knife;
import com.sandman.game.sprites.Perso;
import com.sandman.game.tools.B2WorldCreatorCuisine;
import com.sandman.game.tools.ColisionListener;

public class LevelCuisine extends Level{

  //Gestionnaire de colision
	private ColisionListener colision;

  //Entité variable
  private Bed lit;
  private ArrayList<Egg> eggs;
  private ArrayList<Drop> drop;
  private Knife knife;
  private Cafard cafard;
  private ButtonObject bouton1;
  private Fire feu;

  public LevelCuisine(final Sandman game) {
    super(game, "images/kitchen.tmx", "sounds/themecuisine.wav", 20f);

    //Création et gestion des body
    worldCreator = new B2WorldCreatorCuisine(world, map, this);

    //Player variable
    speed = 1.5f;
    maxSpeed = 5;
    jumpForce = 15f;

    //Initialisation Entités
    player = new Perso(this,16/Sandman.PPM,224/Sandman.PPM);
    lit = new Bed(world, 1800/Sandman.PPM, 39/Sandman.PPM);
    mb = ((B2WorldCreatorCuisine) worldCreator).getBouton();
    eggs = ((B2WorldCreatorCuisine) worldCreator).getEggs();
    knife = ((B2WorldCreatorCuisine) worldCreator).getKnife();
    cafard = ((B2WorldCreatorCuisine) worldCreator).getCafard();
    drop = ((B2WorldCreatorCuisine) worldCreator).getDrop();
    bouton1 = ((B2WorldCreatorCuisine) worldCreator).getBouton1();
    feu = ((B2WorldCreatorCuisine) worldCreator).getFeu();


    //Initialise les colisions
    colision = new ColisionListener(player);
    world.setContactListener(colision);
          
  }

  @Override
  public void borderManagement() {
    if(player.b2body.getPosition().x < 1) {
      player.b2body.setTransform(1, player.b2body.getPosition().y, player.b2body.getAngle());
      player.b2body.setLinearVelocity(0, player.b2body.getLinearVelocity().y);
    }
    else if (player.b2body.getPosition().x > 114) {
      player.b2body.setTransform(114, player.b2body.getPosition().y, player.b2body.getAngle());
      player.b2body.setLinearVelocity(0, player.b2body.getLinearVelocity().y);
    }
  }

  @Override
  public void cameraHandle() {
    //Gestion de la caméra horizontale
    if(player.b2body.getPosition().x >= 15 && player.b2body.getPosition().x <= 100) {
        camera.position.x = player.b2body.getPosition().x;
    }
    if(player.b2body.getPosition().y >= 6 && player.b2body.getPosition().y <= 20) {
        camera.position.y = player.b2body.getPosition().y+4;
    }
  }

  @Override
  public void render(float delta) {
    update(delta);
    //On met tout au noir pour nettoyer l'écran
	  Gdx.gl.glClearColor(0, 0, 0, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	
	  renderer.render();
	
	  //rendu du joueur
	  game.batch.setProjectionMatrix(camera.combined);
    game.batch.begin();
		player.draw(game.batch);
    lit.draw(game.batch);
    knife.draw(game.batch);
    cafard.draw(game.batch);
    bouton1.draw(game.batch);
    feu.draw(game.batch);
    hud.draw(game.batch);
		needle.draw(game.batch);
		mb.draw(game.batch);

		if(player.getGel()){
	    player.getObjetGel().draw(game.batch);
		}

    //On update et on dessine en même temps pour réduire la complexité d'un parcours
		for(Egg e : eggs) {
			e.update();
			e.draw(game.batch);
		}

    for (Drop d : drop) {
      d.update();
      d.draw(game.batch);
    }
		
	  game.batch.end();
	
	  //Affiche les box2d dans le jeu
	  b2dr.render(world, camera.combined);
        
    }

    @Override
    public void update(float dt) {
      System.out.println(camera.position);
      player.handleInput(dt);
	    player.update(dt);
      knife.update();
      cafard.update(dt);
      bouton1.update(dt);
      feu.update(dt);
	    worldCreator.update(dt);
	    borderManagement();
	
	    //On rafraichit les calculs 60x par seconde
	    world.step(1/60f, 6, 2);
	    	
	    cameraHandle();
      mb.update();
	    camera.update();
      
	    	
	    //Dit au renderer de n'afficher que ce que la camera voit
	    renderer.setView(camera);
        
    }

    public void stopLevel() {
      mainTheme.stop();
    }

    @Override
    public void dispose() {
      map.dispose();
      renderer.dispose();
      world.dispose();
      b2dr.dispose();
      //hud.dispose();
    }
    
}
