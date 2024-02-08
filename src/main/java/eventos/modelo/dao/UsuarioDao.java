package eventos.modelo.dao;

import eventos.modelo.entitis.Usuario;

public interface UsuarioDao {
	
	Usuario verUsuario(String username);
	boolean registro(Usuario usuario);
	Usuario UsuarioYpass(String username, String password);
	

}
