package com.fnklabs.flene.http;

import com.fnklabs.flene.Flene;
import org.apache.ignite.cluster.ClusterMetrics;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class NodesMetricsServlet extends FleneHttpServlet {

    protected NodesMetricsServlet(Flene flene, MessageMarshaller marshaller) {
        super(flene, marshaller);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, ClusterMetrics> nodesMetrics = getFlene().getNodesMetrics();

        resp.getOutputStream().write(getMarshaller().serialize(nodesMetrics).getBytes());
    }
}
