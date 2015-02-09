import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Base64;

import javax.json.*;
import javax.net.ssl.HttpsURLConnection;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;



public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method s


		sendPost("eurocali.txt", new File("eurocali2.txt"));

		/*URL url;
		try {
			url = new URL("https://graph.facebook.com/search?q=java&type=post");

		 try (InputStream is = url.openStream();
		      JsonReader rdr = Json.createReader(is)) {

		     JsonObject obj = rdr.readObject();
		     JsonArray results = obj.getJsonArray("data");
		     for (JsonObject result : results.getValuesAs(JsonObject.class)) {
		         System.out.print(result.getJsonObject("from").getString("name"));
		         System.out.print(": ");
		         System.out.println(result.getString("message", ""));
		         System.out.println("-----------");
		     }
		 } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 */
	}



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
			httpPost.addHeader("Authorization", "Basic " + Base64Encode("FMTLab", "FMTLab2013"));
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
			FileBody fileBody = new FileBody(filename, cc);
			builder.addPart("corpus_file", fileBody);
		//	InputStream is = new FileInputStream(nomecorpus);
			//builder.addBinaryBody("corpus_file", is, cc, nomecorpus);
//ContentType.TEXT_PLAIN

			HttpEntity entity = builder.build();
				
			
			httpPost.setEntity(entity);
			
		//	CredentialsProvider provider = new BasicCredentialsProvider();
		//	UsernamePasswordCredentials credentials = new UsernamePasswordCredentials("FMTLab", "FMTLab2013");
		//	provider.setCredentials(AuthScope.ANY, credentials);
			// HttpClient client = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();

			HttpClient client = HttpClientBuilder.create().build();
			httpPost.removeHeaders("Accept-Encoding");
			httpPost.removeHeaders("Connection");
			HttpResponse response = client.execute(httpPost);


			System.out.println("----------------------------------------");
			System.out.println(response.getStatusLine());
			HttpEntity resEntity = response.getEntity();
			
			json(resEntity.getContent()); 
		/*	if (resEntity != null) {
				System.out.println("Response content length: " + resEntity.getContentLength());
			}
			EntityUtils.consume(resEntity);

			
			//add reuqest header
			con.setRequestMethod("POST");
		String USER_AGENT = "Mozilla/5.0";
		con.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty("Content-Type", "multipart/form-data");
		con.setRequestProperty("Accept", "application/json");


		 String userpass = "username" + ":" + "password";
	     String basicAuth = "Basic " + new String( Base64.getEncoder().encodeToString(userpass.getBytes()));
	     con.setRequestProperty("Authorization", basicAuth);
			 */

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

 @SuppressWarnings("unused")
	private static void json(InputStream is){
		 JsonReader rdr = Json.createReader(is);
		 JsonObject obj = rdr.readObject();
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
	         
	     }*/
	}
}
