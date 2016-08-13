package com.fnklabs.flene.http;

import com.fnklabs.flene.Flene;

import javax.servlet.http.HttpServlet;

public abstract class FleneHttpServlet extends HttpServlet {
    private final Flene flene;

    private final MessageMarshaller marshaller;

    protected FleneHttpServlet(Flene flene, MessageMarshaller marshaller) {
        this.flene = flene;
        this.marshaller = marshaller;
    }

    protected Flene getFlene() {
        return flene;
    }

    protected MessageMarshaller getMarshaller() {
        return marshaller;
    }
}
