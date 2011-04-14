package com.github.wicketconsole.pages;

import org.wicketstuff.console.ClojureScriptEnginePanel;

public class ClojurePage extends ConsoleBasePage {

    private static final long serialVersionUID = 1L;

    public ClojurePage() {

        final ClojureScriptEnginePanel enginePanel = new ClojureScriptEnginePanel(
                "console");
        enginePanel.setInput("(println 42)");
        add(enginePanel);

    }

}
