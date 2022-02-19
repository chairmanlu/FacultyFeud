import java.util.*;

public class Game{
	private int strikes;
	private int[] scores;//Teams are numbered 0 or 1
	private ArrayList<Question> questions;
	private int curQ;
	private int currentScore;

	public Game(){
		questions = new ArrayList<>();
		curQ = 0;
		strikes = 0;
		scores = new int[2];
		currentScore = 0;
	}

	public int getScore(int team){
		return scores[team];
	}

	public void assignScore(int team){

	}

	public int currentScore(){
		return currentScore;
	}

	public void addStrike(){

	}

	public int getStrikes(){
		return strikes;
	}

	public Question getCurrentQuestion(){
		return null;
	}

	//Returns -1 if no further questions
	public int nextQuestion(){
		return -1;
	}
}
