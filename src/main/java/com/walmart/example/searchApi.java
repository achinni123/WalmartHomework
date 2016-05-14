package com.walmart.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.w3c.dom.*;

public class searchApi {
	
	public String search(String name) throws IOException, ParserConfigurationException, SAXException, NullPointerException
	{
			String itemId = ""; 
			URL url = new URL("http://api.walmartlabs.com/v1/search?query="+name+"&format=xml&apiKey=cb3e54pgt4zz2bedqtgwy6a9");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			if (conn.getResponseCode() != 200)
			{
		    throw new IOException(conn.getResponseMessage());
			}
			BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = rd.readLine()) != null) {
		    sb.append(line);
		  }
		  
			String str = sb.toString();
			DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(str));
			Document doc = db.parse(is);
			doc.getDocumentElement().normalize();
			NodeList nodes = doc.getElementsByTagName("items");
			for (int temp = 0; temp < nodes.getLength(); temp++)
			{
				Node nNode = nodes.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
					itemId = eElement.getElementsByTagName("itemId").item(0).getTextContent();
					System.out.println("The first product id retrieved from the search API is : " + eElement.getElementsByTagName("itemId").item(0).getTextContent());
					
				}
			}
		    rd.close();
			conn.disconnect();
			return itemId;
	}
			  

	public ArrayList<String> recommendationSearch(String itemId) throws IOException, ParserConfigurationException, SAXException
	{
		    int k =1;
			ArrayList<String> ls = new ArrayList<String>();
			URL url = new URL("http://api.walmartlabs.com/v1/nbp?format=xml&apiKey=cb3e54pgt4zz2bedqtgwy6a9&itemId="+itemId);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			if (conn.getResponseCode() != 200) {
			    System.out.println("No recommendations found");
				return null;
			}
			System.out.println("error");
			BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = rd.readLine()) != null) {
				sb.append(line);
			}
			String str = sb.toString();
			DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		    InputSource is = new InputSource();
		    is.setCharacterStream(new StringReader(str));
		    try {
            Document doc = db.parse(is);
		    doc.getDocumentElement().normalize();
		    NodeList nodes = doc.getElementsByTagName("item");
		    int length = nodes.getLength();
		    System.out.println("The top ten product IDs retrieved from the product recoomendation Search:");
		    if (length>10)
		    {
		    	length =10;
		    }
		    if(length==0)
		    {
		      System.out.println("No recommnedations found");
		    }
		    else
		    {
		    for (int temp = 0; temp < length; temp++) {

				Node nNode = nodes.item(temp);
						
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;
					itemId = eElement.getElementsByTagName("itemId").item(0).getTextContent();
					ls.add(eElement.getElementsByTagName("itemId").item(0).getTextContent());
					System.out.println("Recommended id "+k+" : " + eElement.getElementsByTagName("itemId").item(0).getTextContent());
					k++;
					
				}
			}
		    }
		    rd.close();
			conn.disconnect();
			return ls;
		    }
		    catch (Exception e)
		    {
		    	System.out.println("No recommendations found");
		    	return null;
		    }
			}
	
	public HashMap<String,String> getReviews(ArrayList<String> ls) throws IOException, ParserConfigurationException, SAXException
	{
			int k =1;
			HashMap<String, String> hm = new HashMap<String, String>(); 
			for (int i=0;i<ls.size();i++)
			{   
			String id = ls.get(i);
			URL url = new URL("http://api.walmartlabs.com/v1/reviews/"+id+"?format=xml&apiKey=cb3e54pgt4zz2bedqtgwy6a9");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			if (conn.getResponseCode() != 200) {
			    throw new IOException(conn.getResponseMessage());
			  }
	        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = rd.readLine()) != null) {
			    sb.append(line);
			   
			  }
			  
			String str = sb.toString();
			DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(str));
            Document doc = db.parse(is);
			doc.getDocumentElement().normalize();
			NodeList nodes1 = doc.getElementsByTagName("itemReview");
			int length = nodes1.getLength();
			for (int temp = 0; temp < length; temp++) {
                    Node nNode = nodes1.item(temp);
					if (nNode.getNodeType() == Node.ELEMENT_NODE) {

						Element eElement = (Element) nNode;
						
							if(eElement.getElementsByTagName("averageOverallRating").getLength()==0){
								{
									hm.put(id, "0.0");
									System.out.println("The average overall rating for product "+k+" obtained from product recommendation search is : 0.0");
									k++;
								}
							}
							else{
								System.out.println("The average overall rating for product "+k+" obtained from product recommendation search is :" + eElement.getElementsByTagName("averageOverallRating").item(0).getTextContent());
								hm.put(id,eElement.getElementsByTagName("averageOverallRating").item(0).getTextContent());
								k++;
							}
						}
			    
			    
			    }
			    rd.close();
				conn.disconnect();
		
	}
		    ArrayList<String> mapKeys = new ArrayList<String>(hm.keySet());
		    ArrayList<String> mapValues = new ArrayList<String>(hm.values());
		    Collections.sort(mapValues);
		    Collections.sort(mapKeys);
            LinkedHashMap<String, String> sortedMap =new LinkedHashMap<String,String>();
            Iterator<String> valueIt = mapValues.iterator();
		    while (valueIt.hasNext()) {
		           String val = valueIt.next();
		           Iterator<String> keyIt = mapKeys.iterator();
                   while (keyIt.hasNext()) {
		           String key = keyIt.next();
		           String comp1 = hm.get(key);
		           String comp2 = val;
                   if (comp1.equals(comp2)) {
		                keyIt.remove();
		                sortedMap.put(key, val);
		                break;
		            }
		        }
		    }
		   System.out.println("The rank order for the recommended products based on the review sentiments is");
		   ArrayList<String> keys = new ArrayList<String>(sortedMap.keySet());
		   int len = keys.size();
		   for (int j=0;j<len;j++)
		   {
		    System.out.println("The item id is "+keys.get(j)+" and the average rating is "+sortedMap.get(keys.get(j)));
		   }
		   return sortedMap;
			    
}
}






