package com.sampling.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.hibernate.Query;
import org.hibernate.Session;

import com.sampling.HB.HibernateUtil;
import com.sampling.constants.Constants;
import com.sampling.entities.Image;
import com.sampling.entities.User;

/**
 * Servlet implementation class ImageSave
 * Used to upload or save an image under
 * a User instance.
 * Saves the uploaded file onto database
 * as a Binary Long Object (BLOB)
 */
@SuppressWarnings("deprecation")
@WebServlet("/ImageSave")
@MultipartConfig(maxFileSize = Constants.MAX_FILE_SIZE) 

public class ImageSave extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
	IOException {
		response.sendRedirect("Welcome");
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
	IOException {
		String username = null ;
		Cookie[] cookies = request.getCookies();
		if (cookies!=null)
		{
			for (Cookie c : cookies) {
				if(c.getName().equals("username"))
				{
					username = c.getValue();
				}
			}
		}
		if (username==null) {
			PrintWriter out = response.getWriter();
			out.println("<script type=\"text/javascript\">");
			out.println("alert('Login First!!');");
		    out.println("location='Welcome';");
			out.println("</script>");
			return ;
		}
		
		Part part = null ;
		try {
			part = request.getPart("imgFile");
		} catch (Exception e) {
			PrintWriter out = response.getWriter();
			out.println("<script type=\"text/javascript\">");
			out.println("alert('Image size greater than 1 MB!!');");
		    out.println("location='ImageUtility.jsp';");
			out.println("</script>");
			return  ;
		}
		
		String fileName = part.getSubmittedFileName().substring(3);
		String SAVE_DIR = "Images";
		String appPath = request.getServletContext().getRealPath("");
		String savePath = appPath + SAVE_DIR;
		Files.createDirectories(Paths.get(savePath));
		String filePath = savePath + File.separator + fileName ;
		InputStream inputStream = part.getInputStream();
		byte[] bFile = new byte[(int) part.getSize()];
		inputStream.read(bFile);
		
		System.out.println(part.getName());
		System.out.println("File name is "+fileName);
		System.out.println("save Path is "+savePath);
		System.out.println("Actual Save path is "+filePath);
		part.write(filePath );

		Image i = new Image() ;
		i.setImg(new File(filePath)) ;
		i.setImageData(bFile);
				
		Session session = HibernateUtil.sessionFactoryInstance.openSession();
		session.beginTransaction();
		
		Query query = session.createQuery("from User where username=:username");
		query.setParameter("username", username);
		
		User user = (User)query.uniqueResult();
		user.getImageList().add(i);
		i.setUser(user);
		try {
			session.save(user);
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
			session.close();
			PrintWriter out = response.getWriter();
			out.println("<script type=\"text/javascript\">");
			out.println("alert('Image could not be saved !!');");
		    out.println("location='ImageUtility.jsp';");
			out.println("</script>");
			return  ;
		}
		
		
		Query query2 = session.createQuery("from Image where user_id=:username");
		query2.setParameter("username", username);
		
		List<Image> list = query2.getResultList();
		
		
		
		long totalSize = 0l;
		for (Image i2 : list) {
			totalSize += i2.getImgSize();
		}
		
		if (totalSize > Constants.MAX_TOTAL_IMAGES) {
			System.out.println("Reached max level");
			session.close();
			PrintWriter out = response.getWriter();
			out.println("<script type=\"text/javascript\">");
			out.println("alert('Image size greater than 10 MB!!');");
		    out.println("location='ImageUtility.jsp';");
			out.println("</script>");
			return  ;
		}
		
		
		
		session.close();
		response.getWriter().print("");
		response.sendRedirect("ImageUtility.jsp");
		
	}
}