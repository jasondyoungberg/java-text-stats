import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;

public class TextStatistics implements TextStatisticsInterface {
	private static final String DELIMITERS = "[\\W\\d_]+";

	private String text;
	private int chars;
	private int words;
	private int lines;

	private int minWordLength;
	private int maxWordLength;
	private double avgWordLength;

	private int wordLengthFrequency[];
	private int letterFrequency[];


	public TextStatistics (File file) {
		try {
			Scanner reader = new Scanner(file);

			wordLengthFrequency = new int[24];
			letterFrequency = new int[26];

			int totalWordLength = 0;
			minWordLength = 23;
			maxWordLength = 1;

			while (reader.hasNextLine()) {
				String line = reader.nextLine();

				chars += line.length() + 1;
				lines++;

				for (String word : line.split(DELIMITERS)){
					if (word.length() > 0){
						totalWordLength += word.length();
						words += 1;

						wordLengthFrequency[word.length()]++;

						for (char ch: word.toLowerCase().toCharArray()) {
							if ('a' <= ch && ch <= 'z'){
								letterFrequency[ch - 'a']++;
							}
						}

						if (word.length() < minWordLength) minWordLength = word.length();
						if (word.length() > maxWordLength) maxWordLength = word.length();
					}
				}
			}

			avgWordLength = (double) totalWordLength / words;

			reader.close();
		} catch (FileNotFoundException e) {
			System.out.println("ERROR: File does not exist");
		}
	}

	public int getCharCount() {return chars;}
	public int getWordCount() {return words;}
	public int getLineCount() {return lines;}
	public int getLetterCount() {return chars;}
	public int getWordLengthCount() {return chars;}
	public int getAverageWordLength() {return chars;}

	public String toString(){
		String wordLengthFrequencyStr = "";
		String letterFrequencyStr = "";

		for (int x : wordLengthFrequency) wordLengthFrequencyStr += x + " ";

		for (int x : letterFrequency) letterFrequencyStr += x + " ";

		return  "chars:                  "+chars+
				"\nwords:                  "+words+
				"\nlines:                  "+lines+
				"\nminWordLength:          "+minWordLength+
				"\nmaxWordLength:          "+maxWordLength+
				"\navgWordLength:          "+avgWordLength+
				"\nwordLengthFrequencyStr: "+wordLengthFrequencyStr+
				"\nletterFrequencyStr:     "+letterFrequencyStr;
	}
}
