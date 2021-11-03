package com.sandman.game.Scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;
import com.sandman.game.Sandman;
    
public class LevelJardin implements Screen {
    final Sandman game;
    
    Texture soltext;
    Texture perso;
    Music rainMusic;
    OrthographicCamera camera;
    Rectangle sol;
    Rectangle posperso;
    
    public LevelJardin(final Sandman game) {
        this.game = game;

        soltext = new Texture(Gdx.files.internal("grass.png"));
        perso = new Texture(Gdx.files.internal("perso.png"));
        
    
        // load the rain background "music"
        rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));
        rainMusic.setLooping(true);
    
        // create the camera and the SpriteBatch
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
    
        sol = new Rectangle();
        sol.x = 0;
        sol.y = 0;
        sol.width = 800;
        sol.height = 64; 

        posperso = new Rectangle(336f, 64f, 64f, 64f);
        
    }
    
    @Override
    public void render(float delta) {

        ScreenUtils.clear(0, 0.2f, 1f, 1);
        // tell the camera to update its matrices.
        camera.update();
    
        // tell the SpriteBatch to render in the
        // coordinate system specified by the camera.
        game.batch.setProjectionMatrix(camera.combined);
    
        // begin a new batch and draw the bucket and
        // all drops
        game.batch.begin();
        game.batch.draw(soltext, sol.x, sol.y, sol.width, sol.height);
        game.batch.draw(perso,posperso.x,posperso.y,posperso.width,posperso.height);
        game.batch.end();

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			camera.translate(-3, 0, 0);
            posperso.x -= 3;
		}

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			camera.translate(3, 0, 0);
            posperso.x += 3;
		}
    
    }
    
    @Override
    public void resize(int width, int height) {
    }
    
    @Override
    public void show() {
        // start the playback of the background music
        // when the screen is shown
        rainMusic.play();
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

