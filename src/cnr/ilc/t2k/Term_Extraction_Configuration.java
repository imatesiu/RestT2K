package cnr.ilc.t2k;

import javax.json.*;

public class Term_Extraction_Configuration {
	private String name = null;
	private boolean apply_contrast = true;
	private int contrast_corpus = 0;

	private int id = 0;

	private String part_of_speech_tagset_start_term = null;
	private String part_of_speech_tagset_internal_term = null;
	private String part_of_speech_tagset_end_term = null;

	private String ortographic_unit = "LEMMA";

	private int max_term_length = 5;

	private int multiword_threshold = 200;

	private int singleword_threshold = 200;

	private int frequency_threshold = 10;

	private String contrast_algorithm = "C_NC";

	private String language = "GB";

	public Term_Extraction_Configuration(String Name) {
		part_of_speech_tagset_start_term = new String();
		part_of_speech_tagset_internal_term = new String();
		part_of_speech_tagset_end_term = new String();
		name=Name;


	}


	public String getPart_of_speech_tagset_start_term() {
		return part_of_speech_tagset_start_term;
	}

	public void resetPart_of_speech_tagset_start_term() {
		part_of_speech_tagset_start_term =null;
	}

	public void setPart_of_speech_tagset_start_term(Part_of_Speech_Tagset... tags) {
		for(Part_of_Speech_Tagset tag : tags){
			this.part_of_speech_tagset_start_term += tag.toString();
		}
	}

	public String getPart_of_speech_tagset_internal_term() {
		return part_of_speech_tagset_internal_term;
	}

	public void resetPart_of_speech_tagset_internal_term() {
		part_of_speech_tagset_internal_term=null;
	}

	public void setPart_of_speech_tagset_internal_term(Part_of_Speech_Tagset... tags) {
		for(Part_of_Speech_Tagset tag : tags){
			this.part_of_speech_tagset_internal_term += tag.toString();
		}
	}

	public String getPart_of_speech_tagset_end_term() {
		return part_of_speech_tagset_end_term;
	}

	public void resetPart_of_speech_tagset_end_term() {
		part_of_speech_tagset_end_term=null;
	}

	/*	public void setPart_of_speech_tagset_end_term(Part_of_Speech_Tagset tag) {
		this.part_of_speech_tagset_end_term = tag.toString();
	}*/

	public void setPart_of_speech_tagset_end_term(Part_of_Speech_Tagset... tags) {
		for(Part_of_Speech_Tagset tag : tags){
			this.part_of_speech_tagset_end_term += tag.toString();
		}
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setApply_contrast(boolean apply_contrast) {
		this.apply_contrast = apply_contrast;
	}

	public void setContrast_corpus(int contrast_corpus) {
		this.contrast_corpus = contrast_corpus;
	}



	public int getId() {
		return id;
	}


	public void setOrtographic_unit(String ortographic_unit) {
		this.ortographic_unit = ortographic_unit;
	}

	public void setMax_term_length(int max_term_length) {
		this.max_term_length = max_term_length;
	}

	public void setMultiword_threshold(int multiword_threshold) {
		this.multiword_threshold = multiword_threshold;
	}

	public void setSingleword_threshold(int singleword_threshold) {
		this.singleword_threshold = singleword_threshold;
	}

	public void setFrequency_threshold(int frequency_threshold) {
		this.frequency_threshold = frequency_threshold;
	}

	public void setContrast_algorithm(String contrast_algorithm) {
		this.contrast_algorithm = contrast_algorithm;
	}

	public void setLanguage(String language) {
		this.language = language;
	}


	public JsonObject getTerm_Extraction_Configuration() {

		JsonObjectBuilder val = Json.createObjectBuilder()
				.add("name", this.name)
				.add("apply_contrast", this.apply_contrast);
		if(this.contrast_corpus<=0){
			val.add("contrast_corpus",  JsonValue.NULL);
		}else{
			val.add("contrast_corpus",  this.contrast_corpus);
		}	
		val.add("part_of_speech_tagset_start_term",this.part_of_speech_tagset_start_term)
		.add("part_of_speech_tagset_internal_term", this.part_of_speech_tagset_internal_term)
		.add("part_of_speech_tagset_end_term", this.part_of_speech_tagset_end_term)
		.add("ortographic_unit",this.ortographic_unit)
		.add("max_term_length", this.max_term_length)
		.add("multiword_threshold", this.multiword_threshold)
		.add("singleword_threshold",this.singleword_threshold)
		.add("frequency_threshold", this.frequency_threshold)
		.add("language", this.language)
		.add("contrast_algorithm", this.contrast_algorithm);

		JsonObject value = val.build();

		return value;

	}


	public Term_Extraction_Configuration(JsonObject terc) {

		name = terc.getJsonString("name").getString();
		apply_contrast = terc.getBoolean("apply_contrast");
		if(!terc.isNull("contrast_corpus")){
			contrast_corpus = terc.getJsonNumber("contrast_corpus").intValue();
		}
		id = terc.getJsonNumber("id").intValue();

		part_of_speech_tagset_start_term = terc.getJsonString("part_of_speech_tagset_start_term").getString();
		part_of_speech_tagset_internal_term = terc.getJsonString("part_of_speech_tagset_internal_term").getString();
		part_of_speech_tagset_end_term = terc.getJsonString("part_of_speech_tagset_end_term").getString();

		ortographic_unit = terc.getJsonString("ortographic_unit").getString();

		max_term_length = terc.getJsonNumber("max_term_length").intValue();

		multiword_threshold = terc.getJsonNumber("multiword_threshold").intValue();

		singleword_threshold = terc.getJsonNumber("singleword_threshold").intValue();

		frequency_threshold = terc.getJsonNumber("frequency_threshold").intValue();

		contrast_algorithm = terc.getJsonString("contrast_algorithm").getString();;

		language = terc.getJsonString("language").getString();;
	}


	@Override
	public String toString() {
		return "Term_Extraction_Configuration [name=" + name
				+ ", apply_contrast=" + apply_contrast + ", contrast_corpus="
				+ contrast_corpus + ", id=" + id
				+ ", part_of_speech_tagset_start_term="
				+ part_of_speech_tagset_start_term
				+ ", part_of_speech_tagset_internal_term="
				+ part_of_speech_tagset_internal_term
				+ ", part_of_speech_tagset_end_term="
				+ part_of_speech_tagset_end_term + ", ortographic_unit="
				+ ortographic_unit + ", max_term_length=" + max_term_length
				+ ", multiword_threshold=" + multiword_threshold
				+ ", singleword_threshold=" + singleword_threshold
				+ ", frequency_threshold=" + frequency_threshold
				+ ", contrast_algorithm=" + contrast_algorithm + ", language="
				+ language + "]";
	}



}
