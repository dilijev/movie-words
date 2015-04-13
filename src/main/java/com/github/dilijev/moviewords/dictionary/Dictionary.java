/*
 * Author: Doug Ilijev
 * Copyright (c) 2015: Doug Ilijev
 */

package com.github.dilijev.moviewords.dictionary;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import com.github.dilijev.moviewords.utils.BufferedReaderHelper;

public class Dictionary {
	private BufferedReaderHelper reader;
	private ReaderState state;

	private HashMap<Integer, String> categoryMap;
	private Trie<ArrayList<Integer>> wordMap;

	public Dictionary(String dictFile) throws IOException {
		InputStream s = new FileInputStream(dictFile);
		reader = new BufferedReaderHelper(new BufferedReader(new InputStreamReader(s)));
		state = ReaderState.START;
		categoryMap = new HashMap<>();
		wordMap = new Trie<>();

		readAllDictionary();

		System.out.println(categoryMap);
		System.out.println(wordMap);
		
		ArrayList<Integer> list = wordMap.get("abandonment");
		System.out.println(list);
	}

	private void readAllDictionary() throws IOException {
		String line = null;
		while (true) {
			line = reader.nextNonEmptyLine();
			if (line == null) {
				break;
			}

			System.out.println(line);

			processLine(line);
		}
	}

	private void processLine(String line) {
		switch (state) {
		case START:
			processStartLine(line);
			break;
		case CATEGORIES:
			processCategoryLine(line);
			break;
		case WORDS:
			processWordLine(line);
			break;
		case INVALID:
		default:
			System.err.println("Invalid dictionary file");
			System.exit(1);
			break;
		}
	}

	private void processStartLine(String line) {
		if (line.startsWith("%")) {
			state = state.CATEGORIES;
		} else {
			state = state.INVALID;
		}
	}

	private void processCategoryLine(String line) {
		// if no more categories, transition to the next region of the file
		if (line.startsWith("%")) {
			state = state.WORDS;
			return;
		}

		String[] a = line.split("\\s+");

		if (a.length != 2) {
			System.err.println("Problem with CATEGORY line in input file:");
			System.err.println(line);
			System.exit(1);
		}

		categoryMap.put(Integer.parseInt(a[0]), a[1]);
	}

	private void processWordLine(String line) {
		String[] a = line.split("\\s+");

		System.out.println(Arrays.toString(a));

		// word lines can contain a word and any number of number tokens
		if (a.length < 2) {
			System.err.println("Problem with WORD line in input file:");
			System.err.println(line);
			System.exit(1);
		}

		// construct a reasonable regex for matching the pattern from the
		// dictionary
		String word = a[0];
		boolean isPrefix = false;

		int index = word.indexOf('*');
		if (index != -1 && index != word.length() - 1) {
			System.err.println("Problem with WORD line in input file: word contains * in location other than the end");
			System.err.println(line);
			System.exit(1);
		} else if (index == word.length() - 1) {
			isPrefix = true;
			word = word.substring(0, word.length() - 1);
		}

		// word = "^" + a[0] + "$";
		// word = word.replaceAll("\\*", ".*");

		ArrayList<Integer> numlist = new ArrayList<>();

		for (int i = 1; i < a.length; i++) {
			numlist.add(Integer.parseInt(a[i]));
		}

		wordMap.insert(word, isPrefix, numlist);
	}

	private enum ReaderState {
		START, CATEGORIES, WORDS, INVALID
	}
}
