package game;

import javafx.scene.image.Image;

/**
 * The sprite the player fights at the boss level
 * 
 * @author Soravit Sophastienphong
 */
public class BossSprite extends Sprite {

    private int directionY = 1;

    /**
     * @param xpos The x position of the boss
     * @param ypos The y position of the boss
     * @param direction The turn direction of the boss (right or left)
     * @return A new instance of BossSprite
     */
    public BossSprite (double xpos, double ypos, int direction) {
        super(xpos,ypos,direction);
        facingRight = new Image(getClass().getClassLoader().getResourceAsStream("bossFacingRight.png"));
        facingLeft = new Image(getClass().getClassLoader().getResourceAsStream("bossFacingLeft.png"));
        setLeftImage(facingLeft);
        setRightImage(facingRight);
        setHeight(100);
        setWidth(100);
        setHealth(30);
        setXVelocity(2);
        setYVelocity(2);
    }

    /**
     * Algorithm that dictates the boss's movement in the horizontal and
     * vertical direction
     */
    public void move () {
        setX(getX() + getXVelocity() * getDirection());
        setY(getY() + getYVelocity() * getDirectionY());
        if(getX() >= Main.WIDTH - getWidth()) {
            setDirection(-1);
        }else if(getX() <= 0) {
            setDirection(1);
        }
        if(getY() >= Main.HEIGHT - getHeight()) {
            setDirectionY(-1);
        }else if(getY() <= 0) {
            setDirectionY(1);
        }
    }

    /**
     * Returns the y direction
     */
    public int getDirectionY () {
        return directionY;
    }

    /**
     * Sets the y direction
     */
    public void setDirectionY (int directionY) {
        this.directionY = directionY;
    }
}