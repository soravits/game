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
	
	public Sprite(){
        this(0,0,1);
	}
	
	public Sprite(double xpos, double ypos, int direction){
		facingRight = new Image(getClass().getClassLoader().getResourceAsStream("SpriteFacingRight.png"));
		facingLeft = new Image(getClass().getClassLoader().getResourceAsStream("SpriteFacingLeft.png"));
		sprite = new ImageView(facingRight);
		if(direction == 1){
			sprite.setImage(facingLeft);
		}else{
			sprite.setImage(facingRight);
		}
		sprite.setFitHeight(40);
		sprite.setFitWidth(25);
		setX(xpos);
		setY(ypos);
		setDirection(direction);
	}
	
	public ImageView getSprite(){
		return sprite;
	}
	
	public void setX(double xpos){
		sprite.setX(xpos);
	}
	
	public double getX(){
		return sprite.getX();
	}
	
	public void setY(double ypos){
		sprite.setY(ypos);
	}
	
	public double getY(){
		return sprite.getY();
	}
	
	public void setDirection(int direction){
		this.direction = direction;
		if(direction == 1){
			sprite.setImage(facingRight);
		}else{
			sprite.setImage(facingLeft);
		}
	}
	public int getDirection(){
		return direction;
	};
	public void setXVelocity(double velX){
		this.velocityX = velX;
	}
	public double getXVelocity(){
		return velocityX;
	}
	public void setYVelocity(double velY){
		this.velocityY = velY;
	}
	public double getYVelocity(){
		return velocityY;
	}
	
	public void move(){
		
	}
}
