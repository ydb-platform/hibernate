package org.jooq.impl;

import org.jooq.Name;
import org.jooq.QueryPart;
import org.jooq.RenderContext;
import org.jooq.VisitContext;
import org.jooq.VisitListener;

/**
 * @author Kirill Kurdyukov
 */
public class YdbListener implements VisitListener {

    private final String quote;

    private volatile int hintedTableStartSize;

    public YdbListener(String quote) {
        this.quote = quote;
    }

    @Override
    public void visitStart(VisitContext context) {
        addQuoteForName(context);
        visitStartHint(context);
    }

    @Override
    public void visitEnd(VisitContext context) {
        addQuoteForName(context);
        try {
            visitEndHint(context);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private void addQuoteForName(VisitContext context) {
        QueryPart part = context.queryPart();
        if (part instanceof Name) {
            RenderContext renderContext = context.renderContext();
            if (renderContext != null) {
                renderContext.sql(quote);
            }
        }
    }

    private void visitStartHint(VisitContext context) {
        QueryPart part = context.queryPart();

        if (part instanceof QOM.HintedTable<?> hintedTable) {
            if (context.renderContext() instanceof DefaultRenderContext renderContext) {
                hintedTableStartSize = renderContext.sql.length();
            }
        }
    }

    private void visitEndHint(VisitContext context) throws NoSuchFieldException, IllegalAccessException {
        QueryPart part = context.queryPart();

        if (part instanceof HintedTable<?> hintedTable) {
            if (context.renderContext() instanceof DefaultRenderContext renderContext) {
                renderContext.sql.setLength(hintedTableStartSize);

                renderContext.sql(" view ");

                // Sorry, Lukas!!! :(
                java.lang.reflect.Field fieldArguments = hintedTable.getClass().getDeclaredField("arguments");
                fieldArguments.setAccessible(true);
                QueryPartList<Name> arguments = (QueryPartList<Name>)fieldArguments.get(hintedTable);
                renderContext.visit(arguments);
            }
        }
    }
}

