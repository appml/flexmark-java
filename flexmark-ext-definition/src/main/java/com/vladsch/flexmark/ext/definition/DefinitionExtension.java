
package com.vladsch.flexmark.ext.definition;

import com.vladsch.flexmark.Extension;
import com.vladsch.flexmark.ext.definition.internal.*;
import com.vladsch.flexmark.formatter.internal.Formatter;
import com.vladsch.flexmark.util.format.options.DefinitionMarker;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.options.DataKey;
import com.vladsch.flexmark.util.options.MutableDataHolder;

/**
 * Extension for definitions
 * <p>
 * Create it with {@link #create()} and then configure it on the builders
 * ({@link com.vladsch.flexmark.parser.Parser.Builder#extensions(Iterable)},
 * {@link com.vladsch.flexmark.html.HtmlRenderer.Builder#extensions(Iterable)}).
 * </p>
 * <p>
 * The parsed definition text is turned into {@link DefinitionList}, {@link DefinitionTerm} and {@link DefinitionItem} nodes.
 * </p>
 */
public class DefinitionExtension implements Parser.ParserExtension, HtmlRenderer.HtmlRendererExtension, Formatter.FormatterExtension {
    public static final DataKey<Boolean> COLON_MARKER = new DataKey<>("COLON_MARKER", true);
    public static final DataKey<Integer> MARKER_SPACES = new DataKey<>("MARKER_SPACE", 1);
    public static final DataKey<Boolean> TILDE_MARKER = new DataKey<>("TILDE_MARKER", true);

    // TODO: implement formatter for this extension
    public static final DataKey<Integer> FORMAT_MARKER_SPACES = new DataKey<>("MARKER_SPACE", 3);
    public static final DataKey<DefinitionMarker> FORMAT_MARKER_TYPE = new DataKey<>("FORMAT_MARKER_TYPE", DefinitionMarker.ANY);

    private DefinitionExtension() {
    }

    public static Extension create() {
        return new DefinitionExtension();
    }

    @Override
    public void extend(final Formatter.Builder builder) {
        builder.nodeFormatterFactory(new DefinitionNodeFormatter.Factory());
    }

    @Override
    public void rendererOptions(final MutableDataHolder options) {

    }

    @Override
    public void parserOptions(final MutableDataHolder options) {

    }

    @Override
    public void extend(Parser.Builder parserBuilder) {
        parserBuilder.customBlockParserFactory(new DefinitionItemBlockParser.Factory());
        parserBuilder.blockPreProcessorFactory(new DefinitionListItemBlockPreProcessor.Factory());
        parserBuilder.blockPreProcessorFactory(new DefinitionListBlockPreProcessor.Factory());
    }

    @Override
    public void extend(HtmlRenderer.Builder rendererBuilder, String rendererType) {
        switch (rendererType) {
            case "HTML":
                rendererBuilder.nodeRendererFactory(new DefinitionNodeRenderer.Factory());
                break;

            case "JIRA":
            case "YOUTRACK":
                break;
        }
    }
}
