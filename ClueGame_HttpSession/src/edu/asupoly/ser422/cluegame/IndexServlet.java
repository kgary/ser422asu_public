package edu.asupoly.ser422.cluegame;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

public class IndexServlet extends HttpServlet {
    @Override
    public void init() throws ServletException {

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        HttpSession session = req.getSession(false);
        out.print("<html><body>");
        out.print("<h1>Clue Game</h1>");
        out.print("<form method=\"POST\" action=\"player-servlet\">");
        out.print("<label>Please enter your name </label>");
        out.print("<input type=\"text\" name=\"playerName\">");
        out.print("<input type=\"submit\" value=\"Submit\"/>");
        out.print("</form>");

        out.print("</body></html>");


        out.close();

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    }
}
