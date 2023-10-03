package kr.ac.lecture.baekjoon.Num1001_10000;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


/*
* [문제 요약]
* 가능한 광고 길이중 가장 짧은 것
*
* [제약 조건]
* 광고판 크기 : 1 ≤ L ≤ 1,000,000
* 소문자로만 구성됨
*
* [문제 설명]
* 전광판에는 같은 내용의 문구가 무한히 반복되어서 나옴
* 전광판의 크기는 한 번에 보이는 최대 문자수 -> L 만큼만 보임
*
* 업자는 길이가 N인 광고를 무한히 붙여서 광고
* 광고 내용이 aaba고 L이 6이면
* aabaaa가 처음 보이는 내용
* 1초마다 한칸씩 옆으로 이동
* 다음 초는 abaaab
* 그 다음 초는 baaaba
*
* 전광판의 문자가 주어졌을 때 가능한 광고 길이의 가장 짧은 것 출력
*
* L = 5
* 전광판 문자가 aaaaa라고 했을 때,
* 문자 'a'만 주어지면 만들 수 있음
*
* 전광판에는 반복되는 문자가 있을 것임 -> 패턴 문자 p
* 특정 문자 s에서 반복되는 패턴인 p를 찾아야 됨 - > kmp
*
* 단, p가 '최소 길이'인 것을 찾아야 됨
*
* 구해야 하는 것
* 1. 문자열 s
* 2. 문자열 p
*
* p의 길이는
* 1 <= p <= L
*
* 파라메트릭 서치로 길이를 찾고 패턴 찾기??
* 어떻게 문자가 반복되는지 알지??
*
*
* 도저히 모르겠어서
* 결국 답을 봤음
*
* 문자열이 접두사로 끝나면 해당 길이만큼 감소 가능
* aabaa 가 있으면
* aab 가 최소 광고 문자임
* aabaa를 접두사 테이블로 만들면
* {0, 1, 0, 1, 2}가 되고,
* 5(문자 길이) - 2 = 3 이 패턴 길이가 됨
*
*
* */
public class Main_BJ_1305_광고 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int L = Integer.parseInt(br.readLine());
        String str = br.readLine();

        int[] pi = getPi(str); // 접두사 테이블
        int value = pi[str.length()-1];

        System.out.println(L - value);

        br.close();
    }

    private static int[] getPi(String p){
        int m = p.length();
        int[] pi = new int[m];
        int  j = 0;

        for(int i=1; i<m; i++){
            while (j > 0 && p.charAt(i) != p.charAt(j)){
                j = pi[j-1];
            }

            if(p.charAt(i) == p.charAt(j)){
                pi[i] = ++j;
            }
        }

        return pi;
    }

}


