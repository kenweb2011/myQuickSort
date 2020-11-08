package geeksforgeeks;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

//  https://en.wikipedia.org/wiki/Quicksort
//		Lomuto partition scheme
//      Hoare  partition scheme
// https://www.geeksforgeeks.org/quick-sort/
//      Lomuto partition scheme
// https://www.geeksforgeeks.org/hoares-vs-lomuto-partition-scheme-quicksort/
public class QuickSortEntity
{
	private final static boolean DEBUG_PRINT = false;

	enum PartitionMethod
	{
		Lomuto, Hoare
	};

	// Driver program
	public static void main(String args[])
	{
		// Integer arr[] = { 10, 7, 8, 9, 1, 5 };
		// Integer arr[] = { 10, 80, 30, 99, 40, 50, 70 };
		// int n = arr.length;

//		ArrayList<Integer> list1 = new ArrayList<>(Arrays.asList(10, 80, 30, 99, 40, 50, 70));
//		if (DEBUG_PRINT)
//		System.out.print("                   ");
//		if (DEBUG_PRINT)
//		for (int i = 0; i < list1.size(); ++i)
//			System.out.printf("%2d ", i);
//		if (DEBUG_PRINT)
//		System.out.println("");
//		QuickSortEntity.quickSort(list1, 0, list1.size() - 1, QuickSortEntity.PartitionMethod.Lomuto);
//		printArray(list1, String.format("          [%2d, %2d] ", 0, list1.size() - 1), QuickSortEntity.PartitionMethod.Lomuto.toString());
//
//		ArrayList<Integer> list2 = new ArrayList<>(Arrays.asList(10, 80, 30, 99, 40, 50, 70));
//		if (DEBUG_PRINT)
//		System.out.print("                   ");
//		if (DEBUG_PRINT)
//		for (int i = 0; i < list2.size(); ++i)
//			System.out.printf("%2d ", i);
//		if (DEBUG_PRINT)
//		System.out.println("");
//		QuickSortEntity.quickSort(list2, 0, list2.size() - 1, QuickSortEntity.PartitionMethod.Hoare);
//		printArray(list2, String.format("          [%2d, %2d] ", 0, list2.size() - 1), QuickSortEntity.PartitionMethod.Hoare.toString());

		sortRandomSamples(1 * 1000 * 1000);
		sortRandomSamples(10 * 1000 * 1000);
		System.out.println("Done.");
	}

	private static void sortRandomSamples(int sampleSize)
	{
		System.out.println("generating...");
		long begin = System.currentTimeMillis();
		Instant start = Instant.now();
		Random rd = new Random(); // creating Random object
		Integer[] arr = new Integer[sampleSize];
		for (int i = 0; i < arr.length; i++)
		{
			arr[i] = rd.nextInt(Integer.MAX_VALUE);
			// System.out.println(arr[i]);
		}
		long end = System.currentTimeMillis();
		Instant finish = Instant.now();
		long timeElapsed = Duration.between(start, finish).toMillis();

		System.out.printf("           ");
		System.out.printf("%.4f seconds (%.4f) to generate %,d integers\n", (end - begin) / 1000F, timeElapsed / 1000f,
				sampleSize);
		System.out.printf("           ");
		System.out.printf("           ");
		for (int i = 0; i < 5; ++i)
			System.out.printf("%d ", arr[i]);
		System.out.println();

		sortAllPartitionMethod(arr);

	}

	private static <T extends Comparable<T>> void sortAllPartitionMethod(T[] arr)
	{
		int n = arr.length;
		long begin, end;

		for (PartitionMethod partitionMethod : PartitionMethod.values())
		{
			T[] a = Arrays.copyOf(arr, n);
			ArrayList<T> list = new ArrayList<T>();
			Collections.addAll(list, a);

			System.out.println("sorting...");
			begin = System.currentTimeMillis();
			Instant start = Instant.now();

			ISorter sorter = CreateSorter(partitionMethod);
			sorter.sort(list, 0, n - 1);

			// System.out.printf("sorted array of size %d using %s\n", n, partitionMethod);
			// printArray(arr1, "", "");
			end = System.currentTimeMillis();
			Instant finish = Instant.now();
			long timeElapsed = Duration.between(start, finish).toMillis();
			System.out.printf("           ");
			System.out.printf("%.4f seconds (%.4f) to sort array of size %,d using %s\n", (end - begin) / 1000F,
					timeElapsed / 1000f, n, partitionMethod);
			System.out.printf("           ");
			System.out.printf("           ");
			for (int i = 0; i < 10; ++i)
				System.out.printf("%d ", list.get(i));
			System.out.println();
		}
	}

	public static ISorter CreateSorter(PartitionMethod partitionMethod)
	{
		switch (partitionMethod)
		{
		case Hoare:
			return new QuickSortEntity().new QuickSortHoare();

		default:
			return new QuickSortEntity().new QuickSortLomuto();
		}
	}

	private <T extends Comparable<T>> void Swap(ArrayList<T> array, int position1, int position2)
	{
		// Copy the first position's element
		T temp = array.get(position1);

		// Assign to the second element
		array.set(position1, array.get(position2));

		// Assign to the first element
		array.set(position2, temp);
	}

	/* A utility function to print array of size n */
	private <T extends Comparable<T>> void printArray(ArrayList<T> arr, String heading, String ending)
	{
		System.out.print(heading);

		int n = arr.size();
		for (int i = 0; i < n; ++i)
			System.out.printf("%2d ", arr.get(i));
		System.out.println(ending);
	}

	public interface ISorter
	{
		<T extends Comparable<T>> void sort(ArrayList<T> arr, int low, int high);
	}

	private class QuickSortLomuto implements ISorter
	{
		public <T extends Comparable<T>> void sort(ArrayList<T> arr, int low, int high)
		{
			if (DEBUG_PRINT)
				printArray(arr, String.format("Sort Beg: [%2d, %2d] ", low, high),
						" " + PartitionMethod.Lomuto.toString());

			if (low < high)
			{
				// pi is partitioning index, arr[pi] is now at right place
				int pi = partition(arr, low, high);

				// Recursively sort elements before partition and after partition
				sort(arr, low, pi - 1);
				sort(arr, pi + 1, high);
			}

			if (DEBUG_PRINT)
				System.out.printf("     End: [%2d, %2d] \n", low, high);
		}

		/*
		 * This function takes last element as pivot, places the pivot element at its
		 * correct position in sorted array, and places all smaller (smaller than pivot)
		 * to left of pivot and all greater elements to right of pivot
		 */
		private <T extends Comparable<T>> int partition(ArrayList<T> arr, int low, int high)
		{
			T pivot = arr.get(high);
			int i = (low - 1); // index of smaller element
			for (int j = low; j < high; j++)
			{
				// If current element is smaller than the pivot
				if (arr.get(j).compareTo(pivot) < 0)// arr[j] < pivot)
				{
					i++;
					Swap(arr, i, j);
				}

				if (DEBUG_PRINT)
					printArray(arr, String.format("          [%2d, %2d] ", i, j), String.format(" pivot <%2d>", pivot));
			}

			// swap arr[i+1] and arr[high] (or pivot)
			Swap(arr, i + 1, high);

			if (DEBUG_PRINT)
				printArray(arr, String.format("          [%2d, %2d] ", low, high),
						String.format(" partition returns %2d", (i + 1)));

			return i + 1;
		}

	}

	private class QuickSortHoare implements ISorter
	{
		public <T extends Comparable<T>> void sort(ArrayList<T> arr, int low, int high)
		{
			if (DEBUG_PRINT)
				printArray(arr, String.format("Sort Beg: [%2d, %2d] ", low, high),
						" " + PartitionMethod.Hoare.toString());

			if (low < high)
			{
				// pi is partitioning index, arr[pi] is now at right place
				int pi = partition(arr, low, high);

				// Recursively sort elements before partition and after partition
				sort(arr, low, pi);
				sort(arr, pi + 1, high);
			}

			if (DEBUG_PRINT)
				System.out.printf("     End: [%2d, %2d] \n", low, high);
		}

		/*
		 * This function takes first element as pivot, and places all the elements
		 * smaller than the pivot on the left side and all the elements greater than the
		 * pivot on the right side. It returns the index of the last element on the
		 * smaller side
		 */
		private <T extends Comparable<T>> int partition(ArrayList<T> arr, int low, int high)
		{
			T pivot = arr.get(low);
			int i = low - 1, j = high + 1;

			while (true)
			{
				// Find leftmost element greater than or equal to pivot
				do
				{
					i++;
				} while (arr.get(i).compareTo(pivot) < 0); // < pivot);

				// Find rightmost element smaller than or equal to pivot
				do
				{
					j--;
				} while (arr.get(j).compareTo(pivot) > 0); // > pivot);

				// If two pointers met.
				if (i >= j)
				{
					if (DEBUG_PRINT)
						printArray(arr, String.format("          [%2d, %2d] ", low, high),
								String.format(" partition returns %2d", j));
					return j;
				}

				Swap(arr, i, j);
				if (DEBUG_PRINT)
					printArray(arr, String.format("          [%2d, %2d] ", i, j), String.format(" pivot <%2d>", pivot));
			}
		}

	}
}
