package de.rizon.autoams.utils;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import de.rizon.autoams.Main;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class AutoAMS {
	
	private ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
	private ScheduledFuture<?> task1;
	private ScheduledFuture<?> task2;
	private ScheduledFuture<?> task3;
	private ScheduledFuture<?> task4;
	private ScheduledFuture<?> task5;
	private Minecraft mc = Minecraft.getMinecraft();
	private boolean state;
	
	public boolean start() {
		state = true;
		if (onServer()) {
			Config config = Main.getConfig();
			mc.thePlayer.closeScreen();
			mc.displayGuiScreen((GuiScreen) null);
			mc.thePlayer.sendChatMessage("/ams " + config.getOwner());
			task1 = executor.schedule(new Runnable() {
				public void run() {
					if (!onServer()) {
						stop();
						return;
					}
					if (!state) return;
					mc.playerController.windowClick(mc.thePlayer.openContainer.windowId, 22, 0, 0, mc.thePlayer);
					task2 = executor.schedule(new Runnable() {
						public void run() {
							if (!onServer()) {
								stop();
								return;
							}
							if (!state) return;
							ContainerChest con = (ContainerChest) mc.thePlayer.openContainer;
							IInventory inv = con.getLowerChestInventory();
							if (scanRows(inv)) {
								Utils.sendMessage(Main.prefix + "Found §7valid §7solution...");
								restart(config.getDelay() + Utils.getRandom(config.getRandom()));
							} else {
								Utils.sendMessage(Main.prefix + "No §7solution §7found...");
								mc.thePlayer.closeScreen();
								mc.displayGuiScreen((GuiScreen) null);
								restart(config.getRetryDelay() + (Utils.getRandom(config.getRandom() / 2)));
							}
						}
					}, 2, TimeUnit.SECONDS);
				}					
			}, 2, TimeUnit.SECONDS);
		} else {
			Utils.sendMessage(Main.prefix + "This §7only §7works §7on §7MineChaos.");
			state = false;
		}
		return state;
	}
	
	private boolean onServer() {
		if (mc.theWorld != null && !mc.isSingleplayer() && !mc.isIntegratedServerRunning()) {
			if (mc.getCurrentServerData().serverIP.toLowerCase().contains("minechaos")) {
				return true;
			}
		}
		return false;
	}
	
	private void restart(int seconds) {
		task3 = executor.schedule(new Runnable() {
			public void run() {
				if (!onServer()) {
					stop();
					return;
				}
				if (!state) return;
				Utils.sendMessage(Main.prefix + "Restarting...");
				start();
			}			
		}, seconds, TimeUnit.SECONDS);
	}
	
	private enum Rows {
		
		A(0, 9, 10),
		B(9, 18, -8),
		C(18, 27, -8);
		
		int start;
		int end;
		int middle;
		
		private Rows(int s, int e, int m) {
			start = s;
			end = e;
			middle = m;
		}
		
		public int getStart() {
			return start;
		}
		
		public int getEnd() {
			return end;
		}
		
		public int getMiddle() {
			return middle;
		}
	}
	
	private boolean scanRows(IInventory inv) {
		for (Rows r : Rows.values()) {
			int anzahl = getAmountInRow(inv, r);
			if (anzahl == 3) {
				solveCaptcha(inv, getFirstInRow(inv, r), (r.equals(Rows.B) ? ((getAmountInRow(inv, Rows.C) == 2) ? 10 : r.getMiddle()) : r.getMiddle()), getKeySlot(inv));
				return true;
			}
		}
		return false;
	}
	
	private void solveCaptcha(IInventory inv, int first, int middle, int key) {
		task4 = executor.schedule(new Runnable() {
			public void run() {
				if (!onServer()) {
					stop();
					return;
				}
				if (!state) return;
				mc.playerController.windowClick(mc.thePlayer.openContainer.windowId, key, 0, 0, mc.thePlayer);
				task5 = executor.schedule(new Runnable() {
					public void run() {
						if (!state) return;
						mc.playerController.windowClick(mc.thePlayer.openContainer.windowId, (first + middle), 0, 0, mc.thePlayer);
					}									
				}, 500, TimeUnit.MILLISECONDS);
			}									
		}, 500, TimeUnit.MILLISECONDS);
	}	
	
	private int getKeySlot(IInventory inv) {
		for (int i = 0; i < inv.getSizeInventory(); i++) {
			ItemStack item = inv.getStackInSlot(i);
			if (item != null && item.hasDisplayName() && item.getDisplayName().contains("Guthaben")) {
				return i;
			}
		}
		return 0;
	}
	
	private int getAmountInRow(IInventory inv, Rows row) {
		int amount = 0;
		for (int i = row.getStart(); i < row.getEnd(); i++) {
			ItemStack item = inv.getStackInSlot(i);
			if (item != null && item.hasDisplayName() && !item.getDisplayName().contains("Guthaben")) {
				amount++;
			}
		}
		return amount;
	}
	
	private int getFirstInRow(IInventory inv, Rows row) {
		for (int i = row.getStart(); i < row.getEnd(); i++) {
			ItemStack item = inv.getStackInSlot(i);
			if (item != null && item.hasDisplayName() && !item.getDisplayName().contains("Guthaben")) {
				return i;
			}
		}
		return 0;
	}
	
	public void stop() {
		mc.displayGuiScreen((GuiScreen) null);
		state = false;
		if (task1 != null) {
			task1.cancel(true);
			task1 = null;
		}
		if (task2 != null) {
			task2.cancel(true);
			task2 = null;
		}
		if (task3 != null) {
			task3.cancel(true);
			task3 = null;
		}
		if (task4 != null) {
			task4.cancel(true);
			task4 = null;
		}
		if (task5 != null) {
			task5.cancel(true);
			task5 = null;
		}
	}
	
	public boolean getState() {
		return state;
	}
}