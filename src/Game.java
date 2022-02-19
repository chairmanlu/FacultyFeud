import java.util.*;

public class Game{
	private int strikes;
	private int[] scores;//Teams are numbered 0 or 1
	private ArrayList<Question> questions;
	private int curQ;

	public Game(){
		questions = new ArrayList<>();
		curQ = 0;
		strikes = 0;
		scores = new int[2];
	}

	public void assignScore(int team){

	}

	public void addStrike(){

	}
}
