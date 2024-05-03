package game.creatures;

public class Hydra extends Creature{
    // Create two stacks representing the two serpents, and stack that points to the current active serpent.
    private Stack<Integer>() FirstSerpent = new Stack<Integer>();
    FirstSerpent.push(5); FirstSerpent.push(4); FirstSerpent.push(3); FirstSerpent.push(2); FirstSerpent.push(1); 
    private Stack<Integer>() SecondSerpent = new Stack<Integer>();
    SecondSerpent.push(6); SecondSerpent.push(5); SecondSerpent.push(4); SecondSerpent.push(3); SecondSerpent.push(2); SecondSerpent.push(1); 
    private Stack<Integer>() CurrentSerpent;

    // Define array for the score values and an integer for the current score.
    private int[] scores = {1, 3, 6, 10, 15, 21, 28, 36, 45, 55, 66};
    private int score;

    // Constructor that initializes the score to 0 and the serpent to the first serpent with 5 heads.
    public Hydra() {
        this.score = 0;
        CurrentSerpent = FirstSerpent;
    }
}
