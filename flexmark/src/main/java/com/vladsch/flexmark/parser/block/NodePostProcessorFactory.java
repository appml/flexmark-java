package com.vladsch.flexmark.parser.block;

import com.vladsch.flexmark.ast.Document;
import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.parser.PostProcessorFactory;

import java.util.*;

public abstract class NodePostProcessorFactory implements PostProcessorFactory {
    private final HashMap<Class<? extends Node>, Set<Class<?>>> NODE_MAP = new HashMap<>();

    // added to force constructor
    public NodePostProcessorFactory(boolean ignored) {
    }

    @Override
    public Set<Class<? extends PostProcessorFactory>> getAfterDependents() {
        return null;
    }

    @Override
    public Set<Class<? extends PostProcessorFactory>> getBeforeDependents() {
        return null;
    }

    @Override
    public final boolean affectsGlobalScope() {
        return false;
    }

    protected final void addNodeWithExclusions(Class<? extends Node> nodeType, Class<?>... excludeDescendantsOf) {
        if (excludeDescendantsOf.length > 0) {
            NODE_MAP.put(nodeType, new HashSet<>(Arrays.asList(excludeDescendantsOf)));
        } else {
            addNodes(nodeType);
        }
    }

    @SafeVarargs
    protected final void addNodes(Class<? extends Node>... nodeTypes) {
        for (Class<? extends Node> nodeType : nodeTypes) {
            //noinspection unchecked
            NODE_MAP.put(nodeType, Collections.EMPTY_SET);
        }
    }

    @Override
    public final Map<Class<? extends Node>, Set<Class<?>>> getNodeTypes() {
        return NODE_MAP;
    }

    @Override
    abstract public NodePostProcessor create(Document document);
}
