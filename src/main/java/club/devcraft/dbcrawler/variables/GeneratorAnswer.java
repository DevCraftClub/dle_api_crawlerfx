package club.devcraft.dbcrawler.variables;

import java.util.ArrayList;
import java.util.List;
public class GeneratorAnswer {
	private boolean success = true;
	private List<String> message = new ArrayList<>();

	public GeneratorAnswer() {
	}

	public void addMessage(String message) {
        if(!this.message.contains(message)) this.message.add(message);
    }

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public List<String> getMessage() {
		return message;
	}

	public void setMessage(List<String> message) {
		this.message = message;
	}
}
