package ca.bcit.comp2522.termprojec.olu;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        StackPane root = new StackPane();
        root.setPrefSize(1200, 800);

        Scene scene = new Scene(root);
        Item items = new Item(root);
        Player player = new Player(root);
        InputHandler inputHandler = new InputHandler(scene, player);
        UI ui = new UI(root);


        ui.createBackGroundTile();
        HashMap<String, Integer> currentCoord = player.getCoordinates();
        items.createItem(currentCoord);
        player.displayPlayer();

        stage.setTitle("Cosmos");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}