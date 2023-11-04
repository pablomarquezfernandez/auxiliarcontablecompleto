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
import co.inphotech.marshando.auxiliarcontable.externos.vo.ItemNotaDebito;
import co.inphotech.marshando.auxiliarcontable.externos.vo.NotaCredito;
import co.inphotech.marshando.auxiliarcontable.externos.vo.NotaDebito;
import co.inphotech.marshando.auxiliarcontable.usuario.vo.Empresa;
import co.inphotech.marshando.auxiliarcontable.util.UtilMundo;

public class NotaDebitoDAO {

	
	public static final String SERVICIO = "/nota-debito"; 
	
	public static NotaDebito getNotaDebito(Empresa empresa, long id) throws Exception{
		NotaDebito notaDebito = null;
		
		String codigoEmpresa = empresa.getCodigo();
		
		String token = ConectorAutorizacionAplicacion.getInstancia().getToken( codigoEmpresa );
		String urlNotaDebito = System.getenv("facturacion_url");
		HttpResponse<String>data = Unirest.get( urlNotaDebito + SERVICIO + "/"  + id).header("authorization", token).asString();
		
		
		if(data.getStatus() != 200) {
			throw new Exception(data.getBody());
		}
		JSONObject jnotaDebito = new JSONObject(data.getBody());
		notaDebito = armarNotaDebitoConItems(empresa, jnotaDebito);
		
		
		return notaDebito;
	}
	public static List<NotaDebito> getNotaDebitos(Empresa empresa, int anio, int mes) throws Exception{
		List<NotaDebito> notaDebitos = new ArrayList<NotaDebito>();
		
		String codigoEmpresa = empresa.getCodigo();
		
		String token = ConectorAutorizacionAplicacion.getInstancia().getToken( codigoEmpresa );
		String urlNotaDebito = System.getenv("facturacion_url");
		HttpRequest request = Unirest.get( urlNotaDebito + SERVICIO ).header("authorization", token).queryString("estadoCierre", "2");
		
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
		JSONArray jnotaDebitos = new JSONArray(data.getBody());
		
		System.out.println( jnotaDebitos );
		
		for( int i = 0; i < jnotaDebitos.length();i++ ) {
			NotaDebito notaDebito = armarNotaDebito(empresa, jnotaDebitos.getJSONObject(i));
			notaDebitos.add(notaDebito);
		}
		
		return notaDebitos;
	}
	
	public static List<NotaDebito> getNotaDebitos(Empresa empresa, Date ultimaFechaActualizacion) throws Exception{
		List<NotaDebito> notaDebitos = new ArrayList<NotaDebito>();
		
		String codigoEmpresa = empresa.getCodigo();
		
		String token = ConectorAutorizacionAplicacion.getInstancia().getToken( codigoEmpresa );
		String urlNotaDebito = System.getenv("facturacion_url");
		HttpResponse<String>data = Unirest.get( urlNotaDebito + SERVICIO ).header("authorization", token).routeParam("estadoCierre", "2").routeParam("ultimaFechaActualizacion", UtilMundo.dateToStringInputHTML(ultimaFechaActualizacion)).asString();
		
		
		if(data.getStatus() != 200) {
			throw new Exception(data.getBody());
		}
		JSONArray jnotaDebitos = new JSONArray(data.getBody());
		
		for( int i = 0; i < jnotaDebitos.length();i++ ) {
			NotaDebito notaDebito = armarNotaDebito(empresa, jnotaDebitos.getJSONObject(i));
			notaDebitos.add(notaDebito);
		}
		
		
		
		
		return notaDebitos;
	}
	public static List<NotaDebito> getNotaDebitos(Empresa empresa) throws Exception{
		List<NotaDebito> notaDebitos = new ArrayList<NotaDebito>();
		
		String codigoEmpresa = empresa.getCodigo();
		
		String token = ConectorAutorizacionAplicacion.getInstancia().getToken( codigoEmpresa );
		String urlNotaDebito = System.getenv("facturacion_url");
		HttpResponse<String>data = Unirest.get( urlNotaDebito + SERVICIO ).header("authorization", token).asString();
		
		
		if(data.getStatus() != 200) {
			throw new Exception(data.getBody());
		}
		JSONArray jnotaDebitos = new JSONArray(data.getBody());
		
		for( int i = 0; i < jnotaDebitos.length();i++ ) {
			NotaDebito notaDebito = armarNotaDebito( empresa, jnotaDebitos.getJSONObject(i));
			notaDebitos.add(notaDebito);
		}
		
		
		
		
		return notaDebitos;
	}
	
	

	public static NotaDebito armarNotaDebito( Empresa empresa, JSONObject jnotaDebito) throws Exception{
		long id = jnotaDebito.getLong("id");
		String consecutivo = jnotaDebito.getString("prefijo") + jnotaDebito.getString("consecutivo");
		Date fecha = UtilMundo.stringToDateHTMLInput( jnotaDebito.getString("sfecha") );
		Date utimaFechaActualizacion = UtilMundo.stringToDateHTMLHoraInput( jnotaDebito.getString("sultimaFechaActualizacion") );
		
		
		String razonSocialTercero = jnotaDebito.getString("razonSocialTercero");
		String nitTercero = jnotaDebito.getString("nitTercero");
		
		double valor = jnotaDebito.getDouble("valor");
		double saldo = jnotaDebito.getDouble("saldo");
		
		
		return new NotaDebito(id, empresa.getCodigo(), fecha, consecutivo, razonSocialTercero, nitTercero, valor, saldo, utimaFechaActualizacion);
	}
	
	public static NotaDebito armarNotaDebitoConItems(Empresa empresa, JSONObject jnotaDebito) throws Exception{
		NotaDebito notaDebito = armarNotaDebito(empresa, jnotaDebito);
		JSONArray jitemsNotaDebito = jnotaDebito.getJSONArray("items");
		
		for( int i = 0; i <  jitemsNotaDebito.length(); i++) {
			JSONObject jitemNotaDebito = jitemsNotaDebito.getJSONObject(i);
			ItemNotaDebito itemNotaDebito = ItemNotaDebitoDAO.armarItemNotaDebito(jitemNotaDebito);
			notaDebito.insertarItemNotaCredito(itemNotaDebito);
		}
		return notaDebito;
	}
}
