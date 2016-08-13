package com.fnklabs.flene.http;

import com.fnklabs.flene.Flene;
import org.apache.ignite.cluster.ClusterMetrics;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ClusterInfoServlet extends FleneHttpServlet {

    public ClusterInfoServlet(Flene flene, MessageMarshaller marshaller) {
        super(flene, marshaller);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ClusterMetrics clusterMetrics = getFlene().getClusterMetrics();

        String serialized = getMarshaller().serialize(clusterMetrics);

        resp.getOutputStream().write(serialized.getBytes());
    }
}
