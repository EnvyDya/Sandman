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
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.sandman.game.Sandman;
import com.sandman.game.Scene.Level;
import com.sandman.game.sprites.interfaces.CanDie;

public class Perso extends Sprite implements Disposable,CanDie{
    public enum State  { FALLING, JUMPING, STANDING, RUNNING};
    public Level level;
    public State currentState;
    public State previousState;
    public Body b2body;
	private Fixture fixture;
    
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
	private Boolean landing;

	//Attributs Gel
	private boolean gel;
	private float tmpsGel;
	private InteractiveTileObject objetGel;
	private Sound bruitHorloge;

	//Attributs Mort
	private boolean dead;
	private float spawnx;
	private float spawny;
  
    //Constructeur
    public Perso(Level level,float spawnx,float spawny) {
    	super(new TextureRegion(new Texture("images/Sandman.png")));
		this.level = level;

		//Initialisation Animation
		currentState = State.STANDING;
		previousState = State.STANDING;

		//Initialise les différents sons
		bruitSaut = Gdx.audio.newSound(Gdx.files.internal("sounds/bruitSaut.wav"));
		bruitHorloge = Gdx.audio.newSound(Gdx.files.internal("sounds/tic tac.mp3"));

		stateTimer = 0;
		runningRight = true;
		dead = false;
		landing = true;
		this.spawnx =spawnx;
		this.spawny = spawny;

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
    	else if(b2body.getLinearVelocity().y < 0f) {
    		return State.FALLING;
    	}
    	else if(b2body.getLinearVelocity().x != 0&&b2body.getLinearVelocity().y == 0 && landing) {
    		return State.RUNNING;
    	}
    	else if(b2body.getLinearVelocity().y == 0 && landing){
			return State.STANDING;
		}
		else return previousState;
    }
    
    /**
     * Met à jour la frame de l'animation en fonction de la position
     * @param dt
     */
	public void update(float dt){
		setPosition(b2body.getPosition().x - getWidth()/2, b2body.getPosition().y + .15f - getHeight()/2);

		//Affiche la frame de la texture
		setRegion(getFrame(dt));

		//Gère le gel de l'objet
		if(gel){
			gestionGel(dt);
		}

		//Gère la mort du perso si le perso sort de la carte
		if(b2body.getPosition().y <=0){
			die();
		}

		if(dead){
			respawn();
		}
	}

	//lance le respawn du joueur en le repositionnant au spawn et annule son dernier gel
	public void respawn(){
		b2body.setTransform(new Vector2(spawnx, spawny), 0);
		level.getCamera().setToOrtho(false, 30, 20);
		dead = false;
		if(gel){
			tmpsGel = 0;
			gel = false;
			objetGel.onClick();
			bruitHorloge.stop();
		}
	}

	public void gestionGel(float dt){
		tmpsGel += dt;
		if(tmpsGel>5){
			tmpsGel = 0;
			gel = false;
			objetGel.onClick();
			bruitHorloge.stop();
		}
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
      
    	//On vérifie si une touche de saut est appuyée et que le joueur ne soit pas déjà dans les airs
    	if(Gdx.input.isKeyPressed(Input.Keys.SPACE) && (getState() == State.STANDING || getState() == State.RUNNING)) {
			landing = false;
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
    		//System.out.println("Clic en " + pos.x + "x et " + pos.y + "y.");
    		pos = level.getCamera().unproject(pos);
    		//On recherche tous les body du monde
    		Array<Body> al = new Array<Body>();
    		level.getWorld().getBodies(al);
    		for(Body b : al) {
    			//Pour chaque body, on teste si notre clic est dedans
    			if(b.getFixtureList().get(0).testPoint(pos.x, pos.y)) {
    				//Si le clic est dedans, on teste chaque InteractiveTile du niveau pour voir si on a le même body
    				for(InteractiveTileObject o : level.getWorldCreator().interactiveTiles) {
    					if(o.body == b) {
    						//Le cas échéant, on réalise le comportement associé à l'objet récupéré
    						//System.out.println("Contact avec l'objet interactif : " + o);
							if(!gel || o == objetGel){
								o.onClick();
								if(!gel) {
									bruitHorloge.play(0.5f);
								}else {
									bruitHorloge.stop();
								}
								tmpsGel = 0;
								objetGel = o;
								gel = !gel;
							}
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

		bdef.position.set(spawnx, spawny);

    	bdef.type = BodyDef.BodyType.DynamicBody;
    	b2body = level.getWorld().createBody(bdef);
    	
    	FixtureDef fdef = new FixtureDef();
    	PolygonShape shape = new PolygonShape();
    	shape.setAsBox(10/Sandman.PPM, 14/Sandman.PPM);
    	
    	fdef.shape = shape;
    	fixture = b2body.createFixture(fdef);
		fixture.setUserData(this);

		//Création d'un sensor en dessous du perso qui va detecté les colisions
        EdgeShape pied = new EdgeShape();
        pied.set(new Vector2(-9/Sandman.PPM, -14/Sandman.PPM),new Vector2(9/Sandman.PPM, -14/Sandman.PPM));
        fdef.shape = pied;
        fdef.isSensor = true;
        b2body.createFixture(fdef).setUserData("pied");
    	
    }

	public boolean getGel(){
		return gel;
	}

	public InteractiveTileObject getObjetGel() {
		return objetGel;
	}

	public void land(){
		landing = true;
	}

	@Override
	public void dispose() {
		bruitSaut.dispose();
		bruitHorloge.dispose();
	}

	@Override
	public void die() {
		dead = true;
	}
}
