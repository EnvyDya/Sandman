package com.sandman.game.Scene;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.sandman.game.sprites.interfaces.CanDie;
import com.sandman.game.sprites.interfaces.Danger;

public class ColisionListener implements ContactListener {

    

    public ColisionListener() {
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        if(fixA.getUserData() != null&&fixB.getUserData() != null){

            //Regarde s'il y a une colision avec un danger
            if(Danger.class.isAssignableFrom(fixA.getUserData().getClass()) || Danger.class.isAssignableFrom(fixB.getUserData().getClass())){
                Fixture danger;
                Fixture objet;

                if(Danger.class.isAssignableFrom(fixA.getUserData().getClass())){
                    danger = fixA;
                    objet = fixB;
                }
                else{
                    danger = fixB;
                    objet = fixA;
                }

                //si le danger peut tuer et que l'objet peut mourir alors invoque la méthode die
                if(((Danger) danger.getUserData()).canKill() && CanDie.class.isAssignableFrom(objet.getUserData().getClass())){
                    ((CanDie) objet.getUserData()).die();
                }
            }
        }
    }

    @Override
    public void endContact(Contact contact) {
        // TODO faire en sorte que le joueur meurt s'il ne quitte pas l'objet gelé
        
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {   
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
    }
};
