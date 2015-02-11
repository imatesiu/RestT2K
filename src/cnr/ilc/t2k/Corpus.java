package cnr.ilc.t2k;

import java.io.File;



import javax.json.JsonObject;






public class Corpus {

	private String corpusName = null;
	private File file = null;
	private int id = 0;
	private Language language = null;
	private String Status = null;
	private String Corpus_file = null;

	private Part_of_Speech part_of_speech = null;

	private Term_Extraction term_extraction = null;

	private Term_Extraction_Indexing term_extraction_indexing = null;

	public Corpus(String fName, File file) {
		corpusName = fName;
		this.file = file;
		language = Language.English ;
	}

	public Corpus(String corpusName, File file, Language lang) {
		this.corpusName = corpusName;
		this.file = file;
		this.language = lang;
	}

	public String getCorpusName() {
		return corpusName;
	}

	public int getId() {
		return id;
	}

	public String getStatus() {
		return Status;
	}

	public boolean getStatusBool() {
		if(Status.equals(new String("successful"))){
			return true;
		}
		return false;
	}

	public String getCorpus_file() {
		return Corpus_file;
	}


	public Part_of_Speech getPart_of_speech() {
		return part_of_speech;
	}

	public Term_Extraction getTerm_extraction() {
		return term_extraction;
	}

	public Term_Extraction_Indexing getTerm_extraction_indexing() {
		return term_extraction_indexing;
	}

	public Corpus(JsonObject obj) {

		this.setCorpus(obj);
	}


	public void setCorpus(JsonObject obj) {

		this.Status =  obj.getJsonString("status").getString();
		this.corpusName= obj.getJsonString("name").getString();
		this.id = obj.getJsonNumber("id").intValue();
		String lang =obj.getJsonString("language").getString();

		this.language = Language.get(lang);
		this.Corpus_file = obj.getJsonString("corpus_file").getString();

		part_of_speech = new Part_of_Speech(obj.getJsonObject("part_of_speech"));

		term_extraction = new Term_Extraction(obj.getJsonObject("term_extraction"));

		term_extraction_indexing = new Term_Extraction_Indexing(obj.getJsonObject("term_extraction_indexer"));

	}

	@Override
	public String toString() {
		return "Corpus [corpusName=" + corpusName + ",  id="
				+ id + ", Language=" + language + ", Status=" + Status
				+ ", Corpus_file=" + Corpus_file + ", part_of_speech="
				+ part_of_speech + ", term_extraction=" + term_extraction
				+ ", term_extraction_indexing=" + term_extraction_indexing
				+ "]";
	}






}

