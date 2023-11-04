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
import co.inphotech.marshando.auxiliarcontable.externos.vo.Gasto;
import co.inphotech.marshando.auxiliarcontable.usuario.vo.Empresa;
import co.inphotech.marshando.auxiliarcontable.util.UtilMundo;

public class GastoDAO {

	
public static final String SERVICIO = "/gasto"; 
	

	
	public static List<Gasto> getGastos(Empresa empresa, int mes, int anio) throws Exception{
		List<Gasto> gastos = new ArrayList<Gasto>();
		
		String codigoEmpresa = empresa.getCodigo();
		
		String token = ConectorAutorizacionAplicacion.getInstancia().getToken( codigoEmpresa );
		String urlGastoDefecto = System.getenv("gastos_url");
		HttpRequest request = Unirest.get( urlGastoDefecto + SERVICIO ).header("authorization", token);
		
		
		System.out.println( urlGastoDefecto + SERVICIO );
		
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
		JSONArray jgastos = new JSONArray(data.getBody());
		
		for( int i = 0; i < jgastos.length();i++ ) {
			Gasto gasto = armarGasto(empresa, jgastos.getJSONObject(i));
			gastos.add(gasto);
		}
		
		
		
		
		return gastos;
	}
	
	

	public static Gasto armarGasto( Empresa empresa, JSONObject jgasto) throws Exception{
		long id = jgasto.getLong("id");

		
		String descripcion = "";
		if( jgasto.has("descripcion") ) {
			descripcion = jgasto.getString("descripcion");
		}
		
		
		String razonSocialTercero = "";
		if( jgasto.has("razonSocialTercero") ) {
			razonSocialTercero = jgasto.getString("razonSocialTercero");
		}
		String consecutivo = "";
		if( jgasto.has("consecutivo") ) {
			consecutivo = jgasto.getString("consecutivo");
		}
		
		String nitTercero = "";
		if( jgasto.has("nitTercero") ) {
			nitTercero = jgasto.getString("nitTercero");
		}
		
		Date fecha = null;
		if( jgasto.has("sfecha") ) {
			fecha = UtilMundo.stringToDateHTMLInput( jgasto.getString("sfecha") );
		}
		
		Date utimaFechaActualizacion = null;
		if( jgasto.has("sultimaFechaActualizacion") ) {
			utimaFechaActualizacion = UtilMundo.stringToDateHTMLHoraInput( jgasto.getString("sultimaFechaActualizacion") );
		}
		
		double valor = 0;
		if( jgasto.has("valor") ) {
			valor = jgasto.getDouble("valor");
		}
		
		double saldo = 0;
		if( jgasto.has("saldo") ) {
			saldo = jgasto.getDouble("saldo");
		}
		
		
		return new Gasto(id, empresa.getCodigo(), consecutivo, descripcion, fecha, utimaFechaActualizacion, razonSocialTercero, nitTercero, valor, saldo);
	}
}
