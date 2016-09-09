package game;

import java.util.ArrayList;
import java.util.Random;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class GameFrame {
	public static final String TITLE = "Mamba's Revenge";
	public static final double MOVE_SPEED = 5;
	public static final double JUMP_SPEED = 5;
	public static final double GRAVITY = 2.5;

	private Scene gameScene;
	private Group root;
	private int level = 1;

	private ArrayList<Rectangle> blocks;
	private ArrayList<Sprite> enemies;
	private ArrayList<Fireball> projectiles;

	private Sprite mainCharacter;
	private Image blockImage;

	private double jumpInitialPos = 0;
	private boolean jumping = false;
	private boolean onSurface;

	private void generateBlocks(){
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

	private void spawnWoodySprites(){
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

	private void checkPlayerCollisions(){
		onSurface = false;		
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
		
		for(int i = 0; i<enemies.size(); i++){
			Sprite enemy = enemies.get(i);
			if(mainCharacter.getSprite().getBoundsInLocal().intersects(enemy.getSprite().getBoundsInLocal())){
				
			}
		}
	}

	private void checkEnemyCollisions(){
		for(int i = 0; i<enemies.size(); i++){
			Sprite enemy = enemies.get(i);

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
	}

	public Scene init (int width, int height) {
		blocks = new ArrayList<Rectangle>();
		enemies = new ArrayList<Sprite>();
		projectiles = new ArrayList<Fireball>();
		root = new Group();
		gameScene = new Scene(root, width, height, Color.WHITE);
		blockImage = new Image(getClass().getClassLoader().getResourceAsStream("block.png"));
		Rectangle initBlock = new Rectangle(0,500,50,50);
		initBlock.setFill(new ImagePattern(blockImage));
		generateBlocks();
		blocks.add(initBlock);
		root.getChildren().add(initBlock);

		mainCharacter = new Sprite(50,460,1);
		root.getChildren().add(mainCharacter.getSprite());

		spawnWoodySprites();

		gameScene.setOnKeyPressed(e -> handleKeyPress(e.getCode()));
		gameScene.setOnKeyReleased(e-> handleKeyRelease(e.getCode()));
		return gameScene;
	}

	public void step (double elapsedTime) {

		mainCharacter.checkBounds();
		if(mainCharacter.getY() < 0 || level == 2){		
			if(level == 1){
				level++;
			}
			root.getChildren().clear();
			generateBlocks();
			root.getChildren().add(mainCharacter.getSprite());
			mainCharacter.setX(50);
			mainCharacter.setY(460);
		}

		for(int i = 0; i < projectiles.size(); i++){
			Fireball projectile = projectiles.get(i);
			projectile.move();
			if(projectile.getX() > projectile.getInitialXPosition() + 100 || projectile.getX() < projectile.getInitialXPosition() - 100 || projectile.getX() > 400 || projectile.getX() < 0){
				root.getChildren().remove(projectile.getSprite());
				projectiles.remove(projectile);
			}
		}

		for(Sprite enemy:enemies){
			enemy.move();
		}

		if(jumping && mainCharacter.getY()>jumpInitialPos-130){
			mainCharacter.setYVelocity(-JUMP_SPEED);
		}else{
			jumping = false;
			mainCharacter.setYVelocity(0);
		}

		mainCharacter.setX(mainCharacter.getX() + mainCharacter.getXVelocity() * mainCharacter.getDirection());

		checkPlayerCollisions();
		checkEnemyCollisions();
		if(!onSurface && !jumping){
			mainCharacter.setYVelocity(GRAVITY);
		}
		mainCharacter.setY(mainCharacter.getY() + mainCharacter.getYVelocity());
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
			if(projectiles.size() == 0){
				Fireball projectile = new Fireball(mainCharacter.getX()+10, mainCharacter.getY(), mainCharacter.getDirection());
				projectiles.add(projectile);
				root.getChildren().add(projectile.getSprite());
			}
		default:
			break;
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
			break;
		}
	}

	public String getTitle() {
		return TITLE;
	}
}