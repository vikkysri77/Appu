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
import java.util.TreeMap;
import java.util.TreeSet;


public class AutoCorrector {
	
	private static Path currentRelativePath = Paths.get("");
    private static String fp = currentRelativePath.toAbsolutePath().toString();

	private static final String DIR_PATH = fp+"/src/processedText/";

	Trie trie = new Trie();
	Map<String, Integer> dictWordCount =
	 new HashMap<>();

	public void loadFiles() {
		File fileFolder = new File(DIR_PATH);

		File[] files = fileFolder.listFiles();

		for (int i = 0; i < files.length; i++) {
			if (files[i].isFile()) {

				try {
					FileReader fileReader = new FileReader(files[i]);
					BufferedReader bufferReader = new BufferedReader(fileReader);

					String line = null;

					while ((line = bufferReader.readLine()) != null) {
						String word = line.toLowerCase();
						
						//System.out.println(word);

						if (!line.contains(" ")) {
							word=word.toLowerCase();
							if(checkAlpha(word))
							{
								dictWordCount.put(word, dictWordCount.getOrDefault(word, 0) + 1);
								trie.addWord(word);
							}
						} else {
							String[] sWords = line.split("\\s");
							
							for (String sWord : sWords) {
								sWord=sWord.toLowerCase();
								if(checkAlpha(sWord))
								{
									dictWordCount.put(sWord, dictWordCount.getOrDefault(sWord, 0) + 1);
									trie.addWord(sWord);
								}
							}
						}
					}
					
					fileReader.close();
					bufferReader.close();
				} catch (IOException e) {
					System.out.println("in exception block1");
					e.printStackTrace();
				}
			}
		}
	}

/*	public void storeInDictionary(File fileName) throws IOException {

		try {
			FileReader fileReader = new FileReader(fileName);
			BufferedReader bufferReader = new BufferedReader(fileReader);

			String line = null;

			while ((line = bufferReader.readLine()) != null) {
				String word = line.toLowerCase();
				
				//System.out.println(word);

				if (!line.contains(" ")) {
					word=word.toLowerCase();
					if(isAlpha(word))
					{
						dictWordCount.put(word, dictWordCount.getOrDefault(word, 0) + 1);
						trie.addWord(word);
					}
				} else {
					String[] sWords = line.split("\\s");
					
					for (String sWord : sWords) {
						sWord=sWord.toLowerCase();
						if(isAlpha(sWord))
						{
							dictWordCount.put(sWord, dictWordCount.getOrDefault(sWord, 0) + 1);
							trie.addWord(sWord);
						}
					}
				}
			}
			
			fileReader.close();
			bufferReader.close();
		} catch (Exception e) {
			System.out.println("in exception block2");
			e.printStackTrace();
			 System.out.println(e);
		}
	} */

	 public static boolean checkAlpha(String sWord) 
	 {
		 
		 return ((sWord != null)
	                && (!sWord.equals(""))
	                && (sWord.matches("^[a-zA-Z]*$")));
	 }
	 
	public String findSimilarWord(String sInput) 
	{
		String result = "";
		if(sInput.length()==0 || sInput==null )
				return result;
		
		String sLowerInput = sInput.toLowerCase();
			
		TreeMap<Integer, TreeMap<Integer, TreeSet<String>>> map = new TreeMap<>();	
		
		TrieNode node = trie.search(sLowerInput);
		
		if(node == null) 
		{
			for (String word: dictWordCount.keySet()) 
			{
				int distance = Sequences.editDistance(word, sLowerInput);				
				TreeMap<Integer, TreeSet<String>> similarWords = map.getOrDefault(distance, new TreeMap<>());
				
				int iFrequency = dictWordCount.get(word);
				TreeSet<String> set = similarWords.getOrDefault(iFrequency, new TreeSet<>());
				set.add(word);			
				similarWords.put(iFrequency, set);
				map.put(distance, similarWords);		
			}		
			
			result = map.firstEntry().getValue().lastEntry().getValue().first();
		} 
		else if (node !=null) 
			result = sLowerInput;
			 
		return result;
	}



}
