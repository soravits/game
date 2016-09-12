package game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * The projectile that the player can shoot to kill/damage enemies
 * 
 * @author Soravit Sophastienphong
 */
public class Fireball extends Sprite {

    private double initialXPosition;

    /**
     * @param xpos The x position of the projectile
     * @param ypos The y position of the projectile
     * @param direction The initial direction that the projectile moves(left or right)
     * @return A new instance of Fireball
     */
    public Fireball (double xpos, double ypos, int direction) {
        super(xpos,ypos,direction);
        facingRight = new Image(getClass().getClassLoader().getResourceAsStream("rightProjectile.png"));
        facingLeft = new Image(getClass().getClassLoader().getResourceAsStream("leftProjectile.png"));
        setLeftImage(facingLeft);
        setRightImage(facingRight);
        initialXPosition = xpos;
        setHeight(30);
        setWidth(30);
        setXVelocity(10);
    }

    /**
     * Returns the initial x position of the projectile when
     * created
     */
    public double getInitialXPosition() {
        return initialXPosition;
    }
}
