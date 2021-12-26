package com.sandman.game.Scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.sandman.game.Sandman;
import com.sandman.game.sprites.Perso;
import com.sandman.game.tools.B2WorldCreator;
    

public class LevelJardin implements Screen {
    private Sandman game;

    private OrthographicCamera camera;

    //Tiled map variables
    private TmxMapLoader maploader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    
    //Box2d variables
    private World world;
    private Box2DDebugRenderer b2dr;
    
    //Player variable
    private Perso player;
    
    private Hud hud;

    public LevelJardin(final Sandman game) {
        this.game = game;

        //Charge notre carte cree avec Tmx
        maploader = new TmxMapLoader();
        map = maploader.load("jardin.tmx");
        renderer = new OrthogonalTiledMapRenderer(map,1/16f);
       
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 30, 20);
       
       
       world = new World(new Vector2(0, -10), true);
       b2dr = new Box2DDebugRenderer();
       
       new B2WorldCreator(world, map);
       
	   player = new Perso(world);
    }
    
    @Override
    public void render(float delta) {

        update(delta);

    	//On met tout au noir pour nettoyer l'ecran
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();
        
        //Affiche les box2d dans le jeu
        b2dr.render(world, camera.combined);

    }

    
    /*
     * Methode qui prend en charge les appuis de touche
     */
    public void handleInput(float dt) {
<<<<<<< HEAD
    	if(Gdx.input.isKeyPressed(Input.Keys.Z)) {
    		player.b2body.applyLinearImpulse(new Vector2(0, 0.4f), player.b2body.getWorldCenter(), true);
    	}
    	if(Gdx.input.isKeyPressed(Input.Keys.D) && player.b2body.getLinearVelocity().x <= 5) {
    		player.b2body.applyLinearImpulse(new Vector2(0.4f, 0), player.b2body.getWorldCenter(), true);
    	}
    	if(Gdx.input.isKeyPressed(Input.Keys.Q) && player.b2body.getLinearVelocity().x >= -5) {
    		player.b2body.applyLinearImpulse(new Vector2(-0.4f, 0), player.b2body.getWorldCenter(), true);
=======
    	if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
    		player.b2body.applyLinearImpulse(new Vector2(0, 0.4f), player.b2body.getWorldCenter(), true);
    	}
    	if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.b2body.getLinearVelocity().x <= 2) {
    		player.b2body.applyLinearImpulse(new Vector2(0.1f, 0), player.b2body.getWorldCenter(), true);
    	}
    	if(Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.b2body.getLinearVelocity().x >= -2) {
    		player.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), player.b2body.getWorldCenter(), true);
>>>>>>> 6b5c4bb36efee01e289e05e43545e63bf57e78e7
    	}
    }    
    
    //Méthode pour mettre à jour l'écran et gérer l'input
    public void update(float dt) {
    	handleInput(dt);

    	//On rafraichit les calculs 60x par seconde
    	world.step(1/60f, 6, 2);
    	
    	camera.position.x = player.b2body.getPosition().x;
    	
    	camera.update();
    	
    	//Dit au renderer de n'afficher que ce que la camera voit
    	renderer.setView(camera);
    }
    
    @Override
    public void resize(int width, int height) {
    }
    
    @Override
    public void show() {
    }
    
    @Override
    public void hide() {
    }
    
    @Override
    public void pause() {
    }
    
    @Override
    public void resume() {
    }
    
    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        hud.dispose();
    }
    
}

