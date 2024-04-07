# Dice Realms: Quest for the Elemental Crests Game

This repository contains the skeleton for a Dice Realms: Quest for the Elemental Crests Java-based game. It uses the Maven build system and JUnit4 for testing. Below are instructions on how to get started.

## Setup

1. Clone the Repository: Clone this repository to your local machine.
2. Import into IDE: Import the project into your preferred Java IDE (e.g., VS Code).
3. Install Dependencies: Maven will automatically download the required dependencies. Otherwise, you can manually import it.
4. Familiarize yourself with the provided files:
   - `GameController.java`: Do not make any changes to this file as it serves as the common blueprint for different controllers.
   - `Main.java`: This file contains the main method to start the game.

## Packages

### game.engine

This package contains the core engine components of the game, including the abstract classes and interfaces that define the game's structure and functionality. It serves as the foundation for implementing various game controllers and managing game logic. Additional classes related to game mechanics and control can be added to this package as needed.

### game.dice

The `game.dice` package encompasses classes related to dice functionality within the game. It includes implementations for rolling dice, managing dice states, and handling dice-related actions and interactions.

### game.creatures

In the `game.creatures` package, you'll find classes representing creatures in their corresponding realms; including all necessary features about how to attack them or their current status to update the score sheet accordingly.

### game.collectibles

The `game.collectibles` package contains classes for the various collectible items within the game; such as power-ups, elemental crest, color bonus, or the essence bonus.

### game.gui

The `game.gui` package houses classes related to the graphical user interface (GUI) of the game. This includes components for rendering game graphics, handling user input, and managing the visual presentation of game elements.

### game.exceptions

The `game.exceptions` package provides classes for defining custom exceptions specific to the game. These exceptions help handle error conditions and unexpected situations, providing meaningful feedback to the player or developer.

## Implementation

You are provided with the following files:

Here's the updated README.md reflecting the changes made to the GameController.java:

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

#### Summary of Changes:

- Refactored `getAllPossibleMoves()` to `getAllPossibleMoves(Player player)` to specify the player for whom moves are determined.
- Refactored `getPossibleMoves(Dice dice)` to `getPossibleMovesForADie(Player player, Dice dice)` to get all possible moves for a given die for a given player.
- Added `getPossibleMovesForAvailableDice(Player player)` to get possible moves for all currently rolled dice for a given player.
- Refactored `getPlayer()` to `getActivePlayer()` and `getScoreSheet()` to `getScoreSheet(Player player)` to specify the player for whom information is retrieved.
- Added `getPassivePlayer()` to get the current passive player's information.
- Refactored `getGameScore()` to `getGameScore(Player player)` to specify the player for whom the score is determined.
- Refactored `getTimeWarpPowers()` and `getArcaneBoostPowers()` to accept a `Player` parameter and return an array of powers for that player.
- Refactored `selectDice(Dice dice)` to `selectDice(Dice dice, Player player)` to specify the player who is selecting the die.
- Refactored `makeMove(Dice dice, Creature creature)` to `makeMove(Player player, Move move)` to specify the player and move details for executing a move.

### `Main.java`

- This file contains the main method to start the game.

---

## Milestone 1: Project Hierarchy and Skeleton - Preparation Instructions

- You are tasked with implementing Dice Realms: Quest for the Elemental Crests game project.
- The project will involve creating various classes and interfaces to handle game logic, user interaction, and testing.
- This milestone focuses on establishing the project hierarchy and skeleton by listing the files, classes, interfaces, and enums you need to implement.

### Submission Deadline: April 10, 2024

- Submit your project hierarchy and skeleton by pushing an updated `ProjectSkeleton.md` file to the repo root folder before **April 10, 2024**.

### Tasks

1. **Update the Project Structure**:

   - Review the project structure to understand where to place your implementation files and test files and make modifications where necessary.

2. **Identify Classes to Implement**:

   - Review the provided GameController and identify the classes, interfaces, and enums you need to implement.

3. **Create Project Skeleton**:

   - Update the `ProjectSkeleton.md` file with the following details for each class you plan to implement:
     - **Class Name**
     - **Package**
     - **Type** (Class, Abstract Class, Interface, or Enum)
     - **Description**
     - **Methods** (List of methods with descriptions, parameters, and return types)

4. **Submission**:

   - Before the deadline, push your updated `ProjectSkeleton.md` file to the repository root folder to indicate your submission and make a Pull Request titled `Milestone 1 Submission`

5. **Review and Feedback**:
   - Once the deadline passes, review any feedback provided by the instructor and prepare for the next milestone accordingly.

---

_Note: Refer to the provided test files for guidance on implementing the required classes and methods. Reach out to the instructor if you encounter any issues or have questions._
