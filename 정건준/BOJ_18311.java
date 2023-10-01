import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/***
 * [문제]
 * 선수는 N개의 코스들을 왕복, 이때 이동 거리 K가 주어졌을때, 현재 지나고 있는 코스의 번호 출력
 * K가 두 코스 사이에 위치한 경우 지나야할 코스의 번호 출력
 * N(코스의 개수, 1<=N<=100,000)
 * K(이동 거리, 0<=K<=왕복 거리-1)
 * 코스 길이(1<=코스 길이<=50,000)
 *
 * [변수]
 * int N
 * long K
 * int[] courseLength;
 *
 * [풀이]
 * courseLength를 입력받음
 *
 * long start = 0
 * long end = 0
 * for(int i=0; i<N; i++)
 *      end += courseLength[i]
 *      if(start <= K < end)
 *          sout(i+1)
 *          system.exit(0)
 *      start = end
 *
 * for(int i=N-1; i>=0; i--)
 *      end += courseLength[i]
 *      if(start <= K < end)
 *          sout(i+1)
 *          system.exit(0)
 *      start = end
 */

public class BOJ_18311 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");

        int N = Integer.parseInt(st.nextToken());
        long K = Long.parseLong(st.nextToken());
        int[] courseLength = new int[N];

        st = new StringTokenizer(br.readLine(), " ");
        for(int i=0; i<N; i++) courseLength[i] = Integer.parseInt(st.nextToken());

        long start = 0;
        long end = 0;

        for(int i=0; i<N; i++) {
            end += courseLength[i];
            if(start <= K && K < end) {
                System.out.println(i+1);
                System.exit(0);
            }
            start = end;
        }

        for(int i=N-1; i>=0; i--) {
            end += courseLength[i];
            if(start <= K && K < end) {
                System.out.println(i+1);
                System.exit(0);
            }
            start = end;
        }
    }
}
