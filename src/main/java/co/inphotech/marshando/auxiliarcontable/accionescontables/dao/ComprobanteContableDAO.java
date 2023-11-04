package co.inphotech.marshando.auxiliarcontable.accionescontables.dao;

import com.amazonaws.util.json.JSONArray;
import com.amazonaws.util.json.JSONObject;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;

import co.inphotech.marshando.auxiliarcontable.accionescontables.vo.ComprobanteContable;
import co.inphotech.marshando.auxiliarcontable.conectores.ConectorAutorizacionAplicacion;
import co.inphotech.marshando.auxiliarcontable.usuario.vo.Empresa;

public class ComprobanteContableDAO {

	public static final String SERVICIO = "/ejecutar-accion-contable";
	
	public static ComprobanteContable ejecutarAccionContable(Empresa empresa, JSONArray jdatos) throws Exception{
		String codigoEmpresa = empresa.getCodigo();
		
		System.out.println( jdatos.toString() );
		
		String token = ConectorAutorizacionAplicacion.getInstancia().getToken( codigoEmpresa );
		String urlAccionContable = System.getenv("contabilidad_url");
		HttpResponse<String>data = Unirest.post( urlAccionContable + SERVICIO ).header("authorization", token).body(jdatos.toString()).asString();
		
		
//		System.out.println( data.getBody() );
//		System.out.println( data.getBody() );
		
		if(data.getStatus() != 200) {
			throw new Exception(data.getBody());
		}
		JSONObject jcomprobanteContable = new JSONObject( data.getBody() );

		
		return armarComprobanteContable(jcomprobanteContable);
	}
	
	public static ComprobanteContable armarComprobanteContable( JSONObject jcomprobanteContable ) throws Exception{
	
		String codigo = "";
		if( jcomprobanteContable.has("codigo") ) {
			codigo = jcomprobanteContable.getString("codigo");
		}
		
		
		String consecutivo = "";
		if( jcomprobanteContable.has("consecutivoCompleto") ) {
			consecutivo = jcomprobanteContable.getString("consecutivoCompleto");
		}
		
		String descripcion = "";
		if( jcomprobanteContable.has("descripcion") ) {
			descripcion = jcomprobanteContable.getString("descripcion");
		}
		
		double credito = -1;
		if( jcomprobanteContable.has("credito") ) {
			credito = jcomprobanteContable.getDouble("credito");
		}
		double debito = -1;
		if( jcomprobanteContable.has("debito") ) {
			debito = jcomprobanteContable.getDouble("debito");
		}
		double saldoInicial = -1;
		if( jcomprobanteContable.has("saldoInicial") ) {
			saldoInicial = jcomprobanteContable.getDouble("saldoInicial");
		}
		
		
		return new ComprobanteContable(codigo, descripcion, consecutivo, credito, debito, saldoInicial);
	}
}
