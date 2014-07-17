package controlador.converters;



import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import controlador.Catalogo;
//import controlador.backingBeans.ControladorPrincipal;
import controlador.backingBeans.MModeloBean;




import modelo.MModelo;
import modelo.MReferencia;


@FacesConverter("MReferenciaConverter")
public class MReferenciaConverter implements Converter{

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		// TODO Auto-generated method stub
		MReferencia  r;
		
			  FacesContext facesContext = FacesContext.getCurrentInstance();
			  
			  MModeloBean mmodeloBean    = (MModeloBean) facesContext.getApplication().getVariableResolver().resolveVariable(facesContext, "mModeloBean");
			  
			  List<MReferencia> mreferencias=mmodeloBean.getmModeloSeleccionado().getmReferencias();
			  for (MReferencia mReferencia : mreferencias) {
				if(mReferencia.getIdTemporal()==Long.parseLong(arg2)){
				return mReferencia;	
				}
			}
			
					
			return null;
		
	
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		// TODO Auto-generated method stub
		if(arg2.equals("")){
			return "";
		}
		MReferencia  r=(MReferencia) arg2;
			return r.getIdTemporal()+"";
		
		
	}

}
