package fr.diyfr.sncf.gtfs.demo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import fr.diyfr.sncf.gtfs.demo.itinerary.helper.DjikstraHelper;
import fr.diyfr.sncf.gtfs.demo.itinerary.model.StopResult;
import fr.diyfr.sncf.gtfs.demo.load.helper.GTFSLoaderHelper;
import fr.diyfr.sncf.gtfs.demo.load.model.GTFSData;
import fr.diyfr.sncf.gtfs.demo.search.helper.GTFSSearchHelper;
import fr.diyfr.sncf.gtfs.demo.search.model.StopList;
import fr.diyfr.sncf.gtfs.demo.search.model.TrainList;
import fr.diyfr.sncf.gtfs.exception.FileAccessException;
import fr.diyfr.sncf.gtfs.helper.*;
import fr.diyfr.sncf.gtfs.model.Stops;

/**
 * Exemple de chargement des données GTFS en provenance de
 * https://ressources.data.sncf.com/explore/dataset/sncf-ter-gtfs/
 *
 */
public class MainDemo {
	static SimpleDateFormat spf = new SimpleDateFormat("dd/MM/yyyy-hh:mm:ss:SSS");

	public static void main(String[] args) {

		GTFSData data;
		try {
			log("Loading files");
			data = GTFSLoaderHelper.readGTFS(args[0]);
			log("Files loaded");
			log("Trips number :" + String.valueOf(data.getTrips().size()));
			// On récupère les données GTFS filtrées pour un numéro de train
			log("Filtering DATA for on TrainNumber (866327)");
			GTFSData dataFiltered = GTFSSearchHelper.getGTFSRawDataForTrainNumber(data, "866327");
			log(dataFiltered);
			dataFiltered = null;
			// On prépare une date
			Date test = null;
			try {
				test = DateHelper.getDateFromGTFSCalendar("20150222");
			} catch (ParseException e) {
				e.printStackTrace();
			}
			// On récupère les données GTFS filtrées pour une date
			log("Filtering DATA for one date 22/02/2015");
			dataFiltered = GTFSSearchHelper.getGTFSRawDataForDate(data, test);
			// log(dataFiltered); Too big for toString();
			log("Trips number :" + String.valueOf(dataFiltered.getTrips().size()));
			dataFiltered = null;

			// On récupère les données GTFS filtrées pour un numéro de train et
			// une date données
			log("Filtering DATA for on TrainNumber (866327) and date :22/02/2015");
			dataFiltered = GTFSSearchHelper.getGTFSRawDataForTrainNumberAndDate(data, "866327", test);
			log(dataFiltered);
			log("Simple Format Result for on TrainNumber (866327)");

			// On récupère une liste de données de circulations simplifées pour
			// un numéro de train
			TrainList trainList = GTFSSearchHelper.getSimpleFormatTrainDateList(data, "866327", null);
			log(trainList);

			// On récupère une liste de données de circulations simplifées pour
			// un numéro de train et une date
			log("Simple Format Result for on TrainNumber (866327) and date :22/02/2015");
			trainList = GTFSSearchHelper.getSimpleFormatTrainDateList(data, "866327", test);
			log(trainList);
			trainList = null;

			// On récupère une Liste des gares simplifées
			log("Simple Format stops List");
			StopList sList = GTFSSearchHelper.getSimpleFormatStopList(data);
			log(sList);
			sList = null;

			// Helper de Calcul d'itinéraire basé sur les durée de trajets
			Date delay = new Date();
			log("Launch Djikstra Helper");
			DjikstraHelper dj = new DjikstraHelper(data);
			log("Helper make Graph in :" + String.valueOf((new Date().getTime() - delay.getTime())) + "ms");
			// exemple de calcul d'itinéraire
			log("Searching Path for 87581751(Pessac) to 87583849(Ruffec)");
			// On donne les codes UIC en entrée
			List<StopResult> path = dj.findFastPath("87581751", "87583849");
			for (StopResult stop : path) {
				Stops stops = data.getStops().get(stop.getStop_id());
				StringBuilder logres = new StringBuilder();
				logres.append("[");
				logres.append(stop.getCodeUIC());
				logres.append("] ");
				logres.append(stops.getStop_name());
				logres.append("\t");
				if (stop.getParentUIC() != null) {
					Stops parA = data.getStops().get(dj.getStopIdByUICCode(stop.getParentUIC()[0]));
					Stops parB = data.getStops().get(dj.getStopIdByUICCode(stop.getParentUIC()[1]));
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
				log(logres.toString());
			}

		} catch (FileAccessException e) {
			e.printStackTrace();
		}
	}

	static void log(Object message) {
		System.out.print(spf.format(new Date()));
		System.out.print(": ");
		System.out.println(message.toString());
	}
}
