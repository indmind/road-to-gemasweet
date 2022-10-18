package lumpat;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int p, l;

        p = sc.nextInt();
        l = sc.nextInt();

        ArrayList<Integer> lSpace = new ArrayList<Integer>();
        ArrayList<Integer> rSpace = new ArrayList<Integer>();
        ArrayList<Integer> tSpace = new ArrayList<Integer>();
        ArrayList<Integer> bSpace = new ArrayList<Integer>();
        ArrayList<Integer> mSpace = new ArrayList<Integer>();
        Set<Integer> rLoc = new HashSet<Integer>();
        HashMap<Integer, ArrayList<Integer>> xLoc = new HashMap<Integer, ArrayList<Integer>>();

        int wP = -1;

        int[] cp = new int[2];
        int[] dp = new int[2];

        char[][] m = new char[p][l];

        sc.nextLine();

        for (int i = 0; i < p; i++) {
            m[i] = sc.nextLine().toCharArray();

            if (m[i][0] == ' ') {
                lSpace.add(i);
            }

            if (m[i][m[i].length - 1] == ' ') {
                rSpace.add(i);
            }

            for (int j = 0; j < m[i].length; j++) {
                if (m[i][j] == 'x') {
                    rLoc.add(i);
                    xLoc.put(i, new ArrayList<Integer>());
                }

                if (m[i][j] == '-' && i > 0 && i < m.length - 1) {
                    wP = i;
                }

                if (m[i][j] == 'C') {
                    cp[0] = i;
                    cp[1] = j;
                }

                if (m[i][j] == 'D') {
                    dp[0] = i;
                    dp[1] = j;
                }

                // buat ngecek space atas & bawah
                if (i == 0 && m[i][j] == ' ') {
                    tSpace.add(j);
                }
                if (i == m.length - 1 && m[i][j] == ' ') {
                    bSpace.add(j);
                }
            }
        }

        for (int i = 0; i < m[wP].length; i++) {
            if (m[wP][i] == ' ') {
                mSpace.add(i);
            }
        }

        rLoc.forEach((i) -> {
            for (int j = 1; j < m[j].length - 1; j++) {
                if (m[i][j] == 'x') {
                    xLoc.get(i).add(j);
                }
            }
        });

        // System.out.println("Posisi C = "+ cp[0] + " " + cp[1]);
        // System.out.println("Posisi D = "+ dp[0] + " " + dp[1]);
        // System.out.println("Posisi M = " + wP);
        // System.out.println("M Space : ");
        // mSpace.forEach((n) -> System.out.println(n));

        xLoc.forEach((k, v) -> {
            System.out.println("Obs ke- " + k);
            System.out.println("XLoc = " + v);
            v.forEach((n) -> System.out.println(n));
        });
        // lSpace.forEach((n) -> System.out.println(n));
        // tSpace.forEach((n) -> System.out.println(n));
        // rSpace.forEach((n) -> System.out.println(n));
        // bSpace.forEach(x -> System.out.println(x));
        // 1 - m.length - 2

        /// logic
        // available = mSpace - xLoc.get(wP - 1) - xLoc.get(wP + 1);
        // if (mSpace.isNotEpty)

        for (int i = 0; i < mSpace.size(); i++) {
            if ((xLoc.get(wP - 1) != null && xLoc.get(wP - 1).contains(mSpace.get(i)))
                    || (xLoc.get(wP + 1) != null && xLoc.get(wP + 1).contains(mSpace.get(i)))) {
                mSpace.remove(i);
            }
        }

        // ceck if D and C is separated by blocking x's
        boolean hasBlocking = false;

        for (int k : xLoc.keySet()) {
            ArrayList<Integer> v = xLoc.get(k);
            System.out.println("cp[0] = " + cp[0]);
            System.out.println("dp[0] = " + dp[0]);
            System.out.println("k = " + k);
            System.out.println("v = " + v);

            if(v.size() == l - 2) {
                if((cp[0] < k && dp[0] > k) || (cp[0] > k && dp[0] < k)) {
                    hasBlocking = true;
                }
            }
        }

        System.out.println("Has blocking = " + hasBlocking);

        if((mSpace.size() > 0 && !hasBlocking) || (cp[0] > wP && dp[0] > wP) || (cp[0] < wP && dp[0] < wP)) {
            System.out.println("Dawala bertemu cepot");
        } else {
            System.out.println("Dawala tidak bertemu cepot");
        }

        for (int k : xLoc.keySet()) {
            ArrayList<Integer> v = xLoc.get(k);
            // calculate available left wall
            if(v.contains(1) && lSpace.contains(k)) {
                lSpace.remove(lSpace.indexOf(k));
            }

            if(v.contains(l - 2) && rSpace.contains(k)) {
                rSpace.remove(rSpace.indexOf(k));
            }

            if(k == 1) {
                // tSpace - v
                tSpace.removeAll(v);
            }

            if(k == p - 2) {
                bSpace.removeAll(v);
            }
        }

        // for (int i = 1; i < l - 1; i++) {
        //     if(tSpace.contains(i))
        // }

        boolean isWallBlocked = mSpace.isEmpty();
        boolean isAcross = (cp[0] < wP && !bSpace.isEmpty()) || (cp[0] > wP && !tSpace.isEmpty());
        boolean hasAccess = false;

        if(isAcross && isWallBlocked) { // kalau ketutup jalan
            if(cp[0] < wP && !tSpace.isEmpty()) { // kalau ada jalan keluar di atas
                hasAccess = true;
            }
            if(cp[0] > wP && !bSpace.isEmpty()) { // kalau ada jalan keluar di bawah
                hasAccess = true;
            }
        } else {
            hasAccess = true;
        }

        if(hasAccess && (tSpace.size() > 0 || bSpace.size() > 0 || lSpace.size() > 0 || rSpace.size() > 0)) {
            System.out.println("ada jalan Cepot lumpat");
        } else {
            System.out.println("tidak ada jalan Cepot lumpat");
        }

        sc.close();

    }

    // public static boolean isHWall(char[] p) {
    // for (int i = 0; i < p.length; i++) {
    // if (p[i] == ' ') {
    // return false;
    // }
    // }

    // return true;
    // }

}
