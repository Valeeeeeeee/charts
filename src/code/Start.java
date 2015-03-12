package code;
import static util.Utilities.*;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.*;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class Start extends JFrame {
	private static final long serialVersionUID = 2288502428577112426L;

	private String workspace;
	private String workspaceWIN = "C:\\Users\\vsh\\myWorkspace\\Charts";
	private String workspaceMAC = "/Users/valentinschraub/Documents/workspace/Charts";


	private String filenameCSV = "/Users/valentinschraub/Desktop/Charts2014/Singles.csv";
	private DefaultListModel jListCSVModel = new DefaultListModel();

	private String config;
	private DefaultListModel jListConfigModel = new DefaultListModel();

	private String daten;
	private DefaultListModel jListDatenModel = new DefaultListModel();

	private int anzahlLieder;
	private static int anzahl_wochen;
	private int currentWeek;
	private int anzahlNeueinsteiger;

	private Chartzeile[] chartZeilen;
	private static int[] datum;
	public static int startdate;
	private int editedWeek;

	// Charts erneuern
	private int aktuellerChartplatz = 1;
	private int[] indicesofnotchartedsongs;
	private int[] indicesOfOldCharts;
	private int[] indicesOfNewCharts;
	private int[] checkliste;
	private String[][] results;
	private int[] neueinsteiger = new int[0];
	private JPanel neueinsteigerPnl;
	private JLabel[] neueinsteigerCHP;
	private JTextField[][] neueinsteigerDaten;
	private JButton[] neueinsteigerReEntry;
	private JButton neueinsteigerFertig;

	// neuen Song anlegen
	private JPanel neuersong;
	private JLabel jLblPlatzNeueinsteiger;
	private JTextField jTFNameOfSong;
	private JTextField jTFNameOfArtist;
	private JButton jBtnNeueinsteigerWeiter;
	private boolean savesong = false;

	// Homescreen
	private JPanel Homescreen;
	private JButton jBtnChartsAnzeigen;
	private JButton jBtnChartsErneuern;
	private JButton jBtnBeenden;
	private JButton jBtnZurueck;

	// Charts aktualisieren
	private JPanel chartsneu;
	private JLabel jLblWoche;
	private JLabel jLblChartplatz;
	private JButton jBtnWeiter;
	private JLabel previewCHZ;
	private JTextField jTFAlterChartplatz;

	// Charts anzeigen
	private JPanel chartsAnzeigen;
	private JLabel jLblChartsOfWeek;
	private JComboBox jCBChartsOfWeek;
	private ChartUebersicht chU;
	private JScrollPane chartsScrP;
	private JPanel chartsPanel;
	
	private Point LOC_CHUEBERSICHT = new Point(700, 90);
	
	private Rectangle REC_ZURUECK = new Rectangle(10, 10, 100, 30);
	private Rectangle REC_ANZEIGEN = new Rectangle(600, 300, 200, 60);
	private Rectangle REC_ERNEUERN = new Rectangle(600, 400, 200, 60);
	private Rectangle REC_BEENDEN = new Rectangle(600, 500, 200, 60);

	private Rectangle REC_CHARTSANZEIGEN = new Rectangle(50, 50, 1350, 750);
	
	private Rectangle REC_LBLWOCHE = new Rectangle(20, 50, 150, 30);
	private Rectangle REC_CBWOCHE = new Rectangle(170, 50, 100, 30);//50);
	
	private Rectangle REC_PANEL = new Rectangle(50, 50, 1300, 600);
	private Rectangle REC_WOCHE = new Rectangle(50, 10, 200, 30);
	private Rectangle REC_CHP = new Rectangle(100, 100, 40, 40);
	private Rectangle REC_WEITER = new Rectangle(150, 100, 100, 40);
	private Rectangle REC_NEUERCHP = new Rectangle(50, 100, 40, 40);
	private Rectangle REC_PREVIEW = new Rectangle(50, 150, 350, 30);
	private Rectangle REC_NEUFERTIG = new Rectangle(30, 460, 90, 30);

	private Rectangle REC_NEUERSONG = new Rectangle(100, 100, 1000, 600);
	private Rectangle REC_PLATZ = new Rectangle(20, 60, 70, 70);
	private Rectangle REC_SONGTITLE = new Rectangle(100, 60, 200, 30);
	private Rectangle REC_INTERPRETER = new Rectangle(100, 100, 200, 30);
	private Rectangle REC_NSWEITER = new Rectangle(310, 80, 70, 30);

	private Color colorChartsAnzeigen = new Color(255, 205, 205);
	private Color colorChartsNeu = new Color(255, 205, 205);
	private Color colorWochenLabel = new Color(25, 255, 25);
	private Color colorChartplatz = new Color(25, 255, 25);
	private Color colorPreviewCHZ = new Color(25, 255, 25);
	private Color colorNeuerSong = new Color(255, 255, 165);
	private Color colorjLblPlatzNeueinsteiger = new Color(25, 255, 25);
	private Color colorNameOfSong = new Color(25, 255, 25);
	private Color colorNameOfArtist = new Color(25, 255, 25);

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Start inst = new Start();
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
			}
		});
	}

	public Start() {
		super();
		checkOS();
		loadConfiguration();
		currentWeek = determineCurrentWeek();
		log("Current week = " + getCurrentWeek());
		datenLaden();
		initGUI();
	}

	private void initGUI() {
		try {
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			getContentPane().setLayout(null);

			// Alle Array werden initialisiert
			
			
			// Homescreen
			{
				Homescreen = new JPanel();
				getContentPane().add(Homescreen);
				Homescreen.setLayout(null);
				Homescreen.setBounds(0, 0, 1440, 874);
				Homescreen.setBackground(Color.red);
				Homescreen.setVisible(true);
			}
			{
				jBtnZurueck = new JButton();
				getContentPane().add(jBtnZurueck);
				jBtnZurueck.setBounds(REC_ZURUECK);
				jBtnZurueck.setText("zurueck");
				jBtnZurueck.setFocusable(false);
				jBtnZurueck.setVisible(false);
				jBtnZurueck.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						jBtnZurueckActionPerformed();
					}
				});
			}
			{
				jBtnChartsAnzeigen = new JButton();
				Homescreen.add(jBtnChartsAnzeigen);
				jBtnChartsAnzeigen.setBounds(REC_ANZEIGEN);
				jBtnChartsAnzeigen.setText("Anzeigen");
				jBtnChartsAnzeigen.setFocusable(false);
				jBtnChartsAnzeigen.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						jBtnChartsAnzeigenActionPerformed();
					}
				});
			}
			{
				jBtnChartsErneuern = new JButton();
				Homescreen.add(jBtnChartsErneuern);
				jBtnChartsErneuern.setBounds(REC_ERNEUERN);
				jBtnChartsErneuern.setText("Erneuern");
				jBtnChartsErneuern.setFocusable(false);
				jBtnChartsErneuern.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						jBtnChartsErneuernActionPerformed();
					}
				});
			}
			{
				jBtnBeenden = new JButton();
				Homescreen.add(jBtnBeenden);
				jBtnBeenden.setBounds(REC_BEENDEN);
				jBtnBeenden.setText("Beenden");
				jBtnBeenden.setFocusable(false);
				jBtnBeenden.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						jBtnBeendenActionPerformed();
					}
				});
			}
			
			// Charts anzeigen
			{
				chartsAnzeigen = new JPanel();
				getContentPane().add(chartsAnzeigen);
				chartsAnzeigen.setLayout(null);
				chartsAnzeigen.setBounds(REC_CHARTSANZEIGEN);
				chartsAnzeigen.setVisible(false);

				chartsAnzeigen.setOpaque(true);
				chartsAnzeigen.setBackground(colorChartsAnzeigen);
			}
			// Label
			{
				jLblChartsOfWeek = new JLabel();
				chartsAnzeigen.add(jLblChartsOfWeek);
				jLblChartsOfWeek.setBounds(REC_LBLWOCHE);
				jLblChartsOfWeek.setText("Charts vom Freitag, den ");
			}
			
			// Combobox
			String[] weeks = new String[anzahl_wochen];
			for (int i = 0; i < weeks.length; i++) {
				weeks[i] = datum(myDateVerschoben(startdate, 7 * i));
			}
			ComboBoxModel<String> CBModel = new DefaultComboBoxModel<String>(weeks);
			{
				jCBChartsOfWeek = new JComboBox();
				chartsAnzeigen.add(jCBChartsOfWeek);
				jCBChartsOfWeek.setBounds(REC_CBWOCHE);
				jCBChartsOfWeek.setModel(CBModel);
				jCBChartsOfWeek.addItemListener(new ItemListener() {
					public void itemStateChanged(ItemEvent evt) {
						jCBChartsOfWeekItemStateChanged(evt);
					}
				});
			}
			
			{
				chartsPanel = new JPanel();
				chartsAnzeigen.add(chartsPanel);
				chartsPanel.setLayout(null);
				chartsPanel.setPreferredSize(new Dimension(Chartzeile.getPreferredWidth(), 0));
				chartsPanel.setBackground(Color.red);
				chartsPanel.setVisible(true);
			}

			{
				chartsScrP = new JScrollPane();
				chartsAnzeigen.add(chartsScrP);
				chartsScrP.setBounds(20, 90, 630 + 19, 580);
//				chartsScrP.setVisible(false);
				chartsScrP.setViewportView(chartsPanel);
			}

			// Charts aktualisieren
			{
				chartsneu = new JPanel();
				getContentPane().add(chartsneu);
				chartsneu.setLayout(null);
				chartsneu.setBounds(REC_PANEL);
				chartsneu.setVisible(false);

				chartsneu.setOpaque(true);
				chartsneu.setBackground(colorChartsNeu);
			}
			{
				jLblWoche = new JLabel();
				chartsneu.add(jLblWoche);
				jLblWoche.setBounds(REC_WOCHE);
				jLblWoche.setHorizontalAlignment(SwingConstants.CENTER);

				jLblWoche.setOpaque(true);
				jLblWoche.setBackground(colorWochenLabel);
			}
			{
				jLblChartplatz = new JLabel();
				chartsneu.add(jLblChartplatz);
				jLblChartplatz.setBounds(REC_CHP);
				jLblChartplatz.setHorizontalAlignment(SwingConstants.CENTER);
				jLblChartplatz.setVisible(false);
				jLblChartplatz.setOpaque(true);
				jLblChartplatz.setBackground(colorChartplatz);
			}
			{
				jBtnWeiter = new JButton();
				chartsneu.add(jBtnWeiter);
				jBtnWeiter.setBounds(REC_WEITER);
				jBtnWeiter.setText("weiter");
				jBtnWeiter.setFocusable(false);
				jBtnWeiter.setVisible(false);
				jBtnWeiter.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						jBtnWeiterActionPerformed();
					}
				});
			}
			{
				jTFAlterChartplatz = new JTextField();
				chartsneu.add(jTFAlterChartplatz);
				jTFAlterChartplatz.setBounds(REC_NEUERCHP);
				jTFAlterChartplatz.setHorizontalAlignment(SwingConstants.CENTER);
				jTFAlterChartplatz.setVisible(false);
				jTFAlterChartplatz.addKeyListener(new KeyAdapter() {
					public void keyTyped(KeyEvent arg0) {
						neuerChartplatzKeyTyped(arg0);
					}
				});
			}
			{
				previewCHZ = new JLabel();
				chartsneu.add(previewCHZ);
				previewCHZ.setBounds(REC_PREVIEW);
				previewCHZ.setHorizontalAlignment(SwingConstants.CENTER);
				previewCHZ.setText("jgbh");

				previewCHZ.setVisible(false);
				previewCHZ.setOpaque(true);
				previewCHZ.setBackground(colorPreviewCHZ);
			}

			// Neuen Song hinzufuegen
			{
				neuersong = new JPanel();
				getContentPane().add(neuersong);
				neuersong.setLayout(null);
				neuersong.setBounds(REC_NEUERSONG);
				neuersong.setVisible(false);

				neuersong.setOpaque(true);
				neuersong.setBackground(colorNeuerSong);
			}
			{
				jLblPlatzNeueinsteiger = new JLabel();
				neuersong.add(jLblPlatzNeueinsteiger);
				jLblPlatzNeueinsteiger.setBounds(REC_PLATZ);
				jLblPlatzNeueinsteiger.setHorizontalAlignment(SwingConstants.CENTER);
				jLblPlatzNeueinsteiger.setText("jgbh");

				jLblPlatzNeueinsteiger.setOpaque(true);
				jLblPlatzNeueinsteiger.setBackground(colorjLblPlatzNeueinsteiger);
			}
			{
				jTFNameOfSong = new JTextField();
				neuersong.add(jTFNameOfSong);
				jTFNameOfSong.setBounds(REC_SONGTITLE);
				jTFNameOfSong.setHorizontalAlignment(SwingConstants.CENTER);
				jTFNameOfSong.setText("");

				jTFNameOfSong.setOpaque(true);
				jTFNameOfSong.setBackground(colorNameOfSong);
			}
			{
				jTFNameOfArtist = new JTextField();
				neuersong.add(jTFNameOfArtist);
				jTFNameOfArtist.setBounds(REC_INTERPRETER);
				jTFNameOfArtist.setHorizontalAlignment(SwingConstants.CENTER);
				jTFNameOfArtist.setText("");

				jTFNameOfArtist.setOpaque(true);
				jTFNameOfArtist.setBackground(colorNameOfArtist);
			}
			{
				jBtnNeueinsteigerWeiter = new JButton();
				neuersong.add(jBtnNeueinsteigerWeiter);
				jBtnNeueinsteigerWeiter.setBounds(REC_NSWEITER);
				jBtnNeueinsteigerWeiter.setText("weiter");
				jBtnNeueinsteigerWeiter.setFocusable(false);
				jBtnNeueinsteigerWeiter.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						jBtnNeueinsteigerWeiterActionPerformed();
					}
				});
			}
			
			showOverviewOf(519);

			pack();
			setSize(1440, 874);
			setResizable(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void fillIndicesOfOldCharts(boolean displayOnConsole) {
		double mw1, mw2, diff1;
		mw1 = zeitpunkt();
		if (displayOnConsole)	log("\n\n\n");

		int counterNOTcharted = 0;
		for (int index = 0; index < this.anzahlLieder; index++) {
			int rank = chartZeilen[index].getRankOfWeek(editedWeek - 1);
			if (rank <= 100) {
				if (displayOnConsole)	log("Song(" + chartZeilen[index].getID() + ") " + chartZeilen[index].getNameOfSong()
						+ " war in Woche " + (editedWeek) + " auf Platz " + rank);
				indicesOfOldCharts[rank - 1] = index;
			} else {
				if (displayOnConsole)	log("Song(" + chartZeilen[index].getID() + ") " + chartZeilen[index].getNameOfSong()
						+ " war in Woche " + (editedWeek) + " nicht in den Charts.");
				indicesofnotchartedsongs[counterNOTcharted] = index;
				counterNOTcharted++;
			}
		}
		mw2 = zeitpunkt();
		diff1 = mw2 - mw1;
		if (displayOnConsole)	log(mw1 + "  " + mw2 + "\n" + diff1);
	}

	private void testAusgabeDerAltenUndNeuenIndizes(boolean displayOnConsole) {
		if (displayOnConsole) {
			for (int i = 0; i < 100; i++) {
				log("IDs auf Platz " + (i + 1) + ":  --  old:" + indicesOfOldCharts[i] + ", new:" + indicesOfNewCharts[i]);
			}
		}
	}

	private void testAusgabeDerUnchartedSongs(boolean displayOnConsole) {
		if (displayOnConsole) {
			for (int i = 0; i < indicesofnotchartedsongs.length; i++) {
				log("Nicht in den Charts sind: " + indicesofnotchartedsongs[i]);
			}
		}
	}

	private void testAusgabeVeraenderungDerChartposition(int oldCHP) {
		if (oldCHP > aktuellerChartplatz) {
			log("Der Song steigt von der " + oldCHP + " auf die " + aktuellerChartplatz + ".");
		} else if (oldCHP < aktuellerChartplatz) {
			log("Der Song faellt von der " + oldCHP + " auf die " + aktuellerChartplatz + ".");
		} else if (oldCHP == aktuellerChartplatz) {
			log("Der Song bleibt auf der " + aktuellerChartplatz + ".");
		}
	}

	private void jBtnZurueckActionPerformed() {
		if (chartsneu.isVisible()) {
//			chartsneu.setVisible(false);
			return; // Sicherheitsabfrage wegen der aktuellen Chart-Eingabe
		} else {
			chartsAnzeigen.setVisible(false);	
		}
		
		Homescreen.setVisible(true);
		jBtnZurueck.setVisible(false);
	}
	
	public void showOverviewOf(int index) {
		if (chU != null) {
			chartsAnzeigen.remove(chU);
		}
		chU = new ChartUebersicht(chartZeilen[index]);
		chartsAnzeigen.add(chU);
		chU.setLocation(LOC_CHUEBERSICHT);
		chU.setVisible(true);
	}
	
	private void getChartsFromWebsite() {
		// TODO
		anzahlNeueinsteiger = 0;
		neueinsteiger = new int[100];
		
		reinitializeIndexArrays();
		fillIndicesOfOldCharts(false);
		
		try {
			String regex = "</li>";
			
			results = new String[10][0];
			
			for (int j = 1; j <= 10; j++) {
				URL url = new URL("http://www.viva.tv/charts/16-viva-top-100?page=" + j);
				String result = accessWebsite(url);
				
				results[j - 1] = result.split(regex);
			}
			
			for (int j = 0; j < 10; j++) {
				for (int k = 1; k <= 10; k++) {
					String elem = results[j][k].substring(results[j][k].indexOf("'current_position'>") + 19);

					results[j][k] = extractData(elem);
					log(results[j][k]);
					
					int platzvorwoche = Integer.parseInt(results[j][k].substring(results[j][k].indexOf("(") + 1, results[j][k].indexOf(")")));
					
					if (platzvorwoche == 0) {
						neueinsteiger[anzahlNeueinsteiger] = 10 * j + k;
						anzahlNeueinsteiger++;
					} else {
						indicesOfNewCharts[j * 10 + k - 1] = indicesOfOldCharts[platzvorwoche - 1];
					}
				}
				log();
			}
			reNewNeueinsteigerPnl();
		} catch (MalformedURLException murle) {
			murle.printStackTrace();
		}
	}
	
	private void reNewNeueinsteigerPnl() {
		if (neueinsteigerPnl != null)	neueinsteigerPnl.removeAll();
		
		int j, k;
		int numberOfNeueinsteigerPerPage = 15;
		int width = 440;
		{
			neueinsteigerPnl = new JPanel();
			chartsneu.add(neueinsteigerPnl);
			neueinsteigerPnl.setBounds(20, 60, width * ((anzahlNeueinsteiger - 1) / numberOfNeueinsteigerPerPage + 1), 500);
			neueinsteigerPnl.setLayout(null);
			neueinsteigerPnl.setBackground(Color.yellow);
		}
		neueinsteigerCHP = new JLabel[anzahlNeueinsteiger];
		neueinsteigerDaten = new JTextField[anzahlNeueinsteiger][2];
		neueinsteigerReEntry = new JButton[anzahlNeueinsteiger];
		for (int i = 0; i < anzahlNeueinsteiger; i++) {
			final int x = i;
			j = (neueinsteiger[i] - 1) / 10;
			k = (neueinsteiger[i] - 1) % 10 + 1;
			log(neueinsteiger[i] + ": " + j + ", " + k);
			neueinsteigerCHP[i] = new JLabel();
			neueinsteigerPnl.add(neueinsteigerCHP[i]);
			neueinsteigerCHP[i].setBounds(10 + width * (i / numberOfNeueinsteigerPerPage), 10 + 30 * (i % numberOfNeueinsteigerPerPage), 25, 25);
			neueinsteigerCHP[i].setText("" + (neueinsteiger[i] + 1));
			neueinsteigerCHP[i].setHorizontalAlignment(SwingConstants.CENTER);
			neueinsteigerCHP[i].setOpaque(true);
			
			neueinsteigerDaten[i][0] = new JTextField();
			neueinsteigerDaten[i][1] = new JTextField();
			neueinsteigerPnl.add(neueinsteigerDaten[i][0]);
			neueinsteigerPnl.add(neueinsteigerDaten[i][1]);
			neueinsteigerDaten[i][0].setBounds(40 + width * (i / numberOfNeueinsteigerPerPage), 10 + 30 * (i % numberOfNeueinsteigerPerPage), 170, 25);
			neueinsteigerDaten[i][1].setBounds(210 + width * (i / numberOfNeueinsteigerPerPage), 10 + 30 * (i % numberOfNeueinsteigerPerPage), 170, 25);
			neueinsteigerDaten[i][0].setText(results[j][k].substring(results[j][k].indexOf(")  ") + 3, results[j][k].indexOf("   ")));
			neueinsteigerDaten[i][1].setText(results[j][k].substring(results[j][k].indexOf("   ") + 3));
			
			neueinsteigerReEntry[i] = new JButton();
			neueinsteigerPnl.add(neueinsteigerReEntry[i]);
			neueinsteigerReEntry[i].setBounds(380 + width * (i / numberOfNeueinsteigerPerPage), 10 + 30 * (i % numberOfNeueinsteigerPerPage), 50, 25);
			neueinsteigerReEntry[i].setText("WE");
			neueinsteigerReEntry[i].setFocusable(false);
			neueinsteigerReEntry[i].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					searchForSimilarSong(x);
				}
			});
		}
		{
			neueinsteigerFertig = new JButton();
			neueinsteigerPnl.add(neueinsteigerFertig);
			neueinsteigerFertig.setBounds(REC_NEUFERTIG);
			neueinsteigerFertig.setText("Speichern");
			neueinsteigerFertig.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					saveNeueinsteiger();
				}
			});
		}
	}
	
	private String extractData(String element) {
		String extractedData = "";
		
		int platz = Integer.parseInt(element.substring(0, element.indexOf("</span>")));
		element = element.substring(element.indexOf("</span>") + 7);
		
		int platzvorwoche = Integer.parseInt(element.substring(element.indexOf("'>") + 2, element.indexOf("</span>")));
		element = element.substring(element.indexOf("</span></div></div>") + 19);
		
		String nameOfSong = element.substring(element.indexOf("<h3>") + 4, element.indexOf("</h3>"));
		String feat = "";
		if (nameOfSong.indexOf("&amp;") != -1)	nameOfSong = nameOfSong.replaceAll("&amp;", "&");
		if (nameOfSong.indexOf("(feat.") != -1) {
			feat = " " + nameOfSong.substring(nameOfSong.indexOf("(feat.") + 1, nameOfSong.lastIndexOf(")"));
			nameOfSong = nameOfSong.substring(0, nameOfSong.indexOf("(feat.") - 1);
		} else if (nameOfSong.indexOf("(Feat.") != -1) {
			feat = " f" + nameOfSong.substring(nameOfSong.indexOf("(Feat.") + 2, nameOfSong.lastIndexOf(")"));
			nameOfSong = nameOfSong.substring(0, nameOfSong.indexOf("(Feat.") - 1);
		} else if (nameOfSong.indexOf("(Ft.") != -1) {
			feat = " feat." + nameOfSong.substring(nameOfSong.indexOf("(Ft.") + 4, nameOfSong.lastIndexOf(")"));
			nameOfSong = nameOfSong.substring(0, nameOfSong.indexOf("(Ft.") - 1);
		} else if (nameOfSong.indexOf("(ft.") != -1) {
			feat = " feat." + nameOfSong.substring(nameOfSong.indexOf("(ft.") + 4, nameOfSong.lastIndexOf(")"));
			nameOfSong = nameOfSong.substring(0, nameOfSong.indexOf("(ft.") - 1);
		}
		if (nameOfSong.indexOf(" (Lyric") != -1) {
			nameOfSong = nameOfSong.substring(0, nameOfSong.indexOf(" (Lyric"));
		}
		element = element.substring(element.indexOf("</h3>") + 5);
		
		String nameOfArtist = element.substring(element.indexOf("'>") + 2, element.indexOf("</p>")) + feat;
		if (nameOfArtist.indexOf("&amp;") != -1)	nameOfArtist = nameOfArtist.replaceAll("&amp;", "&");
		if (nameOfArtist.indexOf("Feat.") != -1) {
			nameOfArtist = nameOfArtist.substring(0, nameOfArtist.indexOf("Feat.")) + "f" + nameOfArtist.substring(nameOfArtist.indexOf("Feat.") + 1);
		} else if (nameOfArtist.indexOf("Ft.") != -1) {
			nameOfArtist = nameOfArtist.substring(0, nameOfArtist.indexOf("Ft.")) + "feat." + nameOfArtist.substring(nameOfArtist.indexOf("F.") + 2);
		} else if (nameOfArtist.indexOf("ft.") != -1) {
			nameOfArtist = nameOfArtist.substring(0, nameOfArtist.indexOf("ft.")) + "feat." + nameOfArtist.substring(nameOfArtist.indexOf("ft.") + 3);
		}
		element = element.substring(element.indexOf("</p>") + 4);
		
		extractedData = platz + " (" + platzvorwoche + ")  " + nameOfSong + "   " + nameOfArtist;
		
		return extractedData;
	}
	
	private ArrayList<Integer> getIdenticalSongs(String nameOfArtist, String nameOfSong) {
		ArrayList<Integer> list = new ArrayList<>();
		
		for (Chartzeile chartzeile : chartZeilen) {
			if (chartzeile.getNameOfArtist().equalsIgnoreCase(nameOfArtist) && chartzeile.getNameOfSong().equalsIgnoreCase(nameOfSong)) {
				list.add(chartzeile.getID());
				log(" Identical song " + chartzeile.getNameOfArtist() + " - " + chartzeile.getNameOfSong());
			}
		}
		
		return list;
	}
	
	private ArrayList<Integer> getSimilarSongs(String nameOfArtist, String nameOfSong) {
		ArrayList<Integer> list = new ArrayList<>();
		
		for (Chartzeile chartzeile : chartZeilen) {
			if (chartzeile.getNameOfArtist().equalsIgnoreCase(nameOfArtist) != chartzeile.getNameOfSong().equalsIgnoreCase(nameOfSong)) {
				list.add(chartzeile.getID());
				log(" Similar song " + chartzeile.getNameOfArtist() + " - " + chartzeile.getNameOfSong());
			}
		}
		
		return list;
	}
	
	private void searchForSimilarSong(int index) {
		String nameOfSong = neueinsteigerDaten[index][0].getText();
		String nameOfArtist = neueinsteigerDaten[index][1].getText();
		ArrayList<Integer> indicesSimilar = new ArrayList<>();
		ArrayList<Integer> indicesIdentical = new ArrayList<>();
		boolean songIsNotNew = false;
		int indexOfIdenticalSong = -1;
		
		log("Searching for a similar song to   " + nameOfArtist + " - " + nameOfSong);
		
		indicesSimilar = getSimilarSongs(nameOfArtist, nameOfSong);
		indicesIdentical = getIdenticalSongs(nameOfArtist, nameOfSong);
		
		log("Found " + indicesIdentical.size() + " identical and " + indicesSimilar.size() + " similar song(s). \n");
		
		
		for (int i = 0; !songIsNotNew && i < indicesIdentical.size(); i++) {
			int isIdentical = JOptionPane.showConfirmDialog(null, "Is the new song " + nameOfArtist + " - " + nameOfSong + "\n"
					+ "identical to " + chartZeilen[indicesIdentical.get(i)].getNameOfArtist() + " - " + chartZeilen[indicesIdentical.get(i)].getNameOfSong());
			if (isIdentical == JOptionPane.YES_OPTION) {
				songIsNotNew = true;
				indexOfIdenticalSong = indicesIdentical.get(i);
			}
		}
		
		for (int i = 0; !songIsNotNew && i < indicesSimilar.size(); i++) {
			int isIdentical = JOptionPane.showConfirmDialog(null, "Is the new song " + nameOfArtist + " - " + nameOfSong + "\n"
					+ "identical to " + chartZeilen[indicesSimilar.get(i)].getNameOfArtist() + " - " + chartZeilen[indicesSimilar.get(i)].getNameOfSong());
			if (isIdentical == JOptionPane.YES_OPTION) {
				songIsNotNew = true;
				indexOfIdenticalSong = indicesSimilar.get(i);
			}
		}
		
		if (songIsNotNew) {
			JOptionPane.showMessageDialog(null, "You identified the song " + chartZeilen[indexOfIdenticalSong].getNameOfArtist() + " - "
					+ chartZeilen[indexOfIdenticalSong].getNameOfSong() + " as identical.");
			indicesOfNewCharts[neueinsteiger[index] - 1] = indexOfIdenticalSong;
			removeNeueinsteiger(index);
			reNewNeueinsteigerPnl();
		}
	}
	
	private void removeNeueinsteiger(int index) {
		anzahlNeueinsteiger--;
		int[] oldNeueinsteiger = neueinsteiger;
		neueinsteiger = new int[anzahlNeueinsteiger];
		
		for (int i = 0; i < index; i++) {
			neueinsteiger[i] = oldNeueinsteiger[i];
		}
		for (int i = index; i < neueinsteiger.length; i++) {
			neueinsteiger[i] = oldNeueinsteiger[i + 1];
		}
	}
	
	private void saveNeueinsteiger() {
		Chartzeile[] oldchz = chartZeilen;
		chartZeilen = new Chartzeile[chartZeilen.length + anzahlNeueinsteiger];
		
		for (int i = 0; i < oldchz.length; i++) {
			chartZeilen[i] = oldchz[i];
		}
		for (int i = 0; i < anzahlNeueinsteiger; i++) {
			chartZeilen[oldchz.length + i] = new Chartzeile(oldchz.length + i, this);
			chartZeilen[oldchz.length + i].setNameOfSong(neueinsteigerDaten[i][0].getText());
			chartZeilen[oldchz.length + i].setNameOfArtist(neueinsteigerDaten[i][1].getText());
			log(chartZeilen[oldchz.length + i].toString());
			// Uebergabe des Indexes
			indicesOfNewCharts[neueinsteiger[i] - 1] = oldchz.length + i;
		}
		
		neuersong.setVisible(false);
		Homescreen.setVisible(true);
		log("\n\nFertig mit den gelesenen Neueinsteigern.");
		testAusgabeDerAltenUndNeuenIndizes(false);
		
		
		// zum Charts-Panel hinzufuegen
		Dimension dim = new Dimension(chartsPanel.getPreferredSize().width, 
				chartsPanel.getPreferredSize().height + anzahlNeueinsteiger * Chartzeile.getPreferredHeight());
		chartsPanel.setPreferredSize(dim);
		for (int i = 0; i < anzahlNeueinsteiger; i++) {
			chartsPanel.add(chartZeilen[oldchz.length + i]);
			chartZeilen[chartZeilen.length - 1].setLocation(0, Chartzeile.getPreferredHeight() * (oldchz.length + i));
		}
		chartsScrP.setViewportView(chartsPanel);
		
		saveDataReadOrFromUserInputToChartzeilen();
	}
	
	public static String accessWebsite(URL url) {
		String result = "";
		try {
			URLConnection connection = url.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			
			String line;
			while ((line = in.readLine()) != null) {
				result = result + line;
			}
			
			return result;
		} catch (IOException exception) {
			log("Fehler: " + exception.getMessage());
		}
		
		return null;
	}
	
	private void chartzeilenSortieren(int week) {
		log("\n\nSortiere Woche " + week + "\n\n");
		Chartzeile[] temp = new Chartzeile[chartZeilen.length];
		int counterForUncharted = 0;
		for (Chartzeile cz : chartZeilen) {
			int rnk = cz.getRankOfWeek(week);
			
			if (rnk == 200) {
				temp[100 + counterForUncharted] = cz;
				counterForUncharted++;
			} else if (rnk >= 1 && rnk <= 100) {
				temp[rnk - 1] = cz;
			}
		}
		
		chartsPanel.setPreferredSize(new Dimension(Chartzeile.getPreferredWidth(), 0));
		chartsPanel.removeAll();
		for (int i = 0; i < temp.length && i < 100; i++) {
			chartsPanel.add(temp[i]);
			Dimension dim = new Dimension(	chartsPanel.getPreferredSize().width,
											chartsPanel.getPreferredSize().height + temp[i].getHeight());
			chartsPanel.setPreferredSize(dim);
			temp[i].setLocation(0, temp[i].getHeight() * i);
		}

		chartsScrP.setViewportView(chartsPanel);
	}
	
	public void jBtnChartsAnzeigenActionPerformed() {
		Homescreen.setVisible(false);
		chartsAnzeigen.setVisible(true);
		chartsAnzeigen.add(jBtnZurueck);
		jBtnZurueck.setVisible(true);
		jCBChartsOfWeek.setSelectedIndex(currentWeek);
	}
	
	public void jCBChartsOfWeekItemStateChanged(ItemEvent evt) {
		if (evt.getStateChange() == ItemEvent.SELECTED) {
			int week = jCBChartsOfWeek.getSelectedIndex();
			
			if (week < 0) {
				jCBChartsOfWeek.setSelectedIndex(0);
				return;
			}
			if (week >= anzahl_wochen) {
				jCBChartsOfWeek.setSelectedIndex(anzahl_wochen - 1);
			}
			
			
			try {
				showChartsOfWeek(week);
			} catch (Exception e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "It was impossible to view the charts for this week(" + week + ").");
				if (week > 0) {
					jCBChartsOfWeek.setSelectedIndex(week - 1);
				}
			}
		}
	}
	
	public void showChartsOfWeek(int week) {
		for (int i = 0; i < chartZeilen.length; i++) {
			chartZeilen[i].updateGUI(week);
		}
		
		chartzeilenSortieren(week);
	}

	public void jBtnChartsErneuernActionPerformed() {
		editedWeek = getNextWeek();
		
		Homescreen.setVisible(false);
		jBtnZurueck.setVisible(true);

		chartsneu.setVisible(true);
		jLblWoche.setText("Charts vom " + datum(myDateVerschoben(startdate, editedWeek * 7)));

		boolean automatic = true;
		if (automatic) {
			getChartsFromWebsite();
		} else {
			jTFAlterChartplatz.setVisible(true);
			jLblChartplatz.setVisible(true);
			
			jTFAlterChartplatz.requestFocus();
			jLblChartplatz.setText("" + aktuellerChartplatz);
			prepareForUserInput();
		}
	}
	
	private void prepareForUserInput() {
		aktuellerChartplatz = 1;
		anzahlNeueinsteiger = 0;
		reinitializeIndexArrays();

		fillIndicesOfOldCharts(false);

		testAusgabeDerAltenUndNeuenIndizes(false);
		testAusgabeDerUnchartedSongs(false);
	}
	
	private void reinitializeIndexArrays() {
		// zwei arrays: ids der songs der top 100 der letzten woche, ids aller anderen songs
		indicesOfOldCharts = new int[100];
		indicesofnotchartedsongs = new int[this.anzahlLieder - 100];
		// drittes array: neue top 100, inhalt: id des songs auf platz i
		indicesOfNewCharts = new int[100];
		checkliste = new int[100];
		for (int i = 0; i < 100; i++) {
			indicesOfOldCharts[i] = -1;
			indicesOfNewCharts[i] = -1;
			checkliste[i] = 1;
		}
	}

	public void jBtnBeendenActionPerformed() {
		datenSchreiben();

		writeConfiguration();

		System.exit(0);
	}

	public void neuerChartplatzKeyTyped(KeyEvent arg0) {
		int i = arg0.getKeyChar();

		if (i == 10) {
			jBtnWeiterActionPerformed();
		} else if (i >= 48 && i <= 57) {
			int platz = -1;
			try {
				// Anzeige des passenden Eintrags aus den Vorwoche-Charts
				platz = Integer.parseInt(jTFAlterChartplatz.getText() + arg0.getKeyChar());
				String daten = chartZeilen[indicesOfOldCharts[platz - 1]].toString();
				String songname = daten.substring(daten.indexOf(";SONG*") + 6, daten.indexOf(";INTERPR*"));
				String interpretenname = daten.substring(daten.indexOf(";INTERPR*") + 9, daten.indexOf(";FCW*"));
				previewCHZ.setText(songname + " - " + interpretenname);
			} catch (ArrayIndexOutOfBoundsException aioobe) {
//				aioobe.printStackTrace();
				if (platz == 0) {
					previewCHZ.setText("Neueinsteiger");
				} else if (platz == 200) {
					previewCHZ.setText("Wiedereinsteiger");
				} else {
					previewCHZ.setText("Fehler - n.a.");
				}
			}
		} else {
			arg0.consume();
		}

	}

	public void jBtnWeiterActionPerformed() {
		try {
			int oldCHP = Integer.parseInt(jTFAlterChartplatz.getText());
			if (checkliste[oldCHP - 1] == 1) { // wirft die Neu- und Wiedereinsteiger raus
				checkliste[oldCHP - 1] = 0;

				testAusgabeVeraenderungDerChartposition(oldCHP);

				// Uebergabe des Indexes
				indicesOfNewCharts[aktuellerChartplatz - 1] = indicesOfOldCharts[oldCHP - 1];

				// wenn das das Ende der Charts ist, zurueck zum Homescreen
				nextPlaceInCharts();
				previewCHZ.setText("");
			} else {
				JOptionPane.showMessageDialog(null, "Die angegebene Chartplatzierung nahm in der Vorwoche bereits ein anderer Song ein.", 
						"Bereits vergeben", JOptionPane.ERROR_MESSAGE);
				jTFAlterChartplatz.requestFocus();
				jTFAlterChartplatz.selectAll();
			}

		} catch (ArrayIndexOutOfBoundsException aioobe) {
			int index = Integer.parseInt(jTFAlterChartplatz.getText());
			if (index == 0) {
				// Speicherung als Neueinsteiger
				anzahlNeueinsteiger++;
				int[] old = neueinsteiger;
				neueinsteiger = new int[anzahlNeueinsteiger];
				for (int i = 0; i < old.length; i++) {
					neueinsteiger[i] = old[i];
					logWONL(" neueinsteiger[" + i + "] == " + neueinsteiger[i]);
				}
				neueinsteiger[anzahlNeueinsteiger - 1] = aktuellerChartplatz;
				log(" neueinsteiger[" + (anzahlNeueinsteiger - 1) + "] == " + neueinsteiger[anzahlNeueinsteiger - 1]);

				nextPlaceInCharts();
			} else if (index == 200) {
				// Speicherung als Wiedereinsteiger TODO!
				anzahlNeueinsteiger++;
				int[] old = neueinsteiger;
				neueinsteiger = new int[anzahlNeueinsteiger];
				for (int i = 0; i < old.length; i++) {
					neueinsteiger[i] = old[i];
					logWONL(" neueinsteiger[" + i + "] == " + neueinsteiger[i]);
				}
				neueinsteiger[anzahlNeueinsteiger - 1] = aktuellerChartplatz;

				nextPlaceInCharts();
			} else {
				JOptionPane.showMessageDialog(null, "Die eingebene Zahl ist ausserhalb der Grenzen (1 - 100 oder 0 fuer neu). Bitte erneut versuchen", 
						"Falsche Eingabe", JOptionPane.ERROR_MESSAGE);
				aioobe.printStackTrace();
				jTFAlterChartplatz.requestFocus();
				jTFAlterChartplatz.selectAll();
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Der eingegebene String ist keine Zahl. Bitte erneut versuchen",
					"Falsche Eingabe", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			jTFAlterChartplatz.requestFocus();
			jTFAlterChartplatz.selectAll();
		}
	}

	public void nextPlaceInCharts() {
		if (aktuellerChartplatz < 100) {
			aktuellerChartplatz++;
			jLblChartplatz.setText("" + aktuellerChartplatz);
			jTFAlterChartplatz.setText("");
			jTFAlterChartplatz.requestFocus();
			previewCHZ.setText("");
		} else {
			chartsneu.setVisible(false);
//			JOptionPane.showMessageDialog(null, "Fertig mit dem ersten Teil.");
			prepareForNextNeueinsteiger();
			log("\n\nFertig mit dem ersten Teil.");
			testAusgabeDerAltenUndNeuenIndizes(false);
		}
	}

	private void jBtnNeueinsteigerWeiterActionPerformed() {
		if (savesong) {
			savesong = !savesong;
			prepareForNextNeueinsteiger();
		} else {
			if (addSong()) {
				savesong = !savesong;
				anzahlNeueinsteiger--;
				jBtnNeueinsteigerWeiterActionPerformed();
			}
		}
	}

	public void prepareForNextNeueinsteiger() {
//		JOptionPane.showMessageDialog(null, "neueinsteiger() wurde aufgerufen mit anzahl_neueinsteiger == " + anzahl_neueinsteiger);
		if (anzahlNeueinsteiger > 0) {
			neuersong.setVisible(true);
			this.jTFNameOfSong.setText("");
			this.jTFNameOfArtist.setText("");
			this.jLblPlatzNeueinsteiger.setText("" + neueinsteiger[neueinsteiger.length - anzahlNeueinsteiger]);
			this.jTFNameOfSong.requestFocus();
		} else {
			neuersong.setVisible(false);
			chartsneu.setVisible(false);
			Homescreen.setVisible(true);
			log("\n\nFertig mit den Neueinsteigern.");
			testAusgabeDerAltenUndNeuenIndizes(false);
			
			saveDataReadOrFromUserInputToChartzeilen();
		}
	}
	
	private void saveDataReadOrFromUserInputToChartzeilen() {
		for (int i = 0; i < 100; i++) {
			chartZeilen[indicesOfNewCharts[i]].setRankInWeek(i + 1, editedWeek);
		}
		
		for (int i = 0; i < chartZeilen.length; i++) {
			if (chartZeilen[i].getRankOfWeek(editedWeek) == 0) {
				chartZeilen[i].setUnchartedInWeek(editedWeek);
			}
			chartZeilen[i].validateHighRank();
			chartZeilen[i].aktualisierenDerFCWundDerLCW();
		}
	}
	
	public boolean addSong() {
		String nameOfSong = this.jTFNameOfSong.getText();
		String nameOfArtist = this.jTFNameOfArtist.getText();

		if (nameOfSong != null && nameOfArtist != null && !nameOfSong.isEmpty() && !nameOfArtist.isEmpty()) {
			ArrayList<Integer> identicalSongs = getIdenticalSongs(nameOfArtist, nameOfSong);
			ArrayList<Integer> similarSongs = getSimilarSongs(nameOfArtist, nameOfSong);
			
			if (identicalSongs.size() > 0 || similarSongs.size() > 0) {
				int indexOfIdenticalSong = -1;
				for (int i = 0; indexOfIdenticalSong == -1 && i < identicalSongs.size(); i++) {
					int isIdentical = JOptionPane.showConfirmDialog(null, "Is the new song " + nameOfArtist + " - " + nameOfSong + "\n"
							+ "identical to " + chartZeilen[identicalSongs.get(i)].getNameOfArtist() + " - " + chartZeilen[identicalSongs.get(i)].getNameOfSong());
					if (isIdentical == JOptionPane.YES_OPTION) {
						indexOfIdenticalSong = identicalSongs.get(i);
					}
				}
				
				for (int i = 0; indexOfIdenticalSong == -1 && i < similarSongs.size(); i++) {
					int isIdentical = JOptionPane.showConfirmDialog(null, "Is the new song " + nameOfArtist + " - " + nameOfSong + "\n"
							+ "identical to " + chartZeilen[similarSongs.get(i)].getNameOfArtist() + " - " + chartZeilen[similarSongs.get(i)].getNameOfSong());
					if (isIdentical == JOptionPane.YES_OPTION) {
						indexOfIdenticalSong = similarSongs.get(i);
					}
				}
				if (indexOfIdenticalSong != -1) {
					indicesOfNewCharts[neueinsteiger[neueinsteiger.length - anzahlNeueinsteiger] - 1] = indexOfIdenticalSong;
					return true;
				}
			}
			
			Chartzeile[] oldchz = chartZeilen;
			chartZeilen = new Chartzeile[chartZeilen.length + 1];

			for (int i = 0; i < oldchz.length; i++) {
				chartZeilen[i] = oldchz[i];
			}
			chartZeilen[chartZeilen.length - 1] = new Chartzeile(chartZeilen.length - 1, this);
			chartZeilen[chartZeilen.length - 1].setNameOfSong(nameOfSong);
			chartZeilen[chartZeilen.length - 1].setNameOfArtist(nameOfArtist);
			log(chartZeilen[chartZeilen.length - 1].toString());

			// Uebergabe des Indexes
			indicesOfNewCharts[neueinsteiger[neueinsteiger.length - anzahlNeueinsteiger] - 1] = chartZeilen.length - 1;

			// zum Charts-Panel hinzufuegen
			Dimension dim = new Dimension(chartsPanel.getPreferredSize().width, 
					chartsPanel.getPreferredSize().height + chartZeilen[chartZeilen.length - 1].getHeight());
			chartsPanel.setPreferredSize(dim);
			chartsPanel.add(chartZeilen[chartZeilen.length - 1]);
			chartZeilen[chartZeilen.length - 1].setLocation(0, chartZeilen[chartZeilen.length - 1].getHeight() * (chartZeilen.length - 1));
			chartsScrP.setViewportView(chartsPanel);

			return true;
		}
		JOptionPane.showMessageDialog(null, "Falsche Eingabe", "Fehler", JOptionPane.ERROR_MESSAGE);
		return false;
	}

	// creates a String out of a date integer
	public static String datum(int myformat) {
		int d1 = (myformat % 100) / 10;
		int d2 = myformat % 10;
		int mo1 = (myformat % 10000) / 1000;
		int mo2 = ((myformat % 10000) / 100) % 10;
		int y = myformat / 10000;

		return d1 + "" + d2 + "." + mo1 + "" + mo2 + "." + y;
	}

	/** This method returns the date of the current day.
	 * 
	 * @return an Integer representing the date of the current day
	 */
	public static int newMyDate() {
		int datum = 0;
		Calendar gc = new GregorianCalendar();
		int dd = gc.get(Calendar.DAY_OF_MONTH);
		int mm = gc.get(Calendar.MONTH) + 1;
		int yyyy = gc.get(Calendar.YEAR);

		datum = 10000 * yyyy + 100 * mm + dd;

		return datum;
	}

	// adds the indicated amount of days to an indicated date
	public static int myDateVerschoben(int startdatum, int tageplus) {
		int dd = startdatum % 100;
		int mm = (startdatum % 10000) / 100;
		int yyyy = startdatum / 10000;

		GregorianCalendar greg = new GregorianCalendar(yyyy, mm - 1, dd);
		greg.add(Calendar.DAY_OF_YEAR, tageplus);

		int datum = 0;

		dd = greg.get(Calendar.DAY_OF_MONTH);
		mm = greg.get(Calendar.MONTH) + 1;
		yyyy = greg.get(Calendar.YEAR);

		datum = 10000 * yyyy + 100 * mm + dd;

		return datum;
	}

	public int getCurrentWeek() {
		return currentWeek;
	}

	public static int determineCurrentWeek() {
		int week = -1;
		int today = newMyDate();

		if (today < datum[0]) {
			week = 0;
		} else if (today >= datum[anzahl_wochen - 1]) {
			week = anzahl_wochen - 1;
		} else {
			week = 0;
			while (today >= datum[week + 1]) {
				week++;
			}
		}
		if (today == datum[week]) {
			if ((new GregorianCalendar()).get(Calendar.HOUR_OF_DAY) < 12) {
				week--;
			}
		}

		return week;
	}
	
	public int getNextWeek() {
		int index;
		for (index = 0; index < anzahl_wochen; index++) {
			if (chartZeilen[0].getRankOfWeek(index) == 0) {
				break;
			}
		}
		return index;
	}
	
	public static int getAnzahlWochen() {
		return anzahl_wochen;
	}
	
	private void datenLaden() {
		daten = workspace + File.separator + "daten.txt";
		ausDatei(daten, jListDatenModel);
		anzahlLieder = jListDatenModel.getSize();
		chartZeilen = new Chartzeile[anzahlLieder];
		for (int i = 0; i < chartZeilen.length; i++) {
			chartZeilen[i] = new Chartzeile((String) jListDatenModel.get(i), this);
		}
	}

	public void datenSchreiben() {
		jListDatenModel.clear();
		for (int i = 0; i < chartZeilen.length; i++) {
			jListDatenModel.addElement(chartZeilen[i].toString());
		}

		inDatei(daten, jListDatenModel);
	}

	// Aus und in Dateien lesen/schreiben
	public void ausDatei(String dateiname, DefaultListModel model) {
		try {
			File datei = new File(dateiname);
			BufferedReader in = null;
			if (!datei.exists()) {
				datei.createNewFile();
				log(" >>> File did not exist but was created!");
			} else {
				String element;
				try {
					in = new BufferedReader(new InputStreamReader(new FileInputStream(dateiname), "UTF-8"));
					model.clear();
					while ((element = in.readLine()) != null && !element.isEmpty()) {
						model.addElement(element);
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
			log("Falscher Pfad, nicht vorhanden auf diesem System!");
			e.printStackTrace();
		}
	}

	private void inDatei(String dateiname, DefaultListModel model) {
		try {
			BufferedWriter out = null;
			try {
				out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(dateiname), "UTF-8"));
				for (int i = 0; i < model.getSize(); i++) {
					out.write(model.get(i).toString());
					out.newLine();
				}
			} catch (Exception e) {
				e.printStackTrace();
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
	
	private void loadConfiguration() {
		config = workspace + File.separator + "config.txt";
		ausDatei(config, jListConfigModel);

//		int counter = 0; TODO

		startdate = 20140103;
		anzahl_wochen = 1 + difference(startdate, newMyDate()) / 7;
		datum = new int[anzahl_wochen];
		for (int i = 0; i < anzahl_wochen; i++) {
			datum[i] = myDateVerschoben(startdate, i * 7);
			log((i + 1) + ". Woche: " + datum[i] + " oder " + datum(datum[i]));
		}
	}

	private void writeConfiguration() {
//		int counter = 0; TODO

		inDatei(config, jListConfigModel);
	}

	private void checkOS() {
		if (new File(workspaceWIN).isDirectory()) {
			// JOptionPane.showMessageDialog(null, "You are running Windows.");
			workspace = workspaceWIN;
		} else if (new File(workspaceMAC).isDirectory()) {
			// JOptionPane.showMessageDialog(null, "You have a Mac.");
			workspace = workspaceMAC;
		} else {
			// JOptionPane.showMessageDialog(null, "You are running neither OS X nor Windows, probably Linux!");
			workspace = null;
		}
	}
	
	public double zeitpunkt() {
		double zeit = 0;
		Calendar greg = new GregorianCalendar();

		zeit = (double) greg.getTimeInMillis() / 1000;

		return zeit;
	}
}

