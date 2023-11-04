package co.inphotech.marshando.auxiliarcontable.externos.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.amazonaws.util.json.JSONArray;
import com.amazonaws.util.json.JSONObject;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.request.HttpRequest;

import co.inphotech.marshando.auxiliarcontable.conectores.ConectorAutorizacionAplicacion;
import co.inphotech.marshando.auxiliarcontable.externos.vo.PeriodoTrabajador;
import co.inphotech.marshando.auxiliarcontable.usuario.vo.Empresa;
import co.inphotech.marshando.auxiliarcontable.util.UtilMundo;

public class PeriodoTrabajadorDAO {

	
	public static final String SERVICIO = "/periodo-trabajador"; 
	

	public static List<PeriodoTrabajador> getPeriodosTrabajadores(Empresa empresa, int mes, int anio) throws Exception{
		List<PeriodoTrabajador> periodosTrabajadores = new ArrayList<PeriodoTrabajador>();
		
		String codigoEmpresa = empresa.getCodigo();
		
		String token = ConectorAutorizacionAplicacion.getInstancia().getToken( codigoEmpresa );
		String urlFactura = System.getenv("nomina_url");
		HttpRequest request = Unirest.get( urlFactura + SERVICIO ).header("authorization", token);
		
		if( mes != -1 ) {
			request = request.queryString("mesUltimaFechaActualizacion", mes + "" );
		}
		if( anio != -1 ) {
			request = request.queryString("anioUltimaFechaActualizacion", anio + "" );
		}
		
		
		HttpResponse<String>data = request.asString();
		if(data.getStatus() != 200) {
			throw new Exception(data.getBody());
		}
		JSONArray jperiodosTrabajadores = new JSONArray(data.getBody());
		
		for( int i = 0; i < jperiodosTrabajadores.length();i++ ) {
			PeriodoTrabajador periodoTrabajador = armarPeriodoTrabajador(empresa, jperiodosTrabajadores.getJSONObject(i));
			periodosTrabajadores.add(periodoTrabajador);
		}
		
		return periodosTrabajadores;
	}
	
	public static PeriodoTrabajador getPeriodoTrabajador(Empresa empresa, long id) throws Exception{
		String codigoEmpresa = empresa.getCodigo();
		
		String token = ConectorAutorizacionAplicacion.getInstancia().getToken( codigoEmpresa );
		String urlFactura = System.getenv("nomina_url");
		HttpRequest request = Unirest.get( urlFactura + SERVICIO + "/" + id).header("authorization", token);
		
		
		HttpResponse<String>data = request.asString();
		if(data.getStatus() != 200) {
			throw new Exception(data.getBody());
		}
		JSONObject jperiodoTrabajador = new JSONObject(data.getBody());
		System.out.println( jperiodoTrabajador );
		PeriodoTrabajador periodoTrabajador = armarPeriodoTrabajadorConNovedades(empresa, jperiodoTrabajador);
		
		return periodoTrabajador;
	}
	

	public static PeriodoTrabajador armarPeriodoTrabajador(Empresa empresa, JSONObject jperiodoTrabajador) throws Exception{
		long id = jperiodoTrabajador.getLong("id");
		
		JSONObject jperiodo = jperiodoTrabajador.getJSONObject("periodo");
		JSONObject jtrabajador = jperiodoTrabajador.getJSONObject("trabajador");
		JSONObject jcontrato = jtrabajador.getJSONObject("contrato");
		
		
		long idTrabajador = jtrabajador.getLong("id");
		long idContrato = jcontrato.getLong("id");
		long idPeriodo = jperiodo.getLong("id");
		
		String nombreTrabajador = jtrabajador.getString("nombres") + " " + jtrabajador.getString("apellidos");
		String documentoTrabajador = jtrabajador.getString("documento");
		
		Date utimaFechaActualizacion = UtilMundo.stringToDateHTMLHoraInput( jperiodoTrabajador.getString("sultimaFechaActualizacion") );
		Calendar cfecha = Calendar.getInstance();
		cfecha.set(Calendar.YEAR, jperiodo.getInt("anio"));
		cfecha.set(Calendar.MONTH, jperiodo.getInt("mes") - 1);
		Date fecha = cfecha.getTime();
		
		String periodo = jperiodo.getInt("anio") + "-" + jperiodo.getInt("mes") +  " " + nombreTrabajador;
		String nombreContrato = jcontrato.getString("nombre");
		String nombrePeriodo = jperiodo.getString("nombre");
		
		return new PeriodoTrabajador(id, empresa.getCodigo(), idTrabajador, idContrato, idPeriodo, nombreTrabajador, documentoTrabajador, periodo, utimaFechaActualizacion, fecha, nombreContrato, nombrePeriodo);
	}
	
	public static PeriodoTrabajador armarPeriodoTrabajadorConNovedades( Empresa empresa, JSONObject jperiodoTrabajador) throws Exception{
		PeriodoTrabajador periodoTrabajador = armarPeriodoTrabajador(empresa, jperiodoTrabajador);
		if( jperiodoTrabajador.has("periodoNovedadesTrabajadorNomina") ) {
			JSONArray jnovedades = jperiodoTrabajador.getJSONArray("periodoNovedadesTrabajadorNomina");
			
			for( int i = 0; i < jnovedades.length(); i++) {
				
				JSONObject jnovedadPeriodo = jnovedades.getJSONObject( i );
				long id = jnovedadPeriodo.getLong("id");
				String nombre = jnovedadPeriodo.getString("nombre");
				double valor = jnovedadPeriodo.getDouble("valor");
				
				JSONObject jnovedad = jnovedadPeriodo.getJSONObject("novedadNomina");
				String codigoNovedad = jnovedad.getString("codigo");
				
				JSONObject jtercero = jnovedadPeriodo.getJSONObject("terceroNomina");
				String codigoTercero = jtercero.getString("codigo");
				
				PeriodoNovedadTrabajadorNomina periodoNovedadTrabajadorNomina = new PeriodoNovedadTrabajadorNomina(id, codigoNovedad, codigoTercero, nombre, valor);
				periodoTrabajador.insertarPeriodoNovedadTrabajadorNomina(periodoNovedadTrabajadorNomina);
				
			}
		}
		
		return periodoTrabajador;
	}
}
