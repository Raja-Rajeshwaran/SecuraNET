package util;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CSVExporter {
	public static void export(String filename, List<String[]> data) throws IOException {
	    String exportPath = "C:\\Users\\LENOVO\\OneDrive\\Documents\\SecuraNetReports\\" + filename; 
	    
	    try (FileWriter writer = new FileWriter(exportPath)) {
	        for (String[] row : data) {
	            writer.append(String.join(",", row)).append("\n");
	        }
	    }
	}
}