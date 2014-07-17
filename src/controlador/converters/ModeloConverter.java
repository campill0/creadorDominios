package controlador.converters;



import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import controlador.Catalogo;



import modelo.MModelo;
import modelo.MReferencia;
import modelo.Modelo;


@FacesConverter("ModeloConverter")
public class ModeloConverter implements Converter{

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		// TODO Auto-generated method stub
		Modelo  m;
		
		if(arg2==null){return null;}
		if(arg2==""){return null;}
			  FacesContext facesContext = FacesContext.getCurrentInstance();
			  Catalogo catalogo   = (Catalogo) facesContext.getApplication().getVariableResolver().resolveVariable(facesContext, "catalogoBean");
			  
			  List<Modelo> modelos=catalogo.getModelos();
			  for (Modelo modelo : modelos) {
				  
				if(modelo.getIdTemporal()==Long.parseLong(arg2)){
				return modelo;	
				}
			}
			
					
			return null;
		
	
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		// TODO Auto-generated method stub
		
		if(arg2 instanceof Modelo){ 
		Modelo  m=(Modelo) arg2;
			return m.getIdTemporal()+"";
		}
		else{
			return "";
		}
		
		
	}

}
