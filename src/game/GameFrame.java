package game;

import java.util.ArrayList;
import java.util.Random;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 * The code that dictates all of the game's mechanics 
 * 
 * @author Soravit Sophastienphong, credit to Robert C. Duvall for template code
 */
public class GameFrame {
    public static final String TITLE = "Mamba's Revenge";
    public static final double MOVE_SPEED = 5;
    public static final double JUMP_SPEED = 5;
    public static final double GRAVITY = 2.5;
    public static final double BLOCK_WIDTH = 50;
    public static final double BLOCK_HEIGHT = 50;
    public static final double PROJECTILE_MOVE_DISTANCE = 150;

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
    private Image explosionSprite;
    private ImagePattern stonePattern;

    private int currLevel = 1;

    private double initialJumpingPosition = 0;
    private boolean jumpingUp = false;
    private boolean charOnBlock = false;
    private boolean invulnerabilityMode = false;

    /**
     * Returns the title of the game
     */
    public String getTitle() {
        return TITLE;
    }

    private void generateBlock(int xpos, int ypos){
        Rectangle newBlock = new Rectangle(xpos, ypos, BLOCK_WIDTH, BLOCK_HEIGHT);
        newBlock.setFill(stonePattern);
        blocks.add(newBlock);
        addToGameScene(newBlock);
    }

    //Sets up platform for levels 1 and 2
    private void generateCombatLevel () {
        blocks.clear();
        for(int i = 0; i<7; i++) {
            for(int j = 0; j<3; j++) {
                generateBlock(i*50, j*200);
            }
        }
        for(int k = 1; k<8; k++) {
            for(int l = 0; l<4; l++) {
                generateBlock(k*50, l*200-100);
            }
        }
        generateBlock(0,500);
    }

    private void generateBossLevel () {
        currLevel = 3;
        removeAllSprites();
        for(int i = 0; i < 8; i++){
            generateBlock(i * 50, 500);
        }
        for(int j = 0; j < 3; j++){
            generateBlock(0, j * 150+100);
            generateBlock(350, j * 150+100);
        }
        for(int k = 1; k < 3; k++){
            for(int l = 1; l < 3; l++){
                generateBlock(130 * k-20, l * 170);
            }
        }
    }

    private void spawnWoodySprites(){
        for(int i  = 0; i < 5; i++) {
            for(int j = 0; j < 2; j++) {
                Random rand = new Random();
                int xPos = rand.nextInt(300);
                WoodySprite woodySprite = new WoodySprite(xPos + 50, 100*i + 70, 1);
                if(xPos > Main.WIDTH/2) {
                    woodySprite.setDirection(1);
                } else {
                    woodySprite.setDirection(-1);
                }
                enemies.add(woodySprite);
                addToGameScene(woodySprite.getSprite());
            }
        }
    }

    private void spawnDragons(){
        for(int k  = 0; k < 5; k++) {
            Random rand = new Random();
            int xPos = rand.nextInt(300);
            DragonSprite dragonSprite = new DragonSprite(xPos + 100, 100*k + 50, 1);
            if(xPos > Main.WIDTH/2){
                dragonSprite.setDirection(1);
            }else{
                dragonSprite.setDirection(-1);
            }
            enemies.add(dragonSprite);
            addToGameScene(dragonSprite.getSprite());
        }	
    }

    //Checks if player collides with blocks 
    private void checkPlayerCollisions () {
        charOnBlock = false;		
        for(int i = 0; i < blocks.size(); i++){
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
    }

    private void checkPlayerDeath(){
        for(int j = 0; j < enemies.size(); j++) {
            Sprite enemy = enemies.get(j);
            if(mainCharacter.getSprite().getBoundsInLocal().intersects(enemy.getSprite().getBoundsInLocal()) && !invulnerabilityMode) {
                playerLoses();
            }
        }
        if(currLevel == 3){
            if(mainCharacter.getSprite().getBoundsInLocal().intersects(bossMissiles.get(0).getBoundsInLocal()) && !invulnerabilityMode){
                playerLoses();
            }
        }
    }

    //Checks if projectile hits enemies
    private void checkProjectileCollisions () {
        for(int i = 0; i < enemies.size(); i++) {
            Sprite enemy = enemies.get(i);
            for(int j = 0; j < projectiles.size(); j++) {
                Sprite projectile = projectiles.get(j);
                if(enemy.getSprite().getBoundsInLocal().intersects(projectile.getSprite().getBoundsInLocal())) {
                    createExplosionEffect(enemy.getX() + enemy.getWidth()/2 - 25, enemy.getY() + enemy.getHeight()/2 - 25);
                    if(enemy.getHealth() > 1) {
                        enemy.setHealth(enemy.getHealth()-1);
                    } else { 
                        if(enemy instanceof BossSprite) {
                            playerWins();
                        }
                        removeFromGameScene(enemy.getSprite());
                        enemies.remove(enemy);
                    }
                    removeFromGameScene(projectile.getSprite());
                    projectiles.remove(projectile);
                }
            }
        }
    }

    private void fireProjectile(){
        if(projectiles.size() == 0) {
            Fireball projectile = new Fireball(mainCharacter.getX() + 10*mainCharacter.getDirection(), mainCharacter.getY(), mainCharacter.getDirection());
            projectiles.add(projectile);
            addToGameScene(projectile.getSprite());
        }
    }

    //Check if projectile moves out of specified range
    private void checkProjectileDistance(){
        for(int i = 0; i < projectiles.size(); i++) {
            Fireball projectile = projectiles.get(i);
            projectile.move();
            if(projectile.getX() > projectile.getInitialXPosition() + PROJECTILE_MOVE_DISTANCE || projectile.getX() < projectile.getInitialXPosition() - PROJECTILE_MOVE_DISTANCE || projectile.getX() > Main.WIDTH || projectile.getX() < 0) {
                removeFromGameScene(projectile.getSprite());
                projectiles.remove(projectile);
            }
        }
        for(int j = 0; j<bossMissiles.size(); j++) {
            BossMissile missile = bossMissiles.get(j);
            missile.move();
            if(missile.getCenterX() > Main.WIDTH || missile.getCenterX() < 0 || missile.getCenterY() > Main.HEIGHT || missile.getCenterY() < 0) {
                removeFromGameScene(missile);
                bossMissiles.remove(missile);
            }
        }	
    }

    private void bossShootMissile() {
        if(bossMissiles.isEmpty()){
            BossMissile missile = new BossMissile(boss.getX() + boss.getSprite().getFitWidth()/2, boss.getY() + boss.getSprite().getFitHeight()/2, 10, mainCharacter);
            missile.determineDirection();
            bossMissiles.add(missile);
            addToGameScene(missile);
        }
    }

    private void createExplosionEffect(double xpos, double ypos){
        explosionSprite = new Image(getClass().getClassLoader().getResourceAsStream("fire_effect.png"));
        ImageView explosion = new ImageView(explosionSprite);
        explosion.setX(xpos);
        explosion.setY(ypos);
        explosion.setFitHeight(50);
        explosion.setFitWidth(50);
        addToGameScene(explosion);
        KeyFrame frame = new KeyFrame(Duration.millis(Main.MILLISECOND_DELAY * 10),
                                      e -> removeFromGameScene(explosion));
        Timeline animation = new Timeline();
        animation.getKeyFrames().add(frame);
        animation.play();
    }

    private void playerWins () {
        messages.getChildren().clear();
        Text text = new Text("You've won! Well done!" + System.lineSeparator() + "Press enter to play again.");
        text.setFont(new Font("Arial Black",15));
        text.setX(20);
        text.setY(200);
        messages.getChildren().add(text);
        Main.getCurrStage().setScene(finalScene);
    }

    private void playerLoses () {
        messages.getChildren().clear();
        Text text = new Text("Game Over. Mamba will never be avenged." + System.lineSeparator() + "Press enter for another chance.");
        text.setFont(new Font("Arial Black",15));
        text.setX(20);
        text.setY(200);
        messages.getChildren().add(text);
        Main.getCurrStage().setScene(finalScene);
    }

    private void spawnBoss(){
        addToGameScene(mainCharacter.getSprite());
        mainCharacter.setX(0);
        mainCharacter.setY(460);
        boss = new BossSprite(100,50,1);
        addToGameScene(boss.getSprite());
        enemies.add(boss);
    }

    private void levelUp () {	
        currLevel++;
        if(currLevel == 2) {
            removeAllSprites();
            generateCombatLevel();
            addToGameScene(mainCharacter.getSprite());
            mainCharacter.setX(0);
            mainCharacter.setY(460);
            spawnDragons();
        } else if(currLevel == 3) {
            generateBossLevel();
            spawnBoss();
        }
    }

    private void jumpIfKeyPressed(){
        if(jumpingUp && mainCharacter.getY() > initialJumpingPosition - 120) {
            mainCharacter.setYVelocity(-JUMP_SPEED);
        } else {
            jumpingUp = false;
            mainCharacter.setYVelocity(0);
        }
    }

    //Instantiates objects/data structures
    private void initialize () {	
        root = new Group();
        messages = new Group();
        bossMissiles = new ArrayList<BossMissile>();
        blocks = new ArrayList<Rectangle>();
        enemies = new ArrayList<Sprite>();
        projectiles = new ArrayList<Fireball>();
        gameScene = new Scene(root, Main.WIDTH, Main.HEIGHT, Color.WHITE);
        finalScene = new Scene(messages, Main.WIDTH, Main.HEIGHT, Color.WHITE);
        blockSprite = new Image(getClass().getClassLoader().getResourceAsStream("block.png"));
        stonePattern = new ImagePattern(blockSprite);
        gameScene.setOnKeyPressed(e -> handleInGameKeyPress(e.getCode()));
        gameScene.setOnKeyReleased(e-> handleKeyRelease(e.getCode()));
        finalScene.setOnKeyPressed(e -> handleMenuKeyPress(e.getCode()));
    }

    //Begins level 1
    private void startGame () {
        generateCombatLevel();
        mainCharacter = new Sprite(0, 460, 1);
        addToGameScene(mainCharacter.getSprite());
        spawnWoodySprites();
    }

    private void addToGameScene(Node node){
        root.getChildren().add(node);
    }

    private void removeFromGameScene(Object object){
        root.getChildren().remove(object);
    }

    private void removeAllSprites() {
        root.getChildren().clear();
        enemies.clear();
        projectiles.clear();
        blocks.clear();
        bossMissiles.clear();
    }

    private void reset() {
        invulnerabilityMode = false;
        removeAllSprites();
        currLevel = 1;
    }

    /**
     * Sets up the splash screen at the beginning of the game
     * showing player controls
     * @param width The width of the splash screen
     * @param height The height of the splash screen
     * @return An instance of the splash screen
     */
    public Scene initializeSplashScreen (int width, int height) {
        Group menuItems = new Group();
        splashScene = new Scene(menuItems,width,height,Color.WHITE);
        ImageView controls = new ImageView(new Image(getClass().getClassLoader().getResourceAsStream("controls.png")));
        controls.setFitHeight(500);
        controls.setFitWidth(300);
        controls.setX(50);
        controls.setY(25);
        menuItems.getChildren().add(controls);
        splashScene.setOnKeyPressed(e -> handleMenuKeyPress(e.getCode()));
        initialize();
        return splashScene;
    }

    /**
     * Runs continuously throughout the program and checks
     * levels, keys, movement, and collisions
     */
    public void step () {
        if(Main.getCurrStage().getScene() == gameScene){
            if(mainCharacter.getY() < 0){
                levelUp();
            }
            mainCharacter.checkBounds();
            checkProjectileDistance();
            for(Sprite enemy:enemies) {
                enemy.move();
            }
            if(currLevel == 3) {
                bossShootMissile();	
            }
            jumpIfKeyPressed();
            checkPlayerDeath();
            checkPlayerCollisions();
            checkProjectileCollisions();
            if(!charOnBlock && !jumpingUp){
                mainCharacter.setYVelocity(GRAVITY);
            }
            mainCharacter.move();
        }
    }

    private void handleMenuKeyPress (KeyCode code) {
        if(code == KeyCode.ENTER){
            if(Main.getCurrStage().getScene() == splashScene) {
                startGame();
                Main.getCurrStage().setScene(gameScene);
            } else {
                reset();
                Main.getCurrStage().setScene(splashScene);
            }
        }
    }

    private void handleInGameKeyPress (KeyCode code) {
        switch (code) {
            case W:
                if(charOnBlock){
                    jumpingUp = true;
                    initialJumpingPosition = mainCharacter.getY();
                }
                break;
            case A:
                mainCharacter.setDirection(-1);
                mainCharacter.setXVelocity(MOVE_SPEED);
                checkPlayerCollisions();
                break;
            case D:
                mainCharacter.setDirection(1);
                mainCharacter.setXVelocity(MOVE_SPEED);
                checkPlayerCollisions();
                break;
            case SPACE:
                fireProjectile();
                break;
            case M:
                reset();
                Main.getCurrStage().setScene(splashScene);
                break;
            case N:
                if(currLevel < 3) {
                    levelUp();
                }
                break;
            case B:
                generateBossLevel();
                spawnBoss();
                break;
            case I:
                invulnerabilityMode = true;
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
}