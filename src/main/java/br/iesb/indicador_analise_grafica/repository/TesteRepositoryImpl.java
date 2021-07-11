package br.iesb.indicador_analise_grafica.repository;

import javax.persistence.EntityManager;

import br.iesb.indicador_analise_grafica.model.Teste;

public class TesteRepositoryImpl implements ITesteRepository {
	
	 private EntityManager em;
     
    public TesteRepositoryImpl(EntityManager em) {
        this.em = em;
    }

	@Override
	public Teste getBookById(Long id) {
		return em.find(Teste.class, id);
	}

	@Override
	public Teste getBookByDescription(String descricao) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Teste saveBook(Teste b) {
		if (b.getId() == null) {
            em.persist(b);
        } else {
            b = em.merge(b);
        }
        return b;
	}

	@Override
	public void deleteBook(Teste b) {
		// TODO Auto-generated method stub
		
	}

}
