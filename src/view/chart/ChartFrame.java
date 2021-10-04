package view.chart;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.TableColumn;

import view.chart.Chart;
import view.table.ChoiceChartTableModel;

/**
 *
 * Classe ChartMain
 */

public class ChartFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	LineChartPane chartPane;
	JSplitPane jsplitpane;

	private JTable choiceTable;
	private ChoiceChartTableModel choiceTableModel;
	private JScrollPane tableScrollPane;

	/**
	 * Constructeur de ChartMain
	 * 
	 * @param projectName le nom du projet
	 * @param title       le titre de la fenêtre d'affichage de courbes
	 */
	public ChartFrame(String projectName, String title) {
		this.setTitle(title);
		// this.setLocationRelativeTo(null);
		this.setSize(1000, 600);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		chartPane = new LineChartPane(projectName, title);

		choiceTableModel = new ChoiceChartTableModel();
		choiceTable = new JTable();
		choiceTable.setModel(choiceTableModel);
		choiceTable.getTableHeader().setReorderingAllowed(false);

		choiceTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (SwingUtilities.isLeftMouseButton(e)) {
					int rowNumber = choiceTable.rowAtPoint(e.getPoint());
					chartPane.setTableModel(choiceTableModel.getChart(rowNumber).getTableModel());
				}

			}
		});

		tableScrollPane = new JScrollPane(choiceTable);
		tableScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		tableScrollPane.setBorder(null);
		TableColumn TypeColumnWFlag = choiceTable.getColumnModel().getColumn(1);
		JCheckBox checkWFlag = new JCheckBox();

		TypeColumnWFlag.setCellEditor(new DefaultCellEditor(checkWFlag));

		jsplitpane = new JSplitPane();
		jsplitpane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
		jsplitpane.setLeftComponent(chartPane);
		jsplitpane.setRightComponent(tableScrollPane);
		jsplitpane.setOneTouchExpandable(true);
		jsplitpane.setResizeWeight(0.0);
		jsplitpane.setBorder(null);

		add(jsplitpane);
		this.setVisible(true);
		jsplitpane.setDividerLocation(0.8);
	}

	/**
	 * Cette méthode permet de récupérer le panneau d'affichage des courbes de type
	 * LineChartPane
	 * 
	 * @return le panneau d'affichage des courbes
	 */
	public LineChartPane getLineChartPane() { return chartPane; }

	/**
	 * Cette méthode permet de récupérer le modèle de table de choix des courbes à
	 * afficher et le choix du tableau de valeurs d'une courbe
	 * 
	 * @return le modèle de table de choix de type ChoiceChartTableModel
	 */
	public ChoiceChartTableModel getChoiceTableModel() { return this.choiceTableModel; }

	/**
	 * Cette méthode permet d'ajouter une courbe au panneau d'affichage des courbes
	 * et au modèle de table de choix des courbes à afficher
	 * 
	 * @param c la courbe de type Chart
	 */
	public void addToLineChartPane(Chart c) {
		c.addObserver(chartPane);
		choiceTableModel.addChart(c);
	}

	/**
	 * Cette méthode permet de supprimer l'ensemble de donnés de la fenêtre
	 * d'affichage de courbes
	 */
	public void reset() {
		chartPane.clearDataset();
		chartPane.resetTableModel();
		choiceTableModel.clearAll();
	}

}
