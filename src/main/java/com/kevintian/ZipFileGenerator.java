package com.kevintian;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.model.enums.CompressionLevel;
import net.lingala.zip4j.model.enums.EncryptionMethod;

public class ZipFileGenerator {
	private File tmpDir; 
	private File rsTxtFile = null;
	private File rsZipFile = null;
	
	public ZipFileGenerator(String tmpDir) {
		this.tmpDir = new File(tmpDir);
		boolean createdDir = this.tmpDir.mkdir();
		if(!createdDir) {
			throw new RuntimeException("Cannot create temporary directory!");
		}
	}
	
	public File generate(String[] labels, boolean lowerCaseOption, boolean upperCaseOption, boolean numberOption, 
				         boolean specialCharOption, String filePw) throws IOException, ZipException { 
		PwGenerator pwGen = new PwGenerator(lowerCaseOption, upperCaseOption, numberOption,
				specialCharOption);
		this.rsTxtFile = new File(this.tmpDir.getAbsolutePath() + "\\rs.txt");
		boolean createdFile = this.rsTxtFile.createNewFile();
		
		if(!createdFile) {
			throw new RuntimeException("Cannot create rs.txt!");
		}

		FileWriter fw = new FileWriter(this.rsTxtFile);
		BufferedWriter bw = new BufferedWriter(fw);

		for (int c = 0; c < labels.length; c++) {
			if (labels[c].equals("")) {
				bw.write(pwGen.generate());
			} else {
				bw.write(labels[c].trim() + " => " + pwGen.generate());
			}

			if (c != (labels.length - 1)) {
				bw.write("\n\n");
			}
		}

		bw.flush();
		bw.close();

		ZipParameters zipParameters = new ZipParameters();
		zipParameters.setCompressionLevel(CompressionLevel.NORMAL);
		zipParameters.setEncryptionMethod(EncryptionMethod.AES);
		ZipFile zipFile = null;

		if (filePw.equals("")) {
			zipParameters.setEncryptFiles(false);
			zipFile = new ZipFile(this.tmpDir.getAbsolutePath() + "\\rs.zip");
		} else {
			zipParameters.setEncryptFiles(true);
			zipFile = new ZipFile(this.tmpDir.getAbsolutePath()  + "\\rs.zip", filePw.toCharArray());
		}

		zipFile.addFile(this.rsTxtFile, zipParameters);
		this.rsZipFile = zipFile.getFile();
		zipFile.close();
		
		return zipFile.getFile();
	}
	
	public void cleanUp() { 
		if(this.rsTxtFile != null) {
			this.rsTxtFile.delete();
		}
		
		if(this.rsZipFile != null) {
			this.rsZipFile.delete();
		}
		
		this.tmpDir.delete();
	}
}
