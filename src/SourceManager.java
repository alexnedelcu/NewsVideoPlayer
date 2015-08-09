import java.util.ArrayList;


public class SourceManager {
	ArrayList<Source> sources = new ArrayList<Source>();
	static SourceManager instance;
	
	public static SourceManager getInstance() {
		if (instance==null)
			instance  = new SourceManager();
		
		return instance;
	}
	
	public void addSource(Source s){
		sources.add(s);
	}
	public void addSource(String name, String url) {
		Source s = new Source(name, url);
		this.addSource(s);
	}
}
