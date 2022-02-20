import java.util.*;
import java.io.*;
import java.util.Scanner;

public class Game{
	private int strikes;
	private int[] scores;//Teams are numbered 0 or 1
	private ArrayList<Question> questions;
	private int curQ; // index of current question
	private int currentScore; // current question, points gotten from current question
    private int multiplier;

	private String questionFile;

	public Game(String questionFile){
		questionFile = questionFile;
		this.parseQuestionFile();

		curQ = 0;
		strikes = 0;
		scores = new int[2];
		currentScore = 0;
        multiplier = 1;
	}

	private void parseQuestionFile(){
        /*
        questions = new ArrayList<Question>();
        Scanner sc = new Scanner(new File(questionFile));
        while (sc.hasNextLine())  //returns a boolean value
        {
            sc.useDelimiter(",")
            System.out.print(sc.next());  //find and returns the next complete token from this scanner
        }
        sc.close();  //closes the scanner

        while((nextRecord = csvReader.readNext()) != null){
            rezzys = new ArrayList<Response>();
            String q = newRecord[0];
            int len = nextRecord.length() - 1;
            for(int i = 0; i < len/2; i++) {
                rez = newRecord[2*i + 1];
                count = newRecord[2*i + 2];
                Response fun = new Response(rez, count);
                rezzys.append(fun);
            }
            Question zesty = new Question(q, rezzys);
            questions.append(zesty);
        }
        */
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
}
