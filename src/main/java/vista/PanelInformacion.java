package vista;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JPanel;

import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.BitmapEncoder.BitmapFormat;
import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.style.Styler.LegendPosition;

import controlador.ControladorAppChat;
import modelo.Grupo;

public class PanelInformacion /*extends JFrame */{

	private ControladorAppChat controlador;
	
	public PanelInformacion() {
		controlador = ControladorAppChat.getUnicaInstancia();
	}
	
	public int imprimir() {
		generarGrafica();
		
		generarPieChart();
		return 1;
	}
	
	private void generarGrafica() {
		ArrayList<Integer> numeroMensajes = controlador.getNumeroMensajesDelAnyo();
		
		CategoryChart chart = new CategoryChartBuilder().width(800).height(600).title("Número de mensajes por mes")
				.xAxisTitle("Meses").yAxisTitle("Número Mensajes").build();

		// Customize Chart
		chart.getStyler().setLegendPosition(LegendPosition.InsideNW);
		chart.getStyler().setHasAnnotations(true);
		
		int[] meses = new int[] {1,2,3,4,5,6,7,8,9,10,11,12};
		int[] nMensajes =numeroMensajes.stream().mapToInt(i -> i).toArray();

		chart.addSeries("Nmensajes", meses , nMensajes);
		try {
			BitmapEncoder.saveBitmap(chart, "./histogramaMensajes", BitmapFormat.PNG);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//new SwingWrapper(chart).displayChart();
	} 
	
	private void generarPieChart() {
		PieChart chart = new PieChartBuilder().width(800).height(600).title("Grupos Más Pesados").build();
		Color[] sliceColors = new Color[] { new Color(224, 68, 14), new Color(230, 105, 62) , new Color(236, 143, 110), new Color(243, 180, 159), new Color(246, 199, 182),
				new Color(250,80,200)};
		chart.getStyler().setSeriesColors(sliceColors);
		chart.getStyler().setLegendVisible(true);
		
		HashMap<Grupo, Double> gruposMasMensajes = new HashMap<Grupo, Double>();
		gruposMasMensajes = controlador.getGruposTopMensajes();
		for(Grupo g : gruposMasMensajes.keySet()) {
			double porcentajeMensajesUsuario = gruposMasMensajes.get(g) * 100;
			chart.addSeries(g.getNombre()+" "+ String.valueOf(porcentajeMensajesUsuario)+"%", porcentajeMensajesUsuario);
		}
		
		try {
			BitmapEncoder.saveBitmap(chart, "./GraficoTartaGrupos", BitmapFormat.PNG);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
