package co.inphotech.marshando.auxiliarcontable.accionescontables.dao;

import java.util.ArrayList;
import java.util.List;

import com.amazonaws.util.json.JSONArray;
import com.amazonaws.util.json.JSONObject;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;

import co.inphotech.marshando.auxiliarcontable.accionescontables.vo.AsientoContable;
import co.inphotech.marshando.auxiliarcontable.conectores.ConectorAutorizacionAplicacion;
import co.inphotech.marshando.auxiliarcontable.usuario.vo.Empresa;

public class AsientoContableDAO {

	
	public static final String SERVICIO = "/simular-accion-contable"; 
	public static final String SERVICIO_SIN_MEZCLAR = "/simular-accion-contable-sin-mezclar"; 
	
	public static List<AsientoContable> getAsientosContables(Empresa empresa, JSONArray jdatos) throws Exception{
		List<AsientoContable>asientosContables = new ArrayList<AsientoContable>();
		
		String codigoEmpresa = empresa.getCodigo();
		
		String token = ConectorAutorizacionAplicacion.getInstancia().getToken( codigoEmpresa );
		String urlAccionContable = System.getenv("contabilidad_url");
		HttpResponse<String>data = Unirest.post( urlAccionContable + SERVICIO_SIN_MEZCLAR ).header("authorization", token).body(jdatos.toString()).asString();
		
		if(data.getStatus() != 200) {
			throw new Exception(data.getBody());
		}
		JSONObject jobjeto = new JSONObject( data.getBody() );
		if( !jobjeto.getString("respuesta") .equals("exito") )  {
			throw new Exception(jobjeto.getString("mensaje"));
		}
		
		JSONArray jasientosContables = jobjeto.getJSONArray("objeto");
		
		for( int i = 0; i < jasientosContables.length();i++ ) {
			AsientoContable asientoContable = armarAsientoContable(jasientosContables.getJSONObject(i) );
			asientosContables.add( asientoContable );
		}
		
		
		return asientosContables;
	}
	
	public static AsientoContable armarAsientoContable( JSONObject jasientoContable ) throws Exception{
	
		String codigoCuentaPUC = "";
		if( jasientoContable.has("codigoCuentaPUC") ) {
			codigoCuentaPUC = jasientoContable.getString("codigoCuentaPUC");
		}
		
		String descripcionCuentaPUC = "";
		if( jasientoContable.has("descripcionCuentaPUC") ) {
			descripcionCuentaPUC = jasientoContable.getString("descripcionCuentaPUC");
		}
		
		String descripcion = "";
		if( jasientoContable.has("descripcion") ) {
			descripcion = jasientoContable.getString("descripcion");
		}
		
		
		String nitTercero = "";
		if( jasientoContable.has("nitTercero") ) {
			nitTercero = jasientoContable.getString("nitTercero");
		}
		
		String razonSocialTercero = "";
		if( jasientoContable.has("razonSocialTercero") ) {
			razonSocialTercero = jasientoContable.getString("razonSocialTercero");
		}
		
		String nombreAccionContable = "";
		if( jasientoContable.has("nombreAccionContable") ) {
			nombreAccionContable = jasientoContable.getString("nombreAccionContable");
		}
		
		double saldoInicial = 0;
		if( jasientoContable.has("saldoInicial") ) {
			saldoInicial = jasientoContable.getDouble("saldoInicial");
		}
		
		
		double credito = 0;
		if( jasientoContable.has("credito") ) {
			credito = jasientoContable.getDouble("credito");
		}
		
		double debito = 0;
		if( jasientoContable.has("debito") ) {
			debito = jasientoContable.getDouble("debito");
		}
		
		
		return new AsientoContable(codigoCuentaPUC, descripcionCuentaPUC, descripcion, nitTercero, razonSocialTercero, saldoInicial, credito, debito, nombreAccionContable);
	}
}
