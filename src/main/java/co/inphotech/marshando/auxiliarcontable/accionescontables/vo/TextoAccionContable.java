package co.inphotech.marshando.auxiliarcontable.accionescontables.vo;

import java.util.List;

import com.amazonaws.util.json.JSONArray;
import com.amazonaws.util.json.JSONObject;

public class TextoAccionContable {

	
	private String codigo;
	private String texto;
	private String elemento;
	
	
	
	public TextoAccionContable(String codigo, String texto, String elemento) {
		super();
		this.codigo = codigo;
		this.texto = texto;
		this.elemento = elemento;
	}
	public String getCodigo() {
		return codigo;
	}
	public String getTexto() {
		return texto;
	}
	public String getElemento() {
		return elemento;
	}
	
	public JSONObject toJSON() throws Exception{
		JSONObject jtextoAccionContable = new JSONObject();
		
		jtextoAccionContable.put("codigo", getCodigo());
		jtextoAccionContable.put("texto", getTexto());
		jtextoAccionContable.put("elemento", getElemento());
		
		return jtextoAccionContable;
	}
	public static JSONArray toJSONArray( List<TextoAccionContable> textosAccionContable ) throws Exception{
		JSONArray jtextosAccionContable = new JSONArray();
		for(int i = 0; i < textosAccionContable.size(); i++){
			jtextosAccionContable.put(textosAccionContable.get(i).toJSON( ) );
		}
		return jtextosAccionContable;
	}
}
