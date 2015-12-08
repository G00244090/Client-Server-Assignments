package org.aitek.collections.core;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.SwingWorker;

import org.aitek.collections.gui.Main;
import org.aitek.collections.gui.StatsPanel;
import org.aitek.collections.utils.Constants;

public class ListSample extends CollectionSample implements PropertyChangeListener {

	private ArrayList<Integer> arrayList;
	private CopyOnWriteArrayList<Integer> CopyOnWriteArray;
	private Stack<Integer> stack;
	private long[] times;
	private Task task;

	public ListSample(StatsPanel statsPanel, Main main) {

		super(statsPanel, main);
		COLLECTION_TYPES = 3;
		times = new long[COLLECTION_TYPES];
		arrayList = new ArrayList<Integer>(Constants.COLLECTION_MAX_SIZE * 1000);
		CopyOnWriteArray = new CopyOnWriteArrayList<Integer>();
		stack = new Stack<Integer>();

	}

	public HashSet<OperationType> getSupportedOperations() {

		HashSet<OperationType> set = new HashSet<OperationType>();

		set.add(OperationType.POPULATE);
		set.add(OperationType.INSERT);
		set.add(OperationType.REMOVE);
		set.add(OperationType.SEARCH);
		set.add(OperationType.ITERATE);
		set.add(OperationType.SORT);

		return set;
	}

	public void execute(OperationType operation) {

		this.currentOperation = operation;

		task = new Task();
		task.addPropertyChangeListener(this);
		task.execute();
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {

		statusBar.updateProgressBar(task.getProgress());
	}

	private class Task extends SwingWorker<Void, Void> {

		private double mult;

		@Override
		public Void doInBackground() {

			mult = 100d / iterations;
			switch (currentOperation) {
				case POPULATE:
					times = fillLists();
					statsPanel.setTimes("Populating", times);
				break;
				case INSERT:
					times = insertIntoLists();
					statsPanel.setTimes("Inserting new elements", times);
				break;
				case REMOVE:
					times = removeFromLists();
					statsPanel.setTimes("Removing existing elements", times);
				break;
				case SEARCH:
					times = searchLists();
					statsPanel.setTimes("Searching existing elements", times);
				break;
				case ITERATE:
					times = iterateOnLists();
					statsPanel.setTimes("Iterating elements", times);
				break;
				case SORT:
					times = sortLists();
					statsPanel.setTimes("Sorting elements", times);
				break;
			}

			return null;
		}

		@Override
		public void done() {

			main.setButtonsState();
			main.setReady();
		}

		private long[] fillLists() {

			long[] times = new long[COLLECTION_TYPES];
			main.setWorking("Filling list with " + getListFormattedSize() + " elements...");
			setProgress(0);

			for (int z = 0; z <= iterations; z++) {

				times = populateList();
				setProgress((int) (z * mult));
			}
			for (int z = 0; z < COLLECTION_TYPES; z++) {
				times[z] = times[z] / iterations / 1000;
			}

			return times;
		}

		private long[] populateList() {

			long times[] = new long[COLLECTION_TYPES];
			arrayList.clear();
			stack.clear();
			CopyOnWriteArray.clear();

			int[] toBeInserted = new int[listSize];
			for (int j = 0; j < getListSize(); j++) {
				toBeInserted[j] = (int) (Math.random() * listSize);
			}

			long startingTime = System.nanoTime();
			for (int j = 0; j < getListSize(); j++) {
				arrayList.add(toBeInserted[j]);
			}
			times[0] += System.nanoTime() - startingTime;

			startingTime = System.nanoTime();
			for (int j = 0; j < getListSize(); j++) {
				CopyOnWriteArray.add(toBeInserted[j]);
			}
			times[1] += System.nanoTime() - startingTime;

			startingTime = System.nanoTime();
			for (int j = 0; j < getListSize(); j++) {
				stack.add(toBeInserted[j]);
			}
			times[2] += System.nanoTime() - startingTime;

			return times;
		}

		private long[] insertIntoLists() {

			long[] times = new long[COLLECTION_TYPES];
			main.setWorking("Inserting elements into list...");
			setProgress(0);

			for (int z = 0; z <= iterations; z++) {

				long startingTime = System.nanoTime();
				for (int j = 0; j < 50; j++)
					arrayList.add(arrayList.size() / 2, 0);
				times[0] += System.nanoTime() - startingTime;

				startingTime = System.nanoTime();
				for (int j = 0; j < 50; j++)
					CopyOnWriteArray.add(CopyOnWriteArray.size() / 2, 0);
				times[1] += System.nanoTime() - startingTime;

				startingTime = System.nanoTime();
				for (int j = 0; j < 50; j++)
					stack.add(stack.size() / 2, 0);
				times[2] += System.nanoTime() - startingTime;

				setProgress((int) (z * mult));
			}
			for (int z = 0; z < COLLECTION_TYPES; z++) {
				times[z] = times[z] / iterations / 1000;
			}

			return times;
		}

		private long[] removeFromLists() {

			long[] times = new long[COLLECTION_TYPES];
			main.setWorking("Removing elements from list...");
			setProgress(0);

			for (int z = 0; z <= iterations; z++)
			{


				long startingTime = System.nanoTime();
				for (int j = 0; j < 10; j++) {
					arrayList.remove(0);
					arrayList.remove(arrayList.size() / 2);
					arrayList.remove(arrayList.size() - 1);


				}
				times[0] += System.nanoTime() - startingTime;



				startingTime = System.nanoTime();
				for (int j = 0; j < 10; j++) {
					CopyOnWriteArray.remove(0);
					CopyOnWriteArray.remove(CopyOnWriteArray.size() / 2);
					CopyOnWriteArray.remove(CopyOnWriteArray.size() - 1);
				}

				times[1] += System.nanoTime() - startingTime;

				startingTime = System.nanoTime();
				for (int j = 0; j < 10; j++) {
					stack.remove(0);
					stack.remove(stack.size() / 2);
					stack.remove(stack.size()-1);
				}
				times[2] += System.nanoTime() - startingTime;

				setProgress((int) (z * mult));
			}
			for (int z = 0; z < COLLECTION_TYPES; z++)
			{
				times[z] = times[z] / iterations / 1000;
			}
			return times;
		}

		private long[] searchLists() {

			long[] times = new long[COLLECTION_TYPES];
			main.setWorking("Searching elements in list...");
			setProgress(0);

			for (int z = 0; z <= iterations; z++) {

				long startingTime = System.nanoTime();
				for (int j = 0; j < 10; j++) {
					arrayList.get(0);
					arrayList.get(arrayList.size() / 2);
					arrayList.get(arrayList.size() - 1);
				}
				times[0] += System.nanoTime() - startingTime;

				startingTime = System.nanoTime();
				for (int j = 0; j < 10; j++) {
					CopyOnWriteArray.get(0);
					CopyOnWriteArray.get(CopyOnWriteArray.size() / 2);
					CopyOnWriteArray.get(CopyOnWriteArray.size() - 1);
				}
				times[1] += System.nanoTime() - startingTime;

				startingTime = System.nanoTime();
				for (int j = 0; j < 10; j++) {
					stack.get(0);
					stack.get(stack.size() / 2);
					stack.get(stack.size() - 1);
				}
				times[2] += System.nanoTime() - startingTime;

				setProgress((int) (z * mult));
			}
			for (int z = 0; z < COLLECTION_TYPES; z++) {
				times[z] = times[z] / iterations / 1000;
			}

			return times;
		}

		private long[] sortLists() {

			long[] times = new long[COLLECTION_TYPES];
			main.setWorking("Sorting elements...");
			setProgress(0);

			for (int z = 0; z <= iterations; z++) {

				populateList();

				long startingTime = System.nanoTime();
				Collections.sort(arrayList);
				times[0] += System.nanoTime() - startingTime;

				startingTime = System.nanoTime();
				Collections.sort(CopyOnWriteArray);
				times[1] += System.nanoTime() - startingTime;

				startingTime = System.nanoTime();
				Collections.sort(stack);
				times[2] += System.nanoTime() - startingTime;

				setProgress((int) (z * mult));
			}
			for (int z = 0; z < COLLECTION_TYPES; z++) {
				times[z] = times[z] / iterations / 1000;
			}

			return times;
		}

		private long[] iterateOnLists() {

			long[] times = new long[COLLECTION_TYPES];
			main.setWorking("Iterating on elements...");
			setProgress(0);

			for (int z = 0; z <= iterations; z++) {

				long startingTime = System.nanoTime();
				Iterator<Integer> iterator = arrayList.iterator();
				while (iterator.hasNext()) {
					iterator.next();
				}
				times[0] += System.nanoTime() - startingTime;

				startingTime = System.nanoTime();
				iterator = CopyOnWriteArray.iterator();
				while (iterator.hasNext()) {
					iterator.next();
				}
				times[1] += System.nanoTime() - startingTime;

				startingTime = System.nanoTime();
				iterator = stack.iterator();
				while (iterator.hasNext()) {
					iterator.next();
				}
				times[2] += System.nanoTime() - startingTime;
				setProgress((int) (z * mult));
			}

			for (int z = 0; z < COLLECTION_TYPES; z++) {
				times[z] = times[z] / iterations / 1000;
			}

			setProgress(100);

			return times;
		}

	}

	@Override
	public boolean isPopulated() {

		return stack.size() > 0;
	}

}
