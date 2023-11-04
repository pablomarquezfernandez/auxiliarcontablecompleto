package co.inphotech.marshando.auxiliarcontable.externos.vo;

public class ImpuestoItemFactura {

	private long id;
	private String codigo;
	private String nombre;
	private double porcentaje;
	private double cantidad;
	private double valorUnitario;
	
	
	
	public ImpuestoItemFactura(long id, String codigo, String nombre, double porcentaje, double cantidad, double valorUnitario) {
		super();
		this.id = id;
		this.codigo = codigo;
		this.nombre = nombre;
		this.porcentaje = porcentaje;
		this.cantidad = cantidad;
		this.valorUnitario = valorUnitario;
	}
	
	
	
	public long getId() {
		return id;
	}
	public String getCodigo() {
		return codigo;
	}
	public String getNombre() {
		return nombre;
	}
	public double getPorcentaje() {
		return porcentaje;
	}
	public double getCantidad() {
		return cantidad;
	}
	public double getValorUnitario() {
		return valorUnitario;
	}
	
	
}
