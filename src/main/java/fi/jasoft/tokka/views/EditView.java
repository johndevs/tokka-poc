package fi.jasoft.tokka.views;

import java.util.List;

import com.vaadin.addon.touchkit.ui.NavigationButton;
import com.vaadin.addon.touchkit.ui.NavigationView;
import com.vaadin.addon.touchkit.ui.Popover;
import com.vaadin.addon.touchkit.ui.VerticalComponentGroup;
import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window.CloseEvent;
import com.vaadin.ui.Window.CloseListener;

import fi.jasoft.tokka.model.TokkaList;

public class EditView extends NavigationView {
	
	VerticalComponentGroup group = new VerticalComponentGroup("Tokka");
	
	private final List<TokkaList> lists;
	
	private class NewListPopup extends Popover implements ClickListener {
		
		TextField field = new TextField("List name");	
		
		Button addBtn = new Button("Add", this);	
		
		public NewListPopup() {			
			setContent(new VerticalLayout(field, addBtn));						
		}

		@Override
		public void buttonClick(ClickEvent event) {						
			TokkaList list = new TokkaList();
			list.setName(field.getValue());			
			lists.add(list);
			close();			
		}		
	}	
	
	public EditView(MainView main, List<TokkaList> lists) {
		this.lists = lists;	
		
		setCaption("Tokka");
		
		// Could full size be default since I am always doing this?
		group.setSizeFull();
		
		setContent(group);
		
		/*
		 * How am I supposed to use this without passing instances to the views here 
		 * and watching out for circular dependencies between the instances?
		 */
		setRightComponent(new NavigationButton("Done", main));
	}
	
	@Override
	protected void onBecomingVisible() {
		super.onBecomingVisible();				

		// Would rather use a listener than overriding methods
		updateList();
	}	
	
	private void updateList(){

		group.removeAllComponents();
		
		// Create new item button
		final Button newList = new Button("+ New list");		
		newList.setWidth("100%");
		newList.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				NewListPopup popup = new NewListPopup();
				popup.addCloseListener(new CloseListener() {
					
					@Override
					public void windowClose(CloseEvent e) {
						updateList();						
					}
				});
				
				popup.showRelativeTo(newList);					
			}
		});
		group.addComponent(newList);
		
		// Re-Populate view
		//List<TokkaList> lists = ((TokkaUI)getUI()).getLists();
		for(final TokkaList list : lists) {
			final HorizontalLayout hl = new HorizontalLayout();
			hl.setWidth("100%");
			hl.addComponent(new Button("X", new Button.ClickListener() {
				
				@Override
				public void buttonClick(ClickEvent event) {
					lists.remove(list);
					group.removeComponent(hl);					
				}
			}));
			hl.addComponent(new Label(list.getName()));
			hl.setExpandRatio(hl.getComponent(1), 1);
			hl.addLayoutClickListener(new LayoutClickListener() {
				
				@Override
				public void layoutClick(LayoutClickEvent event) {
					//(TokkaUI)getUI()).removeList(list);
					lists.remove(list);
					group.removeComponent(hl);
				}
			});
			group.addComponent(hl);
		}					
	}
}
