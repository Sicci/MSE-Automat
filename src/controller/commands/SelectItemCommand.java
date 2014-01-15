package controller.commands;

import controller.Automat;
import controller.exceptions.AutomatException;

public class SelectItemCommand implements ICommand {
	public String numCode;

	public SelectItemCommand(String numCode) {
		super();
		this.numCode = numCode;
	}

	@Override
	public void execute(Automat automat) throws AutomatException {
		automat.selectItem(numCode);
	}

}
