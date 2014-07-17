package tests;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.kohsuke.rngom.ast.util.CheckingSchemaBuilder;

public class PruebasUrls {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
	//analizeUrl("http://example.com:80/docs/books/tutorial/index.html?name=networking#DOWNLOADING");
	analizeUrl("https://www.youtube.com/embed/rNsrl86inpo?controls=0&showinfo=0&modestbranding=0");
	//analizeUrl("http://download.blender.org/peach/bigbuckbunny_movies/BigBuckBunny_320x180.mp4");
	analizeUrl("https://www.youtube.com/watch?id=3434343&v=C8KIGZ3-Izo");
	//analizeUrl("https://vimeo.com/91605331");
	//checkIfUrlIsAvaiable("http://static.adzerk.net/Advertisers/dca8e0dde76949728775df502369189d.jpg");
	}
public static void analizeUrl(String url) throws MalformedURLException{
	if(isLocalFile(url)){
		System.out.println("Es un fichero local.");
		return;
	}
	
	 URL aURL = new URL(url);
	 	
			 
System.out.println("protocol = " + aURL.getProtocol()); //http
System.out.println("authority = " + aURL.getAuthority()); //example.com:80
System.out.println("host = " + aURL.getHost()); //example.com
System.out.println("port = " + aURL.getPort()); //80
System.out.println("path = " + aURL.getPath()); //  /docs/books/tutorial/index.html
System.out.println("query = " + aURL.getQuery()); //name=networking
System.out.println("filename = " + aURL.getFile()); ///docs/books/tutorial/index.html?name=networking
System.out.println("ref = " + aURL.getRef()); //DOWNLOADING
System.out.println("-------------------------------------------------------------");
String videoCode="";
if(aURL.getPath().startsWith("/embed/")){
	 videoCode=aURL.getPath().split("/embed/")[1];
	
}
else if(aURL.getPath().startsWith("/watch")){
	 videoCode=aURL.getQuery().split("v=")[1];
}
System.out.println("https://www.youtube.com/embed/"+videoCode+"?controls=0&showinfo=0&modestbranding=0");
}
public static boolean isYoutube(String url) throws MalformedURLException{
	URL aURL = new URL(url);
 	if((aURL.getHost()=="www.youtube.com")||(aURL.getHost()=="youtube.com")){return true;}
 	return false;
}
public static boolean isVimeo(String url) throws MalformedURLException{
	URL aURL = new URL(url);
	if((aURL.getHost()=="www.vimeo.com")||(aURL.getHost()=="vimeo.com")){return true;}
 	return false;
}
public static boolean isDirectUrlToVideo(String url) throws MalformedURLException{
	URL aURL = new URL(url);
	if(!isLocalFile(url)&&!isVimeo(url)&&!isYoutube(url)){return true;}
 	return false;
}
public static boolean isLocalFile(String url) {
	if(url.startsWith("file://")){
		return true;
	}
	return false;
}
public static boolean checkIfUrlIsAvaiable(String urlStr) {
	
	try {
		URL url;
		url = new URL(urlStr);
	
	HttpURLConnection huc = (HttpURLConnection) url.openConnection();
	huc.setRequestMethod("HEAD");
	String contentType=huc.getContentType();
	int responseCode = huc.getResponseCode();

	if (responseCode == 200) {
	return true;
	} else {
	return false;
	}
	} catch (MalformedURLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return false;
}


}
