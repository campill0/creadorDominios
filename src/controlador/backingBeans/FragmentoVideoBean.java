package controlador.backingBeans;

import java.io.Serializable;
import java.util.Calendar;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.component.slider.Slider;
import org.primefaces.event.SlideEndEvent;

@ManagedBean( name="fragmentoVideoBean")
@ViewScoped
public class FragmentoVideoBean implements Serializable {
/**
	 * 
	 */
	private static final long serialVersionUID = -6702161226374237723L;
	private ModeloBean modeloBean;
		

public FragmentoVideoBean() {
		super();
		FacesContext facesContext = FacesContext.getCurrentInstance();
		modeloBean   = (ModeloBean) facesContext.getApplication().getVariableResolver().resolveVariable(facesContext, "modeloBean");
		
		}






public float getInicio() {
	if(modeloBean.isHayModeloSeleccionado()){
		return modeloBean.getModeloSeleccionado().getFragmentoVideo().getInicio();
	}
	return 0.0F;
}







public void setInicio(float inicio) {
	if(modeloBean.isHayModeloSeleccionado()){
		 modeloBean.getModeloSeleccionado().getFragmentoVideo().setInicio(inicio);
	}
	
}







public float getFin() {
	if(modeloBean.isHayModeloSeleccionado()){
		return modeloBean.getModeloSeleccionado().getFragmentoVideo().getFin();
	}
	return 0.0F;
}







public void setFin(float fin) {
	if(modeloBean.isHayModeloSeleccionado()){
		 modeloBean.getModeloSeleccionado().getFragmentoVideo().setFin(fin);
	}
}







public void fake(){
	System.out.println("FragmentoVideoBean.fake()");
}








public void onSlideStart(SlideEndEvent event) {  
	 
    FacesMessage msg = new FacesMessage("Slide start", "Value: " + event.getValue());  
    FacesContext.getCurrentInstance().addMessage(null, msg);  
}  

public void onSlideEnd(SlideEndEvent event) {  
	Slider slider = (Slider) event.getSource();
	slider.getMaxValue();
	String str="min:" + slider.getMinValue()  +
			"max:" + slider.getMaxValue()  +
			"for:" + slider.getFor()+
			"current:" + event.getValue()+
			"component:" + event.getComponent();
	
	
    FacesMessage msg = new FacesMessage("Slide Ended", "Value: " + str);  
    FacesContext.getCurrentInstance().addMessage(null, msg);  
}  

public void onSlide(SlideEndEvent event) {  
	 
    FacesMessage msg = new FacesMessage("Slide", "Value: " + event.getValue());  
    FacesContext.getCurrentInstance().addMessage(null, msg);  
}  
public String secondToHMS(float time){
	Calendar c1= Calendar.getInstance();
	c1.setTimeInMillis(0);
	c1.set(Calendar.YEAR,2000);
	c1.set(Calendar.HOUR_OF_DAY, 0);
	
	c1.add(Calendar.MILLISECOND, (int) (time * 1000));

	int h = c1.get(Calendar.HOUR_OF_DAY);
	int m = c1.get(Calendar.MINUTE);
	int s = c1.get(Calendar.SECOND);
	int ms = c1.get(Calendar.MILLISECOND);
	
	
	return h+":"+m+":"+s+"."+ms;
}

public String getStartFormated(){
	
	return secondToHMS(getInicio());
}

public String getEndFormated(){
	
	return secondToHMS(getFin());
}


}