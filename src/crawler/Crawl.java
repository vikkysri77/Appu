package crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Crawl {
    private static final int MAX_DEPTH = 2;
    private HashSet<String> links;
    private Path currentRelativePath = Paths.get("");
    private String fp = currentRelativePath.toAbsolutePath().toString();
    
    

    
    List<String> link = new ArrayList<String>();

    public Crawl() {
        links = new HashSet<>();
    }

    public void getPageLinks(String URL, int depth) throws Exception {
    	 
	     File theDir1 = new File(fp+"/src/files");
	     if (!theDir1.exists()){
	         theDir1.mkdirs();
	     }
        if ((!links.contains(URL) && (depth < MAX_DEPTH))) {
            System.out.println(+ depth + " [" + URL + "]");
            
            link.add(URL);
           
            try {
            
//                links.add(URL);

                Document document = Jsoup.connect(URL).get();
                Elements linksOnPage = document.select("a[href]");

                depth++;
                for (Element page : linksOnPage) {
                    getPageLinks(page.attr("abs:href"), depth);
                }
            } catch (IOException e) {
                System.err.println("For '" + URL + "': " + e.getMessage());
            }
            for (String s : link) {
            	download(URL, fp+"/src/files", s);
            }
            
        }
        

        
        }

    public static void main(String[] args) throws Exception {
        new Crawl().getPageLinks("https://www.bbc.com/", 0);
        
    }
    
    public static void download(String urlPath , String targetDirectory,String name) throws Exception {
			     
			System.out.println("url:"+ urlPath);
			URL url = new URL(urlPath);
			HttpURLConnection http = (HttpURLConnection)url.openConnection();
			http.setConnectTimeout(3000);
			        // Set User-Agent to avoid being intercepted
			http.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)");
			String contentType = http.getContentType();
			System.out.println("contentType: "+ contentType);
			        // Get the file size
			long length = http.getContentLengthLong();
			        System.out.println("File size:"+(length / 1024)+"KB");
			        // Get the file name
			String fileName = getFileName(http , urlPath);
			String newName = name;
			InputStream inputStream = http.getInputStream();
			byte[] buff = new byte[1024*10];
			File file=new File(targetDirectory,fileName);
			OutputStream out = new FileOutputStream(file);
			int len ;
			        int count = 0; // count
			while((len = inputStream.read(buff)) != -1) {
			   out.write(buff, 0, len);
			   out.flush();
			   ++count ;
			}
			System.out.println("count:"+ count);
			        // Close the resource
			out.close();
			inputStream.close();
			http.disconnect();
}
    
    private static String getFileName(HttpURLConnection http , String urlPath) throws UnsupportedEncodingException {
        String headerField = http.getHeaderField("Content-Disposition");
        String fileName = null ;
        if(null != headerField) {
            String decode = URLDecoder.decode(headerField, "UTF-8");
            fileName = decode.split(";")[1].split("=")[1].replaceAll("\"", "");
                         System.out.println("The file name is: "+ fileName);
        }
        if(null == fileName) {
                         // Try to get the file name from the url
            String[] arr  = urlPath.split("/");
            fileName = arr[arr.length - 1];
                         System.out.println("Get file name in url:"+ fileName);
        }
        fileName = fileName + ".html";
        return fileName;
    }

}