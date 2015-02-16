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
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

/**
 * Base class for unit test classes.
 *
 * @author Curtis Rueden
 */
public class AbstractTest {

	// -- Helper methods --

	protected void assertNumber(final Number expected, final Number actual) {
		assertSame(expected.getClass(), actual.getClass());
		assertEquals(expected, actual);
	}

	protected void assertFunction(final String expected, final int arity,
		final Object token)
	{
		assertInstance(token, Function.class);
		final Function function = (Function) token;
		assertEquals(expected, function.getToken());
		assertEquals(arity, function.getArity());
	}

	protected void assertVariable(final String expected, final Object token) {
		assertInstance(token, Variable.class);
		assertEquals(expected, ((Variable) token).getToken());
	}

	protected void assertInstance(final Object token, final Class<?> c) {
		assertTrue(token.getClass().getName(), c.isInstance(token));
	}

	protected void assertCount(final int expected, final SyntaxTree tree) {
		assertEquals(expected, tree.count());
	}

	protected void assertToken(final String expected, final Object token) {
		assertEquals(expected, token.toString());
	}

}