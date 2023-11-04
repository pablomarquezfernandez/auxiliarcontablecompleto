package co.inphotech.marshando.auxiliarcontable.contables.vo;

import java.util.List;

import com.amazonaws.util.json.JSONArray;
import com.amazonaws.util.json.JSONObject;

public class AccionContable {

	public static final int ESTADO_ACTIVO = 1;
	public static final int ESTADO_INACTIVO = 2;
	
	// ------------------------------
	// Atributos
	// ------------------------------
	private long id;
	private String nombre;
	private int estado;

	// ------------------------------
	// Constructor 
	// ------------------------------
	public AccionContable( long id, String nombre, int estado ){
		this.id = id;
		this.nombre = nombre;
		this.estado = estado;

	}
	// ------------------------------
	// Gets
	// ------------------------------
	public long getId(){
		return id;
	}
	public String getNombre(){
		return nombre;
	}
	public int getEstado(){
		return estado;
	}

	// ------------------------------
	// ToJSON
	// ------------------------------
	public JSONObject toJSON( ) throws Exception{
		JSONObject jaccionContable = new JSONObject();
		jaccionContable.put("id", getId());
		jaccionContable.put("nombre", getNombre());
		jaccionContable.put("estado", getEstado());

		return jaccionContable;
	}
	public static JSONArray toJSONArray( List<AccionContable> accionContables ) throws Exception{
		JSONArray jaccionContables = new JSONArray();
		for(int i = 0; i < accionContables.size(); i++){
			jaccionContables.put(accionContables.get(i).toJSON( ) );
		}
		return jaccionContables;
	}
	public JSONArray toJSONTabla( ) throws Exception{
		JSONArray jaccionContable = new JSONArray();
		jaccionContable.put( getId());
		jaccionContable.put( getNombre());
		jaccionContable.put( getEstado());

		String botonEditar = 	"<button class = 'btn btn-default btn-icon-anim btn-square editarAccionContable' idAccionContable = '" + getId() + "' data-toggle='modal' data-target='#myModal'>";
		botonEditar += 				"<i class='far fa-edit'></i>";
		botonEditar += 			"</button>&nbsp;";
		String botonEliminar = 	"<button class = 'btn btn-danger btn-icon-anim btn-square eliminarAccionContable' idAccionContable = '" + getId() + "' data-toggle='modal' data-target='#myModal'>";
		botonEliminar += 				"<i class='fas fa-trash-alt'></i>";
		botonEliminar += 			"</button>";
		String botones = botonEditar + botonEliminar;

		jaccionContable.put(botones);
		
		return jaccionContable;
	}
	
	public static JSONObject toJSONTablaCompleto( List<AccionContable> accionContables ) throws Exception{
		JSONArray jaccionContables = new JSONArray();
		for(int i = 0; i < accionContables.size(); i++){
			jaccionContables.put(accionContables.get(i).toJSONTabla( ) );
		}
		JSONObject jdata = new JSONObject();
		jdata.put("data", jaccionContables); 
		return jdata;
	}
}
