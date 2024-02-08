package eventos.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import eventos.modelo.dao.EventoDao;
import eventos.modelo.dao.PerfilDao;
import eventos.modelo.dao.TipoDao;
import eventos.modelo.dao.UsuarioDao;
import eventos.modelo.entitis.Evento;
import eventos.modelo.entitis.Tipo;
import eventos.modelo.entitis.Usuario;
import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {
	@Autowired
	UsuarioDao uDao;
	@Autowired
	EventoDao eDao;
	@Autowired
	TipoDao tDao;
	@Autowired
	PerfilDao pDao;

	// REGISTRO
	/**
	 * Controlador para mostrar el formulario de registro.
	 * 
	 * Este método se utiliza para mostrar un formulario de registro cuando un
	 * usuario visita la URL "/signup". Inicializa un objeto Usuario vacío y lo pasa
	 * al modelo, luego redirige a la vista "registro".
	 * 
	 * @param model 	Parámetro, que se utiliza para pasar datos a la vista.
	 * @return 			Devuelve el nombre de la vista que se mostrará después de ejecutar
	 *         			este método. "registro"
	 */
	@GetMapping("/signup")
	public String registrar(Model model) {
		model.addAttribute("usuario", new Usuario());
		return "registro";
	}

	/**
	 * Controlador para procesar el registro de nuevos usuarios.
	 * 
	 * Este método se utiliza para procesar solicitudes de registro de nuevos
	 * usuarios. Configura la información del usuario, intenta registrar al usuario
	 * y redirige al usuario a la página de inicio de sesión o de registro
	 * dependiendo del resultado del registro.
	 * 
	 * @param model   	Parámetro, que se utiliza para pasar datos a la vista.
	 * @param usuario 	Objeto con la información de Usuario. usuario.setEnabled(1);:
	 *                	Establece el estado del usuario como habilitado. El valor 1
	 *                	significa "habilitado". usuario.setFechaRegistro(new Date());
	 *                	Establece la fecha de registro del usuario como la fecha
	 *                	actual. usuario.addPerfil(pDao.verUnPerfil(3)); Añade un
	 *                	perfil(cliente) al usuario. usuario.setPassword("{noop}" +
	 *                	usuario.getPassword()); Configura la contraseña del usuario.
	 *                	{noop} significa no encifrado o encriptado.
	 * @param ratt      Atributos de redirección que se utilizan para pasar mensajes flash a través de las redirecciones.
	 * @return 			Redirige al usuario de vuelta a la página de registro ("/signup").
	 */
	@PostMapping("/signup")
	public String registrar(Model model, Usuario usuario, RedirectAttributes ratt) {
		usuario.setEnabled(1);
		usuario.setFechaRegistro(new Date());
		usuario.addPerfil(pDao.verUnPerfil(3));
		usuario.setPassword("{noop}" + usuario.getPassword());
		if (uDao.registro(usuario)) {
			ratt.addFlashAttribute("mensaje", "Alta usuario realizada");
			return "redirect:/login";
		} else {
			ratt.addFlashAttribute("mensaje", "Ya existe como usuario");
			return "redirect:/signup";
		}
	}

	// CERRAR SESIÓN
	/**
	 * Controlador para cerrar la sesión de un usuario.
	 * 
	 * Este método se utiliza para cerrar la sesión de un usuario. Elimina la
	 * información del usuario de la sesión y luego invalida la sesión antes de
	 * redirigir al usuario a la página de inicio.
	 * 
	 * @param misesion Utilizado para controlar la sesión habilitada
	 *                 misesion.removeAttribute("usuario");Elimina el atributo
	 *                 llamado "usuario" de la sesión. Esta información del usuario
	 *                 se almacena en la sesión con el nombre de atributo "usuario".
	 *                 misesion.invalidate(); Invalida la sesión, y cierra la sesión
	 *                 actual del usuario.
	 * @return 		   Después de cerrar la sesión, redirige al usuario a la página de
	 *         		   inicio ("/home").
	 */
	@GetMapping("/logout")
	public String cerrarSesion(HttpSession misesion) {
		misesion.removeAttribute("usuario");
		misesion.invalidate();
		return "redirect:/home";
	}

	// LOGIN
	/**
	 * Controlador para mostrar el formulario de login.
	 * 
	 * Este método se utiliza para manejar solicitudes GET a la página de login.
	 * Redirige a la vista "login"
	 * 
	 * @return 		   	Devuelve la vista "login".
	 */
	@GetMapping("/login")
	public String procesarLogin() {
		return "login";
	}
	
	// HOME
	/**
	 * Controlador para mostrar la página de inicio.
	 * 
	 * Este método Verifica la autenticación del usuario, obtiene eventos y tipos de eventos, y luego devuelve la vista "home"
	 * con los datos correspondientes.
	 * 
	 * @param model		Representa el modelo que se utilizará para almacenar datos y pasarlos a la vista.
	 * @param tipo		Objeto de tipo, indicando el tipo de evento que se mostrará (opcional).
	 * @return			El nombre de la vista que se mostrará (en este caso, "home").
	 */
	@GetMapping({ "/", "/home" })
	public String verIndex(Model model, Tipo tipo) {
		if (tipo.getIdTipo() == 0) {
			List<Evento> destacados = eDao.verEventosDestacados();
			model.addAttribute("destacados", destacados);

			List<Evento> activos = eDao.verEventosActivos();
			model.addAttribute("activos", activos);
		} else {

			List<Evento> destacados = eDao.verDestacadosPorTipo(tipo.getIdTipo());
			model.addAttribute("destacados", destacados);

			List<Evento> activos = eDao.verActivosPorTipo(tipo.getIdTipo());
			model.addAttribute("activos", activos);
		}
		
		List<Tipo> tiposEvento = tDao.todosLosTiposEventos();
		model.addAttribute("TiposEvento", tiposEvento);

		return "home";
	}

	/**
	 * Controlador para mostrar una lista de eventos activos.
	 * 
	 * Este método maneja solicitudes GET para mostrar una lista de eventos activos.
	 * Obtiene la lista de eventos activos a través de un objeto eDao y la agrega al
	 * modelo antes de redirigir al usuario a la vista "eventosActivos".
	 * 
	 * @param model    Para pasar datos a la vista
	 * @return 		   Devuelve el nombre de la vista que se mostrará después de ejecutar
	 *         		   este método. cuando se visita la URL "/eventosActivos", se
	 *         		   mostrará la vista "eventosActivos".
	 */
	@GetMapping("/eventosActivos")
	public String verActivos(Model model) {

		List<Evento> activos = eDao.verEventosActivos();
		model.addAttribute("activos", activos);

		return "eventosActivos";
	}

	/**
	 * Controlador para mostrar una lista de eventos destacados
	 * 
	 * Este método maneja solicitudes GET para mostrar una lista de eventos
	 * destacados. Obtiene la lista de eventos destacados a través de un objeto eDao
	 * y la agrega al modelo antes de redirigir al usuario a la vista
	 * "eventosDestacados".
	 * 
	 * @param model    Para pasar datos a la vista
	 * @return 		   Devuelve el nombre de la vista que se mostrará después de ejecutar
	 *         		   este método. Por lo tanto, cuando se visita la URL
	 *                 "/eventosDestacados", se mostrará la vista "eventosDestacados".
	 */
	@GetMapping("/eventosDestacados")
	public String verDestacados(Model model) {

		List<Evento> destacados = eDao.verEventosDestacados();
		model.addAttribute("destacados", destacados);

		return "eventosDestacados";
	}

	/**
	 * Controlador para mostrar detalles de eventos.
	 * 
	 * Este método maneja solicitudes GET para mostrar detalles de eventos. Obtiene
	 * la lista de eventos a través de un objeto eDao y la agrega al
	 * modelo antes de redirigir al usuario a la vista "detalles".
	 * 
	 * @param model    	Para pasar datos a la vista
	 * @return 			Devuelve el nombre de la vista que se mostrará después de ejecutar
	 *         			este método. cuando se visita la URL "/detalles", se mostrará la
	 *         			vista "detalles".
	 */
	@GetMapping("/detalles")
	public String verDetalles(Model model) {

		List<Evento> destacados = eDao.todosLosEventos();
		model.addAttribute("destacados", destacados);

		return "detalles";
	}

}
