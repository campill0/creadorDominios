package controlador.backingBeans;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
@ManagedBean(name="generadorIdsBean")
@SessionScoped
public class GeneradorIds  implements Serializable{

	private Long idTemp;

	
	public GeneradorIds(){
		idTemp=1L;
	}

	public Long getNextIdTmp(){
		return idTemp++;
	}
	
}
