package org.oss.stub;

import java.applet.AppletContext;
import java.applet.AppletStub;
import java.net.URL;
import java.util.Properties;

public class OssStub implements AppletStub {
	private URL codeBase;
	private Properties parameters;
	
	public OssStub(URL codeBase, Properties parameters) {
		this.codeBase = codeBase;
		this.parameters = parameters;
	}

	@Override
	public void appletResize(int w, int h) {
		
	}

	@Override
	public AppletContext getAppletContext() {
		return null;
	}

	@Override
	public URL getCodeBase() {
		return codeBase;
	}

	@Override
	public URL getDocumentBase() {
		return getCodeBase();
	}

	@Override
	public String getParameter(String key) {
		return parameters.getProperty(key);
	}

	@Override
	public boolean isActive() {
		return false;
	}

}
