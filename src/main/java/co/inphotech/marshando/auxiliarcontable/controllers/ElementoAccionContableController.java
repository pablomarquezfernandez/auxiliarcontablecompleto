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

import co.inphotech.marshando.auxiliarcontable.contables.vo.AccionContable;
import co.inphotech.marshando.auxiliarcontable.contables.vo.ElementoAccionContable;
import co.inphotech.marshando.auxiliarcontable.contables.vo.ElementoConfiguracion;
import co.inphotech.marshando.auxiliarcontable.usuario.vo.Empresa;
import co.inphotech.marshando.auxiliarcontable.usuario.vo.Usuario;



@RestController
@RequestMapping("/controllers")
public class ElementoAccionContableController {

	
	@CrossOrigin
	@PostMapping(value = "/auxiliar-contable/elementos-acciones-contables", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getElementoAccionContables(  @RequestParam String codigo, @RequestParam Integer tipo ) throws Exception{
		HttpHeaders responseHeaders = new HttpHeaders();
	
		Usuario usuario = Usuario.autorizacionCodigo(codigo);
		Empresa empresa = usuario.getEmpresa();
		List<ElementoAccionContable> elementoAccionContables = empresa.getElementosAccionesContables(tipo);
		
		return  new ResponseEntity<String>( ElementoAccionContable.toJSONArray( elementoAccionContables).toString(), responseHeaders, HttpStatus.OK );
	}
	
	
	@CrossOrigin
	@PostMapping(value = "/auxiliar-contable/elemento-accion-contable", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getElementoAccionContable( @RequestParam String codigo, @RequestParam Long id) throws Exception{
		
		HttpHeaders responseHeaders = new HttpHeaders();
		try {
			
			Usuario usuario = Usuario.autorizacionCodigo(codigo);
			Empresa empresa = usuario.getEmpresa();
			ElementoAccionContable elementoAccionContable = empresa.getElementoAccionContable(id);
			
			
			return  new ResponseEntity<String>(elementoAccionContable.toJSON().toString(),responseHeaders,HttpStatus.OK);
			
								    			
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
	@PostMapping(value = "/auxiliar-contable/insertar-elemento-accion-contable", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String>insertarElementoAccionContable(  @RequestParam String codigo, @RequestParam Long  idElemento, @RequestParam Integer  tipo, @RequestParam String nombre, @RequestParam Long  idAccionContable ) throws Exception{
		HttpHeaders responseHeaders = new HttpHeaders();
		try {
			
			Usuario usuario = Usuario.autorizacionCodigo(codigo);
			Empresa empresa = usuario.getEmpresa();
			AccionContable accionContable = empresa.getAccionContable(idAccionContable);
			
			ElementoAccionContable elementoAccionContable = empresa.insertarElementoAccionContable(idElemento, tipo, nombre, accionContable);
			
			
			JSONObject jrespuesta = new JSONObject();
			jrespuesta.put("respuesta", "exito");
			jrespuesta.put("mensaje", "Proceso exitoso");
			jrespuesta.put("objeto", elementoAccionContable.toJSON());
			
			return  new ResponseEntity<String>(jrespuesta.toString(),responseHeaders,HttpStatus.OK);
			
								    			
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
	@PostMapping(value = "/auxiliar-contable/actualizar-elemento-accion-contables", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String>actualizarElementoAccionContable(  @RequestParam String codigo,  @RequestParam Long  id, @RequestParam Long  idElemento, @RequestParam Long  idAccionContable ) throws Exception{
		
		HttpHeaders responseHeaders = new HttpHeaders();
		try {
			
			Usuario usuario = Usuario.autorizacionCodigo(codigo);
			Empresa empresa = usuario.getEmpresa();
			ElementoAccionContable elementoAccionContable = empresa.getElementoAccionContable(id);
			
			ElementoConfiguracion elemento = empresa.getElementoConfiguracion(idElemento);
			AccionContable accionContable = empresa.getAccionContable(idAccionContable);
			
			elementoAccionContable.actualizarElementoAccionContable( elemento, accionContable);
			
			
			JSONObject jrespuesta = new JSONObject();
			jrespuesta.put("respuesta", "exito");
			jrespuesta.put("mensaje", "Proceso exitoso");
			jrespuesta.put("objeto", elementoAccionContable.toJSON());
			
			return  new ResponseEntity<String>(jrespuesta.toString(),responseHeaders,HttpStatus.OK);
			
								    			
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
	@PostMapping(value = "/auxiliar-contable/eliminar-elemento-accion-contables", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String>eliminarElementoAccionContable( @RequestParam String codigo, @RequestParam Long id ) throws Exception{
		
		
		HttpHeaders responseHeaders = new HttpHeaders();
		Usuario usuario = Usuario.autorizacionCodigo(codigo);
		Empresa empresa = usuario.getEmpresa();
		ElementoAccionContable elementoAccionContable = empresa.getElementoAccionContable(id);
		
		
		if( elementoAccionContable == null ) {
			return  new ResponseEntity<String>( "{'respuesta':'error', 'mensaje':'ElementoAccionContable sin identifcar'}", responseHeaders, HttpStatus.BAD_REQUEST );
		}
		
		elementoAccionContable.eliminar();
		JSONObject jrespuesta = new JSONObject();
		jrespuesta.put("respuesta", "exito");
		jrespuesta.put("mensaje", "Proceso exitoso");
		
		return  new ResponseEntity<String>( jrespuesta.toString(), responseHeaders, HttpStatus.OK );
		
		
	}
	
}
