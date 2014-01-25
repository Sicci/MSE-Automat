package controller.commands;

import controller.Automat;

public class ChangeItemIdCommand implements ICommand {

	private String addedString;

	public ChangeItemIdCommand(String addedString) {
		super();

		this.addedString = addedString;
	}

	@Override
	public void execute(Automat automat) {
		automat.addToCurrentId(addedString);
	}

}
