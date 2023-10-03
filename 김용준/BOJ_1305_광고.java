import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 풀이 시작 : 5:33
 * 풀이 완료 :
 * 풀이 시간 :
 *
 * 문제 해석
 * 전광판에 쓰인 문자가 입력으로 주어질 때, 가능한 광고의 길이 중 가장 짧은 것을 출력해야 함
 *
 * 구해야 하는 것
 * 가능한 광고 길이 중 가장 짧은 것의 길이
 *
 * 문제 입력
 * 첫째 줄 : 광고판 크기 L
 * 둘째 줄 : 현재 광고판에 보이는 문자열
 *
 * 제한 요소
 * 1 <= L <= 1_000_000
 * 광고판의 문자열은 모두 알파벳 소문자
 *
 * 생각나는 풀이
 * pattern의 길이가 M이라고 할 때
 * 반복된다는 건
 * 1. L / M번만큼 pattern이 존재한다
 * 2. L % M만큼 길이의 나머지 문자열이 pattern의 L % M 길이 prefix와 같다
 * 두 가지 조건을 만족해야 하는데
 *
 * 그럼 일단 pi[L - 1]만큼은 제외할 수 있지 않나??
 * pi[i] = 0 ~ i번 글자 substring에서 prefix == suffix인 가장 긴 길이
 * 광고판 문자열에서 앞뒤 같은건 잘라버려도 되니까
 * 전체 광고판 길이에서 pi[L - 1]만큼 빼면 겹치지 않는 문자열이라는 얘기
 * 즉, 답은 L - pi[L - 1]
 *
 * 구현해야 하는 기능
 * 1. 입력에 따른 pi 배열 구하기
 *
 */
public class BOJ_1305_광고 {
    static int L;
    static int[] pi;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        L = Integer.parseInt(br.readLine());
        String text = br.readLine();
        pi = getPi(text);
//        System.out.println(Arrays.toString(pi));

        System.out.println(L - pi[L - 1]);
    }

    private static int[] getPi(String pattern) {
        int[] pi = new int[L];
        pi[0] = 0;
        int j = 0;

        for (int i = 1; i < L; i++) {
            while (j > 0 && pattern.charAt(i) != pattern.charAt(j)) j = pi[j - 1];
            if (pattern.charAt(i) == pattern.charAt(j)) pi[i] = ++j;
        }

        return pi;
    }

}