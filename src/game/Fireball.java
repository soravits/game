package game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Fireball extends Sprite{

	public Fireball(double xpos, double ypos, int direction){
		facingRight = new Image(getClass().getClassLoader().getResourceAsStream("rightProjectile.png"));
		facingLeft = new Image(getClass().getClassLoader().getResourceAsStream("leftProjectile.png"));
		sprite = new ImageView(facingRight);
		if(direction == 1){
			sprite.setImage(facingLeft);
		}else{
			sprite.setImage(facingRight);
		}
		sprite.setFitHeight(30);
		sprite.setFitWidth(30);
		setX(xpos);
		setY(ypos);
		setDirection(direction);
		setXVelocity(5);
	}
	
	public void move(){
		setX(getX() + getXVelocity() * getDirection());
	}
}
