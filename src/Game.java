import java.util.*;

public class Game{
	private int strikes;
	private int[] scores;//Teams are numbered 0 or 1
	private ArrayList<Question> questions;
	private int curQ; // index of current question
	private int currentScore; // current question, points gotten from current question

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
		scores[team] += currentScore;
		currentScore = 0;
	}

	public int currentScore(){
		return currentScore;
	}

	public void addStrike(){
		strike++;
	}

	public int getStrikes(){
		return strikes;
	}

	public Question getCurrentQuestion(){
		return questions[curQ];
	}

	//Returns -1 if no further questions
	public int nextQuestion(){
		curQ++;
		if(curQ >= questions.length()) {
			return -1;
		}
		else {
			return curQ;
		}
	}
}
