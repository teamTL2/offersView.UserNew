package com.example.tests;

import org.json.JSONArray;
import org.json.JSONException;
import org.junit.Test;

import android.test.AndroidTestCase;

import com.example.offersview.logic.JSONParser;

public class ManageFavoriteTest extends AndroidTestCase{
	String outputBefore;
	String outputAfter;

	String[] resultBefore;
	String[] resultAfter;
	
	JSONParser jParser = new JSONParser();

	@Test
	public void testRemoveAddWithGoodValues() throws JSONException {
		
		/*
		 * To test auto sugkrinei to plithos twn eggrafwn
		 * ston pinaka favourites
		 * prin prosthesoume ena stoixeio kai
		 * afou to exoume prosthesei
		 */
		
		//se periptwsi pou uparxei idi to afairw gia na to ksanaprosthesw
		jParser.removeFavouirte("23", "1");
		outputBefore = jParser.fetchFavrourites("1");
		resultBefore = outputBefore.split("<");
		outputBefore = resultBefore[0];
		
		jParser.addFavorite("23","1");
		outputAfter = jParser.fetchFavrourites("1");
		resultAfter = outputAfter.split("<");
		outputAfter = resultAfter[0];
		
		
		JSONArray output1 = new JSONArray(outputBefore);
		JSONArray output2 = new JSONArray(outputAfter);
	/*
		Log.v("serverInfo1",output1.toString());
		Log.v("serverInfo2",output2.toString());
	*/
		
		int countBefore = output1.length();
		int countAfter = output2.length();
		/*
		 * perimenoume to countBefore na einai megalutero
		 * apo to countAfter giati exoume prosthesi mia eggrafi
		 * ston pinaka mas mesw tis addFavourite()
		 */
		assertNotSame(countAfter, countBefore);
	}
	
	@Test
	public void testRemoveAddWithBadValues() throws JSONException {
		
		/*
		 * To test auto sugkrinei to plithos twn eggrafwn
		 * ston pinaka favourites
		 * prin prosthesoume ena stoixeio kai
		 * afou to exoume prosthesei
		 * kai gia parametrous dinoume lanthasmenes times
		 */
		
		//se periptwsi pou uparxei idi to afairw gia na to ksanaprosthesw
		jParser.removeFavouirte("one", "one");
		outputBefore = jParser.fetchFavrourites("1");
		resultBefore = outputBefore.split("<");
		outputBefore = resultBefore[0];
		
		jParser.addFavorite("two","two");
		outputAfter = jParser.fetchFavrourites("1");
		resultAfter = outputAfter.split("<");
		outputAfter = resultAfter[0];
		
		
		JSONArray output1 = new JSONArray(outputBefore);
		JSONArray output2 = new JSONArray(outputAfter);
	/*	
		Log.v("serverInfo1",output1.toString());
		Log.v("serverInfo2",output2.toString());
	*/
		
		int countBefore = output1.length();
		int countAfter = output2.length();
		
		/*
		 * Perimenoyme to prin kai to meta na einai to idio 
		 * afou den exei ginei kamia allagi stin vasi
		 */
		assertSame(countAfter, countBefore);
	}
}