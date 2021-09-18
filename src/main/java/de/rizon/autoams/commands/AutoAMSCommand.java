package de.rizon.autoams.commands;

import java.util.ArrayList;
import java.util.List;

import de.rizon.autoams.Main;
import de.rizon.autoams.utils.AutoAMS;
import de.rizon.autoams.utils.Config;
import de.rizon.autoams.utils.Utils;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;

public class AutoAMSCommand implements ICommand {

	public void processCommand(ICommandSender sender, String[] args) throws CommandException {
		if (args.length >= 1) {
			Config config = Main.getConfig();
			AutoAMS ams = Main.getAutoAMS();
			if (args[0].equalsIgnoreCase("start")) {
				if (!ams.getState()) {
					if (ams.start()) Utils.sendMessage(Main.prefix + "Bot \u00A7aenabled.");
				} else {
					Utils.sendMessage(Main.prefix + "Bot \u00A77is \u00A77already \u00A7active.");
				}
			} else if (args[0].equalsIgnoreCase("stop")) {
				if (ams.getState()) {
					ams.stop();
					Utils.sendMessage(Main.prefix + "Bot \u00A7cstopped.");
				} else {
					Utils.sendMessage(Main.prefix + "Bot \u00A77is \u00A77not \u00A77active.");
				}
			} else if (args[0].equalsIgnoreCase("owner")) {
				if (args.length >= 2) {
					config.setOwner(args[1]);
					config.save();
				} else {
					Utils.sendMessage(Main.prefix + ".autoams \u00A77owner \u00A77[name]");
				}
			} else if (args[0].equalsIgnoreCase("random")) {
				if (args.length >= 2) {
					int value = 0;
					try {
						value = Integer.parseInt(args[1]);
						if (value < 10) {
							Utils.sendMessage(Main.prefix + "Given \u00A77value \u00A77is \u00A77to \u00A77small.");
							return;
						}
						config.setRandom(value);
						config.save();
					} catch (NumberFormatException e) {
						Utils.sendMessage(Main.prefix + "Given \u00A77value \u00A77is \u00A77not \u00A77numeric.");
					}
				} else {
					Utils.sendMessage(Main.prefix + ".autoams \u00A77random \u00A77[seconds]");
				}
			} else if (args[0].equalsIgnoreCase("delay")) {
				if (args.length >= 2) {
					int value = 0;
					try {
						value = Integer.parseInt(args[1]);
						if (value < 10) {
							Utils.sendMessage(Main.prefix + "Given \u00A77value \u00A77is \u00A77to \u00A77small.");
							return;
						}
						config.setDelay(value);
						config.save();
					} catch (NumberFormatException e) {
						Utils.sendMessage(Main.prefix + "Given \u00A77value \u00A77is \u00A77not \u00A77numeric.");
					}
				} else {
					Utils.sendMessage(Main.prefix + ".autoams \u00A77delay \u00A77[seconds]");
				}
			} else if (args[0].equalsIgnoreCase("retrydelay")) {
				if (args.length >= 2) {
					int value = 0;
					try {
						value = Integer.parseInt(args[1]);
						if (value < 2) {
							Utils.sendMessage(Main.prefix + "Given \u00A77value \u00A77is \u00A77to \u00A77small.");
							return;
						}
						config.setRetryDelay(value);
						config.save();
					} catch (NumberFormatException e) {
						Utils.sendMessage(Main.prefix + "Given \u00A77value \u00A77is \u00A77not \u00A77numeric.");
					}
				} else {
					Utils.sendMessage(Main.prefix + ".autoams \u00A77delay \u00A77[seconds]");
				}
			} else {
				sendUsage();
			}
		} else {
			sendUsage();
		}
	}
	
	private void sendUsage() {
		Utils.sendMessage("\u00A78\u00A7m-[-----------\u00A7r \u00A79\u00A7lAutoAMS\u00A7r \u00A78\u00A7m-----------]-");
		Utils.sendMessage(" ");
		Utils.sendMessage("  \u00A78\u25b6 \u00A77.autoams \u00A77start");
		Utils.sendMessage("  \u00A78\u25b6 \u00A77.autoams \u00A77stop ");
		Utils.sendMessage("  \u00A78\u25b6 \u00A77.autoams \u00A77owner \u00A77[name]");
		Utils.sendMessage("  \u00A78\u25b6 \u00A77.autoams \u00A77random \u00A77[seconds]");
		Utils.sendMessage("  \u00A78\u25b6 \u00A77.autoams \u00A77delay \u00A77[seconds]");
		Utils.sendMessage("  \u00A78\u25b6 \u00A77.autoams \u00A77retrydelay \u00A77[seconds]");
		Utils.sendMessage(" ");
		Utils.sendMessage("\u00A78\u00A7m-[-----------\u00A7r \u00A79\u00A7lAutoAMS\u00A7r \u00A78\u00A7m-----------]-");
	}
	
	public String getCommandName() {
		return ".autoams";
	}

	public String getCommandUsage(ICommandSender sender) {
		return ".autoams";
	}

	public List<String> getCommandAliases() {
		List<String> aliases = new ArrayList<String>();
		aliases.add(".autoams");
		return aliases;
	}
	
	public int compareTo(ICommand arg0) {
		return 0;
	}

	public boolean canCommandSenderUseCommand(ICommandSender sender) {
		return true;
	}

	public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
		return null;
	}

	public boolean isUsernameIndex(String[] args, int index) {
		return false;
	}
}