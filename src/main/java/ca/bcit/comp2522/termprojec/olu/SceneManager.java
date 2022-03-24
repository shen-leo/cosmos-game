package ca.bcit.comp2522.termprojec.olu;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;

public class SceneManager {
    private final Scene gameOverScene;
    private final Stage stage;
    public SceneManager(Stage stage) throws IOException {
        this.stage = stage;
        this.gameOverScene = createGameOverScene();
    }
    public Scene createGame() throws IOException {
        StackPane root = new StackPane();
        root.setPrefSize(1200, 800);

        Scene scene = new Scene(root);
//        Item items = new Item(root);

        SceneManager sceneManager = new SceneManager(stage);
        UI ui = new UI(root, sceneManager);
        Player player = new Player(root, ui);
        InputHandler inputHandler = new InputHandler(scene, player);


        ui.createBackGroundTile();
        HashMap<String, Integer> currentCoord = player.getCoordinates();
        ItemSpawner itemSpawn = new ItemSpawner(root, currentCoord);
        itemSpawn.spawnItems();
//        items.createItem(currentCoord);
        player.displayPlayer();
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
            } catch (IOException ex) {
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
    public void playGame() {

    }
}
