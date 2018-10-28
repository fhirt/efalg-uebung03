package ch.fhnw.efalg;

import java.util.ArrayList;
import java.util.Arrays;

public class Constraint {

  private final int[] coefficients;
  private final String type;

  Constraint(String type, int[] coefficients) {
    this.type = type.trim();
    this.coefficients = Arrays.copyOf(coefficients, coefficients.length);
  }

  public ArrayList<int[]> transformToStandardForm() {
    ArrayList<int[]> standardised = new ArrayList<>();
    if (type.equals("<=")) {
      standardised.add(negateVars());
    } else if (type.equals(">=")) {
      standardised.add(negateConst());
    } else {
      standardised.add(negateConst());
      standardised.add(negateVars());
    }
    return standardised;
  }

  private int[] negateConst() {
    int[] standardised = new int[coefficients.length];
    for (int i = 0; i < coefficients.length; i++) {
      standardised[i] = coefficients[i];
      if (i == coefficients.length - 1) {
        standardised[i] *= -1;
      }
    }
    return standardised;
  }

  private int[] negateVars() {
    int[] standardised = new int[coefficients.length];
    for (int i = 0; i < coefficients.length; i++) {
      standardised[i] = coefficients[i];
      if (i < coefficients.length - 1)
        standardised[i] *= -1;
    }
    return standardised;
  }
}
