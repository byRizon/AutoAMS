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
					if (ams.start()) Utils.sendMessage(Main.prefix + "Bot §aenabled.");
				} else {
					Utils.sendMessage(Main.prefix + "Bot §7is §7already §active.");
				}
			} else if (args[0].equalsIgnoreCase("stop")) {
				if (ams.getState()) {
					ams.stop();
					Utils.sendMessage(Main.prefix + "Bot §cstopped.");
				} else {
					Utils.sendMessage(Main.prefix + "Bot §7is §7not §7active.");
				}
			} else if (args[0].equalsIgnoreCase("owner")) {
				if (args.length >= 2) {
					config.setOwner(args[1]);
					config.save();
				} else {
					Utils.sendMessage(Main.prefix + ".autoams §7owner §7[name]");
				}
			} else if (args[0].equalsIgnoreCase("random")) {
				if (args.length >= 2) {
					int value = 0;
					try {
						value = Integer.parseInt(args[1]);
						if (value < 10) {
							Utils.sendMessage(Main.prefix + "Given §7value §7is §7to §7small.");
							return;
						}
						config.setRandom(value);
						config.save();
					} catch (NumberFormatException e) {
						Utils.sendMessage(Main.prefix + "Given §7value §7is §7not §7numeric.");
					}
				} else {
					Utils.sendMessage(Main.prefix + ".autoams §7random §7[seconds]");
				}
			} else if (args[0].equalsIgnoreCase("delay")) {
				if (args.length >= 2) {
					int value = 0;
					try {
						value = Integer.parseInt(args[1]);
						if (value < 10) {
							Utils.sendMessage(Main.prefix + "Given §7value §7is §7to §7small.");
							return;
						}
						config.setDelay(value);
						config.save();
					} catch (NumberFormatException e) {
						Utils.sendMessage(Main.prefix + "Given §7value §7is §7not §7numeric.");
					}
				} else {
					Utils.sendMessage(Main.prefix + ".autoams §7delay §7[seconds]");
				}
			} else if (args[0].equalsIgnoreCase("retrydelay")) {
				if (args.length >= 2) {
					int value = 0;
					try {
						value = Integer.parseInt(args[1]);
						if (value < 2) {
							Utils.sendMessage(Main.prefix + "Given §7value §7is §7to §7small.");
							return;
						}
						config.setRetryDelay(value);
						config.save();
					} catch (NumberFormatException e) {
						Utils.sendMessage(Main.prefix + "Given §7value §7is §7not §7numeric.");
					}
				} else {
					Utils.sendMessage(Main.prefix + ".autoams §7delay §7[seconds]");
				}
			} else {
				sendUsage();
			}
		} else {
			sendUsage();
		}
	}
	
	private void sendUsage() {
		Utils.sendMessage("§8§m-[-----------§r §9§lAutoAMS§r §8§m-----------]-");
		Utils.sendMessage(" ");
		Utils.sendMessage("  §8\u25b6 §7.autoams §7start");
		Utils.sendMessage("  §8\u25b6 §7.autoams §7stop ");
		Utils.sendMessage("  §8\u25b6 §7.autoams §7owner §7[name]");
		Utils.sendMessage("  §8\u25b6 §7.autoams §7random §7[seconds]");
		Utils.sendMessage("  §8\u25b6 §7.autoams §7delay §7[seconds]");
		Utils.sendMessage("  §8\u25b6 §7.autoams §7retrydelay §7[seconds]");
		Utils.sendMessage(" ");
		Utils.sendMessage("§8§m-[-----------§r §9§lAutoAMS§r §8§m-----------]-");
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