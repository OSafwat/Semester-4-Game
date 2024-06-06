package game.gui.scenes;

import game.engine.GUIGameController;
import game.engine.Player;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;

public class EndScene extends RealmScene {
    private AnchorPane root;
    public void createEndScene() {
        root =new AnchorPane();
        ImageView imageView =  new ImageView(new Image(getClass().getResourceAsStream("/images/EndBg.jpg")));
        imageView.setFitWidth(607);
        imageView.setFitHeight(428);
        imageView.setLayoutX(-1);
        root.getChildren().add(imageView);

        Button exitButton = new Button("Exit");
        exitButton.setLayoutX(239);
        exitButton.setLayoutY(347);
        exitButton.setPrefSize(152, 69);
        exitButton.setStyle("-fx-border-radius: 100px; -fx-background-color: #18C25E; -fx-background-radius: 20px;");
        exitButton.setTextFill(javafx.scene.paint.Color.WHITE);
        exitButton.setFont(Font.font("Cooper Black", 33));
        root.getChildren().add(exitButton);

        Label winnerLabel = new Label();
       // String winner = String winnerPlayer( p1, p2);
        winnerLabel.setText(winner);
        winnerLabel.setLayoutX(192);
        winnerLabel.setLayoutY(194);
        winnerLabel.setPrefSize(221, 41);
        winnerLabel.setStyle("-fx-alignment: center;");
        winnerLabel.setTextFill(javafx.scene.paint.Color.WHITE);
        winnerLabel.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        winnerLabel.setFont(Font.font("Cooper Black", 36));
        root.getChildren().add(winnerLabel);

        Label winsLabel = new Label("Wins!");
        winsLabel.setLayoutX(227);
        winsLabel.setLayoutY(244);
        winsLabel.setPrefSize(152, 41);
        winsLabel.setStyle("-fx-alignment: center;");
        winsLabel.setTextFill(javafx.scene.paint.Color.WHITE);
        winsLabel.setFont(Font.font("Cooper Black", 36));
        root.getChildren().add(winsLabel);

        Label scoreLabel = new Label("Score:");


        scoreLabel.setLayoutX(192);
        scoreLabel.setLayoutY(292);
        scoreLabel.setPrefSize(126, 41);
        scoreLabel.setTextFill(javafx.scene.paint.Color.WHITE);
        scoreLabel.setFont(Font.font("Cooper Black", 36));
        root.getChildren().add(scoreLabel);

        Label scoreValueLabel = new Label();
        int score = winnerPlayerScore(Player p1, Player p2);
        String scoreStr =""+score;
        scoreValueLabel.setText(scoreStr);
        scoreValueLabel.setLayoutX(332);
        scoreValueLabel.setLayoutY(292);
        scoreValueLabel.setPrefSize(95, 41);
        scoreValueLabel.setTextFill(javafx.scene.paint.Color.WHITE);
        scoreValueLabel.setFont(Font.font("Cooper Black", 36));
        root.getChildren().add(scoreValueLabel);
    }

    public static  String winnerPlayer(Player p1, Player p2){
        //int s1=p1.getscoresheet().getgamescore().gettotalscore();
        //int s2=p2.getscoresheet().getgamescore().gettotalscore();
        if (s1>s2)
            return p1.getName();
        return p2.getName();


    }
    public static  int winnerPlayerScore(Player p1, Player p2){
        //int s1=p1.getscoresheet().getgamescore().gettotalscore();
        //int s2=p2.getscoresheet().getgamescore().gettotalscore();
        if (s1>s2)
            return s1;
        return s2;


    }



}
