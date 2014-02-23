package org.oss;

import java.applet.Applet;
import java.net.MalformedURLException;
import java.net.URL;

import org.oss.parser.WebParser;
import org.oss.stub.OssStub;
import org.parabot.core.Context;
import org.parabot.core.asm.ASMClassLoader;
import org.parabot.environment.servers.ServerManifest;
import org.parabot.environment.servers.ServerProvider;
import org.parabot.environment.servers.Type;

/**
 * 
 * @author Everel
 *
 */
@ServerManifest(author = "Everel", name = "Oldschool runescape", type = Type.LOADER, version = 0.1)
public class Provider extends ServerProvider {
	private WebParser parser;

	@Override
	public Applet fetchApplet() {
		try {
			final Context context = Context.getInstance();
			final ASMClassLoader classLoader = context.getASMClassLoader();
			final Class<?> clientClass = classLoader.loadClass("client");
			Object instance = clientClass.newInstance();
			Applet applet = (Applet) instance;
			applet.setStub(new OssStub(parser.getJar(), parser.getParameters()));
			return applet;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public URL getJar() {
		try {
			parser = new WebParser(new URL("http://oldschool2.runescape.com/"));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return parser.getJar();
	}

}
