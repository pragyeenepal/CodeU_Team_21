package com.google.codeu.servlets;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.appengine.repackaged.com.google.protobuf.ByteString.Output;
import com.google.codeu.data.Datastore;
import com.google.codeu.data.Message;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

@WebServlet("/review-comment")
public class ReviewAndComment extends HttpServlet {

	private Datastore datastore;

	@Override
	public void init() {
		datastore = new Datastore();
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

		response.setContentType("review-and-comment/html");

		String user = request.getParameter("user");

		if (user == null || user.equals("")) {
			// Request is invalid, return empty array
			response.getWriter().println("[]");
			return;
		}

		List<Message> messages = datastore.getMessages(user);
		Gson gson = new Gson();
		String json = gson.toJson(messages);

		response.getWriter().println(json);
	}

	@SuppressWarnings("deprecation")
	@Override

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		UserService userService = UserServiceFactory.getUserService();
		if (!userService.isUserLoggedIn()) {
			response.sendRedirect("/index.html");
			return;
		}
		String user = userService.getCurrentUser().getEmail();
		String[] reviews = request.getParameterValues("reviews");
		String text = Arrays.toString(reviews);
		// String text = request.getParameter("reviews");
		String recipient = request.getParameter("recipient");

		Message message = new Message(user, text, recipient, "");
		datastore.storeMessage(message);

		response.sendRedirect("/user-page.html?user=" + recipient);


	
}


	 
