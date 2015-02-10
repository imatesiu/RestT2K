package cnr.ilc.t2k;

import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonString;

public class Term_Extraction_Indexing {

	String conll_file = null;

	int id = 0;

	String status = null;

	public String getConll_file() {
		return conll_file;
	}

	public String getStatus() {
		return status;
	}

	public Term_Extraction_Indexing(JsonObject ter) {
		JsonString jstatus = ter.getJsonString("status");
		JsonString ufile = ter.getJsonString("conll_file");
		JsonNumber jid = ter.getJsonNumber("id");

		this.conll_file = ufile.getString();
		this.id = jid.intValue();
		this.status = jstatus.getString();
	}

	@Override
	public String toString() {
		return "Term_Extraction_Indexing [conll_file=" + conll_file + ", id="
				+ id + ", status=" + status + "]";
	}
	
	
	

}
