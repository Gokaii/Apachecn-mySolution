/**
 *
 */
package com.allendowney.thinkdast;

import java.util.Collection;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

/**
 * Implementation of a Map using a binary search tree.
 *
 * @param <K>
 * @param <V>
 *
 */
public class MyTreeMap<K, V> implements Map<K, V> {

	private int size = 0;
	private Node root = null;

	/**
	 * Represents a node in the tree.
	 *
	 */
	protected class Node {
		public K key;
		public V value;
		public Node left = null;
		public Node right = null;

		/**
		 * @param key
		 * @param value
		 * @param left
		 * @param right
		 */
		public Node(K key, V value) {
			this.key = key;
			this.value = value;
		}
	}

	@Override
	public void clear() {
		size = 0;
		root = null;
	}

	@Override
	public boolean containsKey(Object target) {
		return findNode(target) != null;
	}

	/**
	 * Returns the entry that contains the target key, or null if there is none.
	 *
	 * @param target
	 */
	private Node findNode(Object target) {
		// some implementations can handle null as a key, but not this one
		if (target == null) {
			throw new IllegalArgumentException();
		}

		// something to make the compiler happy
		@SuppressWarnings("unchecked")
		Comparable<? super K> k = (Comparable<? super K>) target;
		if(this.root == null){
			return null;
		}
		Node start = this.root;
		Node res = new Node(null, null);
		// TODO: FILL THIS IN!
		res = dig(k, start);
		return res;
	}

	private Node dig(Object target, Node node) {
		Comparable<? super K> k = (Comparable<? super K>) target;
		if(k.compareTo(node.key)<0){
			if(node.left == null){
				return null;
			}
			return dig(target,node.left);
		}
		if(k.compareTo(node.key)>0){
			if(node.right == null){
				return null;
			}
			return dig(target,node.right);
		}
		
		return node;
	}

	/**
	 * Compares two keys or two values, handling null correctly.
	 *
	 * @param target
	 * @param obj
	 * @return
	 */
	private boolean equals(Object target, Object obj) {
		if (target == null) {
			return obj == null;
		}
		return target.equals(obj);
	}

	@Override
	public boolean containsValue(Object target) {
		return containsValueHelper(root, target);
	}

	private boolean containsValueHelper(Node node, Object target) {
		// TODO: FILL THIS IN!
		if(node == null){
			return false;
		}
		if(equals(node.value, target)){
			return true;
		}
		if(containsValueHelper(node.left, target)){
			return true;
		}
		return	containsValueHelper(node.right, target);
		
		
	}

	private boolean iterKey(Node node, Set<K> set) {
		// TODO: FILL THIS IN!
		if(node == null){
			return false;
		}
		if(iterKey(node.left, set)){
			return true;
		}
		//System.out.println(node.value);
		set.add(node.key);
		return	iterKey(node.right, set);
	}

	


	@Override
	public Set<Map.Entry<K, V>> entrySet() {
		throw new UnsupportedOperationException();
	}

	@Override
	public V get(Object key) {
		Node node = findNode(key);
		if (node == null) {
			return null;
		}
		return node.value;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public Set<K> keySet() {
		Set<K> set = new LinkedHashSet<K>();
		iterKey(root,set);
		return set;
	}

	@Override
	public V put(K key, V value) {
		if (key == null) {
			throw new NullPointerException();
		}
		if (root == null) {
			root = new Node(key, value);
			size++;
			return null;
		}
		return putHelper(root, key, value);
	}

	private V putHelper(Node node, K key, V value) {
		// TODO: FILL THIS IN!
		this.size++;
		return putValue(node, key, value).value;
	}

	private Node putValue(Node node, K key, V value){
		Comparable<? super K> k = (Comparable<? super K>) key;

		if(k.compareTo(node.key)<0){
			if(node.left == null){
				Node newNode = new Node(key, value);
				node.left = newNode;
				return node;
			}
			else{
				node = node.left;
				return putValue(node, key, value);
			}
		}
		if(k.compareTo(node.key)>0){
			if(node.right == null){
				Node newNode = new Node(key, value);
				node.right = newNode;
				return node;
			}
			else{
				node = node.right;
				return putValue(node, key, value);
			}
		}
		else{
			Node oldNode = node;
			node.value = value;
			this.size--;
			return oldNode;
		}
	}


	@Override
	public void putAll(Map<? extends K, ? extends V> map) {
		for (Map.Entry<? extends K, ? extends V> entry: map.entrySet()) {
			put(entry.getKey(), entry.getValue());
		}
	}

	@Override
	public V remove(Object key) {
		// OPTIONAL TODO: FILL THIS IN!
		throw new UnsupportedOperationException();
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public Collection<V> values() {
		Set<V> set = new HashSet<V>();
		Deque<Node> stack = new LinkedList<Node>();
		stack.push(root);
		while (!stack.isEmpty()) {
			Node node = stack.pop();
			if (node == null) continue;
			set.add(node.value);
			stack.push(node.left);
			stack.push(node.right);
		}
		return set;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Map<String, Integer> map = new MyTreeMap<String, Integer>();
		map.put("Word1", 1);
		map.put("Word2", 2);
		Integer value = map.get("Word1");
		System.out.println(value);

		for (String key: map.keySet()) {
			System.out.println(key + ", " + map.get(key));
		}
	}

	/**
	 * Makes a node.
	 *
	 * This is only here for testing purposes.  Should not be used otherwise.
	 *
	 * @param key
	 * @param value
	 * @return
	 */
	public MyTreeMap<K, V>.Node makeNode(K key, V value) {
		return new Node(key, value);
	}

	/**
	 * Sets the instance variables.
	 *
	 * This is only here for testing purposes.  Should not be used otherwise.
	 *
	 * @param node
	 * @param size
	 */
	public void setTree(Node node, int size ) {
		this.root = node;
		this.size = size;
	}

	/**
	 * Returns the height of the tree.
	 *
	 * This is only here for testing purposes.  Should not be used otherwise.
	 *
	 * @return
	 */
	public int height() {
		return heightHelper(root);
	}

	private int heightHelper(Node node) {
		if (node == null) {
			return 0;
		}
		int left = heightHelper(node.left);
		int right = heightHelper(node.right);
		return Math.max(left, right) + 1;
	}
}
