package eventos.modelo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import eventos.modelo.entitis.Reserva;
import eventos.modelo.entitis.Usuario;

public interface ReservaRepository extends JpaRepository<Reserva, Integer>{
	
	@Query
	("select r from Reserva r where r.usuario = ?1")
	public List<Reserva> verReservasPorUsuario(Usuario usuario);
	
	@Query
	("SELECT COALESCE(SUM(r.cantidad), 0) FROM Reserva r WHERE r.evento.idEvento =?1")
	public int cantidadReservas(int idEvento);
	
	@Query
	("SELECT  COALESCE(SUM(r.cantidad), 0) FROM Reserva r WHERE r.evento.idEvento =?1 AND r.usuario.username=?2")
	public int reservasPorUsuarioYEvento(int idEvento, String username);
	
	

}
