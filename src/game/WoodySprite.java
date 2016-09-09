package game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class WoodySprite extends Sprite{
	
	public WoodySprite(int xpos, int ypos, int direction){
		facingRight = new Image(getClass().getClassLoader().getResourceAsStream("woodyFacingRight.png"));
		facingLeft = new Image(getClass().getClassLoader().getResourceAsStream("woodyFacingLeft.png"));
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
		setXVelocity(2.5);
	}
	
	public void move(){
		if(getX() >= 350){
			setDirection(-1);
		}else if(getX() <= 50){
			setDirection(1);
		}
		setX(getX() + getXVelocity() * getDirection());
	}
}
