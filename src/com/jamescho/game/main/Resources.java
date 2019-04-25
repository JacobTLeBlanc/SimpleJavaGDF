package com.jamescho.game.main;

// Imports
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

@SuppressWarnings("deprecation")

public class Resources {
	
	// Initialize images
	public static BufferedImage welcome, iconimage;
	
	public static void load() {
		// Load images
		welcome = loadImage("welcome.png");
		iconimage = loadImage("iconimage.png");
	}
	
	
	private static AudioClip loadSound(String filename) {
		// Get audio file
		URL fileURL = Resources.class.getResource("/resources/" + filename);
		
		// Return as audio
		return Applet.newAudioClip(fileURL);
	}
	
	private static BufferedImage loadImage(String filename) {
		// Create new usable image object
		BufferedImage img = null;
		
		// try to get image from resources
		try {
			img = ImageIO.read(Resources.class.getResourceAsStream("/resources/" + filename));
		} catch (IOException e) {
			System.out.println("Error while reading: " + filename);
			e.printStackTrace();
		}
		return img;
	}
}
