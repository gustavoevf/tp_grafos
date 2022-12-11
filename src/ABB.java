import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class ABB<T> {

    private TreeMap<Integer, T> data;

    public ABB(){
        this.data = new TreeMap<>();
    }

    public T find(int key){
        return this.data.get(key);
    }

    public boolean add(int key, T newElement){
        boolean result = false;
        if(!this.data.containsKey(key)){
            this.data.put(key, newElement);
            result = true;
        }
        return result;
    }
    
    public int size(){
        return this.data.size();
    }

    public T[] allElements(T[] array){
        T[] allData = this.data.values().toArray(array);
        return allData;
    }

    public boolean remove(int key) {
        boolean result = false;
        if(this.data.containsKey(key)) {
            this.data.remove(key);
            result = true;
        }
        return result;
    }
    
    public void allValues(){
    	
    	System.out.print(this.data);
    }

    public Integer getKey(T item) {
        Map.Entry<Integer, String>[] entryArray
                = data.entrySet().toArray(
                new Map.Entry[data.entrySet().size()]);
        if(entryArray.length > 0) {
            for(int i =0; i < entryArray.length; i++) {
                if (entryArray[i] == item) {
                    return entryArray[i].getKey();
                }
            }
        }
        return null;
    }
}
