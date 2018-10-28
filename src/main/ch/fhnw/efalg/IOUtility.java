package ch.fhnw.efalg;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;
import java.util.Scanner;

public class IOUtility {

  public static LinearProblem readLinearProblem(InputStream problemStream) throws FileNotFoundException {
    Queue<String[]> lines = readLinesAndSplitWords(problemStream);
    // parse first line
    String[] line = lines.remove();
    int n = Integer.parseInt(line[0]);
    int m = Integer.parseInt(line[1]);

    // parse target function
    line = lines.remove();
    int targetFunct[] = readTargetFunction(line);

    // parse non-negative constraints
    ArrayList<Constraint> constraints = new ArrayList<>();
    line = lines.remove();
    int[] constraint;
    boolean onePhaseMethod = true;
    for (int i = 0; i < line.length; i++) {
      if (line[i].toLowerCase().trim().equals("true")) {
        constraint = new int[n + 1]; // one field more than vars => constant = 0
        constraint[i] = 1;
        constraints.add(new Constraint(">=", constraint));
      } else {
        onePhaseMethod = false;
      }
    }

    // parse the rest of the constraints
    String symbol;
    while (!lines.isEmpty()) {
      constraint = new int[n + 1];
      line = lines.remove();
      symbol = line[0];
      for (int i = 1; i < line.length; i++) {
        constraint[i - 1] = Integer.parseInt(line[i]);
      }
      constraints.add(new Constraint(symbol, constraint));
    }
    ArrayList<int[]> constr = new ArrayList<>();
    for (Constraint c : constraints) {
      constr.addAll(c.transformToStandardForm());
    }
    m = constr.size();
    double[][] A = new double[m][n];
    double[] c = new double[m];
    int[] tmp;
    for(int i = 0; i < m; i++) {
      tmp = constr.get(i);
      for(int j = 0; j < n; j++) {
        A[i][j] = tmp[j];
      }
      c[i] = tmp[n];
    }

    double[] f = new double[n];
    for(int j = 0; j < n; j++) {
      f[j] = targetFunct[j];
    }
    double d = targetFunct[n];

    return new LinearProblem(A, c, f, d);
  }

  private static Queue<String[]> readLinesAndSplitWords(InputStream problemStream) {
    Queue<String[]> lines = new ArrayDeque<>();
    try (Scanner input = new Scanner(problemStream)) {
      while (input.hasNextLine()) {
        lines.add(input.nextLine().split(";"));
      }
    }
    return lines;
  }

  private static int[] readTargetFunction(String[] line) {
    int[] targetFunc = new int[line.length - 1];
    String funcType = line[0];
    boolean isMaximizingProblem = funcType.toLowerCase().trim().equals("max");
    int tmp;
    for (int i = 1; i < line.length; i++) {
      tmp = Integer.parseInt(line[i]);
      if (!isMaximizingProblem)
        tmp = -tmp;
      targetFunc[i - 1] = tmp;
    }
    return targetFunc;
  }
}
