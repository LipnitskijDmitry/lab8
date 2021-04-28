package bsu.rfe.java.group7.lab8.Lipnitskij.varB.servlet;

import java.io.IOException;
import java.util.Calendar;

import bsu.rfe.java.group7.lab8.Lipnitskij.var3.entity.ChatMessage;
import bsu.rfe.java.group7.lab8.Lipnitskij.var3.entity.ChatUser;
import jakarta.servlet.Servlet;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class NewMessageListServlet
 */
@WebServlet("/send_message.do")
public class NewMessageServlet extends ChatServlet {
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		String message = (String)request.getParameter("message");

		if (message!=null && !"".equals(message)) {

			ChatUser author = activeUsers.get((String) 
					request.getSession().getAttribute("name")); 
			synchronized (messages) {

				messages.add(new ChatMessage(message, author, 
						Calendar.getInstance().getTimeInMillis()));
			}
		}

		response.sendRedirect("/lab8/compose_message.html");
	}
}
