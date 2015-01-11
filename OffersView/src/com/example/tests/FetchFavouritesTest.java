package com.example.tests;

import org.junit.Test;

import android.test.AndroidTestCase;

import com.example.offersview.logic.JSONParser;

public class FetchFavouritesTest extends AndroidTestCase{
	String output;
	JSONParser testJParser = new JSONParser();

	@Test
	public void testReturnsNotNull() {
		output = testJParser.fetchFavrourites("1");
		assertNotNull(output);
	}
	
	@Test
	public void testReturnsNotInt() {
		int wrong = 3;
		output = testJParser.fetchFavrourites("1");
		assertNotSame(wrong, output);
	}
	
	@Test
	public void testArguementIsNotNumber() {
		output = testJParser.fetchFavrourites("not number");
		//take first 4 characters to see if it is null
		String isNull = output.substring(0, 4);
		assertEquals("null",isNull);
	}

}
