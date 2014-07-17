package controlador.backingBeans;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.ocpsoft.prettytime.PrettyTime;
@ManagedBean( name="utilBean")
@RequestScoped
public class Util {
	public static String fechaStr(Date fecha, boolean invertir){
		if(fecha==null){return "NULL";}
		String year=""+(fecha.getYear()+1900);
		
		String month= fecha.getMonth() < 9 ? "0"+fecha.getMonth() : fecha.getMonth()+"";
		String day= fecha.getDate() < 9 ? "0"+fecha.getDate() : fecha.getDate()+"";
		String hours = fecha.getHours() < 9 ? "0"+fecha.getHours() : fecha.getHours()+"";
		String minutes = fecha.getMinutes() < 9 ? "0"+fecha.getMinutes() : fecha.getMinutes()+"";
		String seconds = fecha.getSeconds() < 9 ? "0"+fecha.getSeconds() : fecha.getSeconds()+"";
		if(invertir){
			return year+"/"+month+"/"+day+ " " + hours+":"+minutes+":"+seconds;
		}
		else{
			return day+"/"+month+"/"+year+ " " + hours+":"+minutes+":"+seconds;
		}
	}

	public static String pretty(Date fecha){
		PrettyTime p = new PrettyTime(new Locale("es"));
		return p.format(fecha);
		

	}
	public static String secondToHMS(float time){
		Calendar c1= Calendar.getInstance();
		c1.setTimeInMillis(0);
		c1.set(Calendar.YEAR,2000);
		c1.set(Calendar.HOUR_OF_DAY, 0);
		
		c1.add(Calendar.MILLISECOND, (int) (time * 1000));

		int h = c1.get(Calendar.HOUR_OF_DAY);
		int m = c1.get(Calendar.MINUTE);
		int s = c1.get(Calendar.SECOND);
		int ms = c1.get(Calendar.MILLISECOND);
		 
		String hStr,mStr,sStr,msStr;
		hStr = (h <= 9) ? "0"+h : ""+h;
		mStr = (m <= 9) ? "0"+m : ""+m;
		sStr = (s <= 9) ? "0"+s : ""+s;
		
		
		return hStr+":"+mStr+":"+sStr;
	}
	public static void facesMessage(Severity severity,String title, String text){
		 FacesContext context = FacesContext.getCurrentInstance();
		 
		    context.addMessage(null, new FacesMessage(severity,title,text));
			 
	}
	
	public void sendFacesMessage(){
		System.out.println("sendFacesMessage");
		FacesContext context = FacesContext.getCurrentInstance();
		  Map map = context.getExternalContext().getRequestParameterMap();
		  
		  String message=   (String) map.get("message");
		  Severity sev;
		  String severity=   (String) map.get("severity");
		  switch (severity) {
		case "error":
			sev=FacesMessage.SEVERITY_ERROR;
			break;
		case "info":
			sev=FacesMessage.SEVERITY_INFO;
			break;
		case "fatal":
			sev=FacesMessage.SEVERITY_FATAL;
			break;
		case "warn":
			sev=FacesMessage.SEVERITY_WARN;
			break;
		default:
			sev=FacesMessage.SEVERITY_INFO;
			break;
		}
		  facesMessage(sev,message,message);
		  
	
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
	
}
