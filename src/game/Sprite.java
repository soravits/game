package game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Sprite {

	protected ImageView sprite;
	protected Image facingRight;
	protected Image facingLeft;

	private int direction = 1;
	private double velocityX = 0;
	private double velocityY = 0;
	private int health = 1;

	public Sprite(){
		this(460,0,1);
	}

	public Sprite (double xpos, double ypos, int direction) {
		facingRight = new Image(getClass().getClassLoader().getResourceAsStream("SpriteFacingRight.png"));
		facingLeft = new Image(getClass().getClassLoader().getResourceAsStream("SpriteFacingLeft.png"));
		sprite = new ImageView(facingRight);
		if(direction == 1) {
			sprite.setImage(facingLeft);
		} else {
			sprite.setImage(facingRight);
		}
		sprite.setFitHeight(40);
		sprite.setFitWidth(25);
		setX(xpos);
		setY(ypos);
		setDirection(direction);
	}

	public ImageView getSprite () {
		return sprite;
	}

	public void setX (double xpos) {
		sprite.setX(xpos);
	}

	public double getX () {
		return sprite.getX();
	}

	public void setY (double ypos) {
		sprite.setY(ypos);
	}

	public double getY () {
		return sprite.getY();
	}

	public void setDirection (int direction) {
		this.direction = direction;
		if(direction == 1) {
			sprite.setImage(facingRight);
		} else {
			sprite.setImage(facingLeft);
		}
	}
	public int getDirection () {
		return direction;
	};
	public void setXVelocity (double velX) {
		this.velocityX = velX;
	}
	public double getXVelocity () {
		return velocityX;
	}
	public void setYVelocity (double velY) {
		this.velocityY = velY;
	}
	public double getYVelocity () {
		return velocityY;
	}

	public void checkBounds () {
		if(getX() + sprite.getFitWidth() > Main.WIDTH) {
			setX(0);
		} else if(getX() < 0) {
			setX(Main.WIDTH - sprite.getFitWidth());
		}
	}

	public void setHeight (double height) {
		sprite.setFitHeight(height);
	}

	public double getHeight () {
		return sprite.getFitHeight();
	}

	public void setWidth (double width) {
		sprite.setFitWidth(width);
	}

	public double getWidth () {
		return sprite.getFitWidth();
	}

	public void setLeftImage (Image left) {
		sprite.setImage(left);
	}

	public void setRightImage (Image right) {
		sprite.setImage(right);
	}
	
	public void move() {
		setX(getX() + getXVelocity() * getDirection());
		if(getX() >= Main.WIDTH - getWidth() * 1.5) {
			setDirection(-1);
		} else if(getX() <= 50) {
			setDirection(1);
		}
	}
	
	public int getHealth() {
		return health;
	}

	public void setHealth (int health) {
		this.health = health;
	}
}
