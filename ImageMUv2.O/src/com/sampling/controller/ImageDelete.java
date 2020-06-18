package com.sampling.controller;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.Session;

import com.sampling.HB.HibernateUtil;
import com.sampling.entities.Image;
import com.sampling.entities.User;

/**
 * Servlet implementation class ImageDelete
 * Used to Delete an Image under a 
 * User instance.
 */
@WebServlet("/ImageDelete")
public class ImageDelete extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("Welcome");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String str = request.getParameter("imageId");
		int id  =  Integer.parseInt(str) ;
		Session session = HibernateUtil.sessionFactoryInstance.openSession();
		session.beginTransaction();
		Image i = session.get(Image.class, id);
		User u = i.getUser();
		u.getImageList().remove(i);
		session.delete(i);
		session.getTransaction().commit();
		session.close();
	}
}
