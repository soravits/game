package game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class Character {
	
	public ImageView sprite;
	public Image facingRight;
	public Image facingLeft;
	
	private int direction = 1;
	private double velocityX = 1;
	private double velocityY = 0;
	
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
		if(getX() >= 350){
			setDirection(-1);
		}else if(getX() <= 50){
			setDirection(1);
		}
		setX(getX() + velocityX * getDirection());
	}
}
