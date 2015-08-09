import java.awt.event.ActionEvent;


public abstract class Ctrl {
	String searchPhrase;
	SourceManager sourceManager = SourceManager.getInstance();
	Renderer renderer = Renderer.getInstance();
	
	public abstract void handleAction(ActionEvent e);
	
}
