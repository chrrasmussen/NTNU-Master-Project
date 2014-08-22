package no.ntnu.assignmentsystem.model.impl;

import java.util.Properties;

import no.ntnu.assignmentsystem.model.ModelPackage;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.teneo.PersistenceOptions;
import org.eclipse.emf.teneo.hibernate.HbDataStore;
import org.eclipse.emf.teneo.hibernate.HbHelper;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Environment;

public class HibernateModelLoader {
	public void Temp() {
		// To configure Hibernate, supply properties describing the JDBC driver,
		// URL, username/password and SQL dialect.
		// By default the properties are obtained from the file
		// "hibernate.properties" at the classpath root.
		//
		// Alternatively, you can set the properties programmatically:
		// 
		// For more information see <a
		// href="http://www.hibernate.org/hib_docs/v3/reference/en/html/session-configuration.html#configuration-programmatic">
		// section 3.1 of the Hibernate manual</a>.
		//
		Properties hibernateProperties = new Properties();
		 
		//
		// 1) From a ".properties" file or stream.
		// InputStream in = ...
		// hibernateProperties.load(in);
		//
		// 2) or populated manually:
		hibernateProperties.setProperty(Environment.DIALECT, "org.hibernate.dialect.MySQL5InnoDBDialect");
		hibernateProperties.setProperty(Environment.DRIVER, "com.mysql.jdbc.Driver");
		hibernateProperties.setProperty(Environment.URL, "jdbc:mysql://127.0.0.1:8889/test");
		hibernateProperties.setProperty(Environment.USER, "root");
		hibernateProperties.setProperty(Environment.PASS, "root");
		
		// set a specific option
		// see this page http://wiki.eclipse.org/Teneo/Hibernate/Configuration_Options
		// for all the available options
		hibernateProperties.setProperty(PersistenceOptions.CASCADE_POLICY_ON_NON_CONTAINMENT, "REFRESH,PERSIST,MERGE");
		 
		// use the joined inheritance mapping
		hibernateProperties.setProperty(PersistenceOptions.INHERITANCE_MAPPING, "JOINED");
		 
		// use an annotations file as an example
		// this lets the library use a special table 
		hibernateProperties.setProperty(PersistenceOptions.PERSISTENCE_XML, "no/ntnu/assignmentsystem/model/Annotations.xml");
		
		// Create the DataStore.
		final String dataStoreName = "LibraryDataStore";
		final HbDataStore dataStore = HbHelper.INSTANCE.createRegisterDataStore(dataStoreName);
		dataStore.setDataStoreProperties(hibernateProperties);
		 
		// Configure the EPackages used by this DataStore.
		dataStore.setEPackages(new EPackage[] { ModelPackage.eINSTANCE });
		 
		// Initialize the DataStore. This sets up the Hibernate mapping and, in
		// turn, creates the corresponding tables in the database.
		try {
			dataStore.initialize();
		} finally {
			// print the hibernate mapping
//			System.err.println(dataStore.getMappingXML());
		}
		 
		final SessionFactory sessionFactory = dataStore.getSessionFactory();
		
		// Open a new Session and start transaction.
		final Session session = sessionFactory.openSession();
		session.beginTransaction();
		 
		// Create a writer and book...
//		Course course = ModelFactory.eINSTANCE.createCourse();
//		course.setId("tdt4100");
//		course.setTitle("Objektorientert programmering med Java");
//		session.save(course);
		
//		UoD uod = (UoD)session.createQuery("From UoD").uniqueResult();
//		uod.getCourses().add(course);
//		session.save(course);
		
//		Optional<Assignment> assignment = course.ifPresent(course -> course.get);
//		System.out.println(course.get());
//		uod.getCourses().stream().filter(course -> course.getId().equals("tdt4100")).forEach(course -> System.out.println(course));
//		System.out.println(uod.getCourses());
//		User user = ModelFactory.eINSTANCE.createUser();
//		user.setEmail("christir@stud.ntnu.no");
//		session.save(user);
//		
//		UoD uod = ModelFactory.eINSTANCE.createUoD();
//		uod.getUsers().add(user);
//		session.save(uod);
		
		
//		Query query = session.createQuery("FROM User");
//		List<?> users = query.list();
//		for (Iterator<?> it = users.iterator(); it.hasNext();) {
//			User user = (User)it.next();
//			System.out.println(user.getEmail());
//		}
		 
		// Commit the changes to the database.
		session.getTransaction().commit();
		// Close the session. Not necessary if
		session.close();
	}
}
