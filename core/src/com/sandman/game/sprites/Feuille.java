package com.sandman.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.sandman.game.Sandman;

public class Feuille extends InteractiveTileObject{

	//Constructeur
    public Feuille(World world){
        //Rectangle de positionnement et hitbox de la tondeuse
        super(new TextureRegion(new Texture("images/feuille.png")),world,new Rectangle(1360, 190, 40, 10), BodyDef.BodyType.KinematicBody);
        //Etat initial
        gel = false;

        setBounds(0, 3, 40/Sandman.PPM, 40/Sandman.PPM);
        body.setLinearVelocity(0f, -8f);
    }

	@Override
	public void onClick() {
		System.out.println("La feuille s'arrête");
		gel = !gel;
		if(gel) {
			body.setLinearVelocity(0, 0);
		}else {
			body.setLinearVelocity(0f, -8f);
		}
	}
	
	public void update() {
		setPosition(body.getPosition().x - getWidth()/2, body.getPosition().y - 10/Sandman.PPM);
	}
}