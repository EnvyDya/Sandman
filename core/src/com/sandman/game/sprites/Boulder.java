package com.sandman.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.sandman.game.Sandman;

public class Boulder extends InteractiveTileObject{

	//Constructeur
    public Boulder(World world){
        //Rectangle de positionnement et hitbox de la tondeuse
        super(new TextureRegion(new Texture("images/Boulder.png")),world,new Rectangle(32, 80, 20, 20), BodyDef.BodyType.DynamicBody);
        //Etat initial
        gel = false;

        setBounds(body.getPosition().x, body.getPosition().y, 20/Sandman.PPM, 20/Sandman.PPM);
        setOrigin(getWidth()/2, getWidth()/2);
    }

	@Override
	public void onClick() {
		body.setActive(gel);
		gel = !gel;
	}
	
	public void update() {
		setPosition(body.getPosition().x - getWidth()/2, body.getPosition().y - getHeight()/2);
		if(!gel) this.rotate(10f);
		else this.rotate(0);
	}
}
