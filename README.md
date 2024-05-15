# Dice Realms: Quest for the Elemental Crests Game

This repository contains the code for a Dice Realms: Quest for the Elemental Crests Java-based game. It uses the Maven build system and JUnit4 for testing. Below are instructions on how to get started.

## Setup

1. **Clone the Repository**: Clone this repository to your local machine.
2. **Import into IDE**: Import the project into your preferred Java IDE (e.g., VS Code).
3. **Install Dependencies**: Maven will automatically download the required dependencies. Otherwise, you can manually import them.
   - **Java Version**: The project is configured to be compatible with at least Java 8 (1.8).
   - **Maven**: This project uses Maven (v.3.8.0) for dependency management and build automation.
   - **Encoding**: Ensure your IDE and build tools are configured to use UTF-8 encoding to avoid issues with character representation.
   - **Testing**: Tests are run using JUnit 4.13.2 as specified in the Maven dependencies.
4. **Familiarize yourself with the provided files**:

### **DO NOT** alter all core provided files as per the below list. Any modifications to these files will be reverted, which might disrupt your game’s functionality

- root folder (`/`)
  - [`Grades.md`](/Grades.md)
  - [`README.md`](/README.md)
  - [`TemplateSkeleton.md`](/TemplateSkeleton.md)
- resources folder (`/src/main/resources/`)
  - [`EmptyScoreSheet.txt`](/src/main/resources/EmptyScoreSheet.txt)
  - config folder (`/src/main/resources/config/`)
    - [`RoundsRewards.properties`](/src/main/resources/config/RoundsRewards.properties)
    - [`EmberfallDominionRewards.properties`](/src/main/resources/config/EmberfallDominionRewards.properties)
    - [`TerrasHeartlandRewards.properties`](/src/main/resources/config/TerrasHeartlandRewards.properties)
    - [`TideAbyssRewards.properties`](/src/main/resources/config/TideAbyssRewards.properties)
    - [`MysticalSkyRewards.properties`](/src/main/resources/config/MysticalSkyRewards.properties)
    - [`RadiantSvannaRewards.properties`](/src/main/resources/config/RadiantSvannaRewards.properties)
  - images folder (`/src/main/resources/images/`)
    - [`Project-UML-Diagram.png`](/src/main/resources/images/Project-UML-Diagram.png)
- game folder (`/src/main/java/game/`)
  - [`Main.java`](/src/main/java/game/Main.java)
  - engine folder (`/src/main/java/game/engine/`)
    - [`GameController.java`](/src/main/java/game/engine/GameController.java)
- [test folder](`/src/test/`) (`/src/test/`)
  - All files created under the test folder, however, you are free and highly recommended to add more custom test cases files to ensure proper unit testing of your code.

## Grading

The [Grades.md](/Grades.md) file in your repository will be updated with milestone grades for each team. Individual performance will be assessed during the final evaluation presentation.

---

# Milestone 1: Project Hierarchy and Skeleton

- You are tasked with implementing Dice Realms: Quest for the Elemental Crests game project.
- The project will involve creating various classes and interfaces to handle game logic, user interaction, and testing.
- This milestone focuses on establishing the project hierarchy and skeleton by listing the files, classes, interfaces, and enums you need to implement.

## Submission Deadline

- Submit your project hierarchy and skeleton by pushing an updated `ProjectSkeleton.md` file to the repo root folder before **April 10, 2024**.

## Tasks

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

   - Before the deadline, push your updated `ProjectSkeleton.md` file to the repository root folder to indicate your submission and make a Pull Request titled `Milestone-1-Submission`
   - Since many of you have been working on the main branch directly, which is not recommended. Therefore, you can use the Git-Tag instead of the pull request by tagging your submission commit on the `main` branch with tag `Milestone-1`. Use Git-Tag to tag the commit that is ready for evaluation. If you are unfamiliar with how to use Git-Tag, you can refer to the instructions at the end of this README.

5. **Review and Feedback**:
   - Once the deadline passes, review any feedback provided by the instructor and prepare for the next milestone accordingly.

---

# Milestone 2: Game Engine

## Objective

Develop a fully functional game engine that can be played through the Command Line Interface (CLI). This milestone will focus on implementing the core functionality that allows two players to interact with the game and navigate through different realms, attack creatures and collect crests.

## Submission Deadlines

1. **Check-in Deadline**: 04.05.2024

   This is a binary graded check-in to ensure your project is heading in the correct direction. No content grade, just a submission check, yet mandatory.

2. **Final Submission Deadline**: 15.05.2024

   Deliver a fully functional CLI-based game. This will be graded for passing unit tests, functionality, code quality, and adherence to the project requirements.

## Project Skeleton

You may choose to use your proposed [project skeleton](/ProjectSkeleton.md) or adopt the suggested [template skeleton](/TemplateSkeleton.md). Both approaches are equally valid and should be chosen based on your team’s preference and design strategy.

_**Note:** The provided template skeleton is intended as a guideline to shape your approach. However, it is not fully complete and should not be used as a direct substitute for the Milestone-1 submission._

## Tasks

1. **Implement Defined Classes**:

   - Begin implementing the defined classes within their respective packages based on the chosen project skeleton. If you are collaborating as a team on a single machine, make sure to utilize Git's commit system as much as possible to clearly document each team member's contributions to their specific classes.

2. **Define Custom Exceptions**:

   - Design and develop custom exceptions to enhance the robustness of error handling within your game. By implementing these exceptions, you can ensure that unexpected user inputs or other error conditions are managed gracefully, preventing the game from terminating unexpectedly.

3. **Realms Reward System**:

   - The rewards for each realm are specified in individual files located in the [config folder](/src/main/resources/config/). It is the responsibility of those managing each realm to ensure that the game engine reads these files correctly to distribute rewards accurately. Similarly, the assignment of initial rewards for the rounds is the responsibility of the team coordinator. This setup ensures that rewards are systematically and fairly allocated throughout the game, contributing to a balanced and engaging gameplay experience.

4. **Score Management**:

   - Take a look at the [EmptyScoreSheet.txt](/src/main/resources/EmptyScoreSheet.txt) file located in the resource folder to understand the format for displaying the Score Sheet to players. This should be achieved by utilizing the `toString()` method of the `ScoreSheet` object. Individuals responsible for each realm are tasked with implementing the `toString()` method for their specific creature or realm. The `ScoreSheet` object then combines all these individual representations into a cohesive whole.
   - Additionally, to maintain consistency and ease of management similar to the rewards system, it is recommended to create a configuration file for the scoring system of each realm. This approach ensures that the scoring criteria are clear, structured, and easily adjustable, enhancing the game's transparency and adaptability.

5. **Update the Project Structure & Skeleton**:

   - Prior to submission, ensure that your [ProjectSkeleton.md](/ProjectSkeleton.md) accurately reflects the latest state of your project. This document should provide a comprehensive overview of the current folder and file structure, as well as the organization of packages, classes, and their respective methods. Keeping this document updated is crucial for maintaining a clear and precise record of the project’s development progress.

6. **Review and Feedback**:
   - Once the deadline passes, review any feedback provided by the instructor and prepare for the next milestone accordingly.

## Submission Guidelines

- **Branch Management**: It is recommended to conduct ongoing development on separate branches. Reserve the `main` branch strictly for finalized submissions.
- **Check-in Submission**: As a team, push your current progress to the main branch and tag it as `CheckInMS-2` for evaluation purposes by the given deadline. Failure to correctly tag may result in your submission not being evaluated. If you need guidance on using Git-Tag, please refer to the instructions provided at the end of this README.
- **Final Evaluation**: As a team, push all completed work in the game engine to the main branch and label it `Milestone-2` for evaluation purposes by the given deadline. Failure to correctly tag may result in your submission not being evaluated. If you need guidance on using Git-Tag, please refer to the instructions provided at the end of this README.
- **Feedback Requests**: For interim feedback, initiate a pull request titled "Feedback Request yyyymmdd" (insert the actual date of request). Please allow 1-2 days for the review to be completed. Utilizing feedback requests prior to your final submission can be invaluable for refining your project.
- **Updates and Syncing**: Should there be any updates to the core files by the instructor, your forked repositories will automatically be synchronized with the original repository. Notifications regarding such updates will be communicated via Discord and email.

---

_**Note:** Refer to the provided test files for guidance on implementing the required classes and methods. Reach out to the instructor if you encounter any issues or have questions._

---

# Milestone 3: Game GUI

Develop a fully functional game that can be played through a Graphical User Interface (GUI). This milestone will focus on implementing the UI/UX functionality that allows two players to interact with the game and navigate through different realms, attack creatures and collect crests.

## **Submission Deadlines**

- **Final Deadline**: 02.06.2024
  - Deliver a fully functional GUI-based game that will be graded on unit tests, functionality, code quality, and adherence to the project requirements.

## **General Requirements**

- Ensure the effects of any action performed in the GUI are reflected in the game engine and vice versa.
- Players should be able to view all content at all times without the need to resize the window during gameplay.
- The action currently happening in the game should always be clearly indicated in the GUI.
- Handle all exceptions and input validations robustly. If an exception arises, notify the player and prohibit the erroneous action, prompting for an alternative.
- The game should continue running and not terminate on exceptions.
- Use only JavaFX for the GUI development. The use of SceneBuilder is allowed and encouraged.

## **Game Requirements**

- **Menu Scene**:
  - Options for starting a new game for 2 players or 1 player vs. AI (note if AI is not implemented, notify player that it as WIP).
  - Settings for configuration parameters and an exit button.
- **Game Play Scene**:
  - Display essential player information: Name and Status (active/passive)
  - Display round numbers and their rewards
  - Display designated area for turns and dice selection
  - Display a dynamic tracknig of collected Elemental Crests
  - Display a dynamic tracknig of Arcane Boost powers and their status (active / used)
  - Display a dynamic tracknig of Time Warp powers and their status (active / used)
  - Include a dynamic graphical representation of each player's ScoreSheet in their realm color, detailing hits, rewards, and scoring.
  - Provide a designated area for dice rolling and Forgotten Realm.
- **Game Over Scene**:
  - Show scores per realm, total crests collected, and the total score declaring the winner and protector of Eldoria.

## **GUI Requirements**

- If the design is not immediately intuitive, include a help section or navigation menu for game instructions.
- **Interaction**:
  - Players should be able to select dice, attack regions, and see possible moves highlighted.
  - Include visual or audio cues for each action to guide the player through the game flow.
  - Utilize pop-ups for additional information without affecting the game continuation.
- **Aesthetic Enhancements**:
  - The use of images, animations, and background music is optional but recommended to enhance gameplay experience.

## **AI Requirement**

- If implementing AI, it should be capable of reasoned actions, not random guesses, and aim for a minimum score of 150 points. Incorporating AI grants a 5% bonus point with a competitive edge over other teams.

## Additional Guidelines

- You are free to update the `pom.xml` file to include any additional dependencies that are necessary for your project. However, please be cautious with these changes as they are not recommended without thorough testing. Unintended changes might affect the project build and its dependencies.
- You will begin with `module-info.java` and `DiceRealms.java` as the initial JavaFX files. Feel free to modify these files to suit the needs of your project. They are designed to give you a starting point which you can expand based on your game design requirements.
- FXML files should be added to the `gui` package. This organization helps maintain a clean project structure, making it easier to manage and locate your user interface components.
- CSS files should be stored in the `resources/styles` folder. Organizing your stylesheets in this directory will help separate style from structure and keep your project organized.
- Images files should be stored in the `resources/images` directory.
- Videos files should be stored in the `resources/videos` directory.
- Audio files should be stored in the `resources/sounds` directory.

## **Submission Guidelines**

- As a team, push/merge all completed work to the main branch and tag it `Milestone-3` for evaluation. Incorrect tagging may lead to your submission not being evaluated.
- Refer to the provided instructions at the end of this README for how to tag your submissions correctly.
- Any updates to the core files by the instructor will be synchronized with your forked repositories. Notifications will be communicated via Discord and email.

# Version Control Best Practices

Proper use of version control is critical in managing and tracking the progress of your software project. Below are some best practices for using Git, which will help you maintain a healthy codebase and effective collaboration:

## Regular Commits

- **Commit Often**: Make regular commits to your repository instead of bulk commits. This approach helps in minimizing the risk of losing work and allows for better understanding and tracking of changes.
- **Meaningful Commit Messages**: Write clear and descriptive commit messages that explain why the changes were made. This practice is invaluable for historical tracking and understanding the context of changes.

## Writing and Running Unit Tests

- **Test Early and Often**: Write unit tests alongside your code. Tests are not just for finding bugs but also for ensuring that your code behaves as expected.
- **Run Local Tests Frequently**: Before pushing your changes, run your tests locally to ensure everything works as expected. Use the command:

```bash
mvn test
```

- **Add Custom Tests**: While it's essential to pass the provided tests, creating your own can help cover more scenarios specific to your implementation.

## Creating a Tag on GitHub Website

1. Navigate to the repository on GitHub.
2. Click on the “Releases” label on the right side.
3. Click on the “Create a new release” button.
4. Fill in the “Tag version” field with the desired tag name (e.g., Milestone-1).
5. Optionally, provide a release title and description.
6. Choose the target branch or commit for the tag.
7. Click on the “Publish release” button to create the tag.
