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
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sandman.game.Sandman;
import com.sandman.game.sprites.Perso;
import com.sandman.game.tools.B2WorldCreator;
    

public class LevelJardin implements Screen {
    private Sandman game;

    private OrthographicCamera camera;
    private Viewport gamePort;

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

       //Cree une camera qui va suivre le personnage
       camera = new OrthographicCamera();
       
       /*
        * Ce type de camera permet de faire correspondre le jeu à la taille de la fenêtre
        * Cependant cela fait étirer les textures de manière étrange
        * J'ai donc opté pour le FitViewport qui conserve le ratio en ajoutant des bandes noires au besoin
        * gamePort = new StretchViewport(Sandman.V_WIDTH, Sandman.V_HEIGHT, camera);
        */
       
       //Garde le ratio d'affichage du jeu en fonction de la taille de la fenêtre
       gamePort = new FitViewport(Sandman.V_WIDTH / Sandman.PPM, Sandman.V_HEIGHT / Sandman.PPM, camera);
       
       //Cree le HUD du jeu avec les informations que l'on veut afficher au joueur
       hud = new Hud(game.batch);

       //Charge notre carte créée avec Tmx
       maploader = new TmxMapLoader();
       map = maploader.load("jardin.tmx");
       renderer = new OrthogonalTiledMapRenderer(map, 1/Sandman.PPM);
       
       //Place la caméra en fonction de notre écran en prenant en compte la largeur et la hauteur du monde
       camera.position.set(gamePort.getWorldWidth()/2, gamePort.getWorldHeight()/2, 0);
       
       world = new World(new Vector2(0, -10), true);
       b2dr = new Box2DDebugRenderer();
       
       new B2WorldCreator(world, map);
       
	   player = new Perso(world);
    }
    
    @Override
    public void render(float delta) {

        update(delta);

    	//On met tout au noir pour nettoyer l'écran
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //On place la camera
        game.batch.setProjectionMatrix(camera.combined);
        renderer.render();
        
        //Affiche les box2d dans le jeu
        b2dr.render(world, camera.combined);

        //On place le HUD
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
    }

    
    /*
     * Méthode qui prend en charge les appuis de touche
     */
    public void handleInput(float dt) {
    	if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
    		player.b2body.applyLinearImpulse(new Vector2(0, 0.4f), player.b2body.getWorldCenter(), true);
    	}
    	if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.b2body.getLinearVelocity().x <= 2) {
    		player.b2body.applyLinearImpulse(new Vector2(0.1f, 0), player.b2body.getWorldCenter(), true);
    	}
    	if(Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.b2body.getLinearVelocity().x >= -2) {
    		player.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), player.b2body.getWorldCenter(), true);
    	}
    }    
    
    //MÃ©thode pour mettre Ã  jour l'Ã©cran et gÃ©rer l'input
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
    	gamePort.update(width, height);
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

