package se.dose.irontest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class WebUtils {
    public static Document getDocument(String url)
    throws ClientProtocolException, IOException, ParserConfigurationException, IllegalStateException, SAXException {
        HttpGet uri = new HttpGet(url);
        DefaultHttpClient client = new DefaultHttpClient();
        
        HttpResponse response = client.execute(uri);
        if (response.getStatusLine().getStatusCode() != 200) {
            return null;
        }

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(response.getEntity().getContent());

        return doc;
    }
    
    public static boolean postRequest(String url, Map<String, String> params)
    throws ClientProtocolException, IOException {

        HttpPost uri = new HttpPost(url);
        DefaultHttpClient client = new DefaultHttpClient();

        ArrayList<NameValuePair> postParams = new ArrayList<NameValuePair>();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            postParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        uri.setEntity(new UrlEncodedFormEntity(postParams));
        
        HttpResponse response = client.execute(uri);
        if (response.getStatusLine().getStatusCode() != 200) {
            return false;
        }
        return true;
    }
}
