package co.inphotech.marshando.auxiliarcontable.externos.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.amazonaws.util.json.JSONArray;
import com.amazonaws.util.json.JSONObject;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.request.HttpRequest;

import co.inphotech.marshando.auxiliarcontable.conectores.ConectorAutorizacionAplicacion;
import co.inphotech.marshando.auxiliarcontable.externos.vo.ItemNotaCredito;
import co.inphotech.marshando.auxiliarcontable.externos.vo.NotaCredito;
import co.inphotech.marshando.auxiliarcontable.usuario.vo.Empresa;
import co.inphotech.marshando.auxiliarcontable.util.UtilMundo;

public class NotaCreditoDAO {

	
	public static final String SERVICIO = "/nota-credito"; 
	
	public static NotaCredito getNotaCredito(Empresa empresa, long id) throws Exception{
		NotaCredito notaCredito = null;
		
		String codigoEmpresa = empresa.getCodigo();
		
		String token = ConectorAutorizacionAplicacion.getInstancia().getToken( codigoEmpresa );
		String urlNotaCredito = System.getenv("facturacion_url");
		HttpResponse<String>data = Unirest.get( urlNotaCredito + SERVICIO + "/"  + id).header("authorization", token).asString();
		
		
		if(data.getStatus() != 200) {
			throw new Exception(data.getBody());
		}
		JSONObject jnotaCredito = new JSONObject(data.getBody());
		notaCredito = armarNotaCreditoConItems(empresa, jnotaCredito);
		
		
		return notaCredito;
	}
	public static List<NotaCredito> getNotaCreditos(Empresa empresa, int anio, int mes) throws Exception{
		List<NotaCredito> notaCreditos = new ArrayList<NotaCredito>();
		
		String codigoEmpresa = empresa.getCodigo();
		
		String token = ConectorAutorizacionAplicacion.getInstancia().getToken( codigoEmpresa );
		String urlNotaCredito = System.getenv("facturacion_url");
		HttpRequest request = Unirest.get( urlNotaCredito + SERVICIO ).header("authorization", token).queryString("estadoCierre", "2");
		
		if( mes != -1 ) {
			request = request.queryString("mesUltimaFechaActualizacion", mes + "" );
		}
		if( anio != -1 ) {
			request = request.queryString("anioUltimaFechaActualizacion", anio + "" );
		}
		
		
		HttpResponse<String>data = request.asString();
		System.out.println( data.getBody() );
		if(data.getStatus() != 200) {
			throw new Exception(data.getBody());
		}
		JSONArray jnotaCreditos = new JSONArray(data.getBody());
		
		System.out.println( jnotaCreditos );
		
		for( int i = 0; i < jnotaCreditos.length();i++ ) {
			NotaCredito notaCredito = armarNotaCredito(empresa, jnotaCreditos.getJSONObject(i));
			notaCreditos.add(notaCredito);
		}
		
		return notaCreditos;
	}
	
	public static List<NotaCredito> getNotaCreditos(Empresa empresa, Date ultimaFechaActualizacion) throws Exception{
		List<NotaCredito> notaCreditos = new ArrayList<NotaCredito>();
		
		String codigoEmpresa = empresa.getCodigo();
		
		String token = ConectorAutorizacionAplicacion.getInstancia().getToken( codigoEmpresa );
		String urlNotaCredito = System.getenv("facturacion_url");
		HttpResponse<String>data = Unirest.get( urlNotaCredito + SERVICIO ).header("authorization", token).routeParam("estadoCierre", "2").routeParam("ultimaFechaActualizacion", UtilMundo.dateToStringInputHTML(ultimaFechaActualizacion)).asString();
		
		
		if(data.getStatus() != 200) {
			throw new Exception(data.getBody());
		}
		JSONArray jnotaCreditos = new JSONArray(data.getBody());
		
		for( int i = 0; i < jnotaCreditos.length();i++ ) {
			NotaCredito notaCredito = armarNotaCredito(empresa, jnotaCreditos.getJSONObject(i));
			notaCreditos.add(notaCredito);
		}
		
		
		
		
		return notaCreditos;
	}
	public static List<NotaCredito> getNotaCreditos(Empresa empresa) throws Exception{
		List<NotaCredito> notaCreditos = new ArrayList<NotaCredito>();
		
		String codigoEmpresa = empresa.getCodigo();
		
		String token = ConectorAutorizacionAplicacion.getInstancia().getToken( codigoEmpresa );
		String urlNotaCredito = System.getenv("facturacion_url");
		HttpResponse<String>data = Unirest.get( urlNotaCredito + SERVICIO ).header("authorization", token).asString();
		
		
		if(data.getStatus() != 200) {
			throw new Exception(data.getBody());
		}
		JSONArray jnotaCreditos = new JSONArray(data.getBody());
		
		for( int i = 0; i < jnotaCreditos.length();i++ ) {
			NotaCredito notaCredito = armarNotaCredito( empresa, jnotaCreditos.getJSONObject(i));
			notaCreditos.add(notaCredito);
		}
		
		
		
		
		return notaCreditos;
	}
	
	

	public static NotaCredito armarNotaCredito( Empresa empresa, JSONObject jnotaCredito) throws Exception{
		long id = jnotaCredito.getLong("id");
		String consecutivo = jnotaCredito.getString("prefijo") + jnotaCredito.getString("consecutivo");
		Date fecha = UtilMundo.stringToDateHTMLInput( jnotaCredito.getString("sfecha") );
		Date utimaFechaActualizacion = UtilMundo.stringToDateHTMLHoraInput( jnotaCredito.getString("sultimaFechaActualizacion") );
		
		
		String razonSocialTercero = jnotaCredito.getString("razonSocialTercero");
		String nitTercero = jnotaCredito.getString("nitTercero");
		
		double valor = jnotaCredito.getDouble("valor");
		double saldo = jnotaCredito.getDouble("saldo");
		
		
		return new NotaCredito(id, empresa.getCodigo(), fecha, consecutivo, razonSocialTercero, nitTercero, valor, saldo, utimaFechaActualizacion);
	}
	
	public static NotaCredito armarNotaCreditoConItems(Empresa empresa, JSONObject jnotaCredito) throws Exception{
		NotaCredito notaCredito = armarNotaCredito(empresa, jnotaCredito);
		JSONArray jitemsNotaCredito = jnotaCredito.getJSONArray("items");
		
		for( int i = 0; i <  jitemsNotaCredito.length(); i++) {
			JSONObject jitemNotaCredito = jitemsNotaCredito.getJSONObject(i);
			ItemNotaCredito itemNotaCredito = ItemNotaCreditoDAO.armarItemNotaCredito(jitemNotaCredito);
			notaCredito.insertarItemNotaCredito(itemNotaCredito);
		}
		
		
		
		return notaCredito;
	}
}
