package com.sandman.game.Scene;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sandman.game.Sandman;

public class Hud {
	
	//TODO: A modifier pour convenir au HUD de notre jeu
	public Stage stage;
	private Viewport viewport;
	
	private Integer worldTimer;
	private Integer score;
	
	//Tous les attributs qui seront affiches
	Label countdownLabel;
	Label scoreLabel;
	Label timeLabel;
	Label levelLabel;
	Label worldLabel;
	Label marioLabel;
	
	public Hud(SpriteBatch sb) {
		worldTimer = 300;
		score = 0;
		
		viewport = new FitViewport(Sandman.V_WIDTH, Sandman.V_HEIGHT, new OrthographicCamera());
		stage = new Stage(viewport, sb);
		
		//Table sur laquelle on pose nos Label
		Table table = new Table();
		table.top();
		table.setFillParent(true);
		
		//On cree nos label
		countdownLabel = new Label(String.format("%03d", worldTimer), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		scoreLabel = new Label(String.format("%06d", score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		timeLabel = new Label("TIME", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		levelLabel = new Label("Jardin", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		worldLabel = new Label("Niveau", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		marioLabel = new Label("Boy", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		
		//On ajoute nos label en haut de notre Ecran
		table.add(marioLabel).expandX().padTop(10);
		table.add(worldLabel).expandX().padTop(10);
		table.add(timeLabel).expandX().padTop(10);
		table.row();
		table.add(scoreLabel).expandX();
		table.add(levelLabel).expandX();
		table.add(countdownLabel).expandX();
		
		stage.addActor(table);
	}
}