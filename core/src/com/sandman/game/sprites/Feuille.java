package com.sandman.game.sprites;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.sandman.game.Sandman;
import com.sandman.game.sprites.interfaces.CanDie;
import com.sandman.game.sprites.interfaces.Danger;

public class Feuille extends InteractiveTileObject implements Danger{
	public ArrayList<CanDie> atuer;

	//Constructeur
    public Feuille(World world, int posX, int posY){
        //Rectangle de positionnement et hitbox de la tondeuse
        super(new TextureRegion(new Texture("images/feuille.png")),world,new Rectangle(posX, posY, 40, 40), BodyDef.BodyType.KinematicBody);
        //Etat initial
        gel = false;
        body.getFixtureList().get(0).setSensor(true);
		
		atuer = new ArrayList<CanDie>();

        setBounds(0, 3, 40/Sandman.PPM, 40/Sandman.PPM);
        body.setLinearVelocity(0f, -8f);

		//Création d'un sensor en dessous de la feuille qui va detecté les colisions
        FixtureDef fdef = new FixtureDef();
        EdgeShape danger = new EdgeShape();
        danger.set(new Vector2(-20/Sandman.PPM, -6/Sandman.PPM),new Vector2(20/Sandman.PPM, -6/Sandman.PPM));
        fdef.shape = danger;
        fdef.isSensor = true;
        body.createFixture(fdef).setUserData(this);
        
        //Définition de la plateforme pour sauter contre
        fdef = new FixtureDef();
		PolygonShape shape = new PolygonShape();
 	    shape.setAsBox(20/Sandman.PPM, 1/Sandman.PPM);
 	    fdef.shape = shape;
 	    body.createFixture(fdef);
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