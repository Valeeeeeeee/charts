package code;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

import static util.Utilities.*;

public class Chartzeile extends JPanel {
	private static final long serialVersionUID = 2077455761527352594L;
	
	private int id;
	private Start start;
	
	private String nameofsong = "Default aus der Klasse";
	private String nameofinterpreter = "Def aus der Klasse";
	private int[] all_ranks;
	private int highest_rank = -1;
	private int first_charted_week = -1;
	private int latest_charted_week = -1;
	private int countWeeksCharted = 0;
	
	private JLabel labelSONG;
	private JLabel labelINTERPRETER;
	private JLabel labelTHISWEEK;
	private JLabel labelLASTWEEK;
	private JLabel labelFIRSTCHARTEDWEEK;
	
	private static final Dimension preferreddim = new Dimension(630, 40);
	private Font defaultFont = new Font("Dialog", 0, 12);
	
	public Chartzeile(int id, Start start) {
		super();
		this.id = id;
		this.start = start;
		all_ranks = new int[Start.getAnzahlWochen()];
		initGUI();
	}
	
	public Chartzeile(String daten, Start start) {
		super();
		this.start = start;
		all_ranks = new int[Start.getAnzahlWochen()];
		fromString(daten);
		initGUI();
	}
	
	public void initGUI() {
		this.setLayout(null);
		
		
		setFont(defaultFont);
		{
			labelSONG = new JLabel();
			this.add(labelSONG);
			labelSONG.setBounds(10, 10, 200, 20);
			labelSONG.setFont(defaultFont);
//			labelSONG.setOpaque(true);
		}
		{
			labelINTERPRETER = new JLabel();
			this.add(labelINTERPRETER);
			labelINTERPRETER.setBounds(220, 10, 200, 20);
//			labelINTERPRETER.setOpaque(true);
		}
		{
			labelLASTWEEK = new JLabel();
			this.add(labelLASTWEEK);
			labelLASTWEEK.setBounds(520, 10, 25, 20);
			labelLASTWEEK.setHorizontalAlignment(SwingConstants.CENTER);
		}
		{
			labelTHISWEEK = new JLabel();
			this.add(labelTHISWEEK);
			labelTHISWEEK.setBounds(550, 10, 25, 20);
			labelTHISWEEK.setHorizontalAlignment(SwingConstants.CENTER);
		}
		{
			labelFIRSTCHARTEDWEEK = new JLabel();
			this.add(labelFIRSTCHARTEDWEEK);
			labelFIRSTCHARTEDWEEK.setBounds(430, 10, 80, 20);
			labelFIRSTCHARTEDWEEK.setHorizontalAlignment(SwingConstants.CENTER);
//			labelFIRSTCHARTEDWEEK.setOpaque(true);
		}
		updateGUI(start.getCurrentWeek());
		
		this.setCursor(new Cursor(Cursor.HAND_CURSOR));
		this.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				pressed();
			}
		});
		
		this.setSize(preferreddim);
	}
	
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.setColor(new Color(255, 255, 70));
		g.fillRect(0, 0, getWidth(), getHeight());
		
		
		g.setColor(Color.black);
		g.drawLine(0, getHeight() - 1, getWidth(), getHeight() - 1);
	}
	
	public int getID() {
		return this.id;
	}
	
	public String getNameOfSong() {
		return this.nameofsong;
	}
	
	public void setNameOfSong(String song) {
		this.nameofsong = song;
		this.labelSONG.setText(this.nameofsong);
	}
	
	public String getNameOfArtist() {
		return this.nameofinterpreter;
	}
	
	public void setNameOfArtist(String interpreter) {
		this.nameofinterpreter = interpreter;
		this.labelINTERPRETER.setText(this.nameofinterpreter);
	}
	
	private void pressed() {
		start.showOverviewOf(id);
	}
	
	public void aktualisierenDerFCWundDerLCW() {
		this.first_charted_week = -1;
		this.latest_charted_week = -1;
		for (int i = 0; i < all_ranks.length && all_ranks[i] > 0; i++) {
			if (all_ranks[i] <= 100) {
				this.first_charted_week = i;
				break;
			}
		}
		for (int i = 0; i < all_ranks.length && all_ranks[i] > 0; i++) {
			if (all_ranks[i] <= 100) {
				this.latest_charted_week = i;
			}
		}
	}
	
	public void setRankInWeek(int rank, int week) {
		if (week >= 0 && week < Start.getAnzahlWochen()) {
			if (rank > 0 && rank <= 100) {
				if (this.first_charted_week == -1) {
					this.first_charted_week = week;
				}
				
				for (int i = (this.latest_charted_week + 1); i < week; i++) {
					this.all_ranks[i] = 200;
				}
				this.all_ranks[week] = rank;
				countWeeksCharted++;
				
				validateHighRank();
				
				this.latest_charted_week = week;
			} else if (rank == 200) {
				setUnchartedInWeek(week);
			}
			updateGUI(start.getCurrentWeek());
		}
	}
	
	public String getPublicationDate() {
		return Start.datum(Start.myDateVerschoben(Start.startdate, 7 * first_charted_week));
	}
	
	public void updateGUI(int week) {
		try {
//			System.out.println("This is updateGUI() with " + week);
			labelSONG.setText(nameofsong);
			labelINTERPRETER.setText(nameofinterpreter);
			labelINTERPRETER.setToolTipText(nameofinterpreter);
			labelFIRSTCHARTEDWEEK.setText(getPublicationDate());
			labelTHISWEEK.setText("" + all_ranks[week]);
			labelLASTWEEK.setText("" + all_ranks[week - 1]);
			
			if (all_ranks[week - 1] == 200) {
				labelLASTWEEK.setOpaque(true);
				labelTHISWEEK.setOpaque(true);
				labelLASTWEEK.setForeground(Color.blue);
				labelTHISWEEK.setForeground(Color.blue);
				labelLASTWEEK.setBackground(Color.red);
				if (all_ranks[week] != 200) {
					labelTHISWEEK.setBackground(Color.green);
				} else {
					labelTHISWEEK.setBackground(Color.red);
				}
				
			} else {
				labelLASTWEEK.setOpaque(false);
				labelTHISWEEK.setOpaque(false);
				if (all_ranks[week] < all_ranks[week - 1]) {
					labelLASTWEEK.setForeground(Color.red);
					labelTHISWEEK.setForeground(Color.green);
				} else if (all_ranks[week] > all_ranks[week - 1]) {
					labelLASTWEEK.setForeground(Color.green);
					labelTHISWEEK.setForeground(Color.red);
				} else {
					labelLASTWEEK.setForeground(Color.blue);
					labelTHISWEEK.setForeground(Color.blue);
				}
			}
			
//			System.out.println("UI was updated.");
		} catch(NullPointerException npe) {
//			System.out.println("UI was not updated due to a NullPointerException.");
		} catch(ArrayIndexOutOfBoundsException aioobe) {
//			System.out.println("UI was not updated due to an ArrayIndexOutOfBoundsException.");
			labelLASTWEEK.setText("XX");
		}
		
	}
	
	public void setUnchartedInWeek(int week) {
		this.all_ranks[week] = 200;
	}
	
	public void validateHighRank() {
		int min = 200;
		for (int i = 0; i < this.all_ranks.length; i++) {
			if (this.all_ranks[i] < min && this.all_ranks[i] != 0) {
				min = this.all_ranks[i];
			}
		}
		if (min == 200) {
			this.highest_rank = -1;
		} else {
			this.highest_rank = min;
		}
	}
	
	public int getRankOfWeek(int week) {
		return this.all_ranks[week];
	}
	
	public int getHighestRank() {
		return this.highest_rank;
	}
	
	public int getFirstChartedWeek() {
		return this.first_charted_week;
	}
	
	public int getCountWeeksCharted() {
		return this.countWeeksCharted;
	}
	
	public static int getPreferredWidth() {
		return preferreddim.width;
	}
	
	public static int getPreferredHeight() {
		return preferreddim.height;
	}
	
	public String toString() {
		String alledaten = "ID*" + this.id + ";";
		alledaten = alledaten + "SONG*" + this.nameofsong + ";";
		alledaten = alledaten + "INTERPR*" + this.nameofinterpreter + ";";
		alledaten = alledaten + "FCW*" + this.first_charted_week + ";";
		alledaten = alledaten + "LCW*" + this.latest_charted_week + ";";
		alledaten = alledaten + "HR*" + this.highest_rank + ";";
		for (int i = 0; i < Start.getAnzahlWochen(); i++) {
			if (all_ranks[i] > 0 && all_ranks[i] < 200) {
				alledaten = alledaten + "RW" + i + "*" + this.all_ranks[i] + ";";
			}
		}
		alledaten = alledaten + "***";
		return alledaten;
	}
	
	private void fromString(String daten) {
		this.id = Integer.parseInt(daten.substring(daten.indexOf("ID*") + 3, daten.indexOf(";SONG")));
		this.nameofsong = daten.substring(daten.indexOf("SONG*") + 5, daten.indexOf(";INTERPR"));
		this.nameofinterpreter = daten.substring(daten.indexOf("INTERPR*") + 8, daten.indexOf(";FCW"));
		this.first_charted_week = Integer.parseInt(daten.substring(daten.indexOf("FCW*") + 4, daten.indexOf(";LCW")));
		this.latest_charted_week = Integer.parseInt(daten.substring(daten.indexOf("LCW*") + 4, daten.indexOf(";HR")));
		this.highest_rank = Integer.parseInt(daten.substring(daten.indexOf("HR*") + 3, daten.indexOf(";RW")));
		String[] ranks = daten.substring(daten.indexOf(";RW") + 1).split(";");
		for (int week = 0; week < Start.getAnzahlWochen(); week++) {
			this.all_ranks[week] = 200;
		}
		for (String string : ranks) {
			if (!string.equals("***")) {
				String[] strings = string.replaceAll("R|W", "").split("\\*");
				int week = Integer.parseInt(strings[0]);
				this.all_ranks[week] = Integer.parseInt(strings[1]);
				if (this.all_ranks[week] > 0 && this.all_ranks[week] < 200)		countWeeksCharted++;
			}
		}
		
		validateHighRank();
		aktualisierenDerFCWundDerLCW();
	}
}

