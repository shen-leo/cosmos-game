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
    public SceneManager(Stage stage) {
        this.stage = stage;
        this.gameOverScene = createGameOverScene();
        this.nextLevelScene = createNextLevelScene();
    }
    public Scene createGame() throws Exception {
        StackPane root = new StackPane();
        root.setPrefSize(1200, 800);

        Scene scene = new Scene(root);

        SceneManager sceneManager = new SceneManager(stage);
        UI ui = new UI(root, sceneManager);
        Player player = new Player(root, ui);
        ItemSpawner itemSpawner = new ItemSpawner(root, ui);
        InputHandler inputHandler = new InputHandler(scene, player, itemSpawner);


        ui.createBackGroundTile();
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

    private Scene createNextLevelScene() {
        StackPane root = new StackPane();
        root.setPrefSize(1200, 800);

        Scene scene = new Scene(root);
        Text text = new Text("YOU WON!");
        text.setFont(Font.font(20));
        Button button = new Button("Next Level");
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
        this.nextLevelScene = createNextLevelScene();
        stage.setScene(this.nextLevelScene);
    }
}
