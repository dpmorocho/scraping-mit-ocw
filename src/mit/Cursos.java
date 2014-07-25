package mit;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.csvreader.CsvWriter;
import com.jaunt.Element;
import com.jaunt.Elements;
import com.jaunt.ResponseException;
import com.jaunt.UserAgent;

public class Cursos {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		
		try{
			  UserAgent userAgent = new UserAgent();
			  userAgent.visit("http://ocw.mit.edu/courses/");   
			                                                   
			  Elements urls = userAgent.doc.findEach("<table>").findEach("<a>");
			  
			  String outputFile = "Cursos.csv";
				boolean alreadyExists = new File(outputFile).exists();
				CsvWriter csvOutput = new CsvWriter(new FileWriter(outputFile, true), ',');
				
				if (!alreadyExists)
				{
					csvOutput.write("Nivel");
					csvOutput.write("Codigo");
					csvOutput.write("Curso");
					csvOutput.endRecord();
				}
			 
			  for(Element url : urls){
				  csvOutput.write(url.innerHTML());
				  csvOutput.endRecord();
			  }
				csvOutput.close();
			}
			catch(ResponseException e){
				
			  System.out.println(e);
			}

	}

}