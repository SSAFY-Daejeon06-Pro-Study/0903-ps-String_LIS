import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * 풀이 시작 : 6:21
 * 풀이 완료 :
 * 풀이 시간 :
 *
 * 문제 해석
 * 전화번호 목록이 주어질 때, 이 목록이 일관성이 있는지 없는지를 구해야 함
 * 일관성을 유지하려면 한 번호가 다른 번호의 접두어인 경우가 없어야 함
 *
 * 구해야 하는 것
 * 전화번호 목록이 주어질 때, 이 목록이 일관성이 있는지 없는지를 구해야 함
 *
 * 문제 입력
 * 첫째 줄 : 테케 수 t
 *
 * 테케당 입력
 * 첫째 줄 : 전화번호 수 N
 * 둘째 줄 ~ N개 줄 : 목록에 포함된 전화번호
 *
 * 제한 요소
 * 1 <= t <= 50
 * 1 <= N <= 10000
 * 1 <= 전화번호 길이 <= 10
 *
 * 생각나는 풀이
 * trie
 * trie에 입력 순으로 채워넣으면서 만약 지나가는 노드에 data가 있다면 그것이 접두사이므로 NO 출력하면 될듯
 *
 * 구현해야 하는 기능
 *
 */
public class BOJ_5052_전화번호목록 {
    static final String YES = "YES";
    static final String NO = "NO";
    static Node root = new Node();
    static class Node {
        boolean data;
        Node[] children;

        public Node() {
            this.children = new Node[10];
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        int t = Integer.parseInt(br.readLine());

        while (t-- > 0) {
            int N = Integer.parseInt(br.readLine());
            Arrays.fill(root.children, null);
            boolean isPrefix = false;
            while (N-- > 0) {
                String num = br.readLine();
                if (isPrefix) continue;
                isPrefix = fillTrie(num, 0, root);
            }

            if (isPrefix) sb.append(NO);
            else sb.append(YES);
            sb.append('\n');
        }

        System.out.println(sb);
    }

    private static boolean fillTrie(String num, int idx, Node node) {
        if (idx == num.length()) {
            for (int i = 0; i < 10; i++) {
                if (node.children[i] != null) return true;
            }
            node.data = true;
            return false;
        }
        if (node.data) return true;
        int nowValue = num.charAt(idx) - '0';
        if (node.children[nowValue] == null) {
            node.children[nowValue] = new Node();
        }

        return fillTrie(num, idx + 1, node.children[nowValue]);
    }

}