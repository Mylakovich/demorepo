package game;

import java.util.ArrayList;
import java.util.Scanner;

public class App {

	// declare variables

	// the running total of the players points
	int points;

	// the number of the turn displayed to the player
	int currentTurn;

	// the number of turns remaining before judgement
	int remainingTurns;

	// the total number of points the player must meet or exceed by the end of
	// the round in order to survive
	int threshold;

	// the starting number of point of the threshold
	int startingDifficulty;

	// the growth factor of the threshold each time the player succeeds
	double diffGain;

	// the number of turns before which the player must meet the threshold
	int roundLength;

	// list of options presented to the player
	// built from the initializer
	ArrayList<Option> optionList;

	// text input scanner
	Scanner s = new Scanner(System.in);

	// constructor method
	public App(int startingDifficulty, double diffGain, int roundLength) {

		this.startingDifficulty = startingDifficulty;
		this.diffGain = diffGain;
		this.roundLength = roundLength;

		initialize();
	}

	// overloaded constructor method with default values
	public App() {
		this(100, 1.1, 10);
	}

	// sets up initial conditions for the game
	public void initialize() {

		points = 0;
		currentTurn = 0;
		remainingTurns = roundLength ;

		threshold = startingDifficulty;

		optionList = new ArrayList<Option>();

		optionList.add(createLowRisk());
		optionList.add(createHighRisk());
	}

	// an example option, giving a 50% chance to gain 25 points
	// grows by 3 each time it succeeds
	private Option createHighRisk() {
		Option output = new Option("High Risk", 0.5, 25, 3);
		return output;
	}

	// a guaranteed 10 points
	// grows by 1 each time used
	private Option createLowRisk() {
		Option output = new Option("Low Risk", 1, 10, 1);
		return output;
	}

	private void displayScore() {
		System.out.println("Turn " + currentTurn + " - Remaining: "
				+ remainingTurns + " - Points: " + points + "/" + threshold);
	}

	private void displayOptions() {
		for (int x = 0; x < optionList.size(); x++) {
			System.out.println((x + 1) + ". " + optionList.get(x).toString());
		}
	}

	private void askOptions() {
		int input = s.nextInt();

		switch (input) {
		case 1:

			points += optionList.get(0).attempt();

			break;
		case 2:
			points += optionList.get(1).attempt();

			break;
		default:
			System.out.println("Invalid");
			askOptions();
			break;
		}

	}

	private void nextTurn() {
		currentTurn++;
		remainingTurns--;
	}

	private void nextRound() {
		points = 0;
		currentTurn = 0;
		remainingTurns = roundLength;
		threshold = (int) (threshold * diffGain);
	}
	
	private void run(){
		boolean lose = false;

		while (!lose) {
			while (currentTurn < roundLength) {
				nextTurn();
				displayScore();
				displayOptions();
				askOptions();
			}
			
			// judgement
			if (points < threshold) {
				System.out.println("You don't have enough points.");
				lose = true;
			} else {
				System.out.println("You have enough points to keep going.");
				System.out.println("Threshold increased!.");
				nextRound();
			}
		}
		
		System.out.println("GAME OVER");
		System.out.println("Final score: " +  points);
	}

	public static void main(String[] args) {

		App app = new App();
		
		app.run();
	}

}
