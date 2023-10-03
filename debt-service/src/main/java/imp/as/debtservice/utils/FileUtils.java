package imp.as.debtservice.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import imp.as.debtservice.constant.ConstantsExport;

public class FileUtils {
	public static List<String> readCSVName(String path, String csvFilename) throws Exception {
	  return readCSVName(path, csvFilename, "ANSI");
	}
	
	public static List<String> readCSVName(String path, String csvFilename, String encoding) throws Exception {
		List<String> res = new ArrayList<String>();
		
		String fullPath = path + "/" + csvFilename;
		
	    try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fullPath)
	    											, ConstantsExport.getEncoding(encoding)))) {
	        String line;
	        while ((line = br.readLine()) != null) {
	        	res.add(line);
	        }
	    }
	  return res;
	}
	
	public static void writeCSVName(String path, String csvFilename, List<String> data) throws Exception {
		String fullPath = path + "/" + csvFilename;
		
		try (PrintWriter writer = new PrintWriter(new File(fullPath))) {
            for (String line : data) {
                writer.println(line);
            }
        }
	}
}
