import java.io.File;

public class ProcessText {
	public static void main(String[] args) {
		if (args.length < 1) {
			System.out.println("Usage: java ProcessText file1 [file2 ...]");
		} else {
			for (String path : args) {
				File file = new File(path);
				System.out.println(new TextStatistics(file));
			}
		}
	}
}