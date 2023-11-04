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
import co.inphotech.marshando.auxiliarcontable.externos.vo.AbonoGasto;
import co.inphotech.marshando.auxiliarcontable.usuario.vo.Empresa;
import co.inphotech.marshando.auxiliarcontable.util.UtilMundo;

public class AbonoGastoDAO {

	
	public static final String SERVICIO = "/abono"; 
	
	public static List<AbonoGasto> getAbonosGastos(Empresa empresa, int mes, int anio) throws Exception{
		List<AbonoGasto> abonosFacturas = new ArrayList<AbonoGasto>();
		
		String codigoEmpresa = empresa.getCodigo();
		
		String token = ConectorAutorizacionAplicacion.getInstancia().getToken( codigoEmpresa );
		String urlGastoDefecto = System.getenv("gastos_url");
		HttpRequest request = Unirest.get( urlGastoDefecto + SERVICIO ).header("authorization", token);
		
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
		JSONArray jabonosFcaturas = new JSONArray(data.getBody());
		
		for( int i = 0; i < jabonosFcaturas.length();i++ ) {
			AbonoGasto abonoFactura = armarAbonoFactura(empresa, jabonosFcaturas.getJSONObject(i));
			abonosFacturas.add(abonoFactura);
		}
		
		return abonosFacturas;
	}
	
	
	public static AbonoGasto armarAbonoFactura(Empresa empresa, JSONObject jabonoGasto ) throws Exception{
		
		long id = jabonoGasto.getLong("id");
		String descripcion = "";
		if( jabonoGasto.has("descripcion") ) {
			descripcion = jabonoGasto.getString("descripcion");
		}
		double valor = jabonoGasto.getDouble("valor");
		
		long idCajaCuentaBanco = jabonoGasto.getLong("idCajaCuentaBanco");
		String nombreCajaCuentaBanco = jabonoGasto.getString("nombreCajaCuentaBanco");
		
		
		Date fecha = UtilMundo.stringToDateHTMLInput( jabonoGasto.getString("sfecha") );
		Date utimaFechaActualizacion = UtilMundo.stringToDateHTMLHoraInput( jabonoGasto.getString("sultimaFechaActualizacion") );
		
		
		return new AbonoGasto(id, empresa.getCodigo(), valor, descripcion, idCajaCuentaBanco, nombreCajaCuentaBanco, fecha, utimaFechaActualizacion);
	}
}
