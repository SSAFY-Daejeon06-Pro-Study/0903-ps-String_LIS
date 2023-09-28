import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.StringTokenizer;

/**
 * 풀이 시작 : 10:17
 * 풀이 완료 : 11:15
 * 풀이 시간 : 58분
 *
 * 문제 해석
 * 전깃줄 1과 동일
 * 하지만 구해야 하는 것에 전깃줄의 위치가 추가됨
 *
 * 구해야 하는 것
 * 없애야 하는 전깃줄의 최소 갯수
 * 없애야 하는 전깃줄의 A 전봇대 위치
 *
 * 문제 입력
 * 첫째 줄 : 두 전봇대 사이의 전깃줄 개수 M
 * 둘째 줄 ~ M개 줄 : 전깃줄이 연결되는 위치 A, B
 *
 * 제한 요소
 * 1 <= M <= 100000
 * 1 <= A[i], B[i] <= 500000
 * 같은 곳에 두 개의 전깃줄 없음
 *
 * 생각나는 풀이
 * LIS nlogn -> lower_bound 이용
 * LIS 배열의 각 인덱스가 바뀔 때마다 이전 값의 a좌표를 저장하는 배열 둠
 *
 * 구현해야 하는 기능
 * 1. 입력에 따른 전깃줄 저장
 * 2. A전봇대 위치 기준 오름차순 정렬
 * 3. LIS 구하기
 *  3-1. LIS 배열에 저장할 때마다 현재 인덱스 바로 이전 위치를 저장해둠
 */
public class BOJ_2568_전깃줄2 {
    static int N;
    static Pair[] pairs;
    static Pair[] LIS;
    static class Pair {
        int a, b;
        Pair prev;

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
        LIS = new Pair[N + 1];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            pairs[i] = new Pair(a, b);
        }

        Arrays.sort(pairs, (a, b) -> a.a - b.a);

        LIS[1] = pairs[0];
        int length = 1;
        for (int i = 1; i < N; i++) {
            int idx;
            if (LIS[length].b < pairs[i].b) idx = ++length;
            else idx = binarySearchLowerBound(pairs[i].b, length);
            LIS[idx] = pairs[i];
            pairs[i].prev = LIS[idx - 1];
        }

        StringBuilder sb = new StringBuilder();
        sb.append(N - length).append('\n');
        HashSet<Integer> set = new HashSet<>();
        for (Pair now = LIS[length]; now != null; now = now.prev) {
            set.add(now.a);
        }

        for (int i = 0; i < N; i++) {
            if (set.contains(pairs[i].a)) continue;
            sb.append(pairs[i].a).append('\n');
        }
        System.out.println(sb);
    }

    private static int binarySearchLowerBound(int value, int hi) {
        int lo = 1;
        while (lo < hi) {
            int mid = (lo + hi) >> 1;
            if (LIS[mid].b < value) lo = mid + 1;
            else hi = mid;
        }
        return lo;
    }

}