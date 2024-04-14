# Project Skeleton

## Folder Structure

```css
Dice-Realms/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── game/
│   │   │       ├── collectibles/
│   │   │       │   ├── Reward.java
│   │   │       │   ├── Power.java
│   │   │       │   ├── Bonus.java
│   │   │       │   ├── ElementalCrest.java
│   │   │       │   ├── ArcaneBoost.java
│   │   │       │   ├── TimeWarp.java
│   │   │       │   └── EssenceBonus.java
│   │   │       ├── creatures/
│   │   │       │   ├── Creature.java
│   │   │       │   ├── Dragon.java
│   │   │       │   ├── Gaia.java
│   │   │       │   ├── Hydra.java
│   │   │       │   ├── Phoenix.java
│   │   │       │   └── Lion.java
│   │   │       ├── dice/
│   │   │       │   ├── Dice.java
│   │   │       │   ├── RedDice.java
│   │   │       │   ├── GreenDice.java
│   │   │       │   ├── BlueDice.java
│   │   │       │   ├── MagentaDice.java
│   │   │       │   ├── YellowDice.java
│   │   │       │   └── ArcanePrism.java
│   │   │       ├── engine/
│   │   │       │   ├── GameController.java
│   │   │       │   ├── CLIGameController.java
│   │   │       │   ├── GameBoard.java
│   │   │       │   ├── Player.java
│   │   │       │   ├── ScoreSheet.java
│   │   │       │   ├── GameStatus.java
│   │   │       │   ├── GameScore.java
│   │   │       │   └── Move.java
│   │   │       ├── exceptions/
│   │   │       │   ├── RewardException.java
│   │   │       │   ├── PlayerActionException.java
│   │   │       │   ├── InvalidMoveException.java
│   │   │       │   ├── InvalidDiceSelectionException.java
│   │   │       │   ├── DiceRollException.java
│   │   │       │   ├── CommandFormatException.java
│   │   │       │   └── ExhaustedResourceException.java
│   │   │       ├── gui/
│   │   │       └── Main.java
│   │   │
│   │   └── resources/
│   │       ├── images/
│   │       │   ├── Project-UML-Diagram.png
│   │       ├── config/
│   │       │   ├── EmberfallDominionRewards.properties
│   │       │   ├── RoundsRewards.properties
│   │       │   ├── MysticalSkyRewards.properties
│   │       │   ├── TerrasHeartlandRewards.properties
│   │       │   ├── RadiantSvannaRewards.properties
│   │       │   └── TideAbyssRewards.properties
│   │       └── EmptyScoreSheet.txt
│   │
│   └── test/
│       └── java/
│           └── game/
│               ├── collectibles/
│               ├── creatures/
│               ├── dice/
│               ├── engine/
│               ├── exceptions/
│               └── gui/
│
├── pom.xml
├── Grades.md
└── README.md
```

## Packages

### game.engine

This package serves as the core for all game mechanics, containing both abstract and concrete classes that manage game flow, player interactions, and the game board:

- `GameController`: Abstract class that sets the foundational methods for game controllers.
- `CLIGameController`: Extends GameController, implementing the CLI (Command Line Interface) version of the game controller.
- `GameBoard`: Manages the layout and state of the game board, including dice and players.
- `Player`: Represents a player in the game, managing their status, score, and actions.
- `ScoreSheet`: Keeps track of a player's scores across different realms.
- `GameStatus`: Manages the current status of the game, including rounds and turns.
- `GameScore`: Details the scoring logic and current scores for players.
- `Move`: Represents a player's move, including dice and creature interactions.

### game.dice

This package includes classes for different types of dice and their statuses:

- `Dice`: Abstract base class for all dice.
- `RedDice`, `GreenDice`, `BlueDice`, `MagentaDice`, `YellowDice`, `ArcanePrism`: Specific dice types used in the game, each with unique properties.

### game.creatures

This package houses classes that represent various mythical creatures in the game:

- `Creature`: Abstract base class for all creatures.
- `Dragon`, `Gaia`, `Hydra`, `Phoenix`, `Lion`: Specific creature classes with unique attributes and behaviors.

### game.collectibles

This package includes classes for different types of game rewards and powers that players can collect:

- `Reward`: Abstract base class for all rewards.
- `Power`: Abstract base class for power-up abilities.
- `Bonus`: Base class for the bonus abilities.
- `ArcaneBoost`, `TimeWarp`, `EssenceBonus`: Various collectible items of power-ups and bonuses that provide advantages in gameplay.
- `ElementalCrest`: A unique collectible item, which represent the pinnacle achievement and it is the quest of the game to collect them all.

### game.exceptions

This package defines custom exceptions to handle various error scenarios specifically related to game actions:

- `RewardException`
- `PlayerActionException`
- `InvalidMoveException`
- `InvalidDiceSelectionException`
- `DiceRollException`
- `CommandFormatException`
- `ExhaustedResourceException`

### game.gui

This package is designated for graphical user interface components, which are crucial for versions of the game that include a GUI. (work-in-progress)

## Implementation

Here is the detailed implementation skeleton for each of the classes and their corresponding methods:

### `GameController.java`

- **Package**: `game.engine`
- **Type**: Abstract Class
- **Description**: This abstract class represents the controller for the game. It defines the common blueprint for different controllers used in the game.

#### Methods:

1. `void startGame()`

   - **Description**: Initializes necessary components and starts the game loop.

2. `boolean switchPlayer()`

   - **Description**: Switches the role of the current active player to passive and vice versa, ensuring that the turn-taking mechanism functions correctly.
   - **Return Type**: `boolean`
     - `true` if the switch was successful,
     - `false` otherwise.

3. `Dice[] rollDice()`

   - **Description**: Rolls all available dice for the current turn, assigning each a random number from 1 to 6.
   - **Return Type**: Array of `Dice`
     - An array of the currently rolled dice.

4. `Dice[] getAvailableDice()`

   - **Description**: Gets the dice available for rolling or rerolling.
   - **Return Type**: Array of `Dice`
     - An array of dice available for the current turn.

5. `Dice[] getAllDice()`

   - **Description**: Gets all six dice, providing their current state and value within the game regardless of their location or status.
   - **Return Type**: Array of `Dice`
     - An array of all six dice, with each die's state and value.

6. `Dice[] getForgottenRealmDice()`

   - **Description**: Gets the dice currently available in the Forgotten Realm.
   - **Return Type**: Array of `Dice`
     - An array of dice that are currently in the Forgotten Realm.

7. `Move[] getAllPossibleMoves(Player player)`

   - **Description**: Gets all possible moves for a given player.
   - **Parameters**:
     - `player`: The player for whom to determine possible moves.
   - **Return Type**: Array of `Move`
     - An array of all possible moves for all rolled dice.

8. `Move[] getPossibleMovesForAvailableDice(Player player)`

   - **Description**: Gets possible moves for all currently rolled dice for a given player.
   - **Parameters**:
     - `player`: The player for whom to determine possible moves.
   - **Return Type**: Array of `Move`
     - An array of all possible moves for all rolled dice.

9. `Move[] getPossibleMovesForADie(Player player, Dice dice)`

   - **Description**: Gets all possible moves for a given die for a given player.
   - **Parameters**:
     - `player`: The player for whom to determine possible moves.
     - `dice`: The dice to determine possible moves for.
   - **Return Type**: Array of `Move`
     - An array of possible moves for the given dice.

10. `GameBoard getGameBoard()`

    - **Description**: Gets the current game board, including all players and all score sheets.
    - **Return Type**: `GameBoard`
      - The current game board object.

11. `Player getActivePlayer()`

    - **Description**: Gets the current active player's information.
    - **Return Type**: `Player`
      - The active player object.

12. `Player getPassivePlayer()`

    - **Description**: Gets the current passive player's information.
    - **Return Type**: `Player`
      - The passive player object.

13. `ScoreSheet getScoreSheet(Player player)`

    - **Description**: Gets the score sheet for a given player.
    - **Parameters**:
      - `player`: The player to get the current score sheet for.
    - **Return Type**: `ScoreSheet`
      - The score sheet object for the given player.

14. `GameStatus getGameStatus()`

    - **Description**: Gets the current game status, including round and turn information for the current active player.
    - **Return Type**: `GameStatus`
      - The current game status object.

15. `GameScore getGameScore(Player player)`

    - **Description**: Gets the current score of the game for a given player.
    - **Parameters**:
      - `player`: The player to determine current score for.
    - **Return Type**: `GameScore`
      - The current game score object for the given player.

16. `TimeWarp[] getTimeWarpPowers(Player player)`

    - **Description**: Gets the array of TimeWarp powers and their status for a given player.
    - **Parameters**:
      - `player`: The player to get the current TimeWarp powers for.
    - **Return Type**: Array of `TimeWarp`
      - An array of `TimeWarp` objects representing the TimeWarp powers for the given player.

17. `ArcaneBoost[] getArcaneBoostPowers(Player player)`

    - **Description**: Gets the array of ArcaneBoost powers and their status for a given player.
    - **Parameters**:
      - `player`: The player to get the current ArcaneBoost powers for.
    - **Return Type**: Array of `ArcaneBoost`
      - An array of `ArcaneBoost` objects representing the ArcaneBoost powers for the given player.

18. `boolean selectDice(Dice dice, Player player)`

    - **Description**: Selects a die and adds it to the player's class, then moves all other dice with less value to the Forgotten Realm.
    - **Parameters**:
      - `player`: The player who selected the die.
      - `dice`: The dice to be selected.
    - **Return Type**: `boolean`
      - `true` if the selection was successful,
      - `false` otherwise.

19. `boolean makeMove(Player player, Move move)`

    - **Description**: Executes a move using the selected dice on a specified creature.
    - **Parameters**:
      - `player`: The player who wants to make the move.
      - `move`: The move to be executed, including the selected dice and target creature.
    - **Return Type**: `boolean`
      - `true` if the move is successfully completed,
      - `false` otherwise.

## Additional Notes

- Each class is designed to work together seamlessly to provide a robust game experience. The structure allows for expansion and integration of additional features such as network play or AI opponents.
- The `Main.java` serves as the entry point of the application, initializing the game environment and starting the game loop.
- For additional information, please refer to the [Project UML Diagram](/src/main/resources/images/Project-UML-Diagram.png). Further details will not available at this stage.

_NOTE: This package structure and UML diagram provide a comprehensive overview of how the game components interact and are managed within the codebase._
