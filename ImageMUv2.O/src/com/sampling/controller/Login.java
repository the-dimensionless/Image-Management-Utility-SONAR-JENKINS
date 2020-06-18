package com.sampling.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.sampling.HB.HibernateUtil;
import com.sampling.entities.User;

/**
 * Servlet implementation class Login
 */
	@WebServlet("/login")
	public class Login extends HttpServlet {
		private static final long serialVersionUID = 1L;
	       
	    
		@SuppressWarnings("rawtypes")
		protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			String username = request.getParameter("loginuserName");
			String pass = request.getParameter("loginpass");
			
			
			Session session = HibernateUtil.sessionFactoryInstance.openSession();
			session.beginTransaction();
			Query query = session.createQuery("from User where username=:username");
			query.setParameter("username", username);
			User user = (User)query.uniqueResult();
			session.getTransaction().commit();
			session.close();
			
			if (user==null)
			{
				response.getWriter().print("No such User Exists");
			}
			else if(user.getPass().equals(pass))
			{
				response.getWriter().print("Login sucessful");
				response.addCookie(new Cookie("username", username));
				response.addCookie(new Cookie("pass", pass));
			}
			else
				response.getWriter().print("Sorry, Invalid Password");
			
		}


		@Override
		protected void doGet(HttpServletRequest req, HttpServletResponse resp)
				throws ServletException, IOException {
			resp.sendRedirect("Welcome");
		}
		
		
		

	}