import java.util.*;
import java.io.*;
import java.util.Scanner;

public class Game{
	private int strikes;
	private int[] scores;//Teams are numbered 0 or 1
	private String[] teamNames;
	private ArrayList<Question> questions;
	private int curQ; // index of current question
	private int currentScore; // current question, points gotten from current question
	private int multiplier;
	private boolean[] revealed;
	private boolean scoresAssigned;
	private boolean questionRevealed;

	private String questionFile;

	public Game(String questionFile) throws FileNotFoundException, IOException {
		questions = new ArrayList<Question>();
        teamNames = new String[2];
		Scanner sc = new Scanner(new File(questionFile));
		boolean firstLine = true;
		while(sc.hasNextLine()){
			ArrayList<Question.Response> responses = new ArrayList<Question.Response>();
			String line = sc.nextLine();
			String[] tokens = line.split(",");
			if(firstLine){
			// first line are team names
				teamNames[0] = tokens[0];
				teamNames[1] = tokens[1];
				firstLine = false;
			}
			else{
			// rest of lines are question, response1, score1, response2, score2, ...
				for(int i=1;i<tokens.length;i+=2){
					responses.add(new Question.Response(tokens[i],Integer.parseInt(tokens[i+1])));
				}
				Question q = new Question(tokens[0],responses);
				questions.add(q);
			}
		}
		sc.close();
		curQ = 0;
		strikes = 0;
		scores = new int[2];
		currentScore = 0;
		multiplier = 1;
		revealed = new boolean[8];
		questionRevealed = false;
	}

	public int getScore(int team){
		return scores[team];
	}

	public void assignScore(int team){
		scores[team] += (currentScore * multiplier);
		currentScore = 0;
		scoresAssigned = true;
	}

	public int currentScore(){
		return currentScore * multiplier;
	}

	// returns true if it was strike 3
	public boolean addStrike(){
		if(strikes < 3){
			strikes++;
		}
		return strikes == 3;
	}

	public int getStrikes(){
		return strikes;
	}

	public Question getCurrentQuestion(){
		return questions.get(curQ);
	}

	//Returns -1 if no further questions
	public int nextQuestion(){
		curQ++;
		strikes = 0;
		currentScore = 0;
		scoresAssigned = false;
		questionRevealed = false;

		for(int i=0;i<revealed.length;i++){
			revealed[i] = false;
		}

		if(curQ >= questions.size()) {
			return -1;
		}
		else {
			return curQ;
		}
	}

	public boolean isRevealed(int i){
		return revealed[i];
	}

	public boolean scoringAllowed(){
		return !scoresAssigned;
	}

	public void setMultiplier(int m){
		multiplier = m;
	}

	public void revealQuestion(){
		questionRevealed = true;
	}

	public boolean questionRevealed(){
		return questionRevealed;
	}

	public void revealResponse(int i){
		if(!scoresAssigned && strikes < 3){
			Question rev = questions.get(curQ);
			Question.Response res = rev.getResponse(i);
			int points = res.getScore();
			currentScore = currentScore + points;
		}
		revealed[i] = true;
	}

	public String getTeamName(int team){
		return teamNames[team];
	}
}
