package edu.asupoly.ser422.cluegame;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

public class GuessServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        PrintWriter out = resp.getWriter();
        resp.setContentType("text/html");
        out.print("<html><body>");
        if(session == null){
            System.out.println("Null session found in GuessServlet doPost");
            resp.sendRedirect(resp.encodeRedirectURL("/"));
            return;
        }

        // Create a new guess object which would represent the current guess the player has made
        Guess playerGuess = new Guess(req.getParameter("suspect"),
                req.getParameter("weapon"),
                req.getParameter("room"));


        Guess winningSecret = (Guess) session.getAttribute("winningSecret");
        System.out.println("Winning Secret " + winningSecret);

        // Linked List to maintain the history of guesses in order
        LinkedList guessHistory = (LinkedList<Guess>) session.getAttribute("guessHistory");
        if(guessHistory == null)
            guessHistory = new LinkedList<Guess>();

        // Compare the current player guess with guesses in the history to check for a duplicate guess
        if(guessHistory.contains(playerGuess)){
            out.println("<p style=\"color:red\">You've already made the " + playerGuess + ". Please try again</p>");
            out.print("<form method=\"GET\" action=\"player-servlet\">");
            out.print("<input type=\"Submit\" value=\"Continue\"/>");
            out.print("</form>");
            return;
        }

        // If not duplicate, add the current guess to the history
        guessHistory.add(playerGuess);
        session.setAttribute("guessHistory", guessHistory);


        // Check if the current guess is the winning guess
        if(playerGuess.equals(winningSecret)) {
            out.println("<p style=\"color:red\">Your " + playerGuess + " was correct. You win!</p>");
            session.invalidate();
            out.print("<form method=\"GET\" action=\"./\">");
            out.print("<input type=\"Submit\" value=\"Continue\"/>");
            out.print("</form>");
        }

        // Otherwise respond that the guess and incorrect and display one component of the guess which is incorrect
        else {
            out.println("<p style=\"color:red\">Your " + playerGuess + " was incorrect. You guessed "
                    + playerGuess.whichIsWrong(winningSecret)
                    + " incorrectly. Please try again</p>");
            ArrayList<String> computerRooms = (ArrayList<String>) session.getAttribute("computerRoomsList");
            ArrayList<String> computerSuspects = (ArrayList<String>) session.getAttribute("computerSuspectsList");
            ArrayList<String> computerWeapons = (ArrayList<String>) session.getAttribute("computerWeaponsList");

            // Randomly generate a computer guess and keep generating until the guess is unique by checking
            // against history
            Guess computerGuess = new Guess();
            Random r = new Random();
            do {
                computerGuess.room = computerRooms.get(r.nextInt(computerRooms.size()));
                computerGuess.weapon = computerWeapons.get(r.nextInt(computerWeapons.size()));
                computerGuess.suspect = computerSuspects.get(r.nextInt(computerSuspects.size()));

            }while (guessHistory.contains(computerGuess));
            guessHistory.add(computerGuess);
            session.setAttribute("guessHistory", guessHistory);
            out.print("Computer's " + computerGuess);
            out.print("</br>");

            // If computer's guess is correct. Responds back with a message and ends the game
            if(computerGuess.equals(winningSecret)){
                out.println("<p style=\"color:red\">Computer guess is correct. Computer wins!</p>");
                session.invalidate();
                out.print("<form method=\"GET\" action=\"./\">");
                out.print("<input type=\"Submit\" value=\"Continue\"/>");
                out.print("</form>");
            }

            // Otherwise respond by telling the user the computer guess was incorrect and display one incorrect
            // component of the guess
            else{
                out.println("<p style=\"color:red\">Computer guess is incorrect. Computer guessed "
                        + computerGuess.whichIsWrong(winningSecret) + " incorrectly</p>");
                out.print("<form method=\"GET\" action=\"player-servlet\">");
                out.print("<input type=\"Submit\" value=\"Continue\"/>");
                out.print("</form>");
            }

        }
    }
}
