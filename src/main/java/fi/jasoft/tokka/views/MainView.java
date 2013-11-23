package fi.jasoft.tokka.views;

import java.util.List;

import com.vaadin.addon.touchkit.ui.NavigationButton;
import com.vaadin.addon.touchkit.ui.NavigationView;
import com.vaadin.addon.touchkit.ui.VerticalComponentGroup;
import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

import fi.jasoft.tokka.model.TokkaList;

public class MainView extends NavigationView {

	VerticalComponentGroup group = new VerticalComponentGroup("Tokka");
	
	// Data store for demo purposes.
	private final List<TokkaList> lists;
	
	public MainView(List<TokkaList> lists) {
		this.lists = lists;		
		setCaption("Tokka");
		
		// Could full size be default since I am always doing this?
		group.setSizeFull();
				
		setRightComponent(new NavigationButton("Edit", new EditView(this, this.lists)));
	}
	
	/*
	 * Why do I need to override this method just so I can put some data in the 
	 * VerticalComponentGroup? Was expecting some kind of listener or abstract 
	 * init method instead.
	 */
	@Override
	protected void onBecomingVisible() {
		super.onBecomingVisible();
		
		// Would rather use a listener than overriding methods
		updateList();
	}
	
	private void updateList() {

		group.removeAllComponents();
		
		if(lists.size() > 0) {
			// Re-Populate view
			//List<TokkaList> lists = ((TokkaUI)getUI()).getLists();
			for(final TokkaList list : lists) {
				HorizontalLayout hl = new HorizontalLayout();				
				hl.setWidth("100%");
				
				Label lbl = new Label(list.getName());
				lbl.setSizeUndefined();				
				hl.addComponent(lbl);
				
				lbl = new Label(list.getUndoneItems()+"/"+list.getTotalItems());
				lbl.setSizeUndefined();				
				hl.addComponent(lbl);
				
				hl.setExpandRatio(hl.getComponent(0), 1);
				
				hl.addLayoutClickListener(new LayoutClickListener() {
					
					@Override
					public void layoutClick(LayoutClickEvent event) {
						getNavigationManager().navigateTo(new ItemView(list, MainView.this));						
					}
				});
				
				group.addComponent(hl);
			}	
		} else {
			group.addComponent(new Label("No items added yet."));					
		}		
	}
}
