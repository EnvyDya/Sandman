package com.sandman.game.Scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;
import com.sandman.game.Sandman;


public class MainMenuScreen implements Screen {

    final Sandman game;
    OrthographicCamera camera;

    public MainMenuScreen(final Sandman game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
    }
    
	@Override
	public void render(float delta) {
        // TODO Refaire le menu principal
		ScreenUtils.clear(0, 0, 0.128f, 1);

		camera.update();
		game.batch.setProjectionMatrix(camera.combined);

		game.batch.begin();
		game.font.draw(game.batch, "Bienvenue sur le Menu Principal", 300, 240);
		game.font.draw(game.batch, "Appuyer pour commencer à jouer", 300, 200);
		game.batch.end();

		if (Gdx.input.isTouched()) {
			game.setScreen(new LevelJardin(game));
			dispose();
		}
	}

    @Override
    public void dispose() {  
    }

    @Override
    public void show() {
        
    }

    @Override
    public void resize(int width, int height) {
        
    }

    @Override
    public void pause() {
        
    }

    @Override
    public void resume() {
        
    }

    @Override
    public void hide() {
    
    }
        
}
