package com.sandman.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.sandman.game.Sandman;
import com.sandman.game.Scene.Level;

public class Perso extends Sprite implements Disposable{
    public enum State  { FALLING, JUMPING, STANDING, RUNNING};
    public Level level;
    public State currentState;
    public State previousState;
    public Body b2body;
    
    //Attributs animation
    private Animation<TextureRegion> playerRun;
    private Animation<TextureRegion> playerJump;
	private Animation<TextureRegion> playerFall;
	private Animation<TextureRegion> playerStand;    
    private float stateTimer;
    private boolean runningRight;
	
    
    //Attributs de deplacement
    private float minRunningSpeed = 1f;
	private Sound bruitSaut;
  
  
    //Constructeur
    public Perso(Level level) {
    	super(new TextureRegion(new Texture("Sandman.png"),0,0,256,96));
		this.level = level;

		//Initialisation Animation
		currentState = State.STANDING;
		previousState = State.STANDING;

		//Initialise les différents sons
		bruitSaut = Gdx.audio.newSound(Gdx.files.internal("bruitSaut.wav"));

		stateTimer = 0;
		runningRight = true;

		createFrames();

		definePerso();
		setRegion(getFrame(0));
    }
    
    
    /**
     * Méthode qui crée les différentes animations
     */
    public void createFrames() {
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
    }
    
    
    /**
     * @return l'état dans lequel se trouve notre personnage
     */
    public State getState() {
    	if(b2body.getLinearVelocity().y > 0){
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
     * Met à jour la frame de l'animation en fonction de la position
     * @param dt
     */
	public void update(float dt){
		//TODO: Régler l'animation pour qu'elle soit cohérente avec la hitbox carrée.
		setPosition(b2body.getPosition().x - getWidth()/2, b2body.getPosition().y + .15f - getHeight()/2);
		if(previousState==State.FALLING && (getState()==State.STANDING||getState()==State.RUNNING)){
			
		}
		setRegion(getFrame(dt));
	}
	
	/*
	 * Récupère la frame d'animation correspondant au temps de l'animation ainsi que son orientation
	 */
    private TextureRegion getFrame(float dt) {
		currentState = getState();
		TextureRegion region;
		//Donne l'animation correspondant à l'action
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
		
		//Donne l'orientation
		if((b2body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()){
			region.flip(true,false);
			runningRight = false;
		}
		else if((b2body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()){
			region.flip(true, false);
			runningRight = true;
		}
		
		//Réglage du timer de l'animation
		stateTimer = currentState == previousState ? stateTimer + dt : 0;
		previousState = currentState;
		return region;
	}

	/**
     * Methode qui prend en charge les appuis de touche
     */
    public void handleInput(float dt) {
    	//TODO: Régler problème escalade des murs
      
    	//On vérifie si une touche de saut est appuyée et que le joueur ne soit pas déjà dans les airs
    	if(Gdx.input.isKeyPressed(Input.Keys.SPACE) && (getState() == State.STANDING || getState() == State.RUNNING)) {
			bruitSaut.play();
    		this.b2body.applyLinearImpulse(new Vector2(0, level.getJumpForce()), this.b2body.getWorldCenter(), true);
    	}
    	if(Gdx.input.isKeyPressed(Input.Keys.D) && this.b2body.getLinearVelocity().x <= level.getMaxSpeed() ) {
    		this.b2body.applyLinearImpulse(new Vector2(level.getSpeed(), 0), this.b2body.getWorldCenter(), true);
    	}
    	if(Gdx.input.isKeyPressed(Input.Keys.Q) && this.b2body.getLinearVelocity().x >= -level.getMaxSpeed()) {
    		this.b2body.applyLinearImpulse(new Vector2(-level.getSpeed(), 0), this.b2body.getWorldCenter(), true);
    	}
    	
    	//On ralentit le joueur s'il n'appuie plus sur la touche pour avancer
    	if(!Gdx.input.isKeyPressed(Input.Keys.Q) && !Gdx.input.isKeyPressed(Input.Keys.D) && Math.abs(this.b2body.getLinearVelocity().x) > minRunningSpeed && getState() == State.RUNNING) {
    		this.b2body.applyLinearImpulse(new Vector2(-this.b2body.getLinearVelocity().x/10, 0), this.b2body.getWorldCenter(), true);
    	}
    	//On arrête le joueur s'il est sous la vitesse minimale
    	if(Math.abs(this.b2body.getLinearVelocity().x) < minRunningSpeed && getState() == State.RUNNING) {
    		this.b2body.setLinearVelocity(new Vector2(0, this.b2body.getLinearVelocity().y));
    	}
    	//Gestion du clic souris
    	if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
    		//On replace le curseur dans le contexte du jeu
    		Vector3 pos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
    		pos = level.getCamera().unproject(pos);
    		//On recherche tous les body du monde
    		Array<Body> al = new Array<Body>();
    		level.getWorld().getBodies(al);
    		for(Body b : al) {
    			//Pour chaque body, on teste si notre clic est dedans
    			if(b.getFixtureList().get(0).testPoint(pos.x, pos.y)) {
    				//Si le clic est dedans, on teste chaque InteractiveTile du niveau pour voir si on a le même body
    				for(InteractiveTileObject w : level.getWorldCreator().interactiveTiles) {
    					if(w.body == b) {
    						//Le cas échéant, on réalise le comportement associé à l'objet récupéré
    						System.out.println("Contact avec l'eau : " + w);
    						w.onClick();
    					}
    				}
    			}
    			
    		}
        }
    }
    
    /**
     * Définition physique du personnage
     */
    public void definePerso() {
    	BodyDef bdef = new BodyDef();
    	bdef.position.set(16/Sandman.PPM, 64/Sandman.PPM);
    	bdef.type = BodyDef.BodyType.DynamicBody;
    	b2body = level.getWorld().createBody(bdef);
    	
    	FixtureDef fdef = new FixtureDef();
    	PolygonShape shape = new PolygonShape();
    	shape.setAsBox(10/Sandman.PPM, 14/Sandman.PPM);
    	
    	fdef.shape = shape;
    	b2body.createFixture(fdef);
    	
    }

	@Override
	public void dispose() {
		bruitSaut.dispose();
		
	}
}
