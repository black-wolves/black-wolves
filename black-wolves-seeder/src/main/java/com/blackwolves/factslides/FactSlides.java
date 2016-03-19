package com.blackwolves.factslides;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import com.blackwolves.seeder.util.Constant;

public class FactSlides {

	public static void main(String[] args) {
		int count = 5407;
		for (int i = 5683; i <= 5817; i++) {
			try {
				String imageUrl = "http://www.factslides.com/imgs/ishots/" + i + ".png";
				BufferedImage bufferedImage = ImageIO.read(new URL(imageUrl));
				ImageIO.write(bufferedImage, "png", new File(Constant.FactSlides.TCI_DOWNLOAD + count + ".png"));
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
