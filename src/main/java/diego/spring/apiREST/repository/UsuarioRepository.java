package diego.spring.apiREST.repository;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import diego.spring.apiREST.model.UsuarioModel;

@Repository
public interface UsuarioRepository extends CrudRepository<UsuarioModel, Long>{
	// asbtract <- spring hace lo demas si metodo: findBy{campo}
	public abstract ArrayList<UsuarioModel> findByPrio(Integer prio);
}
