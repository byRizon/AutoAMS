package de.rizon.autoams.utils;

import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

public class Utils {

	public static void sendMessage(String msg) {
		Minecraft mc = Minecraft.getMinecraft();
		if (mc.theWorld != null) mc.thePlayer.addChatMessage(new ChatComponentText(msg));
	}
	
	public static int getRandom(int maxValue) {
		Random random = new Random();
		int i = random.nextInt(maxValue);
		return i;
	}
}