package modelo;

import java.util.ArrayList;
import java.util.List;

public enum TipoDeDato {
	TEXT ("java.lang.String","Texto"),
	LONG_TEXT ("java.lang.String","Texto largo"),
	BOOLEAN ("java.lang.Boolean","Verdadero o falso"),
	LONG ("java.lang.Long","Número entero"),
	DOUBLE ("java.lang.Double","Número real"),
	DATE ("java.util.Date","Fecha");

    private final String texto;       
    private final String etiqueta;
    private TipoDeDato(String texto,String etiqueta) {
        this.texto = texto;
        this.etiqueta=etiqueta;
    }
    
    public String getTexto() {
		return texto;
	}

	public String getEtiqueta() {
		return etiqueta;
	}

	public String getClave(){
    	return this.name();
    }
    public boolean equalsName(String otherName){
        return (otherName == null)? false:texto.equals(otherName);
    }

    public String toString(){
       return texto;
    }
  
    
}