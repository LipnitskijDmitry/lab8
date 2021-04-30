package bsu.rfe.java.group7.lab8.Lipnitskij.varB.servlet;

import java.io.IOException;
import java.util.ArrayList;
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
	
	private ArrayList<String> HTMLOpen;
	private ArrayList<String> HTMLClose;
	
	
	private String cutHTML(String message) {
		

		int f1= 0;
		int f2=0;
		int l=0;
		int i=0;
		int j=0;
		Boolean b = true;
		String Nmessage=message;
		String substr;
		
		if(HTMLOpen==null && HTMLClose==null) {
			
			HTMLOpen = new ArrayList<String>(10);
			HTMLClose = new ArrayList<String>(10);
			
			HTMLOpen.add("<h>");
			HTMLClose.add("</h>");
			
			HTMLOpen.add("<i>");
			HTMLClose.add("</i>");
			
			HTMLOpen.add("<b>");
			HTMLClose.add("</b>");
			
			HTMLOpen.add("<strong>");
			HTMLClose.add("</strong>");
			
			HTMLOpen.add("<p>");
			HTMLClose.add("</p>");
		}
		
		while(b) {

			for (i=0; i<HTMLOpen.size(); i++) {
				f1 = Nmessage.indexOf(HTMLOpen.get(i));
				if( f1!=-1 ) {
					break;
				}
			}
			for (j=0; j<HTMLOpen.size(); j++) {
				f2 = Nmessage.indexOf(HTMLOpen.get(j));
				if( f2!=-1 && f2>f1 ) {
					break;
					
				}
			}


			if(j==HTMLOpen.size()&& f2<=f1) {
				f2=-1;
			}

			if(f2!=-1 && f1!=-1) {
				l = Nmessage.lastIndexOf(HTMLClose.get(i),f2);
			} else if(f1!=-1){
				l = Nmessage.lastIndexOf(HTMLClose.get(i));
			}
			
			if( f1!=-1 && l!=-1) {
				substr = Nmessage.substring(f1,l+HTMLClose.get(i).length());
				Nmessage=Nmessage.replaceFirst(substr,"");
				
			} else if(f1!=-1) {
				Nmessage=Nmessage.replaceFirst(HTMLOpen.get(i),"");
			}
			if(f1==-1) {
				b=false;
			}
		
		}
		for (i=0; i<HTMLOpen.size(); i++) {
			f2= Nmessage.indexOf(HTMLClose.get(i));

			if( f2!=-1 ) {
				Nmessage=Nmessage.replaceAll(HTMLClose.get(i),"");
			}
		}
		
		return Nmessage;
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		String message = cutHTML((String)request.getParameter("message"));

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
