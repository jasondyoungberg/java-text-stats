import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;

public class TextStatistics implements TextStatisticsInterface {
	private static final String DELIMITERS = "[\\W\\d_]+";

	private String path;
	private int chars;
	private int words;
	private int lines;

	private int minWordLength;
	private int maxWordLength;
	private double avgWordLength;

	private int wordLengthFrequency[];
	private int letterFrequency[];

	private boolean error;


	public TextStatistics (File file) {
		path = file.getPath();
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
			error = false;
		} catch (FileNotFoundException e) {
			error = true;
		}
	}

	public int getCharCount() {return chars;}
	public int getWordCount() {return words;}
	public int getLineCount() {return lines;}
	public int[] getLetterCount() {return letterFrequency;}
	public int[] getWordLengthCount() {return wordLengthFrequency;}
	public double getAverageWordLength() {return avgWordLength;}

	public String toString(){
		if (error) return "Invalid file path: " + path;

		String wordLengthFrequencyStr = "";
		String letterFrequencyStr = "";

		String temp;

		//for (int x : wordLengthFrequency) wordLengthFrequencyStr += x + " ";
		//for (int x : letterFrequency) letterFrequencyStr += x + " ";

		for (int i = 0; i < 13; i++) {
			temp = " " + (char)('a' + i) + " = " + letterFrequency[i];
			while (temp.length() < 16) temp += " ";

			letterFrequencyStr += temp;

			temp = " " + (char)('n' + i) + " = " + letterFrequency[i + 13];
			while (temp.length() < 16) temp += " ";
			letterFrequencyStr += temp + "\n";
		}

		for (int i = minWordLength; i <= maxWordLength; i++) {
			temp = i + "";
			while (temp.length() < 7) temp = " " + temp;

			wordLengthFrequencyStr += temp;

			temp = wordLengthFrequency[i] + "";
			while (temp.length() < 11) temp = " " + temp;

			wordLengthFrequencyStr += temp + "\n";
		}

		return "Statistics for " + path + "\n" +
			"==========================================================\n" +
			lines + " lines\n" +
			words + " words\n" +
			chars + " characters\n" +
			"------------------------------\n" +
			letterFrequencyStr +
			"------------------------------\n" +
			" length  frequency\n" +
			" ------  ---------\n" +
			wordLengthFrequencyStr +
			"\n" +
			"Average word length = " + String.format("%.2f", avgWordLength) + "\n" +
			"==========================================================\n";
	}
}
