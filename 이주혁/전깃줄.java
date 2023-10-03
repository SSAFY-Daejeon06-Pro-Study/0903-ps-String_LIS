import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Stack;
import java.util.StringTokenizer;

/**
 * LIS 구현
 */
public class 전깃줄 {
	private static class Line implements Comparable<Line> {
		
		int start;
		int end;
		
		public Line(int start, int end) {
			this.start = start;
			this.end = end;
		}
		
		@Override
		public int compareTo(Line o) {
			return this.start - o.start; 
		}
		
	}
	
	public static void main(String[] args) throws IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		
		Line[] arr = new Line[N];
		int[][] LIS = new int[N][2];
		
		for(int i=0; i<N; i++) {
			
			StringTokenizer st = new StringTokenizer(br.readLine());
			int start = Integer.parseInt(st.nextToken());
			int end = Integer.parseInt(st.nextToken());
			
			arr[i] = new Line(start, end);
			
		}
		
		Arrays.sort(arr);
		
		int idx = 0;
		LIS[idx][0] = arr[0].start;
		LIS[idx][1] = arr[0].end;
		for(int i=1; i<N; i++) {
			if(arr[i].end > LIS[idx][1]) {
				LIS[++idx][0] = arr[i].start;
				LIS[idx][1] = arr[i].end;
			}
			else if(arr[i].end < LIS[idx][1]) {
				ChangeIndexWithlowerBound(idx, arr[i].start, arr[i].end, LIS);
				
			}
				
		}
		
		System.out.println(N - idx - 1);
		
		
	}

	private static void ChangeIndexWithlowerBound(int right, int idx, int insertVal, int[][] LIS) {
		
		int left = 0;
		
		while(left < right) {
			int mid = (left + right) / 2;
			if(LIS[mid][1] < insertVal) {
				left = mid + 1;
			}
			else if(LIS[mid][1] >= insertVal) {
				right = mid;
			} 
			
		}
		LIS[right][0] = idx;
		LIS[right][1] = insertVal;
		
	}
	
	
}