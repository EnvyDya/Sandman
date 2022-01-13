package com.sandman.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.sandman.game.Sandman;

public class Perso extends Sprite {
	public enum State  { FALLING, JUMPING, STANDING, RUNNING};
	public State currentState;
	public State previousState;
    public World world;
    public Body b2body;
    
    //Attributs animation
    private Animation marioRun;
    private Animation marioJump;    
    private float stateTimer;
    private boolean runningRight;
    
    //Attributs de deplacement
    private float jumpForce;
    private float speed;
    private float maxSpeed;
    
    //Constructeur
    public Perso(World world, float jumpForce, float speed, float maxSpeed) {
    	this.world = world;
    	this.jumpForce = jumpForce;
    	this.speed = speed;
    	this.maxSpeed = maxSpeed;
    	currentState = State.STANDING;
    	previousState = State.STANDING;
    	stateTimer = 0;
    	runningRight = true;
    	
    	
    	definePerso();
    }
    
    /**
     * @return l'état dans lequel se trouve notre personnage
     */
    public State getState() {
    	if(b2body.getLinearVelocity().y > 0 || (b2body.getLinearVelocity().y < 0 && previousState == State.JUMPING)) {
    		return State.JUMPING;
    	}
    	else if(b2body.getLinearVelocity().y < 0) {
    		return State.FALLING;
    	}
    	else if(b2body.getLinearVelocity().x != 0) {
    		return State.RUNNING;
    	}
    	else return State.STANDING;
    }
    
    /**
     * Methode qui prend en charge les appuis de touche
     */
    public void handleInput(float dt) {
    	//On v�rifie si une touche de saut est appuy�e et que le joueur ne soit pas d�j� dans les airs
    	if(Gdx.input.isKeyJustPressed(Input.Keys.Z) && this.b2body.getLinearVelocity().y == 0) {
    		this.b2body.applyLinearImpulse(new Vector2(0, jumpForce), this.b2body.getWorldCenter(), true);
    	}
    	if(Gdx.input.isKeyPressed(Input.Keys.D) && this.b2body.getLinearVelocity().x <= maxSpeed) {
    		this.b2body.applyLinearImpulse(new Vector2(speed, 0), this.b2body.getWorldCenter(), true);
    		//System.out.println(player.b2body.getPosition().x);
    	}
    	if(Gdx.input.isKeyPressed(Input.Keys.Q) && this.b2body.getLinearVelocity().x >= -maxSpeed) {
    		this.b2body.applyLinearImpulse(new Vector2(-speed, 0), this.b2body.getWorldCenter(), true);
    		//System.out.println(player.b2body.getPosition().x);
    	}
    }
    
    /**
     * Définition physique du personnage
     */
    public void definePerso() {
    	BodyDef bdef = new BodyDef();
    	bdef.position.set(32/Sandman.PPM, 60/Sandman.PPM);
    	bdef.type = BodyDef.BodyType.DynamicBody;
    	b2body = world.createBody(bdef);
    	
    	FixtureDef fdef = new FixtureDef();
    	CircleShape shape = new CircleShape();
    	shape.setRadius(15/Sandman.PPM);
    	
    	fdef.shape = shape;
    	b2body.createFixture(fdef);
    	
    }
}
