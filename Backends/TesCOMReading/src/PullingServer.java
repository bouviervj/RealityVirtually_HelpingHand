import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.swing.JFrame;

public class PullingServer {
	
	public static int inc;
	
	public static String retrieveUrl(){
		String result = "";
		URL oracle;
		try {
			oracle = new URL("http://vps75420.vps.ovh.ca:10110/ws/api/v1/getTrigger?p="+inc);
			inc++;
			URLConnection yc = oracle.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(
					yc.getInputStream()));
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				System.out.println(inputLine);
				result = inputLine;
			}
			in.close();
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
		return result;
	}
	
	public static void main(String[] args) {
	        
		 while (true) {
			
			String aData = retrieveUrl();
			System.out.println("Pulling:"+aData);
			
			if (aData.equals("[\"launch\"]")) {
				try {
					Runtime.getRuntime().exec("C:\\Program Files\\Internet Explorer\\iexplore.exe https://soundcloud.com/eddie-palmieri/vamonos-pal-monte-1");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			 
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }
		 
		 
	}

}
