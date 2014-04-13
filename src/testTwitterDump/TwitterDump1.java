package testTwitterDump;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.URI;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;



public class TwitterDump1 {

	public TwitterDump1() {
		// TODO Auto-generated constructor stub
	}
	public static void main(String []args) throws Exception {
		FileWriter fw = null;
		try{
			
			String endPoint="https://stream.twitter.com/1.1/statuses/sample.json";
			
			String AccessToken ="1368765691-Om1IRPJqFcsJg21oDj3h8VWaqfH9qVI2lfHY7ct";
	        String AccessSecret = "O7cA6lHCBFUnl7ZOhFNBnJkOwTzZBrVGDqicUJG7UfyzS";
	        String ConsumerKey = "N0tfrQ33nUO2Il3S3FQ9FQ";
	        String ConsumerSecret = "EJiJaSX9RpC4LUZOSzo7H0Ajn717NgAwnqRrJP7Djfg";
	        
//	       
	        OAuthConsumer consumer;
	        consumer = new CommonsHttpOAuthConsumer(ConsumerKey,ConsumerSecret);
	        consumer.setTokenWithSecret(AccessToken,AccessSecret);
	        
			DateFormat dateFormat = new SimpleDateFormat("yyyy#MMM#dd");
		    Date date = new Date();
		    System.out.println(dateFormat.format(date));
		    
			File file = new File("C:/Users/Rohit/Desktop/Master Project/Twitter/"+dateFormat.format(date).toString()+".txt");
		    if(!file.exists()) 
				file.createNewFile();
		    
		    
		    
		    long backoff = 1000;
			while(true)
			//for(int i =0; i<1; i++)
			{
				
				//final HttpParams httpParams = new BasicHttpParams();
			    //HttpConnectionParams.setConnectionTimeout(httpParams, 30000);
				try{
					fw=new FileWriter(file,true);
					HttpClient httpClient = new DefaultHttpClient();
					
					HttpGet httpGet = new HttpGet();
					httpGet.setURI(new URI(endPoint));
			        consumer.sign(httpGet);
					
					HttpResponse response = httpClient.execute(httpGet);
					System.out.println(response.getStatusLine().getStatusCode());
				    if(response.getStatusLine().getStatusCode()==420){
				    	dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				    	date = new Date();
				    	System.out.println(dateFormat.format(date)+" "+backoff/1000+" min");
				    	backoff=backoff*2;
				    	Thread.sleep(backoff);
				    }
				    else
				    
				    {	backoff=1000;
				    	BufferedReader bf = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
				    	String line = null;
				    	while((line = bf.readLine())!=null){
				    		System.out.println(line);
				    		fw.append("\n"+line);
				    	}
					    fw.close();
				    }
				}
				catch(Exception e){
					System.out.println(e.toString());
				}
				finally{
					System.out.println("Stopped");
					fw.close();
				}
		    }
		}
		catch(Exception e){
			System.out.println(e.toString());
		}
		finally{
			System.out.println("Stopped");
			fw.close();
		}
	}
}
