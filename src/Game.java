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


	private String questionFile;

	public Game(String questionFile) throws FileNotFoundException, IOException {
//		BufferedReader reader = new BufferedReader(new FileReader(questionFile));
//
//		// read file line by line
//		String line = null;
//		Scanner scanner = null;
//		int index = 0;
		questions = new ArrayList<Question>();
//
//		while ((line = reader.readLine()) != null) {
//			ArrayList<Question.Response> rezzys = new ArrayList<Question.Response>();
//			String q = "";
//			scanner = new Scanner(line);
//			scanner.useDelimiter(",");
//			int count = 0;
//			while (scanner.hasNext()) {
//				if(count == 0){
//					q = scanner.next();
//					count++;
//				} else {
//					String rez = scanner.next();
//					int points = Integer.parseInt(scanner.next());
//					Question.Response fun = new Question.Response(rez, points);
//					rezzys.add(fun);
//					count++;
//				}
//			}
//			Question zesty = new Question(q, rezzys);
//			questions.add(zesty);
//		}
//
//		//close reader
//		reader.close();

		Scanner sc = new Scanner(new File(questionFile));
		ArrayList<Question.Response> responses = new ArrayList<Question.Response>();
		boolean firstLine = true;
		while(sc.hasNextLine()){
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
	}

	public int getScore(int team){
		return scores[team];
	}

	public void assignScore(int team){
		scores[team] += (currentScore * multiplier);
		currentScore = 0;
	}

	public int currentScore(){
		return currentScore * multiplier;
	}

	// returns true if it was strike 3
	public boolean addStrike(){
		strikes++;
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
		if(curQ >= questions.size()) {
			return -1;
		}
		else {
			return curQ;
		}
	}

	public void setMultiplier(int m){
		multiplier = m;
	}

	public void revealResponse(int i){
		Question rev = questions.get(curQ);
		Question.Response res = rev.getResponse(i);
		int points = res.getScore();
		currentScore = currentScore + points;
	}

	public String getTeamName(int team){
		return teamNames[team];
	}
}
