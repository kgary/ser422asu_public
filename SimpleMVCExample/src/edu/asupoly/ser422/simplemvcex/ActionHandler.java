package edu.asupoly.ser422.simplemvcex;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;

public interface ActionHandler {
	public String handleIt(HttpServletRequest req, HttpServletResponse response);
}
