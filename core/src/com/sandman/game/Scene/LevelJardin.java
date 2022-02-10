package com.sandman.game.Scene;

import com.badlogic.gdx.Gdx;
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
    private float gravity = 10f;
    
    //Player variable
    private Perso player;
    private float speed = 1.5f;
    private float maxSpeed = 5;
    private float jumpForce = 6f;
    
    private Hud hud;

    public LevelJardin(final Sandman game) {
        this.game = game;

        //Charge notre carte cree avec Tmx
        maploader = new TmxMapLoader();
        map = maploader.load("jardin.tmx");
        renderer = new OrthogonalTiledMapRenderer(map,1/16f);
       
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 30, 20);
       
       
        world = new World(new Vector2(0, -gravity), true);
        b2dr = new Box2DDebugRenderer();
       
        new B2WorldCreator(world, map);
       
        player = new Perso(world, jumpForce, speed, maxSpeed);
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
    
    
    /**
     * Contraint le joueur à rester dans les limites du jeu, le ramène et annule sa vélocité s'il essaye d'en sortir
     */
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
    
    /**
    * Méthode qui place la caméra au bon endroit sur la carte
    */
    public void cameraHandle() {
    	//Gestion de la caméra horizontale
    	//System.out.println(camera.position.x); //track la position de la camera, 15 bonne position au départ, 115 à la fin
    	if(player.b2body.getPosition().x >= 15 && player.b2body.getPosition().x <= 115) {
        	camera.position.x = player.b2body.getPosition().x;
    	}
    }
    
    //M�thode pour mettre à jour l'écran et gérer l'input
    public void update(float dt) {
    	player.handleInput(dt);
    	borderManagement();

    	//On rafraichit les calculs 60x par seconde
    	world.step(1/60f, 6, 2);
    	
    	cameraHandle();
    	
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

