package com.alexnedelcu.videoplayer;

public class Source {
	String name;
	String url;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Source(String name, String url) {
		this.name=name;
		this.url=name;
	}
}
