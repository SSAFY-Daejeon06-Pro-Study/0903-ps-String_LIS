import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 풀이 시작 : 5:33
 * 풀이 완료 :
 * 풀이 시간 :
 *
 * 문제 해석
 * N개의 이어진 일직선상의 코스를 모두 지나 끝까지 도착한 후에 다시 출발 지점으로 돌아와야 함
 * 전체 코스를 지나고 있는 상황에서 이동거리가 K일 때 현재 지나고 있는 코스의 번호를 추력
 * 이동거리 K가 두 코스 사이에 위치한 경우 지나야 할 코스 번호 출력
 *
 * 구해야 하는 것
 * 현재 지나고 있는 코스
 *
 * 문제 입력
 * 첫째 줄 : N, K
 * 둘째 줄 : 각 코스의 길이
 *
 * 제한 요소
 * 1 <= N <= 100000
 * 1 <= l[i] <= 50000
 * 0 <= K <= 코스 총 길이
 *
 * 생각나는 풀이
 * 각 구간 시작점 저장하고 for문 돌면 될듯
 *
 * 구현해야 하는 기능
 * 1. 입력에 따른 구간 시작 지점 체크
 *
 */
public class BOJ_18311_왕복 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        long K = Long.parseLong(st.nextToken());

        int[] distance = new int[N << 1];
        int[] order = new int[N << 1];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            distance[i] = distance[N + Math.abs(N - 1 - i)]= Integer.parseInt(st.nextToken());
            order[i] = order[N + Math.abs(N - 1 - i)] = i + 1;
        }

        int now = 0;
        long nowDist = 0L;
        for (int i = 0; i < (N << 1); i++) {
            if (nowDist > K) break;
            now = i;
            nowDist += distance[i];
        }

        System.out.println(order[now]);
    }
    
}