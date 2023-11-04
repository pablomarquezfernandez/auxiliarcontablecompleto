package co.inphotech.marshando.auxiliarcontable.usuario.vo;
import com.amazonaws.util.json.JSONObject;

import co.inphotech.marshando.auxiliarcontable.usuario.dao.UsuarioDAO;




public class Usuario {

	public static final String ESTADO_ACTIVO = "Activo";
	public static final String ESTADO_INACTIVO = "Inactivo";
	
	// ------------------------------
	// Atributos
	// ------------------------------
	
	private String codigo;
	private String codigoEmpresa;
	private Empresa empresa;
	

	// ------------------------------
	// Constructor 
	// ------------------------------
	public Usuario( String codigo, String codigoEmpresa, JSONObject jempresa ) throws Exception{
		this.codigo = codigo;
		this.codigoEmpresa = codigoEmpresa;
//		String codigo, String nombre, String nit, String imagen
		String nombre = "";
		if( jempresa.has("nombre") ) {
			nombre = jempresa.getString("nombre");
		}
		String nit = "";
		if( jempresa.has("nit") ) {
			nit = jempresa.getString("nit");
		}
		String imagen = "";
		if( jempresa.has("imagen") ) {
			imagen = jempresa.getString("imagen");
		}
		
		empresa = new Empresa(codigoEmpresa, nombre, nit, "", "", "", "", imagen);

	}
	// ------------------------------
	// Gets
	// ------------------------------
	public String getCodigo() {
		return codigo;
	}
	public String getCodigoEmpresa() {
		return codigoEmpresa;
	}
	// ------------------------------
	// Empresa/Usuario
	// ------------------------------
	public Empresa getEmpresa() throws Exception{
		return empresa;
	}
	public static String getEstadoActivo() {
		return ESTADO_ACTIVO;
	}
	public static Usuario autorizacion(  String susuario ) throws Exception{
		JSONObject jusuario = new JSONObject(susuario);
		System.out.println( jusuario.toString() );
		
		String codigo = jusuario.getString("codigo");
		String codigoEmpresa = jusuario.getString("codigoEmpresa");
		JSONObject jempresa = jusuario.getJSONObject("empresa");
		
		Usuario usuario = new Usuario(codigo, codigoEmpresa, jempresa);
		
		return usuario;
	}
	public static Usuario autorizacionCodigo(  String codigoUsuario ) throws Exception{
		Usuario usuario = UsuarioDAO.autorizacion(codigoUsuario);
		

		
		return usuario;
	}
	// ------------------------------
	// Autenticar
	// ------------------------------
//	public static Usuario autorizacion(String codigo) throws Exception{
//		return UsuarioDAO.autorizacion(codigo);
//	}
//	
//	public static JSONObject autenticar(String correo, String contrasena) throws Exception{
//		return UsuarioDAO.autenticar(correo, contrasena);
//	}
	// ------------------------------
	// JSON
	// ------------------------------
	public JSONObject toJSON() throws Exception{
		JSONObject jusuario = new JSONObject();
		jusuario.put("codigo", getCodigo());
		jusuario.put("codigoEmpresa", getCodigoEmpresa());
		return jusuario;
	}
}
