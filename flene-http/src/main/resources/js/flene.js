var graphPeriod = -19;
var gBInBytes = 1 / 1024 / 1024 / 1024;

function fleneMemoryUsage(htmlContainer, getClusterInfoFunc) {
    Highcharts.setOptions({
        global: {
            useUTC: false
        }
    });

    $(htmlContainer).highcharts({
        chart: {
            type: 'spline',
            animation: Highcharts.svg, // don't animate in old IE
            marginRight: 10,
            events: {
                load: function () {
                    // set up the updating of the chart each second

                    var heapMemoryUsedSeries = this.series[0];
                    var heapMemoryTotalSeries = this.series[1];
                    var nonHeapMemoryUsedSeries = this.series[2];
                    var nonHeapMemoryTotalSeries = this.series[3];

                    setInterval(function () {
                        var x = (new Date()).getTime(); // current time

                        getClusterInfoFunc(function (response) {

                            var heapMemoryUsed = response['heapMemoryUsed'] * gBInBytes;
                            heapMemoryUsedSeries.addPoint([x, heapMemoryUsed], true, true);

                            var heapMemoryTotal = response['heapMemoryTotal'] * gBInBytes;
                            heapMemoryTotalSeries.addPoint([x, heapMemoryTotal], true, true);

                            var nonHeapMemoryUsed = response['nonHeapMemoryUsed'] * gBInBytes;
                            nonHeapMemoryUsedSeries.addPoint([x, nonHeapMemoryUsed], true, true);

                            var nonHeapMemoryTotal = response['nonHeapMemoryTotal'] * gBInBytes;
                            nonHeapMemoryTotalSeries.addPoint([x, nonHeapMemoryTotal], true, true);
                        })
                    }, 5000);
                }
            }
        },
        title: {
            text: 'Ignite Memory Load'
        },
        xAxis: {
            type: 'datetime',
            tickPixelInterval: 150
        },
        yAxis: {
            title: {
                text: 'Memory usage'
            }
        },
        tooltip: {
            formatter: function () {
                return '<b>' + this.series.name + '</b><br/>' +
                    Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', this.x) + '<br/>' +
                    Highcharts.numberFormat(this.y, 2);
            }
        },
        legend: {
            enabled: false
        },
        exporting: {
            enabled: false
        },
        series: [
            {
                name: 'Heap memory used',
                data: (function () {
                    // generate an array of random data
                    var data = [],
                        time = (new Date()).getTime(),
                        i;

                    for (i = graphPeriod; i <= 0; i += 1) {
                        data.push({
                            x: time + i * 1000,
                            y: 0
                        });
                    }

                    console.log(data);
                    return data;
                }())
            },
            {
                name: 'Heap Memory Total',
                data: (function () {
                    // generate an array of random data
                    var data = [],
                        time = (new Date()).getTime(),
                        i;

                    for (i = graphPeriod; i <= 0; i += 1) {
                        data.push({
                            x: time + i * 1000,
                            y: 0
                        });
                    }

                    console.log(data);
                    return data;
                }())
            },
            {
                name: 'Non Heap Memory Used',
                data: (function () {
                    // generate an array of random data
                    var data = [],
                        time = (new Date()).getTime(),
                        i;

                    for (i = graphPeriod; i <= 0; i += 1) {
                        data.push({
                            x: time + i * 1000,
                            y: 0
                        });
                    }

                    console.log(data);
                    return data;
                }())
            },
            {
                name: 'Non Heap Memory Total',
                data: (function () {
                    // generate an array of random data
                    var data = [];
                    var time = (new Date()).getTime();

                    for (var i = graphPeriod; i <= 0; i += 1) {
                        data.push({x: time + i * 1000, y: 0});
                    }

                    console.log(data);
                    return data;
                }())
            }
        ]
    });
}

function fleneCpuLoad(htmlContainer, getClusterInfoFunc) {
    Highcharts.setOptions({
        global: {
            useUTC: false
        }
    });

    $(htmlContainer).highcharts({
        chart: {
            type: 'spline',
            animation: Highcharts.svg, // don't animate in old IE
            marginRight: 10,
            events: {
                load: function () {
                    // set up the updating of the chart each second

                    var cpuLoadSeries = this.series[0];
                    var avgCpuLoadSeries = this.series[1];

                    setInterval(function () {
                        var x = (new Date()).getTime(); // current time

                        getClusterInfoFunc(function (response) {
                            var currentCpuLoad = response['currentCpuLoad'] * 100;
                            cpuLoadSeries.addPoint([x, currentCpuLoad], true, true);

                            var averageCpuLoad = response['averageCpuLoad'] * 100;
                            avgCpuLoadSeries.addPoint([x, averageCpuLoad], true, true);
                        })
                    }, 5000);
                }
            }
        },
        title: {
            text: 'Ignite CPU Load'
        },
        xAxis: {
            type: 'datetime',
            tickPixelInterval: 150
        },
        yAxis: {
            title: {
                text: 'CPU Load'
            }
        },
        tooltip: {
            formatter: function () {
                return '<b>' + this.series.name + '</b><br/>' +
                    Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', this.x) + '<br/>' +
                    Highcharts.numberFormat(this.y, 2);
            }
        },
        legend: {
            enabled: false
        },
        exporting: {
            enabled: false
        },
        series: [
            {
                name: 'CPU Load',
                data: (function () {
                    // generate an array of random data
                    var data = [],
                        time = (new Date()).getTime(),
                        i;

                    for (i = graphPeriod; i <= 0; i += 1) {
                        data.push({
                            x: time + i * 1000,
                            y: 0
                        });
                    }

                    console.log(data);
                    return data;
                }())
            },
            {
                name: 'Avg CPU Load',
                data: (function () {
                    // generate an array of random data
                    var data = [],
                        time = (new Date()).getTime(),
                        i;


                    for (i = graphPeriod; i <= 0; i += 1) {
                        data.push({
                            x: time + i * 1000,
                            y: 0
                        });
                    }

                    console.log(data);
                    return data;
                }())
            }
        ]
    });
}


function fleneNodesInfo(htmlContainer, getNodesMetricsFunc) {
    window.setInterval(function () {

        getNodesMetricsFunc(function (nodesMetrics) {
            $(htmlContainer).html('');

            for (var key in nodesMetrics) {

                var nodeMetric = nodesMetrics[key];

                var tr = $('<tr></tr>');
                tr.append($('<td></td>').html(key));
                tr.append($('<td></td>').html(new Number(nodeMetric['currentCpuLoad'] * 100).toPrecision(2)));
                tr.append($('<td></td>').html(new Number(nodeMetric['averageCpuLoad'] * 100).toPrecision(2)));

                tr.append($('<td></td>').html(new Number(nodeMetric['heapMemoryUsed'] * gBInBytes).toPrecision(2)));
                tr.append($('<td></td>').html(new Number(nodeMetric['heapMemoryCommitted'] * gBInBytes).toPrecision(2)));
                tr.append($('<td></td>').html(new Number(nodeMetric['heapMemoryTotal'] * gBInBytes).toPrecision(2)));

                tr.append($('<td></td>').html(new Number(nodeMetric['nonHeapMemoryUsed'] * gBInBytes).toPrecision(2)));
                tr.append($('<td></td>').html(new Number(nodeMetric['nonHeapMemoryCommitted'] * gBInBytes).toPrecision(2)));
                tr.append($('<td></td>').html(new Number(nodeMetric['nonHeapMemoryMaximum'] * gBInBytes).toPrecision(2)));
                tr.append($('<td></td>').html(new Number(nodeMetric['nonHeapMemoryTotal'] * gBInBytes).toPrecision(2)));

                tr.append($('<td></td>').html(nodeMetric['sentMessagesCount']));
                tr.append($('<td></td>').html(new Number(nodeMetric['sentBytesCount'] * gBInBytes).toPrecision(2)));
                tr.append($('<td></td>').html(nodeMetric['receivedMessagesCount']));
                tr.append($('<td></td>').html(new Number(nodeMetric['receivedBytesCount'] * gBInBytes).toPrecision(2)));
                tr.append($('<td></td>').html(nodeMetric['outboundMessagesQueueSize']));


                tr.append($('<td></td>').html(nodeMetric['currentActiveJobs']));
                tr.append($('<td></td>').html(nodeMetric['currentWaitingJobs']));
                tr.append($('<td></td>').html(nodeMetric['currentRejectedJobs']));
                tr.append($('<td></td>').html(nodeMetric['totalRejectedJobs']));
                tr.append($('<td></td>').html(nodeMetric['totalCancelledJobs']));
                tr.append($('<td></td>').html(nodeMetric['totalExecutedJobs']));

                tr.append($('<td></td>').html(nodeMetric['currentJobWaitTime']));
                tr.append($('<td></td>').html(nodeMetric['maximumJobWaitTime']));
                tr.append($('<td></td>').html(nodeMetric['maximumJobExecuteTime']));
                tr.append($('<td></td>').html(nodeMetric['currentJobExecuteTime']));
                tr.append($('<td></td>').html(nodeMetric['totalExecutedTasks']));

                console.log(tr);

                $(htmlContainer).append(tr);
            }

        })
    }, 5000);

}

function fleneCacheInfo(htmlContainer, getCacheMetricsFunc, rebalanceFunction, getNodesMetrics) {
    window.setInterval(function () {

        getCacheMetricsFunc(function (cachesMetrics) {
            $(htmlContainer).html('');

            for (var key in cachesMetrics) {

                var cacheMetrics = cachesMetrics[key];


                var cacheName = $('<a></a>').html(key);
                cacheName.attr("data-cache-name", key);
                cacheName.click(function () {
                    getNodesMetrics($(this).attr('data-cache-name'));
                });
                var rebalanceBtn = $('<a class="btn btn-danger" role="button">Rebalance</a>');
                rebalanceBtn.attr("data-cache-name", key);
                var tr = $('<tr></tr>');

                tr.append($('<td></td>').html(cacheName));
                tr.append($('<td></td>').html(cacheMetrics['cacheHits']));
                tr.append($('<td></td>').html(cacheMetrics['cacheGets']));
                tr.append($('<td></td>').html(cacheMetrics['cachePuts']));
                tr.append($('<td></td>').html(cacheMetrics['cacheRemovals']));
                tr.append($('<td></td>').html(cacheMetrics['averageGetTime']));
                tr.append($('<td></td>').html(cacheMetrics['averagePutTime']));
                tr.append($('<td></td>').html(cacheMetrics['averageRemoveTime']));
                tr.append($('<td></td>').html(cacheMetrics['size']));


                tr.append($('<td></td>').html(rebalanceBtn));


                rebalanceBtn.click(function () {
                    var btn = $(this);
                    var cacheName = btn.attr('data-cache-name');

                    btn.addClass("disabled");

                    rebalanceFunction(cacheName, function () {
                        $(btn).removeClass("disabled");
                    });

                });

                $(htmlContainer).append(tr);
            }

        })
    }, 5000);
}