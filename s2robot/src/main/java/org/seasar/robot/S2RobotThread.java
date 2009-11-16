/*
 * Copyright 2004-2009 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.seasar.robot;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.io.IOUtils;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.factory.SingletonS2ContainerFactory;
import org.seasar.framework.util.StringUtil;
import org.seasar.robot.client.S2RobotClient;
import org.seasar.robot.client.S2RobotClientFactory;
import org.seasar.robot.client.fs.ChildUrlsException;
import org.seasar.robot.entity.ResponseData;
import org.seasar.robot.entity.UrlQueue;
import org.seasar.robot.helper.LogHelper;
import org.seasar.robot.interval.IntervalController;
import org.seasar.robot.log.LogType;
import org.seasar.robot.processor.ResponseProcessor;
import org.seasar.robot.rule.Rule;
import org.seasar.robot.service.DataService;
import org.seasar.robot.service.UrlQueueService;
import org.seasar.robot.util.CrawlingParameterUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author shinsuke
 *
 */
public class S2RobotThread implements Runnable {
    private static final Logger logger = LoggerFactory
            .getLogger(S2RobotThread.class);

    @Resource
    protected UrlQueueService urlQueueService;

    @Resource
    protected DataService dataService;

    @Resource
    protected S2Container container;

    protected S2RobotClientFactory clientFactory;

    protected S2RobotContext robotContext;

    protected void startCrawling() {
        synchronized (robotContext.activeThreadCountLock) {
            robotContext.activeThreadCount++;
        }
    }

    protected void finishCrawling() {
        synchronized (robotContext.activeThreadCountLock) {
            robotContext.activeThreadCount--;
        }
    }

    protected boolean isContinue(int tcCount) {
        if (!SingletonS2ContainerFactory.hasContainer()) {
            // system shutdown
            return false;
        }

        boolean isContinue = false;
        if (tcCount < robotContext.maxThreadCheckCount) {
            if (robotContext.maxAccessCount > 0) {
                if (logger.isDebugEnabled()) {
                    logger.debug("Access Count: " + robotContext.accessCount);
                }
                if (robotContext.accessCount >= robotContext.maxAccessCount) {
                    return false;
                }
            }
            isContinue = true;
        }

        if (!isContinue && robotContext.activeThreadCount > 0) {
            // still running..
            return true;
        }

        return isContinue;
    }

    protected void logType(LogHelper logHelper, LogType key, Object... objs) {
        if (logHelper != null) {
            logHelper.logType(key, objs);
        }
    }

    private LogHelper getLogHelper() {
        if (container.hasComponentDef(LogHelper.class)) {
            return (LogHelper) container.getComponent(LogHelper.class);
        }
        return null;
    }

    /* (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    public void run() {
        int threadCheckCount = 0;
        // set urlQueue to thread
        CrawlingParameterUtil.setRobotContext(robotContext);
        CrawlingParameterUtil.setUrlQueueService(urlQueueService);
        CrawlingParameterUtil.setDataService(dataService);
        LogHelper logHelper = getLogHelper();
        try {
            while (robotContext.running && isContinue(threadCheckCount)) {
                UrlQueue urlQueue = urlQueueService
                        .poll(robotContext.sessionId);
                if (isValid(urlQueue)) {
                    ResponseData responseData = null;
                    logType(logHelper, LogType.START_CRAWLING, robotContext, urlQueue);
                    try {
                        S2RobotClient client = getClient(urlQueue.getUrl());
                        if (client == null) {
                            logType(logHelper,
                                    LogType.UNSUPPORTED_URL_AT_CRAWLING_STARTED,
                                    robotContext, urlQueue);
                            break;
                        }

                        startCrawling();

                        // set urlQueue to thread
                        CrawlingParameterUtil.setUrlQueue(urlQueue);

                        if (robotContext.intervalController != null) {
                            robotContext.intervalController
                                    .delay(IntervalController.PRE_PROCESSING);
                        }

                        boolean contentUpdated = true;
                        if (urlQueue.getLastModified() != null) {
                            logType(logHelper, LogType.CHECK_LAST_MODIFIED,
                                    robotContext, urlQueue);
                            long startTime = System.currentTimeMillis();
                            //  head method
                            responseData = client.doHead(urlQueue.getUrl());
                            if (responseData != null
                                    && responseData.getLastModified().getTime() <= urlQueue
                                            .getLastModified().getTime()
                                    && responseData.getHttpStatusCode() == 200) {
                                logType(logHelper, LogType.NOT_MODIFIED, robotContext,
                                        urlQueue);

                                responseData.setExecutionTime(System
                                        .currentTimeMillis()
                                        - startTime);
                                responseData.setParentUrl(urlQueue
                                        .getParentUrl());
                                responseData
                                        .setSessionId(robotContext.sessionId);
                                responseData
                                        .setStatus(Constants.NOT_UPDATED_STATUS);
                                processResponse(urlQueue, responseData);

                                contentUpdated = false;
                            }
                        }

                        if (contentUpdated) {
                            logType(logHelper, LogType.GET_CONTENT, robotContext,
                                    urlQueue);
                            // access an url
                            long startTime = System.currentTimeMillis();
                            responseData = client.doGet(urlQueue.getUrl());
                            responseData.setExecutionTime(System
                                    .currentTimeMillis()
                                    - startTime);
                            responseData.setParentUrl(urlQueue.getParentUrl());
                            responseData.setSessionId(robotContext.sessionId);

                            if (responseData.getRedirectLocation() != null) {
                                logType(logHelper, LogType.REDIRECT_LOCATION,
                                        robotContext, urlQueue, responseData);
                                // redirect
                                synchronized (robotContext.accessCountLock) {
                                    //  add an url
                                    storeChildUrl(
                                            responseData.getRedirectLocation(),
                                            urlQueue.getUrl(),
                                            urlQueue.getDepth() != null ? urlQueue
                                                    .getDepth() + 1
                                                    : 1);
                                }
                            } else {
                                logType(logHelper, LogType.PROCESS_RESPONSE,
                                        robotContext, urlQueue, responseData);
                                processResponse(urlQueue, responseData);
                            }
                        }

                        logType(logHelper, LogType.FINISHED_CRAWLING, robotContext,
                                urlQueue);
                    } catch (ChildUrlsException e) {
                        synchronized (robotContext.accessCountLock) {
                            //  add an url
                            storeChildUrls(e.getChildUrlList(), urlQueue
                                    .getUrl(),
                                    urlQueue.getDepth() != null ? urlQueue
                                            .getDepth() + 1 : 1);
                        }
                    } catch (RobotCrawlAccessException e) {
                        if (e.isDebugEnabled()) {
                            logger.debug("Crawling Access Exception at "
                                    + urlQueue.getUrl(), e);
                        } else if (e.isInfoEnabled()) {
                            logger.info(e.getMessage());
                        } else if (e.isWarnEnabled()) {
                            logger.warn("Crawling Access Exception at "
                                    + urlQueue.getUrl(), e);
                        } else if (e.isErrorEnabled()) {
                            logger.error("Crawling Access Exception at "
                                    + urlQueue.getUrl(), e);
                        }
                    } catch (Exception e) {
                        logger.error("Crawling Exception at "
                                + urlQueue.getUrl(), e);
                    } finally {
                        if (responseData != null) {
                            IOUtils
                                    .closeQuietly(responseData
                                            .getResponseBody());
                        }
                        if (robotContext.intervalController != null) {
                            try {
                                robotContext.intervalController
                                        .delay(IntervalController.POST_PROCESSING);
                            } catch (Exception e) {
                                logger.warn("Could not sleep a thread: "
                                        + Thread.currentThread().getName(), e);
                            }
                        }
                        threadCheckCount = 0; // clear
                        // remove urlQueue from thread
                        CrawlingParameterUtil.setUrlQueue(null);
                        finishCrawling();
                    }
                } else {
                    if (logger.isDebugEnabled()) {
                        logger.debug("No url in a queue. (" + threadCheckCount
                                + ")");
                    }

                    if (robotContext.intervalController != null) {
                        try {
                            robotContext.intervalController
                                    .delay(IntervalController.NO_URL_IN_QUEUE);
                        } catch (Exception e) {
                            logger.warn("Could not sleep a thread: "
                                    + Thread.currentThread().getName(), e);
                        }
                    }

                    threadCheckCount++;
                }

                // interval
                if (robotContext.intervalController != null) {
                    try {
                        robotContext.intervalController
                                .delay(IntervalController.WAIT_NEW_URL);
                    } catch (Exception e) {
                        logger.warn("Could not sleep a thread: "
                                + Thread.currentThread().getName(), e);
                    }
                }
            }
        } finally {
            // remove robotContext from thread
            CrawlingParameterUtil.setRobotContext(null);
            CrawlingParameterUtil.setUrlQueueService(null);
            CrawlingParameterUtil.setDataService(null);
        }
    }

    protected S2RobotClient getClient(String url) {
        return clientFactory.getClient(url);
    }

    protected void processResponse(UrlQueue urlQueue, ResponseData responseData) {
        // get a rule
        Rule rule = robotContext.ruleManager.getRule(responseData);
        if (rule != null) {
            responseData.setRuleId(rule.getRuleId());
            ResponseProcessor responseProcessor = rule.getResponseProcessor();
            if (responseProcessor != null) {
                responseProcessor.process(responseData);
            } else {
                if (logger.isDebugEnabled()) {
                    logger.debug("No ResponseProcessor for ("
                            + responseData.getUrl() + ", "
                            + responseData.getMimeType()
                            + "). PLEASE CHECK YOUR CONFIGURATION.");
                }
            }
        } else {
            if (logger.isDebugEnabled()) {
                logger.debug("No rule for (" + responseData.getUrl() + ", "
                        + responseData.getMimeType()
                        + "). PLEASE CHECK YOUR CONFIGURATION.");
            }
        }

    }

    private void storeChildUrls(Set<String> childUrlList, String url, int depth) {
        //  add url and filter 
        List<UrlQueue> childList = new ArrayList<UrlQueue>();
        for (String childUrl : childUrlList) {
            if (robotContext.urlFilter.match(childUrl)) {
                UrlQueue uq = (UrlQueue) container.getComponent(UrlQueue.class);
                uq.setCreateTime(new Timestamp(new Date().getTime()));
                uq.setDepth(depth);
                uq.setMethod(Constants.GET_METHOD);
                uq.setParentUrl(url);
                uq.setSessionId(robotContext.sessionId);
                uq.setUrl(childUrl);
                childList.add(uq);
            }
        }
        urlQueueService.offerAll(robotContext.sessionId, childList);
    }

    private void storeChildUrl(String childUrl, String url, int depth) {
        //  add url and filter 
        if (robotContext.urlFilter.match(childUrl)) {
            List<UrlQueue> childList = new ArrayList<UrlQueue>(1);
            UrlQueue uq = (UrlQueue) container.getComponent(UrlQueue.class);
            uq.setCreateTime(new Timestamp(new Date().getTime()));
            uq.setDepth(depth);
            uq.setMethod(Constants.GET_METHOD);
            uq.setParentUrl(url);
            uq.setSessionId(robotContext.sessionId);
            uq.setUrl(childUrl);
            childList.add(uq);
            urlQueueService.offerAll(robotContext.sessionId, childList);
        }
    }

    protected boolean isValid(UrlQueue urlQueue) {
        if (urlQueue == null) {
            return false;
        }

        if (StringUtil.isBlank(urlQueue.getUrl())) {
            return false;
        }

        if (robotContext.getMaxDepth() >= 0
                && urlQueue.getDepth() > robotContext.getMaxDepth()) {
            return false;
        }

        //  url filter
        if (robotContext.urlFilter.match(urlQueue.getUrl())) {
            return true;
        }

        return false;
    }
}
