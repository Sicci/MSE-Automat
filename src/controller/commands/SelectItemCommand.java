package controller.commands;

import controller.Automat;
import controller.exceptions.AutomatException;

public class SelectItemCommand implements ICommand {

	public SelectItemCommand() {
		super();
	}

	@Override
	public void execute(Automat automat) throws AutomatException {
		automat.selectItem();
	}

}
