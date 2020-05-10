/**
 * 
 */
package features;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author ishaqkhan
 *
 */
public class BreadthFirstSearch {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List<Integer> g1 = Arrays.asList(new Integer[]{1,0,0,0,0});
		List<Integer> g2 = Arrays.asList(new Integer[]{0,1,0,0,0});
		List<Integer> g3 = Arrays.asList(new Integer[]{0,0,1,0,0});
		List<Integer> g4 = Arrays.asList(new Integer[]{0,0,0,1,0});
		List<Integer> g5 = Arrays.asList(new Integer[]{0,0,0,0,1});
		List<List<Integer>> grid = new ArrayList<>();
		grid.add(g1);grid.add(g2);grid.add(g3);grid.add(g4);grid.add(g5);
		System.out.println(minimumHours(grid));

	}

	public static int minimumHours(List<List<Integer>> grid) {
		
		int rows = grid.size();
		if(rows == 0)
			return -1;
		
		int cols = grid.get(0).size();
		Queue<int[]> q = new LinkedList();
		int zombies = 0,hours = 0;
		int target = rows * cols;
		for(int i = 0 ; i<rows; i++) {
			for(int j = 0 ; j<cols; j++) {
				if(grid.get(i).get(j) == 1) {
					zombies++;
					q.offer(new int[] {i,j});
				}
			}
		}
		
		int[][] dir = {{0,1}, {0,-1}, {1,0}, {-1,0}};
		while(!q.isEmpty()) {
			int size = q.size();
			if(zombies == target)
				return hours;
			for(int k = 0; k<size; k++) {
				int[] curr = q.poll();
				int r = curr[0];
				int c = curr[1];
				for(int[] adj : dir) {
					int newRow = r + adj[0];
					int newCol = c + adj[1];
					if(newRow >=0 && newRow<rows && newCol >=0 && newCol<cols && grid.get(newRow).get(newCol) == 0) {
						zombies++;
						q.offer(new int[] {newRow, newCol});
						grid.get(newRow).set(newCol, 1);
					}
				}
			}
			hours++;
		}
		return -1;
	}
}
