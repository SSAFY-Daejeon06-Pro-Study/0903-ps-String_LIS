import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/***
 * [문제]
 * 전화번호 목록이 일관성을 유지하려면, 한 번호가 다른 번호의 접두어인 경우가 없어야함
 * 전화번호 목록이 주어졌을 때, 일관성이 있는 목록인경우 YES, 없는 목록인경우 NO 출력
 *
 * N (전화번호 수, 1<=N<=10000)
 * 전화번호는 길어야 10자리, 목록에 있는 두 전화번호가 같은 경우는 없음
 *
 * [풀이]
 * 무식하게 접근하면, 이중 반복문으로 각 전화번호가 다른 전화번호의 접두어가 되는지 체크할 수 있음.
 * 이 경우 시간 복잡도 O(10000 * 10000 * 10) = 시간 초과
 * 
 * [trie 적용]
 * 전화번호를 String 배열에 저장
 * String 배열의 전화번호를 모두 트라이에 넣음
 * 전화번호를 모두 순회하면서
 *      트라이에 해당 전화번호와 같은 접두어 노드를 가져옴
 *      접두어 노드가 하나의 자식이라도 가지고 있으면 해당 전화번호는 다른 전화번호의 접두어가 됨 => NO를 출력, 다음 테케로 이동
 * 순회를 모두 마치면 전화번호 목록은 일관성 유지 => YES 출력, 다음 테케로 이동
 *
 * 시간 복잡도 = O(10,000 * 10) = O(100,000)
 */

public class BOJ_5052 {

    static class Node {
        Node[] children = new Node[10];
    }

    static class Trie {
        Node treeRoot = new Node();

        private int toNumber(char ch) {
            return ch - '0';
        }

        private void insert(Node root, String key, int keyIdx) {
            if(key.length() == keyIdx) return;

            int idx = toNumber(key.charAt(keyIdx));
            if(root.children[idx] == null) root.children[idx] = new Node();
            insert(root.children[idx], key, keyIdx + 1);
        }

        private Node find(Node root, String key, int keyIdx) {
            if(key.length() == keyIdx) return root;

            int idx = toNumber(key.charAt(keyIdx));
            if(root.children[idx] == null) return root;
            return find(root.children[idx], key, keyIdx + 1);
        }

        public void insertNode(String key) {
            insert(treeRoot, key, 0);
        }

        public boolean isContained(String key) {
            Node node = find(treeRoot, key, 0);

            for(int i=0; i<10; i++) {
                if(node.children[i] != null) return true;
            }
            return false;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int testCase = Integer.parseInt(br.readLine());

      A:for(int t=0; t<testCase; t++) {
            int N = Integer.parseInt(br.readLine());
            Trie trie = new Trie();
            String[] numbers = new String[N];

            for(int i=0; i<N; i++) {
                numbers[i] = br.readLine();
                trie.insertNode(numbers[i]);
            }

            for(int i=0; i<N; i++) {
                if(trie.isContained(numbers[i])) {
                    sb.append("NO").append('\n');
                    continue A;
                }
            }
            sb.append("YES").append('\n');
        }

        System.out.print(sb);
    }
}

