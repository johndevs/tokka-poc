package fi.jasoft.tokka.model;

public class TokkaList {

	private String name;
	
	private java.util.List<TokkaItem> items;

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
}
