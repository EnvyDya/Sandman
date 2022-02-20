package com.sandman.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.sandman.game.Sandman;
import com.sandman.game.sprites.interfaces.Danger;

public class Feuille extends InteractiveTileObject implements Danger{

	//Constructeur
    public Feuille(World world){
        //Rectangle de positionnement et hitbox de la tondeuse
        super(new TextureRegion(new Texture("images/feuille.png")),world,new Rectangle(1360, 190, 40, 10), BodyDef.BodyType.KinematicBody);
        //Etat initial
        gel = false;

        setBounds(0, 3, 40/Sandman.PPM, 40/Sandman.PPM);
        body.setLinearVelocity(0f, -8f);

		//Création d'un sensor en dessous de la feuille qui va detecté les colisions
        FixtureDef fdef = new FixtureDef();
        EdgeShape danger = new EdgeShape();
        danger.set(new Vector2(-20/Sandman.PPM, -6/Sandman.PPM),new Vector2(20/Sandman.PPM, -6/Sandman.PPM));
        fdef.shape = danger;
        fdef.isSensor = true;
        body.createFixture(fdef).setUserData(this);
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

	@Override
	public Boolean canKill() {
		return !gel;
	}
}