package eventos.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import eventos.modelo.dao.ReservaDao;
import eventos.modelo.dao.UsuarioDao;
import eventos.modelo.entitis.Reserva;
import eventos.modelo.entitis.Usuario;

@Controller
public class ReservaController {

	@Autowired
	private ReservaDao rDao;

	@Autowired
	private UsuarioDao uDao;

	// VER MIS RESERVAS
	/**
	 * Controlador para mostrar las reservas del usuario autenticado.
	 * 
	 * Este método maneja solicitudes GET para mostrar las reservas de un usuario.
	 * Verifica si hay un usuario autenticado, obtiene la información del usuario y
	 * sus reservas asociadas, y luego pasa esta información al modelo antes de
	 * redirigir al usuario a la vista "misReservas". Si no hay un usuario
	 * autenticado, redirige al usuario a la página de inicio de sesión.
	 * 
	 * @param model    Para pasar datos a la vista
	 * @param aut      Objeto para manejar la información de autenticación del
	 *                 usuario
	 * @return 		   Devuelve el nombre de la vista "misReservas". En caso de que el usuario no esté
	 *         		   autentificado redirige a la página ("/login").
	 */
	@GetMapping("/misReservas")
	public String verMisReservas(Model model, Authentication aut) {
		if (aut != null && aut.isAuthenticated()) {
			String nombreUsuario = aut.getName();

			Usuario user = uDao.verUsuario(nombreUsuario);
			List<Reserva> reservas = rDao.verReservasPorUsuario(user);
			model.addAttribute("reservas", reservas);
			return "misReservas";
		} else {
			return "redirect:/login";
		}
	}

	// ELIMINAR RESERVA
	/**
	 * Controlador para eliminar una reserva específica.
	 * 
	 * Este método maneja solicitudes GET para eliminar una reserva específica.
	 * Intenta eliminar la reserva con el identificador proporcionado, agrega un
	 * mensaje al modelo según el resultado y redirige al usuario de vuelta a la
	 * página de sus reservas.
	 * 
	 * @param idReserva Identificador de la reserva a eliminar
	 * @param model     Para pasar datos a la vista
	 * @return 			Redirige al usuario a la página de "misReservas" después de eliminar
	 *         			la reserva. En caso de no poder eliminar la reserva, lo indica con un
	 *         			mensaje ("No ha podido eliminar la reserva").
	 */
	@GetMapping("/misReservas/eliminar/{id}")
	public String eliminarReserva(@PathVariable("id") int idReserva, Model model) {
		if (rDao.eliminarReserva(idReserva) == 1) {
			return "redirect:/misReservas";
		} else
			model.addAttribute("mensaje", "No ha podido eliminar la reserva");
		return "redirect:/misReservas";
	}

}
