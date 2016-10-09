import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;

import javax.swing.JFrame;

import gnu.io.CommPortIdentifier;

public class SimpleConnect {

	static int inc = 0;
	
	public static void tacleUrl(String event){
		URL oracle;
		try {
			oracle = new URL("http://vps75420.vps.ovh.ca:10110/ws/api/v1/"+event+"?p="+inc);
			inc++;
			URLConnection yc = oracle.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(
					yc.getInputStream()));
			String inputLine;
			while ((inputLine = in.readLine()) != null) 
				System.out.println(inputLine);
			in.close();
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

		String hostName = "localhost";
		int portNumber = 8088;

		int width = 640;
		int height = 480;
		JFrame frame = new JFrame("Shimmer sensor data from iMotion server");

		DirectDrawDemo panel = new DirectDrawDemo(width, height);

		frame.add(panel);
		frame.pack();
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		try {
			Socket echoSocket = new Socket(hostName, portNumber);
			System.out.println("Connected to the server");

			BufferedReader in =
					new BufferedReader(
							new InputStreamReader(echoSocket.getInputStream()));

			PrintWriter out =
					new PrintWriter(echoSocket.getOutputStream(), true);
			out.flush();

			String userInput;
			int count = 1, count_avg = 0;
			float avg = 0, avg_retained = 0;
			int y_prec = 0, y = 0;

			//Bypass counter
			int bp_counter = 0;
			int gesture_counter =0;
			int gesture_peak = 0;

			while ((userInput = in.readLine()) != null) {
				if (count>width-1) {
					count = 1;
				}

				int value = 12;
				int value1 = 11;

				try {

					String[] strings = userInput.split(";");
					//System.out.println("Data Length: " + strings.length);
					//System.out.println("Date: " + userInput);

					y_prec = y;
					y = (int) (240+(new Float(strings[value])-new Float(strings[value1])-avg_retained)*200.0);
					if ((y>0) && (y<height)) {
						panel.drawLine(Color.WHITE, count, 0, count, width-1);
						panel.drawLine(Color.RED, count-1, y_prec, count, y );
					}

					if (gesture_counter>0){
						gesture_counter++;
						if (gesture_counter>600) {
							gesture_counter = 0;


							if (gesture_peak==1) {
								System.out.println("Single");
								tacleUrl("single");
							} else if (gesture_peak==2) {
								System.out.println("Double");
								tacleUrl("double");
							} else if (gesture_peak==3) {
								System.out.println("Triple");
								tacleUrl("triple");
							}

							gesture_peak = 0;
						}
					}

					if (bp_counter>0) {
						bp_counter++;
						if (bp_counter>50) {
							bp_counter = 0;
						}
					}

					//System.out.println("Abs:"+Math.abs(y_prec-y));
					if (Math.abs(y_prec-y)>20) {
						//System.out.println(" Draw line" );

						if (bp_counter==0) {
							bp_counter++;
						} 

						if (bp_counter==1) { // We draw only the second signal
							panel.drawLine(Color.GREEN, count, 0, count, width-1);
							gesture_counter+=1;
							gesture_peak++;
						}

					}

					count++;
					count_avg++;
					avg+=(new Float(strings[value])-new Float(strings[value1]));
					if ( count_avg == 1000) {
						avg_retained = avg/1000.0f;
						avg = 0;
						count_avg = 0;
					}

				} catch (Exception e) {
					e.printStackTrace();
				}

			}	

		} catch (Exception e) {

			e.printStackTrace();

		}



	}

}
