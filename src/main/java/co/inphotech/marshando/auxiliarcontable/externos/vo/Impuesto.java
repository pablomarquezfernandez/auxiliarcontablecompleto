package co.inphotech.marshando.auxiliarcontable.externos.vo;

public class Impuesto {

	private long id;
	private String nombre;
	private String codigo;
	public Impuesto(long id, String nombre, String codigo) {
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
	
	
	
}
