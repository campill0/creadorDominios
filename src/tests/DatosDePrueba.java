package tests;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import modelo.Concepto;
import modelo.FragmentoVideo;
import modelo.MConcepto;
import modelo.MModelo;
import modelo.MPropiedad;
import modelo.MReferencia;
import modelo.Modelo;
import modelo.ModeloException;
import modelo.Posicion;
import modelo.Propiedad;
import modelo.Referencia;
import modelo.TipoDeDato;
import modelo.Video;

import modelo.dao.DAOException;
import modelo.dao.DAOFactory;
import modelo.dao.ModeloDAO;
import modelo.dao.VideoDAO;

import modelo.dao.MModeloDAO;

import controlador.Catalogo;
import controlador.CatalogoException;
import controlador.FactoriaMetaModelos;
import controlador.FactoriaModelos;
import controlador.FactoriaVideos;

import modelo.dao.DAOFactory.Type;

public class DatosDePrueba {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		

		TipoDeDato[] datos = TipoDeDato.values();
	
		
		
		DAOFactory f=DAOFactory.getDAOFactory(Type.JPA);
		MModeloDAO mModeloDAO=f.getMModeloDAO();
		List mmodelos = mModeloDAO.findAll();
		prueba1();
	prueba2();
		prueba3();
		System.out.println();
	}
	public static void prueba3() throws DAOException, CatalogoException{
		Catalogo cat= new Catalogo();
		DAOFactory f=DAOFactory.getDAOFactory(Type.JPA);
		MModeloDAO mModeloDAO=f.getMModeloDAO();
		
		FactoriaMetaModelos fmm = FactoriaMetaModelos.instancia();
		MModelo mmodeloSeleccionado=fmm.createMModelo("actores/actrices en tv y cine");
		
		mmodeloSeleccionado=fmm.createMConcepto(mmodeloSeleccionado, "actor/actriz", 
				Arrays.asList(
						new MPropiedad("nombre",TipoDeDato.TEXT),
						new MPropiedad("edad",TipoDeDato.LONG)
				)
				, new Posicion(401,210));

		mmodeloSeleccionado=fmm.createMConcepto(mmodeloSeleccionado, "película", 
				Arrays.asList(
						new MPropiedad("nombre",TipoDeDato.TEXT)
				)
				, new Posicion(188,227));
		
		mmodeloSeleccionado=fmm.createMConcepto(mmodeloSeleccionado, "serie", 
				Arrays.asList(
						new MPropiedad("nombre",TipoDeDato.TEXT)
				)
				, new Posicion(188,327));
		
		mmodeloSeleccionado=fmm.createMConcepto(mmodeloSeleccionado, "certamen", 
				Arrays.asList(
						new MPropiedad("nombre",TipoDeDato.TEXT),
						
						new MPropiedad("fecha",TipoDeDato.DATE)
					
				)
				, new Posicion(642,95));
		mmodeloSeleccionado=fmm.createMConcepto(mmodeloSeleccionado, "resultado", 
				Arrays.asList(
						new MPropiedad("categoría",TipoDeDato.TEXT),
						
						new MPropiedad("ganador",TipoDeDato.TEXT)
					
				)
				, new Posicion(642,95));
		
		mmodeloSeleccionado=fmm.createMReferencia(mmodeloSeleccionado, cat.getMConcepto(mmodeloSeleccionado, "actor/actriz"), cat.getMConcepto(mmodeloSeleccionado, "película"), "actúa en");
		
		mmodeloSeleccionado=fmm.createMReferencia(mmodeloSeleccionado, cat.getMConcepto(mmodeloSeleccionado, "actor/actriz"), cat.getMConcepto(mmodeloSeleccionado, "serie"), "actúa en");
		
		mmodeloSeleccionado=fmm.createMReferencia(mmodeloSeleccionado, cat.getMConcepto(mmodeloSeleccionado, "certamen"), cat.getMConcepto(mmodeloSeleccionado, "resultado"), "se dieron una serie de");
		mmodeloSeleccionado=fmm.createMReferencia(mmodeloSeleccionado, cat.getMConcepto(mmodeloSeleccionado, "resultado"), cat.getMConcepto(mmodeloSeleccionado, "actor/actriz"), " de un");
		mmodeloSeleccionado=fmm.createMReferencia(mmodeloSeleccionado, cat.getMConcepto(mmodeloSeleccionado, "resultado"), cat.getMConcepto(mmodeloSeleccionado, "película"), " por una");
		mmodeloSeleccionado=fmm.createMReferencia(mmodeloSeleccionado, cat.getMConcepto(mmodeloSeleccionado, "resultado"), cat.getMConcepto(mmodeloSeleccionado, "serie"), " por una");
		
		
		mModeloDAO.save(mmodeloSeleccionado);
		
		
	}
	public static void prueba1() throws DAOException, CatalogoException, MalformedURLException{
		Catalogo cat= new Catalogo();
		DAOFactory f=DAOFactory.getDAOFactory(Type.JPA);
		MModeloDAO mModeloDAO=f.getMModeloDAO();
		
		FactoriaMetaModelos fmm = FactoriaMetaModelos.instancia();
		MModelo mmodeloSeleccionado=fmm.createMModelo("Entrevistas");
		mModeloDAO.save(mmodeloSeleccionado);
		
		mmodeloSeleccionado=fmm.createMConcepto(mmodeloSeleccionado, "persona", 
				Arrays.asList(
						new MPropiedad("nombre",TipoDeDato.TEXT),
						new MPropiedad("edad",TipoDeDato.LONG)
				)
				, new Posicion(401,210));

		mmodeloSeleccionado=fmm.createMConcepto(mmodeloSeleccionado, "lugar", 
				Arrays.asList(
						new MPropiedad("nombre",TipoDeDato.TEXT)
				)
				, new Posicion(188,227));
		
		mmodeloSeleccionado=fmm.createMConcepto(mmodeloSeleccionado, "pais", 
				Arrays.asList(
						new MPropiedad("nombre",TipoDeDato.TEXT)
				)
				, new Posicion(642,95));
		
		mmodeloSeleccionado=fmm.createMReferencia(mmodeloSeleccionado, cat.getMConcepto(mmodeloSeleccionado, "persona"), cat.getMConcepto(mmodeloSeleccionado, "persona"), "entrevista a");
		mmodeloSeleccionado=fmm.createMReferencia(mmodeloSeleccionado, cat.getMConcepto(mmodeloSeleccionado, "persona"), cat.getMConcepto(mmodeloSeleccionado, "lugar"), "esta en");
		mmodeloSeleccionado=fmm.createMReferencia(mmodeloSeleccionado, cat.getMConcepto(mmodeloSeleccionado, "persona"), cat.getMConcepto(mmodeloSeleccionado, "pais"), "es originario de");
		
		
		mModeloDAO.save(mmodeloSeleccionado);
		
		
		
		ModeloDAO modeloDAO=f.getModeloDAO();
		VideoDAO videoDAO=f.getVideoDAO();
		
		FactoriaModelos fm = FactoriaModelos.instancia();
		FactoriaVideos fv= FactoriaVideos.instancia();
		Video nuevoVideo=fv.createVideo("Un video", "http://download.blender.org/peach/bigbuckbunny_movies/BigBuckBunny_320x180.mp4",590.0F);
		videoDAO.save(nuevoVideo);
		
		Modelo modelo=fm.createModelo(mmodeloSeleccionado, "un modelo", "esto es un modelo",nuevoVideo);
		FragmentoVideo nuevoFragmentoVideo=	fv.createFragmentoVideo(modelo, nuevoVideo);
		
		modeloDAO.save(modelo);
		cat.refreshCatalogo();
		System.out.println("DatosDePrueba.prueba1()");
		//modeloDAO.remove(modelo);
		//mModeloDAO.remove(mmodeloSeleccionado);
	}
	
	public static void prueba2() throws DAOException, CatalogoException{
		Catalogo cat= new Catalogo();
		DAOFactory f=DAOFactory.getDAOFactory(Type.JPA);
		MModeloDAO mModeloDAO=f.getMModeloDAO();
		
		FactoriaMetaModelos fmm = FactoriaMetaModelos.instancia();
		MModelo mmodeloSeleccionado=fmm.createMModelo("Debate");
		
		mmodeloSeleccionado=fmm.createMConcepto(mmodeloSeleccionado, "persona", 
				Arrays.asList(
						new MPropiedad("nombre",TipoDeDato.TEXT),
						new MPropiedad("edad",TipoDeDato.LONG)
				)
				, new Posicion(400,158));

		mmodeloSeleccionado=fmm.createMConcepto(mmodeloSeleccionado, "tema", 
				Arrays.asList(
						new MPropiedad("título",TipoDeDato.TEXT)
				)
				, new Posicion(180,221));
		mmodeloSeleccionado=fmm.createMConcepto(mmodeloSeleccionado, "oficio", 
				Arrays.asList(
						new MPropiedad("nombre",TipoDeDato.TEXT)
				)
				, new Posicion(575,115));
		

		
		
		mmodeloSeleccionado=fmm.createMReferencia(mmodeloSeleccionado, cat.getMConcepto(mmodeloSeleccionado, "persona"), cat.getMConcepto(mmodeloSeleccionado, "tema"), "habla sobre");
		mmodeloSeleccionado=fmm.createMReferencia(mmodeloSeleccionado, cat.getMConcepto(mmodeloSeleccionado, "persona"), cat.getMConcepto(mmodeloSeleccionado, "oficio"), "trabaja de");
		mmodeloSeleccionado=fmm.createMReferencia(mmodeloSeleccionado, cat.getMConcepto(mmodeloSeleccionado, "persona"), cat.getMConcepto(mmodeloSeleccionado, "oficio"), "trabajó de");
	
		
		mModeloDAO.save(mmodeloSeleccionado);
	
	}

}
