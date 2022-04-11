package ca.bcit.comp2522.termprojec.olu;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static javafx.scene.media.MediaPlayer.INDEFINITE;


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
        Text text = new Text("Cosmos");
        text.setTranslateY(-50);
        text.setFont(Font.font(60));

        Button playButton = new Button("Play");
        resetButton(playButton);

        Button loginButton = new Button("Login");
        loginButton.setTranslateY(120);
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
        registerButton.setTranslateY(180);
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
            backgroundFile = new File("src/main/resources/images/Backgrounds/default-background.jpg");
        }

        Image img = new Image(backgroundFile.toURI().toString());
        BackgroundImage bImg = new BackgroundImage(img,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        Background bGround = new Background(bImg);
        root.setBackground(bGround);
        List<DamageTile> damageTiles = new ArrayList<>();
        SceneManager sceneManager = new SceneManager(stage, levelManager, mapManager, user);
        UI ui = new UI(root, sceneManager);
        Player player = new Player(root, ui);
        Enemy enemy = new Enemy(player, root, ui);
        ItemSpawner itemSpawner = new ItemSpawner(root, ui);

        List<Enemy> enemies = new ArrayList<>();
        enemies.add(enemy);


        if (levelManager.getLevel() >= 3) {
            System.out.println("Special");
            Enemy enemy2 = new Enemy(player, root, ui);
            ui.createSpecialBackGroundTile();
            for (int i = 0; i < 5; i++) {
                damageTiles.add(new DamageTile(player, root, ui));
            }
            enemy2.displayEnemy();
            enemies.add(enemy2);
            InputHandler inputHandler = new InputHandler(scene, player, enemies, itemSpawner, damageTiles);
        } else {
            System.out.println("Non Special");
            InputHandler inputHandler = new InputHandler(scene, player, enemies, itemSpawner);
            ui.createBackGroundTile();
        }
        enemy.displayEnemy();
        player.displayPlayer();
        itemSpawner.spawnItems(player);
        ui.createHeart();
        return scene;
    }
    private Scene createGameOverScene() {
        levelManager.setLevel(1);
        StackPane root = new StackPane();
        root.setPrefSize(1200, 800);

        Scene scene = new Scene(root);
        Text gameOver = new Text("Game Over");
        gameOver.setFont(Font.font(50));
        gameOver.setTranslateY(-175);

        Text coinsCollected = new Text("Coins Collected: " +
                HelloApplication.stats.getCoinsCollected());
        coinsCollected.setFont(Font.font(20));
        coinsCollected.setTranslateX(-200);
        coinsCollected.setTranslateY(-50);

        Text damageTaken = new Text("Damage taken: " +
                HelloApplication.stats.getNumberOfEnemiesPlayerHit());
        damageTaken.setFont(Font.font(20));
        damageTaken.setTranslateX(200);
        damageTaken.setTranslateY(-50);


        this.getUser().incrementDeaths();

        Button button = new Button("Play game");
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
        Text text = new Text("YOU WON!");
        text.setFont(Font.font(50));
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

    public User getUser() {
        return this.user;
    }
}
