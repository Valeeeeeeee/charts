package util;

import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Utilities {
	
	public static void main(String[] args) {
//		testAusDatei();
		
	}
	
	private static void testAusDatei() {
		String[] strings = new String[0];
		String dateiname = "/Users/valentinschraub/Documents/workspace/Bundesliga/config.txt";
		strings = ausDatei(dateiname);
		for (int i = 0; i < strings.length; i++) {
			log(strings[i]);
		}
	}
	
	public static void log() {
		log("");
	}
	
	public static void log(Object object) {
		System.out.println(object);
	}
	
	public static void error(Object object) {
		System.err.println(object);
	}
	
	public static void logWONL(Object object) {
		System.out.print(object);
	}
	
	public static int newMyDate() {
		int datum = 0;
		Calendar gc = new GregorianCalendar();
		int dd = gc.get(Calendar.DAY_OF_MONTH);
		int mm = gc.get(Calendar.MONTH) + 1;
		int yyyy = gc.get(Calendar.YEAR);
		
		datum = 10000 * yyyy + 100 * mm + dd;
		
		return datum;
	}
	
	public static int difference(int firstDate, int secondDate) {
		int dd = firstDate % 100;
		int mm = (firstDate % 10000) / 100;
		int yyyy = firstDate / 10000;
		
		GregorianCalendar greg = new GregorianCalendar(yyyy, mm - 1, dd);
		
		dd = secondDate % 100;
		mm = (secondDate % 10000) / 100;
		yyyy = secondDate / 10000;
		
		GregorianCalendar greg2 = new GregorianCalendar(yyyy, mm - 1, dd);
		
		int difference = 0;
		if (firstDate < secondDate) {
			while (greg.compareTo(greg2) == -1) {
				greg.add(Calendar.DAY_OF_MONTH, 1);
				difference++;
			}
		} else {
			while (greg.compareTo(greg2) == 1) {
				greg2.add(Calendar.DAY_OF_MONTH, 1);
				difference++;
			}
		}
		
		return difference;
	}
	
	public static String[] ausDatei(String dateiname) {
		ArrayList<String> arraylist = new ArrayList<String>();
		try {
			File datei = new File(dateiname);
			BufferedReader in = null;
			if (!datei.exists()) {
				datei.createNewFile();
				log(" >>> File did not exist but was created! --> " + datei.getAbsolutePath());
			} else {
				String element;
				try {
					in = new BufferedReader(new InputStreamReader(new FileInputStream(dateiname), "UTF-8"));
					while ((element = in.readLine()) != null) {
						arraylist.add(element);
					}
				} catch (Exception e) {
					log("Fehler beim Laden!");
					e.printStackTrace();
				} finally {
					if (in != null) {
						try {
							in.close();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		} catch (Exception e) {
			log("Programmfehler!");
			e.printStackTrace();
		}
		String[] zielarray = new String[arraylist.size()];
		for (int i = 0; i < arraylist.size(); i++) {
			zielarray[i] = arraylist.get(i);
		}

		return zielarray;
	}
	
	public static void inDatei(String dateiname, String[] strings) {
		try {
			BufferedWriter out = null;
			try {
				out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(dateiname), "UTF-8"));
				for (int i = 0; i < strings.length; i++) {
					out.write(strings[i]);
					out.newLine();
				}
			} catch (Exception e) {
//				e.printStackTrace();
			} finally {
				if (out != null) {
					try {
						out.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		} catch (Exception e) {
			log(" >> in_datei >> Exception caught at file: " + dateiname + "\n");
		}
	}
}

