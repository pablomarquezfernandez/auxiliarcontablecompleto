package co.inphotech.marshando.auxiliarcontable.accionescontables.vo;

import java.util.List;

import com.amazonaws.util.json.JSONArray;
import com.amazonaws.util.json.JSONObject;

public class TerceroAccionContable {

	
	private String codigo;
	private String tercero;
	private String elemento;
	
	
	
	public TerceroAccionContable(String codigo, String tercero, String elemento) {
		super();
		this.codigo = codigo;
		this.tercero = tercero;
		this.elemento = elemento;
	}
	public String getCodigo() {
		return codigo;
	}
	public String getTercero() {
		return tercero;
	}
	public String getElemento() {
		return elemento;
	}
	
	public JSONObject toJSON() throws Exception{
		JSONObject jtercerosAccionContable = new JSONObject();
		
		jtercerosAccionContable.put("codigo", getCodigo());
		jtercerosAccionContable.put("tercero", getTercero());
		jtercerosAccionContable.put("elemento", getElemento());
		
		return jtercerosAccionContable;
	}
	public static JSONArray toJSONArray( List<TerceroAccionContable> tercerosAccionContable ) throws Exception{
		JSONArray jtercerosAccionContable = new JSONArray();
		for(int i = 0; i < tercerosAccionContable.size(); i++){
			jtercerosAccionContable.put(tercerosAccionContable.get(i).toJSON( ) );
		}
		return jtercerosAccionContable;
	}
}
