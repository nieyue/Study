package com.nieyue.p2p;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Properties;

public class Stupid {
 
	public static Properties getPZ() throws Exception{
		Properties p=new Properties();
		BufferedReader in=new BufferedReader(
				new InputStreamReader(
						new FileInputStream(
								new File((Stupid.class.getResource("")).toString().substring(6)+"ipAndPort.properties"))));
								//new File(Stupid.class.getResource("")+"./ipAndPort.properties"))));
		p.load(in);
        return p;        
   
	}
	
}