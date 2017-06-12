package simpleSPPServer;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;

import javax.bluetooth.*;
import javax.microedition.io.*;

import org.math.plot.*;
import javax.swing.*;

/**
* Class that implements an SPP Server which accepts single line of
* message from an SPP client and sends a single line of response to the client.
*/
public class SimpleSPPServer {
	
//start server
private void startServer() throws IOException{

	//Create a UUID for SPP
	UUID uuid = new UUID("1101", true);
	//Create the service url
	String connectionString = "btspp://localhost:" + uuid +";name=Sample SPP Server";
	System.out.println(connectionString);
	
	//open server url
	StreamConnectionNotifier streamConnNotifier = (StreamConnectionNotifier)Connector.open( connectionString );
	
	//Wait for client connection
	System.out.println("\nServer Started. Waiting for clients to connect¡­");
	StreamConnection connection=streamConnNotifier.acceptAndOpen();
	
	RemoteDevice dev = RemoteDevice.getRemoteDevice(connection);
	//System.out.println("Remote device address: "+dev.getBluetoothAddress());
	//System.out.println("Remote device name: "+dev.getFriendlyName(true));
	System.out.println("Connected");
	
	OutputStream outStream=connection.openOutputStream();
	PrintWriter pWriter=new PrintWriter(new OutputStreamWriter(outStream));
	pWriter.write("Response String from SPP Server\r\n");
	pWriter.flush();
	
	System.out.println("Response String from SPP Server\r\n");
	
	Thread lsnt = new Thread(new ListenThread(connection));
	lsnt.start();
	//read string from spp client
	/*
	 * InputStream inStream=connection.openInputStream();

	BufferedReader bReader=new BufferedReader(new InputStreamReader(inStream));
	String lineRead=bReader.readLine();
	
    do{
	System.out.println(lineRead);
	
	//send response to spp client
	OutputStream outStream=connection.openOutputStream();
	PrintWriter pWriter=new PrintWriter(new OutputStreamWriter(outStream));
	pWriter.write("Response String from SPP Server\r\n");
	pWriter.flush();
	
	pWriter.close();
	streamConnNotifier.close();
    }while((lineRead=bReader.readLine())!="exit");
    */

}

public static void main(String[] args) throws IOException {
//plot();
	//display local device address and name
	LocalDevice localDevice = LocalDevice.getLocalDevice();
	
	
	System.out.println("Address: "+localDevice.getBluetoothAddress());
	System.out.println("Name: "+localDevice.getFriendlyName());
	
	SimpleSPPServer simpleSPPServer=new SimpleSPPServer();
	simpleSPPServer.startServer();

}



public SimpleSPPServer(){}

public static void plot(){
    // define your data
    double[] x = { 1, 2, 3, 4, 5, 6 };
    double[] y = { 45, 89, 6, 32, 63, 12 };

    // create your PlotPanel (you can use it as a JPanel)
    Plot2DPanel plot = new Plot2DPanel();

    // define the legend position
    plot.addLegend("SOUTH");

    // add a line plot to the PlotPanel
    plot.addLinePlot("my plot", x, y);

    // put the PlotPanel in a JFrame like a JPanel
    JFrame frame = new JFrame("a plot panel");
    frame.setSize(600, 600);
    frame.setContentPane(plot);
    frame.setVisible(true);
}

}