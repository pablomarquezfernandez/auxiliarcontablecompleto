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
import co.inphotech.marshando.auxiliarcontable.externos.vo.AbonoFacturacion;
import co.inphotech.marshando.auxiliarcontable.usuario.vo.Empresa;
import co.inphotech.marshando.auxiliarcontable.util.UtilMundo;

public class AbonoFacturacionDAO {

	
	public static final String SERVICIO = "/abono"; 
	
	public static List<AbonoFacturacion> getAbonosFacturas(Empresa empresa, int tipo, int mes, int anio) throws Exception{
		List<AbonoFacturacion> abonosFacturas = new ArrayList<AbonoFacturacion>();
		
		String codigoEmpresa = empresa.getCodigo();
		
		String token = ConectorAutorizacionAplicacion.getInstancia().getToken( codigoEmpresa );
		String urlFactura = System.getenv("facturacion_url");
		HttpRequest request = Unirest.get( urlFactura + SERVICIO ).header("authorization", token).queryString("tipo", tipo + "" );
		
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
			AbonoFacturacion abonoFactura = armarAbonoFactura(empresa, jabonosFcaturas.getJSONObject(i));
			abonosFacturas.add(abonoFactura);
		}
		
		return abonosFacturas;
	}
	
	
	public static AbonoFacturacion armarAbonoFactura( Empresa empresa, JSONObject jabonoFactura ) throws Exception{
		
		long id = jabonoFactura.getLong("id");
		String descripcion = jabonoFactura.getString("descripcion");
		double valor = jabonoFactura.getDouble("valor");
		int tipo = jabonoFactura.getInt("tipo");
		
		long idCajaCuentaBanco = jabonoFactura.getLong("idCajaCuentaBanco");
		String nombreCajaCuentaBanco = jabonoFactura.getString("nombreCajaCuentaBanco");
		
		
		Date fecha = UtilMundo.stringToDateHTMLInput( jabonoFactura.getString("sfecha") );
		Date utimaFechaActualizacion = UtilMundo.stringToDateHTMLHoraInput( jabonoFactura.getString("sultimaFechaActualizacion") );
		
		
		return new AbonoFacturacion(id, empresa.getCodigo(), tipo, valor, descripcion, idCajaCuentaBanco, nombreCajaCuentaBanco, fecha, utimaFechaActualizacion);
	}
}
