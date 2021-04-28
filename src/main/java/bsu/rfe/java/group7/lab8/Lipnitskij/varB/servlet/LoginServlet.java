package bsu.rfe.java.group7.lab8.Lipnitskij.varB.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebInitParam;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Calendar;

import bsu.rfe.java.group7.lab8.Lipnitskij.var3.entity.*;
/**
 * Servlet implementation class LoginServlet
 */
@WebServlet(
		urlPatterns = { "/login.do" }, 
		initParams = { 
				@WebInitParam(name = "SESSION_TIMEOUT", value = "3600")
		})
public class LoginServlet extends ChatServlet {
	private static final long serialVersionUID = 1L;

	private int sessionTimeout = 10*60;
 
	public void init() throws ServletException {
		super.init();

		String value = getServletConfig().getInitParameter("SESSION_TIMEOUT");

		if (value!=null) {
			sessionTimeout = Integer.parseInt(value);
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String name = (String)request.getSession().getAttribute("name");

		String errorMessage = (String)request.getSession().getAttribute("error");

		String previousSessionId = null;

		if (name==null) {

			for (Cookie aCookie: request.getCookies()) {
				if (aCookie.getName().equals("sessionId")) {

					previousSessionId = aCookie.getValue();
					break;
				}
			}
			if (previousSessionId!=null) {

				for (ChatUser aUser: activeUsers.values()) {
					if(aUser.getSessionId().equals(previousSessionId)) {
						
						name = aUser.getName();
						aUser.setSessionId(request.getSession().getId());
					}
				}
			}
		}

		if (name!=null && !"".equals(name)) {
			errorMessage = processLogonAttempt(name, request, response);
		} 

		response.setCharacterEncoding("utf8");

		PrintWriter pw = response.getWriter();
		pw.println("<html><head><title>Мега-чат!</title><meta http-equiv='Content-Type' content='text/html; charset=utf-8'/></head>");

		if (errorMessage!=null) {
			pw.println("<p><font color='red'>" + errorMessage + "</font></p>");
		}

		pw.println("<form action='/lab8/' method='post'>Введите имя: <input type='text' name='name' value=''><input type='submit' value='Войти вчат'>");
		pw.println("</form></body></html>");

		request.getSession().setAttribute("error", null);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		String name = (String)request.getParameter("name");

		String errorMessage = null;
		if (name==null || "".equals(name)) {

			errorMessage = "Имя пользователя не может быть пустым!";
		} else {

			errorMessage = processLogonAttempt(name, request, response);
		
		}
		if (errorMessage!=null) {

			request.getSession().setAttribute("name", null);

			request.getSession().setAttribute("error", errorMessage);

			response.sendRedirect(response.encodeRedirectURL("/lab8/"));
		}
	}

	String processLogonAttempt(String name, HttpServletRequest request, 
	HttpServletResponse response) throws IOException {

	String sessionId = request.getSession().getId();

	ChatUser aUser = activeUsers.get(name);
	if (aUser==null) {

	aUser = new ChatUser(name, 
	Calendar.getInstance().getTimeInMillis(), sessionId);

	synchronized (activeUsers) {
	activeUsers.put(aUser.getName(), aUser);
	}
	}
	if (aUser.getSessionId().equals(sessionId) || 
	aUser.getLastInteractionTime()<(Calendar.getInstance().getTimeInMillis()-
	sessionTimeout*1000)) {

	request.getSession().setAttribute("name", name);

	aUser.setLastInteractionTime(Calendar.getInstance().getTimeInMillis());

	Cookie sessionIdCookie = new Cookie("sessionId", sessionId);

	sessionIdCookie.setMaxAge(60*60*24*365);

	response.addCookie(sessionIdCookie);

	response.sendRedirect(response.encodeRedirectURL("/lab8/view.html"));

	return null;
	} else {

	return "Извините, но имя <strong>" + name + "</strong> уже кем-то занято. Пожалуйста выберите другое имя!";
	}
	}
}