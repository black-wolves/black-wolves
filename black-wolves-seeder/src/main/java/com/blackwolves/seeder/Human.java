/**
 * 
 */
package com.blackwolves.seeder;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.blackwolves.seeder.util.Constant;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

/**
 * @author danigrane
 *
 */
public abstract class Human {
	
	public static final Logger logger = LoggerFactory.getLogger(Human.class);

	public abstract void type(WebElement input, String string);
	
	/**
	 * 
	 * @param mySeed
	 * @return
	 */
	public String generateRandomTo(String mySeed) {
		String[] s = mySeed.split(",");
		String myMail = s[0];
		List<String[]> seeds = YahooRunnable.generateSeedsList("seeds.csv");
		String to = Constant.EMPTY_STRING;
		do {
			String[] seed = seeds.get(YahooRunnable.randInt(0, seeds.size()-1));
			to = seed[0];
		} while (myMail.equals(to));
		return to;
	}
	
	/**
	 * 
	 * @return
	 */
	public String generateRandomSubject() {
		logger.info("Generating a random subject");
		String content = generateRandomContent();
		String subject = Constant.EMPTY_STRING;
		int numberOfWords = Integer.MAX_VALUE;
		int min = content.length()<=Constant.MIN_SUBJECT_WORDS?content.length():Constant.MIN_SUBJECT_WORDS;
		int max = content.length()<=Constant.MAX_SUBJECT_WORDS?content.length():Constant.MAX_SUBJECT_WORDS;
		while (numberOfWords >= max || numberOfWords <= min) {
			subject = content.substring(YahooRunnable.randInt(1, content.length()));
			numberOfWords = subject.length();
		}
		return subject;
	}

	/**
	 * 
	 * @return
	 */
	public String generateRandomBody() {
		logger.info("Generating a random body");
		String content = generateRandomContent();
		String body = Constant.EMPTY_STRING;
		int numberOfWords = Integer.MAX_VALUE;
		int min = content.length()<=Constant.MIN_BODY_WORDS?content.length():Constant.MIN_BODY_WORDS;
		int max = content.length()<=Constant.MAX_BODY_WORDS?content.length():Constant.MAX_BODY_WORDS;
		while (numberOfWords >= max || numberOfWords <= min) {
			body = content.substring(YahooRunnable.randInt(1, content.length()));
			numberOfWords = body.length();
		}
		return body;
	}
	
	/**
	 * 
	 * @return
	 */
	private String generateRandomContent() {
		logger.info("Getting content from the PDF");
		String page = Constant.EMPTY_STRING;
		PdfReader reader = null;
		try {
			reader = new PdfReader(Constant.PDF_FILE);
			int randomPage = YahooRunnable.randInt(3, reader.getNumberOfPages());
			page = PdfTextExtractor.getTextFromPage(reader, randomPage);
			reader.close();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}finally{
			if(reader!=null){
				reader.close();
			}
		}
		return page.replaceAll("(\\r|\\n|\\r\\n)+", "");
	}
}
