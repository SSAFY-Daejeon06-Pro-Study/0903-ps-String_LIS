import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Objects;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 풀이 시작 : 8:06
 * 풀이 완료 :
 * 풀이 시간 :
 * <p>
 * 문제 해석
 * 두 마리 백조가 호수에 살고 있음
 * 호수를 덮는 빙판으로 인해 만나지 못함
 * 호수는 직사각형
 * 매일 물과 접촉한 모든 빙판은 녹음
 * 백조는 물로만 이동 가능
 * 며칠이 지나야 백조가 만날 수 있는지 구해야 함
 * <p>
 * 구해야 하는 것
 * 백조가 만날 수 있는 최소 시간
 * <p>
 * 문제 입력
 * 첫째 줄 : 호수의 세로, 가로 크기 R, C
 * 둘째 줄 ~ R개 줄 : 호수의 상태
 * '.' = 물, 'X' = 빙판, 'L' = 백조
 * <p>
 * 제한 요소
 * 1 <= R, C <= 1500
 * <p>
 * 생각나는 풀이
 * 그래프 탐색, 일반적인 탐색하면 매 초마다 1500^2번 돌아야 하므로 시간초과
 * 시간을 줄일 무언가가 필요
 * 일단 물과 인접한 애들만 인접 탐색하고 백조 이동을 어떻게 시킬 것인지가 관건
 * union-find 연산으로 이동 가능한 묶음을 관리
 * <p>
 * 구현해야 하는 기능
 * 1. 입력에 따른 호수 저장
 * 2. 호수의 값에 따라 초기 세팅
 * 2-1. 현재 칸이 물
 * - 인접한 물과 union-find 연산
 * 2-2. 현재 칸이 얼음
 * - 인접한 물이 있으면 녹을 얼음 목록에 추가
 * 2-3. 현재 칸이 백조
 * - 백조 목록에 추가 후 현재 칸 물로 변경
 * 3. 매 시간마다 두 백조 위치 find로 같은 묶음인지 체크
 * - 같은 묶음이면 시간 출력
 * - 다른 묶음이면 얼음 녹이기 진행
 */
public class BOJ_3197_백조의호수 {
    static int R, C;
    static char[][] lake;
    static Node[] swan = new Node[2];
    static int[] dx = {-1, 0, 1, 0};
    static int[] dy = {0, -1, 0, 1};
    static Queue<Node> meltingIce = new ArrayDeque<>();
    static Node[][] parent;
    static boolean[][] visited;

    static class Node {
        int x, y;

        public Node(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            Node node = (Node) o;
            return x == node.x && y == node.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        public boolean isBigger(Node o) {
            if (this.x == o.x) return this.y - o.y > 0;
            return this.x - o.x > 0;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());

        lake = new char[R][C];
        parent = new Node[R][C];
        visited = new boolean[R][C];
        for (int i = 0; i < R; i++) {
            lake[i] = br.readLine().toCharArray();
            for (int j = 0; j < C; j++) {
                parent[i][j] = new Node(i, j);
            }
        }

        for (int i = 0, idx = 0; i < R; i++) {
            fo : for (int j = 0; j < C; j++) {
                char value = lake[i][j];

                if (value == 'L') {
                    swan[idx++] = new Node(i, j);
                    lake[i][j] = '.';
                } else if (value == 'X') {
                    // 얼음이라면 인접탐색 통해 물과 닿았는지 체크
                    for (int dir = 0; dir < 4; dir++) {
                        int nextX = i + dx[dir];
                        int nextY = j + dy[dir];

                        if (!isInRange(nextX, nextY) || lake[nextX][nextY] == 'X') continue;
                        meltingIce.offer(new Node(i, j));
                        visited[i][j] = true;
                        continue fo; // 하나라도 물에 닿았으면 더 탐색할 필요 없음
                    }
                }

                for (int dir = 0; dir < 2; dir++) { // 반복문 구조상 좌상만 탐색해도 됨
                    int nextX = i + dx[dir];
                    int nextY = j + dy[dir];
                    if (!isInRange(nextX, nextY) || lake[nextX][nextY] == 'X') continue;
                    union(i, j, nextX, nextY);
                }
            }
        }

        int time = 0;
        Node swan1 = swan[0];
        Node swan2 = swan[1];
        while (!find(swan1.x, swan1.y).equals(find(swan2.x, swan2.y))) {
            melt(); // 얼음 녹이기
            time++; // 시간 증가
        }

        System.out.println(time);
    }

    // 얼음 녹는 메서드
    private static void melt() {
        int size = meltingIce.size(); // 현재 물과 인접한 얼음의 수만큼 반복
        for (int i = 0; i < size; i++) {
            Node now = meltingIce.poll();
            lake[now.x][now.y] = '.'; // 물로 바꿔줌

            for (int dir = 0; dir < 4; dir++) {
                int nextX = now.x + dx[dir];
                int nextY = now.y + dy[dir];

                if (!isInRange(nextX, nextY)) continue;
                if (lake[nextX][nextY] == 'X' && !visited[nextX][nextY]) { // 새로 물과 접하는 얼음인 경우
                    meltingIce.offer(new Node(nextX, nextY)); // 녹을 얼음 목록에 추가
                    visited[nextX][nextY] = true; // 재방문 안하기 위해 방문 처리
                } else if (lake[nextX][nextY] == '.') { // 물이라면 물끼리 union연산
                    union(now.x, now.y, nextX, nextY);
                }
            }
        }
    }

    private static void union(int aX, int aY, int bX, int bY) {
        Node a = find(aX, aY);
        Node b = find(bX, bY);

        if (a.equals(b)) return;
        // 좌표가 작은 노드가 부모
        if (a.isBigger(b)) {
            Node temp = a;
            a = b;
            b = temp;
        }

        parent[b.x][b.y] = a;
    }

    private static Node find(int x, int y) {
        if (parent[x][y].x == x && parent[x][y].y == y) return parent[x][y];
        return parent[x][y] = find(parent[x][y].x, parent[x][y].y);
    }

    private static boolean isInRange(int x, int y) {
        return x >= 0 && x < R && y >= 0 && y < C;
    }

}