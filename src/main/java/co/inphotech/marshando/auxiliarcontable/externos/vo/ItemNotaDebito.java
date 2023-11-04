package co.inphotech.marshando.auxiliarcontable.externos.vo;

import java.util.ArrayList;
import java.util.List;

import com.amazonaws.util.json.JSONArray;
import com.amazonaws.util.json.JSONObject;

import co.inphotech.marshando.auxiliarcontable.contables.vo.ElementoConfiguracion;
import co.inphotech.marshando.auxiliarcontable.contables.vo.ElementoContabilizado;
import co.inphotech.marshando.auxiliarcontable.externos.dao.ImpuestoItemNotaDebitoDAO;
import co.inphotech.marshando.auxiliarcontable.usuario.vo.Empresa;
import co.inphotech.marshando.auxiliarcontable.util.UtilMundo;

public class ItemNotaDebito {

	private long id;
	private long idProducto;
	private long idCategoriaContable;
	private String referencia;
	private String descripcion;
	private double precioUnitarioBruto;
	private double total;
	private double cantidad;
	
	private List<ImpuestoItemNotaDebito>impuestosItemNotaDebito;
	
	
	public ItemNotaDebito(long id, long idProducto, long idCategoriaContable, String referencia, String descripcion, double precioUnitarioBruto, double cantidad, double total) {
		super();
		this.id = id;
		this.idProducto = idProducto;
		this.idCategoriaContable = idCategoriaContable;
		this.referencia = referencia;
		this.descripcion = descripcion;
		this.precioUnitarioBruto = precioUnitarioBruto;
		this.cantidad = cantidad;
		this.total = total;
		
		this.impuestosItemNotaDebito = new ArrayList<ImpuestoItemNotaDebito>();
	}
	
	
	
	public long getId() {
		return id;
	}
	public long getIdProducto() {
		return idProducto;
	}
	public long getIdCategoriaContable() {
		return idCategoriaContable;
	}
	public String getReferencia() {
		return referencia;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public double getPrecioUnitarioBruto() {
		return precioUnitarioBruto;
	}
	public double getCantidad() {
		return cantidad;
	}
	public double getTotal() {
		return total;
	}
	
	public List<ImpuestoItemNotaDebito> getImpuestosItemNotaDebito() {
		return impuestosItemNotaDebito;
	}
	public void insertarImpuestosItemNotaDebito( ImpuestoItemNotaDebito impuestoItemNotaDebito ) {
		impuestosItemNotaDebito.add( impuestoItemNotaDebito );
	}
	
	
	
	// ---------------------------------------
	// 
	// ---------------------------------------
	public JSONArray getJSONArrayTerceros( NotaDebito notaDebito ) throws Exception{
		JSONArray jterceros = new  JSONArray();
		
		JSONObject jtercero = new JSONObject();
		jtercero.put("codigo", "defecto");
		jtercero.put("nit", notaDebito.getNitTercero());
		jtercero.put("razonSocialTercero", notaDebito.getRazonSocialTercero());
		jterceros.put(jtercero);
		
		return jterceros;
		
	}
	public JSONArray getJSONTextos( NotaDebito notaDebito ) throws Exception{
		JSONArray jterceros = new  JSONArray();
		
		JSONObject jtercero = new JSONObject();
		jtercero.put("codigo", "numeroNotaDebito");
		jtercero.put("valor", notaDebito.getConsecutivo());
		jterceros.put(jtercero);
		
		return jterceros;
		
	}
	public JSONArray getJSONValores( Empresa empresa, NotaDebito notaDebito ) throws Exception{
		List<Impuesto>impuestos = empresa.getImpuestos();
		JSONArray jvariables = new  JSONArray();
		//
		// codigo, valor
		List<String>codigosImpuestos = getImpuestos();
		for( int i = 0; i < codigosImpuestos.size();i++) {
			String codigoImpuesto = codigosImpuestos.get(i);
			JSONObject jvariable = new JSONObject();
			jvariable.put("codigo", codigoImpuesto);
			jvariable.put("valor", getTotalImpuesto(impuestosItemNotaDebito, codigoImpuesto));
			jvariables.put( jvariable );
		}
		
		
		for( int i = 0; i < impuestosItemNotaDebito.size();i++ ) {
			ImpuestoItemNotaDebito impuesto = impuestosItemNotaDebito.get(i);
			JSONObject jvariable = new JSONObject();
			
			String porcentaje = impuesto.getPorcentaje() + "";
			porcentaje = porcentaje.replace(".", "_");
			
			jvariable.put("codigo", impuesto.getCodigo() + "_" + porcentaje);
			jvariable.put("valor", (impuesto.getValorUnitario() * impuesto.getCantidad()) );
			jvariables.put( jvariable );
			
			jvariable = new JSONObject();
			jvariable.put("codigo", "notaDebito_" + impuesto.getCodigo() + "_" + porcentaje);
			jvariable.put("valor", ( getPrecioUnitarioBruto() * getCantidad() ) );
			jvariables.put( jvariable );
			
		}
		
		
		
		for( int i = 0; i < impuestos.size();i++ ) {
			Impuesto impuesto = impuestos.get(i);
			ImpuestoItemNotaDebito impuestoItemNotaDebito = ImpuestoItemNotaDebitoDAO.getImpuestoItemNotaDebito(impuestosItemNotaDebito, impuesto.getCodigo());
			if( impuestoItemNotaDebito == null ) {
				JSONObject jvariable = new JSONObject();
				jvariable.put("codigo", "notaDebito_" + impuesto.getCodigo() + "_0_0");
				jvariable.put("valor", ( getPrecioUnitarioBruto() * getCantidad() ) );
				jvariables.put( jvariable );
				
				jvariable = new JSONObject();
				jvariable.put("codigo", impuesto.getCodigo() + "_0_0");
				jvariable.put("valor", (getPrecioUnitarioBruto() * getCantidad()) );
				jvariables.put( jvariable );
				
				
			}
		}
		
		
		JSONObject jvariable = new JSONObject();
		jvariable.put("codigo", "clientes");
		jvariable.put("valor", getTotal() );
		jvariables.put( jvariable );
		
		
		jvariable = new JSONObject();
		jvariable.put("codigo", "valor");
		jvariable.put("valor", getTotal() );
		jvariables.put( jvariable );
		
		
		jvariable = new JSONObject();
		jvariable.put("codigo", "valorBruto");
		jvariable.put("valor", getPrecioUnitarioBruto() * getCantidad() );
		jvariables.put( jvariable );
		
		jvariable = new JSONObject();
		jvariable.put("codigo", "valorNeto");
		jvariable.put("valor", getTotal() );
		jvariables.put( jvariable );
		
		
		System.out.println( jvariables );
		
		
		return jvariables;
		
	}
	
	public double getTotalImpuesto( List<ImpuestoItemNotaDebito>impuestos, String codigoImpuesto ) {
		double total = 0;
		for( int i = 0; i < impuestos.size();i++ ) {
			ImpuestoItemNotaDebito impuestoItemNotaDebito = impuestos.get(i);
			if( impuestoItemNotaDebito.getCodigo().equals(codigoImpuesto ) ) {
				total += ( impuestoItemNotaDebito.getValorUnitario() * impuestoItemNotaDebito.getCantidad() );
			}
		}
		return total;
	}
	public List<String> getImpuestos() throws Exception{
		List<String>impuestos = new ArrayList<String>();
		for( int i = 0; i < impuestosItemNotaDebito.size();i++ ) {
			String codigo = impuestosItemNotaDebito.get(i).getCodigo();
			if( !esta(impuestos, codigo) ) {
				impuestos.add( codigo );
			}
		}
		return impuestos;
		
	}
	public boolean esta( List<String>impuestos, String codigo ) {
		boolean encontro = false;
		for( int i = 0; i < impuestos.size() && !encontro;i++ ) {
			String impuesto = impuestos.get(i);
			if( impuesto.equals(codigo) ) {
				encontro = true;
			}
		}
		return encontro;
	}
	
	
	
	public JSONObject toJSONAccionContable(  ElementoContabilizado elementoContabilizado, Empresa empresa,  NotaDebito notaDebito ) throws Exception{
		JSONObject jitemNotaDebito = new JSONObject();
		
		ElementoConfiguracion elementoConfiguracion = empresa.getElementoConfiguracion(getIdCategoriaContable(), ElementoConfiguracion.TIPO_NOTA_CREDITO);
		jitemNotaDebito.put("id", empresa.getElementoAccionContable(elementoConfiguracion).getIdAccionContable());
		//
		// Textos notaDebito
		jitemNotaDebito.put("textosDescripcion", getJSONTextos(notaDebito));
		
		//
		// Terceros
		jitemNotaDebito.put("terceros", getJSONArrayTerceros(notaDebito));
		
		//
		// Valores
		jitemNotaDebito.put("valores", getJSONValores(empresa, notaDebito));
		
		
		//
		// Datos básicos
		jitemNotaDebito.put("fecha", UtilMundo.dateToStringInputHTML(notaDebito.getFecha()));
		jitemNotaDebito.put("descripcion", "CAUSACIÓN NOTA CREDITO " + notaDebito.getConsecutivo());
		jitemNotaDebito.put("mes13", false);
		
		
		
		if( elementoContabilizado != null ) {
			if( elementoContabilizado.getCodigoComprobanteContable() != null ) {
				jitemNotaDebito.put("codigoComprobanteContable", elementoContabilizado.getCodigoComprobanteContable() );
			}
		}
		
		return jitemNotaDebito;
	}
} 
