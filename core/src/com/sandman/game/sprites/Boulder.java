package com.sandman.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.sandman.game.Sandman;

public class Boulder extends InteractiveTileObject{

	//Constructeur
    public Boulder(World world, int posX, int posY){
        //Rectangle de positionnement et hitbox des pierres
    	//Position spawner près de la tondeuse : 1300x 130y
    	//Position spawner près du spawn : 690x 110y
        super(new TextureRegion(new Texture("images/Boulder.png")),world,new Rectangle(posX, posY, 20, 20), BodyDef.BodyType.DynamicBody);
        //Etat initial
        gel = false;

        setBounds(body.getPosition().x, body.getPosition().y, 20/Sandman.PPM, 20/Sandman.PPM);
        setOrigin(getWidth()/2, getWidth()/2);
        body.setLinearVelocity(-4, -10);
        fixture.setFriction(0);
    }

	@Override
	public void onClick() {
		body.setActive(gel);
		gel = !gel;
	}
	
	public void update() {
		setPosition(body.getPosition().x - getWidth()/2, body.getPosition().y - getHeight()/2);
        body.setLinearVelocity(-6, body.getLinearVelocity().y);
        body.applyForceToCenter(world.getGravity(), false);
		if(!gel) this.rotate(10f);
		else this.rotate(0);
	}
}
