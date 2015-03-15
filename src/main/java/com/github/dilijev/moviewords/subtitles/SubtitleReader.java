package com.github.dilijev.moviewords.subtitles;

import java.io.BufferedReader;
import java.io.IOException;

public class SubtitleReader {
	private BufferedReader reader;

	long subtitleNumber = 0;

	public SubtitleReader(BufferedReader reader) {
		this.reader = reader;
		this.subtitleNumber = 0;
	}

	public void readAllCues(Histogram h) throws IOException {
		while (readCue(h) == true)
			; // loop until done
	}

	public boolean readCue(Histogram h) throws IOException {
		String cueNumberLine = "";
		
		// go until we find a good line
		boolean good = false;
		while (!good) {
			// read until we find a non-blank line
			while (cueNumberLine.trim().isEmpty()) {
				cueNumberLine = reader.readLine();
				if (cueNumberLine == null) {
					return false;
				}
			}
			
			if (cueNumberLine.matches("^.*-->.*$")) {
				// read until next blank line
				while (!cueNumberLine.trim().isEmpty()) {
					cueNumberLine = reader.readLine();
					if (cueNumberLine == null) {
						return false;
					}
				}
			} else {
				good = true;
			}
		}

		int cueNumber = Integer.parseInt(cueNumberLine.trim());

		String timeStamp = reader.readLine();
		h.addTimeStamp(cueNumber, timeStamp); // store timestamp for later

		StringBuilder cue = new StringBuilder();
		String cueLine = "";

		do {
			cueLine = reader.readLine();
			if (cueLine == null) {
				break;
			}

			cue.append(cueLine.trim());
			cue.append(" ");
		} while (!cueLine.trim().isEmpty());

		String cueText = cleanCue(cue.toString());

		String[] words = cueText.split("\\s+");

		for (String w : words) {
			h.insertWordCountAtTimestampIndex(w, cueNumber);
		}

		return true;
	}

	private String cleanCue(String cue) {
		return cue.toLowerCase()
		// remove html formatting
				.replaceAll("\\<.*\\>", "")
				// remove stage motion between [ and ]
				.replaceAll("\\[.*\\]", "")
				// normalize quotes
				.replaceAll("[‘’]", "'")
				// normalize double-quotes
				.replaceAll("“”", "\"")
				// remove word-leading quotes
				.replaceAll("(^|\\s)['\"]+(\\w*)", " $2")
				// remove word-trailing quotes
				.replaceAll("(\\w*)['\"]+(\\s|$)", "$1 ")
				// .replaceAll("^?\\s+[\\W&&[^À-ÖØ-öø-ÿ]]+$?"," ")
				.replaceAll("^?\\s+[\\W&&[^'À-ÖØ-öø-ÿ]]+$?", " ")
				// .replaceAll("^?[\\W&&[^À-ÖØ-öø-ÿ]]+\\s+$?"," ")
				.replaceAll("^?[\\W&&[^'À-ÖØ-öø-ÿ]]+\\s+$?", " ")
				// remove brackets
				.replaceAll("[\\[\\]]", "")
				// .replaceAll("^?[\\W&&[^À-ÖØ-öø-ÿ]]+$?"," ")
				.replaceAll("^?[\\W&&[^'À-ÖØ-öø-ÿ]]+$?", " ")
				// remove all unnecessary whitespace for string split
				.trim();
	}
}
