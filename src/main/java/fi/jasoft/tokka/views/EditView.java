package fi.jasoft.tokka.views;

import java.util.List;

import com.vaadin.addon.touchkit.ui.NavigationButton;
import com.vaadin.addon.touchkit.ui.NavigationView;
import com.vaadin.addon.touchkit.ui.Popover;
import com.vaadin.addon.touchkit.ui.VerticalComponentGroup;
import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

import fi.jasoft.tokka.TokkaUI;
import fi.jasoft.tokka.model.TokkaList;

public class EditView extends NavigationView {
	
	VerticalComponentGroup group = new VerticalComponentGroup("Tokka");
	
	private final List<TokkaList> lists;
	
	private class NewListPopup extends VerticalLayout implements ClickListener {
		
		public NewListPopup() {
			TextField field = new TextField("List name");				
			addComponent(field);
			
			Button addBtn = new Button("Add", this);
			addBtn.setSizeFull();
			addComponent(addBtn);
		}

		@Override
		public void buttonClick(ClickEvent event) {
			// TODO Auto-generated method stub
			
		}		
	}
	
	
	public EditView(MainView main, List<TokkaList> lists) {
		this.lists = lists;	
		
		/*
		 *  Why cannot I just set the VerticalComponentGroup as content?
		 */
		group.setSizeFull();
		setContent(group);

		/*
		 * How am I supposed to use this without passing instances here  and watching out for circular dependencies?
		 */
		setRightComponent(new NavigationButton("Done", main));
		
		final Button newList = new Button("+ New list");		
		newList.setSizeFull();
		newList.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				new Popover(new NewListPopup()).showRelativeTo(newList);;
				
			}
		});
		
		group.addComponent(newList);
		
	}
	
	@Override
	protected void onBecomingVisible() {
		super.onBecomingVisible();		

		group.removeAllComponents();
		
		// Re-Populate view
		//List<TokkaList> lists = ((TokkaUI)getUI()).getLists();
		for(final TokkaList list : lists) {
			final HorizontalLayout hl = new HorizontalLayout();
			hl.addComponent(new Button("X"));
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
