import java.util.Arrays;

public class SourceDataFormat {
	public String movieID, idSubtitleFile, subLanguageID, idSubtitle,
			subActualCD, subSumCD, subFormat, movieName, movieYear,
			movieImdbID, userRank, subDownloadsCnt;

	public static String titleRow;

	static {
		titleRow = "MovieID,IDSubtitleFile,SubLanguageID,IDSubtitle,SubActualCD,SubSumCD,SubFormat,"
				+ "MovieName,MovieYear,MovieImdbID,UserRank,SubDownloadsCnt"
						.split(",");
	}

	public SourceDataFormat(String data) {
		String[] fields = CSVParser.parse(data);
		System.out.println(Arrays.toString(fields));

		movieID = fields[0];
		idSubtitleFile = fields[1];
		subLanguageID = fields[2];
		idSubtitle = fields[3];
		subActualCD = fields[4];
		subSumCD = fields[5];
		subFormat = fields[6];
		movieName = fields[7];
		movieYear = fields[8];
		movieImdbID = fields[9];
		userRank = fields[10];
		subDownloadsCnt = fields[11];
	}

	@Override
	public String toString() {
		return String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s", movieID,
				idSubtitleFile, subLanguageID, idSubtitle, subActualCD,
				subSumCD, subFormat, movieName, movieYear, movieImdbID,
				userRank, subDownloadsCnt);
	}
}
