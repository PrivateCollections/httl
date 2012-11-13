/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package httl.spi.translators.expression;


import java.io.Serializable;
import java.text.ParseException;
import java.util.Map;


/**
 * Node
 * 
 * @author Liang Fei (liangfei0201 AT gmail DOT com)
 */
public abstract class Node implements Serializable {

	private static final long serialVersionUID = 1L;

    private final int offset;
    
    private final Map<String, Class<?>> parameterTypes;

    public Node(Map<String, Class<?>> parameterTypes, int offset) {
        this.offset = offset;
        this.parameterTypes = parameterTypes;
    }
    
    public int getOffset() {
        return offset;
    }

    public Map<String, Class<?>> getParameterTypes() {
        return parameterTypes;
    }

    public Class<?>[] getReturnTypes() throws ParseException {
        Class<?> type = getReturnType();
        return type == null ? new Class<?>[0] : new Class<?>[] { type };
    }

    public String getVariableName() {
    	return getVariableName(this);
    }

    protected static String getVariableName(Node node) {
    	do {
			if (node instanceof BinaryOperator) {
				node = ((BinaryOperator)node).getLeftParameter();
			} else if (node instanceof UnaryOperator) {
				node = ((UnaryOperator)node).getParameter();
			}
			if (node instanceof Variable) {
        		return ((Variable)node).getName();
			}
		} while (node instanceof Operator);
    	return null;
    }

    public abstract Class<?> getReturnType() throws ParseException;

    public abstract String getCode() throws ParseException;

}
