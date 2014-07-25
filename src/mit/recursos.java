package mit;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.jaunt.Element;
import com.jaunt.Elements;
import com.jaunt.HttpResponse;
import com.jaunt.ResponseException;
import com.jaunt.UserAgent;

import com.csvreader.CsvWriter;

public class recursos {

	/**
	 * @param args
	 * @throws IOException 
	 */
	@SuppressWarnings("rawtypes")
	public static void main(String[] args) throws IOException {
		
		// URL where we can get the resources
		String sitio = "http://ocw.mit.edu/courses/";
		// Subpath, here we can choose between lectures, readings or course materials
		String subpath = "/lecture-notes/"; // Also: "/readings/" or "/download-course-materials/"
		// Name of the file
		String outputFile = "MITOCW.csv";
		// Check if the file exist
		boolean alreadyExists = new File(outputFile).exists();
		// New file, comma separated
		CsvWriter csvOutput = new CsvWriter(new FileWriter(outputFile, true), ',');
		
		// Create headings
		if (!alreadyExists)
		{
			// Label name
			csvOutput.write("Recurso");
			csvOutput.endRecord();
		}

		try {
			// Get ready for an user agent
			UserAgent userAgent = new UserAgent();
			// Go to URL
			userAgent.visit(sitio);
			
			
			// Create a list of links from each table
			Elements urls = userAgent.doc.findEach("<table>").findEach("<a>");
			
			// List for iterate
			Set<String> link = new HashSet<String>();

			for (Element a : urls) {
				// Get each course link and add a subpath
				link.add(a.getAttx("href") + subpath);
			}
			// Get ready for read the list of course links
			Iterator it = link.iterator();
			while (it.hasNext()) {
				try {
					// Visit each course link
					userAgent.visit(it.next().toString());
				} catch (ResponseException e) {
					// If we don't get a good response, we go to next link
					HttpResponse response = e.getResponse();
					if (response != null) {
						it.next();
					} else {
						it.next();
					}
				}
				
				// Get the links of each resource in all tables
				Elements linkrecursos = userAgent.doc.findEach("<table>").findEach("<a>");
				
				// List for iterate
				Set<String> recursos = new HashSet<String>();

				for (Element a : linkrecursos) {
					// Get the link resources of the course
					recursos.add(a.getAttx("href"));
				}
				// Get ready for read the list of resource links
				Iterator iter = recursos.iterator();
				while (iter.hasNext()) {
					// Write a entry on CSV file
					csvOutput.write(iter.next().toString());
					csvOutput.endRecord();
				}

			}
			// Close the file
			csvOutput.close();

		} catch (ResponseException e) {
			System.out.println(e);
		}

	}

}
