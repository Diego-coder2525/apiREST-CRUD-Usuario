package diego.spring.apiREST.controller;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import diego.spring.apiREST.model.UsuarioModel;
import diego.spring.apiREST.service.UsuarioService;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
	// Inyeccion de dependencias sin instanciacion explicita
	@Autowired
	private UsuarioService usuarioService;

	// Entra automaticamente si no hay mas mapeos get
	@GetMapping()
	public ArrayList<UsuarioModel> listUsuario() {
		return usuarioService.listUsuario();
	}

	// Entra automaticamente si no hay mas mapeos post
	// Guarda y actualiza <-- si enviamos el id en la peticion POST,
	// sera una actualizacion y si no, una agregacion
	// @RequestBody <- indica que debe ser enviado en determinado formato a la
	// peticion
	@PostMapping()
	public UsuarioModel guardarUsuario(@RequestBody UsuarioModel usuario) {
		return usuarioService.guardarUsuario(usuario);
	}

	// Recoge el parametro del path {id} con @PathVariable
	// NOTA: No es necesario especificar explicitamente el path con "path="
	@GetMapping(path = "/{id}")
	public Optional<UsuarioModel> obtenerUsuarioPorId(@PathVariable("id") Long id) {
		return usuarioService.obtenerPorId(id);
	}

	// Recoger el parametro por query ? --> /usuario/query?prio=n, tambien se pueden
	// enviar mas:
	// query?prio =n & n=n
	@GetMapping("/query")
	public ArrayList<UsuarioModel> obtenerUsuarioPorPrio(@RequestParam("prio") Integer prio) {
		return usuarioService.obtenerPorPrio(prio);
	}

	@DeleteMapping("/{id}")
	public String eliminarPorId(@PathVariable("id") Long id) {
		boolean r = usuarioService.eliminarUsuario(id);
		if (r) {
			return "Se elimino el usuario con id: " + id;
		} else {
			return "No se pudo elimianr el usuario con id: " + id;
		}

	}

	// Actualizar (mejor practica)
	@PutMapping("/put/{id}")
	public ResponseEntity<UsuarioModel> actualizarUsuario(@RequestBody UsuarioModel usuario, @PathVariable Long id) {
		
		try {
			// Buscamos usuario por id
			UsuarioModel usuarioActual = usuarioService.obtenerPorId(id).get();
			
			
			usuarioActual.setNombre(usuario.getNombre());
			usuarioActual.setEmail(usuario.getEmail());
			usuarioActual.setPrio(usuario.getPrio());

			usuarioService.guardarUsuario(usuarioActual);
			
			// Envia la respuesta al RESTclient de estado http OK 200
			return new ResponseEntity<UsuarioModel>(HttpStatus.OK);

		} catch (Exception e) {
			
			// Envia la respuesta al RESTclient de estado http not_found 404
			return new ResponseEntity<UsuarioModel>(HttpStatus.NOT_FOUND);
		}

	}
}
