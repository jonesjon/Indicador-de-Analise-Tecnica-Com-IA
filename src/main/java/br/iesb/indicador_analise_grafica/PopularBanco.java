package br.iesb.indicador_analise_grafica;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import br.iesb.indicador_analise_grafica.repository.InfoCandleRepository;
import net.bytebuddy.implementation.Implementation.Context;

@Service
public class PopularBanco {
	
	@Autowired
	private  InfoCandleRepository infoCandle;
	private static InfoCandleRepository infoCandleRepository;
	@PostConstruct
	public void getInfoCandleRepository(){
		infoCandleRepository=infoCandle;
	}
	
	private static EntityManagerFactory em;
	
	public static EntityManager getEntityManager() {
		
		if(em==null || !em.isOpen()) {
			em = Persistence.createEntityManagerFactory("indicador");
		}
		return em.createEntityManager();
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
	
	public static List<InfoCandle> getInfoCandle(){
		return (List<InfoCandle>) infoCandleRepository.findAll();
	}

}
