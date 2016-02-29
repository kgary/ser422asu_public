package edu.asupoly.ser422.mvchg.handler;

import java.util.Map;

import javax.servlet.http.HttpSession;

import edu.asupoly.ser422.mvchg.model.LoginCredentials;
import edu.asupoly.ser422.mvchg.model.User;
import edu.asupoly.ser422.mvchg.service.LoginService;

public class LoginHandler implements ActionHandler {

	public String handleIt(Map<String, String[]> params, HttpSession session) {
		String returnPage = "loginfailure";
		String username = ((String[]) params.get("name"))[0];

        // Check username password
        User user = LoginService.login(new LoginCredentials(username, ""));
        if (user != null) {
        		session.setAttribute("user", user);
        		returnPage = "loginsuccess";
        }
		return returnPage;
	}

}
