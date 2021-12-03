package com.sandman.game.personage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.TimeUtils;

public class Perso implements Disposable {
    private Status etat = Status.tombe;
    private final static int vitessedemarche = 250; 
    Vector2 position;
    float height;
    float width;
    Vector2 translation = new Vector2();
    long debutsaut;
    private Orientation orientation = Orientation.DROITE;
    private Texture perso = new Texture(Gdx.files.internal("perso.png"));

    public Perso(float x,float y,float width,float height){
        position = new Vector2(x,y);
        this.width=width;
        this.height=height;
    }


    /**Appelle cette fonction pour que le personnage puisse se deplacer
     * @param delta permet d'avoir le temps entre la dernière frame et cette frame pour avoir un déplacement à vitesse identique
    */
    public void mouvement(float delta,OrthographicCamera camera){

        //TODO améliorer le saut car c'est degeulasse
        //déplacement vertical
        if(etat==Status.ausol&&Gdx.input.isKeyPressed(Input.Keys.SPACE)){
            etat=Status.saute;
            debutsaut = TimeUtils.nanoTime();
        }
        if(etat==Status.saute&&MathUtils.nanoToSec*(TimeUtils.nanoTime()-debutsaut)<0.5){
            translation.y = 400;
        }
        else if(etat==Status.saute){
            etat = Status.tombe;
        }

        if(etat==Status.tombe&&position.y<=64){
            etat = Status.ausol;
            position.y=64;
            translation.y = 0;
        }
        else if(etat==Status.tombe){
            //gravité
            translation.y -= delta * 600; 
        }
        position.mulAdd(translation, delta);

        //déplacements horizontaux
        if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
			camera.translate(-delta*vitessedemarche, 0, 0);
            this.gauche(delta);
		}

        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
			camera.translate(delta*vitessedemarche, 0, 0);
            this.droite(delta);
		}
        

    


    }

    public void droite(float delta){
        orientation = Orientation.DROITE;
        position.x += delta*vitessedemarche;
    }

    public void gauche(float delta){
        orientation = Orientation.GAUCHE;
        position.x -= delta*vitessedemarche;
    }

    public void saute(){
        if(etat == Status.ausol){
            etat = Status.saute;
        }
    }

    public void render(SpriteBatch batch){
        if(orientation == Orientation.DROITE){
            batch.draw(perso, position.x, position.y,width,height);
        } 
        else if(orientation == Orientation.GAUCHE){
            batch.draw(perso, position.x+64, position.y,-width,height);
        }
    }

    @Override
    public void dispose() {
        perso.dispose();
    }

    public float getx(){
        return position.x;
    }

    public float gety(){
        return position.y;
    }

    public Status getEtat() {
        return etat;
    }

}
