package modelo;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable 
public class Posicion implements Serializable {

	
	private static final long serialVersionUID = -2473126574182423570L;
	private float x;
	
	
	public float getX() {
		return x;
	}
	public void setX(float x) {
		this.x = x;
	}
	public float getY() {
		return y;
	}
	public void setY(float y) {
		this.y = y;
	}
	private float y;
	public Posicion() {
		super();
	x=0;
	y=0;
	}
	public Posicion(float x, float y) {
		super();
		this.x = x;
		this.y = y;
	}
	
	
}
