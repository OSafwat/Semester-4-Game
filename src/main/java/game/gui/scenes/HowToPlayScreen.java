package game.gui.scenes;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HowToPlayScreen extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Create the main layout for the How-to-Play scene
        VBox layout = new VBox(20);
        layout.setStyle("-fx-padding: 20; -fx-alignment: center;");

        // Create a title label
        Label titleLabel = new Label("How to Play");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        // Create instructions
        Label instruction1 = new Label("1. Use arrow keys to move your character.");
        Label instruction2 = new Label("2. Press spacebar to jump.");
        Label instruction3 = new Label("3. Avoid obstacles and collect coins.");
        Label instruction4 = new Label("4. Reach the finish line to complete the level.");
        Label instruction5 = new Label("5. Have fun and try to beat your high score!");

        // Style instructions
        instruction1.setStyle("-fx-font-size: 16px;");
        instruction2.setStyle("-fx-font-size: 16px;");
        instruction3.setStyle("-fx-font-size: 16px;");
        instruction4.setStyle("-fx-font-size: 16px;");
        instruction5.setStyle("-fx-font-size: 16px;");

        // Create a back button
        Button backButton = new Button("Back");
        backButton.setOnAction(e -> primaryStage.close()); // Close the how-to-play window

        // Add all components to the layout
        layout.getChildren().addAll(titleLabel, instruction1, instruction2, instruction3, instruction4, instruction5, backButton);

        // Create the scene with the layout
        Scene scene = new Scene(layout);

        // Set the stage properties
        primaryStage.setTitle("How to Play");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
