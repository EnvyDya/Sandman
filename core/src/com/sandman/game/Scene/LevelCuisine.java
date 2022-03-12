package com.sandman.game.Scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.sandman.game.Sandman;
import com.sandman.game.sprites.Perso;

public class LevelCuisine extends Level{

    //Gestionnaire de colision
	//private ColisionListener colision;

    public LevelCuisine(final Sandman game) {
        super(game, "images/cuisine.tmx", "sounds/themecuisine.wav", 20f);

        //Player variable
        speed = 1.5f;
        maxSpeed = 5;
        jumpForce = 12f;

        //Initialisation Entités
        player = new Perso(this);

		//Initialise les colisions
		//colision = new ColisionListener(player);
        //world.setContactListener(colision);
        
    }

    @Override
    public void borderManagement() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void cameraHandle() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void render(float delta) {
        update(delta);
	
	    //On met tout au noir pour nettoyer l'écran
	    Gdx.gl.glClearColor(0, 0, 0, 1);
	    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	
	    renderer.render();
		//colision.update();
	
	    //rendu du joueur
	    game.batch.setProjectionMatrix(camera.combined);
	    game.batch.begin();
		player.draw(game.batch);
        
		if(player.getGel()){
	    	player.getObjetGel().draw(game.batch);
		}
		
	    game.batch.end();
	
	    //Affiche les box2d dans le jeu
	    b2dr.render(world, camera.combined);
        
    }

    @Override
    public void update(float dt) {
        player.handleInput(dt);
	    player.update(dt);
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
