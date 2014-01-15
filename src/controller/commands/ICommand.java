package controller.commands;

import controller.Automat;
import controller.exceptions.AutomatException;

public interface ICommand {
	public void execute(Automat automat) throws AutomatException;
}
