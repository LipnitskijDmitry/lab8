package bsu.rfe.java.group7.lab8.Lipnitskij.varB.servlet;

import java.io.IOException;

import jakarta.servlet.Servlet;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import bsu.rfe.java.group7.lab8.Lipnitskij.var3.entity.*;
/**
 * Servlet implementation class LogoutServlet
 */
@WebServlet("/logout.do")
public class LogoutServlet extends ChatServlet {
	private static final long serialVersionUID = 1L;
 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = (String) request.getSession().getAttribute("name");

		if (name!=null) {

			ChatUser aUser = activeUsers.get(name);

			if (aUser.getSessionId().equals((String) 
					request.getSession().getId())) {

				synchronized (activeUsers) {
					activeUsers.remove(name);
				}

				request.getSession().setAttribute("name", null);

				response.addCookie(new Cookie("sessionId", null));

				response.sendRedirect(response.encodeRedirectURL("/lab8/"));
			} else {

				response.sendRedirect(response.encodeRedirectURL("/lab8/view.html"));
			}
		} else {

			response.sendRedirect(response.encodeRedirectURL("/lab8/view.html"));
		}
	}
}

