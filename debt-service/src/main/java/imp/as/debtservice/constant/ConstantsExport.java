package imp.as.debtservice.constant;

public class ConstantsExport {
	
	//ENCODING FILE
	public static final String ENCODING_ANSI = "TIS-620";
	public static final String ENCODING_TIS620 = "TIS-620";
	public static final String ENCODING_UTF8 = "UTF-8";
	
	//FILE EXTENSION
	public static final String FILE_EXTENSION_DAT = ".dat";
	public static final String FILE_EXTENSION_SYNC = ".sync";
	public static final String FILE_EXTENSION_ACK = ".ack";
	public static final String FILE_EXTENSION_CSV = ".csv";
	public static final String FILE_EXTENSION_XML = ".xml";
	
	public static String getEncoding(String encoding) throws Exception {
		switch (encoding) {
		case "ANSI":
			return ConstantsExport.ENCODING_ANSI;

		case "TIS-620":
			return ConstantsExport.ENCODING_TIS620;

		case "UTF-8":
			return ConstantsExport.ENCODING_UTF8;

		default:
			break;
		}
		return null;
	}
}
