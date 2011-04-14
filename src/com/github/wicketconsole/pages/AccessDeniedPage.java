package com.github.wicketconsole.pages;

import org.apache.wicket.markup.html.pages.RedirectPage;

public class AccessDeniedPage extends RedirectPage {

    private static final long serialVersionUID = 1L;

    public AccessDeniedPage() {
        super("/denied.html");
    }

}
