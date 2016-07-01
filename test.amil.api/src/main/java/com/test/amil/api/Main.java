package com.test.amil.api;

import java.io.FileNotFoundException;
import java.util.Properties;
import java.util.Scanner;

import com.test.amil.api.controller.FileContoller;
import com.test.amil.api.core.AppCore;

/**
 * Classe de execução do app
 * @author tbdea
 *
 */
public class Main {

	public static void main(String[] args) {
		AppCore core = AppCore.getInstance();
		Properties props = core.getProperties();

		while(true) {			
			try {
				System.out.println(props.getProperty("amil.label.filepath.input"));
				Scanner pathInput = new Scanner(System.in);
				if(pathInput.hasNext()) {
					String filePath = pathInput.nextLine();					
					FileContoller fileContoller = new FileContoller(props);
					fileContoller.processFile(filePath);;
				}
				pathInput.close();
				break;
			} catch (FileNotFoundException e) {
				System.out.println(props.getProperty("amil.label.filepath.error"));
				continue;
			} catch (Exception e) {
				e.printStackTrace();
				break;
			}
		}
	}
}
