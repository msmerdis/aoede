package com.aoede.plugins.validation.source;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

public class LineValidator implements Consumer<String> {
	public static List<String> ignoreTrailingList = List.of("", "txt");
	public static List<String> ignoreLeadingList = List.of("", "txt", "yml", "yaml");
	public static List<String> ignoreJavaDocList = List.of("java", "ts");

	private int lineNo = 0;
	private int consecutiveEmptyLines = 0;

	private boolean checkTrailing;
	private boolean checkLeading;

	private List<String> errors = new LinkedList<String>();
	private String ext;

	public LineValidator (String extension) {
		ext = extension;
		checkTrailing = !ignoreTrailingList.contains(ext);
		checkLeading  = !ignoreLeadingList.contains(ext);
	}

	public void accept(String line) {
		// increase line size
		lineNo += 1;

		// process line as bytes
		byte[] bytes = line.getBytes();

		// calculate consecutive empty lines
		if (bytes.length == 0) {
			consecutiveEmptyLines += 1;
		} else {
			int i, j;

			// skip leading tab characters and trailing whitespace
			for (i = 0; i < bytes.length && bytes[i] == '\t'; i += 1);
			for (j = bytes.length - 1; j >= 0 && Character.isWhitespace(bytes[j]); j -= 1);

			// line must not have trailing whitespaces
			if (checkTrailing && Character.isWhitespace(bytes[bytes.length-1])) {
				errors.add("line " + lineNo + " ends with whitespace");
			}

			// line must not have leading whitespace other than tabs
			if (checkLeading && i < bytes.length && Character.isWhitespace(bytes[i])) {
				// check for java docs
				if (
					!ignoreJavaDocList.contains(ext) ||
					i + 1 >= bytes.length ||
					bytes[ i ] != ' ' ||
					bytes[i+1] != '*'
				) {
					errors.add("line " + lineNo + " starts with non tab whitespace");
				}

			}

			// check that tabs are not used after the initial tab sequence
			for (; i <= j; i += 1) {
				if (bytes[i] == '\t') {
					errors.add("line " + lineNo + " contains tab");
				}
			}

			consecutiveEmptyLines = 0;
		}
	}

	public boolean hasErrors () {
		return errors.size() > 0;
	}

	public List<String> getErrors () {
		return errors;
	}

	public int getConsecutiveEmptyLines () {
		return consecutiveEmptyLines;
	}

}



