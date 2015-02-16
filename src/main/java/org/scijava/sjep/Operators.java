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

import java.util.Arrays;
import java.util.List;

import org.scijava.sjep.Operator.Associativity;

/**
 * A collection of standard {@link Operator}s.
 *
 * @author Curtis Rueden
 */
public final class Operators {

	public static final Operator NEG = new DefaultOperator("-", "neg", 1,
		Associativity.RIGHT, 2);

	public static final Operator ADD = new DefaultOperator("+", "add", 2,
		Associativity.EITHER, 2);
	public static final Operator SUB = new DefaultOperator("-", "sub", 2,
		Associativity.LEFT, 2);
	public static final Operator MUL = new DefaultOperator("*", "mul", 2,
		Associativity.EITHER, 3);
	public static final Operator DIV = new DefaultOperator("/", "div", 2,
		Associativity.LEFT, 3);
	public static final Operator POW = new DefaultOperator("^", "pow", 2,
		Associativity.RIGHT, 4);

	private Operators() {
		// NB: Prevent instantiation of utility class.
	}

	/** Gets the standard list of operators. */
	public static List<Operator> standardList() {
		return Arrays.asList(Operators.NEG, Operators.ADD, Operators.SUB,
			Operators.MUL, Operators.DIV, Operators.POW);
	}

}