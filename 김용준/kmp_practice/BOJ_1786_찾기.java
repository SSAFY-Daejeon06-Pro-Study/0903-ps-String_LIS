package kmp_practice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class BOJ_1786_찾기 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String text = br.readLine(); // 전체 문자열
        String pattern = br.readLine(); // 검색할 패턴

        List<Integer> list = kmp(text, pattern);
        StringBuilder sb = new StringBuilder();
        sb.append(list.size()).append('\n');
        for (int idx : list) sb.append(idx).append(' ');
        System.out.println(sb);
    }

    private static ArrayList<Integer> kmp(String text, String pattern) {
        ArrayList<Integer> startPoints = new ArrayList<>();
        int[] pi = getPi(pattern);

        int N = text.length();
        int M = pattern.length();
        int j = 0;

        for (int i = 0; i < N; i++) {
            // 패턴이 일치하지 않으면 점프하게 됨
            while (j > 0 && text.charAt(i) != pattern.charAt(j)) j = pi[j - 1];
            if (text.charAt(i) == pattern.charAt(j)) {
                // 패턴이 전부 일치하는 경우
                if (j == M - 1) {
                    startPoints.add(i - M + 2); // 시작 위치 저장
                    j = pi[j]; // 다음 겹치는 패턴을 검색하기 위해 공통된 문자열만큼 점프
                }
                // 패턴이 일부(처음 ~ j번까지) 일치하는 경우
                else {
                    // text의 다음 인덱스와 pattern의 다음 인덱스를 비교해야 하기 때문에 j를 1 증가
                    j++;
                }
            }
        }

        return startPoints;
    }


    private static int[] getPi(String pattern) {
        int length = pattern.length();
        int[] pi = new int[length];
        pi[0] = 0; // 1글자짜리는 prefix와 suffix가 없으므로 항상 0
        int j = 0; // j = (prefix == suffix)일 때의 prefix 맨 뒤 인덱스
        for (int i = 1; i < length; i++) { // i =  0 ~ i 부분 문자열의 맨 뒤 인덱스
            while (j > 0 && pattern.charAt(i) != pattern.charAt(j)) j = pi[j - 1];
            if (pattern.charAt(i) == pattern.charAt(j)) {
                pi[i] = ++j;
            }
        }
        return pi;
    }
}
