package crawler;


import java.io.*;
import javax.swing.text.html.*;
import javax.swing.text.html.parser.*;
import java.net.*;
import java.nio.file.Path;
import java.nio.file.Paths;



public class HtmlToText  extends HTMLEditorKit.ParserCallback {
	
	private static Path currentRelativePath = Paths.get("");
    private static String fp = currentRelativePath.toAbsolutePath().toString();
	
	StringBuffer s;

	 public HtmlToText() {}

	 public void parse(Reader in) throws IOException {
	   s = new StringBuffer();
	   ParserDelegator delegator = new ParserDelegator();
	   delegator.parse(in, this, Boolean.TRUE);
	 }

	 public void handleText(char[] text, int pos) {
	   s.append(text);
	 }

	 public String getText() {
	   return s.toString();
	 }

	 public static void main (String[] args) {
	   try {
	     FileReader in = new FileReader(fp + "/src/files/" +"#.html");
	     HtmlToText parser = new HtmlToText();
	     parser.parse(in);
	     in.close();
	     String textHTML = parser.getText();
	     System.out.println(textHTML);
	     
	     // Write the text to a file  
	     BufferedWriter writerTxt = new BufferedWriter(new FileWriter("testHtml.txt"));
	     writerTxt.write(textHTML);
	     writerTxt.close();

	   }
	   catch (Exception e) {
	     e.printStackTrace();
	   }
	 }

}
