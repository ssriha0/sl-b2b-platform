package extra.crispy;

import org.junit.Test
import static org.junit.Assert.assertEquals

/** Demonstrate using Groovy with JUnit4 (even though JUnit3 is
 *  built in) */
class JUnit4Test {
    final shouldFail = new GroovyTestCase().&shouldFail

    @Test
    void divideByZero() {
        shouldFail(ArithmeticException) {
            println ("got the expected failure for divide by zero");
            1/0;
        }
    }
}

