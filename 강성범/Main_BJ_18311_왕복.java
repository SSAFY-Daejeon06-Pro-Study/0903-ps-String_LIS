package kr.ac.lecture.baekjoon.Num10001_20000;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
* [문제 요약]
* 길이가 주어질 때, 몇 번 코스?
*
* [제약 조건]
* (1≤N≤100,000)
* K는 항상 왕복 거리보다 작은 양의 정수 혹은 0
* 각 코스의 길이는 50,000보다 작거나 같은 자연수
*
* [문제 설명]
* k에서 길이를 빼주면서 0보다 작아지는 지점
*
* */
public class Main_BJ_18311_왕복 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer stz = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(stz.nextToken());
        long k = Long.parseLong(stz.nextToken());

        int[] arr = new int[n];
        stz = new StringTokenizer(br.readLine());
        for(int i=0; i<n; i++){
            arr[i] = Integer.parseInt(stz.nextToken());
        }

        boolean isDirect = false;
        for(int i=0; i<n; i++){
            k -= arr[i];

            if(k < 0){
                System.out.println(i+1);
                isDirect = true;
                break;
            }
        }

        if(!isDirect){
            for(int i=n-1; i>=0; i--){
                k -= arr[i];

                if(k < 0){
                    System.out.println(i+1);
                    break;
                }
            }
        }

        br.close();
    }
}
