package eventos.modelo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import eventos.modelo.entitis.Evento;
import eventos.modelo.entitis.Tipo;

public interface EventoRepository extends JpaRepository<Evento, Integer>{
	
	@Query
	("Select e from Evento e where e.destacado = 'S'")
	public List<Evento> verDestacados();
	
	@Query
	("Select e from Evento e where e.estado = 'Activo'")
	public List<Evento> verActivos();
	
	@Query
	("Select e from Evento e where e.tipo.idTipo = ?1 and e.destacado ='S'")
	public List<Evento> verEventosDestacadosPorTipo(int idTipo);

	@Query
	("Select e from Evento e where e.tipo.idTipo = ?1 and e.estado = 'Activo'")
	public List<Evento> verEventosActivosPorTipo(int idTipo);
	

}
