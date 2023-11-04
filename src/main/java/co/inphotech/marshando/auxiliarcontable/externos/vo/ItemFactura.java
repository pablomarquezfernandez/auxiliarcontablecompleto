package co.inphotech.marshando.auxiliarcontable.externos.vo;

import java.util.ArrayList;
import java.util.List;

import com.amazonaws.util.json.JSONArray;
import com.amazonaws.util.json.JSONObject;

import co.inphotech.marshando.auxiliarcontable.contables.vo.ElementoConfiguracion;
import co.inphotech.marshando.auxiliarcontable.contables.vo.ElementoContabilizado;
import co.inphotech.marshando.auxiliarcontable.externos.dao.ImpuestoItemFacturaDAO;
import co.inphotech.marshando.auxiliarcontable.usuario.vo.Empresa;
import co.inphotech.marshando.auxiliarcontable.util.UtilMundo;

public class ItemFactura {

	private long id;
	private long idProducto;
	private long idCategoriaContable;
	private String referencia;
	private String descripcion;
	private double precioUnitarioBruto;
	private double total;
	private double cantidad;
	
	private List<ImpuestoItemFactura>impuestosItemFactura;
	
	
	public ItemFactura(long id, long idProducto, long idCategoriaContable, String referencia, String descripcion, double precioUnitarioBruto, double cantidad, double total) {
		super();
		this.id = id;
		this.idProducto = idProducto;
		this.idCategoriaContable = idCategoriaContable;
		this.referencia = referencia;
		this.descripcion = descripcion;
		this.precioUnitarioBruto = precioUnitarioBruto;
		this.cantidad = cantidad;
		this.total = total;
		
		this.impuestosItemFactura = new ArrayList<ImpuestoItemFactura>();
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
	
	public List<ImpuestoItemFactura> getImpuestosItemFactura() {
		return impuestosItemFactura;
	}
	public void insertarImpuestosItemFactura( ImpuestoItemFactura impuestoItemFactura ) {
		impuestosItemFactura.add( impuestoItemFactura );
	}
	
	
	
	// ---------------------------------------
	// 
	// ---------------------------------------
	public JSONArray getJSONArrayTerceros( Factura factura ) throws Exception{
		JSONArray jterceros = new  JSONArray();
		
		JSONObject jtercero = new JSONObject();
		jtercero.put("codigo", "defecto");
		jtercero.put("nit", factura.getNitTercero());
		jtercero.put("razonSocialTercero", factura.getRazonSocialTercero());
		jterceros.put(jtercero);
		
		return jterceros;
		
	}
	public JSONArray getJSONTextos( Factura factura ) throws Exception{
		JSONArray jterceros = new  JSONArray();
		
		JSONObject jtercero = new JSONObject();
		jtercero.put("codigo", "numeroFactura");
		jtercero.put("valor", factura.getConsecutivo());
		jterceros.put(jtercero);
		
		return jterceros;
		
	}
	public JSONArray getJSONValores( Empresa empresa, Factura factura ) throws Exception{
		List<Impuesto>impuestos = empresa.getImpuestos();
		JSONArray jvariables = new  JSONArray();
		//
		// codigo, valor
		List<String>codigosImpuestos = getImpuestos();
		for( int i = 0; i < codigosImpuestos.size();i++) {
			String codigoImpuesto = codigosImpuestos.get(i);
			JSONObject jvariable = new JSONObject();
			jvariable.put("codigo", codigoImpuesto);
			jvariable.put("valor", getTotalImpuesto(impuestosItemFactura, codigoImpuesto));
			jvariables.put( jvariable );
		}
		
		
		for( int i = 0; i < impuestosItemFactura.size();i++ ) {
			ImpuestoItemFactura impuesto = impuestosItemFactura.get(i);
			JSONObject jvariable = new JSONObject();
			
			String porcentaje = impuesto.getPorcentaje() + "";
			porcentaje = porcentaje.replace(".", "_");
			
			jvariable.put("codigo", impuesto.getCodigo() + "_" + porcentaje);
			jvariable.put("valor", (impuesto.getValorUnitario() * impuesto.getCantidad()) );
			jvariables.put( jvariable );
			
			jvariable = new JSONObject();
			jvariable.put("codigo", "facturado_" + impuesto.getCodigo() + "_" + porcentaje);
			jvariable.put("valor", ( getPrecioUnitarioBruto() * getCantidad() ) );
			jvariables.put( jvariable );
			
		}
		
		
		
		for( int i = 0; i < impuestos.size();i++ ) {
			Impuesto impuesto = impuestos.get(i);
			ImpuestoItemFactura impuestoItemFactura = ImpuestoItemFacturaDAO.getImpuestoItemFactura(impuestosItemFactura, impuesto.getCodigo());
			if( impuestoItemFactura == null ) {
				JSONObject jvariable = new JSONObject();
				jvariable.put("codigo", "facturado_" + impuesto.getCodigo() + "_0_0");
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
	
	public double getTotalImpuesto( List<ImpuestoItemFactura>impuestos, String codigoImpuesto ) {
		double total = 0;
		for( int i = 0; i < impuestos.size();i++ ) {
			ImpuestoItemFactura impuestoItemFactura = impuestos.get(i);
			if( impuestoItemFactura.getCodigo().equals(codigoImpuesto ) ) {
				total += ( impuestoItemFactura.getValorUnitario() * impuestoItemFactura.getCantidad() );
			}
		}
		return total;
	}
	public List<String> getImpuestos() throws Exception{
		List<String>impuestos = new ArrayList<String>();
		for( int i = 0; i < impuestosItemFactura.size();i++ ) {
			String codigo = impuestosItemFactura.get(i).getCodigo();
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
	
	
	
	public JSONObject toJSONAccionContable(  ElementoContabilizado elementoContabilizado, Empresa empresa,  Factura factura ) throws Exception{
		JSONObject jitemFactura = new JSONObject();
		
		ElementoConfiguracion elementoConfiguracion = empresa.getElementoConfiguracion(getIdCategoriaContable(), ElementoConfiguracion.TIPO_FACTURACION);
		jitemFactura.put("id", empresa.getElementoAccionContable(elementoConfiguracion).getIdAccionContable());
		//
		// Textos factura
		jitemFactura.put("textosDescripcion", getJSONTextos(factura));
		
		//
		// Terceros
		jitemFactura.put("terceros", getJSONArrayTerceros(factura));
		
		//
		// Valores
		jitemFactura.put("valores", getJSONValores(empresa, factura));
		
		
		//
		// Datos básicos
		jitemFactura.put("fecha", UtilMundo.dateToStringInputHTML(factura.getFecha()));
		jitemFactura.put("descripcion", "CAUSACIÓN FACTURA " + factura.getConsecutivo());
		jitemFactura.put("mes13", false);
		
		
		
		if( elementoContabilizado != null ) {
			if( elementoContabilizado.getCodigoComprobanteContable() != null ) {
				jitemFactura.put("codigoComprobanteContable", elementoContabilizado.getCodigoComprobanteContable() );
			}
		}
		
		return jitemFactura;
	}
} 
