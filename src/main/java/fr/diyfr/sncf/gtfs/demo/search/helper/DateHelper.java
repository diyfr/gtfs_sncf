package fr.diyfr.sncf.gtfs.demo.search.helper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import fr.diyfr.sncf.gtfs.model.StopTime;

/**
 * 
 * Helper de manipulation des dates
 *
 */
public class DateHelper {
	private static final String FORMAT_JSON = "yyyy-MM-dd'T'HH:mm:ssZZ";
	private static final SimpleDateFormat spf_FORMAT_JSON = new SimpleDateFormat(FORMAT_JSON);

	private static final String FORMAT_TIME_JSON = "HH:mm:ssZZ";
	private static final SimpleDateFormat spf_FORMAT_TIME_JSON = new SimpleDateFormat(FORMAT_TIME_JSON);

	/**
	 * Fournit une chaine représentant une date/heure
	 * 
	 * @param dateTime
	 * @return
	 */
	public static String getJsonFormatDateTime(Date dateTime) {
		String result = null;
		result = spf_FORMAT_JSON.format(dateTime);
		return result;
	}

	/**
	 * Fournit une chaine représentant une heure
	 * 
	 * @param dateTime
	 * @return
	 */
	public static String getJsonFormatTime(Date dateTime) {
		String result = null;
		result = spf_FORMAT_TIME_JSON.format(dateTime);
		return result;
	}

	/**
	 * Assemble une date et une heure
	 * 
	 * @param date
	 * @param stopTime
	 * @return
	 */
	public static Date concatDateTime(Date date, StopTime stopTime) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR, stopTime.getHours());
		cal.set(Calendar.MINUTE, stopTime.getMinutes());
		cal.set(Calendar.SECOND, stopTime.getSeconds());
		cal.set(Calendar.MILLISECOND, 0);
		if (stopTime.isMidnightJump()) {
			cal.add(Calendar.DAY_OF_YEAR, 1);
		}
		return cal.getTime();
	}

	/**
	 * génére une chaine de caractères correspondant aux jours de semaines de
	 * circulation
	 * 
	 * @param cal
	 * @return
	 */
	public static String getTrafficDayOfWeekToString(fr.diyfr.sncf.gtfs.model.Calendar cal) {
		StringBuilder stResult = new StringBuilder();
		StringBuilder stD = new StringBuilder();
		stD.append((cal.isMonday() ? "1" : "0"));
		stD.append((cal.isTuesday() ? "1" : "0"));
		stD.append((cal.isWednesday() ? "1" : "0"));
		stD.append((cal.isThursday() ? "1" : "0"));
		stD.append((cal.isFriday() ? "1" : "0"));
		stD.append((cal.isSaturday() ? "1" : "0"));
		stD.append((cal.isSunday() ? "1" : "0"));
		int value = Integer.parseInt(stD.toString(), 2);
		switch (value) {
		case 0b1111111:
			stResult.append("Tous les jours");
			break;
		case 0b1111110:
			stResult.append("Du Lun au Sam");
			break;
		case 0b1111100:
			stResult.append("Du Lun au Ven");
			break;
		case 0b1111000:
			stResult.append("Du Lun au Jeu");
			break;
		case 0b0111000:
			stResult.append("Du Mar au Jeu");
			break;
		case 0b0111100:
			stResult.append("Du Mar au Ven");
			break;
		case 0b1000111:
			stResult.append("Du Ven au Lun");
			break;
		default:
			stResult.append((cal.isMonday() ? "Lun, " : ""));
			stResult.append((cal.isTuesday() ? "Mar," : ""));
			stResult.append((cal.isWednesday() ? "Mer, " : ""));
			stResult.append((cal.isThursday() ? "Jeu, " : ""));
			stResult.append((cal.isFriday() ? "Ven, " : ""));
			stResult.append((cal.isSaturday() ? "Sam, " : ""));
			stResult.append((cal.isSunday() ? "Dim, " : ""));
			break;
		}

		return stResult.toString();
	}
}
