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
import java.util.ArrayList;
import java.util.List;



public class SceneManager {
    private Scene nextLevelScene;
    private final Stage stage;
    private final LevelManager levelManager;
    private final MapManager mapManager;
    private User user;
    public SceneManager(Stage stage, LevelManager levelManager, MapManager mapManager, User user) {

        this.stage = stage;
        this.user = user;
        this.levelManager = levelManager;
        this.mapManager = mapManager;
        this.nextLevelScene = createNextLevelScene(levelManager.getLevel());
    }

    public Scene createTitleScene() {
        StackPane root = new StackPane();
        root.setPrefSize(1200, 850);

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
        text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 120));
//        text.setFont(Font.font(80));
        text.setTranslateY(-140);
//        text.setFont(Font.font(60));

        Button playButton = new Button("Play");
        resetButton(playButton);
        playButton.setTranslateY(50);
        playButton.setStyle("-fx-background-color: #73a9c2; -fx-font-size: 3em; -fx-text-fill: #FFFFFF");

        Button loginButton = new Button("Login");
        loginButton.setTranslateY(200);
        loginButton.setStyle("-fx-background-color: #73a9c2; -fx-font-size: 1.5em; -fx-text-fill: #FFFFFF");
        EventHandler<ActionEvent> loginEvent = e -> {
            try {
                this.user = LoginForm.loginUser();
                HelloApplication.setUser(user);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        };
        loginButton.setOnAction(loginEvent);

        Button registerButton = new Button("Register");
        registerButton.setTranslateY(280);
        registerButton.setStyle("-fx-background-color: #73a9c2; -fx-font-size: 1.5em; -fx-text-fill: #FFFFFF");
        EventHandler<ActionEvent> registerEvent = e -> {
            try {
                this.user = RegistrationForm.getRegisterUser();
                HelloApplication.setUser(user);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        };
        registerButton.setOnAction(registerEvent);

        root.getChildren().addAll(text, playButton, loginButton, registerButton);
        return scene;
    }

    public Scene createGame() throws Exception {
        StackPane root = new StackPane();
        root.setPrefSize(1200, 850);

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


        if (levelManager.getLevel() >= 3) {
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

    private Background createTransition(StackPane root) {

//        Scene scene = new Scene(root);
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
        root.setPrefSize(1200, 800);

        Scene scene = new Scene(root);
        root.setBackground(createTransition(root));
        Text gameOver = new Text("You Died...");
        gameOver.setFill(Color.WHITE);
        gameOver.setFont(Font.font(50));
        gameOver.setTranslateY(-175);

        Text coinsCollected = new Text("Total Souls Collected: "
                + HelloApplication.STATS.getCoinsCollected());
        coinsCollected.setFont(Font.font(20));
        coinsCollected.setFill(Color.WHITE);
        coinsCollected.setTranslateX(-200);
        coinsCollected.setTranslateY(-50);

        Text damageTaken = new Text("Total Damage taken: "
                + HelloApplication.STATS.getNumberOfEnemiesPlayerHit());
        damageTaken.setFont(Font.font(20));
        damageTaken.setFill(Color.WHITE);
        damageTaken.setTranslateX(200);
        damageTaken.setTranslateY(-50);


        this.getUser().incrementDeaths();

        Button button = new Button("Play Again");
        resetButton(button);
        root.getChildren().addAll(gameOver, button, coinsCollected, damageTaken);
        return scene;
    }

    private void resetButton(Button button) {
        button.setTranslateY(50);
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

    public void gameOver() {
        stage.setScene(createGameOverScene());
    }

    private Scene createNextLevelScene(int level) {
        StackPane root = new StackPane();
        root.setPrefSize(1200, 800);

        Scene scene = new Scene(root);
        root.setBackground(createTransition(root));
        Text text = new Text("LEVEL CLEARED!");
        text.setFont(Font.font(50));
        text.setFill(Color.WHITE);
        text.setTranslateY(-50);
        Button button = new Button("Next Level: " + level);
        button.setPrefHeight(50);
        button.setPrefWidth(150);
        button.setTranslateY(60);
        EventHandler<ActionEvent> event = e -> {
            try {
                stage.setScene(createGame());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        };
        button.setOnAction(event);

        Button saveButton = new Button("Save");
        saveButton.setTranslateY(180);
        EventHandler<ActionEvent> saveEvent = e -> {
            try {
                if (this.user.getUsername() != null) {
                    this.user.saveGame();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        };
        saveButton.setOnAction(saveEvent);

        root.getChildren().addAll(text, button, saveButton);
        return scene;
    }

    public void nextLevel() {
        this.levelManager.nextLevel();
        this.nextLevelScene = createNextLevelScene(levelManager.getLevel());
        stage.setScene(this.nextLevelScene);
        System.out.println("Current Level: " + levelManager.getLevel());
    }
//
//    public MapManager getMapManager() {
//        return this.mapManager;
//    }

    public User getUser() {
        return this.user;
    }
}
