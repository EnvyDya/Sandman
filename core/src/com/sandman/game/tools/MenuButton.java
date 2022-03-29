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
    	
        //Rectangle de positionnement et hitbox des pierres
    	//Position spawner près de la tondeuse : 1300x 130y
    	//Position spawner près du spawn : 690x 110y
        super(new TextureRegion(new Texture("images/menuButton.png")),world,new Rectangle(425, 270, 40, 40), BodyDef.BodyType.DynamicBody);
        //Etat initial
        gel = false;
        
        //Référence au niveau
        this.level = level;

        setBounds((level.getCamera().position.x)+12, (level.getCamera().position.y)/Sandman.PPM + 16.5f, 40/Sandman.PPM, 40/Sandman.PPM);
        setOrigin(getWidth()/2, getWidth()/2);
        fixture.setFriction(0);
        body.setGravityScale(0);
        setPosition(body.getPosition().x - getWidth()/2, body.getPosition().y - getHeight()/2);
		body.setTransform((level.getCamera().position.x)+13, (level.getCamera().position.y)/Sandman.PPM + 17.5f, 0);
    }

	@Override
	public void onClick() {
		//Revient au menu principal
		level.getGame().setScreen(new MainMenuScreen(level.getGame()));
	}
	
	public void update() {
		setBounds((level.getCamera().position.x)+12, (level.getCamera().position.y)/Sandman.PPM + 16.5f, 40/Sandman.PPM, 40/Sandman.PPM);
		setPosition(body.getPosition().x - getWidth()/2, body.getPosition().y - getHeight()/2);
		body.setTransform((level.getCamera().position.x)+13, (level.getCamera().position.y)/Sandman.PPM + 17.5f, 0);
	}
}
