package co.inphotech.marshando.auxiliarcontable.contables.vo;

import java.util.List;

import com.amazonaws.util.json.JSONArray;
import com.amazonaws.util.json.JSONObject;

import co.inphotech.marshando.auxiliarcontable.contables.dao.ElementoConfiguracionDAO;

public class ElementoConfiguracion {

	public static final int ESTADO_ACTIVO = 1;
	public static final int ESTADO_INACTIVO = 2;
	
	
	public static final int TIPO_FACTURACION = 1;
	public static final int TIPO_PAGO_FACTURACION = 2;
	public static final int TIPO_NOTA_CREDITO = 9;
	public static final int TIPO_PAGO_NOTA_CREDITO = 10;
	public static final int TIPO_NOTA_DEBITO = 11;
	public static final int TIPO_PAGO_NOTA_DEBITO = 12;
	
	
	public static final int TIPO_GASTO = 5;
	public static final int TIPO_PAGO_GASTO = 6;
	
	
	public static final int TIPO_PERIODO_TRABAJADOR = 3;
	public static final int TIPO_PAGO_PERIODO_TRABAJADOR = 4;
	public static final int TIPO_PERIODO_SEGURIDAD_SOCIAL = 7;
	public static final int TIPO_PAGO_PERIODO_SEGURIDAD_SOCIAL = 8;
	
	// ------------------------------
	// Atributos
	// ------------------------------
	private long id;
	private long idElemento;
	private String codigoEmpresa;
	private String nombre;
	private int tipo;
	private int estado;

	// ------------------------------
	// Constructor 
	// ------------------------------
	public ElementoConfiguracion( long id, String codigoEmpresa, long idElemento, String nombre, int tipo, int estado ){
		this.id = id;
		this.idElemento = idElemento;
		this.codigoEmpresa = codigoEmpresa;
		this.nombre = nombre;
		this.tipo = tipo;
		this.estado = estado;

	}
	// ------------------------------
	// Gets
	// ------------------------------
	public long getId(){
		return id;
	}
	public long getIdElemento(){
		return idElemento;
	}
	public String getCodigoEmpresa(){
		return codigoEmpresa;
	}
	public String getNombre(){
		return nombre;
	}
	public int getTipo(){
		return tipo;
	}
	public int getEstado(){
		return estado;
	}
	
	// ------------------------------
	// Actualizar
	// ------------------------------
	public void actualizarElemento(  long idElemento, String nombre, int tipo ) throws Exception
	{
		ElementoConfiguracionDAO.actualizarElementoConfiguracion(  this, codigoEmpresa, idElemento, nombre, tipo );
		this.nombre = nombre;
		this.tipo = tipo;

	}
	// ------------------------------
	// Eliminar
	// ------------------------------
	public void eliminar( ) throws Exception{
		ElementoConfiguracionDAO.actualizarEstadoElementoConfiguracion(this, ElementoConfiguracion.ESTADO_INACTIVO );
		this.estado  = ElementoConfiguracion.ESTADO_INACTIVO;
	}
	// ------------------------------
	// ToJSON
	// ------------------------------
	public JSONObject toJSON( ) throws Exception{
		JSONObject jelemento = new JSONObject();
		jelemento.put("id", getId());
		
		jelemento.put("idElemento", getIdElemento() );
		jelemento.put("codigoEmpresa", getCodigoEmpresa());
		jelemento.put("nombre", getNombre());
		jelemento.put("tipo", getTipo());
		jelemento.put("estado", getEstado());

		return jelemento;
	}
	public static JSONArray toJSONArray( List<ElementoConfiguracion> elementos ) throws Exception{
		JSONArray jelementos = new JSONArray();
		for(int i = 0; i < elementos.size(); i++){
			jelementos.put(elementos.get(i).toJSON( ) );
		}
		return jelementos;
	}
	public JSONArray toJSONTabla( ) throws Exception{
		JSONArray jelemento = new JSONArray();
		jelemento.put( getId());
		jelemento.put( getCodigoEmpresa());
		jelemento.put( getNombre());
		jelemento.put( getTipo());
		jelemento.put( getEstado());

		String botonEditar = 	"<button class = 'btn btn-default btn-icon-anim btn-square editarElemento' idElemento = '" + getId() + "' data-toggle='modal' data-target='#myModal'>";
		botonEditar += 				"<i class='far fa-edit'></i>";
		botonEditar += 			"</button>&nbsp;";
		String botonEliminar = 	"<button class = 'btn btn-danger btn-icon-anim btn-square eliminarElemento' idElemento = '" + getId() + "' data-toggle='modal' data-target='#myModal'>";
		botonEliminar += 				"<i class='fas fa-trash-alt'></i>";
		botonEliminar += 			"</button>";
		String botones = botonEditar + botonEliminar;

		jelemento.put(botones);
		
		return jelemento;
	}
	
	public static JSONObject toJSONTablaCompleto( List<ElementoConfiguracion> elementos ) throws Exception{
		JSONArray jelementos = new JSONArray();
		for(int i = 0; i < elementos.size(); i++){
			jelementos.put(elementos.get(i).toJSONTabla( ) );
		}
		JSONObject jdata = new JSONObject();
		jdata.put("data", jelementos); 
		return jdata;
	}
}
