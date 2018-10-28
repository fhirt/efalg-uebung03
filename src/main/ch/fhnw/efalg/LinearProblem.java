package ch.fhnw.efalg;

import java.util.Arrays;

public class LinearProblem {

  private final double d;
  private final double[] f;
  private final double[][] A;
  private final double[] c;
  private final int n;
  private final int m;

  LinearProblem(double[][] A, double[] c, double[] f, double d) {
    this.n = f.length;
    this.m = A.length;
    this.c = Arrays.copyOf(c, m);
    this.f = Arrays.copyOf(f, n);
    this.d = d;
    this.A = copyOfA(A);
  }

  private double[][] copyOfA(double[][] A) {
    double[][] tmp = new double[A.length][];
    for (int i = 0; i < A.length; i++) {
      tmp[i] = Arrays.copyOf(A[i], A[i].length);
    }
    return tmp;
  }

  /**
   * @return
   */
  public long upperLimit() {
    int top = n + m;
    int bottom = m;
    long limit = 1;
    if (bottom > top - bottom)
      bottom = top - bottom;

    for (int i = 1, k = top; i <= bottom; i++, k--) {
      limit = limit * k / i;
    }
    return limit;
  }

  /**
   * Determines the column number of the pivot element
   *
   * @return the index of the column, or -1 if the algorithm fails
   */
  public int getPivotColumn() {
    int index = -1;
    for (int i = 0; i < f.length; i++) {
      if (f[i] > 0)
        return i;
      if (f[i] == 0)
        index = i;
    }
    return index;
  }

  public double getD() {
    return d;
  }

  public double[] getF() {
    return Arrays.copyOf(f, n);
  }

  public double[][] getA() {
    return copyOfA(A);
  }

  public double[] getC() {
    return Arrays.copyOf(c, m);
  }

  public int getN() {
    return n;
  }

  public int getM() {
    return m;
  }

  /**
   * Determines the row number of the pivot element
   *
   * @param q the column number of the pivot element, as determined by getPivotColumn()
   * @return the index of the row, or -1 if the algorithm fails
   */
  public int getPivotRow(int q) {
    if (q == -1)
      return -1;
    double Qi = Integer.MAX_VALUE;
    double tmp;
    int index = -1;
    for (int i = 0; i < A.length; i++) {
      if (A[i][q] < 0) {
        tmp = Math.abs(c[i] / A[i][q]);
        if (tmp < Qi) {
          Qi = tmp;
          index = i;
        }
      }
    }
    return index;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    String tmp;
    builder.append("   |");
    for (int i = 0; i < n; i++) {
      tmp = "x" + i;
      builder.append(String.format("%3s ", tmp));
    }
    builder.append(String.format("%3s \n", "c"));

    for (int i = 0; i <= n+1; i++) {
      builder.append("----");
    }
    builder.append('\n');

    for (int i = 0; i < m; i++) {
      tmp = "y" + i;
      builder.append(String.format("%2s |", tmp));
      for (double a : A[i]) {
        builder.append(String.format("%3f ", a));
      }
      builder.append(String.format("%3f \n", c[i]));
    }

    builder.append(" z |");
    for (double b : f) {
      builder.append(String.format("%3f ", b));
    }
    builder.append(String.format("%3f ", d));

    return builder.toString();
  }

  public boolean solutionFound() {
    for(double b : f) {
      if(b > 0) return false;
    }
    return true;
  }
}
