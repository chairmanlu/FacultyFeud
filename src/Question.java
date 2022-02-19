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
	
	private ArrayList<Response> responses;

	public Question(){
		responses = new ArrayList<>();
	}
}
