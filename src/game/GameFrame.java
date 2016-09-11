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
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class GameFrame {
	public static final String TITLE = "Mamba's Revenge";
	public static final double MOVE_SPEED = 5;
	public static final double JUMP_SPEED = 5;
	public static final double GRAVITY = 2.5;
	public static final double BLOCK_WIDTH = 50;
	public static final double BLOCK_HEIGHT = 50;
	public static final int PROJECTILE_MOVE_DISTANCE = 150;

	private Scene gameScene;
	private Scene splashScene;
	private Scene finalScene;

	private Group root;
	private Group messages;

	private ArrayList<Rectangle> blocks;
	private ArrayList<Sprite> enemies;
	private ArrayList<Fireball> projectiles;
	private ArrayList<BossMissile> bossMissiles;

	private Sprite mainCharacter;
	private BossSprite boss;
	private Image blockSprite;

	private int currLevel = 1;

	private double initialJumpingPosition = 0;
	private boolean jumpingUp = false;
	private boolean charOnBlock = false;

	private void startGame () {
		Rectangle initBlock = new Rectangle(0, 500, 50, 50);
		initBlock.setFill(new ImagePattern(blockSprite));
		generateBlocks();
		blocks.add(initBlock);
		root.getChildren().add(initBlock);
		mainCharacter = new Sprite(0, 460, 1);
		root.getChildren().add(mainCharacter.getSprite());
		spawnEnemies();
	}

	private void generateBlocks () {
		ImagePattern stonePattern = new ImagePattern(blockSprite);
		blocks.clear();
		for(int i = 0; i<7; i++) {
			for(int j = 0; j<3; j++) {
				Rectangle newBlock = new Rectangle(i*50, j*200, BLOCK_WIDTH, BLOCK_HEIGHT);
				newBlock.setFill(stonePattern);
				blocks.add(newBlock);
				root.getChildren().add(newBlock);
			}
		}
		for(int k = 1; k<8; k++) {
			for(int l = 0; l<4; l++) {
				Rectangle newBlock = new Rectangle(k*50, l*200-100, BLOCK_WIDTH, BLOCK_HEIGHT);
				newBlock.setFill(stonePattern);
				blocks.add(newBlock);
				root.getChildren().add(newBlock);
			}
		}
	}

	private void spawnEnemies () {
		if(currLevel == 1) {
			for(int i  = 0; i<5; i++) {
				for(int j = 0; j<2; j++) {
					Random rand = new Random();
					int xPos = rand.nextInt(300);
					WoodySprite woodySprite = new WoodySprite(xPos + 50, 100*i + 70, 1);
					if(xPos > Main.WIDTH/2) {
						woodySprite.setDirection(1);
					} else {
						woodySprite.setDirection(-1);
					}
					enemies.add(woodySprite);
					root.getChildren().add(woodySprite.getSprite());
				}
			}
		} else if(currLevel == 2) {
			for(int k  = 0; k<5; k++) {
				Random rand = new Random();
				int xPos = rand.nextInt(300);
				DragonSprite dragonSprite = new DragonSprite(xPos + 100, 100*k + 50, 1);
				if(xPos > Main.WIDTH/2){
					dragonSprite.setDirection(1);
				}else{
					dragonSprite.setDirection(-1);
				}
				enemies.add(dragonSprite);
				root.getChildren().add(dragonSprite.getSprite());
			}		
		}
	}

	private void checkPlayerCollisions () {
		charOnBlock = false;		
		for(int i = 0; i<blocks.size(); i++){
			Rectangle currBlock = blocks.get(i);
			if(mainCharacter.getSprite().getBoundsInLocal().intersects(currBlock.getBoundsInLocal()) && mainCharacter.getY() + mainCharacter.getSprite().getFitHeight() == currBlock.getY() && mainCharacter.getX() < currBlock.getX() + currBlock.getWidth() && mainCharacter.getX() + mainCharacter.getSprite().getFitWidth() > currBlock.getX()) {
				charOnBlock = true;
			}
			if(mainCharacter.getSprite().getBoundsInLocal().intersects(currBlock.getBoundsInLocal()) && mainCharacter.getY() == currBlock.getY() + currBlock.getHeight() && mainCharacter.getX() < currBlock.getX() + currBlock.getWidth() && mainCharacter.getX() + mainCharacter.getSprite().getFitWidth() > currBlock.getX()) {
				mainCharacter.setY(currBlock.getY() + currBlock.getHeight());
				mainCharacter.setYVelocity(0);
				jumpingUp = false;
			}
			if(mainCharacter.getSprite().getBoundsInLocal().intersects(currBlock.getBoundsInLocal()) && mainCharacter.getX() + mainCharacter.getSprite().getFitWidth() == currBlock.getX() && mainCharacter.getY() + mainCharacter.getSprite().getFitHeight() > currBlock.getY() && mainCharacter.getY() < currBlock.getY() + currBlock.getHeight()) {
				mainCharacter.setXVelocity(0);
			}
			if(mainCharacter.getSprite().getBoundsInLocal().intersects(currBlock.getBoundsInLocal()) && mainCharacter.getX() == currBlock.getX() + currBlock.getWidth() && mainCharacter.getY() + mainCharacter.getSprite().getFitHeight() > currBlock.getY() && mainCharacter.getY() < currBlock.getY() + currBlock.getHeight()) {
				mainCharacter.setXVelocity(0);
			}
		}
		for(int j = 0; j<enemies.size(); j++) {
			Sprite enemy = enemies.get(j);
			if(mainCharacter.getSprite().getBoundsInLocal().intersects(enemy.getSprite().getBoundsInLocal())) {
				playerLoses();
			}
		}
	}

	private void checkEnemyCollisions () {
		for(int i = 0; i<enemies.size(); i++) {
			Sprite enemy = enemies.get(i);
			for(int j = 0; j < projectiles.size(); j++) {
				Sprite projectile = projectiles.get(j);
				if(enemy.getSprite().getBoundsInLocal().intersects(projectile.getSprite().getBoundsInLocal())) {
					if(enemy.getHealth() > 1) {
						enemy.setHealth(enemy.getHealth()-1);
						root.getChildren().remove(projectile.getSprite());
						projectiles.remove(projectile);
					} else { 
						if(enemy instanceof BossSprite) {
							playerWins();
						}
						root.getChildren().remove(projectile.getSprite());
						root.getChildren().remove(enemy.getSprite());
						enemies.remove(enemy);
					}
				}
			}
		}	
	}

	public void playerWins () {
		messages.getChildren().clear();
		Text text = new Text("You've won! Well done!" + System.lineSeparator() + "Press enter to play again.");
		text.setFont(new Font("Arial Black",15));
		text.setX(20);
		text.setY(200);
		messages.getChildren().add(text);
		Main.currStage.setScene(finalScene);
	}

	public void playerLoses () {
		messages.getChildren().clear();
		Text text = new Text("Game Over. Mamba will never be avenged." + System.lineSeparator() + "Press enter for another chance.");
		text.setFont(new Font("Arial Black",15));
		text.setX(20);
		text.setY(200);
		messages.getChildren().add(text);
		Main.currStage.setScene(finalScene);
	}

	public Scene initializeSplashScreen (int width, int height) {
		Group menuItems = new Group();
		splashScene = new Scene(menuItems,width,height,Color.WHITE);
		ImageView controls = new ImageView(new Image(getClass().getClassLoader().getResourceAsStream("controls.png")));
		controls.setFitHeight(500);
		controls.setFitWidth(300);
		controls.setX(50);
		controls.setY(25);
		menuItems.getChildren().add(controls);
		splashScene.setOnKeyPressed(e -> handleKeyPress(e.getCode()));
		splashScene.setOnKeyReleased(e-> handleKeyRelease(e.getCode()));
		initialize();
		return splashScene;
	}

	public void initialize () {	
		root = new Group();
		messages = new Group();
		bossMissiles = new ArrayList<BossMissile>();
		blocks = new ArrayList<Rectangle>();
		enemies = new ArrayList<Sprite>();
		projectiles = new ArrayList<Fireball>();
		gameScene = new Scene(root, Main.WIDTH, Main.HEIGHT, Color.WHITE);
		finalScene = new Scene(messages, Main.WIDTH, Main.HEIGHT, Color.WHITE);
		blockSprite = new Image(getClass().getClassLoader().getResourceAsStream("block.png"));

		gameScene.setOnKeyPressed(e -> handleKeyPress(e.getCode()));
		gameScene.setOnKeyReleased(e-> handleKeyRelease(e.getCode()));
		finalScene.setOnKeyPressed(e -> handleKeyPress(e.getCode()));
		finalScene.setOnKeyReleased(e-> handleKeyRelease(e.getCode()));
	}

	public void goNextLevel () {	
		currLevel++;
		removeAllSprites();
		generateBlocks();
		if(currLevel == 2) {
			root.getChildren().add(mainCharacter.getSprite());
			mainCharacter.setX(50);
			mainCharacter.setY(460);
			spawnEnemies();
		} else if(currLevel == 3) {
			generateBossLevel();
		}
	}

	public void generateBossLevel () {
		removeAllSprites();
		currLevel = 3;
		for(int i = 0; i<8; i++){
			Rectangle bottomBlock = new Rectangle(i*50, 500, BLOCK_WIDTH, BLOCK_HEIGHT);
			bottomBlock.setFill(new ImagePattern(blockSprite));
			blocks.add(bottomBlock);
			root.getChildren().add(bottomBlock);
		}
		for(int j = 0; j<3; j++){
			Rectangle leftBlock = new Rectangle(0, j*150+100, BLOCK_WIDTH, BLOCK_HEIGHT);
			Rectangle rightBlock = new Rectangle(350, j*150+100, BLOCK_WIDTH, BLOCK_HEIGHT);
			leftBlock.setFill(new ImagePattern(blockSprite));
			rightBlock.setFill(new ImagePattern(blockSprite));
			blocks.add(leftBlock);
			blocks.add(rightBlock);
			root.getChildren().add(leftBlock);
			root.getChildren().add(rightBlock);
		}

		for(int k = 1; k<3; k++){
			for(int l = 1; l<3; l++){
				Rectangle centralBlock = new Rectangle(130*k-20,l*170, BLOCK_WIDTH, BLOCK_HEIGHT);
				centralBlock.setFill(new ImagePattern(blockSprite));
				blocks.add(centralBlock);
				root.getChildren().add(centralBlock);
			}
		}
		root.getChildren().add(mainCharacter.getSprite());
		mainCharacter.setX(50);
		mainCharacter.setY(460);
		boss = new BossSprite(100,50,1);
		root.getChildren().add(boss.getSprite());
		enemies.add(boss);
	}

	public void step (double elapsedTime) {
		if(Main.currStage.getScene() == gameScene){
			if(mainCharacter.getY() < 0){
				goNextLevel();
			}
			mainCharacter.checkBounds();

			for(int i = 0; i < projectiles.size(); i++) {
				Fireball projectile = projectiles.get(i);
				projectile.move();
				if(projectile.getX() > projectile.getInitialXPosition() + PROJECTILE_MOVE_DISTANCE || projectile.getX() < projectile.getInitialXPosition() - PROJECTILE_MOVE_DISTANCE || projectile.getX() > Main.WIDTH || projectile.getX() < 0) {
					root.getChildren().remove(projectile.getSprite());
					projectiles.remove(projectile);
				}
			}
			if(currLevel == 3) {
				bossAttack();
				for(int j = 0; j<bossMissiles.size(); j++) {
					BossMissile missile = bossMissiles.get(j);
					missile.move();
					if(missile.getBoundsInLocal().intersects(mainCharacter.getSprite().getBoundsInLocal())) {
						playerLoses();
					}
					if(missile.getCenterX() > Main.WIDTH || missile.getCenterX() < 0 || missile.getCenterY() > Main.HEIGHT || missile.getCenterY() < 0) {
						root.getChildren().remove(missile);
						bossMissiles.remove(missile);
					}
				}		
			}
			for(Sprite enemy:enemies) {
				enemy.move();
			}

			if(jumpingUp && mainCharacter.getY() > initialJumpingPosition-120) {
				mainCharacter.setYVelocity(-JUMP_SPEED);
			} else {
				jumpingUp = false;
				mainCharacter.setYVelocity(0);
			}

			mainCharacter.setX(mainCharacter.getX() + mainCharacter.getXVelocity() * mainCharacter.getDirection());

			checkPlayerCollisions();
			checkEnemyCollisions();
			if(!charOnBlock && !jumpingUp){
				mainCharacter.setYVelocity(GRAVITY);
			}
			mainCharacter.setY(mainCharacter.getY() + mainCharacter.getYVelocity());
		}
	}

	private void handleKeyPress (KeyCode code) {
		switch (code) {
		case W:
			if(charOnBlock && Main.currStage.getScene() == gameScene) {
				jumpingUp = true;
				initialJumpingPosition = mainCharacter.getY();
			}
			break;
		case A:
			if(Main.currStage.getScene() == gameScene) {
			mainCharacter.setDirection(-1);
			mainCharacter.setXVelocity(MOVE_SPEED);
			checkPlayerCollisions();
			}
			break;
		case D:
			if(Main.currStage.getScene() == gameScene) {
			mainCharacter.setDirection(1);
			mainCharacter.setXVelocity(MOVE_SPEED);
			checkPlayerCollisions();
			}
			break;
		case SPACE:
			if(projectiles.size() == 0 && Main.currStage.getScene() == gameScene) {
				Fireball projectile = new Fireball(mainCharacter.getX() + 10*mainCharacter.getDirection(), mainCharacter.getY(), mainCharacter.getDirection());
				projectiles.add(projectile);
				root.getChildren().add(projectile.getSprite());
			}
			break;
		case ENTER:
			if(Main.currStage.getScene() == splashScene) {
				startGame();
				Main.currStage.setScene(gameScene);
			}else if(Main.currStage.getScene() == finalScene) {
				reset();
				Main.currStage.setScene(splashScene);
			}
			break;
		case M:
			if(Main.currStage.getScene() == gameScene) {
				reset();
				Main.currStage.setScene(splashScene);
			}
			break;
		case N:
			if(currLevel < 3 && Main.currStage.getScene() == gameScene) {
				goNextLevel();
			}
			break;
		case B:
			if(Main.currStage.getScene() == gameScene) {
				generateBossLevel();
			}
			break;
		default:
			break;
		}
	}

	private void handleKeyRelease (KeyCode code) {
		switch (code) {
		case W:
			break;
		case A:
			if(mainCharacter.getDirection() == -1) {
				mainCharacter.setXVelocity(0);
			}
			break;
		case D:
			if(mainCharacter.getDirection() == 1) {
				mainCharacter.setXVelocity(0);
			}
			break;
		default:
			break;
		}
	}

	public void removeAllSprites() {
		root.getChildren().clear();
		enemies.clear();
		projectiles.clear();
		blocks.clear();
		bossMissiles.clear();
	}

	public void reset() {
		removeAllSprites();
		currLevel = 1;
	}

	public void bossAttack() {
		if(bossMissiles.isEmpty()){
			BossMissile missile = new BossMissile(boss.getX() + boss.getSprite().getFitWidth()/2, boss.getY() + boss.getSprite().getFitHeight()/2, 10, mainCharacter);
			missile.determineDirection();
			bossMissiles.add(missile);
			root.getChildren().add(missile);
		}
	}

	public String getTitle() {
		return TITLE;
	}
}