package com.nitya.billingapp.server;

import java.sql.Connection;
import java.sql.DriverManager;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.nitya.billingapp.client.GreetingService;
import com.nitya.billingapp.entity.Expense;
import com.nitya.billingapp.entity.Invoice;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class GreetingServiceImpl extends RemoteServiceServlet implements GreetingService {
	
	public String greetServer(int exUnit,String exProd,String exPrice,int inUnit,String inProd,String inPrice,String input) throws IllegalArgumentException {
		// Verify that the input is valid. 
		
		String jdbcURL = "jdbc:mysql://localhost:3306/billingApp?allowPublicKeyRetrieval=true&useSSL=false";
		String user = "root";
		String pass = "N2nservices!";
		try {
			System.out.println("connecting to database****************");
			Connection myConn = DriverManager.getConnection(jdbcURL,user,pass);
			System.out.println("connected successfully");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		//session hibernate
		SessionFactory factory = (SessionFactory) new Configuration()
				.configure("hibernate.cfg.xml")
				.addAnnotatedClass(Invoice.class)
				.addAnnotatedClass(Expense.class)
				.buildSessionFactory();
		
		Session session = factory.getCurrentSession();
		System.out.println("current sessione is "+ session);
		String intotal = "" + (inUnit * Integer.parseInt(inPrice));
		String extotal = "" + (exUnit * Integer.parseInt(exPrice));
		try {
			inProd = escapeHtml(inProd);
			
			
				// create invoice object
				Invoice invoiceObj = new Invoice(inUnit,inProd,inPrice,intotal);
				Expense expenseObj = new Expense(exUnit,exProd,exPrice,extotal);
				//start Transaction 
				session.beginTransaction();
				//save invoice object 
				session.save(invoiceObj);
				session.save(expenseObj);
				// commit the transaction 
				session.getTransaction().commit();
				System.out.println("Invoice saved !!");
			
			// create invoice object
		
			//start Transaction 
//			session.beginTransaction();
			//save invoice object 
			
			// commit the transaction 
//			session.getTransaction().commit();
			System.out.println("Expense saved !!");
			
			
		}
		finally {
			
		}
		
		return inProd;
		
	}
	private String escapeHtml(String html) {
		if (html == null) {
			return null;
		}
		return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;");
	}
}	


