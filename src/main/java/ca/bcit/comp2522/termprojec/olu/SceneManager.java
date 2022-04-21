package ca.bcit.comp2522.termprojec.olu;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 * Manages the scenes and transition screens of the game.
 * @author Urjit, Leo
 * @version 2022
 */
public class SceneManager {

    private static final int ROOT_SIZE_V = 1200;
    private static final int ROOT_SIZE_V1 = 850;
    private static final int TITLE_FONT_SIZE = 120;
    private static final int TITLE_TEXT_Y_TRANSLATE = -140;
    private static final int PLAY_BUTTON_Y_TRANSLATE = 50;
    private static final int LOGIN_BUTTON_Y_TRANSLATE = 200;
    private static final int REGISTER_BUTTON_Y_TRANSLATE = 280;
    private static final int LEVEL_THREE = 3;
    private static final int GAME_OVER_FONT_SIZE = 50;
    private static final int GAME_OVER_Y_TRANSLATE = -175;
    private static final int GAME_OVER_STATS_FONT_SIZE = 20;
    private static final int GAME_OVER_STATS_Y_TRANSLATE = -50;
    private static final int GAME_OVER_STATS_X_TRANSLATE = 200;
    private static final int PLAY_AGAIN_BUTTON_HEIGHT = 50;
    private static final int PLAY_AGAIN_BUTTON_WIDTH = 150;
    private static final int RESET_BUTTON_Y_TRANSLATE = 50;
    private static final int LEVEL_CLEAR_TEXT_FONT_SIZE = 50;
    private static final int LEVEL_CLEAR_TEXT_Y_TRANSLATE = -50;
    private static final int NEXT_LEVEL_BUTTON_HEIGHT = 50;
    private static final int NEXT_LEVEL_BUTTON_WIDTH = 150;
    private static final int NEXT_LEVEL_BUTTON_Y_TRANSLATE = 60;
    private static final int SAVE_BUTTON_Y_TRANSLATE = 180;

    private Scene nextLevelScene;
    private final Stage stage;
    private final LevelManager levelManager;
    private final MapManager mapManager;
    private User user;

    /**
     * Constructor for the SceneManager class.
     * @param stage the current JavaFX stage
     * @param levelManager levelManager object that tracks the current level
     * @param mapManager mapManager object that holds the level-specific assets for each map
     * @param user an object representing the current user
     */
    public SceneManager(final Stage stage, final LevelManager levelManager, final MapManager mapManager,
                        final User user) {
        this.stage = stage;
        this.user = user;
        this.levelManager = levelManager;
        this.mapManager = mapManager;
        this.nextLevelScene = createNextLevelScene(levelManager.getLevel());
    }

    /**
     * Creates the title screen for the game.
     * @return a Scene object representing the game's title screen
     */
    public Scene createTitleScene() {

        StackPane root = new StackPane();
        root.setPrefSize(ROOT_SIZE_V, ROOT_SIZE_V1);

        Scene scene = new Scene(root);

        File backgroundFile = new File("src/main/resources/images/Backgrounds/wallpapers/galaxy.jpg");

        Image img = new Image(backgroundFile.toURI().toString());
        BackgroundImage bImg = new BackgroundImage(img,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        Background bGround = new Background(bImg);
        root.setBackground(bGround);

        Text text = new Text("COSMOS");
        text.setFill(Color.WHITE);
        text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, TITLE_FONT_SIZE));
        text.setTranslateY(TITLE_TEXT_Y_TRANSLATE);

        Button playButton = new Button("Play");
        resetButton(playButton);
        playButton.setTranslateY(PLAY_BUTTON_Y_TRANSLATE);
        playButton.setStyle("-fx-background-color: #73a9c2; -fx-font-size: 3em; -fx-text-fill: #FFFFFF");

        Button loginButton = new Button("Login");
        loginButton.setTranslateY(LOGIN_BUTTON_Y_TRANSLATE);
        loginButton.setStyle("-fx-background-color: #73a9c2; -fx-font-size: 1.5em; -fx-text-fill: #FFFFFF");
        EventHandler<ActionEvent> loginEvent = e -> {
            try {
                this.user = LoginForm.loginUser();
                HelloApplication.setUser(user);
            } catch (NullPointerException ex) {
                ex.printStackTrace();
            }
        };
        loginButton.setOnAction(loginEvent);

        Button registerButton = new Button("Register");
        registerButton.setTranslateY(REGISTER_BUTTON_Y_TRANSLATE);
        registerButton.setStyle("-fx-background-color: #73a9c2; -fx-font-size: 1.5em; -fx-text-fill: #FFFFFF");
        EventHandler<ActionEvent> registerEvent = e -> {
            try {
                this.user = RegistrationForm.getRegisterUser();
                HelloApplication.setUser(user);
            } catch (NullPointerException ex) {
                ex.printStackTrace();
            }
        };
        registerButton.setOnAction(registerEvent);

        root.getChildren().addAll(text, playButton, loginButton, registerButton);
        return scene;
    }

    /**
     * Creates a normal level for the game.
     * @return a Scene object representing a normal level
     * @throws Exception an error where the createGame scene fails to be created
     */
    public Scene createGame() throws Exception {
        StackPane root = new StackPane();
        root.setPrefSize(ROOT_SIZE_V, ROOT_SIZE_V1);

        Scene scene = new Scene(root);
        File backgroundFile;
        if (mapManager.getMap(levelManager.getLevel()) != null) {
            backgroundFile = mapManager.getMap(levelManager.getLevel());
        } else {
            backgroundFile = new File("src/main/resources/images/Backgrounds/wallpapers/galaxy.jpg");
        }

        Image img = new Image(backgroundFile.toURI().toString());
        BackgroundImage bImg = new BackgroundImage(img,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        Background bGround = new Background(bImg);
        root.setBackground(bGround);
        SceneManager sceneManager = new SceneManager(stage, levelManager, mapManager, user);
        UI ui = new UI(root, sceneManager);
        Player player = new Player(root, ui);
        Enemy enemy = new Enemy(player, root, ui);
        ItemSpawner itemSpawner = new ItemSpawner(root, ui);

        List<Enemy> enemies = new ArrayList<>();
        enemies.add(enemy);

        if (levelManager.getLevel() >= LEVEL_THREE) {
            ui.createSpecialBackGroundTile();
            InputHandler inputHandler = new InputHandler(scene, player, enemies, itemSpawner, true);
        } else {
            InputHandler inputHandler = new InputHandler(scene, player, enemies, itemSpawner);
            ui.createBackGroundTile();
        }
        enemy.displayEnemy();
        player.displayPlayer();
        itemSpawner.spawnItems(player);
        ui.createHeart();
        return scene;
    }

    private Background createTransition(final StackPane root) {

        File backgroundFile = new File("src/main/resources/images/Backgrounds/wallpapers/game_over.jpeg");
        Image img = new Image(backgroundFile.toURI().toString());
        BackgroundImage bImg = new BackgroundImage(img,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        return new Background(bImg);
    }

    private Scene createGameOverScene() {

        levelManager.setLevel(1);
        StackPane root = new StackPane();
        root.setPrefSize(ROOT_SIZE_V, ROOT_SIZE_V1);

        Scene scene = new Scene(root);
        root.setBackground(createTransition(root));
        Text gameOver = new Text("You Died...");
        gameOver.setFill(Color.WHITE);
        gameOver.setFont(Font.font(GAME_OVER_FONT_SIZE));
        gameOver.setTranslateY(GAME_OVER_Y_TRANSLATE);

        Text coinsCollected = new Text("Total Souls Collected: "
                + HelloApplication.STATS.getCoinsCollected());
        coinsCollected.setFont(Font.font(GAME_OVER_STATS_FONT_SIZE));
        coinsCollected.setFill(Color.WHITE);
        coinsCollected.setTranslateX(-GAME_OVER_STATS_X_TRANSLATE);
        coinsCollected.setTranslateY(GAME_OVER_STATS_Y_TRANSLATE);

        Text damageTaken = new Text("Total Damage taken: "
                + HelloApplication.STATS.getNumberOfEnemiesPlayerHit());
        damageTaken.setFont(Font.font(GAME_OVER_STATS_FONT_SIZE));
        damageTaken.setFill(Color.WHITE);
        damageTaken.setTranslateX(GAME_OVER_STATS_X_TRANSLATE);
        damageTaken.setTranslateY(GAME_OVER_STATS_Y_TRANSLATE);

        this.getUser().incrementDeaths();

        Button button = new Button("Play Again");
        resetButton(button);

        button.setPrefHeight(PLAY_AGAIN_BUTTON_HEIGHT);
        button.setPrefWidth(PLAY_AGAIN_BUTTON_WIDTH);
        root.getChildren().addAll(gameOver, button, coinsCollected, damageTaken);
        return scene;
    }

    private void resetButton(final Button button) {

        button.setTranslateY(RESET_BUTTON_Y_TRANSLATE);
        EventHandler<ActionEvent> event = e -> {
            try {
                levelManager.resetLevel(); // reset the game to level one
                stage.setScene(createGame());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        };
        button.setOnAction(event);
    }

    /**
     * Method called to indicate that the player has lost the game; calls the createGameOverScene method.
     */
    public void gameOver() {
        stage.setScene(createGameOverScene());
    }

    private Scene createNextLevelScene(final int level) {

        StackPane root = new StackPane();
        root.setPrefSize(ROOT_SIZE_V, ROOT_SIZE_V1);

        Scene scene = new Scene(root);
        root.setBackground(createTransition(root));
        Text text = new Text("LEVEL CLEARED!");
        text.setFont(Font.font(LEVEL_CLEAR_TEXT_FONT_SIZE));
        text.setFill(Color.WHITE);
        text.setTranslateY(LEVEL_CLEAR_TEXT_Y_TRANSLATE);
        Button button = new Button("Next Level: " + level);
        button.setPrefHeight(NEXT_LEVEL_BUTTON_HEIGHT);
        button.setPrefWidth(NEXT_LEVEL_BUTTON_WIDTH);
        button.setTranslateY(NEXT_LEVEL_BUTTON_Y_TRANSLATE);
        EventHandler<ActionEvent> event = e -> {
            try {
                stage.setScene(createGame());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        };
        button.setOnAction(event);

        Button saveButton = new Button("Save");
        saveButton.setTranslateY(SAVE_BUTTON_Y_TRANSLATE);
        EventHandler<ActionEvent> saveEvent = e -> {
            try {
                if (this.user.getUsername() != null) {
                    this.user.saveGame();
                }
            } catch (SQLException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        };
        saveButton.setOnAction(saveEvent);

        root.getChildren().addAll(text, button, saveButton);
        return scene;
    }

    /**
     * Method called to indicate that the player has passed the current level; calls the createNextLevelScene method.
     */
    public void nextLevel() {
        this.levelManager.nextLevel();
        this.nextLevelScene = createNextLevelScene(levelManager.getLevel());
        stage.setScene(this.nextLevelScene);
        System.out.println("Current Level: " + levelManager.getLevel());
    }

    /**
     * Getter to retrieve the current user.
     * @return a User object representing the current player
     */
    public User getUser() {
        return this.user;
    }
}
