package cpuRetrieval;
import java.io.*;
import java.sql.*;
import org.json.simple.JSONObject;
public class cpuRetrieval{

	static String hint;
	public static void main(String[] args) throws IOException {
		int count = 0;
	   
		// Creating JSON Object
		JSONObject obj = new JSONObject();
		// Storing the Transaction Name in the first attribute 
	    obj.put("Transaction Name", "Sample_Transaction");
			try
		{
		// Connecting to MySQL		
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb","root","admin");  
			Statement stmt=con.createStatement();
		// Importing the input text file	
			File file = new File("C:\\Users\\ivanj\\Desktop\\input.txt");
			BufferedReader br = new BufferedReader(new FileReader(file));
			String st;
			float value;
			float avg = 0,max=0;	
			while ((st=br.readLine())!= null)
			{
				String[] array = st.split(" ");  
				
				if (array[8].length()>0)
				{
					if (array[14].length()>0)
					{
						value = Float.parseFloat(array[14]);
					}
					else 
					{
						value= Float.parseFloat(array[15]);
					}	
				}
				else 
				{
					if (array[16].length()>0)
					{
						value = Float.parseFloat(array[16]);
					}
					else 
					{
						value= Float.parseFloat(array[17]);;
					}	
				}
			// Calculating Average and Maximum of CPU Value
				avg = (avg+value)/2;
				if (value > max)
				{
					max = value;
				}
				count++;
				hint =Integer.toString(count)+ "S";
				obj.put(hint, value);
			}
			System.out.printf("Maximum Value: %f\t Average Value: %f\n",max,avg);
		    obj.put("Maximum", max);
		    obj.put("Average", avg);
		    // Inserting the values into the database
			String sql="INSERT into cputable (transactionname,maximum,average) values('Sample_Transaction','"+max+"','"+avg+"')";
		    stmt.executeUpdate(sql);
		    
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		} 
		finally 
		{
		// Printing the JSON Object
		 System.out.println(obj);
		}
	}
		
}
