package crawler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ReverseIndexing 
{
	
	private static Path currentRelativePath = Paths.get("");
    private static String fp = currentRelativePath.toAbsolutePath().toString();
	
	    private static Map<String, ArrayList<String>> mapWordURLs = new HashMap<String, ArrayList<String>>();
	    
	    private static void extractWords(File file) throws IOException 
		{
			
	    	FileReader fileReader = new FileReader(file);
	    	BufferedReader bufferReader = new BufferedReader(fileReader);
			
			try 
			{
				String sURL = bufferReader.readLine();
							   	
				String line = null;
				
				//System.out.println(sURL);
				while ((line = bufferReader.readLine()) != null) 
				{
					String word = line.toLowerCase();
					
					

					if (!line.contains(" ")) 
					{
						word=word.toLowerCase();
						insertIntoMap(word, sURL);
					}
					else 
					{
						String[] sWords = line.split("\\s");
					
						for (String sWord : sWords) 
						{
							if(checkAlpha(sWord))
							{
								sWord=sWord.toLowerCase();
								insertIntoMap(sWord, sURL);
							}
						}
					}
				}
			
			} catch (IOException e) {
				e.printStackTrace();
			}	 
			
			bufferReader.close();
			
		}
	    
	    public static void insertIntoMap(String word, String sURL)
	    {
	    	if(mapWordURLs.containsKey(word))
			{
				ArrayList<String> list = mapWordURLs.get(word);
				list.add(sURL);
				mapWordURLs.put(word, list);
				
			}
			else
			{
				ArrayList<String> listURLs = new ArrayList<>();
				listURLs.add(sURL);
				mapWordURLs.put(word, listURLs);
			}
	    }
	    
	    
	    public static void readFiles()
	    {
	    	File fileFolder = new File(fp+"/src/processedText/");
	    	
			File[] files = fileFolder.listFiles();

			for (int i = 0; i < files.length; i++) {
				if (files[i].isFile()) {

					try {
						extractWords(files[i]);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
	    }
	    
	    public static ArrayList<String> getURLs(String sWord)
	    {
	    	return mapWordURLs.get(sWord);
	    }
	    
		 public static boolean checkAlpha(String string) 
		 {
			 
			 return ((string != null)
		                && (!string.equals(""))
		                && (string.matches("^[a-zA-Z]*$")));
		 }
	    
	    public static void main(String args[])
	    {
	    	try
	    	{
	    		readFiles();
	    	
	    		ArrayList<String> list = getURLs("food");
	    		
	    		if(list.size() == 0)
	    		{
	    			System.out.println("No results found");
	    		}
	    		else
	    		{
	    			for(String s : list)
	    			{
	    				System.out.println(s);
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
