/**
 * @author sachin_ainapure
 */
package com.nike.reporting.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nike.reporting.exceptions.NikeException;

public final class FileDownloadUtil {

	// Size of a byte buffer to read/write the file
	private static final int BUFFER_SIZE = 4096;
	private static final String HEADER_KEY = "Content-Disposition";

	/**
	 * Method for handling file download request from client
	 */
	public static void doDownload(HttpServletRequest request, HttpServletResponse response, String fileName, boolean isFromTemplatesFolder) throws NikeException {
		String user_id = LoginUtil.getLoggedInUser();
		FileInputStream inputStream = null;
		OutputStream outStream = null;
		ServletContext servletContext = null;
		String fileAbsolutePath;
		String fileNameForDownload;
		try {
			// create ServletContext object
			servletContext = request.getSession().getServletContext();
			// get application real path
			String applicationPath = servletContext.getRealPath("");

			if (isFromTemplatesFolder) {
				// Create the full absolute path of the file to be downloaded
				// from templates folder
				fileAbsolutePath = applicationPath + "\\resources\\templates\\" + fileName;
			} else {
				// Create the full absolute path of the file to be downloaded
				// from downloads folder
				fileAbsolutePath = applicationPath + "\\resources\\downloads\\" + fileName;
			}
			// Create the file object
			File file = new File(fileAbsolutePath);
			inputStream = new FileInputStream(file);

			// get the MIME type of this file
			String mimeType = servletContext.getMimeType(fileAbsolutePath);
			if (null == mimeType) {
				mimeType = "application/octet-stream";
			}

			// set response content type, length & header
			response.setContentType(mimeType);
			response.setContentLength((int) file.length());
			/*
			if (file.getName().equalsIgnoreCase(ReportingConstants.EFFORT_TRACKER_TEMPLATE_NAME)) {
				fileNameForDownload = user_id.toUpperCase() + "_" + file.getName();
			} else {
				fileNameForDownload = file.getName();
			}
			*/
			fileNameForDownload = file.getName();
			String headerValue = String.format("attachment; filename=\"%s\"", fileNameForDownload);
			response.setHeader(HEADER_KEY, headerValue);

			// get the output stream
			outStream = response.getOutputStream();

			byte[] buffer = new byte[BUFFER_SIZE];
			int bytesRead = -1;

			// Write bytes read into the output stream from the input stream
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				outStream.write(buffer, 0, bytesRead);
			}
		} catch (FileNotFoundException e) {
			throw new NikeException(e, e.getMessage());
		} catch (IOException e) {
			throw new NikeException(e, e.getMessage());
		} finally {
			try {
				if (inputStream != null) {
					inputStream.close();
				}
				if (outStream != null) {
					outStream.close();
				}
			} catch (IOException e) {
				throw new NikeException(e, e.getMessage());
			}
		}
	}
}
