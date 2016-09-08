package game;

import java.util.ArrayList;
import java.util.Random;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class GameFrame {
	public static final String TITLE = "Mamba's Revenge";
	public static final double MOVE_SPEED = 5;
	public static final double JUMP_SPEED = 5;
	public static final double GRAVITY = 2.5;

	private Group root;
	
	private Scene gameScene;
	private ArrayList<Rectangle> blocks;
	private ArrayList<Sprite> enemies;
	private ArrayList<Sprite> projectiles;

	private Sprite mainCharacter;
	private Image blockImage;

	private double jumpInitialPos = 0;
	private boolean jumping = false;
	private long jumpTime;
	boolean onSurface;

	public Scene init (int width, int height) {
		blocks = new ArrayList<Rectangle>();
		enemies = new ArrayList<Sprite>();
		projectiles= new ArrayList<Sprite>();
		root = new Group();
		// create a place to see the shapes
		gameScene = new Scene(root, width, height, Color.WHITE);
		blockImage = new Image(getClass().getClassLoader().getResourceAsStream("block.png"));
		mainCharacter = new Sprite(0,460,1);
		Rectangle initBlock = new Rectangle(0,500,50,50);
		initBlock.setFill(new ImagePattern(blockImage));
		generateBlocks();
		blocks.add(initBlock);
		root.getChildren().add(initBlock);
		spawnWoodySprites();
		root.getChildren().add(mainCharacter.getSprite());
		gameScene.setOnKeyPressed(e -> handleKeyPress(e.getCode()));
		gameScene.setOnKeyReleased(e-> handleKeyRelease(e.getCode()));
		return gameScene;
	}

	public void generateBlocks(){
		blocks.clear();
		for(int i = 0; i<7; i++){
			for(int j = 0; j<3; j++){
				Rectangle newBlock = new Rectangle(i*50,j*200,50,50);
				newBlock.setFill(new ImagePattern(blockImage));
				blocks.add(newBlock);
				root.getChildren().add(newBlock);
			}
		}

		for(int i = 1; i<8; i++){
			for(int j = 0; j<4; j++){
				Rectangle newBlock = new Rectangle(i*50,j*200-100,50,50);
				newBlock.setFill(new ImagePattern(blockImage));
				blocks.add(newBlock);
				root.getChildren().add(newBlock);
			}
		}
	}

	public void spawnWoodySprites(){
		for(int i  = 0; i<5; i++){
			for(int j = 0; j<2; j++){
				Random rand = new Random();
				int xPos = rand.nextInt(300);
				WoodySprite woodySprite = new WoodySprite(xPos + 50,100*i + 70,1);
				if(xPos > 200){
					woodySprite.setDirection(1);
				}else{
					woodySprite.setDirection(-1);
				}
				enemies.add(woodySprite);
				root.getChildren().add(woodySprite.getSprite());
			}
		}
	}

	public void step (double elapsedTime) {
		
		if(mainCharacter.getX() + mainCharacter.getSprite().getFitWidth() > 400){
			mainCharacter.setX(0);
		}else if(mainCharacter.getX() < 0){
			mainCharacter.setX(400-mainCharacter.getSprite().getFitWidth());
		}
		
		for(int i = 0; i<enemies.size(); i++){
			Sprite enemy = enemies.get(i);
			enemy.move();
			for(int j = 0; j < projectiles.size(); j++){
				Sprite projectile = projectiles.get(j);
				if(enemy.getSprite().getBoundsInLocal().intersects(projectile.getSprite().getBoundsInLocal())){
					root.getChildren().remove(projectile.getSprite());
					root.getChildren().remove(enemy.getSprite());
					enemies.remove(enemy);
					projectiles.remove(projectile);
				}
			}
		}

		for(Sprite projectile:projectiles){
			projectile.move();
		}

		onSurface = false;

		if(jumping && mainCharacter.getY()>jumpInitialPos-100){
			mainCharacter.setYVelocity(-JUMP_SPEED);
		}else{
			jumping = false;
			mainCharacter.setYVelocity(0);
		}
		mainCharacter.setX(mainCharacter.getX() + mainCharacter.getXVelocity() * mainCharacter.getDirection());

		for(int i = 0; i<blocks.size(); i++){
			Rectangle currBlock = blocks.get(i);
			if(mainCharacter.getSprite().getBoundsInLocal().intersects(currBlock.getBoundsInLocal()) && mainCharacter.getY() + mainCharacter.getSprite().getFitHeight() == currBlock.getY() && mainCharacter.getX() < currBlock.getX() + currBlock.getWidth() && mainCharacter.getX() + mainCharacter.getSprite().getFitWidth() > currBlock.getX()){
				onSurface = true;
			}
			if(mainCharacter.getSprite().getBoundsInLocal().intersects(currBlock.getBoundsInLocal()) && mainCharacter.getY() == currBlock.getY() + currBlock.getHeight() && mainCharacter.getX() < currBlock.getX() + currBlock.getWidth() && mainCharacter.getX() + mainCharacter.getSprite().getFitWidth() > currBlock.getX()){
				mainCharacter.setY(currBlock.getY() + currBlock.getHeight());
				mainCharacter.setYVelocity(0);
				jumping = false;
			}
			if(mainCharacter.getSprite().getBoundsInLocal().intersects(currBlock.getBoundsInLocal()) && mainCharacter.getX() + mainCharacter.getSprite().getFitWidth() == currBlock.getX() && mainCharacter.getY() + mainCharacter.getSprite().getFitHeight() > currBlock.getY() && mainCharacter.getY() < currBlock.getY() + currBlock.getHeight()){
				mainCharacter.setXVelocity(0);
			}
			if(mainCharacter.getSprite().getBoundsInLocal().intersects(currBlock.getBoundsInLocal()) && mainCharacter.getX() == currBlock.getX() + currBlock.getWidth() && mainCharacter.getY() + mainCharacter.getSprite().getFitHeight() > currBlock.getY() && mainCharacter.getY() < currBlock.getY() + currBlock.getHeight()){
				mainCharacter.setXVelocity(0);
			}
		}
		
		if(!onSurface && !jumping){
			mainCharacter.setYVelocity(GRAVITY);
		}
		mainCharacter.setY(mainCharacter.getY()+mainCharacter.getYVelocity());
	}

	private void handleKeyPress (KeyCode code) {
		switch (code) {
		case W:
			jumpInitialPos = mainCharacter.getY();
			break;
		case A:
			mainCharacter.setDirection(-1);
			mainCharacter.setXVelocity(MOVE_SPEED);
			break;
		case D:
			mainCharacter.setDirection(1);
			mainCharacter.setXVelocity(MOVE_SPEED);
			break;
		case SPACE:
			Fireball fireball = new Fireball(mainCharacter.getX()+10, mainCharacter.getY(), mainCharacter.getDirection());
			projectiles.add(fireball);
			root.getChildren().add(fireball.getSprite());
		default:
			// do nothing
		}
	}

	private void handleKeyRelease (KeyCode code) {
		switch (code) {
		case W:
			if(onSurface)
			jumping = true;
			break;
		case A:
			if(mainCharacter.getDirection() == -1){
				mainCharacter.setXVelocity(0);
			}
			break;
		case D:
			if(mainCharacter.getDirection() == 1){
				mainCharacter.setXVelocity(0);
			}
			break;
		default:
			// do nothing
		}
	}

	public String getTitle() {
		return TITLE;
	}
}