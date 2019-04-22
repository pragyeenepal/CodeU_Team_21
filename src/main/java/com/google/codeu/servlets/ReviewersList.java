package com.google.codeu.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/reviewers-list")
public class ReviewersList extends HttpServlet {
	
	 @Override
	  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		 
		 response.setContentType("text/html");
		 
		 HashMap<String, ArrayList<String>> reviewers = new HashMap<>();
		 reviewers = tempCollegeReviewers(reviewers);
		 String collegeName = request.getParameter("college");
		 
		 if (collegeName.equals("Simmons University")) {
			 for(int i = 0; i<reviewers.get("Simmons University").size(); i++) {
				 String user = "user-page.html?user="+ reviewers.get("Simmons University").get(i)+".com";
				 response.getWriter().println("<html>" + "<head><title>" + "List" +"</title><link rel = \"stylesheet\" href=\"/css/style.css\"></head><body><a href = "+user+ "><button>"+reviewers.get("Simmons University").get(i)+ "</button></a></body></html>");
			 }
		 }
			 
		 
		 else if (collegeName.equals("Morehouse College")) {
			 for(int i = 0; i<reviewers.get("Morehouse College").size(); i++) {
				 String user = "user-page.html?user="+reviewers.get("Morehouse College").get(i)+".com";
				 response.getWriter().println("<html>" + "<head><title>" + "List" +"</title><link rel = \"stylesheet\" href=\"/css/style.css\"></head><body><a href = "+user+ "><button>"+reviewers.get("Morehouse College").get(i)+ "</button></a></body></html>");
			 }
		}
		
		 else {
			 String user = "user-page.html?user="+reviewers.get("Mount Holyoke College").get(0)+".com";
			 for(int i = 0; i<reviewers.get("Mount Holyoke College").size(); i++) {
				 response.getWriter().println("<html>" + "<head><title>" + "List" +"</title><link rel = \"stylesheet\" href=\"/css/style.css\"></head><body><a href = "+user+ "><button>"+reviewers.get("Mount Holyoke College").get(0)+ "</button></a></body></html>");
		 
			 }
		}
		 
	 }
	
	 public HashMap<String, ArrayList<String>>tempCollegeReviewers(HashMap<String,ArrayList<String>> reviewers) {
		 reviewers.put("Simmons University", new ArrayList<String>());
			reviewers.put("Morehouse College", new ArrayList<String>());
			reviewers.put("Mount Holyoke College", new ArrayList<String>());
			
			reviewers.get("Simmons University").add("pnepal@codeustudents.com");
			reviewers.get("Morehouse College").add("Itilahun@codeustudents.com");
			reviewers.get("Mount Holyoke College").add("mkhuu@codeustudents.com");
			 return reviewers;
		 
	 }

}
