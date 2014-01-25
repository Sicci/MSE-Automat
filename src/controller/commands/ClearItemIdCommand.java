package controller.commands;

import controller.Automat;

public class ClearItemIdCommand implements ICommand {

	public ClearItemIdCommand() {
		super();

	}

	@Override
	public void execute(Automat automat) {
		automat.clearCurrentId();
	}
}
