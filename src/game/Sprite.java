package game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/** 
 * This is the superclass from which all sprites in the game inherit. It
 * contains many initial values and their respective getters
 * and setters. It also defines a default method for movement.
 * 
 * @author Soravit Sophastienphong
 */

public class Sprite {

    protected ImageView sprite;
    protected Image facingRight;
    protected Image facingLeft;

    private int direction = 1;
    private double velocityX = 0;
    private double velocityY = 0;
    private int health = 1;

    /**
     * The default constructor
     */
    public Sprite(){
        this(460,0,1);
    }

    /**
     * The constructor that sets the initial values for the sprite
     * @param xpos The x position of the sprite
     * @param ypos The y position of the sprite
     * @param direction The direction that the sprite turns
     */
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

    /**
     * Returns the ImageView of the sprite
     */
    public ImageView getSprite () {
        return sprite;
    }

    /**
     * Returns the x position of the sprite
     */
    public double getX () {
        return sprite.getX();
    }

    /**
     * Sets the x position of the sprite
     */
    public void setX (double xpos) {
        sprite.setX(xpos);
    }

    /**
     * Returns the y position of the sprite
     */
    public double getY () {
        return sprite.getY();
    }

    /**
     * Sets the y position of the sprite
     */
    public void setY (double ypos) {
        sprite.setY(ypos);
    }

    /**
     * Returns the direction of the sprite
     */
    public int getDirection () {
        return direction;
    };

    /**
     * Sets the direction of the sprite
     */
    public void setDirection (int direction) {
        this.direction = direction;
        if(direction == 1) {
            sprite.setImage(facingRight);
        } else {
            sprite.setImage(facingLeft);
        }
    }

    /**
     * Returns the x velocity
     */
    public double getXVelocity () {
        return velocityX;
    }

    /**
     * Sets the x velocity
     */
    public void setXVelocity (double velX) {
        this.velocityX = velX;
    }

    /**
     * Returns the y velocity
     */
    public double getYVelocity () {
        return velocityY;
    }

    /**
     * Sets the y velocity
     */
    public void setYVelocity (double velY) {
        this.velocityY = velY;
    }

    /**
     * Returns the height of the sprite
     */
    public double getHeight () {
        return sprite.getFitHeight();
    }

    /**
     * Sets the height of the sprite
     */
    public void setHeight (double height) {
        sprite.setFitHeight(height);
    }

    /**
     * Returns the width of the sprite
     */
    public double getWidth () {
        return sprite.getFitWidth();
    }

    /**
     * Sets the width of the sprite
     */
    public void setWidth (double width) {
        sprite.setFitWidth(width);
    }

    /**
     * Returns the health of the sprite
     */
    public int getHealth() {
        return health;
    }

    /**
     * Sets the health of the sprite
     */
    public void setHealth (int health) {
        this.health = health;
    }

    /**
     * Sets the image of the sprite facing left
     */
    public void setLeftImage (Image left) {
        sprite.setImage(left);
    }

    /**
     * Sets the image of the sprite facing right
     */
    public void setRightImage (Image right) {
        sprite.setImage(right);
    }

    /**
     * Checks whether the sprite is going off the side of the screen
     * and moves him to the other side if it does
     */
    public void checkBounds () {
        if(getX() + sprite.getFitWidth() > Main.WIDTH) {
            setX(0);
        } else if(getX() < 0) {
            setX(Main.WIDTH - sprite.getFitWidth());
        }
    }

    /**
     * Moves the sprite according to his current velocities and direction
     */
    public void move() {
        setX(getX() + getXVelocity() * getDirection());
        setY(getY() + getYVelocity());
    }
}