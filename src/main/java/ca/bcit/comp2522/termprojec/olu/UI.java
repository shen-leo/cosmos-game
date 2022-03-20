package ca.bcit.comp2522.termprojec.olu;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;


import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class UI {
    private final StackPane stackPane;
    public UI(StackPane stackPane) {
        this.stackPane = stackPane;
    }


    public void createBackGroundTile()throws IOException {
        InputStream is = Files.newInputStream(Paths.get("src/main/resources/images/background.png"));
        Image img = new Image(is);
        ImageView imageView = new ImageView(img);
        imageView.setFitWidth(576);
        imageView.setFitHeight(576);
        stackPane.getChildren().addAll(imageView);

        is.close();
    }
}
