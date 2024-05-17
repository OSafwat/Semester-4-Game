# Project Skeleton

## Folder Structure

```css
dice-realms
├── Grades.md
├── ProjectSkeleton.md
├── README.md
├── TemplateSkeleton.md
├── bin
│   ├── Grades.md
│   ├── ProjectSkeleton.md
│   ├── README.md
│   ├── TemplateSkeleton.md
│   ├── pom.xml
│   └── src
│       └── main
│           └── resources
│               ├── EmptyScoreSheet.txt
│               ├── config
│               │   ├── EmberfallDominionRewards.properties
│               │   ├── MysticalSkyRewards.properties
│               │   ├── RadiantSvannaRewards.properties
│               │   ├── RoundsRewards.properties
│               │   ├── RoundsSettings.properties
│               │   ├── TerrasHeartlandRewards.properties
│               │   └── TideAbyssRewards.properties
│               └── images
│                   └── Project-UML-Diagram.png
├── pom.xml
└── src
    ├── main
    │   ├── java
    │   │   ├── game
    │   │   │   ├── Main.java
    │   │   │   ├── collectibles
    │   │   │   │   ├── ArcaneBoost.java
    │   │   │   │   ├── Bonus.java
    │   │   │   │   ├── ElementalCrest.java
    │   │   │   │   ├── EssenceBonus.java
    │   │   │   │   ├── Power.java
    │   │   │   │   ├── Reward.java
    │   │   │   │   └── TimeWarp.java
    │   │   │   ├── creatures
    │   │   │   │   ├── Creature.java
    │   │   │   │   ├── Dragon.java
    │   │   │   │   ├── Hydra.java
    │   │   │   │   ├── Lion.java
    │   │   │   │   ├── Phoenix.java
    │   │   │   │   └── greenclasses
    │   │   │   │       ├── Gaia.java
    │   │   │   │       └── Guardians.java
    │   │   │   ├── dice
    │   │   │   │   ├── ArcanePrism.java
    │   │   │   │   ├── BlueDice.java
    │   │   │   │   ├── Dice.java
    │   │   │   │   ├── GreenDice.java
    │   │   │   │   ├── MagentaDice.java
    │   │   │   │   ├── RedDice.java
    │   │   │   │   └── YellowDice.java
    │   │   │   ├── engine
    │   │   │   │   ├── AI.java
    │   │   │   │   ├── CLIGameController.java
    │   │   │   │   ├── GameBoard.java
    │   │   │   │   ├── GameController.java
    │   │   │   │   ├── GameScore.java
    │   │   │   │   ├── GameStatus.java
    │   │   │   │   ├── Move.java
    │   │   │   │   ├── Player.java
    │   │   │   │   ├── ScoreSheet.java
    │   │   │   │   └── enums
    │   │   │   │       ├── DiceStatus.java
    │   │   │   │       ├── DragonNumber.java
    │   │   │   │       ├── PlayerStatus.java
    │   │   │   │       ├── RealmColor.java
    │   │   │   │       ├── RewardStates.java
    │   │   │   │       └── RewardType.java
    │   │   │   ├── exceptions
    │   │   │   │   ├── BonusException.java
    │   │   │   │   ├── CommandFormatException.java
    │   │   │   │   ├── DiceRollException.java
    │   │   │   │   ├── ExhaustedResourceException.java
    │   │   │   │   ├── InvalidBonusSelectionException.java
    │   │   │   │   ├── InvalidDiceSelectionException.java
    │   │   │   │   ├── InvalidMoveException.java
    │   │   │   │   ├── NoAvailableMovesException.java
    │   │   │   │   ├── PlayerActionException.java
    │   │   │   │   └── RewardException.java
    │   │   │   └── gui
    │   │   │       └── DiceRealms.java
    │   │   └── module-info.java
    │   └── resources
    │       ├── EmptyScoreSheet.txt
    │       ├── config
    │       │   ├── EmberfallDominionRewards.properties
    │       │   ├── MysticalSkyRewards.properties
    │       │   ├── RadiantSvannaRewards.properties
    │       │   ├── RoundsRewards.properties
    │       │   ├── RoundsSettings.properties
    │       │   ├── TerrasHeartlandRewards.properties
    │       │   └── TideAbyssRewards.properties
    │       └── images
    │           └── Project-UML-Diagram.png
    └── test
        └── java
            └── game
                └── engine
                    └── CLIGameControllerTest.java
```

## Packages

### game.collectibles

The `game.collectibles` package contains classes for the various collectible items within the game; such as power-ups, elemental crest, color bonus, or the essence bonus.

### game.creatures

In the `game.creatures` package, you'll find classes representing creatures in their corresponding realms; including all necessary features about how to attack them or their current status to update the score sheet accordingly.

### game.dice

The `game.dice` package encompasses classes related to dice functionality within the game. It includes implementations for rolling dice, managing dice states, and handling dice-related actions and interactions.

### game.engine

This package contains the core engine components of the game, including the abstract classes and interfaces that define the game's structure and functionality. It serves as the foundation for implementing various game controllers and managing game logic. Additional classes related to game mechanics and control can be added to this package as needed.

### game.exceptions

The `game.exceptions` package provides classes for defining custom exceptions specific to the game. These exceptions help handle error conditions and unexpected situations, providing meaningful feedback to the player or developer.

### game.gui

The `game.gui` package houses classes related to the graphical user interface (GUI) of the game. This includes components for rendering game graphics, handling user input, and managing the visual presentation of game elements.

## Classes

For each package, add the skeleton details for the class and duplicate as much as needed. As an example, the `GameController.java` skeleton is provided as guideline.

### `GameController` class

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

### `CLIGameController` class

- **Package**: `game.engine`
- **Type**: Class
- **Description**: This class represents the main controller for the game. 

#### Methods:

1. `int[] getSettings()`

    - **Description**: Reads the config files to get the game settings data.
    - **Return Type**: `int[]`
        An integer array that contains the values related to the game settings.

2. `String[] getRewards(int numberOfRounds)`

    - **Description**: Reads the config files to get the round rewards data.
    - **Parameters**:
        - `numberOfRounds`: A value which specifies an upper count on the number of round rewards that should be obtained from the config file.
    - **Return Type**: `String[]`
        A string array that contains the strings that represent the round rewards.

3. `void handleRoundRewards(Player player, String reward)`

    - **Description**: Handles the rewards obtained at the start of a player's turn in a new round.
    - **Parameters**:
        - `player`: The player who has obtained the reward.
        - `reward`: The reward that the player should get now.
    - **Return Type**: `void`

4. `void playRound(Player player, String reward, int turnCount)`

    - **Description**: A method which manages how a round plays out.
    - **Parameters**:
        - `player`: The player who is playing his turn now.
        - `reward`: The reward obtained in this round.
        - `turnCount`: The number of turns to be played in the round.
    - **Return Type**: `void`

5. `boolean playTurn(Player player, boolean isThisATimeWarpRerollCall)`

    - **Description**: A method which handles every ACTIVE turn being played.
    - **Parameters**:
        - `player`: The player whose active turn is now.
        - `isThisATimeWarpRerollCall`: .
    - **Return Type**: `boolean`
        - `true` if the turn is successfully completed,
        - `false` otherwise.

6. `boolean playForgottenTurn(Player player)`

    - **Description**: A method which handles every PASSIVE turn being played.
    - **Parameters**:
        - `player`: The player whose passive turn is now.
    - **Return Type**: `void`

7. `void resetRed()`

    - **Description**: A method which resets the Red Dice, in case any mishandling of it occurs.
    - **Parameters**:

    - **Return Type**: `void`

8. `boolean turnCompletion(Player player) throws NoAvailableMovesException`

    - **Description**: A method where all turns converge to get handled.
    - **Parameters**:
        - `player`: The player whose turn is now.
    - **Return Type**: `boolean`
        - `true` if the turn is successfully completed,
        - `false` otherwise.

9. `Dice[] getArcaneBoostDice()`

    - **Description**: A method that gets the dice that can be used in an arcane boost.
    - **Parameters**:
    - **Return Type**: `Dice[]`
        - An array of dice containing the dice that can be used in this arcane boost call.

10. `void handleArcaneBoostCall(Player player)`

    - **Description**: A method which handles any arcane boosts that get used.
    - **Parameters**:
        - `player`: The player who is using the arcane boost.
    - **Return Type**: `void`

11. `RedDice handleRedDice(RedDice finalDie) throws InvalidDiceSelectionException`

    - **Description**: A method which handles any red dice that get used, since they need to be handled differently than other dice.
    - **Parameters**:
        - `finalDie`: The red dice that is being used
    - **Return Type**: `boolean`
        - The red dice after the dragon has been selected. 

12. `void handleDiceDisplay(Dice[] diceSet, int indicator)`

    - **Description**: A method which handles the process of displaying dice to the user.
    - **Parameters**:
        - `diceSet`: The set of dice that are being displayed.
        - `indicator`: A parameter that dictates the messages that appear along with the dice.
    - **Return Type**: `void`

13. `Dice handleDiceSelection(Player player, Dice[] diceSet) throws InvalidDiceSelectionException, NoAvailableMovesException`

    - **Description**: A method which handles the process of selecting a die to attack with.
    - **Parameters**:
        - `player`: The player who is selecting a die to attack with now.
        - `diceSet`: The set of dice that the player is selecting a die from now.
    - **Return Type**: `Dice`
        - The die the player has selected

14. `Dice handleArcanePrism(Dice chosenDie, Player player)`

    - **Description**: A method which handles the selection of the Arcane Prism, since it requires different handling from other dice.
    - **Parameters**:
        - `player`: The player who has selected the Arcane Prism to attack with now.
        - `chosenDie`: The die that the player has chosen.
    - **Return Type**: `Dice`
        - The die after it has been changed into the correct type to attack with, according to the player's choice.

15. `void displayDice (Dice dice)`

    - **Description**: A method which handles the process of displaying a singular die, and is used repeatedly in handleDiceDisplay to display all dice.
    - **Parameters**:
        - `dice`: The die to be displayed.
    - **Return Type**: `void`

16. `boolean handleArcaneBoost(ArcaneBoost[] arcaneBoosts)`

    - **Description**: A method that handles arcane boosts and checks if the player can use one.
    - **Parameters**:
        - `arcaneBoosts`: The array of arcane boosts that is present in the player clas. It contains all arcane boosts that the player can obtain, regardless of their status.
    - **Return Type**: `boolean`
        - `true`: If the player has decided to use an arcane boost,
        - `false`: otherwise.

17. `void displayDice (Dice dice)`

    - **Description**: A method which handles the process of displaying a singular die, and is used repeatedly in handleDiceDisplay to display all dice.
    - **Parameters**:
        - `dice`: The die to be displayed.
    - **Return Type**: `void`

18. `void handleBonus(Player player, RealmColor realmColor)`

    - **Description**: A method which handles any bonuses obtained via the round rewards system.
    - **Parameters**:
        - `player`: The current player who has just obtained a bonus.
        - `realmColor`: The realm color that dictates which bonus the realm will be used against.
    - **Return Type**: `void`

19. `boolean handleTimeWarps(TimeWarp[] timewarps)`

    - **Description**: A method that handles time warps and checks if the player can use one.
    - **Parameters**:
        - `timewarps`: The array of time warps that is present in the player clas. It contains all time warps that the player can obtain, regardless of their status.
    - **Return Type**: `boolean`
        - `true`: If the player has decided to use a time warp,
        - `false`: otherwise.

20. `Move[] getAllPossibleMovesForDiceSet (Player player, Dice[] dice) throws NoAvailableMovesException`

    - **Description**: A method that gets all possible moves that the player can do with any set of dice.
    - **Parameters**:
        - `player`: The player whose moves we want to get.
        - `dice`: The set of dice that the player can use.
    - **Return Type**: `Move[]`
        - A Move array that contains all moves that the player can do using the dice set passed as a parameter.

21. `void removeGreenDuplicate(ArrayList<Move> result)`

    - **Description**: A method that checks of a move arraylist contains a green duplicate move (the green die's move, and the green version of the Arcane Prism) and removes the duplicate.
    - **Parameters**:
        - `result`: The arraylist of Moves to be checked.
    - **Return Type**: `void`

22. `Dice handleColorBonusException(RealmColor color, Player player) throws NoAvailableMovesException, InvalidBonusSelectionException, InvalidDiceSelectionException`

    - **Description**: A method which handles any bonuses obtained throughout the game.
    - **Parameters**:
        - `color`: The color of the realm that the bonus can be used against.
        - `player`: The player that has just obtained a bonus.
    - **Return Type**: `Dice`
        - A die containing the move the player has chosen. 

23. `public void moveAllIntoForgotten()`

    - **Description**: A method which moves all dice to the forgotten realm after the active player is done playing.
    - **Parameters**:
    - **Return Type**: `void`


### `TimeWarp` class

- **Package**: `game.collectibles`
- **Type**:  Class
- **Description**: This class represents the Time Warp boost which allows the player to reroll the die.
#### Methods:

1. `void setState(PowerState)`
   - **Parameters**: 
     - The PowerState value to be changed to
   - **Description**: changes the state of the TimeWarp to the value passed in to the function.
2. `PowerState getState()`
   - **Description**: returns the state of the TimeWarp
   - **Return Type**: `PowerState`
     - `Returns the current state of the the Time Warp represented in the enum: PowerState.` 

### `ArcaneBoost` class

- **Package**: `game.collectibles`
- **Type**:  Class
- **Description**: This class represents the Arcane Boost which helps player to have an extra round.

#### Methods:
1. `void setState(PowerState)`
    - **Parameters**:
        - The PowerState value to be changed to
    - **Description**: changes the state of the ArcaneBoost to the value passed in to the function.
    - **Return Type**: Void
2. `PowerState getUsed()`
    - **Description**: returns the state of the ArcaneBoost
    - **Return Type**: `PowerState`
        - `Returns the current state of the the Arcane Boost represented in the enum: PowerState.`


### `Dice` class

- **Package**: `game.dice`
- **Type**: Class
- **Description**: This class represents the dice to be rolled and used in the game 

#### Methods:

1. `void selectsDragon(int dragonNumber)`
   - **Description**: sets the `dragonNumber` instance variable to the value passed as a parameter
   - **Parameter**:
     - `dragonNumber`: The value that the instance variable should be changed to.
   - **Return Type**: `void`

2. `int getDragonNumber()`
   - **Description**: returns the `dragonNumber` instance variable
   - **Parameter**:
   - **Return Type**: `int`
     - The `dragonNumber` instance variable

### `RedDice` class

- **Package**: `game.dice`
- **Type**: Class
- **Description**: This class represents the red dice in the game


### `Move` class

- **Package**: `game.dice`
- **Type**: Class
- **Description**: This class represents the move attempted by the player 

#### Methods:


1. `boolean makeMove(Dice dice, Creature creature)`
    - **Description**: Executes a move using the selected dice on a specified creature.
    - **Parameters**:
      - `dice`: The dice selected by the active player for the move.
      - `creature`: The target creature that the move is against.
    - **Return Type**: `boolean`
      - `true` if the move is successfully completed,
      - `false` otherwise.
      - 
2. `Creature getCreature()`
   - **Description**: returns the creature corresponding to the move made.
   - **Return Type**: `Creature`
     - The creature that makes up the move 
     
3. `Dice getDice()`
   - **Description**: returns a dice with a given color and a random number between 1 and 6 
   - **Return Type**: `Dice`
     - The dice represented by the move.



### `GameBoard` class

- **Package**: `game.engine`
- **Type**:  Class
- **Description**: This class shows the player the game board including the score sheet and the printing of all the 5 realms for both of the players.

#### Methods:
1. `void printBoard()`
   - **Description**: shows the game board containing the score sheet and the details of the 5 realms for all players.
   - **Return Type**: `void`

### 'ScoreSheet' class

  - **Package**: `game.engine`
  - **Type**: Class
  - **Description**: This class shows the scoresheet including the details of the scores of each realm including which monsters were killed and the number of collectibles for the current player.

#### Methods:
1. `ScoreSheet getScoreSheet()`
    -**Description**: returns the scoresheet for the current player.
    -**Return Type**: ScoreSheet.

2. `void initScoreSheet()`
    -**Description**: initializes the scoresheet to one with no scores in all realms and no collectibles for both players.
    -**Return Type**:void 

3. `void printScoreSheet()`
    -**Description**: prints out the entire score sheet for the current player including the number of each collectible and the specific scores of each realm and the total score.
    -**Return Type**: `void`

4. `void updateScore()`
    -**Description**: updates the score of the current player after making a valid move in both the respective realm and the total score.
    -**Return Type**: `void`

5. `void printCollectible()`
    **Description**: prints out all the collectibles for the current player.
    **Return Type**: `void`

6. `void printRealms()`
    **Description**: prints out the details of all the realms including which monsters were killed and which collectibles were acquired.
    **Return Type**: `void`


### `GameStatus` class

- **Package**: `game.engine`
- **Type**: Class
- **Description**: This class is going to show the turns and rounds information and the current active player.

#### Methods:
1. `GameStatus getGameStatus()`
    -**Description**: returns the game status with turn and round information and the current player.
    -**Return Type**: GameStatus 

2. `void initGameStatus()`
    -**Description**: initializes the game status to the first round and turn and the first active player.
    -**Return Type**: void

3. `void updateGameStatus()`
    -**Description**: updates the game status with the new turn and round information and the current player.
    -**Return Type**: void

4. `void printGameStatus()`
    **Description**: prints the turn and round information and the current player
    **Return Type**: void

5. `void printTurn()`
    **Description**: prints the turn information and the current player
    **Return Type**: void

6. `void printRound()`
    **Description**: prints the round information and the current player
    **Return Type**: void


### `GameScore` class
- **Package**: `game.engine`
- **Type**: Class
- **Description**: This class shows current score of the game including the scores of each realm, the number of elemental crests, and the total score for the current player.

#### Methods:
1. `GameScore getGameScore()`
    - **Description**: returns the current score of the game including the total score of each realm, the number of elemental crests, and the total score for the current player.
    - **Return Type**: 

2. `void initGameScore()`
    - **Description**: initializes the gamescore to one with 0 scores in all realms and 0 elemental crests.
    - **Return Type**: void

3. `void printGameScore()`
    - **Description**: prints the current score of the game including the total score of each realm, the number of elemental crests, and the total score for the current player.
    - **Return Type**: void

4. `void updateGameScore()`
    - **Description**: updates the current score of a realm after making a valid move in it, then updates the total score and the number of elemental crests for the current player.
    - **Return Type**: void  

### `Player` class
- **Package**: `game.engine`
- **Type**: Class
- **Description**: This class will contain information regarding the player.

### Methods:
1. `getPlayerState()` 
    - **Description**: Returns the player state for the current player. 
    - **Return Type**: PlayerState

2. `setPlayerState(PlayerState playerState)`
    - **Description**: Sets the player state for the current player to the input state.
    - **Parameters**:
        `playerState`: The PlayerState to be changed to.
    - **Return Type**: Void

3. `HashMap<Dice> getDiceUsed()`
    - **Description**: Used to get the hashmap container having the dice used by the player.
    - **Return Type**: Hashmap with the dice used by the player.

4. `void addDiceUsed(Dice dice)`
    - **Description**: Used to add dice to the hashmap container having the dice used by the player.
    - **Return Type**: Void


### `Realm` class

- **Package**: `game.engine`
- **Type**: Abstract Class
- **Description**: This class is going to serve as the template for the five different realm classes.

#### Methods:
1. `int getScore()`
   - **Description**: Returns the current score of this realm.
   - **Return Type**: `int`
     - Is the integer value representing the total score collected in this realm for the current player.

2. `void updateScore()`
   - **Description**: Updates the value of the score of this realm according to the entity that has been defeated.
   - **Return Type**: `void`

3. `boolean checkMove(Dice dice, Creature creature)`
   - **Description**: Check if a move using a specific dice can be used against a specific entity.
   - **Parameter**: 
     - `dice`: The dice selected by the player.
     - `creature`: The target creature selected by the player.
   - **Return Type**: `boolean`
     - `true` if the move is possible.
     - `false` otherwise.

4. `boolean makeMove(Dice dice, Creature creature)`
   - **Description**: Executes an attack on a specific entity using a specific dice.
   - **Parameter**: 
     - `dice`: The dice selected by the player for the move.
     - `creature`: The target creature selected by the player.
   - **Return Type**: `boolean`
     - `true` if the move is successfully completed.
     - `false` otherwise.

5. `void print()`
   - **Description**: Displays data related to the realm.
   - **Parameter**: 
   - **Return Type**: `void`

### `GreenRealm` class

- **Package**: `game.engine`
- **Type**: Class
- **Description**: This class is going to represent the realm that has the gaia guardians.

#### Methods:
1. `int getScore()`
   - **Description**: Returns the current score of this realm.
   - **Return Type**: `int`

2. `void updateScore()`
   - **Description**: Updates the value of the score of this realm according to the guardian that has been defeated.
   - **Return Type**: `void`

3. `boolean checkMove(Dice dice, Creature creature)`
   - **Description**: Checks if a move using a specific dice can be used against a specific guardian.
   - **Parameter**: 
     - `dice`: The dice selected by the player.
     - `creature`: The target guardian selected by the player.
   - **Return Type**: `boolean`
     - `true` if the move is possible.
     - `false` otherwise.

4. `boolean makeMove(Dice dice, Creature creature)`
   - **Description**: Executes an attack on a specific guardian using a specific dice.
   - **Parameter**: 
     - `dice`: The dice selected by the player for the move.
     - `creature`: The target guardian selected by the player.
   - **Return Type**: `boolean`
     - `true` if the move is successfully completed.
     - `false` otherwise.

5. `void print()`
   - **Description**: Displays data related to the realm.
   - **Parameter**: 
   - **Return Type**: `void`

### `BlueRealm` class

- **Package**: `game.engine`
- **Type**: Class
- **Description**: This class is going to represent the realm that has the hydra serpents.
#### Methods:
1. `int getScore()`
   - **Description**: Returns the current score of this realm.
   - **Return Type**: `int`

2. `void updateScore()`
   - **Description**: Updates the value of the score of this realm according to the serpent that has been defeated.
   - **Return Type**: `void`

3. `boolean checkMove(Dice dice, Creature creature)`
   - **Description**: Checks if a move using a specific dice can be used against a specific serpent.
   - **Parameter**: 
     - `dice`: The dice selected by the player.
     - `creature`: The target serpent selected by the player.
   - **Return Type**: `boolean`
     - `true` if the move is possible.
     - `false` otherwise.

4. `boolean makeMove(Dice dice, Creature creature)`
   - **Description**: Executes an attack on a specific serpent using a specific dice.
   - **Parameter**: 
     - `dice`: The dice selected by the player for the move.
     - `creature`: The target serpent selected by the player.
   - **Return Type**: `boolean`
     - `true` if the move is successfully completed.
     - `false` otherwise.

5. `void print()`
   - **Description**: Displays data related to the realm.
   - **Parameter**: 
   - **Return Type**: `void`

### `YellowRealm` class

- **Package**: `game.engine`
- **Type**: Class
- **Description**: This class is going to represent the realm that has the solar lion.

#### Methods:
1. `int getScore()`
   - **Description**: Returns the current score of this realm.
   - **Return Type**: `int`

2. `void updateScore()`
   - **Description**: Updates the value of the score of this realm according to the lion that has been defeated.
   - **Return Type**: `void`

3. `boolean checkMove(Dice dice, Creature creature)`
   - **Description**: Checks if a move using a specific dice can be used against the lion.
   - **Parameter**: 
     - `dice`: The dice selected by the player.
     - `creature`: The solar lion.
   - **Return Type**: `boolean`
     - `true` if the move is possible.
     - `false` otherwise.

4. `boolean makeMove(Dice dice, Creature creature)`
   - **Description**: Executes an attack on the lion using a specific dice.
   - **Parameter**: 
     - `dice`: The dice selected by the player for the move.
     - `creature`: The solar lion.
   - **Return Type**: `boolean`
     - `true` if the move is successfully completed.
     - `false` otherwise.

5. `void print()`
   - **Description**: Displays data related to the realm.
   - **Parameter**: 
   - **Return Type**: `void`



### `DiceState` class

- **Package**: `game.engine`
- **Type**: Enum Class
- **Description**: This class will contain the different enumerations for the states required for the dice class.


### `Color` class

- **Package**: `game.engine`
- **Type**: Enum Class
- **Description**: This class will contain the different enumerations for colors required for various classes, such as realms and die.

### `PowerState` class

- **Package**: `game.engine`
- **Type**: Enum Class
- **Description**: This class will contain the different enumerations that represent the states of powers like the "Arcane Boost" and "Time Warp".

### `PlayerState` class

- **Package**: `game.engine`
- **Type**: Enum Class
- **Description**: This class will contain the different enumerations that represent the states of the player, such as "Active" and "Passive".

### `Dragon` class

- **Package**: `game.creatures`
- **Type**: class
- **Description**: This class represents the PyroDragon in the game. It extends the Creature class.

#### Methods:

1. `String toString()`
   - **Description**: Outputs a string containing the details of the PyroDragon.
   - **Parameter**:
   - **Return Type**: `String`
     - The String containing the PyroDragon .

2. `void initialization()`
   - **Description**: A method which contains all other initialization methods.
   - **Parameter**:
   - **Return Type**: `void`

3. `void initRewards()`
    - **Description**: A method which initializes the rewards array using the config file.
    - **Parameter**:
    - **Return Type**: `void`

4. `boolean checkValidityOfReward(String reward)`
   - **Description**: A method that checks whether a reward is an actual proper reward.
   - **Parameter**: 
     - `reward`: The reward to be checked.
   - **Return Type**: `boolean`
     - `True` if the reward is a correct reward,
     - `False` otherwise.

5. `void initPointMap()`
    - **Description**: A method that initializes an array called pointMap with the values of the score that should be obtained from every dragon after it has been slain.
    - **Parameter**:
    - **Return Type**: `void`

6. `void initTimeWarpsAndArcaneBoosts ()`
    - **Description**: A method that initializes the time warp and arcane boost array lists.
    - **Parameter**:
    - **Return Type**: `void`

7. `void initPossibleMoves()`
    - **Description**: A method that initializes an arraylist with all current possible moves.
    - **Parameter**:
    - **Return Type**: `void`

8. `DragonNumber getDragonNumber()`
    - **Description**: A method that returns a dragon's Dragon Number.
    - **Parameter**:
    - **Return Type**: `Dragon Number`
      - `Dragon1`: If the dragon is the first dragon.
      - `Dragon2`: If the dragon is the second dragon.
      - `Dragon3`: If the dragon is the third dragon.
      - `Dragon4`: If the dragon is the fourth dragon.

9. `Dragon selectsDragon(int number)`
    - **Description**: A method that returns the dragon that has been selected using the number parameter.
   - **Parameter**:
     - `number`: The number that indicated which dragon should be selected for this attack.        
   - **Return Type**: `Dragon`
     - The dragon that has been selected.

10. `boolean isDead()`
    - **Description**: A method used to know whether a Dragon is dead or not.
    - **Parameter**:
    - **Return Type**: `boolean`
      -`true`: If the dragon is dead,
      -`false`: otherwise. 

11. `String getRewardStringDependingOnIndex(int index)`
    - **Description**: A method that gets a reward as a string based on the index passed as a parameter.
    - **Parameter**:
      - `index`: The index that dictates which reward string should be returned.
    - **Return Type**: `String`
      - A string containing the reward that should be returned.

12. `boolean equals(Object obj)`
    - **Description**: A method used to compare two dragons.
    - **Parameter**:
      - `obj`: An object which contains the dragon that is to be compared to the dragon that this method is called upon.
    - **Return Type**: `boolean`
      - `true`: If the two dragons are equal,
      - `false`: otherwise.

13. `void initNextTimeWarp()`
    - **Description**: A method used to initialize the next time warp.
    - **Parameter**:
    - **Return Type**: `void`

14. `void initNextArcaneBoost()`
    - **Description**: A method used to initialize the next arcane boost.
    - **Parameter**:
    - **Return Type**: `void`

15. `RealmColor decodeLetterToRealmColor (char c)`
    - **Description**: A method used to translate a character into the corresponding realm color.
    - **Parameter**:
      - `c`: The character that is used to determine which realm color should the method return.
    - **Return Type**: `RealmColor`
      - The realm color corresponding to the character c, if there exists one,
      - `null` otherwise.

16. `boolean moveHelper(int targetValue, boolean doMove)`
    - **Description**: A method that contains the common code segment between the makemove and checkmove methods in order to reduce code redundancy.
    - **Parameter**:
      - `targetValue`: The value that the dice being used in this move contains.
      - `doMove`: A boolean which dictates whether the move should actually occur or not. Check move passes this parameter as false, while make move passes it as true.
    - **Return Type**: `boolean`
      - `true`: If the move was successful OR if the move can be applied (for makemove and checkmove respectively),
      - `false`: otherwise.

17. `String changeToString(Integer integer)`
    - **Description**: A method that helps in printing the scoresheet.
    - **Parameter**:
      - `integer`: The value of the dragon part that this method is called upon.
    - **Return Type**: `String`
      - `"" + value`: If the integer passed is not `null`,
      - `"X "`: otherwise.

18. `String getFirstRowRewardString()`
    - **Description**: A method that returns a string containing the reward obtained from the first row.
    - **Parameter**:
    - **Return Type**: `String`
      - A string containing the initials of the two major words that make up the reward (for example, RedBonus becomes RB) depending on whether the conditions for this reward have been met,
      - `"X "`: otherwise.

19. `String getSecondRowRewardString()`
    - **Description**: A method that returns a string containing the reward obtained from the second row.
    - **Parameter**:
    - **Return Type**: `String`
      - A string containing the initials of the two major words that make up the reward (for example, RedBonus becomes RB) depending on whether the conditions for this reward have been met,
      - `"X "`: otherwise.

20. `String getThirdRowRewardString()`
    - **Description**: A method that returns a string containing the reward obtained from the third row.
    - **Parameter**:
    - **Return Type**: `String`
      - A string containing the initials of the two major words that make up the reward (for example, RedBonus becomes RB) depending on whether the conditions for this reward have been met,
      - `"X "`: otherwise.

21. `String getFourthRowRewardString()`
    - **Description**: A method that returns a string containing the reward obtained from the fourth row.
    - **Parameter**:
    - **Return Type**: `String`
      - A string containing the initials of the two major words that make up the reward (for example, RedBonus becomes RB) depending on whether the conditions for this reward have been met,
      - `"X "`: otherwise.

22. `String getCornerRewardString()`
    - **Description**: A method that returns a string containing the reward obtained from the corner/diagonal.
    - **Parameter**:
    - **Return Type**: `String`
      - A string containing the initials of the two major words that make up the reward (for example, RedBonus becomes RB) depending on whether the conditions for this reward have been met,
      - `"X "`: otherwise.

23. `String encode (String reward)`
    - **Description**: A method that encodes a reward by changing it into a string that contains the initials of the two major words that make up the reward (for example, RedBonus becomes RB).
    - **Parameter**:
      - `reward`: The string that is to be encoded.
    - **Return Type**: `String`
      - The string that contains the encoded version of the reward.

### `GaiaGaurdian` class

- **Package**: `game.creatures`
- **Type**: class
- **Description**: This class represents the GaiaGaurdian in the game.

#### Methods:

1. `boolean makeMove(Dice dice, GaiaGaurdian gaiaGaurdian)`
   - **Description**: Executes an attack on a GaiaGaurdian.
   - **Parameter**: 
     - `dice`: The dice selected by the active player for the move.
     - `creature`: The GaiaGaurdian that the move is against.
   - **Return Type**: `boolean`
     - `true` if the move is successfully completed,
     - `false` otherwise.

2. `boolean checkMove(Dice dice,  GaiaGaurdian gaiaGaurdian)`
   - **Description**: Check if a move on a GaiaGaurdian is possible.
   - **Parameter**: 
     - `dice`: The dice selected by the active player to check for the move.
     - `creature`: The GaiaGaurdian that the active player wants to check if the move is possible against.
   - **Return Type**: `boolean`
     - `True` if the move is possible,
     - `False` otherwise.

3. `String toString()`
   - **Description**: Outputs a string containing the details of the GaiaGaurdian.
   - **Return Type**: `String`
     - The String containing the GaiaGaurdian.


### `HydraSerpent` class

- **Package**: `game.creatures`
- **Type**: class
- **Description**: This class represents the HydraSerpent in the game.

#### Methods:

1. `boolean makeMove(Dice dice, HydraSerpent hydraSerpent)`
   - **Description**: Executes an attack on a HydraSerpent.
   - **Parameter**: 
     - `dice`: The dice selected by the active player for the move.
     - `creature`: The target creature that the move is against.
   - **Return Type**: `boolean`
     - `true` if the move is successfully completed,
     - `false` otherwise.

2. `boolean checkMove(Dice dice,  HydraSerpent hydraSerpent)`
   - **Description**: Check if a move on a HydraSerpent is possible.
   - **Parameter**: 
     - `dice`: The dice selected by the active player to check for the move.
     - `creature`: The HydraSerpent that the active player wants to check if the move is possible against.
   - **Return Type**: `boolean`
     - `True` if the move is possible,
     - `False` otherwise.

3. `String toString()`
   - **Description**: Outputs a string containing the details of the HydraSerpent.
   - **Return Type**: `String`
     - The String containing the HydraSerpent.
  

### `Phoenix` class

- **Package**: `game.creatures`
- **Type**: class
- **Description**: This class represents the MajesticPhoenix in the game.

#### Methods:

1. `public Phoenix()`
   - **Description**: Class constructor that initialises an instance of Phoenix.

2. `public void initRewards()`
   - **Description**: Initialises the arcaneBoosts and the timeWarps ArrayLists depending on how many of each was found in the configuration file.
   - **Parameter**:
   - **Return Type**: `void`

3. `public void populateRewardLocationFromConfigFile()`
   - **Description**: Reads the game configuration from the respective .properties file, and if the file is bugged, uses a default configuration,   either configurations are then loaded into a HashMap for later use.
   - **Parameter**:
   - **Return Type**: `void`

4. `public void populateMappedRewardLocation()`
  - **Description**: Uses the rewardLocation HashMap initialised in the populateRewardLocationFromConfigFile() method to reverse the mapping in the HashMap with the Key-Value pair being <String, ArrayList> to a String Array where the index of a reward is its respective location. This is done for easier and faster access of the reward location later on.
  - **Parameter**:
  - **Return Type**: `void`

5. `public void updateAllPossibleMoves()`
  - **Description**: Updates the allPossibleMoves ArrayList to include all the -new- possible moves a player could do in the Magenta realm.
  - **Parameter**:
  - **Return Type**: `void`

6. `public String getRewardString(String rewardName, int n)`
  - **Description**: Returns a string indicating how the reward should be displayed in the score sheet.
  - **Parameter**:
    - `rewardName`: The full reward name.
    - `n`: The number of Phoenixes killed that should grant the reward.
  - **Return Type**: `String`
    - The string indicating how the reward should be displayed in the score sheet.

### `Lion` class

- **Package**: `game.creatures`
- **Type**: class
- **Description**: This class represents the SolarLion in the game.

#### Methods:

0. `public Lion()`
  - **Description**: class constructor that initializes a lion instance.

1. `public boolean makeMove(Dice dice)`
   - **Description**: executes an attack on a SolarLion.
   - **Parameter**: 
     - `dice`: the dice selected by the player for the move.
   - **Return Type**: `boolean`
     - `true` if the move is successfully completed,
     - `false` otherwise.

2. `public boolean checkMove(Dice dice)`
   - **Description**: checks if a move on a lion is possible.
   - **Parameter**: 
     - `dice`: the dice selected by the player to check for the move.
   - **Return Type**: `boolean`
     - `True` if the move is possible,
     - `False` otherwise.

3. `public ArrayList<TimeWarp> getAllTimeWarps()`
  - **Description**: a method that returns all acquired timewarps.
  - **Return Type**: `ArrayList<TimeWarp>`
        - an arraylist with all the timewarps acquired from the yellow realm.

4. `public ArrayList<ArcaneBoost> getAllArcaneBoosts()`
  - **Description**: a method that returns all acquired arcaneboosts.
  - **Return Type**: `ArrayList<ArcaneBoost>`
        -an arraylist with all the arcaneboosts acquired from the yellow realm.

5. `public int[] getLions()`
  - **Description**: a method that returns the scores of all lions
  - **Return Type**: `int[]`
        - an array of all the scores of all the lions.

6. `private void setLions(int[] lions)`
  - **Description**: a method that sets the scores of the lions in the realm. 
  - **Parameter**:
        - `lions`: an array that has the scores of the lions.
  - **Return Type**: `void`

7. `private void initLions()`
  - **Description**: a method that intializes the scores of all the lions.
  - **Return Type**: `void`

8. `private void updateLions(Dice dice)`
  - **Description**: a method that updates the scores of the lions after making a successful move.
  - **Parameter**: 
        - `dice`: the dice with which you attack.
  - **Return Type**: `void`

9. `public int getDeadLions()`
  - **Description**: a method that returns the number of dead lions.
  - **Return Type**: `int`
        - the number of dead lions in the yellow realm.

10. `private void setDeadLions (int deadLions)`
  - **Description**: a method that sets the number of dead lions.
  - **Parameter**: 
        - `deadLions`: the number of the dead lions to which you wanna set.
  - **Return Type**: `void`

11. `private void updateDeadLions()`
  - **Description**: a method that updates the number of dead lions after making a move.
  - **Return Type**: `void`

12. `public int getScore()`
  - **Description**: a method that returns the total score of the yellow realm.
  - **Return Type**: `int`
        - the total score of the yellow realm.

13. `private int calculateScore(Dice dice)`
  - **Description**: a method that calculates the actual score for a given dice.
  - **Parameter** :
        -`dice`: the dice for which we want to calculate the score.
  - **Return Type**: `int`
        - the actual score for a given dice depending on the multiplier at that position.

14. `private void updateScore(Dice dice)`
  - **Description**: a method that updates the total score of the yellow realm.
  - **Parameter**:
      - `dice`: the dice with which we add to the total score of the realm.
  - **Return Type**: `void`

15. `public String getScoreSheet()`
  - **Description**: a method the returns the scoresheet for the yellow realm.
  - **Return Type**: `String`
        - the partial scoresheet for the yellow realm.

16. `private void setScoreSheet(String scoreSheet)`
  - **Description**: a method that sets the scoresheet for the yellow realm.
  - **Return Type**: `void`

17. `private void initScoreSheet()`
  - **Description**: a method that intializes an empty scoresheet for the yellow realm.
  - **Return Type**: `void`

18. `public int getElementalCrest()`
  - **Description**: a method that returns the number of elemental crests in the yellow realm.
  - **Return Type**: `int`
        - the number of elemental crests obtained in the yellow realm.

19. `public ArrayList<Move> getAllPossibleMoves()`
  - **Description**: a method that returns all possible moves in the yellow realm.
  - **Return Type**: `ArrayList<Move>`
        - an arraylist of all possible moves in the yellow realm.

20. `public void populateRewardLocationFromConfigFile()`
  - **Description**: a method that reads the game configuration from the respective .properties file, and if the file is bugged, uses the default configuration,   then loads the configuration into a hashmap for later use.
  - **Return Type**: `void`

21. `public void populateMappedRewardLocation()`
  - **Description**: a method that uses the rewardLocation hashmap initialized in the populateRewardLocationFromConfigFile() method to reverse the mapping in the hashmap with the key-value pair being <String, ArrayList> to a string array where the index of a reward is its respective location. this is done for easier and faster access of the reward location later on.
  - **Return Type**: `void`

22. `public String getMultiplier(int n)`
  - **Description**: a method that returns how the multiplier should look like in the scoresheet.
  - **Parameter**: 
        - `n` the number of lions killed that should use the multiplier.
  - **Return Type**: `String`
        - the string indicating how the multiplier should be displayed in the score sheet.

23. `public String getRedBonusString(int n)`
  - **Description**: a method that returns how the red bonus should look like in the scoresheet.
  - **Parameter**: 
        - `n` the number of lions killed that should grant the red bonus.
  - **Return Type**: `String`
        - the string indicating how the red bonus should be displayed in the score sheet.

24. `public String getGreenBonusString(int n)`
  - **Description**: a method that returns how the green bonus should look like in the scoresheet.
  - **Parameter**: 
        - `n` the number of lions killed that should grant the green bonus.
  - **Return Type**: `String`
        - the string indicating how the green bonus should be displayed in the score sheet.

25. `public String getBlueBonusString(int n)`
  - **Description**: a method that returns how the blue bonus should look like in the scoresheet.
  - **Parameter**: 
        - `n` the number of lions killed that should grant the blue bonus.
  - **Return Type**: `String` 
        - the string indicating how the blue bonus should be displayed in the score sheet.

26. `public String getMagentaBonusString(int n)`
  - **Description**: a method that returns how the magenta bonus should look like in the scoresheet.
  - **Parameter** 
        - `n` the number of lions killed that should grant the magenta bonus.
  - **Return Type**: `String`
        - the string indicating how the magenta bonus should be displayed in the score sheet.

27. `public String getYellowBonusString(int n)`
  - **Description**: a method that returns how the yellow bonus should look like in the scoresheet.
  - **Parameter**: 
        - `n` the number of lions killed that should grant the yellow bonus.
  - **Return Type**: `String`
        - the string indicating how the yellow bonus should be displayed in the score sheet.

28. `public String getEssenceBonusString(int n)`
  - **Description**: a method that returns how the essence bonus should look like in the scoresheet.
  - **Parameter**: 
        - `n` the number of lions killed that should grant the essence bonus.
  - **Return Type**: `String`
          - the string indicating how the essence bonus should be displayed in the score sheet.

29. `public String getElementalCrestString(int n)`
  - **Description**: a method that returns how the elemental crest should look like in the scoresheet.
  - **Parameter**: 
        - `n` the number of lions killed that should grant the elemental crest reward.
  - **Return Type**: `String`
        - the string indicating how the elemental crest should be displayed in the score sheet.

30. `public String getArcaneBoostString(int n)`
  - **Description**: a method that returns how the arcaneboost should look like in the scoresheet.
  - **Parameter**: 
        - `n` the number of lions killed that should grant the arcaneboost reward.
  - **Return Type**: `String`
        - the string indicating how the arcane boost should be displayed in the score sheet.

31. `public String getTimeWarpString(int n)`
  - **Description**: a method that returns how the timewarp should look like in the scoresheet.
  - **Parameter**: 
        - `n` the number of lions killed that should grant the timewarp reward.
  - **Return Type**: `String`
        - the string indicating how the time warp should be displayed in the score sheet.


## Interfaces

### `Creature` interface

- **Package**: `game.creatures`
- **Type**: Interface
- **Description**: This interface represents the creatures in the game.

#### Methods:

1. `boolean makeMove(Dice dice, Creature creature)`
   - **Description**: Executes an attack on a specific creature.
   - **Parameter**: 
     - `dice`: The dice selected by the active player for the move.
     - `creature`: The target creature that the move is against.
   - **Return Type**: `boolean`
     - `true` if the move is successfully completed,
     - `false` otherwise.

2. `boolean checkMove(Dice dice, Creature creature)`
   - **Description**: Check if a move on a specific creature is possible.
   - **Parameter**: 
     - `dice`: The dice selected by the active player to check for the move.
     - `creature`: The target creature that the active player wants to check if the move is possible against.
   - **Return Type**: `boolean`
     - `True` if the move is possible,
     - `False` otherwise.

3. `String toString()`
   - **Description**: Outputs a string containing the details of the creature.
   - **Return Type**: `String`
     - The String containing the creature data.
