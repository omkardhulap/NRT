package com.nike.reporting.util;

public final class ErrorMessages {

	public static final String CREATE_OUTAGE_PPT_EXCEPTION = "Error in creating outage ppt";
	public static final String CREATE_OUTAGE_PPT_FILE_NOT_EXCEPTION = "Error in downloading ppt. File not found";
	public static final String CREATE_OUTAGE_PPT_IO_EXCEPTION = "Could not close I/O stream while ";

	public static final String PASSWORD_ENCRYPTION_EXCEPTION = "Exception occured while encrypting password";
	public static final String PASSWORD_DECRYPTION_EXCEPTION = "Exception occured while decrypting password";

	public static final String NO_DB_USER_EXCEPTION = "No User Found in database";
	public static final String DB_USER_EXISTS_EXCEPTION = "User Id already exists in database";

	public static final String DATE_PARSING_EXCEPTION = "Exception occurred while parsing the date";

	public static final String EFFORT_FILE_NOT_FOUND_EXCEPTION = "Effort File not found";
	public static final String EFFORT_FILE_IO_EXCEPTION = "Error in reading Effort file";
	public static final String EFFORT_FILE_INCORRECT = "Invalid Effort Template. Please download & use the latest Effort Template";
	public static final String EFFORT_FILE_EXTENSTION_INCORRECT = "Invalid File Extension. xlsx file expected";
	public static final String EFFORT_FILE_VALIDATION_EXCEPTION = "Error(s) in the uploaded file: ";

	// MTTR
	public static final String MTTR_FILE_EXTENSTION_INCORRECT = "Invalid File Extension. xls file expected";
	public static final String MTTR_FILE_INCORRECT = "Invalid MTTR dump. Please download from SNOW and re-upload";
	public static final String MTTR_FILE_NOT_FOUND_EXCEPTION = "MTTR File not found";
	public static final String MTTR_FILE_IO_EXCEPTION = "Error in reading MTTR file";

}