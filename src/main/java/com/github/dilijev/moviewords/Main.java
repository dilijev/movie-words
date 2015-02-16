/*
 * Author: Doug Ilijev
 * Copyright (c) 2015: Doug Ilijev
 */

package com.github.dilijev.moviewords;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import com.github.dilijev.moviewords.imdb.ImdbScraper;
import com.github.dilijev.moviewords.subtitles.Histogram;
import com.github.dilijev.moviewords.subtitles.SubtitleReader;
import com.github.dilijev.moviewords.dictionary.Dictionary;

public class Main {
	public static void testCSV() throws FileNotFoundException {
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
	
	public static void testImdbScraper() {
		System.out.println(ImdbScraper.byImdbId(137523)); // Fight Club
		System.out.println(ImdbScraper.byImdbId(120737)); // LOTR: Fellowship
		System.out.println(ImdbScraper.byImdbId(2702698)); // Sherlock (TV) -- TODO not working
	}
	
	public static void main(String[] args) throws IOException {
		if (args.length == 0) {
			usage();
			return;
		}
		
		String filename = args[0];
		
		Histogram h = new Histogram();
		InputStream s = new FileInputStream(filename);
		BufferedReader br = new BufferedReader(new InputStreamReader(s));
		
		SubtitleReader subReader = new SubtitleReader(br);
		subReader.readAllCues(h);
		
		FileWriter cues = new FileWriter(filename + ".cues.csv");
		cues.write(h.makeTimestampTable());
		cues.close();
		
		FileWriter words = new FileWriter(filename + ".histo.csv");
		words.write(h.makeHistogramTable(false));
		words.close();
		
		FileWriter wordsCues = new FileWriter(filename + ".histo.cuelist.csv");
		wordsCues.write(h.makeHistogramTable(true));
		wordsCues.close();
		
		FileWriter all = new FileWriter(filename + ".all.csv");
		all.write(h.toString());
		all.close();
	}
	
	public static void usage() {
		System.err.println("Wrong number of arguments.");
		System.exit(1);
	}
}
