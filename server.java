package alsetfuelcalc;


import java.io.*;
import java.net.*;
import java.util.ArrayList;

/**
 *
 * @author Karishma
 */
 
public class sever {
    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        
        ServerSocket welcomeSocket = new ServerSocket(6789);
        System.out.println("Server Ready!!!");
        while(true){
            Socket connectionSocket = welcomeSocket.accept();
            System.out.println("Server-client joined!!!");
            DataOutputStream strOut = new DataOutputStream(connectionSocket.getOutputStream());
            
            String line;        //for storing data to be read by BufferedReader
            BufferedReader buffR = new BufferedReader(new
                                        FileReader("resources/fuelPrice.txt")); //will read data from this path
            ArrayList<String> fuelData = new ArrayList<>(); //for storing values of files
            while ((line = buffR.readLine()) != null) {     //read line of file until line becomes null(go line by line in to file)
                    fuelData.add(line); //add read single value to arraylist of fuelData
		}
		strOut.writeUTF(String.valueOf(fuelData));
		/*DataInputStream stringInp = new DataInputStream(new FileInputStream(connectionSocket.getOutputStream()));
		String line;
		while ((line = buffR.readLine()) != null) {     //read line of file until line becomes null(go line by line in to file)
			fuelData.add(line); //add read single value to arraylist of fuelData
		}*/
            
	}
}
    
}
