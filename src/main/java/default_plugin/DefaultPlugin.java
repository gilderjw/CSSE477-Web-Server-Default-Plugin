package default_plugin;

import java.util.HashMap;

import handlers.DeleteRequestHandler;
import handlers.GetRequestHandler;
import handlers.HeadRequestHandler;
import handlers.PostRequestHandler;
import handlers.PutRequestHandler;
import plugins.IPlugin;
import protocol.Protocol;
import request_handlers.IRequestHandler;

public class DefaultPlugin implements IPlugin {

	HashMap<String, IRequestHandler> handlers;

	public DefaultPlugin() {
		this.handlers = new HashMap<>();
		this.addHandler(Protocol.POST, new PostRequestHandler());
		this.addHandler(Protocol.GET, new GetRequestHandler());
		this.addHandler(Protocol.HEAD, new HeadRequestHandler());
		this.addHandler(Protocol.DELETE, new DeleteRequestHandler());
		this.addHandler(Protocol.PUT, new PutRequestHandler());
	}

	@Override
	public void performPluginAction() {
		System.err.println("This shouldn't be a thing");
		throw new RuntimeException();
	}

	@Override
	public void addHandler(String type, IRequestHandler handler) {
		this.handlers.put(type, handler);
	}

	@Override
	public IRequestHandler getHandler(String type) {
		return this.handlers.get(type);
	}

}
