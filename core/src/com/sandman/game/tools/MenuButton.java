package com.sandman.game.tools;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.sandman.game.Sandman;
import com.sandman.game.Scene.Level;
import com.sandman.game.Scene.MainMenuScreen;
import com.sandman.game.sprites.InteractiveTileObject;

public class MenuButton extends InteractiveTileObject{
	private Level level;
	//Constructeur
    public MenuButton(World world, Level level){
        super(new TextureRegion(new Texture("images/menuButton.png")),world,new Rectangle(level.getCamera().position.x*Sandman.PPM,level.getCamera().position.y*Sandman.PPM, 40, 40), BodyDef.BodyType.KinematicBody,0);
        //Etat initial
        gel = false;
		body.getFixtureList().get(0).setSensor(true);
        
        //Référence au niveau
        this.level = level;
        setBounds(level.getCamera().position.x,level.getCamera().position.y, 40/Sandman.PPM, 40/Sandman.PPM); 
		body.setTransform((level.getCamera().position.x), (level.getCamera().position.y), 0);
    }

	@Override
	public void onClick() {
		//Revient au menu principal
		Sandman game = level.getGame();
		game.setScreen(new MainMenuScreen(game));
		level.stopLevel();
	}
	
	public void update(){
		setPosition((level.getCamera().position.x) + (level.getCamera().viewportWidth/2) -48/Sandman.PPM , (level.getCamera().position.y)+(level.getCamera().viewportHeight/2) -48/Sandman.PPM);
		body.setTransform((level.getCamera().position.x) + (level.getCamera().viewportWidth/2) -28/Sandman.PPM , (level.getCamera().position.y)+(level.getCamera().viewportHeight/2) -28/Sandman.PPM, 0);
	}
}
