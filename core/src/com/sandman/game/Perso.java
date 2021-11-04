package com.sandman.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Disposable;

public class Perso implements Disposable {
    private Rectangle posPerso;
    private Status etat = Status.ausol;
    private Orientation orientation = Orientation.DROITE;
    private Texture perso = new Texture(Gdx.files.internal("perso.png"));

    public Perso(float x,float y,float width,float height){
        posPerso = new Rectangle(x,y,width,height);
    }

    public void droite(){
        orientation = Orientation.DROITE;
        posPerso.x += 3;
    }

    public void gauche(){
        orientation = Orientation.GAUCHE;
        posPerso.x -= 3;
    }

    public void render(SpriteBatch batch){
        if(orientation == Orientation.DROITE){
            batch.draw(perso, posPerso.x, posPerso.y,posPerso.width,posPerso.height);
        } 
        else if(orientation == Orientation.GAUCHE){
            batch.draw(perso, posPerso.x+64, posPerso.y,-posPerso.width,posPerso.height);
        }
    }

    @Override
    public void dispose() {
        perso.dispose();
    }

    public float getx(){
        return posPerso.x;
    }

    public float gety(){
        return posPerso.y;
    }

    public float getWidth(){
        return posPerso.width;
    }

    public float getHeight(){
        return posPerso.height;
    }

    public Status getEtat() {
        return etat;
    }

}
