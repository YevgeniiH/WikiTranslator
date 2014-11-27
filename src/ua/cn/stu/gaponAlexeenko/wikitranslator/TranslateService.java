package ua.cn.stu.gaponAlexeenko.wikitranslator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.Parser;
import org.apache.http.message.BasicNameValuePair;

import android.util.Log;
import android.widget.Toast;

public class TranslateService {
	private static final String API_KEY = "trnsl.1.1.20141126T155101Z.62d4a30e14383283.6bf1058546625891d10cdb1dc57a8f53e5f997fd";//"trnsl.1.1.20130919T140003Z.ce0a7167a79851c6.f12d0812d39beee660dcb5a17fc38ecb6d8aeefc";
    private static String par = "";
	private static String text = "";
	public static String convert(String from, String to, String tt){
		par = from + "-" + to;
		String[] text = tt.split(" ");
		tt="";
		for (String s : text) {
			tt += s+"+";
		}
		//tt = tt.replaceAll(" ", "+");
		String url = "https://translate.yandex.net/api/v1.5/tr.json/translate?key="+API_KEY+"&lang="+par+"&text="+tt;
		
		String result = callWebService(url);
		
		try {
			JSONObject obj = new JSONObject(result);
			String txt = obj.getString("text");
			char[] c = txt.toCharArray();
			String s ="";
			for (char d : c) {
				if (d!='"' && d!='[' && d!=']') {
					s += d;
				}
			}
			return s;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return "";
	}
	
	private static String callWebService(String serviceURL){
		HttpClient client=new DefaultHttpClient();
		HttpGet getRequest=new HttpGet();
		try {
			getRequest.setURI(new URI(serviceURL));
		} catch (URISyntaxException e) {
			Log.e("URISyntaxException", e.toString());
		}
		BufferedReader in = null;
		HttpResponse response =null;
		try {
			response = client.execute(getRequest);
		} catch (ClientProtocolException e) {
			Log.e("ClientProtocolException", e.toString());
		} catch (IOException e) {
			Log.e("IO exception", e.toString());
		}
		
		try {
			in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		} catch (IllegalStateException e) {
			Log.e("IllegalStateException", e.toString());
		} catch (IOException e) {
			Log.e("IO exception", e.toString());
		}
		
		StringBuffer buff=new StringBuffer("");
		String line="";
		try {
			while((line=in.readLine())!=null) {
				buff.append(line);
			}
		} catch (IOException e) {
			Log.e("IO exception", e.toString());
			return e.getMessage();
		}
		
		try {
			in.close();
		} catch (IOException e) {
			Log.e("IO exception", e.toString());
		}
		
		return buff.toString();
	}

	
}
