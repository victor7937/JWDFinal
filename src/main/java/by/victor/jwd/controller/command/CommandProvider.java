package by.victor.jwd.controller.command;

import java.util.HashMap;
import java.util.Map;

import by.victor.jwd.controller.command.impl.*;

public class CommandProvider {
	private Map<CommandName, Command> commands = new HashMap<>();
	
	public CommandProvider() {
		commands.put(CommandName.AUTH, new Authentication());
		commands.put(CommandName.REGISTRATION, new GoToRegistrationPage());
		commands.put(CommandName.SAVENEWCUSTOMER, new SaveNewCustomer());
		commands.put(CommandName.GOTOINDEXPAGE, new GoToIndexPage());
		commands.put(CommandName.GOTOMAINPAGE, new GoToMainPage());
		commands.put(CommandName.LOGOUT, new Logout());
		commands.put(CommandName.GOTOCART, new GoToCart());
		commands.put(CommandName.GOTOCATEGORY, new GoToCategory());
		commands.put(CommandName.GOTOCONTACT, new GoToContact());
		commands.put(CommandName.GOTOPRODUCT, new GoToProduct());
		commands.put(CommandName.GOTOSIGNINPAGE, new GoToSignInPage());
		commands.put(CommandName.GOTOPROFILE, new GoToProfile());
	}
	
	
	public Command takeCommand(String name) {
		CommandName commandName;
		
		commandName = CommandName.valueOf(name.toUpperCase());
		
		return commands.get(commandName);
	}

}
