package game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class WoodySprite extends Sprite{
	
	public WoodySprite(int xpos, int ypos, int direction){
		super(xpos,ypos,direction);
		facingRight = new Image(getClass().getClassLoader().getResourceAsStream("woodyFacingRight.png"));
		facingLeft = new Image(getClass().getClassLoader().getResourceAsStream("woodyFacingLeft.png"));
		setLeftImage(facingLeft);
		setRightImage(facingRight);
		setHeight(30);
		setWidth(30);
		setXVelocity(2.5);
	}
}
