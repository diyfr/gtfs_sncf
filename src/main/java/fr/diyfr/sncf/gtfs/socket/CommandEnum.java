package fr.diyfr.sncf.gtfs.socket;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum CommandEnum {

	STATIONS("(stations)"), TRAIN("train/(.[0-9]*)?"), TRAIN_DATE("train/(.[0-9]*)?&((201[4-9]|202[0-9])-(0[1-9]|1[0-2])-(0[1-9]|1[0-9]|2[0-9]|3[0-1]))"), ITINERARY(
			"itinerary/(.[0-9]*)?&(.[0-9]*)?&((201[4-9]|202[0-9])-(0[1-9]|1[0-2])-(0[1-9]|1[0-9]|2[0-9]|3[0-1]))"), DEPARTURES("stations/(.[0-9]*)?/departures"), ARRIVALS(
			"stations/(.[0-9]*)?/arrivals"), HELP("");
	private String path;
	private Pattern internalPattern;

	private CommandEnum(String path) {
		this.path = path;
		if (path.length() > 0) {
			internalPattern = Pattern.compile(path);
		}
	}

	public static CommandEnum getCmd(String cmd) {
		CommandEnum result = HELP;
		for (CommandEnum enu : CommandEnum.values()) {
			if (enu.internalPattern != null) {
				Matcher match = enu.internalPattern.matcher(cmd);
				if (match.matches()) {
					result = enu;
					break;
				}
			}
		}
		return result;
	}

	public List<String> getData(String cmd){
		List<String> result = new  ArrayList<String>();
		Matcher match = this.internalPattern.matcher(cmd);
		if (match.matches()&& match.find()){
			for (int i=0;i< match.groupCount();i++){
				result.add(match.group(i));
			}
		}
		return result;
	}
}
