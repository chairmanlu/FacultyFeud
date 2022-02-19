import java.util.*;

public class Question{
	static class Response{
		String response;
		int score;

		public Response(String response, int score){
			this.response = response;
			this.score = score;
		}
	}
	
	private String question;
	private ArrayList<Response> responses;

	public Question(String question, ArrayList<Response> responses){
		question = question;
		responses = responses;
	}
}
