package eventos.modelo.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import eventos.modelo.entitis.Perfil;
import eventos.modelo.repository.PerfilRepository;

@Repository
public class PerfilDaoImpl implements PerfilDao{
	@Autowired
	private PerfilRepository pRepo;

	@Override
	public Perfil verUnPerfil(int idPerfil) {
		return pRepo.findById(idPerfil).orElse(null);
	}

}
