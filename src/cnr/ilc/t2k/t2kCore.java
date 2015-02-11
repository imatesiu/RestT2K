package cnr.ilc.t2k;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import java.util.List;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;


public class t2kCore {


	private Corpus corpus = null;
	private CredentialsProvider provider=null;


	/**
	 * This constructor is used indicate user e password of
	 * t2k 
	 * @param user String of user name
	 * @param pass String of password
	 */
	public t2kCore(String user, String pass) {

		provider = new BasicCredentialsProvider();
		UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(user, pass);
		provider.setCredentials(AuthScope.ANY, credentials);
	}




	public Corpus getCorpus() {
		return corpus;
	}

	/**
	 * Download existing Corpus
	 * @param id interger 
	 * @return Corpus Class
	 */
	public Corpus setCorpus(int id){
		
		String url = "http://t2k.italianlp.it/rest/corpus/"+id;

		try {
			HttpGet httpget = new HttpGet(url);

			HttpClient client = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();

			HttpResponse response = client.execute(httpget);
			System.out.println("----------------------------------------");
			System.out.println(response.getStatusLine());
			HttpEntity resEntity = response.getEntity();

			JsonReader rdr = Json.createReader(resEntity.getContent());
			JsonObject obj = rdr.readObject();

			corpus = new Corpus(obj);

			return corpus;

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
		
	}

	/**
	 * Creation of the New Corpus 
	 * @param file File name with text
	 * @param corpusname name of corpus
	 * @param lang Language of corpus
	 */
	public void executeNewCorpus(File file, String corpusname, Language lang){

		String url = "http://t2k.italianlp.it/rest/new_corpus";
		try {
			String boundary = "------------------------" + System.currentTimeMillis();
			HttpPost httpPost = new HttpPost(url);
			MultipartEntityBuilder builder = MultipartEntityBuilder.create();  
			builder.setBoundary(boundary);
			//builder.setMode(HttpMultipartMode.STRICT);
			builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

			ContentType contenttype = ContentType.create("text/plain");


			httpPost.addHeader("Accept", "application/json");
			httpPost.addHeader("Content-Type","multipart/form-data; boundary="+boundary);

			builder.addTextBody("name", corpusname,contenttype);

			builder.addTextBody("language", lang.toString(),contenttype);


			builder.addBinaryBody("corpus_file", file, contenttype, file.getName());

			HttpEntity entity = builder.build();


			httpPost.setEntity(entity);


			HttpClient client = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();


			HttpResponse response = client.execute(httpPost);


			System.out.println("----------------------------------------");
			System.out.println(response.getStatusLine());
			HttpEntity resEntity = response.getEntity();

			JsonReader rdr = Json.createReader(resEntity.getContent());
			JsonObject obj = rdr.readObject();

			corpus = new Corpus(obj);

			System.out.println(corpus);





		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}






	}

	/**
	 * Execute Part Of Speech of the Corpus
	 */
	public void executePartOfSpeech(){

		if(corpus!=null){

			String url = "http://t2k.italianlp.it/rest/corpus/"+corpus.getId()+"/execute/part_of_speech";
			try {
				HttpGet httpget = new HttpGet(url);


				HttpClient client = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();
				HttpResponse response = client.execute(httpget);
				System.out.println("----------------------------------------");
				System.out.println(response.getStatusLine());
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	/**
	 * Check if Part of Speech is ready
	 * @return true if Part of Speech is ready
	 */
	public boolean QueryPartofSpeech(){
		if(corpus!=null){
			String url = "http://t2k.italianlp.it/rest/corpus/"+corpus.getId();

			try {
				HttpGet httpget = new HttpGet(url);

				HttpClient client = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();

				HttpResponse response = client.execute(httpget);
				System.out.println("----------------------------------------");
				System.out.println(response.getStatusLine());
				HttpEntity resEntity = response.getEntity();

				JsonReader rdr = Json.createReader(resEntity.getContent());
				JsonObject obj = rdr.readObject();

				corpus = new Corpus(obj);

				if(corpus.getPart_of_speech().getStatusBool()){
					return true;

				}

			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;

	}

	/**
	 * 	Download Part of Speech file
	 */
	public void downloadPartofSpeech(){
		if(corpus!=null){
			if(corpus.getPart_of_speech().getStatusBool()){
				downloadUsingStream(corpus.getPart_of_speech().getAnalysis_file(),"pos");
			}
		}

	}

	private void downloadUsingStream(String urlStr,String ext) {

		String url =  "http://t2k.italianlp.it";
		try {
			BufferedInputStream in = new BufferedInputStream(new URL(url+urlStr).openStream());
			FileOutputStream  fout = new FileOutputStream(corpus.getCorpusName()+"."+ext+".txt");

			byte data[] = new byte[1024];
			int count;
			while ((count = in.read(data, 0, 1024)) != -1) {
				fout.write(data, 0, count);
			}
			if (in != null)
				in.close();
			if (fout != null)
				fout.close();

			System.out.println("----------------------------------------");
			System.out.println("write file: "+corpus.getCorpusName()+"."+ext+".txt");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Return a List of Term Extraction Configuration
	 * @return List of Term Extraction Configuration
	 */
	public List<Term_Extraction_Configuration> getListTerm_Extraction_Configuration(){

		String url = "http://t2k.italianlp.it/rest/term_extraction_configuration/list";

		try {
			HttpGet httpget = new HttpGet(url);

			HttpClient client = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();

			HttpResponse response = client.execute(httpget);
			System.out.println("----------------------------------------");
			System.out.println(response.getStatusLine());
			HttpEntity resEntity = response.getEntity();

			JsonReader rdr = Json.createReader(resEntity.getContent());

			JsonArray arrobj = rdr.readArray();

			List<Term_Extraction_Configuration> cter = new ArrayList<Term_Extraction_Configuration>();

			for(JsonValue Jval: arrobj){

				JsonObject obj = (JsonObject)Jval;

				Term_Extraction_Configuration terc = new Term_Extraction_Configuration(obj);
				cter.add(terc);

			}


			return cter;




		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;


	}


	/**
	 * Set Term Extraction Configuration
	 * @param terc
	 */
	public void setTerm_Extraction_Configuration(Term_Extraction_Configuration terc){
		if(corpus!=null){

			String url = "http://t2k.italianlp.it/rest/corpus/"+corpus.getId()+"/set_term_extraction_configuration/"+terc.getId();

			try {
				HttpGet httpget = new HttpGet(url);

				HttpClient client = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();

				HttpResponse response = client.execute(httpget);
				System.out.println("----------------------------------------");
				System.out.println(response.getStatusLine());

			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


		}
	}

	/**
	 * Execute Term Extraction of the Corpus
	 */
	public void executeTerm_Extraction(Term_Extraction_Configuration terc){
		if(corpus!=null && corpus.getPart_of_speech().getStatusBool()){
			setTerm_Extraction_Configuration(terc);


			String url = "http://t2k.italianlp.it/rest/corpus/"+corpus.getId()+"/execute/term_extraction";
			try {
				HttpGet httpget = new HttpGet(url);

				HttpClient client = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();
				HttpResponse response = client.execute(httpget);
				System.out.println("----------------------------------------");
				System.out.println(response.getStatusLine());
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	/**
	 * Check if Term Extraction is ready
	 * @return true if Term Extraction is ready
	 */
	public boolean QueryTerm_Extraction(){
		if(corpus!=null && corpus.getPart_of_speech().getStatusBool()){
			String url = "http://t2k.italianlp.it/rest/corpus/"+corpus.getId();

			try {
				HttpGet httpget = new HttpGet(url);

				HttpClient client = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();

				HttpResponse response = client.execute(httpget);
				System.out.println("----------------------------------------");
				System.out.println(response.getStatusLine());
				HttpEntity resEntity = response.getEntity();

				JsonReader rdr = Json.createReader(resEntity.getContent());
				JsonObject obj = rdr.readObject();

				corpus = new Corpus(obj);

				if(corpus.getTerm_extraction().getStatusBool()){
					return true;

				}

			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;

	}

	/**
	 * 	Download Term Extraction file
	 */
	public void downloadTerm_Extraction(){
		if(corpus!=null){
			if(corpus.getTerm_extraction().getStatusBool()){
				downloadUsingStream(corpus.getTerm_extraction().getAnalysis_file(),"ter");
			}
		}

	}

	/**
	 * Execute Term Extraction Indexer of the Corpus
	 */
	public void executeTerm_Extraction_Indexer(){
		if(corpus!=null && corpus.getTerm_extraction().getStatusBool()){



			String url = "http://t2k.italianlp.it/rest/corpus/"+corpus.getId()+"/execute/term_extraction_indexer";
			try {
				HttpGet httpget = new HttpGet(url);

				HttpClient client = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();
				HttpResponse response = client.execute(httpget);
				System.out.println("----------------------------------------");
				System.out.println(response.getStatusLine());
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	/**
	 * Check if Term Extraction Indexer is ready
	 * @return true if Term Extraction Indexer is ready
	 */
	public boolean QueryTerm_Extraction_Indexer(){
		if(corpus!=null &&  corpus.getTerm_extraction().getStatusBool()){
			String url = "http://t2k.italianlp.it/rest/corpus/"+corpus.getId();

			try {
				HttpGet httpget = new HttpGet(url);

				HttpClient client = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();

				HttpResponse response = client.execute(httpget);
				System.out.println("----------------------------------------");
				System.out.println(response.getStatusLine());
				HttpEntity resEntity = response.getEntity();

				JsonReader rdr = Json.createReader(resEntity.getContent());
				JsonObject obj = rdr.readObject();

				corpus = new Corpus(obj);

				if(corpus.getTerm_extraction_indexing().getStatusBool()){
					return true;

				}

			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;

	}

	/**
	 * 	Download Term Extraction Indexer file
	 */
	public void downloadTerm_Extraction_Indexer(){
		if(corpus!=null){
			if(corpus.getTerm_extraction_indexing().getStatusBool()){
				downloadUsingStream(corpus.getTerm_extraction_indexing().getConll_file(),"cnll");
			}
		}

	}

}
