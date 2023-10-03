import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 풀이 시작 : 9:54
 * 풀이 완료 : 10:16
 * 풀이 시간 : 22분
 *
 * 문제 해석
 * 전깃줄이 교차하지 않도록 만들어야 함
 * 가장 적은 전깃줄을 잘라야 함
 *
 * 구해야 하는 것
 * 전깃줄이 서로 교차하지 않게 하기 위해 없애야 하는 전깃줄의 최소 갯수
 *
 * 문제 입력
 * 첫째 줄 : 두 전봇대 사이의 전깃줄의 개수 M
 * 둘째 줄 ~ M개 줄 : 전깃줄이 연결되는 위치 A, B
 *
 * 제한 요소
 * 1 <= M <= 100
 * 1 <= A[i], B[i] <= 500
 * 하나의 칸에 하나의 전깃줄만 연결됨
 *
 * 생각나는 풀이
 * LIS
 *
 * 구현해야 하는 기능
 * 1. 입력에 따른 전깃줄 연결 위치 저장
 * 2. 하나의 전봇대 기준으로 정렬
 * 3. LIS 구하기
 */
public class BOJ_2565_전깃줄 {
    static int N;
    static Pair[] pairs;
    static int[] LIS;

    static class Pair {
        int a, b;

        public Pair(int a, int b) {
            this.a = a;
            this.b = b;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        N = Integer.parseInt(br.readLine());
        pairs = new Pair[N];
        LIS = new int[N + 1];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            pairs[i] = new Pair(a, b);
        }
        Arrays.sort(pairs, (a, b) -> a.a - b.a);

        LIS[1] = pairs[0].b;
        int length = 1;
        for (int i = 1; i < N; i++) {
            int value = pairs[i].b;
            if (LIS[length] < value) LIS[++length] = value;
            else LIS[binarySearchLowerBound(value, length)] = value;
        }

        System.out.println(N - length);
    }

    private static int binarySearchLowerBound(int value, int hi) {
        int lo = 0;
        while (lo < hi) {
            int mid = (lo + hi) >> 1;
            if (LIS[mid] < value) lo = mid + 1;
            else hi = mid;
        }
        return hi;
    }

}