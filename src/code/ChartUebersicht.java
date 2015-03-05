package code;
import java.awt.*;
import javax.swing.*;

public class ChartUebersicht extends JPanel {
	private static final long serialVersionUID = -1417841593201970078L;
	
	private int firstWeek = Start.getAnzahlWochen() - 56;
	private int numberOfWeeks = 56;
	
	private Dimension stdDim = new Dimension(50 + numberOfWeeks * 10, 620);
	private Chartzeile chZ;
	
	private int week, place, lastHeight;
	private int offsetChartX = 30;
	private int offsetChartY = 20;
	private int pixelsPerPosition = 4;
	
	private int xxx = 30;
	private int yyy = 520;
	
	private Rectangle RECDESSONG = new Rectangle(xxx + 0, yyy + 0, 40, 30);
	private Rectangle RECSONG = new Rectangle(xxx + 110, yyy + 0, 200, 30);
	private Rectangle RECDESINT = new Rectangle(xxx + 0, yyy + 30, 65, 30);
	private Rectangle RECINT = new Rectangle(xxx + 110, yyy + 30, 200, 30);
	private Rectangle RECDESPUB = new Rectangle(xxx + 0, yyy + 60, 100, 30);
	private Rectangle RECPUB = new Rectangle(xxx + 110, yyy + 60, 75, 30);
	private Rectangle RECDESHR = new Rectangle(xxx + 340, yyy + 0, 120, 30);
	private Rectangle RECHR = new Rectangle(xxx + 340, yyy + 30, 60, 60);
	
	private JLabel lblDescrNameSong;
	private JLabel lblNameSong;
	private JLabel lblDescrNameInterpreter;
	private JLabel lblNameInterpreter;
	private JLabel lblDescrPublicationDate;
	private JLabel lblPublicationDate;
	private JLabel lblDescrHighestRank;
	private JLabel lblHighestRank;
	
	public ChartUebersicht(Chartzeile chZ) {
		super();
		this.chZ = chZ;
		initGUI();
	}
	
	public void initGUI() {
		this.setLayout(null);
		
		{
			lblDescrNameSong = new JLabel();
			this.add(lblDescrNameSong);
			lblDescrNameSong.setBounds(RECDESSONG);
			lblDescrNameSong.setText("Song: ");
		}
		{
			lblNameSong = new JLabel();
			this.add(lblNameSong);
			lblNameSong.setBounds(RECSONG);
			lblNameSong.setText(chZ.getNameOfSong());
		}
		{
			lblDescrNameInterpreter = new JLabel();
			this.add(lblDescrNameInterpreter);
			lblDescrNameInterpreter.setBounds(RECDESINT);
			lblDescrNameInterpreter.setText("Interpret: ");
		}
		{
			lblNameInterpreter = new JLabel();
			this.add(lblNameInterpreter);
			lblNameInterpreter.setBounds(RECINT);
			lblNameInterpreter.setText(chZ.getNameOfArtist());
		}
		{
			lblDescrPublicationDate = new JLabel();
			this.add(lblDescrPublicationDate);
			lblDescrPublicationDate.setBounds(RECDESPUB);
			lblDescrPublicationDate.setText("erschienen am: ");
		}
		{
			lblPublicationDate = new JLabel();
			this.add(lblPublicationDate);
			lblPublicationDate.setBounds(RECPUB);
			lblPublicationDate.setText(chZ.getPublicationDate());
		}
		{
			lblDescrHighestRank = new JLabel();
			this.add(lblDescrHighestRank);
			lblDescrHighestRank.setBounds(RECDESHR);
			lblDescrHighestRank.setText("beste Platzierung: ");
		}
		{
			lblHighestRank = new JLabel();
			this.add(lblHighestRank);
			lblHighestRank.setBounds(RECHR);
			lblHighestRank.setText("" + chZ.getHighestRank());
			lblHighestRank.setHorizontalAlignment(SwingConstants.CENTER);
			lblHighestRank.setVerticalAlignment(SwingConstants.CENTER);
			lblHighestRank.setFont(getFont().deriveFont(36.0f));
			lblHighestRank.setOpaque(true);
			lblHighestRank.setBackground(Color.green);
		}
		
		this.setSize(stdDim);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.setColor(Color.white);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		
		int width = 100 * pixelsPerPosition + 80;

		g.setColor(Color.black);
		g.drawLine(offsetChartX, offsetChartY, offsetChartX + 10 * (numberOfWeeks + 1), offsetChartY);
		g.drawLine(offsetChartX, offsetChartY, offsetChartX, offsetChartY + width);
		g.drawLine(offsetChartX, offsetChartY + width, offsetChartX + 10 * (numberOfWeeks + 1), offsetChartY + width);
		g.drawLine(offsetChartX + 10 * (numberOfWeeks + 1), offsetChartY, offsetChartX + 10 * (numberOfWeeks + 1), offsetChartY + width);

		g.setColor(Color.black);
		for (int i = 0; i < 11; i++) {
			g.drawLine(offsetChartX - 5, offsetChartY + i * 10 * pixelsPerPosition, offsetChartX + 10 * (numberOfWeeks + 1) + 5, offsetChartY + i * 10 * pixelsPerPosition);
		}
		
		g.setColor(Color.black);
		for (week = 0; week < numberOfWeeks; week++) {
			place = this.chZ.getRankOfWeek(firstWeek + week);
			int height = -1;
			if (place == 0) {
				
			} else if (place == 200) {
				height = offsetChartY + width - 10;
			} else {
				height = offsetChartY + pixelsPerPosition * place;
			}
			
			if (height != -1) {
				g.setColor(Color.blue);
				g.drawRect(offsetChartX + 10 + week * 10 - 1, height - 1, 2, 2);
				g.setColor(Color.black);
				if (week > 0)	g.drawLine(offsetChartX + week * 10, lastHeight, offsetChartX + (week + 1) * 10, height);
			}
			
			lastHeight = height;
		}
	}
}

