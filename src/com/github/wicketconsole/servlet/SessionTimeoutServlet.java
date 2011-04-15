package com.github.wicketconsole.servlet;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;

public class SessionTimeoutServlet extends HttpServlet {

    private static final int MAX_AGE = 1000 * 60 * 60 * 24; // 1 day
    private static final int MAX_SESSIONS_PER_GET = 200;
    private static final long serialVersionUID = 1L;
    private DatastoreService ds;
    private transient Logger logger;

    @Override
    public void init() throws ServletException {
        super.init();
        ds = DatastoreServiceFactory.getDatastoreService();
    }

    @Override
    protected void doGet(final HttpServletRequest req,
            final HttpServletResponse resp) throws ServletException,
            IOException {

        final Query query = new Query("_ah_SESSION");
        final long expires = System.currentTimeMillis() - MAX_AGE;
        query.addFilter("_expires", FilterOperator.LESS_THAN, expires);

        final PreparedQuery pq = ds.prepare(query);
        final Iterable<Entity> entities = pq.asIterable();

        final StringBuilder log = new StringBuilder(
                "### Killing Sessions since " + new Date(expires)).append("\n");
        int i = 0;

        for (final Entity entity : entities) {

            final Object expires1 = entity.getProperty("_expires");
            log.append("Deleting session with date " + expires1).append("\n");
            ds.delete(entity.getKey());

            if (i == MAX_SESSIONS_PER_GET)
                break;
            i = i + 1;
        }

        log.append(i + " Sessions killed\n");
        getLogger().info(log.toString());
        resp.getWriter().write(log.toString());
    }

    public Logger getLogger() {
        if (logger == null) {
            logger = Logger.getLogger(SessionTimeoutServlet.class);
        }
        return logger;
    }
}
