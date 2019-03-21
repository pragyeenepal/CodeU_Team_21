/*
 * Copyright 2019 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.codeu.servlets;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.ServingUrlOptions;
import com.google.appengine.api.users.UserService;

import com.google.appengine.api.users.UserServiceFactory;
import com.google.codeu.data.Datastore;
import com.google.codeu.data.Message;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;


/** Handles fetching and saving {@link Message} instances. */
@SuppressWarnings("serial")
@WebServlet("/messages")
public class MessageServlet extends HttpServlet {

  private Datastore datastore;

  @Override
  public void init() {
    datastore = new Datastore();
  }

  /**
   * Responds with a JSON representation of {@link Message} data for a specific user. Responds with
   * an empty array if the user is not provided.
   */
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

    response.setContentType("application/json");

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

  /** Stores a new {@link Message}. */
  @SuppressWarnings("rawtypes")
@Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

	  UserService userService = UserServiceFactory.getUserService();
	    if (!userService.isUserLoggedIn()) {
	      response.sendRedirect("/index.html");
	      return;
	    }
	    String user = userService.getCurrentUser().getEmail();
	    String text = Jsoup.clean(request.getParameter("text"), Whitelist.none());
	    String recipient = request.getParameter("recipient");

	    Message message = new Message(user, text, recipient);
	    datastore.storeMessage(message);

	    response.sendRedirect("/user-page.html?user=" + recipient);
	  }
		
  
  
@SuppressWarnings({ "unchecked", "rawtypes" })
public ArrayList<String> pullLinks(String text) {
	  ArrayList<String> links = new ArrayList<String>();
	   
	  String regex = "(https?://([^\\\\s.]+.?[^\\\\s.]*)+/[^\\\\s.]+.(png|jpg|gif|jpeg|tif|tiff|jif|jfif|jp2|jpx|j2k|j2c|fpx|pcd))";
	  Pattern p = Pattern.compile(regex);
	  Matcher m = p.matcher(text);
	  while(m.find()) {
	  String urlStr = m.group();
	  if (urlStr.startsWith("(") && urlStr.endsWith(")"))
	  {
	  urlStr = urlStr.substring(1, urlStr.length() - 1);
	  }
	  links.add(urlStr);
	  }
	  return links;
	  }
}
