package kr.ac.lecture.baekjoon.Num1001_10000;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/*
* [문제 요약]
* 전깃줄 일부를 제거해서 모든 전깃줄이 교차하지 않는 최대 개수
*
* [제약 조건]
* 100 이하의 자연수
* 위치의 번호는 500 이하의 자연수이고, 같은 위치에 두 개 이상의 전깃줄이 연결될 수 없다.
*
* [문제 설명]
* 최장길이부분수열(LIS) 문제임
* 교차가 되지 않으려면 증가 부분 수열로 이뤄져야 함
* 문제에서 최소 전깃줄을 구하라고 했기 때문에, 최대 길이 증가 수열이 만들어져야 함
*
* 입력에서 시작 위치가 오름차순이 아니기 때문에, 정렬을 한 뒤 LIS를 구해야 함
*
* LIS를 구하는 방법은 크게 두 가지가 있음
* 1. N^2
* - 부분 수열을 구할 때, 특정 위치 i인 위치가 부분 수열의 마지막 원소라고 가정
* i 위치에서 이전의 것을 보면서 최대+1하면 됨
* for(int j=i-1; j>=0; j--){
*     if(작으면){
*       dp[i] = Math.max(dp[i], dp[j]+1)
*     }
* }
*
* 2. NlongN
* - 배열에서 하한 이진 탐색을 사용해서 알맞은 값을 넣는 것임
* 배열이 만들어지는 길이가 LIS의 길이임
*
*
* */
public class Main_BJ_2565_전깃줄 {

    private static int n;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer stz = new StringTokenizer(br.readLine());

        n = Integer.parseInt(stz.nextToken());
        Node[] nodes = new Node[n];

        for(int i=0; i<n; i++){
            stz = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(stz.nextToken());
            int b = Integer.parseInt(stz.nextToken());

            nodes[i] = new Node(a, b);
        }

        Arrays.sort(nodes);

        System.out.println(n - nLogn(nodes));

        br.close();
    }

    // n^2 풀이
    private static int nn(Node[] nodes){
        int[] dp = new int[n];
        int max = 1;

        for(int i=0; i<n; i++){
            dp[i] = 1;

            for(int j=i-1; j>=0; j--){
                if(nodes[i].b > nodes[j].b){
                    dp[i] = Math.max(dp[i], dp[j]+1);
                }

                max = Math.max(max, dp[i]);
            }
        }


        return max;
    }

    private static int nLogn(Node[] nodes){
        int size = 0;
        int[] lis = new int[n];

        for(int i=0; i<n; i++){
            int value = nodes[i].b;

            int left = 0;
            int right = size;

            // 하한(중복값이 없기 때문에 상한을 써도 됨)
            while (left < right){
                int mid = (left + right) / 2;

                if(lis[mid] >= value){
                    right = mid;
                }else{
                    left = mid+1;
                }
            }

            lis[left] = value;

            // 마지막에 삽입될 때 -> 배열의 크기 증가
            if(left == size){
                size++;
            }
        }

        return size;
    }

    private static class Node implements Comparable<Node>{
        int a, b;

        public Node(int a, int b) {
            this.a = a;
            this.b = b;
        }

        @Override
        public int compareTo(Node o) {
            return this.a - o.a;
        }
    }
}
