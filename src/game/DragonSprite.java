package game;

import java.util.Random;
import javafx.scene.image.Image;

/**
 * The dragon sprite that the player fights at the second level
 * 
 * @author Soravit Sophastienphong
 */
public class DragonSprite extends Sprite {

    /**
     * @param xpos The x position of the dragon
     * @param ypos The y position of the dragon
     * @param direction The turn direction of the dragon (right or left)
     * @return A new instance of DragonSprite
     */
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

    /**
     * Moves the dragon back and forth across the screen
     */
    public void move(){
        super.move();
        if(getX() >= Main.WIDTH - getWidth() * 1.5) {
            setDirection(-1);
        } else if(getX() <= 50) {
            setDirection(1);
        }
    }
}