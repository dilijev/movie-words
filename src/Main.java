import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


public class Main {
	public static void main(String[] args) throws FileNotFoundException {
		Scanner sc = new Scanner(new File("E:\\dev\\movie-words\\source.csv"));
		sc.nextLine(); // throw away title line
		
		ArrayList<SourceDataFormat> list = new ArrayList<>();
		
		int num = 0;
		while (sc.hasNextLine()) {
			String line = sc.nextLine();
			SourceDataFormat sdf = new SourceDataFormat(line);
			
//			System.out.println(sdf);
//			System.out.println(sdf.movieName);
			
			list.add(sdf);
			
			num++;
//			if (num >= 100) break;
			
			System.out.println(num);
		}
		
		System.out.println(num + " rows read from file");
	}
}
