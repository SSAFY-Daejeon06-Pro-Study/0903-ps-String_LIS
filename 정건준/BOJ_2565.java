import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/***
 * [문제]
 * 전깃줄을 시작점 기준으로 정렬해 나열하고 전깃줄 수열에서 최장 증가 수열을 찾는 문제
 * 수열에서 순서상 앞에 있는 전깃줄 (x,y)가 있을때 뒷 순서의 전깃줄 중 x,y 두개의 수보다 큰 전깃줄은 (x,y)와 교차 X
 *
 * [풀이]
 * dp 접근
 * f(x) = x번째 전깃줄을 포함했을때, 최장 증가 수열의 개수
 * f(x) = Max(f(p)) + 1, (p = 1...x-1, p.s < x.s and p.e < x.e)
 */


public class BOJ_2565 {
    static class Line {
        int s;
        int e;
        public Line(int s, int e) {
            this.s = s;
            this.e = e;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int N = Integer.parseInt(br.readLine());
        Line[] lines = new Line[N];
        int[] dp = new int[N];

        for(int i=0; i<N; i++) {
            st = new StringTokenizer(br.readLine());
            int start = Integer.parseInt(st.nextToken());
            int end = Integer.parseInt(st.nextToken());
            lines[i] = new Line(start, end);
        }

        Arrays.sort(lines, (a,b)-> {
           return Integer.compare(a.s, b.s);
        });

        dp[0] = 1;
        for(int i=1; i<N; i++) {
            int maxDp = 0;
            for(int j=i-1; j>=0; j--) {
                if(lines[j].s < lines[i].s && lines[j].e < lines[i].e) {
                    maxDp = Math.max(maxDp, dp[j]);
                }
            }
            dp[i] = maxDp + 1;
        }

        int maxDp = 0;
        for(int i=0; i<N; i++) maxDp = Math.max(maxDp, dp[i]);
        System.out.println(N - maxDp);
    }
}
