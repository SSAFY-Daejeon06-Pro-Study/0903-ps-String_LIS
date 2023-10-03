import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class BOJ_1305 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int L = Integer.parseInt(br.readLine());
        String str = br.readLine();

        List<Integer> p = getPartialMatch(str);
        System.out.println(L - p.get(L-1));
    }

    //짚더미 H의 부분 문자열로 바늘 N이 출현하는 시작 위치들을 모두 반환
    //문제에서 안쓰임
    static List<Integer> kmpSearch(String H, String N) {
        int n = H.length();
        int m = N.length();
        List<Integer> ret = new ArrayList<>();

        //p[i] = N[...i]의 접미사도 되고 접두사도 되는 문자열의 최대 길이
        List<Integer> pi = getPartialMatch(N);

        int begin = 0;
        int matched = 0;

        while(begin <= n - m) {
            if(matched < m && H.charAt(matched) == N.charAt(matched)) {
                ++matched;
                if(matched == m) ret.add(begin);
            }
            //불일치 또는 일치가 발생한 직후
            else {
                if(matched == 0) {
                    begin += 1;
                }
                else {
                    //다음 탐색을 시작할 시작위치
                    begin += matched - pi.get(matched - 1);
                    //시작 위치를 옮긴 이후, 처음부터 비교할 필요가 없음
                    //0번째 ~ pi.get(matched - 1) - 1번째 문자는 이미 일치하기 때문
                    matched = pi.get(matched - 1);
                }
            }
        }
        return ret;
    }

    //p[i] 계산
    static List<Integer> getPartialMatch(String N) {
        int m = N.length();
        List<Integer> pi = new ArrayList<>();
        for(int i=0; i<m; i++) pi.add(0);

        int begin = 1, matched = 0;
        while(begin + matched < m) {
            if(N.charAt(begin + matched) == N.charAt(matched)) {
                ++matched;
                pi.set(begin+matched-1, matched);
            }
            else {
                if(matched == 0) {
                    ++begin;
                }
                else {
                    begin += matched - pi.get(matched-1);
                    matched = pi.get(matched-1);
                }
            }
        }
        return pi;
    }
}
