# Project Skeleton

## Folder Structure

```
Dice-Realms/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── game/
│   │   │       ├── collectibles/   
│   │   │       │   ├── TimeWarp.java
│   │   │       │   ├── ArcaneBoost.java
│   │   │       │   ├── ColorBonus.java
│   │   │       │
│   │   │       ├── creatures/
│   │   │       │   ├── PyroclassDragon.java
│   │   │       │   ├── GaiaGaurdian.java
│   │   │       │   ├── HydraSerpent.java
│   │   │       │   ├── MajesticPheonix.java
│   │   │       │   ├── SolarLion.java
│   │   │       │   ├── RedRealm.java
│   │   │       │   ├── GreenRealm.java
│   │   │       │   ├── BlueRealm.java
│   │   │       │   ├── MagentaRealm.java
│   │   │       │   └── YellowRealm.java
│   │   │       │
│   │   │       ├── dice/
│   │   │       │   ├── Dice.java
│   │   │       │   └── Move.java
│   │   │       │
│   │   │       ├── engine/
│   │   │       │   ├── GameController.java
│   │   │       │   ├── Player.java
│   │   │       │   ├── Realm.java
│   │   │       │   ├── enum/
│   │   │       │   │  ├── DiceState.java
│   │   │       │   │  ├── Color.java
│   │   │       │   │  ├── PowerState.java
│   │   │       │   │  └── PlayerState.java
│   │   │       │   └── interface/
│   │   │       │       └── Creature.java
│   │   │       │   
│   │   │       ├── exceptions/
│   │   │       │
│   │   │       ├── gui/
│   │   │       │
│   │   │       └── Main.java
│   │   │         
│   │   └── resources/
│   │       ├── images/
│   │       └── config/
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
└── README.md
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
  
### `TimeWarp` class

- **Package**: `game.collectibles`
- **Type**:  Class
- **Description**: This class represents the Time Warp boost which allows the player to reroll the die.
#### Methods:

1. `void setState(PowerState)`
   - **Parameters**: 
     - The PowerState value to be changed to
   - **Description**: changes the state of the TimeWarp to the value passed in to the function.
2. `PowerState getUsed()`
   - **Description**: returns the state of the TimeWarp
   - **Return Type**: `PowerState`
     - `Returns the current state of the the Time Warp represented in the enum: PowerState.` 

### `ArcaneBoost` class

- **Package**: `game.collectibles`
- **Type**:  Class
- **Description**: This class represents the Arcane Boost which helps player to have an extra round.

#### Methods:
1. `int getCounter()`
   - **Description**: returns  the number of availabe Arcane boost a player has
   - **Return Type**: `int`
     - `number of active Arcane boost a player has` 
2. `int getTotal()`
   - **Description**: returns all Arcane boost including the availabe and the used  ones
   - **Return Type**: `int`
     - `returns all Arcane boost including the availabe and the used  ones`.
3. `void getUsed()`
   - **Description**: returns all the Arcane boost which the player had already used
   - **Return Type**: `int`
     - `returns all the Arcane boost which the player had already used` 


### `Dice` class

- **Package**: `game.dice`
- **Type**: Class
- **Description**: This class represents the dice to be rolled and used in the game 

#### Methods:

1. `Dice rollDice()`
   - **Description**: returns a dice with a given color and a random number between 1 and 6 
   - **Return Type**: `Dice`
     - `Dice` object representing the dice rolled.
2. `int getValue()`
   - **Description**: returns the numerical value represented by the dice
   - **Return Type**: `int`
     - The number that the dice has been rolled on
3. `Color getColor()`
   - **Description**: returns the color of the dice 
   - **Return Type**: `Color`
     - The color represented by the dice.


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
   - **Description**: returns a dice with a given color and a random number between 1 and 6 
   - **Return Type**: `Creature`
     - The creature that makes up the move 
     - 
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

### `ScoreSheet` class

  - **Package**: `game.engine`
  - **Type**: Class
  - **Description**: This class shows the scoresheet including the details of the scores of each realm including which monsters were killed and the number of collectibles for the current active player.

#### Methods:
1. `void printScoreSheet()`
    -**Description**: prints out the entire score sheet for the current active player including the number of each collectible and the specific scores of each realm and the total score.
    -**Return Type**: `void`

2. `void updateScore()`
    -**Description**: updates the score of the current active player after making a valid move in both the respective realm and the total score.
    -**Return Type**: `vid`

3. `void printCollectible()`
    **Description**: prints out all the collectibles for the current active player.
    **Return Type**: `void`

4. `void printRealms()`
    **Description**: prints out the details of all the realms including which monsters were killed and which collectibles were acquired.
    **Return Type**: `void`

### `GameStatus` class

- **Package**: `game.engine`
- **Type**: Class
- **Description**: This class is going to show the turns and rounds information for the current active player.

#### Methods:
1. `void printTurn()`
    **Description**: prints the turn information for the current active player
    **Return Type**: void

2. `void printRound()`
    **Description**: prints the round information for the current active player
    **Return Type**: void

3. `void printGameStatus()`
    **Description**: prints the turn and round information for the current active player
    **Return Type**: void

### `GameScore` class
- **Package**: `game.engine`
- **Type**: Class
- **Description**: This class shows current score of the game including the scores of each realm, the number of elemental crests, and the total score for the current active player.

#### Methods:
1. `void printGameScore()`
    **Description**: prints the current score of the game including the scores of each realm, the number of elemental crests, and the total score for the current active player.
    **Return Type**: void

2. `void updateGameScore()`
    **Description**: updates the current score of a realm after making a valid move in it, then updates the total score and the number of elemental crests for the current active player.
    **Return Type**: void    

### `Player` class
- **Package**: `game.engine`
- **Type**: Class
- **Description**: This class will contain information regarding the player.

### Methods:
1. `getPlayerState()`
    **Description**: Returns the player state for the current player.
    **Return Type**: PlayerState

2. `setPlayerState(PlayerState playerState)`
    **Description**: Sets the player state for the current player to the input state.
    **Parameters**:
        `playerState`: The PlayerState to be changed to.
    **Return Type**: Void
3. `HashMap<Dice> getDiceUsed()`
    **Description**: Used to get the hashmap container having the dice used by the player.
    **Return Type**: Hashmap with the dice used by the player.
4. `void addDiceUsed(Dice dice)`
    **Description**: Used to add dice to the hashmap container having the dice used by the player.
    **Return Type**: Void


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