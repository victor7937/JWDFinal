package by.victor.jwd.controller.command;

import by.victor.jwd.controller.command.impl.post.Logout;
import by.victor.jwd.controller.command.impl.get.ShowOrders;
import by.victor.jwd.controller.command.impl.get.ShowUsers;
import by.victor.jwd.controller.command.impl.go.*;
import by.victor.jwd.controller.command.impl.post.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Class for getting necessary Command object by command string given
 */
public class CommandProvider {
	private Map<CommandName, Command> commands = new HashMap<>();
	
	public CommandProvider() {
		commands.put(CommandName.AUTH, new Authentication());
		commands.put(CommandName.REGISTRATION, new GoToRegistrationPage());
		commands.put(CommandName.SAVENEWCUSTOMER, new SaveNewCustomer());
		commands.put(CommandName.GOTOMAINPAGE, new GoToMainPage());
		commands.put(CommandName.LOGOUT, new Logout());
		commands.put(CommandName.GOTOCART, new GoToCart());
		commands.put(CommandName.GOTOCATEGORY, new GoToCategory());
		commands.put(CommandName.GOTOCONTACT, new GoToContact());
		commands.put(CommandName.GOTOPRODUCT, new GoToProduct());
		commands.put(CommandName.GOTOSIGNINPAGE, new GoToSignInPage());
		commands.put(CommandName.GOTOPROFILE, new GoToProfile());
		commands.put(CommandName.UPDATEPROFILE, new UpdateProfile());
		commands.put(CommandName.ADDTOCART, new AddToCart());
		commands.put(CommandName.DELETECARTITEM, new DeleteCartItem());
		commands.put(CommandName.GOTOADMINPAGE, new GoToAdminPage());
		commands.put(CommandName.BLOCKUSER, new BlockCustomer());
		commands.put(CommandName.UNBLOCKUSER, new UnblockCustomer());
		commands.put(CommandName.MAKEADMIN, new MakeCustomerAdmin());
		commands.put(CommandName.NEWORDER, new CreateNewOrder());
		commands.put(CommandName.SHOWORDERS, new ShowOrders());
		commands.put(CommandName.SHOWUSERS, new ShowUsers());
		commands.put(CommandName.CHANGESTATUS, new ChangeOrderStatus());
		commands.put(CommandName.USERDECLINE, new UserDecline());
		commands.put(CommandName.ADDFOOTWEAR, new GoToAddingFootwear());
		commands.put(CommandName.NEWFOOTWEAR, new CreateFootwear());
		commands.put(CommandName.GOTOCHANGEPASSWORD, new GoToChangePassword());
		commands.put(CommandName.CHANGEPASSWORD, new ChangePassword());
		commands.put(CommandName.GOTOADDFEATURES, new GoToAddFeatures());
		commands.put(CommandName.GOTOITEMSPAGE, new GoToItemsPage());
		commands.put(CommandName.ADDCATEGORY, new AddNewCategory());
		commands.put(CommandName.ADDCOLOR, new AddNewColor());
		commands.put(CommandName.ADDBRAND, new AddNewBrand());
		commands.put(CommandName.ADDITEM, new AddNewItem());
		commands.put(CommandName.CHANGEITEMSTATUS, new ChangeItemStatus());
		commands.put(CommandName.GOTOEDITFOOTWEAR, new GoToEditFootwear());
		commands.put(CommandName.EDITFOOTWEAR, new EditFootwear());
		commands.put(CommandName.DELETEIMAGE, new DeleteImage());
		commands.put(CommandName.FASTORDER, new CreateFastOrder());

	}
	
	
	public Command takeCommand(String name) {
		CommandName commandName;
		
		commandName = CommandName.valueOf(name.toUpperCase());
		
		return commands.get(commandName);
	}

}
