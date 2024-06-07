package game.gui.scenes;
import game.engine.Player;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.PopupWindow.AnchorLocation;

import java.util.Arrays;

public class EndScene {
    private Scene scene;
    private int winnerPlayerScore;
    private String winnerPlayerName;
    private Label exitButton;
    Player p1, p2;

    public EndScene(Player p1, Player p2) {
        setPlayers(p1, p2);
        AnchorPane root = new AnchorPane();
        root.getStylesheets().add(getClass().getResource("/MainMenu.css").toExternalForm());
        
        ImageView backgroundImageView =  new ImageView(new Image(getClass().getResourceAsStream("/images/EndBg.jpg")));
        backgroundImageView.setFitWidth(1920);
        backgroundImageView.setFitHeight(1080);
        root.getChildren().add(backgroundImageView);


        setWinnerPlayerStats();

        Label label = new Label(winnerPlayerName + " Wins! Score: " + winnerPlayerScore);
        label.setStyle("-fx-alignment: center;");
        // label.setTextFill(javafx.scene.paint.Color.WHITE);
        // label.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        label.getStyleClass().add("endText");
        label.setLayoutX(400);
        label.setLayoutY(200);
        root.getChildren().add(label);


        exitButton = new Label("Exit");
        exitButton.getStyleClass().add("endText");

        exitButton.setLayoutX(400);
        exitButton.setLayoutY(500);        
        root.getChildren().add(exitButton);


        scene = new Scene(root, 1920, 1080);
    }

    public void setWinnerPlayerStats() {
        int s1 = 0, s2 = 0;
        int player1Min = Integer.MAX_VALUE, player2Min = Integer.MAX_VALUE;
        int[] player1Scores = p1.getScoreSheet().getScores(), player2Scores  = p2.getScoreSheet().getScores();


        for (int i = 0; i < player1Scores.length; i++) {
            s1 += player1Scores[i];
            s2 += player2Scores[i];

            player1Min = Math.min(player1Min, player1Scores[i]);
            player2Min = Math.min(player2Min, player2Scores[i]);
        }
        
        s1 += player1Min * p1.getScoreSheet().getElementalCrests();
        s2 += player2Min * p2.getScoreSheet().getElementalCrests();


        System.out.println(Arrays.toString(player1Scores));
        System.out.println(Arrays.toString(player2Scores));


        if (s1 > s2) {
            winnerPlayerName = p1.getName();
            winnerPlayerScore = s1;
        } else {
            winnerPlayerName = p2.getName();
            winnerPlayerScore = s2;
        }
    }

    public void setPlayers(Player p1, Player p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    public Scene getExitScene() {
        return scene;
    }

    public Label getExitButton() {
        return exitButton;
    }
}
