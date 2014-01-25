package controller.commands;

import controller.Automat;
import controller.exceptions.NoPartialCardPaymentException;
import controller.exceptions.NotEnoughChangeException;

public class InsertCardCommand implements ICommand {

	private String card;

	public InsertCardCommand(String card) {
		super();

		this.card = card;
	}

	@Override
	public void execute(Automat automat) throws NotEnoughChangeException, NoPartialCardPaymentException {
		automat.payWithCard(card);
	}

}
