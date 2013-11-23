package fi.jasoft.tokka.views;

import com.vaadin.addon.touchkit.ui.NavigationButton;
import com.vaadin.addon.touchkit.ui.NavigationView;
import com.vaadin.addon.touchkit.ui.Switch;
import com.vaadin.addon.touchkit.ui.Toolbar;
import com.vaadin.addon.touchkit.ui.VerticalComponentGroup;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.event.ShortcutListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import fi.jasoft.tokka.model.TokkaItem;
import fi.jasoft.tokka.model.TokkaList;

public class ItemView extends NavigationView {

	VerticalComponentGroup group = new VerticalComponentGroup();
	
	private final TokkaList list;
	
	private boolean showDoneItems = true;
	
	private boolean showUndoneItems = true;
	
	public ItemView(TokkaList list, MainView main) {
		this.list = list;			
		
		setCaption("Work todo");
		
		setLeftComponent(new NavigationButton("...", main));
			
		VerticalLayout vl = new VerticalLayout();
		vl.setSizeFull();
		vl.setSpacing(true);
		setContent(vl);
				
		final TextField field = new TextField();
		field.setInputPrompt("New item");	
		field.addShortcutListener(new ShortcutListener(null,KeyCode.ENTER,null) {
			
			@Override
			public void handleAction(Object sender, Object target) {
				TokkaItem item = new TokkaItem();
				item.setName(field.getValue());
				ItemView.this.list.addItem(item);			
				updateList();				
			}
		});		
		vl.addComponent(field);		
		vl.setComponentAlignment(field, Alignment.MIDDLE_CENTER);

		// Could full size be default since I am always doing this?
		group.setSizeFull();
		
		vl.addComponent(group);
		vl.setExpandRatio(group, 1);
		
		buildToolbar();
	}
	
	private void buildToolbar(){
		Toolbar toolbar = new Toolbar();
		toolbar.addComponent(new Button("All", new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				showDoneItems = true;
				showUndoneItems = true;
				updateList();				
			}
		}));
		toolbar.addComponent(new Button("Todo", new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				showDoneItems = false;
				showUndoneItems = true;
				updateList();				
			}
		}));
		toolbar.addComponent(new Button("Done", new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				showDoneItems = true;
				showUndoneItems = false;
				updateList();				
			}
		}));
		setToolbar(toolbar);
	}
	
	@Override
	protected void onBecomingVisible() {	
		super.onBecomingVisible();
		
		// Would rather use a listener than overriding methods
		updateList();
	}
	
	private void updateList(){
		group.removeAllComponents();
		
		for(final TokkaItem item : list.getItems()) {			
			if((showDoneItems && item.isDone()) || (showUndoneItems && !item.isDone())){
				final HorizontalLayout hl = new HorizontalLayout();
				hl.setWidth("100%");
							
				Label lbl = new Label(item.getName());
				lbl.setSizeUndefined();				
				hl.addComponent(lbl);
						
				final Switch done = new Switch(null, item.isDone());
				done.addValueChangeListener(new ValueChangeListener() {
					
					@Override
					public void valueChange(ValueChangeEvent event) {
						item.setDone(done.booleanValue());					
					}
				});
				hl.addComponent(done);			
				
				hl.setExpandRatio(hl.getComponent(0), 1);		
				group.addComponent(hl);
			}			
		}					
	}
}
