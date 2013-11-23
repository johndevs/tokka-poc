package fi.jasoft.tokka;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;
import com.vaadin.ui.Label;
import com.vaadin.addon.touchkit.ui.NavigationManager;
import com.vaadin.annotations.Theme;

import fi.jasoft.tokka.model.TokkaList;
import fi.jasoft.tokka.views.EditView;
import fi.jasoft.tokka.views.MainView;

@Theme("mobiletheme")
public class TokkaUI extends UI{
	
	private List<TokkaList> lists = new ArrayList<>(); 
	
	@Override
	protected void init(VaadinRequest request){
		
		/*
		 * https://vaadin.com/book/-/page/mobile.html says setContent(new MainView()) but it 
		 * will not work if you want to navigate in your application
		 */		
		setContent(new NavigationManager(new MainView(lists)));
	}
	
	/*
	 * First had intended to use these but really cant since the views onBecomingVisible() 
	 * is returning NULL for getUI() so I cannot access these.
	 * 
	public List<TokkaList> getLists(){
		return Collections.unmodifiableList(lists);
	}
	
	public void addList(TokkaList list) {
		lists.add(list);
	}
	
	public void removeList(TokkaList list) {
		lists.remove(list);
	}
	-*/
}
