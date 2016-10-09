import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;

import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;

public class SimpleRead {
	
    private SerialPort serialPort;
    private OutputStream outStream;
    private InputStream inStream;
 
    public void connect(String portName) throws IOException {
        try {
            // Obtain a CommPortIdentifier object for the port you want to open
            CommPortIdentifier portId =
                    CommPortIdentifier.getPortIdentifier(portName);
 
            // Get the port's ownership
            serialPort =
                    (SerialPort) portId.open("Demo application", 5000);
 
            // Set the parameters of the connection.
            setSerialPortParameters();
 
            // Open the input and output streams for the connection. If they won't
            // open, close the port before throwing an exception.
            outStream = serialPort.getOutputStream();
            inStream = serialPort.getInputStream();
        } catch (NoSuchPortException e) {
            throw new IOException(e.getMessage());
        } catch (PortInUseException e) {
            throw new IOException(e.getMessage());
        } catch (IOException e) {
            serialPort.close();
            throw e;
        }
    }
 
    /**
     * Get the serial port input stream
     * @return The serial port input stream
     */
    public InputStream getSerialInputStream() {
        return inStream;
    }
 
    /**
     * Get the serial port output stream
     * @return The serial port output stream
     */
    public OutputStream getSerialOutputStream() {
        return outStream;
    }
 
    /**
     * Sets the serial port parameters
     */
    private void setSerialPortParameters() throws IOException {
        int baudRate = 115200; // 57600bps
 
        try {
            // Set serial port to 57600bps-8N1..my favourite
            serialPort.setSerialPortParams(
                    baudRate,
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);
 
            serialPort.setFlowControlMode(
                    SerialPort.FLOWCONTROL_NONE);
        } catch (UnsupportedCommOperationException ex) {
            throw new IOException("Unsupported serial port parameter");
        }
    }
    
    static CommPortIdentifier portId;
    
    static Enumeration portList;
    
    public static void main(String[] args) {
    	
        portList = CommPortIdentifier.getPortIdentifiers();
        while (portList.hasMoreElements()) {
            portId = (CommPortIdentifier) portList.nextElement();
            if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
            	 
            	/*
                 if (portId.getName().equals("COM1")) {
			//                if (portId.getName().equals("/dev/term/a")) {
                    SimpleRead reader = new SimpleRead();
                 }
                 */
            	System.out.println(portId.getName());
            	
            	
            }
        }
        
        SimpleRead comReader = new SimpleRead();
        try {
			
        	comReader.connect("COM7");
			
        	byte[] readBuffer = new byte[20];

            try {
            	System.out.print("Trying to read device data");
                while (comReader.getSerialInputStream().available() > 0) {
                	System.out.print("Available data :"+comReader.getSerialInputStream().available());
                    int numBytes = comReader.getSerialInputStream().read(readBuffer);
                    System.out.println(new String(readBuffer));
                }
                System.out.print(new String(readBuffer));
            } catch (IOException e) {System.out.println(e);}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        
        
        
    }
}