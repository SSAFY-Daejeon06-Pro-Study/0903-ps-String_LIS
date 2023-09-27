package kr.ac.lecture.baekjoon.Num1001_10000;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/*
 * [문제 요약]
 * 두 마리의 백조가 만나는 최소 시간
 *
 * [제약 조건]
 * 1 ≤ R, C ≤ 1500
 * '.'은 물 공간, 'X'는 빙판 공간, 'L'은 백조가 있는 공간
 *
 *
 * [얼음 두께 구하기]
 * 얼음이 얼마나 시간이 지나야 녹는지 알아야 함
 * 얼음이 물에서 부터 얼마나 떨어져 있는지 알 수 있으면 구할 수 있음
 * "..XXX.." 가 있다고 했을 때,
 * "0012100" 이 됨
 * 0은 물이 있는 위치이고, 그 이외의 숫자는 물에서 부터 거리임
 *
 * 0행, r-1행, 0열 c-1열을 기준으로 반대 방향으로 가면서 얼음이 얼마나 떨어져 있는지 확인할 수 있음
 *
 * 0행에서 부터 시작하면
 * thickness[i][j] = Math.min(thickness[i][j], thickness[i][j-1]+1);
 * ...
 *
 *
 *
 * [문제 풀이]
 *
 * 백조 한 마리만을 움직임
 * 해당 초에 백조가 갈 수 있는 모든 위치를 구함 -> swanPoint
 * 만약 다른 백조를 만나게 되면 종료
 *
 * 특정 시간 x에 백조가 갈 수 있는 곳은 얼음의 두께가 x보다 작거나 같아야 함
 *
 *
 *
 * */
public class Main_BJ_3197_백조의호수 {
    private static final int[] DX = {-1, 1, 0, 0,};
    private static final int[] DY = {0, 0, -1, 1};

    private static final int MAX = 1501;

    private static int r, c;
    private static char[][] map;
    private static boolean[][] visited;

    private static Point start, end;
    private static int[][] thickness;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer stz = new StringTokenizer(br.readLine());

        r = Integer.parseInt(stz.nextToken());
        c = Integer.parseInt(stz.nextToken());
        map = new char[r][c];
        visited = new boolean[r][c];
        thickness = new int[r][c];

        List<Point> swanPoint = new ArrayList<>();

        for (int i = 0; i < r; i++) {
            String str = br.readLine();
            for (int j = 0; j < c; j++) {
                map[i][j] = str.charAt(j);

                if(map[i][j] == 'L' && start == null) {
                    start = new Point(i, j);
                    visited[i][j] = true;
                }else if(map[i][j] == 'L') {
                    end = new Point(i, j);
                }

                thickness[i][j] = MAX;
                if(map[i][j] == '.'){
                    thickness[i][j] = 0;
                }
            }
        }


        thickness[start.x][start.y] = 0;
        thickness[end.x][end.y] = 0;

        iceMeltTimes();

        swanPoint.add(start);
        swanPoint = nextSwanPoint(swanPoint, 0);

        if(swanPoint == null || swanPoint.size() == 0) { // 바로 다른 백조를 만날 경우
            System.out.println(0);
        }else {
            int time = 0;

            while(true) {
                time++;

                // 2. 백조를 움직임
                swanPoint = nextSwanPoint(swanPoint, time);

                if(swanPoint == null || swanPoint.size() == 0) {
                    System.out.println(time);
                    break;
                }
            }
        }

        br.close();
    }

    private static void iceMeltTimes() {
        for(int i=0; i<r; i++) {
            for(int j=1; j<c; j++) {
                thickness[i][j] = Math.min(thickness[i][j], thickness[i][j-1]+1);
            }
        }

        for(int i=0; i<r; i++) {
            for(int j=c-2; j>=0; j--) {
                thickness[i][j] = Math.min(thickness[i][j], thickness[i][j+1]+1);
            }
        }

        for(int j = 0; j<c; j++) {
            for(int i=1; i<r; i++) {
                thickness[i][j] = Math.min(thickness[i][j], thickness[i-1][j]+1);
            }
        }

        for(int j = 0; j<c; j++) {
            for(int i=r-2; i>=0; i--) {
                thickness[i][j] = Math.min(thickness[i][j], thickness[i+1][j]+1);
            }
        }
    }

    // 얼음을 만나면 해당 위치가 다음 시작 위치임
    private static List<Point> nextSwanPoint(List<Point> swanPoint, int time) {
        Queue<Point> qu = new ArrayDeque<>(swanPoint);
        List<Point> next = new ArrayList<>(); // 얼음을 만나는 위치

        while(!qu.isEmpty()) {
            Point cn = qu.poll();

            if(cn.x == end.x && cn.y == end.y) {
                return null; // 다른 백조를 만나면 null 반환
            }

            for(int d = 0; d<DX.length; d++) {
                int nx = cn.x + DX[d];
                int ny = cn.y + DY[d];

                if(!inRange(nx, ny) || visited[nx][ny] || time+1 < thickness[nx][ny]) continue;

                visited[nx][ny] = true;
                if(map[nx][ny] == 'X' && thickness[nx][ny] == time+1) { //
                    next.add(new Point(nx, ny));
                }else {
                    qu.add(new Point(nx, ny));
                }

            }
        }

        return next;
    }

    private static boolean inRange(int x, int y) {
        return (x>=0 && y>=0 && x<r && y<c);
    }

    private static class Point{
        int x, y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
