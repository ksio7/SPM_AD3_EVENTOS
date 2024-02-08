package eventos.modelo.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import eventos.modelo.entitis.Usuario;
import eventos.modelo.repository.UsuarioRepository;
@Repository
public class UsuarioDaoImpl implements UsuarioDao{
	
	@Autowired
	private UsuarioRepository uRepo;

	
	@Override
	public Usuario verUsuario(String username) {
		return uRepo.buscarUsuario(username);
	}


	@Override
	public boolean registro(Usuario usuario) {
		if (verUsuario(usuario.getUsername()) == null) {
			uRepo.save(usuario);
			return true;
	}
	return false;
}


	@Override
	public Usuario UsuarioYpass(String username, String password) {
		return uRepo.findByUserAndPass(username, password);
	}


}
