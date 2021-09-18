package de.rizon.autoams;

import java.io.File;

import de.rizon.autoams.commands.AutoAMSCommand;
import de.rizon.autoams.utils.AutoAMS;
import de.rizon.autoams.utils.Config;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

@Mod(modid = "autoams", name = "AutoAMS", version = "69", acceptedMinecraftVersions = "[1.8.9]")
public class Main {

	private static Main instance;
	private static Config config;
	private static AutoAMS autoams;
	public static String prefix = "\u00A79\u00A7lAutoAMS \u00A78\u25b6 \u00A77";
	
	@EventHandler
	public void onInit(FMLInitializationEvent e) {
		instance = this;
		config = new Config(new File("config/AutoAMS.cfg"));
		autoams = new AutoAMS();
		loadCommands(ClientCommandHandler.instance);
	}
	
	private void loadCommands(ClientCommandHandler cch) {
		cch.registerCommand(new AutoAMSCommand());
	}
	
	public static Main getInstance() {
		return instance;
	}
	
	public static Config getConfig() {
		return config;
	}
	public static AutoAMS getAutoAMS() {
		return autoams;
	}
}