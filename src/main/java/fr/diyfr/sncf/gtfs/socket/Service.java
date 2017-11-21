package fr.diyfr.sncf.gtfs.socket;

import java.util.Date;
import java.util.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import fr.diyfr.sncf.gtfs.demo.itinerary.helper.DjikstraHelper;
import fr.diyfr.sncf.gtfs.demo.itinerary.model.StopResult;
import fr.diyfr.sncf.gtfs.demo.load.helper.GTFSLoaderHelper;
import fr.diyfr.sncf.gtfs.demo.load.model.GTFSData;
import fr.diyfr.sncf.gtfs.demo.search.helper.GTFSSearchHelper;
import fr.diyfr.sncf.gtfs.exception.FileAccessException;
import fr.diyfr.sncf.gtfs.helper.DateHelper;
import fr.diyfr.sncf.gtfs.model.Stops;

public class Service {

	private ServerSocket myService;
	private boolean isOpen = false;
	private CommandEnum currentCommand = CommandEnum.HELP;
	private GTFSData gtfsData;

	public Service(int PortNumber) {
		try {
			myService = new ServerSocket(PortNumber);
			isOpen = true;
		} catch (IOException e) {
			isOpen = false;
			System.out.println(e);
		}

		if (isOpen) {
			while (true) {
				PrintWriter response;
				BufferedReader command;
				String resultCmd;
				try {
					Socket client = myService.accept();
					System.out.println("Connected!");
					response = new PrintWriter(client.getOutputStream());
					command = new BufferedReader(new InputStreamReader(client.getInputStream()));
					resultCmd = consumeCmd(command.readLine());
					response.print(resultCmd + "\r\n");
					response.flush();
				} catch (IOException ex) {
					System.out.println(ex);
				}
			}
		}
	}

	public void start(String path) {
		try {
			gtfsData = GTFSLoaderHelper.readGTFS(path);
		} catch (FileAccessException e) {
			System.err.println(e.getLocalizedMessage());
		}
	}

	public String consumeCmd(String cmd) {
		String result = "Later";
		currentCommand = CommandEnum.getCmd(cmd);
		List<String> data = currentCommand.getData(cmd);
		switch (currentCommand) {
		case ITINERARY:
			data.get(0); // UIC DEPART
			data.get(1); // UIC ARRIVEE
			Date dte = DateHelper.getShortDateFormatFromString(data.get(1));
			if (dte != null) {
				result = getItinerary(data.get(0), data.get(1), dte);
			}
			break;
		case STATIONS:
			result = GTFSSearchHelper.getSimpleFormatStopList(gtfsData).toString();
			break;
		case TRAIN:
			result = GTFSSearchHelper.getSimpleFormatTrainDateList(gtfsData, data.get(0), null).toString();
			break;
		case TRAIN_DATE:
			Date dteTrain = DateHelper.getShortDateFormatFromString(data.get(1));
			if (dteTrain != null) {
				result = GTFSSearchHelper.getSimpleFormatTrainDateList(gtfsData, data.get(0), dteTrain).toString();
			}
			break;
		case ARRIVALS:
			data.get(0); // UIC
			data.get(1); // Date
		case DEPARTURES:
			data.get(0); // UIC
			data.get(1); // Date
		case HELP:
		default:
			break;
		}
		return result;
	}

	private String getItinerary(String UICStart, String UICDest, Date date) {
		GTFSData currentData = GTFSSearchHelper.getGTFSRawDataForDate(gtfsData, date);
		DjikstraHelper dj = new DjikstraHelper(currentData);
		List<StopResult> path = dj.findFastPath(UICStart, UICDest);
		StringBuilder logres = new StringBuilder();
		for (StopResult stop : path) {
			Stops stops = currentData.getStops().get(stop.getStop_id());
			logres.append("[");
			logres.append(stop.getCodeUIC());
			logres.append("] ");
			logres.append(stops.getStop_name());
			logres.append("\t");
			if (stop.getParentUIC() != null) {
				Stops parA = currentData.getStops().get(dj.getStopIdByUICCode(stop.getParentUIC()[0]));
				Stops parB = currentData.getStops().get(dj.getStopIdByUICCode(stop.getParentUIC()[1]));
				logres.append("<");
				logres.append("[");
				logres.append(stop.getParentUIC()[0]);
				logres.append("] ");
				logres.append(parA.getStop_name());

				logres.append(", ");
				logres.append("[");
				logres.append(stop.getParentUIC()[1]);
				logres.append("] ");
				logres.append(parB.getStop_name());
				logres.append(">");
			}
		}

		return null;

	}

	public ServerSocket getMyService() {
		return myService;
	}

	public void setMyService(ServerSocket myService) {
		this.myService = myService;
	}

	public boolean isOpen() {
		return isOpen;
	}

	public void setOpen(boolean isOpen) {
		this.isOpen = isOpen;
	}

}
