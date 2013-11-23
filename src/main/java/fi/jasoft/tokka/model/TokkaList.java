package fi.jasoft.tokka.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TokkaList {

	private String name;
	
	private List<TokkaItem> items = new ArrayList<>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public int getTotalItems(){
		return items.size();
	}
	
	public int getUndoneItems(){
		int count = 0;
		for(TokkaItem item : items){
			count += item.isDone() ? 1 : 0;
		}
		return count;
	}
	
	public void addItem(TokkaItem item) {
		items.add(item);
	}
	
	public void removeItem(TokkaItem item) {
		items.remove(item);
	}
	
	public List<TokkaItem> getItems() {
		return Collections.unmodifiableList(items);
	}
}
