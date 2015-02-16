/*
 * #%L
 * SciJava mathematical expression parser.
 * %%
 * Copyright (C) 2015 Board of Regents of the University of
 * Wisconsin-Madison.
 * %%
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * #L%
 */

package org.scijava.sjep;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import java.util.LinkedList;

import org.junit.Test;

/**
 * Tests {@link ExpressionParser}.
 *
 * @author Curtis Rueden
 */
public class ExpressionParserTest extends AbstractTest {

	// NB: Disabled until number parsing stops sucking.
	//@Test
	public void testNumbers() {
		final ExpressionParser parser = new ExpressionParser();
		final LinkedList<Object> queue = parser.parsePostfix("1.1+2L+3f+4");

		assertNotNull(queue);
		assertSame(7, queue.size());
		assertSame(1.1, queue.get(0));
		assertSame(2L, queue.get(1));
		assertSame(3f, queue.get(3));
		assertSame(4, queue.get(5));
	}

	@Test
	public void testParseUnaryOperator1() {
		final ExpressionParser parser = new ExpressionParser();
		final LinkedList<Object> queue = parser.parsePostfix("-a");

		assertNotNull(queue);
		// [a, -]
		assertVariable("a", queue.get(0));
		assertSame(Operators.NEG, queue.get(1));
	}

	@Test
	public void testParseUnaryOperator2() {
		final ExpressionParser parser = new ExpressionParser();
		final LinkedList<Object> queue = parser.parsePostfix("--a");

		assertNotNull(queue);
		// [a, -, -]
		assertVariable("a", queue.get(0));
		assertSame(Operators.NEG, queue.get(1));
		assertSame(Operators.NEG, queue.get(2));
	}

	@Test
	public void testParseUnaryOperator3() {
		final ExpressionParser parser = new ExpressionParser();
		final LinkedList<Object> queue = parser.parsePostfix("a--b");

		assertNotNull(queue);
		// [a, b, -, -]
		assertVariable("a", queue.get(0));
		assertVariable("b", queue.get(1));
		assertSame(Operators.NEG, queue.get(2));
		assertSame(Operators.SUB, queue.get(3));
	}

	@Test
	public void testParseUnaryOperator4() {
		final ExpressionParser parser = new ExpressionParser();
		final LinkedList<Object> queue = parser.parsePostfix("a---b");

		assertNotNull(queue);
		// [a, b, -, -, -]
		assertVariable("a", queue.get(0));
		assertVariable("b", queue.get(1));
		assertSame(Operators.NEG, queue.get(2));
		assertSame(Operators.NEG, queue.get(3));
		assertSame(Operators.SUB, queue.get(4));
	}

	@Test
	public void testParseUnaryOperator5() {
		final ExpressionParser parser = new ExpressionParser();
		final LinkedList<Object> queue = parser.parsePostfix("--a---b");

		assertNotNull(queue);
		// [a, b, -, -, -]
		assertVariable("a", queue.get(0));
		assertSame(Operators.NEG, queue.get(1));
		assertSame(Operators.NEG, queue.get(2));
		assertVariable("b", queue.get(3));
		assertSame(Operators.NEG, queue.get(4));
		assertSame(Operators.NEG, queue.get(5));
		assertSame(Operators.SUB, queue.get(6));
	}

	@Test
	public void testParseBinaryOperator() {
		final ExpressionParser parser = new ExpressionParser();
		final LinkedList<Object> queue = parser.parsePostfix("a-b");

		assertNotNull(queue);
		// [a, b, -]
		assertVariable("a", queue.get(0));
		assertVariable("b", queue.get(1));
		assertSame(Operators.SUB, queue.get(2));
	}

	@Test
	public void testParseUnaryFunction() {
		final ExpressionParser parser = new ExpressionParser();
		final LinkedList<Object> queue = parser.parsePostfix("f(a)");

		assertNotNull(queue);
		// [a, f]
		assertVariable("a", queue.get(0));
		assertFunction("f", 1, queue.get(1));
	}

	@Test
	public void testParseBinaryFunction() {
		final ExpressionParser parser = new ExpressionParser();
		final LinkedList<Object> queue = parser.parsePostfix("f(a,b)");

		assertNotNull(queue);
		// [a, b, f]
		assertVariable("a", queue.get(0));
		assertVariable("b", queue.get(1));
		assertFunction("f", 2, queue.get(2));
	}

	@Test
	public void testParseTernaryFunction() {
		final ExpressionParser parser = new ExpressionParser();
		final LinkedList<Object> queue = parser.parsePostfix("f(a,b,c)");

		assertNotNull(queue);
		// [a, b, c, f]
		assertVariable("a", queue.get(0));
		assertVariable("b", queue.get(1));
		assertVariable("c", queue.get(2));
		assertFunction("f", 3, queue.get(3));
	}

	@Test
	public void testOperatorPrecedence() {
		final ExpressionParser parser = new ExpressionParser();
		final LinkedList<Object> queue = parser.parsePostfix("a+b*c");

		assertNotNull(queue);
		// [a, b, c, *, +]
		assertVariable("a", queue.get(0));
		assertVariable("b", queue.get(1));
		assertVariable("c", queue.get(2));
		assertSame(Operators.MUL, queue.get(3));
		assertSame(Operators.ADD, queue.get(4));
	}

	@Test
	public void testOperatorAssociativity() {
		final ExpressionParser parser = new ExpressionParser();
		final LinkedList<Object> queue = parser.parsePostfix("(a/b/c)+(a^b^c)");

		assertNotNull(queue);
		// [a, b, /, c, /, a, b, c, ^, ^, +]
		assertVariable("a", queue.get(0));
		assertVariable("b", queue.get(1));
		assertSame(Operators.DIV, queue.get(2));
		assertVariable("c", queue.get(3));
		assertSame(Operators.DIV, queue.get(4));
		assertVariable("a", queue.get(5));
		assertVariable("b", queue.get(6));
		assertVariable("c", queue.get(7));
		assertSame(Operators.POW, queue.get(8));
		assertSame(Operators.POW, queue.get(9));
		assertSame(Operators.ADD, queue.get(10));
	}

	/** A more complex test of {@link ExpressionParser#parsePostfix}. */
	@Test
	public void testParsePostfix() {
		final ExpressionParser parser = new ExpressionParser();
		final LinkedList<Object> queue =
			parser.parsePostfix("a*b-c*a/func(quick,brown,fox)^foo^bar");

		assertNotNull(queue);
		assertEquals(16, queue.size());
		// [a, b, *, c, a, *, quick, brown, fox, func, foo, bar, ^, ^, /, -]
		assertVariable("a", queue.get(0));
		assertVariable("b", queue.get(1));
		assertSame(Operators.MUL, queue.get(2));
		assertVariable("c", queue.get(3));
		assertVariable("a", queue.get(4));
		assertSame(Operators.MUL, queue.get(5));
		assertVariable("quick", queue.get(6));
		assertVariable("brown", queue.get(7));
		assertVariable("fox", queue.get(8));
		assertFunction("func", 3, queue.get(9));
		assertVariable("foo", queue.get(10));
		assertVariable("bar", queue.get(11));
		assertSame(Operators.POW, queue.get(12));
		assertSame(Operators.POW, queue.get(13));
		assertSame(Operators.DIV, queue.get(14));
		assertSame(Operators.SUB, queue.get(15));
	}

}