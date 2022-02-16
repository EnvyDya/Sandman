package com.sandman.game.Scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
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


public abstract class Level implements Screen{
	protected Sandman game;
    protected OrthographicCamera camera;

    //Tiled map variables
    protected TmxMapLoader maploader;
    protected TiledMap map;

    protected OrthogonalTiledMapRenderer renderer;
    protected B2WorldCreator worldCreator;
    
    //Box2d variables
    protected World world;
    protected Box2DDebugRenderer b2dr;
    
    //Player variable
    protected Perso player;
    protected float speed;
    protected float maxSpeed;
    protected float jumpForce;

    //Music
    protected Music mainTheme;
    
    //private Hud hud;
    
    public Level(final Sandman game, String mapName, String themeName, float gravity) {
    	this.game = game;
    	
    	//Charge notre carte cree avec Tmx
        maploader = new TmxMapLoader();
        map = maploader.load(mapName);
        renderer = new OrthogonalTiledMapRenderer(map,1/16f);
        
        //Charge et lance la musique
        mainTheme = Gdx.audio.newMusic(Gdx.files.internal(themeName));
        mainTheme.setLooping(true);
        mainTheme.play();
        
    	camera = new OrthographicCamera();
        camera.setToOrtho(false, 30, 20);

        world = new World(new Vector2(0, -gravity), true);
        b2dr = new Box2DDebugRenderer();
        
        //Création et gestion des body
        worldCreator = new B2WorldCreator(world, map);
    }
    
    /**
     * Contraint le joueur à rester dans les limites du jeu, le ramène et annule sa vélocité s'il essaye d'en sortir
     */
    public abstract void borderManagement();
    
    /**
    * Méthode qui place la caméra au bon endroit sur la carte
    */
    public abstract void cameraHandle();
    
    public abstract void render(float delta);
    
    //Méthode pour mettre à jour l'écran et gérer l'input
    public abstract void update(float dt);
    
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
    public abstract void dispose();
    
	public OrthographicCamera getCamera() {
		return camera;
	}

	//
	public B2WorldCreator getWorldCreator() {
		return worldCreator;
	}

	//
	public World getWorld() {
		return world;
	}

	//
	public float getSpeed() {
		return speed;
	}

	//
	public float getMaxSpeed() {
		return maxSpeed;
	}
	
	//
	public float getJumpForce() {
		return jumpForce;
	}
}
