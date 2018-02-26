package edu.asupoly.ser422.restexample.api;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
	/*
	@POST
	@Consumes("text/plain")
    public int createAuthor(String name) {
		String[] names = name.split(" ");
		int aid = __bService.createAuthor(names[0], names[1]);
		return aid;
    }
    */
	@POST
	@Consumes("text/plain")
    public Response createAuthor(String name) {
		String[] names = name.split(" ");
		int aid = __bService.createAuthor(names[0], names[1]);
		if (aid == -1) {
			return Response.status(500).entity("{ \" EXCEPTION INSERTING INTO DATABASE! \"}").build();
		} else if (aid == 0) {
			return Response.status(500).entity("{ \" ERROR INSERTING INTO DATABASE! \"}").build();
		}
		return Response.status(201).entity("{ \"Author\" : \"" + aid + "\"}").build();
    }
	
	@PUT
	@Consumes("application/json")
    public Response updateAuthor(Author a) {
		if (__bService.updateAuthor(a)) {
			return Response.status(201).entity("{ \"Author\" : \"" + a.getAuthorId() + "\"}").build();
		} else {
			return Response.status(404, "{ \"message \" : \"No such Author " + a.getAuthorId() + "\"}").build();
		}
    }
	
	@DELETE
    public Response deleteAuthor(@QueryParam("id") int aid) {
		if (__bService.deleteAuthor(aid)) {
			return Response.status(204).build();
		} else {
			return Response.status(404, "{ \"message \" : \"No such Author " + aid + "\"}").build();
		}
    }
	
	@PATCH
	public Response patchAuthor(@QueryParam("id") int aid) {
		return Response.status(405, "{ \"message \" : \"PATCH not supported\"}").build();
    }
}
