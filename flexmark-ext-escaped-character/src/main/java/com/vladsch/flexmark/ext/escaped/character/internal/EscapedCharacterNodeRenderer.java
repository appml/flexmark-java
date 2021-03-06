package com.vladsch.flexmark.ext.escaped.character.internal;

import com.vladsch.flexmark.ext.escaped.character.EscapedCharacter;
import com.vladsch.flexmark.html.CustomNodeRenderer;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.NodeRenderer;
import com.vladsch.flexmark.html.renderer.NodeRendererContext;
import com.vladsch.flexmark.html.renderer.NodeRendererFactory;
import com.vladsch.flexmark.html.renderer.NodeRenderingHandler;
import com.vladsch.flexmark.util.options.DataHolder;

import java.util.HashSet;
import java.util.Set;

public class EscapedCharacterNodeRenderer implements NodeRenderer {
    private final EscapedCharacterOptions options;

    public EscapedCharacterNodeRenderer(DataHolder options) {
        this.options = new EscapedCharacterOptions(options);
    }

    @Override
    public Set<NodeRenderingHandler<?>> getNodeRenderingHandlers() {
        HashSet<NodeRenderingHandler<?>> set = new HashSet<>();
        set.add(new NodeRenderingHandler<>(EscapedCharacter.class, new CustomNodeRenderer<EscapedCharacter>() {
            @Override
            public void render(EscapedCharacter node, NodeRendererContext context, HtmlWriter html) {
                EscapedCharacterNodeRenderer.this.render(node, context, html);
            }
        }));

        return set;
    }

    private void render(EscapedCharacter node, NodeRendererContext context, HtmlWriter html) {
        html.text(node.getChars().unescape());
    }

    public static class Factory implements NodeRendererFactory {
        @Override
        public NodeRenderer create(final DataHolder options) {
            return new EscapedCharacterNodeRenderer(options);
        }
    }
}
