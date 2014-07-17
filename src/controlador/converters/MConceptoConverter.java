package controlador.converters;



import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import controlador.Catalogo;
import controlador.CatalogoException;



import modelo.MConcepto;
import modelo.MModelo;
import modelo.MReferencia;
import modelo.Modelo;


@FacesConverter("MConceptoConverter")
public class MConceptoConverter implements Converter{

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		// TODO Auto-generated method stub
		MModelo  mm;
		
		if(arg2==null){return null;}
		if(arg2==""){return null;}
			  FacesContext facesContext = FacesContext.getCurrentInstance();
			  Catalogo catalogo   = (Catalogo) facesContext.getApplication().getVariableResolver().resolveVariable(facesContext, "catalogoBean");
			  
			 
			 try {
				MModelo mModelo=catalogo.getMModelo(Long.parseLong(arg2.split(":")[0]));
				MConcepto mConcepto=catalogo.getMConcepto(mModelo, Long.parseLong(arg2.split(":")[1]));
				return mConcepto;
			} catch (NumberFormatException | CatalogoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
					
			return null;
		
	
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		// TODO Auto-generated method stub
		if(arg2 instanceof MConcepto){ 
			MConcepto  mc=(MConcepto) arg2;
				return mc.getMmodelo().getId()+":"+mc.getIdTemporal()+":"+mc.getNombre();
			}
			else{
				return "";
			}
		
		
	}

}
