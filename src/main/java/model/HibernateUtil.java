package model;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

	public static Session session;
	public static String erro = null;

	public static Session abrirSession() {

		try {

			SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

			session = sessionFactory.openSession();

			return session;

		} catch (Exception e) {
			erro = e.toString();
			return null;
		}

	}

	public static void fecharSession() {
		if (session != null) {
			session.close();
		}
	}

}
