package com.opisoft.engine;

import java.util.ArrayList;
import java.util.List;

import com.opisoft.engine.commands.Command;
import com.opisoft.engine.commands.ICommandResult;
import com.opisoft.engine.commands.LoadTexturesCommand;
import com.opisoft.engine.commands.PositionCommand;
import com.opisoft.engine.commands.RenderCommand;
import com.opisoft.engine.commands.ResizeCommand;
import com.opisoft.engine.components.ComponentFactory;
import com.opisoft.engine.components.Move;
import com.opisoft.engine.components.Position;
import com.opisoft.engine.components.Render;
import com.opisoft.engine.components.Size;
import com.opisoft.engine.services.CollisionService;
import com.opisoft.engine.services.IService;
import com.opisoft.engine.services.PositionService;
import com.opisoft.engine.services.RenderService;
import com.opisoft.engine.services.SizeService;
import com.stickycoding.Rokon.Rokon;
import com.stickycoding.Rokon.RokonActivity;

public class GameEngine {
	public static final GameEngine self = new GameEngine();

	private static MapLoader _mapLoader = new MapLoader();
	private GameMap _currMap;
	private List<IService> _services;	
	
	protected GameEngine() {
		_services = new ArrayList<IService>();
		registerComponents();
		registerServices();
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
			
	public RokonActivity gameActivity() {
		return (RokonActivity) Rokon.getRokon().getActivity();
	}
	
	public GameMap currentMap() {
		return _currMap;
	}
	
	public Entity getEntity(String name) {
		return currentMap().getEntity(name);
	}

	public Metrics mapMetrics() {
		return currentMap().metrics();
	}
		
	public boolean loadMap(String mapPath) {
		boolean res = false;
		_mapLoader.setActivity(gameActivity());		

		GameMap map = _mapLoader.loadMap(mapPath);
        
		if (map != null) {
			try {
				_currMap = map;
				initBackground();
				process(new LoadTexturesCommand());
				resizeMapEntities();
				positionMapEntities();
				renderMapEntities();
				res = true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return res;
	}
	
	protected void initBackground() {
		Rokon.getRokon().setBackground(_currMap.background());		
	}	
	
	protected void registerComponents() {
		ComponentFactory.self.registerObject("position", Position.class);
		ComponentFactory.self.registerObject("size", Size.class);
		ComponentFactory.self.registerObject("render", Render.class);
		ComponentFactory.self.registerObject("move", Move.class);
	}
	
	protected void registerServices() {
		registerService(new PositionService());
		registerService(new SizeService());
		registerService(new RenderService());
		registerService(new CollisionService());
	}
	
	protected void positionMapEntities() {
		process(new PositionCommand(currentMap().entities()));
	}

	protected void resizeMapEntities() {
		process(new ResizeCommand(currentMap().entities()));
	}

	protected void renderMapEntities() {
		process(new RenderCommand(currentMap().entities()));
	}
}
