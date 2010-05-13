package com.opisoft.engine.services;

import java.util.List;

import com.opisoft.engine.commands.Command;
import com.opisoft.engine.commands.ICommandResult;

public interface IService {
	ICommandResult process(Command command);
	List<Class<? extends Command>> handledCommands();
}
