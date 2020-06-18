package com.sampling.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Welcome
 * A User validation Helper Servlet that mostly is called 
 * when get method request is achieved on Servlets/JSPs that
 * are only meant to handle POST type requests.
 * 
 * Servlets validates the user and then redirects
 * them to either the index page or dashboard page (ImageUtility) .
 */
@WebServlet("/Welcome")
public class Welcome extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Cookie[] cookies = req.getCookies();
		if (cookies!=null)
		{
			for (Cookie c : cookies) {
				if(c.getName().equals("username"))
				{
					resp.sendRedirect("ImageUtility.jsp");
					return ;
				}
			}
		}
		resp.sendRedirect("loginPage.html");
	}
}