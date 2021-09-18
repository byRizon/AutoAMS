package de.rizon.autoams.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import net.minecraft.client.Minecraft;

public class Config {

	private File file;
	private String amsowner;	
	private int random;
	private int delay;
	private int retrydelay;
	
	public Config(File f) {
		file = f;
		amsowner = Minecraft.getMinecraft().getSession().getUsername();
		random = 45;
		delay = 60;
		retrydelay = 5;
		load();
	}
	
	public void load() {
        try {
            if (!file.exists()) return;
            final BufferedReader reader = new BufferedReader(new FileReader(file));
            int i = 0;
            String line;
            while ((line = reader.readLine()) != null) {
                switch (++i) {
                    case 1: {
                    	amsowner = line.trim();
                        continue;
                    }
                    case 2: {
                    	random = Integer.parseInt(line);
                        continue;
                    }
                    case 3: {
                    	delay = Integer.parseInt(line);
                        continue;
                    }
                    case 4: {
                    	retrydelay = Integer.parseInt(line);
                        continue;
                    }
                    default: {
                        continue;
                    }
                }
            }
            reader.close();
        } catch (Throwable e) {}
	}
	
	public void save() {
        try {
            if (!file.exists()) {
            	file.getParentFile().mkdirs();
            	file.createNewFile();
            }
            FileWriter w = new FileWriter(file, false);
            w.write(amsowner + "\n" + random + "\n" + delay + "\n" + retrydelay);
            w.close();
        }
        catch (Throwable e) {}
	}

	public String getOwner() {
		return amsowner;
	}

	public void setOwner(String newowner) {
		amsowner = newowner;
	}
	
	public int getRandom() {
		return random;
	}

	public void setRandom(int r) {
		random = r;
	}

	public int getDelay() {
		return delay;
	}

	public void setDelay(int d) {
		delay = d;
	}

	public int getRetryDelay() {
		return retrydelay;
	}

	public void setRetryDelay(int r) {
		retrydelay = r;
	}	
}