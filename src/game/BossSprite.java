package game;

import java.util.ArrayList;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.shape.Circle;

public class BossSprite extends Sprite {

	private int directionY = 1;

	public BossSprite (double xpos, double ypos, int direction) {
		super(xpos,ypos,direction);
		facingRight = new Image(getClass().getClassLoader().getResourceAsStream("bossFacingRight.png"));
		facingLeft = new Image(getClass().getClassLoader().getResourceAsStream("bossFacingLeft.png"));
		setLeftImage(facingLeft);
		setRightImage(facingRight);
		setHeight(100);
		setWidth(100);
		setHealth(50);
		setXVelocity(1);
		setYVelocity(1);
	}

	public void move () {
		if(getX() >= Main.WIDTH - getWidth()) {
			setDirection(-1);
		}else if(getX() <= 0) {
			setDirection(1);
		}
		if(getY() >= Main.HEIGHT - getHeight()) {
			setDirectionY(-1);
		}else if(getY() <= 0) {
			setDirectionY(1);
		}
		setX(getX() + getXVelocity() * getDirection());
		setY(getY() + getYVelocity() * getDirectionY());
	}

	public int getDirectionY () {
		return directionY;
	}

	public void setDirectionY (int directionY) {
		this.directionY = directionY;
	}
}
