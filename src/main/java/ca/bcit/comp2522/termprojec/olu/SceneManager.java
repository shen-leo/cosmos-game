package ca.bcit.comp2522.termprojec.olu;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class SceneManager {
    private final Scene gameOverScene;
    private Scene nextLevelScene;
    private final Stage stage;
    private final LevelManager levelManager;
    private User user;
    public SceneManager(Stage stage, LevelManager levelManager, User user) {
        this.stage = stage;
        this.user = user;
        this.levelManager = levelManager;
        this.gameOverScene = createGameOverScene();
        this.nextLevelScene = createNextLevelScene(levelManager.getLevel());
    }

    public Scene createTitleScene() {
        StackPane root = new StackPane();
        root.setPrefSize(1200, 800);

        Scene scene = new Scene(root);
        Text text = new Text("Cosmos");
        text.setTranslateY(-50);
        text.setFont(Font.font(60));

        Button playButton = new Button("Play");
        playButton.setTranslateY(50);
        EventHandler<ActionEvent> event = e -> {
            try {
                levelManager.resetLevel(); // reset the game to level one
                stage.setScene(createGame());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        };
        playButton.setOnAction(event);

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
        root.setPrefSize(1200, 800);

        Scene scene = new Scene(root);

        SceneManager sceneManager = new SceneManager(stage, levelManager, user);
        UI ui = new UI(root, sceneManager);
        Player player = new Player(root, ui);
        Enemy enemy = new Enemy(player, root);
        ItemSpawner itemSpawner = new ItemSpawner(root, ui);
        InputHandler inputHandler = new InputHandler(scene, player, enemy, itemSpawner);


        ui.createBackGroundTile();
        enemy.displayEnemy();
        player.displayPlayer();
        itemSpawner.spawnItems(player);
        ui.createHeart();
        return scene;
    }
    private Scene createGameOverScene() {
        StackPane root = new StackPane();
        root.setPrefSize(1200, 800);

        Scene scene = new Scene(root);
        Text text = new Text("Game Over");
        text.setFont(Font.font(15));
        Button button = new Button("Play game");
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
        root.getChildren().addAll(text, button);
        return scene;
    }
    public void gameOver() {
        stage.setScene(gameOverScene);
    }

    private Scene createNextLevelScene(int level) {
        StackPane root = new StackPane();
        root.setPrefSize(1200, 800);

        Scene scene = new Scene(root);
        Text text = new Text("YOU WON!");
        text.setFont(Font.font(20));
        Button button = new Button("Next Level: " + level);
        button.setTranslateY(60);
        EventHandler<ActionEvent> event = e -> {
            try {
                stage.setScene(createGame());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        };
        button.setOnAction(event);
        root.getChildren().addAll(text, button);
        return scene;
    }

    public void nextLevel() {
        this.levelManager.nextLevel();
        this.nextLevelScene = createNextLevelScene(levelManager.getLevel());
        stage.setScene(this.nextLevelScene);
        System.out.println("Current Level: " + levelManager.getLevel());
    }
}
