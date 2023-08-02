package com.grupobios.common;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;

public class BigDecimalHelper {

	public static final Character GROUP_SEPARATOR = '.';
	public static final Character DECIMAL_SEPARATOR = ',';
	public static final String DEFAULTFORMAT = "#,##";

	public static BigDecimal parseString(String sValue) throws ParseException {
		DecimalFormatSymbols symbols = new DecimalFormatSymbols();
		symbols.setGroupingSeparator(GROUP_SEPARATOR);
		symbols.setDecimalSeparator(DECIMAL_SEPARATOR);
		String pattern = DEFAULTFORMAT;
		DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
		decimalFormat.setParseBigDecimal(true);
		decimalFormat.setGroupingSize(3);
		decimalFormat.setMaximumFractionDigits(2);
		BigDecimal parsedStringValue = (BigDecimal) decimalFormat.parse(sValue);
		return parsedStringValue;
	}
}
