package co.inphotech.marshando.auxiliarcontable.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/controllers")
public class ElementoContabilizadoController {

	
//	@CrossOrigin
//	@PostMapping(value = "/auxiliar-contable/elementos-contabilizados", produces = MediaType.APPLICATION_JSON_VALUE)
//	public ResponseEntity<String> getElementoContabilizados(  @RequestParam String codigo, @RequestParam Integer tipo, @RequestParam Integer anio, @RequestParam Integer mes ) throws Exception{
//		HttpHeaders responseHeaders = new HttpHeaders();
//	
//		Usuario usuario = Usuario.autorizacionCodigo(codigo);
//		Empresa empresa = usuario.getEmpresa();
//		List<ElementoContabilizado> elementoContabilizados = empresa.getElementoContabilizados(tipo, anio, mes);
//		
//		
//		return  new ResponseEntity<String>( ElementoContabilizado.toJSONArray( elementoContabilizados).toString(), responseHeaders, HttpStatus.OK );
//	}
	
	
//	@CrossOrigin
//	@PostMapping(value = "/ElementoContabilizado", produces = MediaType.APPLICATION_JSON_VALUE)
//	public ResponseEntity<String> getElementoContabilizado( @RequestParam String codigo, @RequestParam Long id) throws Exception{
//		
//		HttpHeaders responseHeaders = new HttpHeaders();
//		try {
//			
//			Usuario usuario = Usuario.autorizacionCodigo(codigo);
//			Empresa empresa = usuario.getEmpresa();
//			ElementoContabilizado elementoContabilizado = empresa.getElementoContabilizado(id);
//			
//			
//			return  new ResponseEntity<String>(elementoContabilizados.toJSON().toString(),responseHeaders,HttpStatus.OK);
//			
//								    			
//		} catch (Exception e) {
//			e.printStackTrace();
//			if( e.getMessage().contains("403") ) {
//				return  new ResponseEntity<String>("{}", responseHeaders, HttpStatus.FORBIDDEN );
//			}else {
//				return  new ResponseEntity<String>("{'mensaje','" + e.getMessage() + "'}",responseHeaders,HttpStatus.BAD_REQUEST );
//			}
//		}
//		
//	}
//	
//	@CrossOrigin
//	@PostMapping(value = "/insertar-ElementoContabilizado", produces = MediaType.APPLICATION_JSON_VALUE)
//	public ResponseEntity<String>insertarElementoContabilizado(  @RequestParam String codigo, @RequestParam Long  id, @RequestParam Integer  tipo, @RequestParam String  enlaceElemento, @RequestParam String  codigoComprobanteContable, @RequestParam String  enlaceComprobanteContable, @RequestParam Integer  estadoContabilidad, @RequestParam Integer  estado ) throws Exception{
//		HttpHeaders responseHeaders = new HttpHeaders();
//		try {
//			
//			Usuario usuario = Usuario.autorizacionCodigo(codigo);
//			Empresa empresa = usuario.getEmpresa();
//			ElementoContabilizado elementoContabilizado = empresa.insertarElementoContabilizado( tipo, enlaceElemento, codigoComprobanteContable, enlaceComprobanteContable, estadoContabilidad )
//			
//			
//			JSONObject jrespuesta = new JSONObject();
//			jrespuesta.put("respuesta", "exito");
//			jrespuesta.put("mensaje", "Proceso exitoso");
//			jrespuesta.put("objeto", elementoContabilizado.toJSON());
//			
//			return  new ResponseEntity<String>(jrespuesta.toString(),responseHeaders,HttpStatus.OK);
//			
//								    			
//		} catch (Exception e) {
//			e.printStackTrace();
//			if( e.getMessage().contains("403") ) {
//				return  new ResponseEntity<String>("{}", responseHeaders, HttpStatus.FORBIDDEN );
//			}else {
//				return  new ResponseEntity<String>("{'mensaje','" + e.getMessage() + "'}",responseHeaders,HttpStatus.BAD_REQUEST );
//			}
//		}
//		
//	}
//	
//	
//	@CrossOrigin
//	@PostMapping(value = "/actualizar-ElementoContabilizado", produces = MediaType.APPLICATION_JSON_VALUE)
//	public ResponseEntity<String>actualizarElementoContabilizado(  @RequestParam String codigo,  @RequestParam Long  id, @RequestParam Integer  tipo, @RequestParam String  enlaceElemento, @RequestParam String  codigoComprobanteContable, @RequestParam String  enlaceComprobanteContable, @RequestParam Integer  estadoContabilidad, @RequestParam Integer  estado ) throws Exception{
//		
//		HttpHeaders responseHeaders = new HttpHeaders();
//		try {
//			
//			Usuario usuario = Usuario.autorizacionCodigo(codigo);
//			Empresa empresa = usuario.getEmpresa();
//			ElementoContabilizado elementoContabilizado = empresa.getElementoContabilizado(id);
//			elementoContabilizado.actualizarElementoContabilizado( tipo, enlaceElemento, codigoComprobanteContable, enlaceComprobanteContable, estadoContabilidad )
//			
//			
//			JSONObject jrespuesta = new JSONObject();
//			jrespuesta.put("respuesta", "exito");
//			jrespuesta.put("mensaje", "Proceso exitoso");
//			jrespuesta.put("objeto", elementoContabilizado.toJSON());
//			
//			return  new ResponseEntity<String>(jrespuesta.toString(),responseHeaders,HttpStatus.OK);
//			
//								    			
//		} catch (Exception e) {
//			e.printStackTrace();
//			if( e.getMessage().contains("403") ) {
//				return  new ResponseEntity<String>("{}", responseHeaders, HttpStatus.FORBIDDEN );
//			}else {
//				return  new ResponseEntity<String>("{'mensaje','" + e.getMessage() + "'}",responseHeaders,HttpStatus.BAD_REQUEST );
//			}
//		}
//		
//	}
//	
//	
//	@CrossOrigin
//	@PostMapping(value = "/eliminar-ElementoContabilizado", produces = MediaType.APPLICATION_JSON_VALUE)
//	public ResponseEntity<String>eliminarElementoContabilizado( @RequestParam String codigo, @RequestParam Long id ) throws Exception{
//		
//		
//		HttpHeaders responseHeaders = new HttpHeaders();
//		Usuario usuario = Usuario.autorizacionCodigo(codigo);
//		Empresa empresa = usuario.getEmpresa();
//		ElementoContabilizado elementoContabilizado = empresa.getElementoContabilizado(id);
//		
//		
//		if( elementoContabilizado == null ) {
//			return  new ResponseEntity<String>( "{'respuesta':'error', 'mensaje':'ElementoContabilizado sin identifcar'}", responseHeaders, HttpStatus.BAD_REQUEST );
//		}
//		
//		JSONObject jrespuesta = new JSONObject();
//		jrespuesta.put("respuesta", "exito");
//		jrespuesta.put("mensaje", "Proceso exitoso");
//		
//		return  new ResponseEntity<String>( jrespuesta.toString(), responseHeaders, HttpStatus.OK );
//		
//		
//	}
	
}
