package co.inphotech.marshando.auxiliarcontable.externos.dao;

public class PeriodoNovedadTrabajadorNomina {

	
	private long id;
	private String codigoNovedad;
	private String codigoTercero;
	private String nombre;
	private double valor;
	
	
	
	public PeriodoNovedadTrabajadorNomina(long id, String codigoNovedad, String codigoTercero, String nombre, double valor) {
		super();
		this.id = id;
		this.codigoNovedad = codigoNovedad;
		this.codigoTercero = codigoTercero;
		this.nombre = nombre;
		this.valor = valor;
	}
	
	
	public long getId() {
		return id;
	}
	public String getCodigoNovedad() {
		return codigoNovedad;
	}
	public String getCodigoTercero() {
		return codigoTercero;
	}
	public String getNombre() {
		return nombre;
	}
	public double getValor() {
		return valor;
	}
	
	
	
}
