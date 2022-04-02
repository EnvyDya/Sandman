package com.sandman.game.sprites;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.sandman.game.Sandman;
import com.sandman.game.sprites.interfaces.CanDie;
import com.sandman.game.sprites.interfaces.Danger;


public class Knife extends InteractiveTileObject implements Danger{
	public ArrayList<CanDie> atuer;

	//Constructeur
    public Knife(World world, float posX, float posY){
        //Rectangle de positionnement et hitbox du couteau
        super(new TextureRegion(new Texture("images/knife.png")),world,new Rectangle(posX, posY, 32, 240), BodyDef.BodyType.KinematicBody,120); 
        //Etat initial
        gel = false;
		
		atuer = new ArrayList<CanDie>();

        setBounds(body.getPosition().x, body.getPosition().y, 32/Sandman.PPM, 240/Sandman.PPM);
        body.setLinearVelocity(0f, -30f);

		//Création d'un sensor en dessous du couteau qui va detecté les colisions
        FixtureDef fdef = new FixtureDef();
        EdgeShape danger = new EdgeShape();
        danger.set(new Vector2(-15/Sandman.PPM, -1/Sandman.PPM),new Vector2(15/Sandman.PPM, -1/Sandman.PPM));
        fdef.shape = danger;
        fdef.isSensor = true;
        body.createFixture(fdef).setUserData(this);
    }

	@Override
	public void onClick() {
		gel = !gel;
		if(gel) {
			body.setLinearVelocity(0, 0);
            body.setType(BodyDef.BodyType.StaticBody);
		}else {
            body.setType(BodyDef.BodyType.KinematicBody);
			body.setLinearVelocity(0f, -30f);
            
		}
	}
	
	public void update() {
        gestionEtat();
		setPosition(body.getPosition().x - getWidth()/2, body.getPosition().y);
        if(!gel){
            for (CanDie canDie : atuer) {
                canDie.die();
            }
        }
	}

    public void gestionEtat(){
        if (body.getPosition().y < 224/Sandman.PPM) {
            body.setLinearVelocity(0f, 15f);
        }
        else if(body.getPosition().y - getHeight() > 128/Sandman.PPM){
            body.setLinearVelocity(0f, -30f);
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
