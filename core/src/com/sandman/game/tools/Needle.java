package com.sandman.game.tools;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.sandman.game.Sandman;
import com.sandman.game.Scene.Level;

public class Needle extends Sprite{

		private Level level;
		
		public Needle(Level level) {
			super(new TextureRegion(new Texture("images/needle.png")));
			this.level = level;
			setBounds((level.getCamera().position.x), (level.getCamera().position.y), 3/Sandman.PPM, 32/Sandman.PPM);

	 	    setOriginCenter();
	 	    setOrigin(getOriginX()-getWidth()/2 +1/Sandman.PPM, getOriginY() - getHeight()/2 + 16/Sandman.PPM);
		}
		
		public void update() {
			setPosition((level.getCamera().position.x) + (level.getCamera().viewportWidth/2) - 24/Sandman.PPM - getWidth() ,(level.getCamera().position.y) - (level.getCamera().viewportHeight/2) + 22/Sandman.PPM);
	 	    if(level.getPerso().getGel()) {
	 	    	rotate(1.2f);
	 	    }else {
	 	    	setRotation(0);
	 	    }
	 	    
		}
}
