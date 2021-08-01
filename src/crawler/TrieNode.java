package crawler;

public class TrieNode {
	
	static final int NO_OF_LETTERS = 26;

	 

	 TrieNode[] children = new TrieNode[NO_OF_LETTERS];

	 int count;

	 boolean isEnd;


	 

	 

	 public int getValue() { 

	 return count;

	}

	 

	 public void incrementValue() {

	 count++; 

	}

	 

	 public TrieNode[] getChildren() {

	 return children;

	}

}
