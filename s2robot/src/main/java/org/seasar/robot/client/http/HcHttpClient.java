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
package org.seasar.robot.client.http;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.NoRouteToHostException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.DeferredFileOutputStream;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScheme;
import org.apache.http.auth.AuthSchemeFactory;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.seasar.extension.timer.TimeoutManager;
import org.seasar.extension.timer.TimeoutTask;
import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.container.annotation.tiger.Binding;
import org.seasar.framework.container.annotation.tiger.BindingType;
import org.seasar.framework.container.annotation.tiger.DestroyMethod;
import org.seasar.framework.util.StringUtil;
import org.seasar.robot.Constants;
import org.seasar.robot.MaxLengthExceededException;
import org.seasar.robot.RobotCrawlAccessException;
import org.seasar.robot.RobotSystemException;
import org.seasar.robot.S2RobotContext;
import org.seasar.robot.client.AbstractS2RobotClient;
import org.seasar.robot.entity.ResponseData;
import org.seasar.robot.entity.RobotsTxt;
import org.seasar.robot.helper.ContentLengthHelper;
import org.seasar.robot.helper.RobotsTxtHelper;
import org.seasar.robot.util.CrawlingParameterUtil;
import org.seasar.robot.util.StreamUtil;
import org.seasar.robot.util.TemporaryFileInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author shinsuke
 * 
 */
public class HcHttpClient extends AbstractS2RobotClient {

    public static final String CONNECTION_TIMEOUT_PROPERTY =
        "connectionTimeout";

    public static final String STALE_CHECKING_ENABLED_PROPERTY =
        "staleCheckingEnabled";

    public static final String SO_TIMEOUT_PROPERTY = "soTimeout";

    public static final String LINGER_PROPERTY = "linger";

    public static final String PROXY_HOST_PROPERTY = "proxyHost";

    public static final String PROXY_PORT_PROPERTY = "proxyPort";

    public static final String PROXY_AUTH_SCHEME_PROPERTY = "proxyAuthScheme";

    public static final String PROXY_CREDENTIALS_PROPERTY = "proxyCredentials";

    public static final String USER_AGENT_PROPERTY = "userAgent";

    public static final String BASIC_AUTHENTICATIONS_PROPERTY =
        "basicAuthentications";

    public static final String REQUERT_HEADERS_PROPERTY = "requestHeaders";

    public static final String COOKIES_PROPERTY = "cookies";

    public static final String AUTH_SCHEME_FACTORIES_PROPERTY =
        "authSchemeFactories";

    private static final Logger logger = LoggerFactory // NOPMD
        .getLogger(HcHttpClient.class);

    @Binding(bindingType = BindingType.MAY)
    @Resource
    protected RobotsTxtHelper robotsTxtHelper;

    @Binding(bindingType = BindingType.MAY)
    @Resource
    protected ContentLengthHelper contentLengthHelper;

    protected volatile DefaultHttpClient httpClient; // NOPMD

    private final List<Header> requestHeaderList = new ArrayList<Header>();

    private final Map<String, Object> httpClientPropertyMap =
        new HashMap<String, Object>();

    private TimeoutTask connectionMonitorTask;

    public Integer connectionTimeout;

    public Integer maxTotalConnections;

    public Integer maxConnectionsPerRoute;

    public Boolean staleCheckingEnabled;

    public Integer soTimeout;

    public Integer linger;

    public String cookiePolicy;

    public String userAgent = "S2Robot";

    @Binding(bindingType = BindingType.MAY)
    public HttpContext httpClientContext = new BasicHttpContext();

    public String proxyHost;

    public Integer proxyPort;

    @Binding(bindingType = BindingType.MAY)
    public AuthScheme proxyAuthScheme = new BasicScheme();

    @Binding(bindingType = BindingType.MAY)
    public Credentials proxyCredentials;

    public int responseBodyInMemoryThresholdSize = 1 * 1024 * 1024; // 1M

    public String defaultMimeType = "application/octet-stream";

    @Binding(bindingType = BindingType.MAY)
    public CookieStore cookieStore = new BasicCookieStore();

    public ClientConnectionManager clientConnectionManager;

    public Map<String, AuthSchemeFactory> authSchemeFactoryMap;

    public int connectionCheckInterval = 5; // sec

    public long idleConnectionTimeout = 60 * 1000; // 1min

    public Pattern redirectHttpStatusPattern = Pattern.compile("[3][0-9][0-9]");

    public synchronized void init() {
        if (httpClient != null) {
            return;
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Initializing " + HcHttpClient.class.getName());
        }

        final DefaultHttpClient defaultHttpClient =
            new DefaultHttpClient(clientConnectionManager);
        final HttpParams params = defaultHttpClient.getParams();

        final Integer connectionTimeoutParam =
            getInitParameter(CONNECTION_TIMEOUT_PROPERTY, connectionTimeout);
        if (connectionTimeoutParam != null) {
            HttpConnectionParams.setConnectionTimeout(
                params,
                connectionTimeoutParam);
        }
        final Boolean staleCheckingEnabledParam =
            getInitParameter(
                STALE_CHECKING_ENABLED_PROPERTY,
                staleCheckingEnabled);
        if (staleCheckingEnabledParam != null) {
            HttpConnectionParams.setStaleCheckingEnabled(
                params,
                staleCheckingEnabledParam);
        }
        final Integer soTimeoutParam =
            getInitParameter(SO_TIMEOUT_PROPERTY, soTimeout);
        if (soTimeoutParam != null) {
            HttpConnectionParams.setSoTimeout(params, soTimeoutParam);
        }
        final Integer lingerParam = getInitParameter(LINGER_PROPERTY, linger);
        if (lingerParam != null) {
            HttpConnectionParams.setLinger(params, lingerParam);
        }

        // AuthSchemeFactory
        final Map<String, AuthSchemeFactory> authSchemeFactoryMap =
            getInitParameter(
                AUTH_SCHEME_FACTORIES_PROPERTY,
                this.authSchemeFactoryMap);
        if (authSchemeFactoryMap != null) {
            for (final Map.Entry<String, AuthSchemeFactory> entry : authSchemeFactoryMap
                .entrySet()) {
                defaultHttpClient.getAuthSchemes().register(
                    entry.getKey(),
                    entry.getValue());
            }
        }

        final AuthCache authCache = new BasicAuthCache();
        boolean useAuthCache = false;

        // proxy
        final String proxyHost =
            getInitParameter(PROXY_HOST_PROPERTY, this.proxyHost);
        final Integer proxyPort =
            getInitParameter(PROXY_PORT_PROPERTY, this.proxyPort);
        if (proxyHost != null && proxyPort != null) {
            final HttpHost proxy = new HttpHost(proxyHost, proxyPort);
            params.setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);

            final Credentials proxyCredentials =
                getInitParameter(
                    PROXY_CREDENTIALS_PROPERTY,
                    this.proxyCredentials);
            if (proxyCredentials != null) {
                defaultHttpClient.getCredentialsProvider().setCredentials(
                    new AuthScope(proxyHost, proxyPort),
                    proxyCredentials);
                final AuthScheme proxyAuthScheme =
                    getInitParameter(
                        PROXY_AUTH_SCHEME_PROPERTY,
                        this.proxyAuthScheme);
                if (proxyAuthScheme != null) {
                    authCache.put(proxy, proxyAuthScheme);
                    useAuthCache = true;
                }
            }
        }

        // user agent
        final String userAgent =
            getInitParameter(USER_AGENT_PROPERTY, this.userAgent);
        if (StringUtil.isNotBlank(userAgent)) {
            HttpProtocolParams.setUserAgent(params, userAgent);
        }

        // Authentication
        final Authentication[] siteCredentialList =
            getInitParameter(
                BASIC_AUTHENTICATIONS_PROPERTY,
                new Authentication[0]);
        for (final Authentication authentication : siteCredentialList) {
            final AuthScope authScope = authentication.getAuthScope();
            defaultHttpClient.getCredentialsProvider().setCredentials(
                authScope,
                authentication.getCredentials());
            final AuthScheme authScheme = authentication.getAuthScheme();
            if (authScope.getHost() != null && authScheme != null) {
                final HttpHost targetHost =
                    new HttpHost(authScope.getHost(), authScope.getPort());
                authCache.put(targetHost, authScheme);
                useAuthCache = true;
            }
        }
        if (useAuthCache) {
            httpClientContext.setAttribute(ClientContext.AUTH_CACHE, authCache);
        }

        // Request Header
        final RequestHeader[] requestHeaders =
            getInitParameter(REQUERT_HEADERS_PROPERTY, new RequestHeader[0]);
        for (final RequestHeader requestHeader : requestHeaders) {
            if (requestHeader.isValid()) {
                requestHeaderList.add(new BasicHeader(
                    requestHeader.getName(),
                    requestHeader.getValue()));
            }
        }

        // do not redirect
        defaultHttpClient.getParams().setBooleanParameter(
            ClientPNames.HANDLE_REDIRECTS,
            false);

        // cookie store
        defaultHttpClient.setCookieStore(cookieStore);
        if (cookieStore != null) {
            final Cookie[] cookies =
                getInitParameter(COOKIES_PROPERTY, new Cookie[0]);
            for (final Cookie cookie : cookies) {
                cookieStore.addCookie(cookie);
            }
        }

        if (!httpClientPropertyMap.isEmpty()) {
            final BeanDesc beanDesc =
                BeanDescFactory.getBeanDesc(DefaultHttpClient.class);
            for (final Map.Entry<String, Object> entry : httpClientPropertyMap
                .entrySet()) {
                final String propertyName = entry.getKey();
                if (beanDesc.hasPropertyDesc(propertyName)) {
                    final PropertyDesc propertyDesc =
                        beanDesc.getPropertyDesc(propertyName);
                    propertyDesc.setValue(defaultHttpClient, entry.getValue());
                } else {
                    logger.warn("DefaultHttpClient does not have "
                        + propertyName + ".");
                }
            }
        }
        connectionMonitorTask =
            TimeoutManager.getInstance().addTimeoutTarget(
                new HcConnectionMonitorTarget(
                    clientConnectionManager,
                    idleConnectionTimeout),
                connectionCheckInterval,
                true);

        httpClient = defaultHttpClient;
    }

    @DestroyMethod
    public void destroy() {
        if (connectionMonitorTask != null) {
            connectionMonitorTask.cancel();
        }
        if (httpClient != null) {
            httpClient.getConnectionManager().shutdown();
        }
    }

    public void addHttpClientProperty(final String name, final Object value) {
        if (StringUtil.isNotBlank(name) && value != null) {
            httpClientPropertyMap.put(name, value);
        }
    }

    protected void processRobotsTxt(final String url) {
        if (StringUtil.isBlank(url)) {
            throw new RobotSystemException("url is null or empty.");
        }

        if (robotsTxtHelper == null) {
            // not support robots.txt
            return;
        }

        // robot context
        final S2RobotContext robotContext =
            CrawlingParameterUtil.getRobotContext();
        if (robotContext == null) {
            // wrong state
            return;
        }

        final int idx = url.indexOf('/', url.indexOf("://") + 3);
        String hostUrl;
        if (idx >= 0) {
            hostUrl = url.substring(0, idx);
        } else {
            hostUrl = url;
        }
        final String robotTxtUrl = hostUrl + "/robots.txt";

        // check url
        if (robotContext.getRobotTxtUrlSet().contains(robotTxtUrl)) {
            if (logger.isDebugEnabled()) {
                logger.debug(robotTxtUrl + " is already visited.");
            }
            return;
        }

        if (logger.isInfoEnabled()) {
            logger.info("Checking URL: " + robotTxtUrl);
        }
        // add url to a set
        robotContext.getRobotTxtUrlSet().add(robotTxtUrl);

        final HttpGet httpGet = new HttpGet(robotTxtUrl);

        // cookie
        if (cookiePolicy != null) {
            httpGet.getParams().setParameter(
                ClientPNames.COOKIE_POLICY,
                cookiePolicy);
        }

        // request header
        for (final Header header : requestHeaderList) {
            httpGet.addHeader(header);
        }

        HttpEntity httpEntity = null;
        try {
            // get a content
            final HttpResponse response = executeHttpClient(httpGet);
            httpEntity = response.getEntity();

            final int httpStatusCode = response.getStatusLine().getStatusCode();
            if (httpStatusCode == 200) {

                // check file size
                final Header contentLengthHeader =
                    response.getFirstHeader("Content-Length");
                if (contentLengthHeader != null) {
                    final String value = contentLengthHeader.getValue();
                    final long contentLength = Long.parseLong(value);
                    if (contentLengthHelper != null) {
                        final long maxLength =
                            contentLengthHelper.getMaxLength("text/plain");
                        if (contentLength > maxLength) {
                            throw new MaxLengthExceededException(
                                "The content length (" + contentLength
                                    + " byte) is over " + maxLength
                                    + " byte. The url is " + robotTxtUrl);
                        }
                    }
                }

                if (httpEntity != null) {
                    final RobotsTxt robotsTxt =
                        robotsTxtHelper.parse(httpEntity.getContent());
                    if (robotsTxt != null) {
                        final RobotsTxt.Directives directives =
                            robotsTxt.getDirectives(userAgent);
                        if (directives != null) {
                            for (String urlPattern : directives.getDisallows()) {
                                if (StringUtil.isNotBlank(urlPattern)) {
                                    urlPattern =
                                        convertRobotsTxtPathPattern(urlPattern);
                                    robotContext.getUrlFilter().addExclude(
                                        hostUrl + urlPattern);
                                }
                            }
                        }
                    }
                }
            }
        } catch (final RobotSystemException e) {
            httpGet.abort();
            throw e;
        } catch (final Exception e) {
            httpGet.abort();
            throw new RobotCrawlAccessException("Could not process "
                + robotTxtUrl + ". ", e);
        } finally {
            EntityUtils.consumeQuietly(httpEntity);
        }
    }

    protected String convertRobotsTxtPathPattern(final String path) {
        String newPath =
            path.replaceAll("\\.", "\\\\.").replaceAll("\\*", ".*");
        if (newPath.charAt(0) != '/') {
            newPath = ".*" + newPath;
        }
        if (!newPath.endsWith("$") && !newPath.endsWith(".*")) {
            newPath = newPath + ".*";
        }
        return newPath.replaceAll("\\.\\*\\.\\*", ".*");
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.seasar.robot.http.HttpClient#doGet(java.lang.String)
     */
    @Override
    public ResponseData doGet(final String url) {
        HttpUriRequest httpGet;
        try {
            httpGet = new HttpGet(url);
        } catch (final IllegalArgumentException e) {
            throw new RobotCrawlAccessException("The url may not be valid: "
                + url, e);
        }
        return doHttpMethod(url, httpGet);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.seasar.robot.http.HttpClient#doHead(java.lang.String)
     */
    @Override
    public ResponseData doHead(final String url) {
        HttpUriRequest httpHead;
        try {
            httpHead = new HttpHead(url);
        } catch (final IllegalArgumentException e) {
            throw new RobotCrawlAccessException("The url may not be valid: "
                + url, e);
        }
        return doHttpMethod(url, httpHead);
    }

    public ResponseData doHttpMethod(final String url,
            final HttpUriRequest httpRequest) {
        if (httpClient == null) {
            init();
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Accessing " + url);
        }

        try {
            processRobotsTxt(url);
        } catch (final RobotCrawlAccessException e) {
            if (logger.isInfoEnabled()) {
                final StringBuilder buf = new StringBuilder();
                buf.append(e.getMessage());
                if (e.getCause() != null) {
                    buf.append(e.getCause().getMessage());
                }
                logger.info(buf.toString());
            } else if (logger.isDebugEnabled()) {
                logger.debug("Crawling Access Exception at " + url, e);
            }
        }

        // cookie
        if (cookiePolicy != null) {
            httpRequest.getParams().setParameter(
                ClientPNames.COOKIE_POLICY,
                cookiePolicy);
        }

        // request header
        for (final Header header : requestHeaderList) {
            httpRequest.addHeader(header);
        }

        ResponseData responseData = null;
        InputStream inputStream = null;
        HttpEntity httpEntity = null;
        try {
            // get a content
            final HttpResponse response = executeHttpClient(httpRequest);
            httpEntity = response.getEntity();

            final int httpStatusCode = response.getStatusLine().getStatusCode();
            // redirect
            if (isRedirectHttpStatus(httpStatusCode)) {
                final Header locationHeader =
                    response.getFirstHeader("location");
                if (locationHeader == null) {
                    logger.warn("Invalid redirect location at " + url);
                } else {
                    responseData = new ResponseData();
                    responseData.setRedirectLocation(locationHeader.getValue());
                    return responseData;
                }
            }

            long contentLength = 0;
            String contentEncoding = Constants.UTF_8;
            if (httpEntity == null) {
                inputStream = new ByteArrayInputStream(new byte[0]);
            } else {
                final InputStream responseBodyStream = httpEntity.getContent();
                final File outputFile =
                    File.createTempFile("s2robot-HcHttpClient-", ".out");
                DeferredFileOutputStream dfos = null;
                try {
                    try {
                        dfos =
                            new DeferredFileOutputStream(
                                responseBodyInMemoryThresholdSize,
                                outputFile);
                        StreamUtil.drain(responseBodyStream, dfos);
                        dfos.flush();
                    } finally {
                        IOUtils.closeQuietly(dfos);
                    }
                } catch (final Exception e) {
                    if (!outputFile.delete()) {
                        logger.warn("Could not delete "
                            + outputFile.getAbsolutePath());
                    }
                    throw e;
                }

                if (dfos.isInMemory()) {
                    inputStream = new ByteArrayInputStream(dfos.getData());
                    contentLength = dfos.getData().length;
                    if (!outputFile.delete()) {
                        logger.warn("Could not delete "
                            + outputFile.getAbsolutePath());
                    }
                } else {
                    inputStream = new TemporaryFileInputStream(outputFile);
                    contentLength = outputFile.length();
                }

                final Header contentEncodingHeader =
                    httpEntity.getContentEncoding();
                if (contentEncodingHeader != null) {
                    contentEncoding = contentEncodingHeader.getValue();
                }
            }

            String contentType = null;
            final Header contentTypeHeader =
                response.getFirstHeader("Content-Type");
            if (contentTypeHeader != null) {
                contentType = contentTypeHeader.getValue();
                final int idx = contentType.indexOf(';');
                if (idx > 0) {
                    contentType = contentType.substring(0, idx);
                }
            }

            // check file size
            if (contentLengthHelper != null) {
                final long maxLength =
                    contentLengthHelper.getMaxLength(contentType);
                if (contentLength > maxLength) {
                    throw new MaxLengthExceededException("The content length ("
                        + contentLength + " byte) is over " + maxLength
                        + " byte. The url is " + url);
                }
            }

            responseData = new ResponseData();
            responseData.setUrl(url);
            responseData.setCharSet(contentEncoding);
            if (httpRequest instanceof HttpHead) {
                responseData.setMethod(Constants.HEAD_METHOD);
            } else {
                responseData.setMethod(Constants.GET_METHOD);
            }
            responseData.setResponseBody(inputStream);
            responseData.setHttpStatusCode(httpStatusCode);
            for (final Header header : response.getAllHeaders()) {
                responseData.addMetaData(header.getName(), header.getValue());
            }
            if (contentType == null) {
                responseData.setMimeType(defaultMimeType);
            } else {
                responseData.setMimeType(contentType);
            }
            final Header contentLengthHeader =
                response.getFirstHeader("Content-Length");
            if (contentLengthHeader == null) {
                responseData.setContentLength(contentLength);
            } else {
                final String value = contentLengthHeader.getValue();
                try {
                    responseData.setContentLength(Long.parseLong(value));
                } catch (final Exception e) {
                    responseData.setContentLength(contentLength);
                }
            }
            final Header lastModifiedHeader =
                response.getFirstHeader("Last-Modified");
            if (lastModifiedHeader != null) {
                final String value = lastModifiedHeader.getValue();
                if (StringUtil.isNotBlank(value)) {
                    final Date d = parseLastModified(value);
                    if (d != null) {
                        responseData.setLastModified(d);
                    }
                }
            }
            if (responseData.getLastModified() == null) {
                responseData.setLastModified(new Date()); // set current time
            }

            return responseData;
        } catch (final UnknownHostException e) {
            httpRequest.abort();
            IOUtils.closeQuietly(inputStream);
            throw new RobotCrawlAccessException("Unknown host("
                + e.getMessage() + "): " + url, e);
        } catch (final NoRouteToHostException e) {
            httpRequest.abort();
            IOUtils.closeQuietly(inputStream);
            throw new RobotCrawlAccessException("No route to host("
                + e.getMessage() + "): " + url, e);
        } catch (final ConnectException e) {
            httpRequest.abort();
            IOUtils.closeQuietly(inputStream);
            throw new RobotCrawlAccessException("Connection time out("
                + e.getMessage() + "): " + url, e);
        } catch (final SocketException e) {
            httpRequest.abort();
            IOUtils.closeQuietly(inputStream);
            throw new RobotCrawlAccessException("Socket exception("
                + e.getMessage() + "): " + url, e);
        } catch (final IOException e) {
            httpRequest.abort();
            IOUtils.closeQuietly(inputStream);
            throw new RobotCrawlAccessException("I/O exception("
                + e.getMessage() + "): " + url, e);
        } catch (final RobotSystemException e) {
            httpRequest.abort();
            IOUtils.closeQuietly(inputStream);
            throw e;
        } catch (final Exception e) {
            httpRequest.abort();
            IOUtils.closeQuietly(inputStream);
            throw new RobotSystemException("Failed to access " + url, e);
        } finally {
            EntityUtils.consumeQuietly(httpEntity);
        }
    }

    protected boolean isRedirectHttpStatus(final int httpStatusCode) {
        return redirectHttpStatusPattern.matcher(
            Integer.toString(httpStatusCode)).matches();
    }

    private HttpResponse executeHttpClient(final HttpUriRequest httpRequest)
            throws IOException, ClientProtocolException {
        return httpClient.execute(httpRequest, new BasicHttpContext(
            httpClientContext));
    }

    protected Date parseLastModified(final String value) {
        final SimpleDateFormat sdf =
            new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z", Locale.ENGLISH);
        try {
            return sdf.parse(value);
        } catch (final ParseException e) {
            return null;
        }
    }

}
