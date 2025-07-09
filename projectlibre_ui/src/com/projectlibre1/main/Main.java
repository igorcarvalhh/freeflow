package com.projectlibre1.main;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.prefs.Preferences;

import com.projectlibre1.strings.Messages;
import com.projectlibre1.util.Environment;

public class Main {
	public static void main(String[] args) {
		int runNumber=getRunNumber()+1;
		long firstRun=getFirstRun();
		Preferences.userNodeForPackage(Main.class).putInt("projectlibreRunNumber",runNumber);
		Preferences.userNodeForPackage(Main.class).putLong("projectlibrefirstRun",firstRun);		
		System.setProperty("projectlibre.runNumber", runNumber+"");
		System.setProperty("projectlibre.firstRun", firstRun+"");
		System.setProperty("projectlibre.projectLibreRunNumber", getProjectLibreRunNumber()+"");
		System.setProperty("projectlibre.projectLibreFirstRun", getProjectLibreFirstRun()+"");
		
		Environment.setStandAlone(true);
		String[] formatedArgs;
		if (args!=null && args.length>0){
			ArrayList<String> nonEmptyArgs=new ArrayList<>(args.length);
            for (String arg : args) {
                if (arg != null && !arg.isEmpty()) nonEmptyArgs.add(arg);
            }
			if (!nonEmptyArgs.isEmpty()){
				ArrayList<String> formatedList=new ArrayList<>();
				String s1,s2;
				for (Iterator<String> i=nonEmptyArgs.iterator();i.hasNext();){
					s1=i.next();
					if (i.hasNext()){
						s2=i.next();
					}else{
						s2=s1;
						s1="--fileNames";
					}
					formatedList.add(s1);
					formatedList.add(s2);
				}
				formatedArgs=formatedList.toArray(new String[]{});
			} else formatedArgs=args;
		} else formatedArgs=args;

		com.projectlibre1.pm.graphic.gantt.Main.main(formatedArgs);
	}
	public static int getRunNumber() {
		return Preferences.userNodeForPackage(Main.class).getInt("projectlibreRunNumber",0);
	}
	public static long getFirstRun() {
		return Preferences.userNodeForPackage(Main.class).getLong("projectlibreFirstRun",System.currentTimeMillis());
	}
	public static int getProjectLibreRunNumber() {
		return Preferences.userNodeForPackage(Main.class).getInt("runNumber",0);
	}
	public static long getProjectLibreFirstRun() {
		return Preferences.userNodeForPackage(Main.class).getLong("firstRun",System.currentTimeMillis());
	}
	public static String getRunSinceMessage() {
		return MessageFormat.format(Messages.getString("Text.runsSinceMessage"), getRunNumber(), new Date(getFirstRun()));
	}

}
