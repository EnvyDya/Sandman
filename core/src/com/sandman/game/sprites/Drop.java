package com.sandman.game.sprites;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.sandman.game.Sandman;
import com.sandman.game.sprites.interfaces.CanDie;
import com.sandman.game.sprites.interfaces.Danger;

public class Drop extends InteractiveTileObject implements Danger{
	public ArrayList<CanDie> atuer;

	//Constructeur
    public Drop(World world, int posX, int posY){
        //Rectangle de positionnement et hitbox de la goutte
        super(new TextureRegion(new Texture("images/drop.png")),world,new Rectangle(posX, posY, 32, 32), BodyDef.BodyType.KinematicBody,5);
        //Etat initial
        gel = false;
        body.getFixtureList().get(0).setSensor(true);
        body.getFixtureList().get(0).setUserData(this);
		
		atuer = new ArrayList<CanDie>();

        setBounds(body.getPosition().x, body.getPosition().y, 32/Sandman.PPM, 32/Sandman.PPM);
        body.setLinearVelocity(0f, -8f);
    }

	@Override
	public void onClick() {
		gel = !gel;
		if(gel) {
			body.setLinearVelocity(0, 0);
		}
        else {
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