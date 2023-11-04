package co.inphotech.marshando.auxiliarcontable.contables.vo;

import java.util.List;

import com.amazonaws.util.json.JSONArray;
import com.amazonaws.util.json.JSONObject;

import co.inphotech.marshando.auxiliarcontable.contables.dao.ElementoAccionContableDAO;

public class ElementoAccionContable {

	public static final int ESTADO_ACTIVO = 1;
	public static final int ESTADO_INACTIVO = 2;
	
	// ------------------------------
	// Atributos
	// ------------------------------
	private long id;
	private String codigoEmpresa;
	private ElementoConfiguracion elemento;
	
	private long idAccionContable;
	private String nombreAccionContable;
	private int estado;

	// ------------------------------
	// Constructor 
	// ------------------------------
	public ElementoAccionContable( long id, String codigoEmpresa, ElementoConfiguracion elemento, long idAccionContable, String nombreAccionContable, int estado ){
		this.id = id;
		this.codigoEmpresa = codigoEmpresa;
		
		this.elemento = elemento;
		this.idAccionContable = idAccionContable;
		this.nombreAccionContable = nombreAccionContable;
		this.estado = estado;

	}
	// ------------------------------
	// Gets
	// ------------------------------
	public long getId(){
		return id;
	}
	public String getCodigoEmpresa(){
		return codigoEmpresa;
	}
	public String getNombreAccionContable(){
		return nombreAccionContable;
	}
	public int getEstado(){
		return estado;
	}
	public ElementoConfiguracion getElemento() {
		return elemento;
	}
	public long getIdAccionContable() {
		return idAccionContable;
	}
	// ------------------------------
	// Actualizar
	// ------------------------------
	public void actualizarElementoAccionContable( ElementoConfiguracion elemento, AccionContable accionContable ) throws Exception
	{
		ElementoAccionContableDAO.actualizarElementoAccionContable(  this, codigoEmpresa, elemento, accionContable );

		this.elemento = elemento;
		this.idAccionContable = accionContable.getId();
		this.nombreAccionContable = accionContable.getNombre();

	}
	// ------------------------------
	// Eliminar
	// ------------------------------
	public void eliminar( ) throws Exception{
		ElementoAccionContableDAO.actualizarEstadoElementoAccionContable(this, ElementoAccionContable.ESTADO_INACTIVO );
		this.estado  = ElementoAccionContable.ESTADO_INACTIVO;
	}
	// ------------------------------
	// ToJSON
	// ------------------------------
	public JSONObject toJSON( ) throws Exception{
		JSONObject jelementoAccionContable = new JSONObject();
		jelementoAccionContable.put("id", getId());
		
		
		jelementoAccionContable.put("codigoEmpresa", getCodigoEmpresa());
		
		jelementoAccionContable.put("idElemento", getElemento().getIdElemento() );
		jelementoAccionContable.put("tipo", getElemento().getTipo() );
		jelementoAccionContable.put("nombreElemento", getElemento().getNombre() );
		
		jelementoAccionContable.put("idAccionContable", getIdAccionContable());
		jelementoAccionContable.put("nombreAccionContable", getNombreAccionContable());
		jelementoAccionContable.put("estado", getEstado());

		return jelementoAccionContable;
	}
	public static JSONArray toJSONArray( List<ElementoAccionContable> elementoAccionContables ) throws Exception{
		JSONArray jelementoAccionContables = new JSONArray();
		for(int i = 0; i < elementoAccionContables.size(); i++){
			jelementoAccionContables.put(elementoAccionContables.get(i).toJSON( ) );
		}
		return jelementoAccionContables;
	}
	public JSONArray toJSONTabla( ) throws Exception{
		JSONArray jelementoAccionContable = new JSONArray();
		jelementoAccionContable.put( getId());
		jelementoAccionContable.put( getCodigoEmpresa());
		jelementoAccionContable.put( getNombreAccionContable());
		jelementoAccionContable.put( getEstado());

		String botonEditar = 	"<button class = 'btn btn-default btn-icon-anim btn-square editarElementoAccionContable' idElementoAccionContable = '" + getId() + "' data-toggle='modal' data-target='#myModal'>";
		botonEditar += 				"<i class='far fa-edit'></i>";
		botonEditar += 			"</button>&nbsp;";
		String botonEliminar = 	"<button class = 'btn btn-danger btn-icon-anim btn-square eliminarElementoAccionContable' idElementoAccionContable = '" + getId() + "' data-toggle='modal' data-target='#myModal'>";
		botonEliminar += 				"<i class='fas fa-trash-alt'></i>";
		botonEliminar += 			"</button>";
		String botones = botonEditar + botonEliminar;

		jelementoAccionContable.put(botones);
		
		return jelementoAccionContable;
	}
	
	public static JSONObject toJSONTablaCompleto( List<ElementoAccionContable> elementoAccionContables ) throws Exception{
		JSONArray jelementoAccionContables = new JSONArray();
		for(int i = 0; i < elementoAccionContables.size(); i++){
			jelementoAccionContables.put(elementoAccionContables.get(i).toJSONTabla( ) );
		}
		JSONObject jdata = new JSONObject();
		jdata.put("data", jelementoAccionContables); 
		return jdata;
	}
}
