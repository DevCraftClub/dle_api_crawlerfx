package club.devcraft.dbcrawler.apiObject;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ProtocolProfileBehavior {
	@JsonProperty("disableBodyPruning")
	@JsonAnyGetter
	private boolean disableBodyPruning;

	public boolean isDisableBodyPruning() {
		return disableBodyPruning;
	}

	public void setDisableBodyPruning(boolean disableBodyPruning) {
		this.disableBodyPruning = disableBodyPruning;
	}
}
