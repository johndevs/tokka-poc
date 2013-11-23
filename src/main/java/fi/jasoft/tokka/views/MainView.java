package fi.jasoft.tokka.views;

import java.util.List;

import com.vaadin.addon.touchkit.ui.NavigationButton;
import com.vaadin.addon.touchkit.ui.NavigationView;
import com.vaadin.addon.touchkit.ui.VerticalComponentGroup;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Button.ClickEvent;

import fi.jasoft.tokka.TokkaUI;
import fi.jasoft.tokka.model.TokkaList;


// Following example at https://vaadin.com/book/-/page/mobile.html

public class MainView extends NavigationView {

	VerticalComponentGroup group = new VerticalComponentGroup("Tokka");
	
	private final List<TokkaList> lists;
	
	public MainView(List<TokkaList> lists) {
		this.lists = lists;
		
		/*
		 *  Why cannot I just set the VerticalComponentGroup as content? 
		 */
		group.setSizeFull();
		setContent(group);

		
		setRightComponent(new NavigationButton("Edit", new EditView(this, lists)));
	}
	
	/*
	 * Why do I need to override this method just so I can put some data in the 
	 * VerticalComponentGroup? Was expecting some kind of listener or abstract 
	 * init method instead.
	 */
	@Override
	protected void onBecomingVisible() {
		super.onBecomingVisible();
		
		group.removeAllComponents();
		
		// Re-Populate view
		//List<TokkaList> lists = ((TokkaUI)getUI()).getLists();
		for(TokkaList list : lists) {
			HorizontalLayout hl = new HorizontalLayout();
			hl.addComponent(new Label(list.getName()));
			hl.addComponent(new Label(list.getUndoneItems()+"/"+list.getTotalItems()));
			hl.setExpandRatio(hl.getComponent(0), 1);
			group.addComponent(hl);
		}	
	}
	
}
