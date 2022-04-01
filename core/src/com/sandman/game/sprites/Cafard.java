package com.sandman.game.sprites;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.sandman.game.Sandman;
import com.sandman.game.sprites.interfaces.CanDie;
import com.sandman.game.sprites.interfaces.Danger;


public class Cafard extends InteractiveTileObject implements Danger{
	public ArrayList<CanDie> atuer;
    public Boolean runningLeft;

    //Attribut animation
    private Animation<TextureRegion> animCafard;
    private float stateTimer;

	//Constructeur
    public Cafard(World world, float posX, float posY){
        //Rectangle de positionnement et hitbox du cafard
        super(new TextureRegion(new Texture("images/cafardcingle.png")),world,new Rectangle(posX, posY, 130, 160), BodyDef.BodyType.KinematicBody,80); 
        //Etat initial
        gel = false;
        runningLeft = true;
        body.getFixtureList().get(0).setSensor(true);
		
		atuer = new ArrayList<CanDie>();

        //set l'état initial
        stateTimer = 0;

        //Creation de l'animation
        Array<TextureRegion> frames = new Array<TextureRegion>();
        for (int i = 0; i < 3; i++) {
            frames.add(new TextureRegion(getTexture(),i*130,0,130,160));
        }
        animCafard = new Animation<TextureRegion>(0.1f,frames);
        frames.clear();

        setBounds(body.getPosition().x, body.getPosition().y, 130/Sandman.PPM, 160/Sandman.PPM);
        setRegion(getFrame(0));
        body.setLinearVelocity(10f, 0f);

		//Création d'une hitbox pour monter sur le cafard
        FixtureDef fdef = new FixtureDef();
        PolygonShape danger = new PolygonShape();
        Vector2 center = new Vector2(0/Sandman.PPM,32/Sandman.PPM);
        danger.setAsBox((getWidth()/2) - 16/Sandman.PPM , 32/Sandman.PPM,center,0);
        fdef.shape = danger;
        body.createFixture(fdef).setUserData(this);

    }

	@Override
	public void onClick() {
		gel = !gel;
		if(gel) {
			body.setLinearVelocity(0, 0);
		}else {
            if (runningLeft) {
                body.setLinearVelocity(-10f, 0f);
            }
            else{
                body.setLinearVelocity(10f, 0f);
            }   
		}
	}
	
	public void update(float dt) {
        gestionEtat();
		setPosition(body.getPosition().x - getWidth()/2, body.getPosition().y);
        if(!gel){
		    setRegion(getFrame(dt));
            for (CanDie canDie : atuer) {
                canDie.die();
            }
        }
	}

    //Retourne la frame à affiché
    private TextureRegion getFrame(float dt) {
        stateTimer += dt;
        TextureRegion region = animCafard.getKeyFrame(stateTimer,true);

        if((body.getLinearVelocity().x < 0 || runningLeft) && region.isFlipX()){
			region.flip(true,false);
			runningLeft = true;
		}
		else if((body.getLinearVelocity().x > 0 || !runningLeft) && !region.isFlipX()){
			region.flip(true, false);
			runningLeft = false;
		}
        
        return region;
    }

    public void gestionEtat(){
        if (body.getPosition().x < 1520/Sandman.PPM) {
            body.setLinearVelocity(10f, 0f);
        }
        else if(body.getPosition().x - getWidth() > 1760/Sandman.PPM){
            body.setLinearVelocity(-10f, 0f);
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
