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
		   
		   String[] pathnames;
		   File f = new File(fp + "/src/files/");
		   pathnames = f.list();
		   
		   
		   for (String pathname : pathnames) {
			   System.out.print(pathname.toString() + " | ");
	
			   FileReader in = new FileReader(fp + "/src/files/" + pathname.toString());
		  
	     HtmlToText parser = new HtmlToText();
	     parser.parse(in);
	     in.close();
	     
	     String[] splitString = pathname.split(".html");
	     
	     String textHTML = "http://www.bbc.com/"+splitString[0]+"\n"+parser.getText();

	     
	     File theDir = new File(fp+"/src/processedText");
	     if (!theDir.exists()){
	         theDir.mkdirs();
	     }
	     
	     BufferedWriter writerTxt = new BufferedWriter(new FileWriter(fp +"/src/processedText/"+ splitString[0] + ".txt"));
	     writerTxt.write(textHTML);
	     writerTxt.close();

		   }
	   }
	   catch (Exception e) {
	     e.printStackTrace();
	   }
	 }

}
