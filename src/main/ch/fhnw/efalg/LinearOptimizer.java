package ch.fhnw.efalg;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class LinearOptimizer {

  public static void main(String[] args) {
    if (args.length < 1)
      throw new RuntimeException("usage error");
    String inputFile = args[0];
    LinearProblem linearProblem = readInitialProblem(inputFile);
    LinearProblem solution = solveLinearProblem(linearProblem);
    System.out.println("-*- solution -*-");
    System.out.println(solution);
  }

  static LinearProblem solveLinearProblem(LinearProblem problem) {
    long limit = problem.upperLimit();
    int step = 0;
    double[][] A;
    double[] f, c;
    double d;
    int p, q, m, n;
    while(limit-- > 0) {
      System.out.println("-*- Step[" + ++step + "] -*-");
      System.out.println(problem);
      m = problem.getM();
      n = problem.getN();
      c = problem.getC();
      d = problem.getD();
      A = problem.getA();
      f = problem.getF();
      q = problem.getPivotColumn();
      p = problem.getPivotRow(q);

      c[p] = -c[p] / A[p][q];
      for(int i = 0; i < m; i++) {
        if(i != p) c[i] = c[i] - (c[p]*A[i][q]/A[p][q]);
      }
      d = d - (c[p]*f[q]/A[p][q]);
      problem = new LinearProblem(A, c, f, d);
      if(problem.solutionFound()) return problem;
    }
    return problem;
  }

  private static LinearProblem readInitialProblem(String inputFile) {
    LinearProblem linearProblem = null;
    try {
      InputStream file = new FileInputStream(inputFile);
      linearProblem = IOUtility.readLinearProblem(file);

    } catch (FileNotFoundException e) {
      System.out.printf("Could not load file %s. File not found.", inputFile);
    }
    return linearProblem;
  }
}
