package chart;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.chart.renderer.xy.XYStepRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import table.TableModelChart;

/**
 * Classe LineChartPane
 *
 */
public class LineChartPane extends JSplitPane implements ActionListener, Observer{

	private static final long serialVersionUID = 1L;

	private TableModelChart tableModel;
	private JTable table;
	private JFreeChart chart;
	public String Line_Renderer_KEY="Ligne";
	public String Step_Rendeerer_KEY="Echelon";
	public String Spline_Renderer_KEY="Spline";
	private XYSeriesCollection dataset;

	/**
	 * Constructeur de LineChartPane
	 * @param titre le titre des courbes
	 * @param legende la légende des courbes
	 */
	public LineChartPane(String titre, String legende) {
		// ---------------------
		//  tableau
		// ---------------------
		tableModel = new TableModelChart();
		table = new JTable(tableModel);
		table.getTableHeader().setReorderingAllowed(false);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBorder(null);

		// ---------------------
		//  graphique
		// ---------------------

		// Add the series to your data set
		dataset = new XYSeriesCollection();

		// Generate the graph
		chart = ChartFactory.createXYLineChart(
				titre, // Title
				"Temps" + ": t", // x-axis Label
				"Value"+ "(t)", // y-axis Label
				dataset, // Dataset
				PlotOrientation.VERTICAL, // Plot Orientation
				true, // Show Legend
				true, // Use tooltips
				false // Configure chart to generate URLs?
				);

		XYPlot plot= (XYPlot) chart.getPlot();
		plot.setBackgroundPaint(Color.WHITE);
		plot.setDomainGridlinePaint(Color.BLUE);
		plot.setRangeGridlinePaint(Color.BLUE);
		XYStepRenderer stepRenderer = new XYStepRenderer();
		stepRenderer.setSeriesPaint(0, Color.BLUE);
		plot.setRenderer(stepRenderer);

		ChartPanel chartPanel = new ChartPanel(chart);

		JPopupMenu menuChart= chartPanel.getPopupMenu();
		JMenu menuRenderer = new JMenu("Graph type");

		JMenuItem menuLine = new JMenuItem("Line");
		menuLine.setActionCommand("Line");
		menuLine.addActionListener(this);
		JMenuItem menuStep = new JMenuItem("Step");
		menuStep.setActionCommand("Step");
		menuStep.addActionListener(this);
		JMenuItem menuSpline = new JMenuItem("Spline");
		menuSpline.setActionCommand("Spline");
		menuSpline.addActionListener(this);
		menuRenderer.add(menuLine);
		menuRenderer.add(menuStep);
		menuRenderer.add(menuSpline);
		menuChart.add(menuRenderer);

		this.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
		this.setLeftComponent(scrollPane);
		this.setRightComponent(chartPanel);
		this.setOneTouchExpandable(true);
		this.setResizeWeight(0.0);
	//	this.setSize(pane.getWidth(), pane.getHeight());

		this.setDividerLocation(0.2);
		this.setBorder(null);
	}

	/**
	 * Cette méthode permet d'informer un changement de la donnée afin de procéder à une mise à jours
	 */
	public void fireDataChanged(){
		chart.fireChartChanged();
	}

	/**
	 * Mise à jour du titre de la courbe
	 * @param titre titre
	 */
	public void updateTitre(String titre) {
		chart.setTitle(titre);
	}

	/**
	 * Cette méthode permet de tester si un string est de type Double.
	 * @param string string à tester
	 * @return true si c'est un Double sinon false.
	 */
	public boolean isDouble(String string)
	{
	   try
	   {
		  Double.parseDouble(string);
		  return true;
	   }
	   catch(NumberFormatException  e)
	   {
		 return false;
	  }
	}

	/**
	 * Cette méthode permet de changer la forme de la courbe soit mode lisse, escalier ou Spline
	 * @param render "Ligne" , "Echelon" ou "Spline"
	 */
	public void changeRenderer(String render){
		XYPlot plot= (XYPlot) chart.getPlot();

		if(render.equals(this.Line_Renderer_KEY)){
			XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
			renderer.setBaseShapesVisible(false);
			renderer.setSeriesPaint(0, Color.BLUE);
			plot.setRenderer(renderer);

		}else if(render.equals(this.Step_Rendeerer_KEY)){
			XYStepRenderer stepRenderer = new XYStepRenderer();
			stepRenderer.setBaseShapesVisible(false);
			stepRenderer.setSeriesPaint(0, Color.BLUE);
			plot.setRenderer(stepRenderer);
		}else if(render.equals(this.Spline_Renderer_KEY)){
			XYSplineRenderer splineRenderer = new XYSplineRenderer();
			splineRenderer.setBaseShapesVisible(false);
			splineRenderer.setSeriesPaint(0, Color.BLUE);
			plot.setRenderer(splineRenderer);
		}
		chart.fireChartChanged();
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if(event.getActionCommand().equals("Line")){
			changeRenderer(this.Line_Renderer_KEY);
		}else if(event.getActionCommand().equals("Step")){
			changeRenderer(this.Step_Rendeerer_KEY);
		}else if(event.getActionCommand().equals("Spline")){
			changeRenderer(this.Spline_Renderer_KEY);
		}

	}

	public XYSeriesCollection getDataset(){
		return dataset;
	}

	public void addDataset(XYSeries s){
		dataset.addSeries(s);
	}

	public void removeDataset(XYSeries s){
		dataset.removeSeries(s);
	}

	public void clearDataset(){
		dataset.removeAllSeries();
	}

	public void setTableModel(TableModelChart tModel){
		table.setModel(tModel);
		fireDataChanged();
	}

	public void resetTableModel(){
		table.setModel(tableModel);
		fireDataChanged();
	}

/**
 * Cette méthode permet de faire une mise à jour de courbes à afficher dans le panneau d'affichage des courbes
 * @param obs de type Chart
 * @param obj la donnée (non pris en compte)
 */
	@Override
	public void update(Observable obs, Object obj) {
		if(obs instanceof Chart){
			if(!((Chart)obs).getIsVisible())
				removeDataset( ((Chart)obs).getSeries() );
			else if(dataset.indexOf(((Chart)obs).getSeries())<0)
				addDataset( ((Chart)obs).getSeries() );
		}

	}

}
