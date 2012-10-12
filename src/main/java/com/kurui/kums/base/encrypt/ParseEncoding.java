package com.kurui.kums.base.encrypt;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

public class ParseEncoding extends Encode {

	int GB2312format[][];
	int GBKformat[][];
	int Big5format[][];
	int EUC_KRformat[][];
	int JPformat[][];

	public ParseEncoding() {
		GB2312format = new int[94][94];
		GBKformat = new int[126][191];
		Big5format = new int[94][158];
		EUC_KRformat = new int[94][94];
		JPformat = new int[94][94];
	}

	public String getEncoding(String path) {
		return check(getEncodeValue(path));
	}

	public String getEncoding(InputStream in) {
		return check(getEncodeValue(in));
	}

	public String getEncoding(byte buffer[]) {
		return check(getEncodeValue(buffer));
	}

	public String getEncoding(URL url) {
		return check(getEncodeValue(url));
	}

	private String check(int result) {
		if (result == -1)
			return nicename[UNKNOWN];
		else
			return nicename[result];
	}

	private int getEncodeValue(String path) {
		int express = UNKNOWN;
		if (path.startsWith("http://"))
			try {
				express = getEncodeValue(new URL(path));
			} catch (MalformedURLException e) {
				express = -1;
			}
		else
			express = getEncodeValue(new File(path));
		return express;
	}

	public int getEncodeValue(InputStream in) {
		byte rawtext[] = new byte[8192];
		int bytesread = 0;
		int byteoffset = 0;
		int express = UNKNOWN;
		InputStream stream = in;
		try {
			while ((bytesread = stream.read(rawtext, byteoffset, rawtext.length
					- byteoffset)) > 0)
				byteoffset += bytesread;
			stream.close();
			express = getEncodeValue(rawtext);
		} catch (Exception e) {
			express = -1;
		}
		return express;
	}

	public int getEncodeValue(URL url) {
		InputStream stream;
		try {
			stream = url.openStream();
		} catch (IOException e) {
			stream = null;
		}
		return getEncodeValue(stream);
	}

	public int getEncodeValue(File file) {
		byte buffer[];
		try {
			buffer = read(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			buffer = (byte[]) null;
		}
		return getEncodeValue(buffer);
	}

	private final byte[] read(InputStream inputStream) {
		byte arrayByte[] = (byte[]) null;
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		byte bytes[] = new byte[8192];
		try {
			bytes = new byte[inputStream.available()];
			int read;
			while ((read = inputStream.read(bytes)) >= 0)
				byteArrayOutputStream.write(bytes, 0, read);
			arrayByte = byteArrayOutputStream.toByteArray();
		} catch (IOException e) {
			return null;
		}
		return arrayByte;
	}

	public int getEncodeValue(byte content[]) {
		if (content == null)
			return -1;
		int maxscore = 0;
		int encoding = UNKNOWN;
		int scores[] = new int[TOTALT];
		scores[GB2312] = gb2312probability(content);
		scores[GBK] = gbkprobability(content);
		scores[BIG5] = big5probability(content);
		scores[UTF8] = utf8probability(content);
		scores[UNICODE] = utf16probability(content);
		scores[EUC_KR] = euc_krprobability(content);
		scores[ASCII] = asciiprobability(content);
		scores[SJIS] = sjisprobability(content);
		scores[EUC_JP] = euc_jpprobability(content);
		scores[UNKNOWN] = 0;
		for (int index = 0; index < TOTALT; index++)
			if (scores[index] > maxscore) {
				encoding = index;
				maxscore = scores[index];
			}

		if (maxscore <= 50)
			encoding = UNKNOWN;
		return encoding;
	}

	private int gb2312probability(byte content[]) {
		int rawtextlen = 0;
		int dbchars = 1;
		int gbchars = 1;
		long gbformat = 0L;
		long totalformat = 1L;
		float rangeval = 0.0F;
		float formatval = 0.0F;
		rawtextlen = content.length;
		for (int i = 0; i < rawtextlen - 1; i++)
			if (content[i] < 0) {
				dbchars++;
				if (-95 <= content[i] && content[i] <= -9
						&& -95 <= content[i + 1] && content[i + 1] <= -2) {
					gbchars++;
					totalformat += 500L;
					int row = (content[i] + 256) - 161;
					int column = (content[i + 1] + 256) - 161;
					if (GB2312format[row][column] != 0)
						gbformat += GB2312format[row][column];
					else if (15 <= row && row < 55)
						gbformat += 200L;
				}
				i++;
			}

		rangeval = 50F * ((float) gbchars / (float) dbchars);
		formatval = 50F * ((float) gbformat / (float) totalformat);
		return (int) (rangeval + formatval);
	}

	private int gbkprobability(byte content[]) {
		int rawtextlen = 0;
		int dbchars = 1;
		int gbchars = 1;
		long gbformat = 0L;
		long totalformat = 1L;
		float rangeval = 0.0F;
		float formatval = 0.0F;
		rawtextlen = content.length;
		for (int i = 0; i < rawtextlen - 1; i++)
			if (content[i] < 0) {
				dbchars++;
				if (-95 <= content[i] && content[i] <= -9
						&& -95 <= content[i + 1] && content[i + 1] <= -2) {
					gbchars++;
					totalformat += 500L;
					int row = (content[i] + 256) - 161;
					int column = (content[i + 1] + 256) - 161;
					if (GB2312format[row][column] != 0)
						gbformat += GB2312format[row][column];
					else if (15 <= row && row < 55)
						gbformat += 200L;
				} else if (-127 <= content[i]
						&& content[i] <= -2
						&& (-128 <= content[i + 1] && content[i + 1] <= -2 || 64 <= content[i + 1]
								&& content[i + 1] <= 126)) {
					gbchars++;
					totalformat += 500L;
					int row = (content[i] + 256) - 129;
					int column;
					if (64 <= content[i + 1] && content[i + 1] <= 126)
						column = content[i + 1] - 64;
					else
						column = (content[i + 1] + 256) - 64;
					if (GBKformat[row][column] != 0)
						gbformat += GBKformat[row][column];
				}
				i++;
			}

		rangeval = 50F * ((float) gbchars / (float) dbchars);
		formatval = 50F * ((float) gbformat / (float) totalformat);
		return (int) (rangeval + formatval) - 1;
	}

	private int big5probability(byte content[]) {
		int rawtextlen = 0;
		int dbchars = 1;
		int bfchars = 1;
		float rangeval = 0.0F;
		float formatval = 0.0F;
		long bfformat = 0L;
		long totalformat = 1L;
		rawtextlen = content.length;
		for (int i = 0; i < rawtextlen - 1; i++)
			if (content[i] < 0) {
				dbchars++;
				if (-95 <= content[i]
						&& content[i] <= -7
						&& (64 <= content[i + 1] && content[i + 1] <= 126 || -95 <= content[i + 1]
								&& content[i + 1] <= -2)) {
					bfchars++;
					totalformat += 500L;
					int row = (content[i] + 256) - 161;
					int column;
					if (64 <= content[i + 1] && content[i + 1] <= 126)
						column = content[i + 1] - 64;
					else
						column = (content[i + 1] + 256) - 97;
					if (Big5format[row][column] != 0)
						bfformat += Big5format[row][column];
					else if (3 <= row && row <= 37)
						bfformat += 200L;
				}
				i++;
			}

		rangeval = 50F * ((float) bfchars / (float) dbchars);
		formatval = 50F * ((float) bfformat / (float) totalformat);
		return (int) (rangeval + formatval);
	}

	private int utf8probability(byte content[]) {
		int score = 0;
		int rawtextlen = 0;
		int goodbytes = 0;
		int asciibytes = 0;
		rawtextlen = content.length;
		for (int i = 0; i < rawtextlen; i++)
			if ((content[i] & 0x7f) == content[i])
				asciibytes++;
			else if (-64 <= content[i] && content[i] <= -33
					&& i + 1 < rawtextlen && -128 <= content[i + 1]
					&& content[i + 1] <= -65) {
				goodbytes += 2;
				i++;
			} else if (-32 <= content[i] && content[i] <= -17
					&& i + 2 < rawtextlen && -128 <= content[i + 1]
					&& content[i + 1] <= -65 && -128 <= content[i + 2]
					&& content[i + 2] <= -65) {
				goodbytes += 3;
				i += 2;
			}

		if (asciibytes == rawtextlen)
			return 0;
		score = (int) (100F * ((float) goodbytes / (float) (rawtextlen - asciibytes)));
		if (score > 98)
			return score;
		if (score > 95 && goodbytes > 30)
			return score;
		else
			return 0;
	}

	private int utf16probability(byte content[]) {
		return (content.length <= 1 || -2 != content[0] || -1 != content[1])
				&& (-1 != content[0] || -2 != content[1]) ? 0 : 100;
	}

	private int asciiprobability(byte content[]) {
		int score = 75;
		int rawtextlen = content.length;
		for (int i = 0; i < rawtextlen; i++) {
			if (content[i] < 0)
				score -= 5;
			else if (content[i] == 27)
				score -= 5;
			if (score <= 0)
				return 0;
		}

		return score;
	}

	private int euc_krprobability(byte content[]) {
		int rawtextlen = 0;
		int dbchars = 1;
		int krchars = 1;
		long krformat = 0L;
		long totalformat = 1L;
		float rangeval = 0.0F;
		float formatval = 0.0F;
		rawtextlen = content.length;
		for (int i = 0; i < rawtextlen - 1; i++)
			if (content[i] < 0) {
				dbchars++;
				if (-95 <= content[i] && content[i] <= -2
						&& -95 <= content[i + 1] && content[i + 1] <= -2) {
					krchars++;
					totalformat += 500L;
					int row = (content[i] + 256) - 161;
					int column = (content[i + 1] + 256) - 161;
					if (EUC_KRformat[row][column] != 0)
						krformat += EUC_KRformat[row][column];
					else if (15 <= row && row < 55)
						krformat += 0L;
				}
				i++;
			}

		rangeval = 50F * ((float) krchars / (float) dbchars);
		formatval = 50F * ((float) krformat / (float) totalformat);
		return (int) (rangeval + formatval);
	}

	private int euc_jpprobability(byte content[]) {
		int rawtextlen = 0;
		int dbchars = 1;
		int jpchars = 1;
		long jpformat = 0L;
		long totalformat = 1L;
		float rangeval = 0.0F;
		float formatval = 0.0F;
		rawtextlen = content.length;
		for (int i = 0; i < rawtextlen - 1; i++)
			if (content[i] < 0) {
				dbchars++;
				if (-95 <= content[i] && content[i] <= -2
						&& -95 <= content[i + 1] && content[i + 1] <= -2) {
					jpchars++;
					totalformat += 500L;
					int row = (content[i] + 256) - 161;
					int column = (content[i + 1] + 256) - 161;
					if (JPformat[row][column] != 0)
						jpformat += JPformat[row][column];
					else if (15 <= row && row < 55)
						jpformat += 0L;
				}
				i++;
			}

		rangeval = 50F * ((float) jpchars / (float) dbchars);
		formatval = 50F * ((float) jpformat / (float) totalformat);
		return (int) (rangeval + formatval);
	}

	private int sjisprobability(byte content[]) {
		int rawtextlen = 0;
		int dbchars = 1;
		int jpchars = 1;
		long jpformat = 0L;
		long totalformat = 1L;
		float rangeval = 0.0F;
		float formatval = 0.0F;
		rawtextlen = content.length;
		for (int i = 0; i < rawtextlen - 1; i++)
			if (content[i] < 0) {
				dbchars++;
				if (i + 1 < content.length
						&& (-127 <= content[i] && content[i] <= -97 || -32 <= content[i]
								&& content[i] <= -17)
						&& (64 <= content[i + 1] && content[i + 1] <= 126 || -128 <= content[i + 1]
								&& content[i + 1] <= -4)) {
					jpchars++;
					totalformat += 500L;
					int row = content[i] + 256;
					int column = content[i + 1] + 256;
					int adjust;
					if (column < 159) {
						adjust = 1;
						if (column > 127)
							column -= 32;
						else
							column -= 25;
					} else {
						adjust = 0;
						column -= 126;
					}
					if (row < 160)
						row = (row - 112 << 1) - adjust;
					else
						row = (row - 176 << 1) - adjust;
					row -= 32;
					column = 32;
					if (row < JPformat.length && column < JPformat[row].length
							&& JPformat[row][column] != 0)
						jpformat += JPformat[row][column];
					i++;
				} else if (-95 <= content[i]) {
					// byte = content[i];
				}
			}

		rangeval = 50F * ((float) jpchars / (float) dbchars);
		formatval = 50F * ((float) jpformat / (float) totalformat);
		return (int) (rangeval + formatval) - 1;
	}
}
