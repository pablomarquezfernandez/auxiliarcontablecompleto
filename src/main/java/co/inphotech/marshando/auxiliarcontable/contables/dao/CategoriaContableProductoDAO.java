package co.inphotech.marshando.auxiliarcontable.contables.dao;

import java.util.ArrayList;
import java.util.List;

import com.amazonaws.util.json.JSONArray;
import com.amazonaws.util.json.JSONObject;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;

import co.inphotech.marshando.auxiliarcontable.conectores.ConectorAutorizacionAplicacion;
import co.inphotech.marshando.auxiliarcontable.contables.vo.CategoriaContableProducto;
import co.inphotech.marshando.auxiliarcontable.usuario.vo.Empresa;

public class CategoriaContableProductoDAO {

	
	public static final String SERVICIO = "/categoria-contable-producto"; 
	
	public static CategoriaContableProducto getCategoriaContableProducto(Empresa empresa, long id) throws Exception{
		CategoriaContableProducto categoriaContableProducto = null;
		
		String codigoEmpresa = empresa.getCodigo();
		
		String token = ConectorAutorizacionAplicacion.getInstancia().getToken( codigoEmpresa );
		String urlCategoriaContableProducto = System.getenv("productos_url");
		HttpResponse<String>data = Unirest.get( urlCategoriaContableProducto + SERVICIO + "/"  + id).header("authorization", token).asString();
		
		
		if(data.getStatus() != 200) {
			throw new Exception(data.getBody());
		}
		JSONObject jcategoriaContableProducto = new JSONObject(data.getBody());
		categoriaContableProducto = armarCategoriaContableProducto(jcategoriaContableProducto);
		
		
		return categoriaContableProducto;
	}
	
	public static List<CategoriaContableProducto> getCategoriaContableProductos(Empresa empresa) throws Exception{
		List<CategoriaContableProducto> categoriaContableProductos = new ArrayList<CategoriaContableProducto>();
		
		String codigoEmpresa = empresa.getCodigo();
		
		String token = ConectorAutorizacionAplicacion.getInstancia().getToken( codigoEmpresa );
		String urlCategoriaContableProducto = System.getenv("productos_url");
		HttpResponse<String>data = Unirest.get( urlCategoriaContableProducto + SERVICIO ).header("authorization", token).asString();
		
		
		if(data.getStatus() != 200) {
			throw new Exception(data.getBody());
		}
		JSONArray jcategoriaContableProductos = new JSONArray(data.getBody());
		
		for( int i = 0; i < jcategoriaContableProductos.length();i++ ) {
			CategoriaContableProducto categoriaContableProducto = armarCategoriaContableProducto(jcategoriaContableProductos.getJSONObject(i));
			categoriaContableProductos.add(categoriaContableProducto);
		}
		
		
		
		
		return categoriaContableProductos;
	}
	
	

	public static CategoriaContableProducto armarCategoriaContableProducto(JSONObject jcategoriaContableProducto) throws Exception{
		long id = jcategoriaContableProducto.getLong("id");
		String nombre = "";
		if( jcategoriaContableProducto.has("nombre") ) {
			nombre = jcategoriaContableProducto.getString("nombre");
		}
		String codigo = "";
		if( jcategoriaContableProducto.has("codigo") ) {
			codigo = jcategoriaContableProducto.getString("codigo");
		}
		
		return new CategoriaContableProducto(id, nombre, codigo);
	}
}
