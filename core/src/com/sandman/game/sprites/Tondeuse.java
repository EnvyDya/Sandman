package com.sandman.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.sandman.game.Sandman;

public class Tondeuse extends InteractiveTileObject{

    //Attribut animation
    private Animation<TextureRegion> animTondeuse;
    private float stateTimer;

    //Constructeur
    public Tondeuse(World world){
        //TODO revoir pour la hitbox
        //Rectangle de positionnement et hitbox de la tondeuse
        super(new TextureRegion(new Texture("Tondeuse.png"),0,0,384,64),world,new Rectangle(1360, 95, 80, 55));

        //reset le temps d'état
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

    }

    public void update(float dt){
		setRegion(getFrame(dt));
	}

    //Retourne la frame à affiché
    private TextureRegion getFrame(float dt) {
        stateTimer += dt;
        return animTondeuse.getKeyFrame(stateTimer,true);
    }

	@Override
	public void onClick() {
		System.out.println("mon hélice ne tourne plus");
	}
}
