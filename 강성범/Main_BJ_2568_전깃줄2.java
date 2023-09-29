package kr.ac.lecture.baekjoon.Num1001_10000;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

/*
* [문제 요약]
* 전깃줄이 교차하지 않게 최소 전깃줄 제거
*
* [제약 조건]
* 전깃줄의 개수는 100,000 이하
* 위치의 번호는 500,000 이하의 자연수
* 같은 위치에 두 개 이상의 전깃줄이 연결될 수 없다.
*
* 첫째 줄 : 길이
* 둘째 줄~ : a 기준 오름차순
*
* [문제 풀이]
* 출발지를 a, 목적지를 b라고 했을 때
*
* b를 기준으로 최장길이부분수열(lis)이 만들어져야 함
* 그래야 교차가 만들어지지 않음
*
* lis를 만드는 방법은 두 가지가 있음
* 1. n^2
* 2. nlogn
*
* 전깃줄의 최대 길이가 10만이기 때문에 n^2은 불가
* 이진 탐색을 사용하면서 lis의 길이를 구할 수 있는 nlogn방법을 써야 함
*
* 1. a 기준 오름차순 정렬
* 2. b를 기준으로 이진 탐색을 통해 lis의 길이를 알아냄 -> right의 크기
*
*
*
* */
public class Main_BJ_2568_전깃줄2 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer stz;

        int n = Integer.parseInt(br.readLine());

        Wire[] wires = new Wire[n];
        for (int i = 0; i < n; i++) {
            stz = new StringTokenizer(br.readLine());
            wires[i] = new Wire(Integer.parseInt(stz.nextToken()), Integer.parseInt(stz.nextToken()));
        }

        Arrays.sort(wires);

        int[] lis = new int[n]; // lis 저장
        int[] lisIndex = new int[n]; // 위치 저장
        int[] parents = new int[n]; // 역추적할 위치 저장
        int size = 0;

        for(int i=0; i<n; i++){
            int left = 0;
            int right = size;
            int value = wires[i].b;

            while (left < right){
                int mid = (left + right)/2;

                if(lis[mid] >= value){
                    right = mid;
                }else{
                    left = mid+1;
                }
            }

            lis[left] = wires[i].b;
            lisIndex[left] = i; // 인덱스 저장

            if (left == 0) {
                parents[i] = -1;
            } else {
                parents[i] = lisIndex[left - 1]; // 역추적을 위한 인덱스 저장
            }

            if(left == size){
                size++;
            }

        }

        // 역추적
        Set<Integer> lisSet = new HashSet<>();
        int find = lisIndex[size - 1];
        while (find != -1) {
            lisSet.add(wires[find].a); // 사용된 전선 번호 기억
            find = parents[find];
        }

        System.out.println(n-size);
        for (int i=0; i<n; i++){
            int value = wires[i].a;
            if(!lisSet.contains(value)){
                System.out.println(value);
            }
        }

        br.close();
    }

    private static class Wire implements Comparable<Wire>{
        int a, b;

        public Wire(int a, int b) {
            this.a = a;
            this.b = b;
        }

        @Override
        public int compareTo(Wire o) {
            return this.a -o.a;
        }
    }
}
