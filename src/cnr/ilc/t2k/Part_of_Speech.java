package cnr.ilc.t2k;

import javax.json.*;

public class Part_of_Speech {

	String analysis_file = null;

	int id = 0;

	String status = null;

	public Part_of_Speech(String analysis_file, int id, String status) {

		this.analysis_file = analysis_file;
		this.id = id;
		this.status = status;
	}

	public String getAnalysis_file() {
		return analysis_file;
	}

	public String getStatus() {
		return status;
	}

	public Part_of_Speech(JsonObject pos) {
		//JsonObject pos = obj.getJsonObject("part_of_speech");
		JsonString jstatus = pos.getJsonString("status");
		if(!pos.isNull("analysis_file")){
			JsonString ufile = pos.getJsonString("analysis_file");
			this.analysis_file = ufile.getString();
		}
		JsonNumber jid = pos.getJsonNumber("id");

		
		this.id = jid.intValue();
		this.status = jstatus.getString();
	}

	@Override
	public String toString() {
		return "Part_of_Speech [analysis_file=" + analysis_file + ", id=" + id
				+ ", status=" + status + "]";
	}





}
