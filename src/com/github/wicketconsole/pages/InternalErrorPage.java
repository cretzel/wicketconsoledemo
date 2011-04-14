package com.github.wicketconsole.pages;

import org.apache.wicket.markup.html.pages.RedirectPage;

public class InternalErrorPage extends RedirectPage {

    private static final long serialVersionUID = 1L;

    public InternalErrorPage() {
        super("/500.html");
    }

}
