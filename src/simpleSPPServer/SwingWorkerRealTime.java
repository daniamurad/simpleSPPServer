package simpleSPPServer;
import java.util.LinkedList;
import java.util.List;

import javax.swing.SwingWorker;

import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.style.markers.Marker;
import java.util.ConcurrentModificationException;

/**
 * Creates a real-time chart using SwingWorker
 */
public class SwingWorkerRealTime {

  MySwingWorker mySwingWorker;
  SwingWrapper<XYChart> sw;
  XYChart chart;
/*
  public static void main(String[] args) throws Exception {

    SwingWorkerRealTime swingWorkerRealTime = new SwingWorkerRealTime();
    swingWorkerRealTime.go();
  }
  */

  public void go() {

    // Create Chart
    chart = QuickChart.getChart("Acceleration", "Time", "Value", "acce", new double[] { 0 }, new double[] { 0 });
    chart.getStyler().setYAxisMax(30.);
    chart.getStyler().setYAxisMin(-30.);
    chart.getStyler().setMarkerSize(0);
    XYSeries series_x = chart.addSeries("acce_x", new double[]{0.}, new double[]{0.});
    XYSeries series_y = chart.addSeries("acce_y", new double[]{0.}, new double[]{0.});
    XYSeries series_z = chart.addSeries("acce_z", new double[]{0.}, new double[]{0.});
    chart.getStyler().setLegendVisible(false);
    chart.getStyler().setXAxisTicksVisible(false);

    // Show it
    sw = new SwingWrapper<XYChart>(chart);
    sw.displayChart();

    mySwingWorker = new MySwingWorker();
    mySwingWorker.execute();
  }
  
  
  public void update(double x, double y , double z){
	  mySwingWorker.update(x, y, z);	  
  }

  private class MySwingWorker extends SwingWorker<Boolean, double[]> {

    LinkedList<Double> fifox = new LinkedList<Double>();
    LinkedList<Double> fifoy = new LinkedList<Double>();
    LinkedList<Double> fifoz = new LinkedList<Double>();
    
    
    public MySwingWorker() {
      fifox.add(0.0);
      fifoy.add(0.0);
      fifoz.add(0.0);
    }
    
    
    public void update(double x, double y , double z){

        fifox.add(x);
        if (fifox.size() > 500) {
          fifox.removeFirst();
        }
        
        fifoy.add(y);
        if (fifoy.size() > 500) {
          fifoy.removeFirst();
        }
        
        fifoz.add(z);
        if (fifoz.size() > 500) {
          fifoz.removeFirst();
        }
        
        double[] arrayx = new double[fifox.size()];
        for (int i = 0; i < fifox.size(); i++) {
          arrayx[i] = fifox.get(i);
        }
        
        double[] arrayy = new double[fifoy.size()];
        for (int i = 0; i < fifoy.size(); i++) {
          arrayx[i] = fifoy.get(i);
        }
        
        double[] arrayz = new double[fifoz.size()];
        for (int i = 0; i < fifoz.size(); i++) {
          arrayx[i] = fifoz.get(i);
        }
        
        publish(arrayx,arrayy,arrayz);
    	
    }

    @Override

    protected Boolean doInBackground() throws Exception {
      /*
      while (!isCancelled()) {
            update(1,2,3);

        try {
          Thread.sleep(100);
        } catch (InterruptedException e) {
          // eat it. caught when interrupt is called
          System.out.println("MySwingWorker shut down.");
        }

      }
      */
      return true;

    }

    @Override
    protected void process(List<double[]> chunks) {
      //System.out.println("number of chunks: " + chunks.size());
      if (chunks.size()>=3){
	      double[] datax = chunks.get(0);
	      double[] datay = chunks.get(1);
	      double[] dataz = chunks.get(2);
	
	      chart.updateXYSeries("acce_x", null, this.fifox, null);
	      chart.updateXYSeries("acce_y", null, this.fifoy, null);
	      chart.updateXYSeries("acce_z", null, this.fifoz, null);
	      try{
	      sw.repaintChart();
	      }catch(ConcurrentModificationException e){}
      }

    }
  }
}