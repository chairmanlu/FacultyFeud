import java.util.*;

public class Question{
	static class Response{
		private String response;
		private int score;

		public Response(String response, int score){
			this.response = response;
			this.score = score;
		}

		public String getResponse(){
			return response;
		}

		public int getScore(){
			return score;
		}
	}

	private String question;
	private ArrayList<Response> responses;

	public Question(String question, ArrayList<Response> responses){
		this.question = question;
		this.responses = responses;
	}

    public Response getResponse(int i){
		return responses.get(i);
	}
}
