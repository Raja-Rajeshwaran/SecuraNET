package util;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CSVExporter {
	// In CSVExporter.java
	public static void export(String filename, List<String[]> data) throws IOException {
	    String exportPath = "C:\\Users\\LENOVO\\eclipse-workspace\\SecuraNET\\exports\\" + filename; // Windows
	    // String exportPath = "/home/user/securanet/exports/" + filename; // Linux/Mac
	    
	    try (FileWriter writer = new FileWriter(exportPath)) {
	        for (String[] row : data) {
	            writer.append(String.join(",", row)).append("\n");
	        }
	    }
	}
}