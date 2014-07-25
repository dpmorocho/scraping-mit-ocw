package mit;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.csvreader.CsvWriter;
import com.jaunt.Element;
import com.jaunt.Elements;
import com.jaunt.HttpResponse;
import com.jaunt.ResponseException;
import com.jaunt.UserAgent;

public class Cadiz {

	/**
	 * @param args
	 */
	public static void main(String[] args)throws IOException {
		
		// URL where we can get the resources
		String sitio = "http://www.oeconsortium.org/providers/76/?page=";
		String outputFile = "CADIZOCW.csv";
		// Check if the file exist
		boolean alreadyExists = new File(outputFile).exists();
		// New file, comma separated
		CsvWriter csvOutput = new CsvWriter(new FileWriter(outputFile, true), ',');
		if (!alreadyExists)
		{
			// Label name
			csvOutput.write("Recurso");
			csvOutput.endRecord();
		}

		try {
			// Get ready for an user agent
			UserAgent userAgent = new UserAgent();
			String id = "";
			// Go to URL
			for(int i = 1; i < 5; i++){
				id = Integer.toString(i);
				userAgent.visit(sitio+id);
				// Create a list of links from each table
				Elements urls = userAgent.doc.findEach("<table>").findEach("<a>");
				
				// List for iterate
				Set<String> link = new HashSet<String>();

				for (Element a : urls) {
					csvOutput.write(a.getAttx("href"));
					csvOutput.endRecord();
				}

			}
			csvOutput.close();

		} catch (ResponseException e) {
			System.out.println(e);
		}

	}

}
