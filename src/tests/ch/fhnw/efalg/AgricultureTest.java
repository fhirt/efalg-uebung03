package ch.fhnw.efalg;

import static junit.framework.TestCase.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class AgricultureTest {
  private LinearProblem agricultureProblem;

  @Before
  public void setUp() {
    double d = 0;
    double[] c = {0, 0, 40, 2400, 312};
    double[] f = {100, 250};
    double[][] A = {
        {1, 0},
        {0, 1},
        {-1, -1},
        {-40, -120},
        {-7, -12}
    };
    agricultureProblem = new LinearProblem(A, c, f, d);
  }

  @Test
  public void testSetup() {
    LinearProblem solution = LinearOptimizer.solveLinearProblem(agricultureProblem);
    assertTrue("something",true);
  }
}
