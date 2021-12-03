package com.sandman.game.Scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;
import com.sandman.game.Sandman;
import com.sandman.game.personage.Perso;
    
public class LevelJardin implements Screen {
    private final Sandman game;
    
    Texture soltext;
    Perso perso;
    Music rainMusic;
    OrthographicCamera camera;
    Rectangle sol;

    
    public LevelJardin(final Sandman game) {
        this.game = game;

        soltext = new Texture(Gdx.files.internal("grass.png"));
        perso = new Perso(336f, 64f, 64f, 64f);
    
        // load the rain background "music"
        //rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));
        //rainMusic.setLooping(true);
    
        // create the camera and the SpriteBatch
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
    
        sol = new Rectangle();
        sol.x = 0;
        sol.y = 0;
        sol.width = 800;
        sol.height = 64; 

        
    }
    
    @Override
    public void render(float delta) {

        ScreenUtils.clear(0, 0.2f, 1f, 1);
        camera.update();
    

        game.batch.setProjectionMatrix(camera.combined);
    
        game.batch.begin();
        game.batch.draw(soltext, sol.x, sol.y, sol.width, sol.height);
        game.batch.draw(soltext, 800, sol.y, sol.width, 200);
        perso.render(game.batch);
        game.batch.end();

        perso.mouvement(delta,camera);
        
    
    }

    public void handleInput(float dt) {
    	if(Gdx.input.isTouched()) {
    		camera.position.x += 100 * dt;
    	}
    }

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
        // start the playback of the background music
        // when the screen is shown
        //rainMusic.play();
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
        soltext.dispose();
        rainMusic.dispose();
    }
    
}

