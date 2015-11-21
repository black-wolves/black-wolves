package com.blackwolves.factslides;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

public class FactSlides {

	public static void main(String[] args) {
		for (int i = 5110; i <= 5444; i++) {
			try {
				String imageUrl = "http://www.factslides.com/imgs/ishots/" + i + ".png";
				BufferedImage bufferedImage = ImageIO.read(new URL(imageUrl));
				ImageIO.write(bufferedImage, "png", new File("/Users/gastondapice/Dropbox/Black Wolves/sites/TheCoolInfo2/images/" + i + ".png"));
				System.out.println("Image: " + i + " downloaded");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				continue;
			} catch (IOException e) {
				e.printStackTrace();
				continue;
			}
		}

	}
}