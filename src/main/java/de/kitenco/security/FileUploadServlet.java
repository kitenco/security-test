package de.kitenco.security;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FileUploadServlet extends HttpServlet {

	/**
	 * TODO nur ein Test Kommentar
	 */
	private static final long serialVersionUID = 1L;
	private static final String UPLOAD_DIRECTORY_STRING = "bla";
	
	public FileUploadServlet () {
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	response.setContentType("text/html");
//	PrintWriter out = response.getWriter();
	String contentType = "";
	if (request != null) {
		contentType = request.getContentType();
	}
	
	// the starting position of the boundary header
	int ind = 0;
	String boundary = "";
	if (contentType != null) {
		ind = contentType.indexOf("boundary=");
		boundary = contentType.substring(ind+9);
	}
	
	String pLine = "";
//	String uploadLocation = new String(UPLOAD_DIRECTORY_STRING); //Constant value
	// INFO die obere Zeile wuerde eine FindBugs Meldung verursachen! Kaps 
	String uploadLocation = UPLOAD_DIRECTORY_STRING; //Constant value
	
	// verify that content type is multipart form data
	if (contentType != null && contentType.indexOf("multipart/form-data") != -1) {
	
	// extract the filename from the Http header
	// INFO ohne das Charset zu definieren, wird hier eine FindBugs Meldung generiert (Kaps)
	BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(),Charset.forName("UTF-8").newDecoder() ));
	
	pLine = br.readLine();
	String filename = "";
	// INFO das Weglassen der If-Abfrage auf null wuerde eine FindBugs Meldung verursachen! Kaps
	if (pLine != null) {
		filename = pLine.substring(pLine.lastIndexOf("\\"), pLine.lastIndexOf("\""));
	}
	
	// output the file to the local upload directory
	try {
//		BufferedWriter bw = new BufferedWriter(new FileWriter(uploadLocation+filename, true));
		// INFO Charset ist hier wichtig, siehe http://stackoverflow.com/questions/9852978/write-a-file-in-utf-8-using-filewriter-java (Kaps)
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(uploadLocation+filename), "UTF-8"));
		for (String line; (line=br.readLine())!=null; ) {
			if (line.indexOf(boundary) == -1) {
				bw.write(line);
				bw.newLine();
				bw.flush();
			}
		} //end of for loop
		bw.close();
	
	} catch (IOException ex) {
		// output successful upload response HTML page
		
	} 
	} else {
	// output unsuccessful upload response HTML page
			
	}
	}

}
