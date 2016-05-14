package com.walmart.example;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.sun.javafx.collections.MappingChange.Map;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }
    
	String id;   
	static searchApi src = new searchApi();
	 
	   
	  

	public void testSearch() throws NullPointerException, IOException, ParserConfigurationException, SAXException {
		String searchitem = "watch";	
		System.out.println("Test serachApi");
	      id = "16533544";
	      assertEquals(id,src.search(searchitem));
		// TODO Auto-generated method stub
		
	}
	
	public void testNullRecommendations() throws NullPointerException, IOException, ParserConfigurationException, SAXException {
		System.out.println("Testing Search for null recommendations");
	      id = " 36442121";
	      assertEquals(null,src.recommendationSearch(id));
		// TODO Auto-generated method stub
		
	}
	
	public void testRecommendationSearch() throws NullPointerException, IOException, ParserConfigurationException, SAXException {
		System.out.println("Test Recommendation search");
	      id = "42608121";
	      assertEquals(Arrays.asList("25857866", "30146246", "42608125","39875894","29749372","45804400","42807912","31232984","42608106","45804384"), src.recommendationSearch(id));
		// TODO Auto-generated method stub
		
	}
	
	public void testGetReviews() throws IOException, ParserConfigurationException, SAXException {
		HashMap<String, String> expected = new HashMap<String, String>();
		expected.put("21880818","3.35");
		expected.put("23583072","3.47");
		expected.put("37408660","3.5");
		expected.put("23583208","3.89");
		expected.put("35514691","4.11");
		expected.put("37408662","4.22");
		expected.put("35514683","4.25");
		expected.put("23583206","4.29");
		expected.put("35514692","4.43");
		expected.put("35514693","4.57");
		
		ArrayList<String> inputIds = new ArrayList<String>( Arrays.asList("21880818", "23583072", "37408660","23583208","35514691","37408662","35514683","23583206","35514692","35514693"));
		HashMap<String,String> actual = src.getReviews(inputIds);
	    assertEquals(expected.size(), actual.size());
	    for(Entry<String, String> value:expected.entrySet()){
	        String actualValue = actual.get(value.getKey());
	        assertNotNull(actualValue);
	        assertEquals(value.getValue(), actualValue);
	    }
	}

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        assertTrue( true );
    }
}
