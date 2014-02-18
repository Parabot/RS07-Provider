package org.oss.parser;

import java.net.URL;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.parabot.environment.api.utils.WebUtil;

public class WebParser {
	private URL webClient;
	private String pageSource;
	private final Pattern parametersPattern;
	private final Pattern gamePackPattern;
	
	private Properties parameters;
	private URL jarLocation;
	
	public WebParser(URL webClient) {
		this.webClient = webClient;
		this.pageSource = WebUtil.getContents(webClient);
		this.parametersPattern = Pattern
				.compile("<param name=\"([^\\s]+)\"\\s+value=\"([^>]*)\">");
		this.gamePackPattern = Pattern.compile("archive=(.+?) '");
	}
	
	public URL getJar() {
		if(jarLocation != null) {
			return jarLocation;
		}
		
		final Matcher matcher = gamePackPattern.matcher(pageSource);
		if (!matcher.find()) {
			throw new RuntimeException("Failed to fetch gamepack location.");
		}
		String jarLoc = matcher.group(1);
		try {
			return jarLocation = new URL(webClient.toString() + jarLoc);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public Properties getParameters() {
		if(parameters != null) {
			parameters = new Properties();
		}
		
		parameters = new Properties();
		
		Matcher matcher = parametersPattern.matcher(pageSource);
		while (matcher.find()) {
			final String key = matcher.group(1);
			final String value = matcher.group(2);
			parameters.put(key, value);
		}
		// meh
		parameters.put("haveie6", "false");
		
		return parameters;
	}

}
