package fr.diyfr.sncf.gtfs.parser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import fr.diyfr.sncf.gtfs.exception.FileAccessException;
import fr.diyfr.sncf.gtfs.exception.FileFormatException;
import fr.diyfr.sncf.gtfs.model.GTFSModel;

/**
 * Classe abstraite permettant la désérialisation des données dans des Listes d'objets 
 * @param <T>
 */
public abstract class CSVParser<T extends GTFSModel> {

	private final Class<T> clazz;

	public CSVParser(Class<T> clasz) {
		this.clazz = clasz;
	}

	private T newClassIntance() {
		T entry;
		try {
			entry = clazz.newInstance();
		} catch (InstantiationException ie) {
			throw new RuntimeException(String.format("can not instantiate class %s", clazz.getName()), ie);
		} catch (IllegalAccessException iae) {
			throw new RuntimeException(String.format("can not access class %s", clazz.getName()), iae);
		}

		return entry;
	}

	public T parseEntry(String... data) {
		T entry = newClassIntance();
		try {
			entry.fromStringArray(data);
		} catch (FileFormatException e) {
			entry = null;
		}
		return entry;
	}

	public List<T> getAll(String file, String separator)throws FileAccessException {
		List<T> result = new ArrayList<T>();
		BufferedReader br = null;
		boolean firstLine = true;
		String line = "";
		try {

			br = new BufferedReader(new FileReader(file));
			while ((line = br.readLine()) != null) {
				if (firstLine) {
					firstLine = false;
					continue;
				}
				String[] splitValue = line.split(separator,newClassIntance().getNbEntry());
				T entry = parseEntry(splitValue);
				if (entry != null)
					result.add(entry);
			}

		} catch (FileNotFoundException e) {
			throw new FileAccessException(e.getMessage());
		} catch (IOException e) {
			throw new FileAccessException(e.getMessage());
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}
}
