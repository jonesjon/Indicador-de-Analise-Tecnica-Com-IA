package br.iesb.indicador_analise_grafica;

import javax.persistence.*;

public class PopularBanco {
	
	private static EntityManagerFactory emf;
	
	private static EntityManager getEntityManager() {
		
		if(emf==null) {
			emf = Persistence.createEntityManagerFactory("indicador");
		}
		return emf.createEntityManager();
	}
	
	public static void adicionaCandle(Candle candle){
		EntityManager em = getEntityManager();
		
		em.getTransaction().begin();
		em.persist(candle);
		em.getTransaction().commit();
		
		em.close();
		emf.close();
	}

}
