package com.sandman.game.Scene;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.sandman.game.Sandman;
import com.sandman.game.sprites.Boulder;
import com.sandman.game.sprites.Feuille;
import com.sandman.game.sprites.Perso;
import com.sandman.game.sprites.Tondeuse;

    
public class LevelJardin extends Level{

	//Gestionnaire de colision
	private ColisionListener colision;

    //Son ambiance
    private Sound sonBird;

    //Entité variable
    private Tondeuse tondeuse;
    private ArrayList<Feuille> feuilles;
    private ArrayList<Boulder> boulders;

    public LevelJardin(final Sandman game) {
        super(game, "images/jardin.tmx", "sounds/themejardin.wav", 20f);

        //ajout bruit Oiseaux
        sonBird = Gdx.audio.newSound(Gdx.files.internal("sounds/sonBird.wav"));
        sonBird.loop(0.25f);

        //Player variable
        speed = 1.5f;
        maxSpeed = 5;
        jumpForce = 12f;

        //Initialisation Entités
        player = new Perso(this);
        tondeuse = worldCreator.getTondeuse();
        feuilles = worldCreator.getFeuille();
        boulders = worldCreator.getBoulder();

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
    else if (player.b2body.getPosition().x > 129) {
      player.b2body.setTransform(129, player.b2body.getPosition().y, player.b2body.getAngle());
      player.b2body.setLinearVelocity(0, player.b2body.getLinearVelocity().y);
    }
  }

  @Override
  public void cameraHandle() {
    //Gestion de la caméra horizontale
    if(player.b2body.getPosition().x >= 15 && player.b2body.getPosition().x <= 115) {
        camera.position.x = player.b2body.getPosition().x;
    }
  }

	@Override
	public void render(float delta) {
	    update(delta);
	
	    //On met tout au noir pour nettoyer l'écran
	    Gdx.gl.glClearColor(0, 0, 0, 1);
	    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	
	    renderer.render();
		//colision.update();
	
	    //rendu
	    game.batch.setProjectionMatrix(camera.combined);
	    game.batch.begin();
	    tondeuse.draw(game.batch);
		if(player.getGel()){
	    	player.getObjetGel().draw(game.batch);
		}
		
		//On update et on dessine en même temps pour réduire la complexité d'un parcours
		for(Feuille f : feuilles) {
			f.update();
			f.draw(game.batch);
		}
		for(Boulder b : boulders) {
			b.update();
			b.draw(game.batch);
		}
		player.draw(game.batch);
		
	    game.batch.end();
	
	    //Affiche les box2d dans le jeu
	    //b2dr.render(world, camera.combined);
			
	}

	@Override
	public void update(float dt) {
		player.handleInput(dt);
	    player.update(dt);
	    tondeuse.update(dt);
	    worldCreator.update(dt);
	    borderManagement();
	
	    //On rafraichit les calculs 60x par seconde
	    world.step(1/60f, 6, 2);
	    	
	    cameraHandle();
	    	
	    camera.update();
	    	
	    //Dit au renderer de n'afficher que ce que la camera voit
	    renderer.setView(camera);
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
