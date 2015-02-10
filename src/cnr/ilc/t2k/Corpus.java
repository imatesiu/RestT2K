package cnr.ilc.t2k;

import java.io.File;



public class Corpus {

	String corpusName = null;
	File file = null;
	int id = 0;
	String Language = null;
	String Status = null;
	String Corpus_file = null;
	
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
	
	
	
}

