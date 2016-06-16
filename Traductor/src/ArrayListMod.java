import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class ArrayListMod<E> extends ArrayList<E>{

	public ArrayListMod() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ArrayListMod(Collection<? extends E> c) {
		super(c);
		// TODO Auto-generated constructor stub
	}

	public ArrayListMod(int initialCapacity) {
		super(initialCapacity);
		
		// TODO Auto-generated constructor stub
	}
	
	public boolean add(E e){
		if (!this.contains(e))
			super.add(e);
		return true;
	}
	

	
	public boolean addAll(Collection<? extends E> c){
		Iterator it = c.iterator();
		while (it.hasNext())
			this.add((E) it.next());
		return true;
	}
	
	public boolean containsAny(Collection<? extends E> c){
		Iterator it = c.iterator();
		
		boolean flag=false;
		while (it.hasNext() && !flag)
			if(this.contains(it.next()))
					flag=true;
		return flag;
	}
	
	

}
