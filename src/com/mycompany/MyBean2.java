package com.mycompany;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class MyBean2 {
	
	private InetAddress local = null;

	public InetAddress getLocal() {
		return local;
	}

	public void setLocal(InetAddress local) {
		this.local = local;
	}
	
	public void setLocal(String localHost) {
		try {
			local = InetAddress.getByName(localHost);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private InetAddress remote = null;

	public InetAddress getRemote() {
		return remote;
	}

	public void setRemote(InetAddress remote) {
		this.remote = remote;
	}
	
	public void host(String remoteHost) {
		try {
			remote = InetAddress.getByName(remoteHost);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
