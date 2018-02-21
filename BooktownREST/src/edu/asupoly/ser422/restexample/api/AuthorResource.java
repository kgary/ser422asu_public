package edu.asupoly.ser422.restexample.api;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import edu.asupoly.ser422.restexample.model.Author;
import edu.asupoly.ser422.restexample.services.BooktownService;
import edu.asupoly.ser422.restexample.services.BooktownServiceFactory;

@Path("/authors")
@Produces(MediaType.APPLICATION_JSON)
public class AuthorResource {

	private static BooktownService __bService = BooktownServiceFactory.getInstance();
	
	@GET
	public List<Author> getAuthors() {
		return __bService.getAuthors();
	}
	
	@GET
	@Path("/{authorId}")
	public Author getAuthor(@PathParam("authorId") int aid) {
		return __bService.getAuthor(aid);
	}
}
