package com.sandman.game;

//Test git commit

public class Herbe {
    private float top;
    private float leftborder;
    private float width;
    private float height;
    private float x;
    private float y;

    // TODO Interface plateforme qui permettra de gerer toute les plateforme de la même manière
    // TODO Interface freezable qui demande l'implementation d'une méthode stoptime
    public Herbe(float x,float y,float width,float height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height =height;
        this.top = y + height;
        this.leftborder = x + width;
    }

    public float getTop() {
        return top;
    }

    public float getLeftborder() {
        return leftborder;
    }

    public float getWidth() {
        return width;
    }


    public float getHeight() {
        return height;
    }


    public float getX() {
        return x;
    }


    public float getY() {
        return y;
    }

}
