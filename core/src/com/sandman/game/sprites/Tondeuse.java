package com.sandman.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.sandman.game.Sandman;

public class Tondeuse extends Sprite{

    //Attribut Physique
    public World world;
    public Body b2body;

    //Attribut animation
    private Animation<TextureRegion> animTondeuse;
    private float stateTimer;

    //Constructeur
    public Tondeuse(World world){
        super(new TextureRegion(new Texture("Tondeuse.png"),0,0,384,64));
    	this.world = world;
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
        setPosition(1360/Sandman.PPM, 100/Sandman.PPM);

        //Creation physique de la Tondeuse
        defineTondeuse();
    }

    public void update(float dt){
		setRegion(getFrame(dt));
	}

    //Retourne la frame à affiché
    private TextureRegion getFrame(float dt) {
        stateTimer += dt;
        return animTondeuse.getKeyFrame(stateTimer,true);
    }

    private void defineTondeuse() {
        BodyDef bdef = new BodyDef();
    	bdef.position.set(1400/Sandman.PPM, 135/Sandman.PPM);
    	bdef.type = BodyDef.BodyType.StaticBody;
    	b2body = world.createBody(bdef);
    	
    	FixtureDef fdef = new FixtureDef();
    	PolygonShape shape = new PolygonShape();
    	shape.setAsBox(40/Sandman.PPM, 20/Sandman.PPM);
    	
    	fdef.shape = shape;
    	b2body.createFixture(fdef);
    	
    }
}
