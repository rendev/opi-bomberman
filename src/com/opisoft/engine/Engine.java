package com.opisoft.engine;

import java.util.ArrayList;
import java.util.List;

import com.opisoft.engine.commands.Command;
import com.opisoft.engine.commands.ICommandResult;
import com.opisoft.engine.services.IService;

public class Engine {
	private List<IService> _services;
	public static Engine self = new Engine();
	
	protected Engine() {
		_services = new ArrayList<IService>();
	}
	
	public void registerService(IService service) {
		if (!_services.contains(service)) {			
			_services.add(service);
		}
	}
	
	public void unregisterService(IService service) {
		_services.remove(service);
	}
	
	public ICommandResult process(Command cmd) {
		ICommandResult res = null;
		
		for (IService service : _services) {
			if (service.handledCommands().contains(cmd.getClass())) {
				res = service.process(cmd);
				break;
			}
		}
		return res;
	}
}
