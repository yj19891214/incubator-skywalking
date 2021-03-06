/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.apache.skywalking.apm.collector.analysis.metric.provider.worker.service.metric;

import org.apache.skywalking.apm.collector.analysis.metric.define.graph.MetricWorkerIdDefine;
import org.apache.skywalking.apm.collector.core.graph.Next;
import org.apache.skywalking.apm.collector.core.graph.NodeProcessor;
import org.apache.skywalking.apm.collector.core.util.Const;
import org.apache.skywalking.apm.collector.core.util.TimeBucketUtils;
import org.apache.skywalking.apm.collector.storage.table.service.ServiceMetric;

/**
 * @author peng-yongsheng
 */
public class ServiceHourMetricTransformNode implements NodeProcessor<ServiceMetric, ServiceMetric> {

    @Override public int id() {
        return MetricWorkerIdDefine.SERVICE_HOUR_METRIC_TRANSFORM_NODE_ID;
    }

    @Override public void process(ServiceMetric serviceMetric, Next<ServiceMetric> next) {
        long timeBucket = TimeBucketUtils.INSTANCE.minuteToHour(serviceMetric.getTimeBucket());

        ServiceMetric newServiceMetric = ServiceMetricCopy.copy(serviceMetric);
        newServiceMetric.setId(String.valueOf(timeBucket) + Const.ID_SPLIT + serviceMetric.getMetricId());
        newServiceMetric.setTimeBucket(timeBucket);
        next.execute(newServiceMetric);
    }
}
