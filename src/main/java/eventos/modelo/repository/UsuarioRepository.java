package eventos.modelo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import eventos.modelo.entitis.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{
	
	@Query("SELECT u FROM Usuario u WHERE u.username = ?1")
    Usuario buscarUsuario(String username);
	
	@Query("select u from Usuario u where u.username = ?1 AND u.password = ?1") 
	public Usuario findByUserAndPass(String username, String password);

}
