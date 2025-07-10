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
		int runNumber = getStoredRunNumber() + 1;
		long firstRun = getStoredRunNumber();

		storeRunInfo(runNumber, firstRun);
		setSystemProperties(runNumber, firstRun);
		
		Environment.setStandAlone(true);

		// TODO Extract to a function
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

	public static Preferences getUserPreferences() {
		return Preferences.userNodeForPackage(Main.class);
	}

	public static void storeRunInfo(int runNumber, long firstRun) {
		Preferences usrPrefs = getUserPreferences();
		usrPrefs.putInt("projectlibreRunNumber", runNumber);
		usrPrefs.putLong("projectlibrefirstRun", firstRun);
	}

	public static void setSystemProperties(int runNumber, long firstRun) {
		System.setProperty("projectlibre.runNumber", String.valueOf(runNumber));
		System.setProperty("projectlibre.firstRun", String.valueOf(firstRun));
		System.setProperty("projectlibre.projectLibreRunNumber", String.valueOf(getProjectLibreRunNumber()));
		System.setProperty("projectlibre.projectLibreFirstRun", String.valueOf(getProjectLibreFirstRun()));
	}

	public static int getStoredRunNumber() {
		return getUserPreferences().getInt("runNumber", 0);
	}
	public static long getStoredFirstRun() {
		return getUserPreferences().getLong("firstRun", System.currentTimeMillis());
	}
	public static int getProjectLibreRunNumber() {
		return getUserPreferences().getInt("projectLibreRunNumber", 0);
	}
	public static long getProjectLibreFirstRun() {
		return getUserPreferences().getLong("projectLibreFirstRun", System.currentTimeMillis());
	}
	public static String getRunSinceMessage() {
		return MessageFormat.format(
				Messages.getString("Text.runsSinceMessage"),
				getStoredRunNumber(),
				new Date(getStoredFirstRun())
		);
	}

}
