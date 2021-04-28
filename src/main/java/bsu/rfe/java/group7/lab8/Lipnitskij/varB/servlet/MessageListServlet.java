package bsu.rfe.java.group7.lab8.Lipnitskij.varB.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import bsu.rfe.java.group7.lab8.Lipnitskij.var3.entity.ChatMessage;
import jakarta.servlet.Servlet;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebInitParam;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class MessageListServlet
 */
@WebServlet(
		urlPatterns = { "/messages.do" }, 
		initParams = { 
				@WebInitParam(name = "MESSAGE_LIMIT", value = "7")
		})
public class MessageListServlet extends ChatServlet {
	private static final long serialVersionUID = 1L;
 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setCharacterEncoding("utf8");

		PrintWriter pw = response.getWriter();

		pw.println("<html><head><meta http-equiv='Content-Type' content='text/html; charset=utf-8'/><meta http-equiv='refresh' content='10'></head>");
		pw.println("<body>");

		for (int i=messages.size()-1; i>=0; i--) {
			ChatMessage aMessage = messages.get(i);
			pw.println("<div><strong>" + aMessage.getAuthor().getName() + "</strong>: " + aMessage.getMessage() + "</div>");
		}
		pw.println("</body></html>");
	}
}
