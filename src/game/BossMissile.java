package game;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class BossMissile extends Circle {
	
	private Sprite target = null;
	private double initialTargetPosX = 0;
	private double initialTargetPosY = 0;
	private double speed = 2;
	private double direction = 1;
	
	
	public BossMissile (double xpos, double ypos, int radius, Sprite target) {
		setCenterX(xpos);
		setCenterY(ypos);
		setRadius(radius);
		this.target = target;
		initialTargetPosX = target.getX();
		initialTargetPosY = target.getY();
		setFill(Color.LIMEGREEN);
	}


	public Sprite getTarget() {
		return target;
	}


	public void setTarget (Sprite target) {
		this.target = target;
	}


	public double getInitialTargetPosX () {
		return initialTargetPosX;
	}


	public void setInitialTargetPosX (double initialTargetPosX) {
		this.initialTargetPosX = initialTargetPosX;
	}


	public double getInitialTargetPosY () {
		return initialTargetPosY;
	}


	public void setInitialTargetPosY (double initialTargetPosY) {
		this.initialTargetPosY = initialTargetPosY;
	}
	
	public void determineDirection () {
		double distanceX = initialTargetPosX - getCenterX();
		double distanceY = initialTargetPosY - getCenterY();
		direction = Math.atan2(distanceY,distanceX);
	}
	
	public void move () {
		setCenterX(getCenterX() + speed * Math.cos(direction));
		setCenterY(getCenterY() + speed * Math.sin(direction));
	}
}
