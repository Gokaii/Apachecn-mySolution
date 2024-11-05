/**
 *
 */
package com.allendowney.thinkdast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Implementation of a HashMap using a collection of MyLinearMap and
 * resizing when there are too many entries.
 *
 * @author downey
 * @param <K>
 * @param <V>
 *
 */
public class MyHashMap<K, V> extends MyBetterMap<K, V> implements Map<K, V> {

	// average number of entries per map before we rehash
	protected static final double FACTOR = 1.0;

	@Override
	public V put(K key, V value) {
		V oldValue = super.put(key, value);

		//System.out.println("Put " + key + " in " + map + " size now " + map.size());

		// check if the number of elements per map exceeds the threshold
		if (size() > maps.size() * FACTOR) {
			rehash();
		}
		return oldValue;
	}

	/**
	 * Doubles the number of maps and rehashes the existing entries.
	 */
	/**
	 *
	 */
	protected void rehash() {
		// TODO: FILL THIS IN!
		int k = this.maps.size();
		for (int i=0; i<k; i++) {
			maps.add(new MyLinearMap<K, V>());
		}
		List<Entry<K,V>> allEntries = new ArrayList<>(); 
		for(MyLinearMap<K,V> map : maps){
			allEntries.addAll(map.getEntries());
		}
		for(MyLinearMap<K,V> map : maps){
			map.clear();
		}
		for(Entry<K,V> e : allEntries){
			super.put(e.getKey(),e.getValue());
		}
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Map<String, Integer> map = new MyHashMap<String, Integer>();
		for (int i=0; i<10; i++) {
			map.put(new Integer(i).toString(), i);
		}
		Integer value = map.get("3");
		System.out.println(value);
	}
}
