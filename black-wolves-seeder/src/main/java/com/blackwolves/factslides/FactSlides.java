package com.blackwolves.factslides;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

public class FactSlides {

	public static void main(String[] args) {
		int count = 5155;
		for (int i = 5444; i <= 5485; i++) {
			try {
				String imageUrl = "http://www.factslides.com/imgs/ishots/" + i + ".png";
				BufferedImage bufferedImage = ImageIO.read(new URL(imageUrl));
				ImageIO.write(bufferedImage, "png", new File("/Users/gastondapice/Dropbox/Black Wolves/sites/TheCoolInfo/images/" + count + ".png"));
				++count;
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
