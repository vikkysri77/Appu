package crawler;

public class Trie {


	TrieNode root = new TrieNode();

	public void addWord(String sWord)

	{

	 TrieNode temp = root;

	 

	 sWord = sWord.toLowerCase();

	 

	for (int i = 0; i < sWord.length(); i++)

	    {

	int index = sWord.charAt(i) - 'a';

	 if (temp.children[index] == null)

	 temp.children[index] = new TrieNode();

	   

	 temp = temp.children[index];

	 

	    }
	
	
	
	temp.isEnd = true;

	 

	}

	 

	 public TrieNode search(String sWord)

	{

	 TrieNode temp = root;

	    for (int i = 0; i < sWord.length(); i++)

	    {

	    int index = sWord.charAt(i) - 'a';

	    

	        if (temp.children[index] == null)

	        return null;

	         temp = temp.children[index];

	    }

	    if( temp != null && temp.isEnd)

	    return temp;

	     

	 return null;

	}
	
	
	
	

	
	
	
	
	
	
	
	

}
