package handlers;

import java.io.File;

import protocol.HttpRequest;
import protocol.HttpResponse;
import protocol.Protocol;
import request_handlers.IRequestHandler;
import response_creators.ResponseCreator;
import server.Server;

public class GetRequestHandler implements IRequestHandler {

	@Override
	public HttpResponse handleRequest(HttpRequest request, Server server) {
		// Map<String, String> header = request.getHeader();
		// String date = header.get("if-modified-since");
		// String hostName = header.get("host");
		//
		// Handling GET request here
		// Get relative URI path from request
		String uri = request.getUri();
		// Get root directory path from server
		String rootDirectory = server.getRootDirectory();
		// Combine them together to form absolute file path
		File file = new File(rootDirectory + uri);

		HttpResponse response;
		ResponseCreator rc = new ResponseCreator();
		rc.fillGeneralHeader(rc.getResponse(), Protocol.CLOSE)
			.setResponseVersion(Protocol.VERSION);

		// Check if the file exists
		if (file.exists()) {
			if (file.isDirectory()) {
				// Look for default index.html file in a directory
				String location = rootDirectory + uri + System.getProperty("file.separator") + Protocol.DEFAULT_FILE;
				file = new File(location);
				if (file.exists()) {
					// Lets create 200 OK response
					response = rc.setResponseStatus(Protocol.OK_CODE)
							.setResponsePhrase(Protocol.OK_TEXT)
							.setResponseFile(file)
							.getResponse();
				} else {
					// File does not exist so lets create 404 file not found code
					response = rc.setResponseStatus(Protocol.NOT_FOUND_CODE)
							.setResponsePhrase(Protocol.NOT_FOUND_TEXT)
							.setResponseFile(null)
							.getResponse();
				}
			} else { // Its a file
						// Lets create 200 OK response
				response = rc.setResponseStatus(Protocol.OK_CODE)
						.setResponsePhrase(Protocol.OK_TEXT)
						.setResponseFile(file)
						.getResponse();
			}
		} else {
			// File does not exist so lets create 404 file not found code
			response = rc.setResponseStatus(Protocol.NOT_FOUND_CODE)
					.setResponsePhrase(Protocol.NOT_FOUND_TEXT)
					.setResponseFile(null)
					.getResponse();
		}
		return response;
	}
}
