/*
 * Author: Doug Ilijev
 * Copyright (c) 2015: Doug Ilijev
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) throws FileNotFoundException {
		Scanner sc = new Scanner(new File("E:\\dev\\movie-words\\source.csv"));
		sc.nextLine(); // throw away title line

		ArrayList<DestDataFormat> list = new ArrayList<>();

		int num = 0;
		while (sc.hasNextLine()) {
			String line = sc.nextLine();
			DestDataFormat ddf = new DestDataFormat(new SourceDataFormat(line));

//			System.out.println(ddf);
			list.add(ddf);

			num++;
			if (num >= 100)
				break;

//			System.out.println(num);
		}

		System.out.println(num + " rows read from file");

		Collections.sort(list, SourceDataTitleOrder.INSTANCE);

		for (DestDataFormat f : list) {
			System.out.println(f.movieName);
		}
	}
}
