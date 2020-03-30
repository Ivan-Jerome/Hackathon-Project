package memoryData;
import java.io.IOException;
import java.sql.*;
import org.json.simple.JSONObject;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
public class memoryData {
	public static void main(String[] args) throws IOException {

		float total=0,avg,value,max=0;
		String sno,st;
		JSONObject object1 =new JSONObject();
		JSONObject object2=new JSONObject();
		File file=new File("C:\\Users\\ivanj\\Desktop\\Hackathon_Project\\#2\\Memory.txt");
		BufferedReader br = new BufferedReader(new FileReader(file));
		int count = 0;
		while ((st = br.readLine()) != null) {
			st = st.trim();
			String[] array = st.split(" ");
			if (array[0].equals("TOTAL")) 
			{
				// Converting the KB Memory value to MB Memory value
				value =Float.parseFloat(array[3]) / 1024; 
				total=total+value;
				//To check the maximum Memory Value
				if(value>max) {
				max=value;
				}
				count=count+1;
				sno=Integer.toString(count)+"S";
				//Pushing the Memory Values into the object1
				object1.put(sno,value); 
			}
		}
		
		//Finding the Average Value
		avg=total/count;

		// Connecting to mySQL and storing the value into Database
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb", "root", "admin");
			Statement stmt = con.createStatement();
			String sql ="INSERT into memorytable (maximum_value,average_value) values('"+ max+"','"+avg+"')";
			stmt.executeUpdate(sql);
		} 
		catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
        // Appending the prefix MB to the memory Value
		String Smax=String.valueOf(max)+" MB"; 
		String Savg=String.valueOf(avg)+" MB"; 
		
	    // Inserting the average,maximum and memory values from JSON object
		object2.put("average",Savg); 
		object2.put("max",Smax); 
		object2.put("values",object1);
		
		//Printing the JSON object2
		System.out.println(object2);
		
		FileWriter fileWriter = new FileWriter("Memory_Data.json");
		try {
			fileWriter.write(obj1.toJSONString()); // Writing to an external JSON file
		} 
		
		catch (Exception e) {
			e.printStackTrace();
		} 
		
		finally {
			fileWriter.flush();
			fileWriter.close();
		}
		
	}
}
