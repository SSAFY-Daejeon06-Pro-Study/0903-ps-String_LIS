import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * [문제]
 * RxC 호수가 있음 (1<= R,C <=1500)
 * .(물), X(빙판), L(백조)
 * 매일 물 공간과 접촉한 빙판은 녹아 물이됨
 * 백조가 만날 수 있는 최소 날짜 출력
 *
 * [변수]
 * class Point {
 *     int r,
 *     int c
 * }
 *
 * int R, C
 * char[][] matrix
 * Point[] parent
 *
 * [풀이]
 * int day = 0;
 * Point swan1, swan2
 * Queue<Point> queue
 * boolean[][] discovered
 *
 * 'L' 위치를 swan1, swan2에 저장
 * 'L'을 '.'로 바꿈
 * 초기 '.'에 해당하는 각 칸을 parent[i*C+j]에 넣음
 * 
 * initUnion() 수행
 * 
 * 아직 발견되지 않은 '.'와 인접한 모든 'X'를 큐에 넣고, discovered[i][j] = true
 * 큐에는 다음날에 녹을 'X'가 들어있음
 *
 * while(queue.empty)
 *      if(find(swan1) == find(swan2)) break
 *      queueSize = queue.size()
 *      day++
 *      for(int i=0; i<queueSize; i++) 
 *          Point point = queue.poll()
 *          matrix[point.r][point.c] = '.'
 *
 *          point 기준 상하좌우 순회
 *              nr, nc칸이 범위를 벗어났다면 continue
 *              if(matrix[nr][nc] == '.')
 *                  if(parent[point.r*C+point.c] == null)
 *                      parent[point.r*C+point.c] = parent[nr*C+nc]
 *                  else
 *                      union(parent[point.r*C+point.c], parent[nr*C+nc])
 *              else
 *                  nr, nc칸이 이미 발견되었다면 continue
 *                  discovered[nr][nc] = true
 *                  queue.offer(new Point(nr, nc))
 *
 * void initUnion() - '.'으로 이루어진 부분 그래프를 집합으로 만듬
 * boolean[][] visit
 * 아직 방문하지 않고, '.'인 칸에 대해 dfs 수행
 * if(!visit[i][j] && matrix[i][j] == '.') dfs(i, j)
 *
 * void dfs(int r, int c, boolean[][] visit)
 * visit[r][c] = true
 * r,c를 방문하고, r,c 기준 상하좌우칸(nr, nc) 순회
 *      nr, nc칸이 범위를 벗어났으면 continue
 *      nr, nc칸이 'X'이면 continue
 *      nr, nc칸이 이미 방문되었으면 continue
 *      union(parent[r*C+c], parent[nr*C+nc]))
 *      dfs(nr, nc)
 *
 * void union(Point a, Point b)
 *      Point parentA = find(a)
 *      Point parentB = find(b)
 *      if(parentA == parentB) return
 *      parent[parentB.r * C + parentB.c] = parentA
 *
 * Point find(Point point)
 *      if(parent[point.r*C+point.c] == point) return point
 *      parent[point.r*C+point.c] = find(parent[point.r*C+point.c])
 *      return parent[point.r*C+point.c];
 */

public class BOJ_3197 {
    static class Point {
        int r, c;
        Point(int r, int c) {
            this.r = r;
            this.c = c;
        }
    }
    static int[] rPos = {-1, 1, 0, 0};
    static int[] cPos = {0, 0, -1, 1};

    static int R, C;
    static char[][] matrix;
    static Point[] parent;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());
        matrix = new char[R][C];
        parent = new Point[R*C];
        boolean[][] discovered = new boolean[R][C];
        Queue<Point> queue = new ArrayDeque<>();
        Point swan1 = null, swan2 = null;
        int day = 0;

        for(int i=0; i<R; i++) {
            String line = br.readLine();
            for(int j=0; j<C; j++) {
                matrix[i][j] = line.charAt(j);

                if(matrix[i][j] == 'L' || matrix[i][j] == '.') {
                    Point point = new Point(i, j);
                    if(matrix[i][j] == 'L') {
                        if(swan1 == null) swan1 = point;
                        else swan2 = point;
                        matrix[i][j] = '.';
                    }
                    parent[i * C + j] = point;
                }
            }
        }

        initUnion();

        //아직 발견되지 않은 '.'와 인접한 모든 'X'를 큐에 넣고, discovered[i][j] = true
        for(int i=0; i<R; i++) {
            for(int j=0; j<C; j++) {
                if(matrix[i][j] == 'X') {
                    for(int z=0; z<4; z++) {
                        int nr = i + rPos[z];
                        int nc = j + cPos[z];
                        if(!isRange(nr, nc)) continue;
                        if(matrix[nr][nc] == '.') {
                            queue.offer(new Point(i, j));
                            discovered[i][j] = true;
                            break;
                        }
                    }
                }
            }
        }

        while(!queue.isEmpty()) {
            if(find(swan1) == find(swan2)) break;
            int queueSize = queue.size();
            day++;

            for(int i=0; i<queueSize; i++) {
                Point point = queue.poll();
                matrix[point.r][point.c] = '.';

                for(int j=0; j<4; j++) {
                    int nr = point.r + rPos[j];
                    int nc = point.c + cPos[j];
                    if(!isRange(nr, nc)) continue;

                    if(matrix[nr][nc] == '.') {
                        if(parent[point.r * C + point.c] == null) parent[point.r * C + point.c] = find(parent[nr * C + nc]);
                        else union(parent[point.r * C + point.c], parent[nr * C + nc]);
                    }
                    else {
                        if(discovered[nr][nc]) continue;
                        queue.offer(new Point(nr, nc));
                        discovered[nr][nc] = true;
                    }
                }
            }
        }

        System.out.println(day);
    }

    static void initUnion() {
        boolean[][] visit = new boolean[R][C];

        for(int i=0; i<R; i++) {
            for(int j=0; j<C; j++) {
                if(!visit[i][j] && matrix[i][j] == '.') dfs(i, j, visit);
            }
        }
    }

    static void dfs(int r, int c, boolean[][] visit) {
        visit[r][c] = true;

        for(int i=0; i<4; i++) {
            int nr = r + rPos[i];
            int nc = c + cPos[i];
            if(!isRange(nr, nc)) continue;
            if(matrix[nr][nc] == 'X') continue;
            if(visit[nr][nc]) continue;
            union(parent[r*C+c], parent[nr*C+nc]);
            dfs(nr, nc, visit);
        }
    }

    static void union(Point a, Point b) {
        Point parentA = find(a);
        Point parentB = find(b);
        if(parentA == parentB) return;
        parent[parentB.r * C + parentB.c] = parentA;
    }

    static Point find(Point point) {
        if(parent[point.r * C + point.c] == point) return point;

        parent[point.r * C + point.c] = find(parent[point.r * C + point.c]);
        return parent[point.r * C + point.c];
    }

    static boolean isRange(int r, int c) {
        return (r >= 0 && r < R && c >= 0 && c < C);
    }
}
