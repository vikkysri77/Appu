package crawler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Rootfunction {
	
	
	public static void main(String[] args) {


	  	Scanner input = new Scanner(System.in);
 		System.out.print("Enter your search phrase: ");
 		String searchKeyWord = input.nextLine();

		
	  	
	  	AutoCorrector corrector = new AutoCorrector();

		 corrector.loadFiles();

		 String suggestion = corrector.findSimilarWord("tra");

		 if (suggestion.length() == 0)
		     System.out.println("There are no similar words. Please enter the valid word to search");

		 else
			 System.out.println("Suggestion: " + searchKeyWord);
		
		ReverseIndexing RI = new ReverseIndexing();
	    		try
	        	{
	        		RI.readFiles();

	        		ArrayList<String> list = RI.getURLs(searchKeyWord.toString());
	        		
	        		if(list.size() == 0)
	        		{
	        			System.out.println("No results found");
	        		}
	        		else
	        		{
	        			for(String s : list)
	        			{
	        				System.out.println("\n"+s);
	        			}
	        		}
	        	}
	        	catch(Exception e)
	        	{
	        		System.out.println(e);
	        		System.out.println("No results found");
	        	}

		
	}
}
