package com.sandman.game.tools;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.sandman.game.Sandman;
import com.sandman.game.Scene.MainMenuScreen;
import com.sandman.game.sprites.Bed;
import com.sandman.game.sprites.ButtonObject;
import com.sandman.game.sprites.Perso;
import com.sandman.game.sprites.Water;
import com.sandman.game.sprites.interfaces.CanDie;
import com.sandman.game.sprites.interfaces.Danger;

public class ColisionListener implements ContactListener {
    private Perso perso;

    public ColisionListener(Perso perso){
        this.perso = perso;
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        if(fixA.getUserData() != null&&fixB.getUserData() != null){

            //Regarde si le perso attérit sur quelque chose
            if(fixA.getUserData()=="pied" || fixB.getUserData()=="pied"){
                if(fixA.getUserData()=="pied"){
                    if(Water.class.isAssignableFrom(fixB.getUserData().getClass())){
                        if(((Water)fixB.getUserData()).isFrozen()){
                            perso.land();
                        }
                    }
                    else if(ButtonObject.class.isAssignableFrom(fixB.getUserData().getClass())){
                        ((ButtonObject) fixB.getUserData()).pression();
                        perso.land();
                    }
                    else if(Bed.class.isAssignableFrom(fixB.getUserData().getClass())){
                        Sandman game = perso.getLevel().getGame();
                        game.setScreen(new MainMenuScreen(game));
                        perso.getLevel().stopLevel();
                    }
                    
                }
                else{
                    if(Water.class.isAssignableFrom(fixA.getUserData().getClass())){
                        if(((Water)fixA.getUserData()).isFrozen()){
                            perso.land();
                        }
                    }
                    else if(ButtonObject.class.isAssignableFrom(fixA.getUserData().getClass())){
                        ((ButtonObject) fixA.getUserData()).pression();
                        perso.land();
                    }
                    else if(Bed.class.isAssignableFrom(fixA.getUserData().getClass())){
                        Sandman game = perso.getLevel().getGame();
                        game.setScreen(new MainMenuScreen(game));
                        perso.getLevel().stopLevel();
                    }
                    else{
                        perso.land();
                    }
                }
            }

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
                if(CanDie.class.isAssignableFrom(objet.getUserData().getClass()) && ((Danger) danger.getUserData()).canKill(( (CanDie)objet.getUserData())) ){
                    ((CanDie) objet.getUserData()).die();
                }
            }
        }
        else if(fixA.getUserData()=="pied" || fixB.getUserData()=="pied"){
            perso.land();
        }
    }

    @Override
    public void endContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        if(fixA.getUserData() != null&&fixB.getUserData() != null){
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
                if(CanDie.class.isAssignableFrom(objet.getUserData().getClass())){
                    ((Danger) danger.getUserData()).cantKillAnymore(((CanDie) objet.getUserData()));
                }
            }
            else if(fixA.getUserData()=="pied" || fixB.getUserData()=="pied"){
                if(fixA.getUserData()=="pied"){
                    if(ButtonObject.class.isAssignableFrom(fixB.getUserData().getClass())){
                        ((ButtonObject) fixB.getUserData()).finpression();
                    }
                }
                else{
                    if(ButtonObject.class.isAssignableFrom(fixA.getUserData().getClass())){
                        ((ButtonObject) fixA.getUserData()).finpression();
                    }
                }
            }
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {   
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
    }
};
