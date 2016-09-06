package game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class WoodySprite extends Character{

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
	}
	
	public void setDirection(int direction){
		super.setDirection(direction);
		if(direction == 1){
			sprite.setImage(facingRight);
		}else{
			sprite.setImage(facingLeft);
		}
	}
	
}
