package com.sandman.game.sprites;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.sandman.game.Sandman;
import com.sandman.game.sprites.interfaces.CanDie;
import com.sandman.game.sprites.interfaces.Danger;


public class Egg extends InteractiveTileObject implements Danger{
	public ArrayList<CanDie> atuer;

	//Constructeur
    public Egg(World world, float posX, float posY){
        //Rectangle de positionnement et hitbox de l'oeuf
        super(new TextureRegion(new Texture("images/egg.png")),world,new Rectangle(posX, posY, 32, 39), BodyDef.BodyType.KinematicBody,9); 
        //Etat initial
        gel = false;
        body.getFixtureList().get(0).setSensor(true);
		
		atuer = new ArrayList<CanDie>();

        setBounds(body.getPosition().x, body.getPosition().y, 32/Sandman.PPM, 39/Sandman.PPM);
        body.setLinearVelocity(0f, -8f);

		//Création d'un sensor en dessous de l'oeuf qui va detecté les colisions
        FixtureDef fdef = new FixtureDef();
        CircleShape danger = new CircleShape();
        danger.setPosition(new Vector2(0, 8/Sandman.PPM));
        danger.setRadius(1);
        fdef.shape = danger;
        fdef.isSensor = true;
        body.createFixture(fdef).setUserData(this);
    }

	@Override
	public void onClick() {
		gel = !gel;
		if(gel) {
			body.setLinearVelocity(0, 0);
		}else {
			body.setLinearVelocity(0f, -8f);
		}
	}
	
	public void update() {
		setPosition(body.getPosition().x - getWidth()/2, body.getPosition().y - 10/Sandman.PPM);
        if(!gel){
            for (CanDie canDie : atuer) {
                canDie.die();
            }
        }
	}

	@Override
    public Boolean canKill(CanDie die) {
        if(gel){
            atuer.add(die);
        }
        return !gel;
    }

	@Override
    public void cantKillAnymore(CanDie die) {
        if(atuer.contains(die)){
            atuer.remove(die);
        }
    }
}
