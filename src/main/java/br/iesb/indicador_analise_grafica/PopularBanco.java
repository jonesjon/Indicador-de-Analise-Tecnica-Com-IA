package br.iesb.indicador_analise_grafica;

import javax.persistence.*;

public class PopularBanco {
	
	private static EntityManagerFactory emf;
	
	public static EntityManager getEntityManager() {
		
		if(emf==null || !emf.isOpen()) {
			emf = Persistence.createEntityManagerFactory("indicador");
		}
		return emf.createEntityManager();
	}
	
	public static void adicionaCandle(InfoCandle infoCandle){
		EntityManager em = getEntityManager();
	
		em.getTransaction().begin();
		em.persist(infoCandle);
		em.getTransaction().commit();
		
		em.close();
		emf.close();
	}

}
