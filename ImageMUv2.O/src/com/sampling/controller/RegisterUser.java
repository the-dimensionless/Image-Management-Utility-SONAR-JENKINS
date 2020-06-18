package com.sampling.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;

import com.sampling.HB.HibernateUtil;
import com.sampling.entities.Image;
import com.sampling.entities.User;

/**
 * Servlet implementation class RegisterUser
 */
@WebServlet("/RegisterUser")
public class RegisterUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("index.html");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name  = request.getParameter("name");
		String userName  = request.getParameter("userName");
		String email  = request.getParameter("email");
		String pass  = request.getParameter("pass");
		
		User user = new User();
		user.setEmail(email);
		user.setFullName(name);
		user.setPass(pass);
		user.setUserName(userName);
		user.setImageList(new ArrayList<Image>());
		
		Session session = HibernateUtil.sessionFactoryInstance.openSession();
		session.beginTransaction();
		try {
			session.save(user);
			session.getTransaction().commit();
		} catch (Exception e) {
			response.getWriter().println("User could not be saved !! Try using unique Username");
			session.getTransaction().rollback();
			session.close() ;
			e.printStackTrace();
			return ;
		}
		session.close() ;
		response.getWriter().print("Registered sucessfully");
		response.addCookie(new Cookie("username", userName));
		response.addCookie(new Cookie("pass", pass));
	}

}
