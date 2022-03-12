package com.sandman.game.sprites;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.sandman.game.Sandman;
import com.sandman.game.sprites.interfaces.CanDie;
import com.sandman.game.sprites.interfaces.Danger;

public class Tondeuse extends InteractiveTileObject implements Danger{

    //Attribut animation
    private Animation<TextureRegion> animTondeuse;
    private float stateTimer;
    public ArrayList<CanDie> atuer;

    //Constructeur
    public Tondeuse(World world){
        //TODO revoir pour la hitbox
        //Rectangle de positionnement et hitbox de la tondeuse
        super(new TextureRegion(new Texture("images/Tondeuse.png")),world,new Rectangle(1428, 95, 80, 55), BodyDef.BodyType.StaticBody);

        atuer = new ArrayList<CanDie>();
        
        //set l'état initial
        stateTimer = 0;

        //Creation de l'animation
        Array<TextureRegion> frames = new Array<TextureRegion>();
		for (int i = 0; i < 4; i++) {
			frames.add(new TextureRegion(getTexture(),i*96,0,96,64));
		}
		animTondeuse = new Animation<TextureRegion>(0.04f,frames);
		frames.clear();

        setBounds(0, 0, 80/Sandman.PPM, 80/Sandman.PPM);	
		setRegion(getFrame(0));
        setPosition(1428/Sandman.PPM, 100/Sandman.PPM);

        //Création d'un sensor sur la tete de la tondeuse qui va detecté les colisions
        FixtureDef fdef = new FixtureDef();
        EdgeShape danger = new EdgeShape();
        danger.set(new Vector2(-32/Sandman.PPM, 32/Sandman.PPM),new Vector2(32/Sandman.PPM, 32/Sandman.PPM));
        fdef.shape = danger;
        fdef.isSensor = true;
        body.createFixture(fdef).setUserData(this);
    }

    public void update(float dt){
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
        return animTondeuse.getKeyFrame(stateTimer,true);
    }

	@Override
	public void onClick() {
        gel = !gel;
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
