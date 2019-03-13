/*Manav Kothari
  MPK 170030
  Chat bot using weather and tumblr api
 */
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import org.jibble.pircbot.*;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ChatBot extends PircBot {
	
	public ChatBot(){
		this.setName("Manavsbot");
	}
	//checks message and then does the following
	public void onMessage(String channel, String sender, String login, String hostname, String message){
		//for weather
	
		String Input[]=new String[2];
        Input = message.split(" ");
        
        if (Input[0].equals("weather")) {
        	sendMessage(channel,weatherHandler(Input[1]));
        }
        else if (Input[0].equals("tumblr")) {
        	sendMessage(channel,tumblrHandler(Input[1]));
        	
        }
        else if (Input[0].equals("exit")) {
        	sendMessage(channel,"GoodBye!");
        }
        else {
        	sendMessage(channel,"Invalid Input");
        }
	}


public static String tumblrHandler(String userId) {

	
	String requestURL = "https://api.tumblr.com/v2/blog/"+ userId +"/info?api_key=FQHaYSXAVcH0DPAxr4Rb4Blj9Of0vHvHg2RDsqNsGXauCZOvnO";
	StringBuilder res = new StringBuilder(); //Holds Java string 
	try {	
		URL url = new URL(requestURL); //URL to be parsed
		
		HttpURLConnection connect = (HttpURLConnection) url.openConnection(); //Connect to API
		connect.setRequestMethod("GET"); //Type of request 
		BufferedReader reader = new BufferedReader(new InputStreamReader(connect.getInputStream())); //Reads from open connection 
		String ln;
	    	while ((ln = reader.readLine()) != null) { //BufferedReader to String
	    		res.append(ln);
	    	}
	    reader.close();
	    TumblrAPI response = sortTumblrJSON(res.toString());
	    if (response==null) {
	    	return "Username Invalid";
	    }
	    else {
	    	return response.toString();
	    }
	    //System.out.println(sortTumblrJSON(res.toString())[1]);
	}
	catch(Exception e){
		return "Error! wrong username";
	}
	
}

public static TumblrAPI sortTumblrJSON(String json) {
	
    JsonObject mainObject = new JsonParser().parse(json).getAsJsonObject();
    int status = mainObject.getAsJsonObject("meta").getAsJsonPrimitive("status").getAsInt();
    int posts=0;
    String title = " ";
    if(status!= 200) {
    	System.out.println("User does not exist!!! ");
    	return null;
    }
    posts = mainObject.getAsJsonObject("response").getAsJsonObject("blog").getAsJsonPrimitive("posts").getAsInt();
    
    title = mainObject.getAsJsonObject("response").getAsJsonObject("blog").getAsJsonPrimitive("title").getAsString();
    TumblrAPI ob = new TumblrAPI(posts,title);
    return ob;
}

public static String weatherHandler(String zipcode) {
	String requestURL = "http://api.openweathermap.org/data/2.5/weather?zip=" + zipcode + "&APPID=759e7371c4d3f2212fce8df343e06102";
	StringBuilder res = new StringBuilder(); //Holds Java string 
	try {	
		URL url = new URL(requestURL); //URL to be parsed
		
		HttpURLConnection connect = (HttpURLConnection) url.openConnection(); //Connect to API
		connect.setRequestMethod("GET"); //Type of request 
		BufferedReader reader = new BufferedReader(new InputStreamReader(connect.getInputStream())); //Reads from open connection 
		String ln;
	    	while ((ln = reader.readLine()) != null) { //BufferedReader to String
	    		res.append(ln);
	    	}
	    reader.close();
	    WeatherAPI response = sortWeatherJSON(res.toString());
	    if (response==null) {
	    	return "Username Invalid";
	    }
	    else {
	    	return response.toString();
	    }
	}
	catch(Exception e){
		return "ERROR! WRONG ZIPCODE";
	}
}

public static WeatherAPI sortWeatherJSON(String json) {
	JsonObject mainObject = new JsonParser().parse(json).getAsJsonObject();
    int cod = mainObject.getAsJsonPrimitive("cod").getAsInt();
    int minimum =0 ;
    int maximum = 0;       
    if(cod!= 200) {
    	System.out.println("User does not exist!!! ");
    	return null;
    }
    minimum = mainObject.getAsJsonObject("main").getAsJsonPrimitive("temp_min").getAsInt();
    minimum= minimum-273;
    maximum = mainObject.getAsJsonObject("main").getAsJsonPrimitive("temp_max").getAsInt();
    maximum=maximum-273;
    WeatherAPI ob = new WeatherAPI(minimum,maximum);
    return ob;
}
}
