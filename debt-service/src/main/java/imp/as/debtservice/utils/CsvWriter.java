package imp.as.debtservice.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class CsvWriter<T> {
	String[] attrNameBody;
	
	private String path;
	private String fileName;
	private String delimited;
	List<T> datas;
	
	public CsvWriter(String path, String fileName, String delimited) {
		this.fileName = fileName;
		this.path = path;
		this.delimited = delimited;
	}
	
	public void writeCsvFile() throws Exception {
		List<String> rows = new ArrayList<>();
		
		for(T obj : datas) {
			String row = "";
			
			for( int i = 0; i < attrNameBody.length; i++ ) {
				final String fieldName = attrNameBody[i];
				
				Field field = obj.getClass().getDeclaredField(fieldName);
				field.setAccessible(true);
				Object column = field.get(obj);
				
				row += column;
				
				if(i != attrNameBody.length - 1) {
					row += delimited;
				}
			}
			
			rows.add(row);
		}
		
		FileUtils.writeCSVName(path, fileName, rows);
	}
}
