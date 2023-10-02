/***
 * 트라이 : 문자열 집합을 저장하며, 집합 내 문자열을 O(M)으로 찾을 수 있다.
 * 알파벳 대문자로 이루어진 문자열 집합을 저장
 */

//트라이 각 노드는 접두사를 나타냄, (해당 노드로 가는 경로로 접두사를 알 수 있으므로 접두사를 노드에 저장하지 않음)
//문자열 집합에 포함되는 노드는 terminal이 true
//노드는 'A'~'Z'에 해당하는 자식 노드 배열을 저장하는데, children[0]에 자식 노드가 있다면, (자신 노드 접두사 + 'A')에 해당하는 접두사가 있다는 뜻.
//메모리가 엄청 낭비되지만, (자신 노드 접두사 + 임의 문자)에 해당하는 접두사가 있는지를 한번에 알 수 있다.

class Node {
    Node[] children;
    boolean terminal;

    Node() {
        children = new Node[26];
        terminal = false;
    }
}

public class Trie {
    Node treeRoot = new Node();

    private int toNumber(char ch) {
        return ch - 'A';
    }

    private void insert(Node tree, String key, int keyIdx) {
        //모든 문자 삽입 완료
        if(keyIdx == key.length()) {
            tree.terminal = true;
            return;
        }

        int idx = toNumber(key.charAt(keyIdx));
        //없으면 노드 생성
        if(tree.children[idx] == null) tree.children[idx] = new Node();
        insert(tree.children[idx], key, keyIdx + 1);
    }

    //노드 반환, 이때 반환되는 노드가 터미널 노드인지 확인하려면 terminal 필드를 확인하면 됨
    private Node find(Node tree, String key, int keyIdx) {
        //key에 해당하는 노드 반환
        if(keyIdx == key.length()) return tree;

        int idx = toNumber(key.charAt(keyIdx));
        //터미널 노드 반환
        if(tree.children[idx] == null) return tree;
        return find(tree.children[idx], key, keyIdx+1);
    }
}



