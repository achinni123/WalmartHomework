package com.walmart.example;

import java.io.IOException;
import java.util.ArrayList;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import java.io.Console;


public class App 
{
    public static void main( String[] args ) throws IOException, ParserConfigurationException, SAXException, NullPointerException
    {
    	Console c = System.console();
    	String searchItem =c.readLine("Enter your search string: ");
		searchApi search = new searchApi();
		String id = search.search(searchItem);
		ArrayList<String> RecommendedIds =search.recommendationSearch(id);
		if(RecommendedIds == null) {}
		else search.getReviews(RecommendedIds);
    }
}
