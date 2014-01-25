package controller.commands;

import controller.Automat;
import controller.exceptions.NoItemSelectedException;
import controller.exceptions.NotEnoughChangeException;
import controller.exceptions.ValueNotAcceptedException;

public class InsertMoneyCommand implements ICommand {

	private int value;

	public InsertMoneyCommand(int value) {
		super();

		this.value = value;
	}

	@Override
	public void execute(Automat automat) throws ValueNotAcceptedException, NotEnoughChangeException, NoItemSelectedException {
		automat.insertMoney(value);
	}

}
