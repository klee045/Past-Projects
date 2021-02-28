package com.ui;

import com.users.User;


public class UIFactory {
	/** Store all instances of StrategyUI */
	private final StrategyUI studentUI = new StudentUI();
	private final StrategyUI adminUI = new AdminUI();

	/**
	 * Get the UI for appropriate user based on user conditions.
	 *
	 * @param u the user object
	 * @return ContextUI based on the User
	 */
	public ContextUI getUI(User u) {
		ContextUI i = new ContextUI();
		if (u.getUsername().startsWith("U")) {
			i.setStrategyUI(studentUI);
			return i;
		}
		else if (u.getUsername().startsWith("A")) {
			i.setStrategyUI(adminUI);
			return i;
		}
		return null;
	}
}
