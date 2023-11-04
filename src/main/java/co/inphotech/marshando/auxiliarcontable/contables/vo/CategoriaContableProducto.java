package co.inphotech.marshando.auxiliarcontable.contables.vo;

import java.util.List;

import com.amazonaws.util.json.JSONArray;
import com.amazonaws.util.json.JSONObject;

public class CategoriaContableProducto {

	private long id;
	private String nombre;
	private String codigo;
	
	
	
	
	public CategoriaContableProducto(long id, String nombre, String codigo) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.codigo = codigo;
	}
	
	
	
	public long getId() {
		return id;
	}
	public String getNombre() {
		return nombre;
	}
	public String getCodigo() {
		return codigo;
	}
	
	
	public JSONObject toJSON( ) throws Exception{
		JSONObject jelementoAccionContable = new JSONObject();
		jelementoAccionContable.put("id", getId());
		jelementoAccionContable.put("nombre", getNombre() );
		jelementoAccionContable.put("codigo", getCodigo() );

		return jelementoAccionContable;
	}
	public static JSONArray toJSONArray( List<CategoriaContableProducto> categoriasContablesProductos ) throws Exception{
		JSONArray jcategoriasContablesProductos = new JSONArray();
		for(int i = 0; i < categoriasContablesProductos.size(); i++){
			jcategoriasContablesProductos.put(categoriasContablesProductos.get(i).toJSON( ) );
		}
		return jcategoriasContablesProductos;
	}
	
}
