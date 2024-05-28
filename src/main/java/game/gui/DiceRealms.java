package game.gui;
import game.dice.Dice;
import game.engine.GUIGameController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class DiceRealms extends Application {
    GUIGameController guiGameController;
    SceneController sceneController;
    Stage primaryStage;
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
        sceneController.boardScene.makeDiceScene(getDicePNGs(guiGameController.getAvailableDice()));
        sceneController.redScene.createScene();
        sceneController.greenScene.createScene();
        sceneController.blueScene.createScene();
        sceneController.magentaScene.createScene();
        sceneController.yellowScene.createScene();
        initEventListeners();
        primaryStage.show();
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
        sceneController.mainMenuScene.getStartGameButton().setOnMouseClicked(e -> setupGameScene()); ;
        sceneController.getRedDice().setOnMouseClicked(e -> setupRealmScene("Red"));
        sceneController.getGreenDice().setOnMouseClicked(e -> setupRealmScene("Green"));
        sceneController.getBlueDice().setOnMouseClicked(e -> setupRealmScene("Blue"));
        sceneController.getMagentaDice().setOnMouseClicked(e -> setupRealmScene("Magenta"));
        sceneController.getYellowDice().setOnMouseClicked(e -> setupRealmScene("Yellow"));
    }

    public void handleMove(String color) {
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
                    //logic here
                    //and go back to the dice board
                    primaryStage.setScene(sceneController.boardScene.getBoardScene());
                }
            }
        }
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
        primaryStage.setScene(sceneController.boardScene.getBoardScene());
        handlePlayerNameInputs();

    }

    public void setupRealmScene(String realmColor) {
        Scene scene;
        switch (realmColor) {
            case "Red": scene = sceneController.redScene.getScene();break;
            case "Green": scene = sceneController.greenScene.getScene(); break;
            case "Blue": scene = sceneController.blueScene.getScene(); break;
            case "Magenta": scene = sceneController.magentaScene.getScene(); break;
            case "Yellow": scene = sceneController.yellowScene.getScene(); break;
            default: scene = null;
        }
        primaryStage.setScene(scene);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
