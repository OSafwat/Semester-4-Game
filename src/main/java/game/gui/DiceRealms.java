package game.gui;
import game.creatures.Creature;
import game.dice.BlueDice;
import game.dice.Dice;
import game.dice.GreenDice;
import game.dice.MagentaDice;
import game.dice.RedDice;
import game.dice.YellowDice;
import game.engine.GUIGameController;
import game.engine.Move;
import game.engine.Player;
import game.engine.enums.RealmColor;
import game.exceptions.BonusException;
import game.exceptions.NoAvailableMovesException;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Random;

public class DiceRealms extends Application {
    GUIGameController guiGameController;
    SceneController sceneController;
    Stage primaryStage;
    boolean isForgotten;
    @Override
    public void start(Stage primaryStage) {
        guiGameController = new GUIGameController();
        this.primaryStage = primaryStage;
        primaryStage.setY(0);
        primaryStage.setX(0);
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/images/wizard hat.png")));

        sceneController = new SceneController();
        setupGame();
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public void setupGame() {
        primaryStage.setTitle("Dice Realms Game");
        primaryStage.setScene(sceneController.mainMenuScene.createMainScene());
        sceneController.boardScene.makeboardScene(getDicePNGs(guiGameController.getAvailableDice()));
        sceneController.redScene.createScene();
        sceneController.greenScene.createScene();
        sceneController.blueScene.createScene();
        sceneController.magentaScene.createScene();
        sceneController.yellowScene.createScene();
        initEventListeners();
        primaryStage.show();
        isForgotten = false;
    }

    public String[] getDicePNGs(Dice[] dice) {
        String[] dicePNGs = new String[dice.length];
        for (int i = 0; i < dice.length; i++) {
            dicePNGs[i] = getColorAsString(dice[i]) + " dice";
        }
        for (int i = 0; i < dice.length; i++) {
            dicePNGs[i] += " " + dice[i].getValue() + ".png";
        }
        return dicePNGs;
    }

    public void openLeftGrimoire() {
        StackPane leftGrimoire = new StackPane();
        leftGrimoire.setPrefSize(1500, 800);
        leftGrimoire.setLayoutX(300);
        leftGrimoire.setLayoutY(300);
        Label scoreSheet = new Label();
        scoreSheet.setText(guiGameController.getScoreSheet(guiGameController.getPlayer1()).toString());
        Image image = new Image(getClass().getResourceAsStream("/images/grimoire.png"));
        BackgroundSize backgroundSize = new BackgroundSize(1500, 800, true, true, false, true);
        BackgroundImage backgroundImage = new BackgroundImage(
                image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                backgroundSize);
        Background background = new Background(backgroundImage);
        leftGrimoire.setBackground(background);
        leftGrimoire.getChildren().add(scoreSheet);
        sceneController.boardScene.addToAnchorPane(leftGrimoire);

    }

    public String getColorAsString(Dice dice) {
        StringBuilder colorString = new StringBuilder("/images/Dice/");
        switch (dice.getRealm()) {
            case RED: colorString.append("Red/red"); break;
            case GREEN: colorString.append("Green/green"); break;
            case BLUE: colorString.append("Blue/blue"); break;
            case MAGENTA: colorString.append("Magenta/magenta"); break;
            case YELLOW: colorString.append("Yellow/yellow"); break;
            default: colorString.append("White/white"); break;
        }
        return colorString.toString();
    }

    public void initEventListeners() {
        sceneController.mainMenuScene.getStartGameButton().setOnMouseClicked(e -> startGame());
        sceneController.boardScene.getLeftGrimoire().setOnMouseClicked(e -> openLeftGrimoire());
        sceneController.mainMenuScene.getExitButton().setOnMouseClicked(e -> primaryStage.close());  //this should close the game when clicked
        sceneController.mainMenuScene.getPvPButton().setOnMouseClicked(e -> startGame());
        sceneController.mainMenuScene.getExitButton().setOnMouseClicked(e -> primaryStage.close());  //this should close the game when clicked
        initDiceEventListeners();
        sceneController.getPhoenix().setOnMouseClicked(e -> handleMove(4, 0));
        sceneController.getGoBackButton().setOnMouseClicked(e -> sceneController.switchToMain());
        sceneController.getStartGameButton().setOnMouseClicked(e -> sceneController.switchFromMain());
        sceneController.getRedRealmGoBackButton().setOnMouseClicked(e -> primaryStage.setScene(sceneController.boardScene.getBoardScene(this.guiGameController.getCurrentRound(), this.guiGameController.getCurrentTurn(), this.guiGameController.getActivePlayer().getName() )));
        sceneController.getGreenRealmGoBackButton().setOnMouseClicked(e -> primaryStage.setScene(sceneController.boardScene.getBoardScene(this.guiGameController.getCurrentRound(), this.guiGameController.getCurrentTurn(), this.guiGameController.getActivePlayer().getName() )));
        sceneController.getBlueRealmGoBackButton().setOnMouseClicked(e -> primaryStage.setScene(sceneController.boardScene.getBoardScene(this.guiGameController.getCurrentRound(), this.guiGameController.getCurrentTurn(), this.guiGameController.getActivePlayer().getName() )));
        sceneController.getMagentaRealmGoBackButton().setOnMouseClicked(e -> primaryStage.setScene(sceneController.boardScene.getBoardScene(this.guiGameController.getCurrentRound(), this.guiGameController.getCurrentTurn(), this.guiGameController.getActivePlayer().getName() )));
        sceneController.getYellowRealmGoBackButton().setOnMouseClicked(e -> primaryStage.setScene(sceneController.boardScene.getBoardScene(this.guiGameController.getCurrentRound(), this.guiGameController.getCurrentTurn(), this.guiGameController.getActivePlayer().getName() )));

        sceneController.getDragon1().setOnMouseClicked(e -> {
            guiGameController.setSelectedDragon(1);
            //handle dragon part
        });
        sceneController.getDragon2().setOnMouseClicked(e -> {
            guiGameController.setSelectedDragon(2);
            //handle dragon part
        });
        sceneController.getDragon3().setOnMouseClicked(e -> {
            guiGameController.setSelectedDragon(3);
            //handle dragon part
        });
        sceneController.getDragon4().setOnMouseClicked(e -> {
            guiGameController.setSelectedDragon(4);
            //handle dragon part
        });
        //To-Do
        /*
        sceneController.getDragonWings().setOnMouseClicked(e -> handleMove());
        sceneController.getDragonWings().setOnMouseClicked(e -> handleMove());
        sceneController.getDragonWings().setOnMouseClicked(e -> handleMove());
        sceneController.getDragonWings().setOnMouseClicked(e -> handleMove());
         */
    }

    public void initDiceEventListeners() {
        sceneController.getRedDice().setOnMouseClicked(e -> setupRealmScene("Red"));
        sceneController.getGreenDice().setOnMouseClicked(e -> setupRealmScene("Green"));
        sceneController.getBlueDice().setOnMouseClicked(e -> setupRealmScene("Blue"));
        sceneController.getMagentaDice().setOnMouseClicked(e -> setupRealmScene("Magenta"));
        sceneController.getYellowDice().setOnMouseClicked(e -> setupRealmScene("Yellow"));
        sceneController.getArcaneDice().setOnMouseClicked(e -> handleMove(6,0));
    }

    public void handleMove(int num, int callLayer) {
        //change this later
        if (color.equals("Magenta")) {
            MagentaDice currDice = (MagentaDice) guiGameController.getGameBoard().getAllDice()[3];
            Creature creature = guiGameController.getActivePlayer().getScoreSheet().getCreatureByColor(RealmColor.MAGENTA);
            Player player = guiGameController.getActivePlayer();
            boolean moveDone = guiGameController.makeMove(player, new Move(currDice, creature));
            if (!moveDone) {
                //if we enter here, that means that some sort of exception has been caught
                //either a bonus exception or an invalid move exception
                Exception exception = guiGameController.getException();
                if (exception instanceof BonusException) {
                    switch (((BonusException)exception).getRealmColor1()) {
                        case RED: setupRealmScene("Red"); break;
                        case GREEN: setupRealmScene("Green"); break;
                        case BLUE: setupRealmScene("Blue"); break;
                        case MAGENTA: setupRealmScene("Magenta"); break;
                        case YELLOW: setupRealmScene("Yellow"); break;
                        default: handleEssenceBonus(); break;
                    }

                    //put in the bonus make move logic

                    switch (((BonusException)exception).getRealmColor2()) {
                        case RED: setupRealmScene("Red"); break;
                        case GREEN: setupRealmScene("Green"); break;
                        case BLUE: setupRealmScene("Blue"); break;
                        case MAGENTA: setupRealmScene("Magenta"); break;
                        case YELLOW: setupRealmScene("Yellow"); break;
                        case WHITE: handleEssenceBonus(); break;
                        default: return;
                    }

                    //put in the bonus make move logic
                }
                else {
                    //put in a popup that tells the user that he has done an illegal move
                    illegalMoveAlert(); 
                    //logic here
                    //and go back to the dice board
                    primaryStage.setScene(sceneController.boardScene.getBoardScene(this.guiGameController.getCurrentRound(), this.guiGameController.getCurrentTurn(), this.guiGameController.getActivePlayer().getName() ));
                }
            }
        }
    }

    public void handleForgottenTurn() {
        guiGameController.moveAllIntoForgotten();
        //initiate forgotten realm turn
        sceneController.boardScene.makeboardScene(getDicePNGs(guiGameController.getForgottenRealmDice()));
        primaryStage.setScene(sceneController.boardScene.getBoardScene(this.guiGameController.getCurrentRound(), this.guiGameController.getCurrentTurn(), this.guiGameController.getCurrentPlayer().getName()));
        isForgotten = true;
        initDiceEventListeners();
    }
    public void illegalMoveAlert(){
        Alert thisIsAnAlert = new Alert(AlertType.WARNING);
        thisIsAnAlert.setTitle("Alert");
        Label contentLabel = new Label("You have made an Illegal Move");
        contentLabel.setStyle("-fx-font-size: 30px;");
        // Set the Label as the content of the alert
        thisIsAnAlert.getDialogPane().setContent(contentLabel);
        thisIsAnAlert.showAndWait();
    }

    public void handleEssenceBonus() {
        TextInputDialog textInputDialog = new TextInputDialog();

        // Set the dialog title and header text
        textInputDialog.setTitle("Essence Bonus");
        textInputDialog.setHeaderText("You have obtained an Essence Bonus! Please input the name of the Realm you would like to attack! Be careful while inputting, because you can't go back.");

        // Show the dialog and capture the input
        Optional<String> result = textInputDialog.showAndWait();
        String realm = "";

        while (realm.isEmpty() || !checkRealmValidity(realm)) {
            try {
                realm = result.get();
            } catch (NoSuchElementException e) {}
        }

        setupRealmScene(realm);
    }

    public boolean checkRealmValidity(String realm) {
        realm = realm.toLowerCase();
        return realm.equals("red") || realm.equals("blue") || realm.equals("green") || realm.equals("yellow") || realm.equals("magenta");
    }

    public void startGame() {
        handlePlayerNameInputs();
        primaryStage.setScene(sceneController.boardScene.getBoardScene(this.guiGameController.getCurrentRound(), this.guiGameController.getCurrentTurn(), this.guiGameController.getActivePlayer().getName() ));


    }

    public void handlePlayerNameInputs() {
        TextInputDialog textInputDialog = new TextInputDialog();

        textInputDialog.setTitle("Player Name Input");
        textInputDialog.setHeaderText("Please enter Player 1's name");

        Optional<String> result = textInputDialog.showAndWait();
        String player1Name;

        try {
            player1Name = handleNames(result.get());
        } catch (NoSuchElementException e) {
            player1Name = handleNames("");
        }
        textInputDialog = new TextInputDialog();

        textInputDialog.setTitle("Player Name Input");
        textInputDialog.setHeaderText("Please enter Player 2's name");

        result = textInputDialog.showAndWait();
        String player2Name;

        try {
            player2Name = handleNames(result.get());
        } catch (NoSuchElementException e) {
            player2Name = handleNames("");
        }

        guiGameController.getPlayer1().setName(player1Name);
        guiGameController.getPlayer2().setName(player2Name);
    }

    public String handleNames(String playerName) {
        String[] magicNames = {
                "Akiramenai", "Clown", "Zephyrion", "Luminara", "Amrosgy", "Elandor", "Celestia", "Drakonis",
                "Seraphina", "Faelan", "Azura", "Eldric", "Isilme", "Badawayyy", "Aelar", "Lyra", "Vesper",
                "Dumbbelldoor", "CNC", "Boring", "Sylphine", "Zeus", "Adolf", "Arion", "Liora", "Valerian",
                "Esmeray", "Adolf", "Amara", "Kael", "MONSTER...THE DRINK", "Oberon", "Elara", "Utopia", "Morrigan",
                "Za3bola", "Kaelen", "REWE", "Dumbledore", "Fenris", "Gandalf", "Dimension6", "Arwen", "Serapis",
                "ACE", "Sixfold", "Marianna", "El Le3ba", "Za3bola", "Square Moustache guy", "Hooba"
        };
        if (playerName.trim().isEmpty()) {
            Random random = new Random();

            // Get a random index between 0 and the length of the array
            int randomIndex = random.nextInt(magicNames.length);

            // Get the random name from the array
            String randomName = magicNames[randomIndex];

            playerName = randomName;
        }

        switch(playerName.toLowerCase()) {
            case "dimension6":
                playerName = guiGameController.changeToRainbowText(playerName);
                break;

            case "slmat":
            case "doctor":
            case "dr":
            case "dr.":
            case "doc":
            case "ahmed hussein":
                playerName = guiGameController.changeToRainbowText("slmat27");
                break;

            case "noureldin":
            case "nesegemaa":
            case "mahmoud":
            case "elephant":
            case "elephanto":
            case "elephanto gyat":
            case "elephantogyat":
            case "0ping":
            case "safwat":
            case "hamed":
            case "hotdog":
            case "hotdawg":
            case "tamer":
            case "kirat":
                playerName = guiGameController.changeToRainbowText("Xx" + playerName + "xX");
                break;

            case "ace":
            case "rewe":
            case "el le3ba":
            case "le3ba":
            case "dumbbeldoor":
            case "sixfold":
            case "amrosgy":
            case "utopia":
            case "akiraminai":
            case "badawayyy":
            case "zeus":
                playerName = guiGameController.changeToRainbowText(playerName);
                break;

            case "sharazad":
                playerName = guiGameController.changeToRainbowText(playerName);
                break;

            case "giu":
                playerName = guiGameController.changeToRainbowText(playerName);
                break;
            case "guc":
                playerName = guiGameController.changeToRainbowText(playerName);
            case "meow":
                playerName = guiGameController.changeToRainbowText(playerName);

            default:
                break;
        }
        return playerName;
    }

    public void setupRealmScene(String realmColor) {
        Scene scene;
        switch (realmColor.toLowerCase()) {
            case "red": scene = sceneController.redScene.getScene();break;
            case "green": scene = sceneController.greenScene.getScene(); break;
            case "blue": scene = sceneController.blueScene.getScene(); break;
            case "magenta": scene = sceneController.magentaScene.getScene(); break;
            case "yellow": scene = sceneController.yellowScene.getScene(); break;
            case "white": 
                        int whiteVal = guiGameController.getGameBoard().getWhite().getValue();
                        Dice [] dietmp= {new RedDice(whiteVal), guiGameController.getGameBoard().getGreen(), new BlueDice(whiteVal), new MagentaDice(whiteVal), new YellowDice(whiteVal)};
                        String [] tmp = getDicePNGs(dietmp);
                        Dialog whiteDialog = sceneController.boardScene.handleWhiteDice(tmp);
                        String result =(String) whiteDialog.showAndWait().get();
                        String [] resultAsArray= result.split(" ");
                        switch (resultAsArray[0]){
                            case "red":     scene = sceneController.redScene.getScene();break;
                            case "green":   scene = sceneController.greenScene.getScene(); break;
                            case "blue":    scene = sceneController.blueScene.getScene(); break;
                            case "magenta": scene = sceneController.magentaScene.getScene(); break;
                            case "yellow":  scene = sceneController.yellowScene.getScene(); break;
                            default:        scene = null;
                        }
                        
                        break;

            default: scene = null;
        }
        primaryStage.setScene(scene);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
