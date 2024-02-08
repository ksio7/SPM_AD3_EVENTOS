package eventos.modelo.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import eventos.modelo.entitis.Evento;
import eventos.modelo.repository.EventoRepository;
import eventos.modelo.repository.ReservaRepository;
@Repository
public class EventoDaoImpl implements EventoDao {
	
	@Autowired
	private EventoRepository eRepo;
	
	@Autowired
	private ReservaRepository rRepo;

	@Override
	public List<Evento> todosLosEventos() {
		return eRepo.findAll();
	}

	@Override
	public Evento verUnEvento(int idEvento) {
		return eRepo.findById(idEvento).orElse(null);
	}

	@Override
	public List<Evento> verEventosDestacados() {
		return eRepo.verDestacados();
	}

	@Override
	public List<Evento> verEventosActivos() {
		return eRepo.verActivos();
	}

	@Override
	public List<Evento> verDestacadosPorTipo(int idTipo) {
		return eRepo.verEventosDestacadosPorTipo(idTipo);
	}

	@Override
	public List<Evento> verActivosPorTipo(int idTipo) {
		return eRepo.verEventosActivosPorTipo(idTipo);
	}
	
	
}
