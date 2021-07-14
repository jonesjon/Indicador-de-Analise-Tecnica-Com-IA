package br.iesb.indicador_analise_grafica.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.iesb.indicador_analise_grafica.PopularBanco;
import br.iesb.indicador_analise_grafica.model.Teste;
import br.iesb.indicador_analise_grafica.model.TesteRepository;
import br.iesb.indicador_analise_grafica.repository.TesteRepositoryImpl;

@Service
public class TesteServiceImpl implements ITesteService {
	
	@Autowired
	TesteRepository testRepository;
	
	public TesteServiceImpl() {
		this.em = PopularBanco.getEntityManager();
	}
	



	@PersistenceContext
	private EntityManager em;
	
	@Transactional
	@Override
	public  Teste getTesteById(Long id) {
		TesteRepositoryImpl t = new TesteRepositoryImpl(em);
		t.count();
		return t.getById(id);
	}
	
}
