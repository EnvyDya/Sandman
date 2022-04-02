package com.sandman.game.sprites;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.sandman.game.Sandman;
import com.sandman.game.sprites.interfaces.CanDie;
import com.sandman.game.sprites.interfaces.Danger;

public class Fire extends InteractiveTileObject implements Danger{

    public ArrayList<CanDie> atuer;

    //Attribut animation
    private Animation<TextureRegion> animFeu;
    private TextureRegion feueteint;
    private float stateTimer;

    //Bouton Lié
    private ButtonObject button;

    //Constructeur
    public Fire(World world,float posX, float posY,ButtonObject button){
        //Rectangle de positionnement et hitbox du feu
        super(new TextureRegion(new Texture("images/Fire.png")),world,new Rectangle(posX, posY, 48, 48), BodyDef.BodyType.KinematicBody,20);
        body.getFixtureList().get(0).setSensor(true);
        atuer = new ArrayList<CanDie>();

        //Création d'un sensor sur le feu qui va detecter les colisions
        FixtureDef fdef = new FixtureDef();
        PolygonShape danger = new PolygonShape();
        danger.setAsBox(24/Sandman.PPM, 96/Sandman.PPM);
        fdef.shape = danger;
        fdef.isSensor = true;
        body.createFixture(fdef).setUserData(this);

        this.button = button;
        
        //set l'état initial
        stateTimer = 0;

        //Creation de l'animation
        Array<TextureRegion> frames = new Array<TextureRegion>();
		for (int i = 0; i < 4; i++) {
			frames.add(new TextureRegion(getTexture(),i*48,0,48,48));
		}
		animFeu = new Animation<TextureRegion>(0.04f,frames);
        feueteint = new TextureRegion(getTexture(),192,0,48,48);
		frames.clear();

        setBounds(body.getPosition().x-24/Sandman.PPM, body.getPosition().y, 48/Sandman.PPM, 48/Sandman.PPM);
        setRegion(getFrame(0));

        

    }

    @Override
	public void onClick() {
		gel = !gel;
	}
	
	public void update(float dt) {
		setRegion(getFrame(dt));
        if(button.getOn()){
            for (CanDie canDie : atuer) {
                canDie.die();
            }
        }
	}

    //Retourne la frame à affiché
    private TextureRegion getFrame(float dt) {
        if(button.getOn()){
            stateTimer += dt;
            return animFeu.getKeyFrame(stateTimer,true);
        }
        else{
            return feueteint;
        }
    }

    @Override
    public Boolean canKill(CanDie die) {
        if(!button.getOn()){
            atuer.add(die);
        }
        return button.getOn();
    }

    @Override
    public void cantKillAnymore(CanDie die) {
        if(atuer.contains(die)){
            atuer.remove(die);
        }
    }
}
