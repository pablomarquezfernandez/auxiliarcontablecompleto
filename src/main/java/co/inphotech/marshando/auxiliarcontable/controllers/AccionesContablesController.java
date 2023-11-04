package co.inphotech.marshando.auxiliarcontable.controllers;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.util.json.JSONObject;

import co.inphotech.marshando.auxiliarcontable.accionescontables.vo.ComprobanteContable;
import co.inphotech.marshando.auxiliarcontable.contables.vo.AccionContable;
import co.inphotech.marshando.auxiliarcontable.usuario.vo.Empresa;
import co.inphotech.marshando.auxiliarcontable.usuario.vo.Usuario;

@RestController
@RequestMapping("/controllers")
public class AccionesContablesController {

	
	@CrossOrigin
	@PostMapping(value = "/auxiliar-contable/acciones-contables", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getElementoAccionContables(  @RequestParam String codigo ) throws Exception{
		HttpHeaders responseHeaders = new HttpHeaders();
	
		Usuario usuario = Usuario.autorizacionCodigo(codigo);
		Empresa empresa = usuario.getEmpresa();
		List<AccionContable> accionesContables = empresa.getAccionesContables();
		
		return  new ResponseEntity<String>( AccionContable.toJSONArray( accionesContables ).toString(), responseHeaders, HttpStatus.OK );
	}
	
	
	@CrossOrigin
	@PostMapping(value = "/auxiliar-contable/accion-contable", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getElementoAccionContable( @RequestParam String codigo, @RequestParam Long id) throws Exception{
		
		HttpHeaders responseHeaders = new HttpHeaders();
		try {
			
			Usuario usuario = Usuario.autorizacionCodigo(codigo);
			Empresa empresa = usuario.getEmpresa();
			AccionContable accionContable = empresa.getAccionContable(id);
			
			
			return  new ResponseEntity<String>(accionContable.toJSON().toString(),responseHeaders,HttpStatus.OK);
			
								    			
		} catch (Exception e) {
			e.printStackTrace();
			if( e.getMessage().contains("403") ) {
				return  new ResponseEntity<String>("{}", responseHeaders, HttpStatus.FORBIDDEN );
			}else {
				return  new ResponseEntity<String>("{'mensaje','" + e.getMessage() + "'}",responseHeaders,HttpStatus.BAD_REQUEST );
			}
		}
		
	}
	
	@CrossOrigin
	@PostMapping(value = "/auxiliar-contable/ejecutar-accion-contable", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> ejecutarAccionContable( @RequestParam String codigo, @RequestParam Long idElemento, @RequestParam Integer tipo) throws Exception{
		
		HttpHeaders responseHeaders = new HttpHeaders();
		try {
			
			Usuario usuario = Usuario.autorizacionCodigo(codigo);
			Empresa empresa = usuario.getEmpresa();
			ComprobanteContable comprobanteContable = empresa.ejecutarAccionContable(tipo, idElemento);
			
			JSONObject jrta = new JSONObject();
			jrta.put("respuesta", "exito");
			jrta.put("mensaje", "Proceso exitoso");
//			jrta.put("objeto", comprobanteContable.toJSON());
			
			return  new ResponseEntity<String>(jrta.toString(),responseHeaders,HttpStatus.OK);
			
								    			
		} catch (Exception e) {
			e.printStackTrace();
			if( e.getMessage().contains("403") ) {
				return  new ResponseEntity<String>("{}", responseHeaders, HttpStatus.FORBIDDEN );
			}else {
				return  new ResponseEntity<String>("{'mensaje','" + e.getMessage() + "'}",responseHeaders,HttpStatus.BAD_REQUEST );
			}
		}
		
	}
	@CrossOrigin
	@PostMapping(value = "/auxiliar-contable/ejecutar-acciones-contable", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> ejecutarAccionContable( @RequestParam String codigo, @RequestParam Integer tipo, @RequestParam Integer mes, @RequestParam Integer anio) throws Exception{
		
		HttpHeaders responseHeaders = new HttpHeaders();
		try {
			
			Usuario usuario = Usuario.autorizacionCodigo(codigo);
			Empresa empresa = usuario.getEmpresa();
			empresa.ejecutarAccionesContable(tipo, mes, anio);
			
			JSONObject jrta = new JSONObject();
			jrta.put("respuesta", "exito");
			jrta.put("mensaje", "Proceso exitoso");
			
			return  new ResponseEntity<String>(jrta.toString(),responseHeaders,HttpStatus.OK);
			
								    			
		} catch (Exception e) {
			e.printStackTrace();
			if( e.getMessage().contains("403") ) {
				return  new ResponseEntity<String>("{}", responseHeaders, HttpStatus.FORBIDDEN );
			}else {
				return  new ResponseEntity<String>("{'mensaje','" + e.getMessage() + "'}",responseHeaders,HttpStatus.BAD_REQUEST );
			}
		}
		
	}
}
