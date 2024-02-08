package eventos.modelo.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import eventos.modelo.entitis.Tipo;
import eventos.modelo.repository.TipoRepository;
@Repository
public class TipoDaoImpl implements TipoDao{
	
	@Autowired
	private TipoRepository tRepo;

	@Override
	public List<Tipo> todosLosTiposEventos() {
		return tRepo.findAll();
	}

}
