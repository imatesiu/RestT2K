package cnr.ilc.t2k;

import java.io.File;

import javax.json.JsonObject;




public class Corpus {

	String corpusName = null;
	File file = null;
	int id = 0;
	String Language = null;
	String Status = null;
	String Corpus_file = null;

	Part_of_Speech part_of_speech = null;

	Term_Extraction term_extraction = null;

	Term_Extraction_Indexing term_extraction_indexing = null;

	public Corpus(String fName, File file) {
		corpusName = fName;
		this.file = file;
		Language = "GB";
	}

	public Corpus(String corpusName, File file, String language) {
		this.corpusName = corpusName;
		this.file = file;
		this.Language = language;
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

		this.Status =  obj.getJsonString("status").getString();
		this.corpusName= obj.getJsonString("name").getString();
		this.id = obj.getJsonNumber("id").intValue();
		this.Language = obj.getJsonString("language").getString();
		this.Corpus_file = obj.getJsonString("corpus_file").getString();

		part_of_speech = new Part_of_Speech(obj.getJsonObject("part_of_speech"));

		term_extraction = new Term_Extraction(obj.getJsonObject("term_extraction"));

		term_extraction_indexing = new Term_Extraction_Indexing(obj.getJsonObject("term_extraction_indexer"));

	}


	public void setCorpus(JsonObject obj) {

		this.Status =  obj.getJsonString("status").getString();
		this.corpusName= obj.getJsonString("name").getString();
		this.id = obj.getJsonNumber("id").intValue();
		this.Language = obj.getJsonString("language").getString();
		this.Corpus_file = obj.getJsonString("corpus_file").getString();

		part_of_speech = new Part_of_Speech(obj.getJsonObject("part_of_speech"));

		term_extraction = new Term_Extraction(obj.getJsonObject("term_extraction"));

		term_extraction_indexing = new Term_Extraction_Indexing(obj.getJsonObject("term_extraction_indexer"));

	}

	@Override
	public String toString() {
		return "Corpus [corpusName=" + corpusName + ", file=" + file + ", id="
				+ id + ", Language=" + Language + ", Status=" + Status
				+ ", Corpus_file=" + Corpus_file + ", part_of_speech="
				+ part_of_speech + ", term_extraction=" + term_extraction
				+ ", term_extraction_indexing=" + term_extraction_indexing
				+ "]";
	}




}

