package game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Fireball extends Sprite {
	
	private double initialXPosition;
	
	public Fireball (double xpos, double ypos, int direction) {
		super(xpos,ypos,direction);
		facingRight = new Image(getClass().getClassLoader().getResourceAsStream("rightProjectile.png"));
		facingLeft = new Image(getClass().getClassLoader().getResourceAsStream("leftProjectile.png"));
		setLeftImage(facingLeft);
		setRightImage(facingRight);
		initialXPosition = xpos;
		setHeight(30);
		setWidth(30);
		setXVelocity(10);
	}
	
	public void move() {
		setX(getX() + getXVelocity() * getDirection());
	}
	
	public double getInitialXPosition() {
		return initialXPosition;
	}
}
