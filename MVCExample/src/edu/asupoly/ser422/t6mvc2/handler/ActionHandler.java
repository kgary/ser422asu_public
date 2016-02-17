package edu.asupoly.ser422.t6mvc2.handler;

import java.util.Map;

import javax.servlet.http.HttpSession;

public interface ActionHandler {
	public String handleIt(Map<String, String[]> params, HttpSession session);
	
}
