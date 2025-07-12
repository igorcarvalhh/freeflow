package com.projectlibre1.pm.graphic.gantt;

import java.awt.Frame;
import java.util.Locale;
import java.util.Map;

import javax.swing.JOptionPane;

import com.projectlibre1.pm.graphic.frames.ApplicationStartupFactory;
import com.projectlibre1.pm.graphic.frames.MainFrameFactory;
import com.projectlibre1.preference.ConfigurationFile;
import com.projectlibre1.strings.Messages;
import com.projectlibre1.util.Environment;

public class Main {

	public static void main(String[] args) {
		configureSystemProperties();
		setDefaultLocale();

		Map<String, Object> options = ApplicationStartupFactory.extractOpts(args);

		if (isLinux() && isJavaVersionCompatible()) handleIncompatibleJava(options);

		Environment.setNewLook(false);

		// put before to initialize standalone flag
		ApplicationStartupFactory startupFactory = new ApplicationStartupFactory(options);

		Frame frame = MainFrameFactory.creareMainFrame(
				Messages.getContextString("Text.ApplicationTitle"),
				null,
				null
		);

		// TODO: see if project param exists in args
		boolean doWelcome = false;

		startupFactory.instanceFromNewSession(frame,doWelcome);
	}

	private static void configureSystemProperties() {
		System.setProperty("apple.awt.application.name","ProjectLibre");
		System.setProperty("apple.laf.useScreenMenuBar","true");
	}

	private static void setDefaultLocale() {
		Locale.setDefault(ConfigurationFile.getLocale());
	}

	private static String getOsName() {
		return System.getProperty("os.name").toLowerCase();
	}

	private static String getJavaVersion() {
		return System.getProperty("java.version");
	}

	private static boolean isLinux() {
		return getOsName().startsWith("linux");
	}

	private static boolean isJavaVersionCompatible() {
		String javaVersion = getJavaVersion();
		return Environment.compareJavaVersion(javaVersion,"1.5") < 0;
	}

	// TODO: Refactor
	private static void handleIncompatibleJava(Map<String, Object> options) {
		String javaVersion = getJavaVersion();
		String javaExec = ConfigurationFile.getRunProperty("JAVA_EXE");

		String message = Messages.getStringWithParam("Text.badJavaVersion", javaVersion);

		if (javaExec!=null && javaExec.length()>0) message+="\n"+Messages.getStringWithParam("Text.javaExecutable", new Object[]{javaExec,"JAVA_EXE","$HOME/.projectlibre/run.conf"});
		if (!options.containsKey("silentlyFail")) JOptionPane.showMessageDialog(null,message, Messages.getContextString("Title.ProjectLibreError"),JOptionPane.ERROR_MESSAGE);

		System.exit(64);
	}
}
