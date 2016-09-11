package game;

import java.util.Random;
import javafx.scene.image.Image;

public class DragonSprite extends Sprite {

	public DragonSprite (double xpos, double ypos, int direction) {
		super(xpos,ypos,direction);
		facingRight = new Image(getClass().getClassLoader().getResourceAsStream("dragonFacingRight.png"));
		facingLeft = new Image(getClass().getClassLoader().getResourceAsStream("dragonFacingLeft.png"));
		setLeftImage(facingLeft);
		setRightImage(facingRight);
		setHeight(50);
		setWidth(50);
		if(getY() == Main.WIDTH + getHeight()) {
			setXVelocity(2);
		} else {
			Random rand = new Random();
			int speed = rand.nextInt(8) + 3;
			setXVelocity(speed);
		}
		setHealth(1);
	}
}
