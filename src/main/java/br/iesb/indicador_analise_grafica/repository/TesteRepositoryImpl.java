package br.iesb.indicador_analise_grafica.repository;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import br.iesb.indicador_analise_grafica.model.Teste;

public class TesteRepositoryImpl implements ITesteRepository	 {
	
	 private EntityManager em;
     
    public TesteRepositoryImpl(EntityManager em) {
        this.em = em;
    }


	@Override
	public <S extends Teste> S save(S entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Teste findOne(Long primaryKey) {
		return this.em.find(Teste.class, primaryKey);
	}

	@Override
	public List<Teste> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void delete(Teste entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean exists(Long primaryKey) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void deleteAllByIdInBatch(Iterable<Long> arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAllInBatch() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAllInBatch(Iterable<Teste> arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Teste> findAll(Sort arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Teste> List<S> findAll(Example<S> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Teste> List<S> findAll(Example<S> arg0, Sort arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Teste> findAllById(Iterable<Long> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void flush() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Teste getById(Long arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Teste getOne(Long arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Teste> List<S> saveAll(Iterable<S> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Teste> List<S> saveAllAndFlush(Iterable<S> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Teste> S saveAndFlush(S arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Teste> findAll(Pageable arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll(Iterable<? extends Teste> arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAllById(Iterable<? extends Long> arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(Long arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean existsById(Long arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Optional<Teste> findById(Long arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Teste> long count(Example<S> arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public <S extends Teste> boolean exists(Example<S> arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public <S extends Teste> Page<S> findAll(Example<S> arg0, Pageable arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Teste> Optional<S> findOne(Example<S> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
