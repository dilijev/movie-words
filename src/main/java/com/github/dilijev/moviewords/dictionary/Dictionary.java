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

import com.github.dilijev.moviewords.utils.BufferedReaderHelper;

public class Dictionary {
	private BufferedReaderHelper reader;
	private ReaderState state;
	
	private TreeMap<Integer, String> categoryMap;

	public Dictionary(String dictFile) throws IOException {
		InputStream s = new FileInputStream(dictFile);
		reader = new BufferedReaderHelper(new BufferedReader(new InputStreamReader(s)));
		state = ReaderState.START;

		readAllDictionary();
	}

	private void readAllDictionary() throws IOException {
		String line = null;
		do {
			line = reader.nextNonEmptyLine();
			processLine(line);
		} while (line != null);
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
		}
	}

	private void processStartLine(String line) {
		if (line.startsWith("%")) {
			state = state.CATEGORIES;
		}
	}

	private void processCategoryLine(String line) {
		// TODO Auto-generated method stub

	}

	private void processWordLine(String line) {
		// TODO Auto-generated method stub

	}

	private enum ReaderState {
		START, CATEGORIES, WORDS
	}
}
