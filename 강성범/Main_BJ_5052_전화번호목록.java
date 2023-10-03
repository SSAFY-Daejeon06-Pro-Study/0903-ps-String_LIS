package kr.ac.lecture.baekjoon.Num1001_10000;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;


/*
* [문제 요약]
* 한 문자열이 다른 문자열의 접두어인지 확인
*
* [제약 조건]
* 테스트 케이스 : 1 ≤ t ≤ 50
* 문자열 수 : 1 ≤ n ≤ 10000
* 문자열 길이 10 이하
*
* [문제 설명]
* 트라이로 하면 될 듯
* 문자열 마지막이면서 end가 아니면 접두어
*
*
* */
public class Main_BJ_5052_전화번호목록 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int t = Integer.parseInt(br.readLine());
        while (t-- > 0){
            int n = Integer.parseInt(br.readLine());
            String[] strs = new String[n];
            for(int i=0; i<n; i++){
                strs[i] = br.readLine();
            }

            Arrays.sort(strs, Comparator.comparingInt(String::length)); // 문자 길이순으로 오름차순 정렬

            Trie trie = new Trie();
            boolean isPossible = true;

            for(int i=0; i< strs.length; i++){
                String s = strs[i];
                if(trie.search(s)){ // 해당 문자가 다른 문자의 prefix가 아니라면
                    trie.insert(s);
                }else{
                    isPossible = false;
                    break;
                }
            }

            System.out.println(isPossible ? "YES" : "NO");
        }


        br.close();
    }

    private static class Trie{
        Node root;

        Trie(){
            root = new Node();
        }

        void insert(String str){
            Node node = root;

            for(int i=0; i<str.length(); i++){
                node = node.child.computeIfAbsent(str.charAt(i), k -> new Node());
            }
            node.end = true;
        }

        boolean search(String str) {
            Node node = root;

            for(int i=0; i<str.length(); i++){
                node = node.child.getOrDefault(str.charAt(i), null);

                if(node == null) return true;
                if(node.end) return false; // 다른 문자열의 prefix라면
            }
            return false;
        }

        private class Node{
            Map<Character, Node> child = new HashMap<>();
            boolean end;
        }
    }
}

