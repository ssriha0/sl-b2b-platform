package extra.crispy;

import groovy.util.GroovyTestCase

class GroovyTest extends GroovyTestCase {

    void testSomething() {
        assert 1 == 1
        assert 2 + 2 == 4 : "We're in trouble, arithmetic is broken"
        shouldFail(ArithmeticException) {
            println ("got the expected failure for divide by zero");
            1/0;
        }

    }
}

