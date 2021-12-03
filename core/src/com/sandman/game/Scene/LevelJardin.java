package com.sandman.game.Scene;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.sandman.game.Sandman;
    

public class LevelJardin implements Screen {
    private final Sandman game;

    private OrthographicCamera camera;


    private TmxMapLoader maploader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    public LevelJardin(final Sandman game) {
        this.game = game;

       camera = new OrthographicCamera();
       camera.setToOrtho(false, 800, 480);


       maploader = new TmxMapLoader();
       map = maploader.load("jardin.tmx");
       renderer = new OrthogonalTiledMapRenderer(map);
        
    }
    
    @Override
    public void render(float delta) {

        update(delta);

    	//Clear the game screen with Black
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();

        //game.batch.setProjectionMatrix(camera.combined);

        
    
    }

    public void handleInput(float dt) {
    	if(Gdx.input.isTouched()) {
    		camera.position.x += 100 * dt;
    	}
    }    
    //Méthode pour mettre à jour l'écran et gérer l'input ca marche po
    public void update(float dt) {
    	handleInput(dt);

    	camera.update();
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
    }
    
}

