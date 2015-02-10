package cnr.ilc.t2k;

import java.io.File;

import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonString;

public class Term_Extraction {
	
	String analysis_file = null;
	
	int id = 0;
	
	String status = null;

	public String getAnalysis_file() {
		return analysis_file;
	}

	public String getStatus() {
		return status;
	}
	
	public Term_Extraction(JsonObject ter) {
		//JsonObject pos = obj.getJsonObject("part_of_speech");
		JsonString jstatus = ter.getJsonString("status");
		JsonString ufile = ter.getJsonString("analysis_file");
		JsonNumber jid = ter.getJsonNumber("id");
		
		this.analysis_file = ufile.getString();
		this.id = jid.intValue();
		this.status = jstatus.getString();
	}

	@Override
	public String toString() {
		return "Term_Extraction [analysis_file=" + analysis_file + ", id=" + id
				+ ", status=" + status + "]";
	}
	
	

}
