/*import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;


import javax.json.*;
import javax.net.ssl.HttpsURLConnection;

import org.apache.http.Consts;
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
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;*/

import cnr.ilc.t2k.Corpus;
import cnr.ilc.t2k.Language;
import cnr.ilc.t2k.Part_of_Speech_Tagset;
import cnr.ilc.t2k.Term_Extraction_Configuration;
import cnr.ilc.t2k.t2kCore;

import java.util.List;
import java.io.File;



/**
 * @author winspa
 *
 */
/**
 * @author winspa
 *
 */
public class main {

	public static void main(String[] args) {
		

		t2kCore t2k = new t2kCore("FMTLab", "");
		
		
		//// Term_Extraction_Configuration
		
		Term_Extraction_Configuration tec = new Term_Extraction_Configuration("NuovaConf");
		
		tec.setPart_of_speech_tagset_start_term(Part_of_Speech_Tagset.Coordinatingconjunction,Part_of_Speech_Tagset.Nounsingular );
		tec.setPart_of_speech_tagset_internal_term(Part_of_Speech_Tagset.Coordinatingconjunction,Part_of_Speech_Tagset.Nounsingular );
		
		tec.setPart_of_speech_tagset_end_term(Part_of_Speech_Tagset.Coordinatingconjunction,Part_of_Speech_Tagset.Nounsingular );
		
		
		t2k.putTerm_Extraction_Configuration(tec);
		
	
		
		////

		File file = new File("test.txt");

		t2k.executeNewCorpus(file, "test", Language.English);
		
		//t2k.setCorpus(772);

		t2k.executePartOfSpeech();

		while(!t2k.QueryPartofSpeech()){
			try {
				Thread.sleep(5000);
				System.out.println("Pausa POS ");
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
		}

		t2k.downloadPartofSpeech(null);

		List<Term_Extraction_Configuration> col = t2k.getListTerm_Extraction_Configuration();

		t2k.executeTerm_Extraction(col.get(2));

		while(!t2k.QueryTerm_Extraction()){
			try {
				Thread.sleep(5000);
				System.out.println("Pausa TE ");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		t2k.downloadTerm_Extraction(null);
		
		
		t2k.executeTerm_Extraction_Indexer();
		
		while(!t2k.QueryTerm_Extraction_Indexer()){
			try {
				Thread.sleep(5000);
				System.out.println("Pausa TEI ");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		t2k.downloadTerm_Extraction_Indexer(null);
		

		System.out.println("Fine "+t2k.getCorpus());
		
		
		t2k.delCorpus();

	}

	


}





/*


public static String Base64Encode(String Name, String Pass){

	String encodedBytes =  Base64.getEncoder().encodeToString((Name+":"+Pass).getBytes());

	return encodedBytes;
}

private static void sendPost(String nomecorpus,File filename)  {

	String url = "http://t2k.italianlp.it/rest/new_corpus";
	try {
		String boundary = "------------------------" + System.currentTimeMillis();
		HttpPost httpPost = new HttpPost(url);
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();  
		builder.setBoundary(boundary);
		builder.setMode(HttpMultipartMode.STRICT);
		builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
		//httpPost.addHeader("Authorization", "Basic " + Base64Encode("FMTLab", "FMTLab2013"));
		ContentType cc = ContentType.create("text/plain");
		//, Consts.UTF_8

		httpPost.addHeader("Accept", "application/json");
		httpPost.addHeader("Content-Type","multipart/form-data; boundary="+boundary);
		//httpPost.addHeader("Expect","100-continue");
		builder.addTextBody("name", "eurocali.txt",cc);

		builder.addTextBody("language", "GB",cc);


		//new ContentType("", Const.UTF8);
		String str = "Architecture descrin\n\r";
		//InputStream is = new ByteArrayInputStream(str.getBytes());
		//FileBody fileBody = new FileBody(filename, cc);
		//	builder.addPart("corpus_file", fileBody);
		//	InputStream is = new FileInputStream(nomecorpus);
		builder.addBinaryBody("corpus_file", filename, cc, nomecorpus);
		//ContentType.TEXT_PLAIN

		HttpEntity entity = builder.build();


		httpPost.setEntity(entity);

		CredentialsProvider provider = new BasicCredentialsProvider();
		UsernamePasswordCredentials credentials = new UsernamePasswordCredentials("FMTLab", "FMTLab2013");
		provider.setCredentials(AuthScope.ANY, credentials);
		HttpClient client = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();

		//HttpClient client = HttpClientBuilder.create().build();
		httpPost.removeHeaders("Accept-Encoding");
		httpPost.removeHeaders("Connection");
		HttpResponse response = client.execute(httpPost);


		System.out.println("----------------------------------------");
		System.out.println(response.getStatusLine());
		HttpEntity resEntity = response.getEntity();

		json(resEntity.getContent(),nomecorpus); 
			if (resEntity != null) {
			System.out.println("Response content length: " + resEntity.getContentLength());
		}
		EntityUtils.consume(resEntity);


		//add reuqest header
		con.setRequestMethod("POST");
	String USER_AGENT = "Mozilla/5.0";
	con.setRequestProperty("User-Agent", USER_AGENT);



	} catch (MalformedURLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

}


private static void json(InputStream is,String nomecorpus){
	JsonReader rdr = Json.createReader(is);
	JsonObject obj = rdr.readObject();

	Corpus cp = new Corpus(obj);

	System.out.println(cp);

	System.out.println(obj.toString());

	JsonNumber id = obj.getJsonNumber("id");
	JsonObject pos = obj.getJsonObject("part_of_speech");
	JsonObject ter = obj.getJsonObject("term_extraction");
	JsonObject terindex = obj.getJsonObject("term_extraction_indexer");
	System.out.println(pos);
	System.out.println(ter);
	System.out.println(terindex);
	/*JsonArray results = obj.getJsonArray("id");
     for (JsonObject result : results.getValuesAs(JsonObject.class)) {
         System.out.print(result.getJsonObject("from").getString("name"));
         System.out.print(": ");
         System.out.println(result.getString("message", ""));
         System.out.println("-----------");

     }
	System.out.println("----------- execute/part_of_speech");
	executePartOfSpeach(id.toString());

	System.out.println("----------- Query/part_of_speech");
	QueryPartofSpeach(id.toString(), nomecorpus);
}

private static void executePartOfSpeach(String id){

	String url = "http://t2k.italianlp.it/rest/corpus/"+id+"/execute/part_of_speech";
	try {
		HttpGet httpget = new HttpGet(url);
		httpget.addHeader("Authorization", "Basic " + Base64Encode("FMTLab", "FMTLab2013"));
		HttpClient client = HttpClientBuilder.create().build();

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

private static void QueryPartofSpeach(String id,String nomecorpus){
	String url = "http://t2k.italianlp.it/rest/corpus/"+id;
	boolean cicla = true;
	while(cicla){
		try {
			HttpGet httpget = new HttpGet(url);
			httpget.addHeader("Authorization", "Basic " + Base64Encode("FMTLab", "FMTLab2013"));
			HttpClient client = HttpClientBuilder.create().build();

			HttpResponse response = client.execute(httpget);
			System.out.println("----------------------------------------");
			System.out.println(response.getStatusLine());
			HttpEntity resEntity = response.getEntity();

			JsonReader rdr = Json.createReader(resEntity.getContent());
			JsonObject obj = rdr.readObject();
			System.out.println(obj.toString());

			JsonNumber idpof = obj.getJsonNumber("id");
			JsonObject pos = obj.getJsonObject("part_of_speech");
			JsonString status = pos.getJsonString("status");
			Thread.sleep(5000);
			if(status.getString().equals(new String("successful"))){
				cicla=false;
				JsonString ufile = pos.getJsonString("analysis_file");


				String urlfile =  "http://t2k.italianlp.it";
				String gd = ufile.getString();
				downloadUsingStream(urlfile+gd,nomecorpus);
			}

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

private static void downloadUsingStream(String urlStr, String file) {

	try {
		BufferedInputStream in = new BufferedInputStream(new URL(urlStr).openStream());
		FileOutputStream  fout = new FileOutputStream(file+".pos");

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
		System.out.println("write file: "+file+".pos");
	} catch (MalformedURLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

}
 */





