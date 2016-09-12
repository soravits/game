package game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * @author Soravit Sophastienphong
 *
 * Defines the sprite that fights with the player at the first level
 */
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

    public void move(){
        super.move();
        if(getX() >= Main.WIDTH - getWidth() * 1.5) {
            setDirection(-1);
        } else if(getX() <= 50) {
            setDirection(1);
        }
    }
}
