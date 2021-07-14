package br.iesb.indicador_analise_grafica;

import javax.persistence.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.iesb.indicador_analise_grafica.repository.InfoCandleRepository;

@Service
public class PopularBanco {
	
	@Autowired
	private static InfoCandleRepository infoCandleRepository;
	
	private static EntityManagerFactory emf;
	
	public static EntityManager getEntityManager() {
		
		if(emf==null || !emf.isOpen()) {
			emf = Persistence.createEntityManagerFactory("indicador");
		}
		return emf.createEntityManager();
	}
	
	public static void adicionaCandle(InfoCandle infoCandle){
		/*EntityManager em = getEntityManager();
	
		em.getTransaction().begin();
		em.persist(infoCandle);
		em.getTransaction().commit();
		
		em.close();
		emf.close();
		*/
		
		infoCandleRepository.save(infoCandle);
		
	}

}
