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
	
	private Scene gameScene;
	private ArrayList<Rectangle> blocks;
	private ArrayList<Character> enemies;

	private ImageView mainCharacter;
	private Image spriteFacingRight;
	private Image spriteFacingLeft;
	private Image blockImage;
	private Image woodyFacingRight;
	private Image woodyFacingLeft;
	
	private int direction = 1;
	private double velocityX = 0;
	private double velocityY = 0;
	private double jumpInitialPos = 0;
	private boolean jumping = false;

	public Scene init (int width, int height) {
		blocks = new ArrayList<Rectangle>();
		enemies = new ArrayList<Character>();
		Group root = new Group();
		// create a place to see the shapes
		gameScene = new Scene(root, width, height, Color.WHITE);
		spriteFacingRight = new Image(getClass().getClassLoader().getResourceAsStream("SpriteFacingRight.png"));
		spriteFacingLeft = new Image(getClass().getClassLoader().getResourceAsStream("SpriteFacingLeft.png"));
		blockImage = new Image(getClass().getClassLoader().getResourceAsStream("block.png"));
		mainCharacter = new ImageView(spriteFacingLeft);
		mainCharacter.setX(0);
		mainCharacter.setY(460);
		mainCharacter.setFitWidth(25);
		mainCharacter.setFitHeight(40);
		Rectangle initBlock = new Rectangle(0,500,50,50);
		initBlock.setFill(new ImagePattern(blockImage));
		generateBlocks();
		blocks.add(initBlock);
		spawnWoodySprites();
		root.getChildren().addAll(blocks);
		for(Character enemy : enemies){
			root.getChildren().add(enemy.getSprite());
		}
		root.getChildren().add(mainCharacter);
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
			}
		}

		for(int i = 1; i<8; i++){
			for(int j = 0; j<4; j++){
				Rectangle newBlock = new Rectangle(i*50,j*200-100,50,50);
				newBlock.setFill(new ImagePattern(blockImage));
				blocks.add(newBlock);
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
			}
		}
	}

	public void step (double elapsedTime) {
		for(Character enemy:enemies){
			enemy.move();
		}
		
		boolean onSurface = false;
		
		if(direction == -1){
			mainCharacter.setImage(spriteFacingLeft);
		}else{
			mainCharacter.setImage(spriteFacingRight);
		}
        if(jumping && mainCharacter.getY()>jumpInitialPos-100){
			velocityY = -JUMP_SPEED;
		}else{
			jumping = false;
			velocityY = 0;
		}
		mainCharacter.setX(mainCharacter.getX() + velocityX * direction);

		for(int i = 0; i<blocks.size(); i++){
			Rectangle currBlock = blocks.get(i);
			if(mainCharacter.intersects(currBlock.getBoundsInLocal()) && mainCharacter.getY() + mainCharacter.getFitHeight() == currBlock.getY() && mainCharacter.getX() < currBlock.getX() + currBlock.getWidth() && mainCharacter.getX() + mainCharacter.getFitWidth() > currBlock.getX()){
				onSurface = true;
			}
			if(mainCharacter.intersects(currBlock.getBoundsInLocal()) && mainCharacter.getY() == currBlock.getY() + currBlock.getHeight() && mainCharacter.getX() < currBlock.getX() + currBlock.getWidth() && mainCharacter.getX() + mainCharacter.getFitWidth() > currBlock.getX()){
				mainCharacter.setY(currBlock.getY() + currBlock.getHeight());
				velocityY = 0;
				jumping = false;
			}
			if(mainCharacter.intersects(currBlock.getBoundsInLocal()) && mainCharacter.getX() + mainCharacter.getFitWidth() == currBlock.getX() && mainCharacter.getY() + mainCharacter.getFitHeight() > currBlock.getY() && mainCharacter.getY() < currBlock.getY() + currBlock.getHeight()){
				velocityX = 0;
			}
			if(mainCharacter.intersects(currBlock.getBoundsInLocal()) && mainCharacter.getX() == currBlock.getX() + currBlock.getWidth() && mainCharacter.getY() + mainCharacter.getFitHeight() > currBlock.getY() && mainCharacter.getY() < currBlock.getY() + currBlock.getHeight()){
				velocityX = 0;
			}
		}
			if(!onSurface && !jumping){
				velocityY = GRAVITY;
			}
			mainCharacter.setY(mainCharacter.getY()+velocityY);
	}

	private void handleKeyPress (KeyCode code) {
		switch (code) {
		case W:
	     	jumpInitialPos = mainCharacter.getY();
			break;
		case A:
			direction = -1;
			velocityX = MOVE_SPEED;
			break;
		case D:
			direction = 1;
			velocityX = MOVE_SPEED;
			break;
		default:
			// do nothing
		}
	}
	
	private void handleKeyRelease (KeyCode code) {
		switch (code) {
		case W:
	     	jumping = true;
			break;
		case A:
			if(direction == -1){
			velocityX = 0;
			}
			break;
		case D:
			if(direction == 1){
			velocityX = 0;
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