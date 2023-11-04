package co.inphotech.marshando.auxiliarcontable.conectores;

import java.io.InputStream;
import java.util.Properties;

public class DatosAplicacion {

	private static DatosAplicacion datosAplicacion;
	
	private String codigoAplicacion = null;
	private String urlKafka = null;
	private String puertoKafka = null;
	
	private String canalCuentaPUC;
	private String canalCentroCosto;
	
	
	private Properties propiedadesKafkaTerceros;
	
	
	private DatosAplicacion() throws Exception{
		Properties propiedades = new Properties();;
		InputStream in = ConectorAutorizacionUsuario.class.getResourceAsStream("/resources/datos.properties");
		propiedades.load(in);
		
		
		codigoAplicacion = propiedades.getProperty("codigoAplicacion").trim();
		urlKafka = propiedades.getProperty("urlKafka").trim();
		puertoKafka = propiedades.getProperty("puertoKafka").trim();
		canalCuentaPUC = propiedades.getProperty("canalCuentaPUC").trim();
		canalCentroCosto = propiedades.getProperty("canalCentroCosto").trim();
	}
	// ------------------------------------
	// GETS
	// ------------------------------------
	public String getCodigoAplicacion() {
		return codigoAplicacion;
	}
	public String getCanalCuentaPUC() {
		return canalCuentaPUC;
	}
	public String getCanalCentroCosto() {
		return canalCentroCosto;
	}
	public Properties getPrpiedadesKafka() {
		if( propiedadesKafkaTerceros == null ) {
			propiedadesKafkaTerceros = new Properties();
			propiedadesKafkaTerceros.put("bootstrap.servers", urlKafka + ":" + puertoKafka);

			// Set acknowledgements for producer requests.
			propiedadesKafkaTerceros.put("acks", "all");
			// If the request fails, the producer can automatically retry,
			propiedadesKafkaTerceros.put("retries", 0);
			// Specify buffer size in config
			propiedadesKafkaTerceros.put("batch.size", 16384);
			// Reduce the no of requests less than 0
			propiedadesKafkaTerceros.put("linger.ms", 1);
			// The buffer.memory controls the total amount of memory available to the
			// producer for buffering.
			propiedadesKafkaTerceros.put("buffer.memory", 33554432);
			propiedadesKafkaTerceros.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
			propiedadesKafkaTerceros.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
			
		}
		return propiedadesKafkaTerceros;
	}
	// ------------------------------------
	// Instancias
	// ------------------------------------
	public static DatosAplicacion getInstancia() throws Exception{
		if(  datosAplicacion == null ) {
			datosAplicacion = new DatosAplicacion();
		}
		return datosAplicacion;
	}
	

}
