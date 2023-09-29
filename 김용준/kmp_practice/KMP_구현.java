package kmp_practice;

import java.util.ArrayList;

public class KMP_구현 {
    public static void main(String[] args) {
        String text = "ABACABABAC"; // 전체 문자열
        String pattern = "ABA"; // 검색할 패턴

        System.out.println("text : " + text);
        String[] prefix = getPrefix(text);
        String[] suffix = getSuffix(text);
        int[] pi = getPi(pattern);
        System.out.println("prefix of " + text);
        for (int i = 0; i < text.length(); i++) {
            System.out.println((i + 1) + ". " + prefix[i]);
        }

        System.out.println();
        System.out.println("suffix of " + text);
        for (int i = 0; i < text.length(); i++) {
            System.out.println((i + 1) + ". " + suffix[i]);
        }

        System.out.println();
        System.out.println("pi[] of " + pattern);
        for (int i = 0; i < pi.length; i++) {
            System.out.println("pi[" + i + "] = " + pi[i]);
        }

        System.out.println(kmp(text, pattern));
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
                    startPoints.add(i - M + 1); // 시작 위치 저장
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

    private static String[] getPrefix(String text) {
        int length = text.length();
        String[] prefix = new String[length];
        StringBuilder prefixBuilder = new StringBuilder();

        for (int i = 0; i < length; i++) {
            prefixBuilder.append(text.charAt(i));
            prefix[i] = prefixBuilder.toString();
        }
        return prefix;
    }

    private static String[] getSuffix(String text) {
        int length = text.length();
        String[] suffix = new String[length];
        StringBuilder suffixBuilder = new StringBuilder();

        for (int i = length - 1; i >= 0; i--) {
            suffixBuilder.insert(0, text.charAt(i));
            suffix[length - 1 - i] = suffixBuilder.toString();
        }
        return suffix;
    }


}
