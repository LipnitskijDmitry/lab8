package bsu.rfe.java.group7.lab8.Lipnitskij.varB.servlet;

import jakarta.servlet.Servlet;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import java.util.ArrayList;
import java.util.HashMap;
import bsu.rfe.java.group7.lab8.Lipnitskij.var3.entity.*;


public class ChatServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected HashMap<String, ChatUser> activeUsers;

	protected ArrayList<ChatMessage> messages;
	@SuppressWarnings("unchecked")
	public void init() throws ServletException {
		super.init();

		activeUsers = (HashMap<String, ChatUser>) 
				getServletContext().getAttribute("activeUsers");
		messages = (ArrayList<ChatMessage>) 
				getServletContext().getAttribute("messages");

		if (activeUsers==null) {

			activeUsers = new HashMap<String, ChatUser>();

			getServletContext().setAttribute("activeUsers", 
					activeUsers);
		}

		if (messages==null) {

			messages = new ArrayList<ChatMessage>(100);

			getServletContext().setAttribute("messages", messages);
		}
	} 
}