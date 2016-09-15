package game;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * This class represents the projectile that targets the player 
 * during the boss level.
 * 
 * @author Soravit Sophastienphong
 */
public class BossMissile extends Circle {

    private Sprite target = null;
    private double initialTargetPosX = 0;
    private double initialTargetPosY = 0;
    private double speed = 2;
    private double direction = 1;

    /**
     * @param xpos The x position of the projectile
     * @param ypos The y position of the projectile
     * @return A new instance of BossMissile
     */
    public BossMissile (double xpos, double ypos, int radius, Sprite target) {
        setCenterX(xpos);
        setCenterY(ypos);
        setRadius(radius);
        setTarget(target);
        initialTargetPosX = target.getX();
        initialTargetPosY = target.getY();
        setFill(Color.LIMEGREEN);
    }

    /**
     * Returns the target of the projectile
     */
    public Sprite getTarget () {
        return target;
    }

    /**
     * Sets the target of the projectile
     */
    public void setTarget (Sprite target) {
        this.target = target;
    }

    /**
     * Returns the initial x position of the target
     */
    public double getInitialTargetPosX () {
        return initialTargetPosX;
    }

    /**
     * Sets the initial x position of the target
     */
    public void setInitialTargetPosX (double initialTargetPosX) {
        this.initialTargetPosX = initialTargetPosX;
    }

    /**
     * Returns the initial y position of the target
     */
    public double getInitialTargetPosY () {
        return initialTargetPosY;
    }

    /** 
     * Sets the initial y position of the target
     */
    public void setInitialTargetPosY (double initialTargetPosY) {
        this.initialTargetPosY = initialTargetPosY;
    }

    /**
     * Moves the projectile towards the initial position of the target
     */
    public void move () {
        setCenterX(getCenterX() + speed * Math.cos(direction));
        setCenterY(getCenterY() + speed * Math.sin(direction));
    }

    /**
     * Finds an angle that indicates the direction of the
     * target from the position of the projectile
     */
    public void determineDirection () {
        double distanceX = initialTargetPosX - getCenterX();
        double distanceY = initialTargetPosY - getCenterY();
        direction = Math.atan2(distanceY, distanceX);
    }
}