package geeksforgeeks;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

public class QuickSortEntityTest
{

	@Before
	public void setUp() throws Exception
	{
	}

	@Test
	public void testSimpleLomuto()
	{
		Integer[] expects = new Integer[] { 10, 30, 40, 50, 70, 80, 99 };
		ArrayList<Integer> list = new ArrayList<>(Arrays.asList(10, 80, 30, 99, 40, 50, 70));

		QuickSortEntity.ISorter sorter = QuickSortEntity.CreateSorter(QuickSortEntity.PartitionMethod.Lomuto);
		sorter.sort(list, 0, list.size() - 1);
		
		assertArrayEquals(expects, list.toArray());
	}
	
	@Test
	public void testSimpleHoare()
	{
		Integer[] expects = new Integer[] { 10, 30, 40, 50, 70, 80, 99 };
		ArrayList<Integer> list = new ArrayList<>(Arrays.asList(10, 80, 30, 99, 40, 50, 70));

		QuickSortEntity.ISorter sorter = QuickSortEntity.CreateSorter(QuickSortEntity.PartitionMethod.Hoare);
		sorter.sort(list, 0, list.size() - 1);
		
		assertArrayEquals(expects, list.toArray());
	}
	
	@Test
	public void testCompareLomutoHoare()
	{
		int sampleSize = 1200;
		
		Random rd = new Random(); // creating Random object
		Integer[] arr = new Integer[sampleSize];
		for (int i = 0; i < arr.length; i++)
		{
			arr[i] = rd.nextInt(Integer.MAX_VALUE);
			// System.out.println(arr[i]);
		}
		
		QuickSortEntity.ISorter sorter;

		ArrayList<Integer> list1 = new ArrayList<Integer>();
		Collections.addAll(list1, Arrays.copyOf(arr, arr.length));
		sorter = QuickSortEntity.CreateSorter(QuickSortEntity.PartitionMethod.Lomuto);
		sorter.sort(list1, 0, list1.size() - 1);
		
		ArrayList<Integer> list2 = new ArrayList<Integer>();
		Collections.addAll(list2, Arrays.copyOf(arr, arr.length));
		sorter = QuickSortEntity.CreateSorter(QuickSortEntity.PartitionMethod.Hoare);
		sorter.sort(list2, 0, list2.size() - 1);
		
		assertArrayEquals(list1.toArray(), list2.toArray());
	}
}
