package com.sandman.game.Scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.sandman.game.Sandman;


public class MainMenuScreen implements Screen {

    final Sandman game;
    OrthographicCamera camera;
    private Texture backgroundTexture;

    public MainMenuScreen(final Sandman game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1190, 654);
        backgroundTexture = new Texture("images/BGTitleWBtn.png");
    }
    
	@Override
	public void render(float delta) {
		//On met tout au noir pour nettoyer l'écran
	    Gdx.gl.glClearColor(0, 0, 0, 1);
	    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();
		game.batch.setProjectionMatrix(camera.combined);

		game.batch.begin();
		game.batch.draw(backgroundTexture, 0, 0);
		game.batch.end();
		
		
		if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
			Vector3 pos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
			pos = camera.unproject(pos);
			if(pos.x > 385 && pos.x < 570 && pos.y > 340 && pos.y < 395) {
				game.setScreen(new LevelJardin(game));
				dispose();
			}
			if(pos.x > 385 && pos.x < 570 && pos.y > 210 && pos.y < 270) {
				//TODO: Remplacer par level cuisine
				game.setScreen(new LevelJardin(game));
				dispose();
			}
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
