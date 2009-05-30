/*
 * Copyright 2006-2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.batch.core.step.item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.support.transaction.TransactionAwareProxyFactory;

/**
 * @author Dan Garrette
 * @since 2.0.1
 */
public class SkipProcessorStub<T> extends ExceptionThrowingItemHandlerStub<T> implements ItemProcessor<T, T> {

	private List<T> processed = new ArrayList<T>();

	private List<T> committed = TransactionAwareProxyFactory.createTransactionalList();

	public SkipProcessorStub() {
		super();
	}

	public SkipProcessorStub(T... failures) {
		super(Arrays.asList(failures));
	}

	public SkipProcessorStub(boolean runtimeException, T... failures) {
		this(failures);
		this.setRuntimeException(runtimeException);
	}

	public List<T> getProcessed() {
		return processed;
	}

	public List<T> getCommitted() {
		return committed;
	}

	public void clear() {
		processed = new ArrayList<T>();
		committed = TransactionAwareProxyFactory.createTransactionalList();
		this.setFailures(new ArrayList<T>());
	}

	public T process(T item) throws Exception {
		processed.add(item);
		committed.add(item);
		checkFailure(item);
		return item;
	}
}