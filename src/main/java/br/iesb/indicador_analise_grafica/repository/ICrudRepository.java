package br.iesb.indicador_analise_grafica.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.*;

@Repository
public interface ICrudRepository<T, ID extends Serializable> extends JpaRepository<T, Long> {

	<S extends T> S save(S entity);
    
	T findOne(ID primaryKey);
    
	List<T> findAll();

	long count();
    
	void delete(T entity);
    
	boolean exists(ID primaryKey);
}
