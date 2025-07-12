package com.projectlibre1.pm.graphic.frames;

import java.awt.Container;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

import javax.swing.JFrame;

import com.projectlibre1.configuration.Settings;
import com.projectlibre1.cookie.CookieUtils;
import com.projectlibre1.util.Environment;
import com.projectlibre1.util.FontUtil;

public class ApplicationStartupFactory extends StartupFactory {

	// TODO: Refactor
	public ApplicationStartupFactory(Map<String, Object> opts) {

		CookieUtils.disableCookieHandler();

		this.opts = opts;

		dumpOpts();

		serverUrl = getOpt("serverUrl");

		if (serverUrl == null) {
            serverUrl = defaultServerUrl;
        }

		String projectIdS = getOpt("projectId");

		if ( projectIdS != null) projectId = Long.parseLong(projectIdS);

		String font=(String)getOpt("font");

		if (font==null){
			String javaVendor=System.getProperty("java.vendor");
			if (javaVendor.startsWith("IBM")){ //to avoid font bug on SLED with IBM jvm
				font=FontUtil.getValidFont(new String[]{"DejaVu Sans","Andale Sans"}); //Lucida Sans
			}
		}else{
			font=font.replace('_', ' ');
		}
		//FontUtil.listFonts();
		if (font!=null){
			Environment.resetFonts();
			Environment.setFont(font,Environment.DEFAULT_FONT);
			FontUtil.setUIFont(font);
		}

		Object o=opts.get("fileNames");
		List fileNames;
		if (o==null) fileNames=null;
		else if (o instanceof List){
			fileNames=(List)o;
		}else{
			fileNames=new ArrayList(1);
			fileNames.add(o);
		}

		if (fileNames!=null) projectUrls=(String[])fileNames.toArray(new String[]{});


		if (Settings.VERSION_TYPE_STANDALONE.equals(getOpt("versionType"))) Environment.setStandAlone(true);

	}

	protected void abort() {
		System.exit(-1);
	}

	protected void getCredentials() {
		String authType=getOpt("credentials",0);
		if (authType!=null){
			if ("login".equals(authType)){
				login=getOpt("credentials",1);
				password=getOpt("credentials",2);
			} else if ("session".equals(authType)){
				String partnerConnectionString =getOpt("credentials",2);
				String timestamp=getOpt("timestamp");
				long d=0L;
				if (timestamp!=null){
					try {
						d=System.currentTimeMillis()-Long.parseLong(timestamp);
					} catch (NumberFormatException e) {}
				}
				String sessionId=getOpt("credentials",1);
				//if (sessionId!=null&&d<=SESSION_EXPIRATION)
				if (sessionId!=null||partnerConnectionString!=null)
				try{
					Properties props=new Properties();
					String urlString = serverUrl + "/" + Settings.WEB_APP + ((partnerConnectionString==null)?"":"/partner")+"/jnlp/projectlibre_credentials.jnlp";
					if (partnerConnectionString != null)
						urlString += "?"+ partnerConnectionString;
					URL url = new URL(urlString);
					HttpURLConnection http = (HttpURLConnection) url.openConnection();
					if (sessionId!=null) http.setRequestProperty("Cookie", "JSESSIONID=" + sessionId);
	//				if (partnerConnectionString == null) {
	//					http.setRequestMethod("POST");
	//				} else {
						http.setRequestMethod("GET");
	//				}
					http.connect();


					props.load(http.getInputStream());
					http.disconnect();

					login=props.getProperty("login");
					password=props.getProperty("password");
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	private String getOpt(String name){
		return getOpt(name, 0);
	}

	private String getOpt(String name, int index){
		if (index<0) return null;
		Object o=opts.get(name);
		if (o==null) return null;
		else if (o instanceof String) return (index==0)?((String)o):null;
		else if (o instanceof List){
			List lopt=(List)o;
			if (index>=lopt.size()) return null;
			return (String)lopt.get(index);
		}
		else return null;
	}

	public static Map<String, Object> extractOpts(String[] args){
		if (args == null || args.length == 0) {
			return new HashMap<>();
		}

		if (isLegacyFormat(args)) {
			return parseLegacyArgs(args);
		}

		return parseArgs(args);
	}

	public void dumpOpts() {
		System.out.println("opts:");
        for (Object o : opts.keySet()) {
            String opt = (String) o;
            System.out.println(opt + ":");
            String arg;
            int index = 0;
            while ((arg = getOpt(opt, index++)) != null) System.out.println("\t" + arg);
        }
	}

	public void doPostInitView(Container container) {
		if (!Environment.isPlugin()) ((JFrame)container).pack();
	}

	private static boolean isLegacyFormat(String[] args) {
		String firstArg = args[0];
		return firstArg != null && firstArg.length() > 1 && (!firstArg.startsWith("--"));
	}

	private static Map<String, Object> parseLegacyArgs(String[] args) {
		Map<String, Object> opts = new HashMap<>();

		if (args.length >= 4) {
			opts.put("serverUrl", args[0]);

			if ("login".equals(args[1])) {
				List<String> credentials = Arrays.asList(args[1], args[2], args[3]);
				opts.put("credentials", credentials);
			}
		}

		return opts;
	}

	private static Map<String, Object> parseArgs(String[] args) {
		Map<String, Object> opts = new HashMap<>();

		String currentKey = null;
		String pendingValue = null;
		List<String> valueList = null;

		for (String arg : args) {
			if (arg.startsWith("--") && arg.length() > 2) {
				// Salva a opção anterior
				if (currentKey != null) {
					if (valueList != null) {
						opts.put(currentKey, valueList);
					} else if (pendingValue != null) {
						opts.put(currentKey, pendingValue);
					}
				}

				currentKey = arg.substring(2);
				pendingValue = null;
				valueList = null;
			} else {
				if (valueList != null) {
					valueList.add(arg);
				} else if (pendingValue != null) {
					valueList = new LinkedList<>();
					valueList.add(pendingValue);
					valueList.add(arg);
					pendingValue = null;
				} else {
					pendingValue = arg;
				}
			}
		}

		if (currentKey != null) {
			if (valueList != null) {
				opts.put(currentKey, valueList);
			} else if (pendingValue != null) {
				opts.put(currentKey, pendingValue);
			}
		}

		return opts;
	}
}
