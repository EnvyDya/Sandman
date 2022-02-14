package com.sandman.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.sandman.game.Sandman;

public class Perso extends Sprite{
	public enum State  { FALLING, JUMPING, STANDING, RUNNING};
	public State currentState;
	public State previousState;
    public World world;
    public Body b2body;
    
    //Attributs animation
    private Animation<TextureRegion> playerRun;
    private Animation<TextureRegion> playerJump;
	private Animation<TextureRegion> playerFall;
	private Animation<TextureRegion> playerStand;    
    private float stateTimer;
    private boolean runningRight;
	
    
    //Attributs de deplacement
    private float jumpForce;
    private float speed;
    private float maxSpeed;
    private float minRunningSpeed = 1f;    
    //Constructeur
    public Perso(World world, float jumpForce, float speed, float maxSpeed) {
		super(new TextureRegion(new Texture("Sandman.png"),0,0,256,96));
    	this.world = world;
    	this.jumpForce = jumpForce;
    	this.speed = speed;
    	this.maxSpeed = maxSpeed;

		//Initialisation Animation
    	currentState = State.STANDING;
    	previousState = State.STANDING;

    	stateTimer = 0;
    	runningRight = true;

		Array<TextureRegion> frames = new Array<TextureRegion>();
		for (int i = 0; i < 6; i++) {
			frames.add(new TextureRegion(getTexture(),i*32,64,32,32));
		}
		playerRun = new Animation<TextureRegion>(0.1f,frames);
		frames.clear();

		for (int i = 2; i < 5; i++) {
			frames.add(new TextureRegion(getTexture(),i*32,32,32,32));
		}
		playerJump = new Animation<TextureRegion>(0.1f, frames);
		frames.clear();

		for (int i = 5; i < 8; i++) {
			frames.add(new TextureRegion(getTexture(),i*32,32,32,32));
		}
		playerFall = new Animation<TextureRegion>(0.2f, frames);
		frames.clear();

		for (int i = 0; i < 4; i++) {
			frames.add(new TextureRegion(getTexture(),i*32,0,32,32));
		}
		playerStand = new Animation<TextureRegion>(0.1f, frames);
		setBounds(0, 0, 32/Sandman.PPM, 32/Sandman.PPM);	

    	definePerso();
		setRegion(getFrame(0));
    }
    
    /**
     * @return l'état dans lequel se trouve notre personnage
     */
    public State getState() {
    	if(b2body.getLinearVelocity().y > 0 ){//|| (b2body.getLinearVelocity().y < 0 && previousState == State.JUMPING)) {
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

	public void update(float dt){
		setPosition(b2body.getPosition().x - getWidth()/2, b2body.getPosition().y - getHeight()/2);
		setRegion(getFrame(dt));
	}
    
    private TextureRegion getFrame(float dt) {
		currentState = getState();
		TextureRegion region;
		switch (currentState) {
			case JUMPING:
				region = playerJump.getKeyFrame(stateTimer);
				break;
			case FALLING:
				region = playerFall.getKeyFrame(stateTimer);
				break;
			case RUNNING:
				region = playerRun.getKeyFrame(stateTimer,true);
				break;
			default:
				region = playerStand.getKeyFrame(stateTimer,true);
				break;
		}

		if((b2body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()){
			region.flip(true,false);
			runningRight = false;
		}
		else if((b2body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()){
			region.flip(true, false);
			runningRight = true;
		}
		stateTimer = currentState == previousState ? stateTimer + dt : 0;
		previousState = currentState;
		return region;
	}

	/**
     * Methode qui prend en charge les appuis de touche
     */
    public void handleInput(float dt) {


    	//TODO: Régler problème escalade des murs
    	
    	//On vérifie si une touche de saut est appuy�e et que le joueur ne soit pas déjà dans les airs
    	if(Gdx.input.isKeyPressed(Input.Keys.Z) && (getState() == State.STANDING || getState() == State.RUNNING)) {

    		this.b2body.applyLinearImpulse(new Vector2(0, jumpForce), this.b2body.getWorldCenter(), true);
    	}
    	if(Gdx.input.isKeyPressed(Input.Keys.D) && this.b2body.getLinearVelocity().x <= maxSpeed ) {
    		this.b2body.applyLinearImpulse(new Vector2(speed, 0), this.b2body.getWorldCenter(), true);
    		//System.out.println(player.b2body.getPosition().x);
    	}
    	if(Gdx.input.isKeyPressed(Input.Keys.Q) && this.b2body.getLinearVelocity().x >= -maxSpeed) {
    		this.b2body.applyLinearImpulse(new Vector2(-speed, 0), this.b2body.getWorldCenter(), true);
    		//System.out.println(player.b2body.getPosition().x);
    	}
    	
    	//On ralentit le joueur s'il n'appuie plus sur la touche pour avancer
    	if(!Gdx.input.isKeyJustPressed(Input.Keys.Q) && !Gdx.input.isKeyJustPressed(Input.Keys.D) && Math.abs(this.b2body.getLinearVelocity().x) > minRunningSpeed && getState() == State.RUNNING) {
    		this.b2body.applyLinearImpulse(new Vector2(-this.b2body.getLinearVelocity().x/10, 0), this.b2body.getWorldCenter(), true);
    	}
    	//On arrête le joueur s'il est sous la vitesse minimale
    	if(Math.abs(this.b2body.getLinearVelocity().x) < minRunningSpeed && getState() == State.RUNNING) {
    		System.out.println("Arret de course");
    		this.b2body.setLinearVelocity(new Vector2(0, this.b2body.getLinearVelocity().y));
    	}
    	
    	if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
    		System.out.println("Clic bouton gauche souris en " + Gdx.input.getX() + "x et " + Gdx.input.getY() + "y.");
    		
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
