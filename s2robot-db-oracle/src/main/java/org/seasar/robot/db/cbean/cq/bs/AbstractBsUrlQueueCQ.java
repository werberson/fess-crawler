/*
 * Copyright 2004-2011 the Seasar Foundation and the Others.
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
package org.seasar.robot.db.cbean.cq.bs;

import java.util.Collection;

import org.seasar.robot.db.allcommon.DBMetaInstanceHandler;
import org.seasar.robot.db.cbean.UrlQueueCB;
import org.seasar.robot.db.cbean.cq.UrlQueueCQ;
import org.seasar.robot.dbflute.cbean.AbstractConditionQuery;
import org.seasar.robot.dbflute.cbean.ConditionQuery;
import org.seasar.robot.dbflute.cbean.SubQuery;
import org.seasar.robot.dbflute.cbean.chelper.HpSSQFunction;
import org.seasar.robot.dbflute.cbean.chelper.HpSSQSetupper;
import org.seasar.robot.dbflute.cbean.ckey.ConditionKey;
import org.seasar.robot.dbflute.cbean.coption.DateFromToOption;
import org.seasar.robot.dbflute.cbean.coption.FromToOption;
import org.seasar.robot.dbflute.cbean.coption.LikeSearchOption;
import org.seasar.robot.dbflute.cbean.cvalue.ConditionValue;
import org.seasar.robot.dbflute.cbean.sqlclause.SqlClause;
import org.seasar.robot.dbflute.dbmeta.DBMetaProvider;

/**
 * The abstract condition-query of URL_QUEUE.
 * 
 * @author DBFlute(AutoGenerator)
 */
public abstract class AbstractBsUrlQueueCQ extends AbstractConditionQuery {

    // ===================================================================================
    // Constructor
    // ===========
    public AbstractBsUrlQueueCQ(final ConditionQuery childQuery, final SqlClause sqlClause,
            final String aliasName, final int nestLevel) {
        super(childQuery, sqlClause, aliasName, nestLevel);
    }

    // ===================================================================================
    // DBMeta Provider
    // ===============
    @Override
    protected DBMetaProvider xgetDBMetaProvider() {
        return DBMetaInstanceHandler.getProvider();
    }

    // ===================================================================================
    // Table Name
    // ==========
    public String getTableDbName() {
        return "URL_QUEUE";
    }

    // ===================================================================================
    // Query
    // =====

    /**
     * Equal(=). And NullIgnored, OnlyOnceRegistered. <br />
     * ID: {PK, NotNull, NUMBER(19)}
     * 
     * @param id
     *            The value of id as equal.
     */
    public void setId_Equal(final Long id) {
        doSetId_Equal(id);
    }

    protected void doSetId_Equal(final Long id) {
        regId(CK_EQ, id);
    }

    /**
     * NotEqual(&lt;&gt;). And NullIgnored, OnlyOnceRegistered.
     * 
     * @param id
     *            The value of id as notEqual.
     */
    public void setId_NotEqual(final Long id) {
        doSetId_NotEqual(id);
    }

    protected void doSetId_NotEqual(final Long id) {
        regId(CK_NES, id);
    }

    /**
     * GreaterThan(&gt;). And NullIgnored, OnlyOnceRegistered.
     * 
     * @param id
     *            The value of id as greaterThan.
     */
    public void setId_GreaterThan(final Long id) {
        regId(CK_GT, id);
    }

    /**
     * LessThan(&lt;). And NullIgnored, OnlyOnceRegistered.
     * 
     * @param id
     *            The value of id as lessThan.
     */
    public void setId_LessThan(final Long id) {
        regId(CK_LT, id);
    }

    /**
     * GreaterEqual(&gt;=). And NullIgnored, OnlyOnceRegistered.
     * 
     * @param id
     *            The value of id as greaterEqual.
     */
    public void setId_GreaterEqual(final Long id) {
        regId(CK_GE, id);
    }

    /**
     * LessEqual(&lt;=). And NullIgnored, OnlyOnceRegistered.
     * 
     * @param id
     *            The value of id as lessEqual.
     */
    public void setId_LessEqual(final Long id) {
        regId(CK_LE, id);
    }

    /**
     * InScope(in (1, 2)). And NullIgnored, NullElementIgnored,
     * SeveralRegistered.
     * 
     * @param idList
     *            The collection of id as inScope.
     */
    public void setId_InScope(final Collection<Long> idList) {
        doSetId_InScope(idList);
    }

    protected void doSetId_InScope(final Collection<Long> idList) {
        regINS(CK_INS, cTL(idList), getCValueId(), "ID");
    }

    /**
     * NotInScope(not in (1, 2)). And NullIgnored, NullElementIgnored,
     * SeveralRegistered.
     * 
     * @param idList
     *            The collection of id as notInScope.
     */
    public void setId_NotInScope(final Collection<Long> idList) {
        doSetId_NotInScope(idList);
    }

    protected void doSetId_NotInScope(final Collection<Long> idList) {
        regINS(CK_NINS, cTL(idList), getCValueId(), "ID");
    }

    /**
     * IsNull(is null). And OnlyOnceRegistered.
     */
    public void setId_IsNull() {
        regId(CK_ISN, DOBJ);
    }

    /**
     * IsNotNull(is not null). And OnlyOnceRegistered.
     */
    public void setId_IsNotNull() {
        regId(CK_ISNN, DOBJ);
    }

    protected void regId(final ConditionKey k, final Object v) {
        regQ(k, v, getCValueId(), "ID");
    }

    abstract protected ConditionValue getCValueId();

    /**
     * Equal(=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br />
     * SESSION_ID: {IX, NotNull, VARCHAR2(20)}
     * 
     * @param sessionId
     *            The value of sessionId as equal.
     */
    public void setSessionId_Equal(final String sessionId) {
        doSetSessionId_Equal(fRES(sessionId));
    }

    protected void doSetSessionId_Equal(final String sessionId) {
        regSessionId(CK_EQ, sessionId);
    }

    /**
     * NotEqual(&lt;&gt;). And NullOrEmptyIgnored, OnlyOnceRegistered.
     * 
     * @param sessionId
     *            The value of sessionId as notEqual.
     */
    public void setSessionId_NotEqual(final String sessionId) {
        doSetSessionId_NotEqual(fRES(sessionId));
    }

    protected void doSetSessionId_NotEqual(final String sessionId) {
        regSessionId(CK_NES, sessionId);
    }

    /**
     * GreaterThan(&gt;). And NullOrEmptyIgnored, OnlyOnceRegistered.
     * 
     * @param sessionId
     *            The value of sessionId as greaterThan.
     */
    public void setSessionId_GreaterThan(final String sessionId) {
        regSessionId(CK_GT, fRES(sessionId));
    }

    /**
     * LessThan(&lt;). And NullOrEmptyIgnored, OnlyOnceRegistered.
     * 
     * @param sessionId
     *            The value of sessionId as lessThan.
     */
    public void setSessionId_LessThan(final String sessionId) {
        regSessionId(CK_LT, fRES(sessionId));
    }

    /**
     * GreaterEqual(&gt;=). And NullOrEmptyIgnored, OnlyOnceRegistered.
     * 
     * @param sessionId
     *            The value of sessionId as greaterEqual.
     */
    public void setSessionId_GreaterEqual(final String sessionId) {
        regSessionId(CK_GE, fRES(sessionId));
    }

    /**
     * LessEqual(&lt;=). And NullOrEmptyIgnored, OnlyOnceRegistered.
     * 
     * @param sessionId
     *            The value of sessionId as lessEqual.
     */
    public void setSessionId_LessEqual(final String sessionId) {
        regSessionId(CK_LE, fRES(sessionId));
    }

    /**
     * InScope(in ('a', 'b')). And NullOrEmptyIgnored,
     * NullOrEmptyElementIgnored, SeveralRegistered.
     * 
     * @param sessionIdList
     *            The collection of sessionId as inScope.
     */
    public void setSessionId_InScope(final Collection<String> sessionIdList) {
        doSetSessionId_InScope(sessionIdList);
    }

    public void doSetSessionId_InScope(final Collection<String> sessionIdList) {
        regINS(CK_INS, cTL(sessionIdList), getCValueSessionId(), "SESSION_ID");
    }

    /**
     * NotInScope(not in ('a', 'b')). And NullOrEmptyIgnored,
     * NullOrEmptyElementIgnored, SeveralRegistered.
     * 
     * @param sessionIdList
     *            The collection of sessionId as notInScope.
     */
    public void setSessionId_NotInScope(final Collection<String> sessionIdList) {
        doSetSessionId_NotInScope(sessionIdList);
    }

    public void doSetSessionId_NotInScope(final Collection<String> sessionIdList) {
        regINS(CK_NINS, cTL(sessionIdList), getCValueSessionId(), "SESSION_ID");
    }

    /**
     * PrefixSearch(like 'xxx%' escape ...). And NullOrEmptyIgnored,
     * SeveralRegistered.
     * 
     * @param sessionId
     *            The value of sessionId as prefixSearch.
     */
    public void setSessionId_PrefixSearch(final String sessionId) {
        setSessionId_LikeSearch(sessionId, cLSOP());
    }

    /**
     * LikeSearch with various options. (versatile) {like '%xxx%' escape ...} <br />
     * And NullOrEmptyIgnored, SeveralRegistered.
     * 
     * @param sessionId
     *            The value of sessionId as likeSearch.
     * @param likeSearchOption
     *            The option of like-search. (NotNull)
     */
    public void setSessionId_LikeSearch(final String sessionId,
            final LikeSearchOption likeSearchOption) {
        regLSQ(
            CK_LS,
            fRES(sessionId),
            getCValueSessionId(),
            "SESSION_ID",
            likeSearchOption);
    }

    /**
     * NotLikeSearch with various options. (versatile) {not like 'xxx%' escape
     * ...} <br />
     * And NullOrEmptyIgnored, SeveralRegistered.
     * 
     * @param sessionId
     *            The value of sessionId as notLikeSearch.
     * @param likeSearchOption
     *            The option of not-like-search. (NotNull)
     */
    public void setSessionId_NotLikeSearch(final String sessionId,
            final LikeSearchOption likeSearchOption) {
        regLSQ(
            CK_NLS,
            fRES(sessionId),
            getCValueSessionId(),
            "SESSION_ID",
            likeSearchOption);
    }

    protected void regSessionId(final ConditionKey k, final Object v) {
        regQ(k, v, getCValueSessionId(), "SESSION_ID");
    }

    abstract protected ConditionValue getCValueSessionId();

    /**
     * Equal(=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br />
     * METHOD: {NotNull, VARCHAR2(10)}
     * 
     * @param method
     *            The value of method as equal.
     */
    public void setMethod_Equal(final String method) {
        doSetMethod_Equal(fRES(method));
    }

    protected void doSetMethod_Equal(final String method) {
        regMethod(CK_EQ, method);
    }

    /**
     * NotEqual(&lt;&gt;). And NullOrEmptyIgnored, OnlyOnceRegistered.
     * 
     * @param method
     *            The value of method as notEqual.
     */
    public void setMethod_NotEqual(final String method) {
        doSetMethod_NotEqual(fRES(method));
    }

    protected void doSetMethod_NotEqual(final String method) {
        regMethod(CK_NES, method);
    }

    /**
     * GreaterThan(&gt;). And NullOrEmptyIgnored, OnlyOnceRegistered.
     * 
     * @param method
     *            The value of method as greaterThan.
     */
    public void setMethod_GreaterThan(final String method) {
        regMethod(CK_GT, fRES(method));
    }

    /**
     * LessThan(&lt;). And NullOrEmptyIgnored, OnlyOnceRegistered.
     * 
     * @param method
     *            The value of method as lessThan.
     */
    public void setMethod_LessThan(final String method) {
        regMethod(CK_LT, fRES(method));
    }

    /**
     * GreaterEqual(&gt;=). And NullOrEmptyIgnored, OnlyOnceRegistered.
     * 
     * @param method
     *            The value of method as greaterEqual.
     */
    public void setMethod_GreaterEqual(final String method) {
        regMethod(CK_GE, fRES(method));
    }

    /**
     * LessEqual(&lt;=). And NullOrEmptyIgnored, OnlyOnceRegistered.
     * 
     * @param method
     *            The value of method as lessEqual.
     */
    public void setMethod_LessEqual(final String method) {
        regMethod(CK_LE, fRES(method));
    }

    /**
     * InScope(in ('a', 'b')). And NullOrEmptyIgnored,
     * NullOrEmptyElementIgnored, SeveralRegistered.
     * 
     * @param methodList
     *            The collection of method as inScope.
     */
    public void setMethod_InScope(final Collection<String> methodList) {
        doSetMethod_InScope(methodList);
    }

    public void doSetMethod_InScope(final Collection<String> methodList) {
        regINS(CK_INS, cTL(methodList), getCValueMethod(), "METHOD");
    }

    /**
     * NotInScope(not in ('a', 'b')). And NullOrEmptyIgnored,
     * NullOrEmptyElementIgnored, SeveralRegistered.
     * 
     * @param methodList
     *            The collection of method as notInScope.
     */
    public void setMethod_NotInScope(final Collection<String> methodList) {
        doSetMethod_NotInScope(methodList);
    }

    public void doSetMethod_NotInScope(final Collection<String> methodList) {
        regINS(CK_NINS, cTL(methodList), getCValueMethod(), "METHOD");
    }

    /**
     * PrefixSearch(like 'xxx%' escape ...). And NullOrEmptyIgnored,
     * SeveralRegistered.
     * 
     * @param method
     *            The value of method as prefixSearch.
     */
    public void setMethod_PrefixSearch(final String method) {
        setMethod_LikeSearch(method, cLSOP());
    }

    /**
     * LikeSearch with various options. (versatile) {like '%xxx%' escape ...} <br />
     * And NullOrEmptyIgnored, SeveralRegistered.
     * 
     * @param method
     *            The value of method as likeSearch.
     * @param likeSearchOption
     *            The option of like-search. (NotNull)
     */
    public void setMethod_LikeSearch(final String method,
            final LikeSearchOption likeSearchOption) {
        regLSQ(
            CK_LS,
            fRES(method),
            getCValueMethod(),
            "METHOD",
            likeSearchOption);
    }

    /**
     * NotLikeSearch with various options. (versatile) {not like 'xxx%' escape
     * ...} <br />
     * And NullOrEmptyIgnored, SeveralRegistered.
     * 
     * @param method
     *            The value of method as notLikeSearch.
     * @param likeSearchOption
     *            The option of not-like-search. (NotNull)
     */
    public void setMethod_NotLikeSearch(final String method,
            final LikeSearchOption likeSearchOption) {
        regLSQ(
            CK_NLS,
            fRES(method),
            getCValueMethod(),
            "METHOD",
            likeSearchOption);
    }

    protected void regMethod(final ConditionKey k, final Object v) {
        regQ(k, v, getCValueMethod(), "METHOD");
    }

    abstract protected ConditionValue getCValueMethod();

    /**
     * Equal(=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br />
     * URL: {IX+, NotNull, VARCHAR2(4000)}
     * 
     * @param url
     *            The value of url as equal.
     */
    public void setUrl_Equal(final String url) {
        doSetUrl_Equal(fRES(url));
    }

    protected void doSetUrl_Equal(final String url) {
        regUrl(CK_EQ, url);
    }

    /**
     * NotEqual(&lt;&gt;). And NullOrEmptyIgnored, OnlyOnceRegistered.
     * 
     * @param url
     *            The value of url as notEqual.
     */
    public void setUrl_NotEqual(final String url) {
        doSetUrl_NotEqual(fRES(url));
    }

    protected void doSetUrl_NotEqual(final String url) {
        regUrl(CK_NES, url);
    }

    /**
     * GreaterThan(&gt;). And NullOrEmptyIgnored, OnlyOnceRegistered.
     * 
     * @param url
     *            The value of url as greaterThan.
     */
    public void setUrl_GreaterThan(final String url) {
        regUrl(CK_GT, fRES(url));
    }

    /**
     * LessThan(&lt;). And NullOrEmptyIgnored, OnlyOnceRegistered.
     * 
     * @param url
     *            The value of url as lessThan.
     */
    public void setUrl_LessThan(final String url) {
        regUrl(CK_LT, fRES(url));
    }

    /**
     * GreaterEqual(&gt;=). And NullOrEmptyIgnored, OnlyOnceRegistered.
     * 
     * @param url
     *            The value of url as greaterEqual.
     */
    public void setUrl_GreaterEqual(final String url) {
        regUrl(CK_GE, fRES(url));
    }

    /**
     * LessEqual(&lt;=). And NullOrEmptyIgnored, OnlyOnceRegistered.
     * 
     * @param url
     *            The value of url as lessEqual.
     */
    public void setUrl_LessEqual(final String url) {
        regUrl(CK_LE, fRES(url));
    }

    /**
     * InScope(in ('a', 'b')). And NullOrEmptyIgnored,
     * NullOrEmptyElementIgnored, SeveralRegistered.
     * 
     * @param urlList
     *            The collection of url as inScope.
     */
    public void setUrl_InScope(final Collection<String> urlList) {
        doSetUrl_InScope(urlList);
    }

    public void doSetUrl_InScope(final Collection<String> urlList) {
        regINS(CK_INS, cTL(urlList), getCValueUrl(), "URL");
    }

    /**
     * NotInScope(not in ('a', 'b')). And NullOrEmptyIgnored,
     * NullOrEmptyElementIgnored, SeveralRegistered.
     * 
     * @param urlList
     *            The collection of url as notInScope.
     */
    public void setUrl_NotInScope(final Collection<String> urlList) {
        doSetUrl_NotInScope(urlList);
    }

    public void doSetUrl_NotInScope(final Collection<String> urlList) {
        regINS(CK_NINS, cTL(urlList), getCValueUrl(), "URL");
    }

    /**
     * PrefixSearch(like 'xxx%' escape ...). And NullOrEmptyIgnored,
     * SeveralRegistered.
     * 
     * @param url
     *            The value of url as prefixSearch.
     */
    public void setUrl_PrefixSearch(final String url) {
        setUrl_LikeSearch(url, cLSOP());
    }

    /**
     * LikeSearch with various options. (versatile) {like '%xxx%' escape ...} <br />
     * And NullOrEmptyIgnored, SeveralRegistered.
     * 
     * @param url
     *            The value of url as likeSearch.
     * @param likeSearchOption
     *            The option of like-search. (NotNull)
     */
    public void setUrl_LikeSearch(final String url, final LikeSearchOption likeSearchOption) {
        regLSQ(CK_LS, fRES(url), getCValueUrl(), "URL", likeSearchOption);
    }

    /**
     * NotLikeSearch with various options. (versatile) {not like 'xxx%' escape
     * ...} <br />
     * And NullOrEmptyIgnored, SeveralRegistered.
     * 
     * @param url
     *            The value of url as notLikeSearch.
     * @param likeSearchOption
     *            The option of not-like-search. (NotNull)
     */
    public void setUrl_NotLikeSearch(final String url,
            final LikeSearchOption likeSearchOption) {
        regLSQ(CK_NLS, fRES(url), getCValueUrl(), "URL", likeSearchOption);
    }

    protected void regUrl(final ConditionKey k, final Object v) {
        regQ(k, v, getCValueUrl(), "URL");
    }

    abstract protected ConditionValue getCValueUrl();

    /**
     * Equal(=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br />
     * PARENT_URL: {VARCHAR2(4000)}
     * 
     * @param parentUrl
     *            The value of parentUrl as equal.
     */
    public void setParentUrl_Equal(final String parentUrl) {
        doSetParentUrl_Equal(fRES(parentUrl));
    }

    protected void doSetParentUrl_Equal(final String parentUrl) {
        regParentUrl(CK_EQ, parentUrl);
    }

    /**
     * NotEqual(&lt;&gt;). And NullOrEmptyIgnored, OnlyOnceRegistered.
     * 
     * @param parentUrl
     *            The value of parentUrl as notEqual.
     */
    public void setParentUrl_NotEqual(final String parentUrl) {
        doSetParentUrl_NotEqual(fRES(parentUrl));
    }

    protected void doSetParentUrl_NotEqual(final String parentUrl) {
        regParentUrl(CK_NES, parentUrl);
    }

    /**
     * GreaterThan(&gt;). And NullOrEmptyIgnored, OnlyOnceRegistered.
     * 
     * @param parentUrl
     *            The value of parentUrl as greaterThan.
     */
    public void setParentUrl_GreaterThan(final String parentUrl) {
        regParentUrl(CK_GT, fRES(parentUrl));
    }

    /**
     * LessThan(&lt;). And NullOrEmptyIgnored, OnlyOnceRegistered.
     * 
     * @param parentUrl
     *            The value of parentUrl as lessThan.
     */
    public void setParentUrl_LessThan(final String parentUrl) {
        regParentUrl(CK_LT, fRES(parentUrl));
    }

    /**
     * GreaterEqual(&gt;=). And NullOrEmptyIgnored, OnlyOnceRegistered.
     * 
     * @param parentUrl
     *            The value of parentUrl as greaterEqual.
     */
    public void setParentUrl_GreaterEqual(final String parentUrl) {
        regParentUrl(CK_GE, fRES(parentUrl));
    }

    /**
     * LessEqual(&lt;=). And NullOrEmptyIgnored, OnlyOnceRegistered.
     * 
     * @param parentUrl
     *            The value of parentUrl as lessEqual.
     */
    public void setParentUrl_LessEqual(final String parentUrl) {
        regParentUrl(CK_LE, fRES(parentUrl));
    }

    /**
     * InScope(in ('a', 'b')). And NullOrEmptyIgnored,
     * NullOrEmptyElementIgnored, SeveralRegistered.
     * 
     * @param parentUrlList
     *            The collection of parentUrl as inScope.
     */
    public void setParentUrl_InScope(final Collection<String> parentUrlList) {
        doSetParentUrl_InScope(parentUrlList);
    }

    public void doSetParentUrl_InScope(final Collection<String> parentUrlList) {
        regINS(CK_INS, cTL(parentUrlList), getCValueParentUrl(), "PARENT_URL");
    }

    /**
     * NotInScope(not in ('a', 'b')). And NullOrEmptyIgnored,
     * NullOrEmptyElementIgnored, SeveralRegistered.
     * 
     * @param parentUrlList
     *            The collection of parentUrl as notInScope.
     */
    public void setParentUrl_NotInScope(final Collection<String> parentUrlList) {
        doSetParentUrl_NotInScope(parentUrlList);
    }

    public void doSetParentUrl_NotInScope(final Collection<String> parentUrlList) {
        regINS(CK_NINS, cTL(parentUrlList), getCValueParentUrl(), "PARENT_URL");
    }

    /**
     * PrefixSearch(like 'xxx%' escape ...). And NullOrEmptyIgnored,
     * SeveralRegistered.
     * 
     * @param parentUrl
     *            The value of parentUrl as prefixSearch.
     */
    public void setParentUrl_PrefixSearch(final String parentUrl) {
        setParentUrl_LikeSearch(parentUrl, cLSOP());
    }

    /**
     * LikeSearch with various options. (versatile) {like '%xxx%' escape ...} <br />
     * And NullOrEmptyIgnored, SeveralRegistered.
     * 
     * @param parentUrl
     *            The value of parentUrl as likeSearch.
     * @param likeSearchOption
     *            The option of like-search. (NotNull)
     */
    public void setParentUrl_LikeSearch(final String parentUrl,
            final LikeSearchOption likeSearchOption) {
        regLSQ(
            CK_LS,
            fRES(parentUrl),
            getCValueParentUrl(),
            "PARENT_URL",
            likeSearchOption);
    }

    /**
     * NotLikeSearch with various options. (versatile) {not like 'xxx%' escape
     * ...} <br />
     * And NullOrEmptyIgnored, SeveralRegistered.
     * 
     * @param parentUrl
     *            The value of parentUrl as notLikeSearch.
     * @param likeSearchOption
     *            The option of not-like-search. (NotNull)
     */
    public void setParentUrl_NotLikeSearch(final String parentUrl,
            final LikeSearchOption likeSearchOption) {
        regLSQ(
            CK_NLS,
            fRES(parentUrl),
            getCValueParentUrl(),
            "PARENT_URL",
            likeSearchOption);
    }

    /**
     * IsNull(is null). And OnlyOnceRegistered.
     */
    public void setParentUrl_IsNull() {
        regParentUrl(CK_ISN, DOBJ);
    }

    /**
     * IsNotNull(is not null). And OnlyOnceRegistered.
     */
    public void setParentUrl_IsNotNull() {
        regParentUrl(CK_ISNN, DOBJ);
    }

    protected void regParentUrl(final ConditionKey k, final Object v) {
        regQ(k, v, getCValueParentUrl(), "PARENT_URL");
    }

    abstract protected ConditionValue getCValueParentUrl();

    /**
     * Equal(=). And NullIgnored, OnlyOnceRegistered. <br />
     * DEPTH: {NotNull, NUMBER(5)}
     * 
     * @param depth
     *            The value of depth as equal.
     */
    public void setDepth_Equal(final Integer depth) {
        doSetDepth_Equal(depth);
    }

    protected void doSetDepth_Equal(final Integer depth) {
        regDepth(CK_EQ, depth);
    }

    /**
     * NotEqual(&lt;&gt;). And NullIgnored, OnlyOnceRegistered.
     * 
     * @param depth
     *            The value of depth as notEqual.
     */
    public void setDepth_NotEqual(final Integer depth) {
        doSetDepth_NotEqual(depth);
    }

    protected void doSetDepth_NotEqual(final Integer depth) {
        regDepth(CK_NES, depth);
    }

    /**
     * GreaterThan(&gt;). And NullIgnored, OnlyOnceRegistered.
     * 
     * @param depth
     *            The value of depth as greaterThan.
     */
    public void setDepth_GreaterThan(final Integer depth) {
        regDepth(CK_GT, depth);
    }

    /**
     * LessThan(&lt;). And NullIgnored, OnlyOnceRegistered.
     * 
     * @param depth
     *            The value of depth as lessThan.
     */
    public void setDepth_LessThan(final Integer depth) {
        regDepth(CK_LT, depth);
    }

    /**
     * GreaterEqual(&gt;=). And NullIgnored, OnlyOnceRegistered.
     * 
     * @param depth
     *            The value of depth as greaterEqual.
     */
    public void setDepth_GreaterEqual(final Integer depth) {
        regDepth(CK_GE, depth);
    }

    /**
     * LessEqual(&lt;=). And NullIgnored, OnlyOnceRegistered.
     * 
     * @param depth
     *            The value of depth as lessEqual.
     */
    public void setDepth_LessEqual(final Integer depth) {
        regDepth(CK_LE, depth);
    }

    /**
     * InScope(in (1, 2)). And NullIgnored, NullElementIgnored,
     * SeveralRegistered.
     * 
     * @param depthList
     *            The collection of depth as inScope.
     */
    public void setDepth_InScope(final Collection<Integer> depthList) {
        doSetDepth_InScope(depthList);
    }

    protected void doSetDepth_InScope(final Collection<Integer> depthList) {
        regINS(CK_INS, cTL(depthList), getCValueDepth(), "DEPTH");
    }

    /**
     * NotInScope(not in (1, 2)). And NullIgnored, NullElementIgnored,
     * SeveralRegistered.
     * 
     * @param depthList
     *            The collection of depth as notInScope.
     */
    public void setDepth_NotInScope(final Collection<Integer> depthList) {
        doSetDepth_NotInScope(depthList);
    }

    protected void doSetDepth_NotInScope(final Collection<Integer> depthList) {
        regINS(CK_NINS, cTL(depthList), getCValueDepth(), "DEPTH");
    }

    protected void regDepth(final ConditionKey k, final Object v) {
        regQ(k, v, getCValueDepth(), "DEPTH");
    }

    abstract protected ConditionValue getCValueDepth();

    /**
     * Equal(=). And NullIgnored, OnlyOnceRegistered. <br />
     * LAST_MODIFIED: {TIMESTAMP(6)(11, 6)}
     * 
     * @param lastModified
     *            The value of lastModified as equal.
     */
    public void setLastModified_Equal(final java.sql.Timestamp lastModified) {
        regLastModified(CK_EQ, lastModified);
    }

    /**
     * GreaterThan(&gt;). And NullIgnored, OnlyOnceRegistered.
     * 
     * @param lastModified
     *            The value of lastModified as greaterThan.
     */
    public void setLastModified_GreaterThan(final java.sql.Timestamp lastModified) {
        regLastModified(CK_GT, lastModified);
    }

    /**
     * LessThan(&lt;). And NullIgnored, OnlyOnceRegistered.
     * 
     * @param lastModified
     *            The value of lastModified as lessThan.
     */
    public void setLastModified_LessThan(final java.sql.Timestamp lastModified) {
        regLastModified(CK_LT, lastModified);
    }

    /**
     * GreaterEqual(&gt;=). And NullIgnored, OnlyOnceRegistered.
     * 
     * @param lastModified
     *            The value of lastModified as greaterEqual.
     */
    public void setLastModified_GreaterEqual(final java.sql.Timestamp lastModified) {
        regLastModified(CK_GE, lastModified);
    }

    /**
     * LessEqual(&lt;=). And NullIgnored, OnlyOnceRegistered.
     * 
     * @param lastModified
     *            The value of lastModified as lessEqual.
     */
    public void setLastModified_LessEqual(final java.sql.Timestamp lastModified) {
        regLastModified(CK_LE, lastModified);
    }

    /**
     * FromTo with various options. (versatile) <br />
     * {(default) fromDatetime &lt;= column &lt;= toDatetime} <br />
     * And NullIgnored, OnlyOnceRegistered.
     * 
     * @param fromDatetime
     *            The from-datetime(yyyy/MM/dd HH:mm:ss.SSS) of lastModified.
     *            (NullAllowed)
     * @param toDatetime
     *            The to-datetime(yyyy/MM/dd HH:mm:ss.SSS) of lastModified.
     *            (NullAllowed)
     * @param fromToOption
     *            The option of from-to. (NotNull)
     */
    public void setLastModified_FromTo(final java.util.Date fromDatetime,
            final java.util.Date toDatetime, final FromToOption fromToOption) {
        regFTQ(
            (fromDatetime != null ? new java.sql.Timestamp(
                fromDatetime.getTime()) : null),
            (toDatetime != null ? new java.sql.Timestamp(toDatetime.getTime())
                : null),
            getCValueLastModified(),
            "LAST_MODIFIED",
            fromToOption);
    }

    /**
     * DateFromTo. (Date means yyyy/MM/dd) <br />
     * {fromDate &lt;= column &lt; toDate + 1 day} <br />
     * And NullIgnored, OnlyOnceRegistered.
     * 
     * <pre>
     * ex) from:{2007/04/10 08:24:53} to:{2007/04/16 14:36:29}
     *     --&gt; column &gt;= '2007/04/10 00:00:00'
     *     and column <span style="color: #FD4747">&lt; '2007/04/17 00:00:00'</span>
     * </pre>
     * 
     * @param fromDate
     *            The from-date(yyyy/MM/dd) of lastModified. (NullAllowed)
     * @param toDate
     *            The to-date(yyyy/MM/dd) of lastModified. (NullAllowed)
     */
    public void setLastModified_DateFromTo(final java.util.Date fromDate,
            final java.util.Date toDate) {
        setLastModified_FromTo(fromDate, toDate, new DateFromToOption());
    }

    /**
     * IsNull(is null). And OnlyOnceRegistered.
     */
    public void setLastModified_IsNull() {
        regLastModified(CK_ISN, DOBJ);
    }

    /**
     * IsNotNull(is not null). And OnlyOnceRegistered.
     */
    public void setLastModified_IsNotNull() {
        regLastModified(CK_ISNN, DOBJ);
    }

    protected void regLastModified(final ConditionKey k, final Object v) {
        regQ(k, v, getCValueLastModified(), "LAST_MODIFIED");
    }

    abstract protected ConditionValue getCValueLastModified();

    /**
     * Equal(=). And NullIgnored, OnlyOnceRegistered. <br />
     * CREATE_TIME: {IX+, NotNull, TIMESTAMP(6)(11, 6)}
     * 
     * @param createTime
     *            The value of createTime as equal.
     */
    public void setCreateTime_Equal(final java.sql.Timestamp createTime) {
        regCreateTime(CK_EQ, createTime);
    }

    /**
     * GreaterThan(&gt;). And NullIgnored, OnlyOnceRegistered.
     * 
     * @param createTime
     *            The value of createTime as greaterThan.
     */
    public void setCreateTime_GreaterThan(final java.sql.Timestamp createTime) {
        regCreateTime(CK_GT, createTime);
    }

    /**
     * LessThan(&lt;). And NullIgnored, OnlyOnceRegistered.
     * 
     * @param createTime
     *            The value of createTime as lessThan.
     */
    public void setCreateTime_LessThan(final java.sql.Timestamp createTime) {
        regCreateTime(CK_LT, createTime);
    }

    /**
     * GreaterEqual(&gt;=). And NullIgnored, OnlyOnceRegistered.
     * 
     * @param createTime
     *            The value of createTime as greaterEqual.
     */
    public void setCreateTime_GreaterEqual(final java.sql.Timestamp createTime) {
        regCreateTime(CK_GE, createTime);
    }

    /**
     * LessEqual(&lt;=). And NullIgnored, OnlyOnceRegistered.
     * 
     * @param createTime
     *            The value of createTime as lessEqual.
     */
    public void setCreateTime_LessEqual(final java.sql.Timestamp createTime) {
        regCreateTime(CK_LE, createTime);
    }

    /**
     * FromTo with various options. (versatile) <br />
     * {(default) fromDatetime &lt;= column &lt;= toDatetime} <br />
     * And NullIgnored, OnlyOnceRegistered.
     * 
     * @param fromDatetime
     *            The from-datetime(yyyy/MM/dd HH:mm:ss.SSS) of createTime.
     *            (NullAllowed)
     * @param toDatetime
     *            The to-datetime(yyyy/MM/dd HH:mm:ss.SSS) of createTime.
     *            (NullAllowed)
     * @param fromToOption
     *            The option of from-to. (NotNull)
     */
    public void setCreateTime_FromTo(final java.util.Date fromDatetime,
            final java.util.Date toDatetime, final FromToOption fromToOption) {
        regFTQ(
            (fromDatetime != null ? new java.sql.Timestamp(
                fromDatetime.getTime()) : null),
            (toDatetime != null ? new java.sql.Timestamp(toDatetime.getTime())
                : null),
            getCValueCreateTime(),
            "CREATE_TIME",
            fromToOption);
    }

    /**
     * DateFromTo. (Date means yyyy/MM/dd) <br />
     * {fromDate &lt;= column &lt; toDate + 1 day} <br />
     * And NullIgnored, OnlyOnceRegistered.
     * 
     * <pre>
     * ex) from:{2007/04/10 08:24:53} to:{2007/04/16 14:36:29}
     *     --&gt; column &gt;= '2007/04/10 00:00:00'
     *     and column <span style="color: #FD4747">&lt; '2007/04/17 00:00:00'</span>
     * </pre>
     * 
     * @param fromDate
     *            The from-date(yyyy/MM/dd) of createTime. (NullAllowed)
     * @param toDate
     *            The to-date(yyyy/MM/dd) of createTime. (NullAllowed)
     */
    public void setCreateTime_DateFromTo(final java.util.Date fromDate,
            final java.util.Date toDate) {
        setCreateTime_FromTo(fromDate, toDate, new DateFromToOption());
    }

    protected void regCreateTime(final ConditionKey k, final Object v) {
        regQ(k, v, getCValueCreateTime(), "CREATE_TIME");
    }

    abstract protected ConditionValue getCValueCreateTime();

    // ===================================================================================
    // Scalar Condition
    // ================
    /**
     * Prepare ScalarCondition as equal. <br />
     * {where FOO = (select max(BAR) from ...)
     * 
     * <pre>
     * cb.query().<span style="color: #FD4747">scalar_Equal()</span>.max(new SubQuery&lt;UrlQueueCB&gt;() {
     *     public void query(UrlQueueCB subCB) {
     *         subCB.specify().setXxx... <span style="color: #3F7E5E">// derived column for function</span>
     *         subCB.query().setYyy...
     *     }
     * });
     * </pre>
     * 
     * @return The object to set up a function. (NotNull)
     */
    public HpSSQFunction<UrlQueueCB> scalar_Equal() {
        return xcreateSSQFunction(CK_EQ.getOperand());
    }

    /**
     * Prepare ScalarCondition as equal. <br />
     * {where FOO &lt;&gt; (select max(BAR) from ...)
     * 
     * <pre>
     * cb.query().<span style="color: #FD4747">scalar_NotEqual()</span>.max(new SubQuery&lt;UrlQueueCB&gt;() {
     *     public void query(UrlQueueCB subCB) {
     *         subCB.specify().setXxx... <span style="color: #3F7E5E">// derived column for function</span>
     *         subCB.query().setYyy...
     *     }
     * });
     * </pre>
     * 
     * @return The object to set up a function. (NotNull)
     */
    public HpSSQFunction<UrlQueueCB> scalar_NotEqual() {
        return xcreateSSQFunction(CK_NES.getOperand());
    }

    /**
     * Prepare ScalarCondition as greaterThan. <br />
     * {where FOO &gt; (select max(BAR) from ...)
     * 
     * <pre>
     * cb.query().<span style="color: #FD4747">scalar_GreaterThan()</span>.max(new SubQuery&lt;UrlQueueCB&gt;() {
     *     public void query(UrlQueueCB subCB) {
     *         subCB.specify().setFoo... <span style="color: #3F7E5E">// derived column for function</span>
     *         subCB.query().setBar...
     *     }
     * });
     * </pre>
     * 
     * @return The object to set up a function. (NotNull)
     */
    public HpSSQFunction<UrlQueueCB> scalar_GreaterThan() {
        return xcreateSSQFunction(CK_GT.getOperand());
    }

    /**
     * Prepare ScalarCondition as lessThan. <br />
     * {where FOO &lt; (select max(BAR) from ...)
     * 
     * <pre>
     * cb.query().<span style="color: #FD4747">scalar_LessThan()</span>.max(new SubQuery&lt;UrlQueueCB&gt;() {
     *     public void query(UrlQueueCB subCB) {
     *         subCB.specify().setFoo... <span style="color: #3F7E5E">// derived column for function</span>
     *         subCB.query().setBar...
     *     }
     * });
     * </pre>
     * 
     * @return The object to set up a function. (NotNull)
     */
    public HpSSQFunction<UrlQueueCB> scalar_LessThan() {
        return xcreateSSQFunction(CK_LT.getOperand());
    }

    /**
     * Prepare ScalarCondition as greaterEqual. <br />
     * {where FOO &gt;= (select max(BAR) from ...)
     * 
     * <pre>
     * cb.query().<span style="color: #FD4747">scalar_GreaterEqual()</span>.max(new SubQuery&lt;UrlQueueCB&gt;() {
     *     public void query(UrlQueueCB subCB) {
     *         subCB.specify().setFoo... <span style="color: #3F7E5E">// derived column for function</span>
     *         subCB.query().setBar...
     *     }
     * });
     * </pre>
     * 
     * @return The object to set up a function. (NotNull)
     */
    public HpSSQFunction<UrlQueueCB> scalar_GreaterEqual() {
        return xcreateSSQFunction(CK_GE.getOperand());
    }

    /**
     * Prepare ScalarCondition as lessEqual. <br />
     * {where FOO &lt;= (select max(BAR) from ...)
     * 
     * <pre>
     * cb.query().<span style="color: #FD4747">scalar_LessEqual()</span>.max(new SubQuery&lt;UrlQueueCB&gt;() {
     *     public void query(UrlQueueCB subCB) {
     *         subCB.specify().setFoo... <span style="color: #3F7E5E">// derived column for function</span>
     *         subCB.query().setBar...
     *     }
     * });
     * </pre>
     * 
     * @return The object to set up a function. (NotNull)
     */
    public HpSSQFunction<UrlQueueCB> scalar_LessEqual() {
        return xcreateSSQFunction(CK_LE.getOperand());
    }

    protected HpSSQFunction<UrlQueueCB> xcreateSSQFunction(final String operand) {
        return new HpSSQFunction<UrlQueueCB>(new HpSSQSetupper<UrlQueueCB>() {
            public void setup(final String function, final SubQuery<UrlQueueCB> subQuery) {
                xscalarCondition(function, subQuery, operand);
            }
        });
    }

    protected void xscalarCondition(final String function,
            final SubQuery<UrlQueueCB> subQuery, final String operand) {
        assertObjectNotNull("subQuery<UrlQueueCB>", subQuery);
        final UrlQueueCB cb = new UrlQueueCB();
        cb.xsetupForScalarCondition(this);
        subQuery.query(cb);
        final String subQueryPropertyName = keepScalarCondition(cb.query()); // for
                                                                       // saving
                                                                       // query-value.
        registerScalarCondition(
            function,
            cb.query(),
            subQueryPropertyName,
            operand);
    }

    public abstract String keepScalarCondition(UrlQueueCQ subQuery);

    // ===================================================================================
    // Myself InScope
    // ==============
    /**
     * Myself InScope (SubQuery). {mainly for CLOB and Union}
     * 
     * @param subQuery
     *            The implementation of sub query. (NotNull)
     */
    public void myselfInScope(final SubQuery<UrlQueueCB> subQuery) {
        assertObjectNotNull("subQuery<UrlQueueCB>", subQuery);
        final UrlQueueCB cb = new UrlQueueCB();
        cb.xsetupForInScopeRelation(this);
        subQuery.query(cb);
        final String subQueryPropertyName = keepMyselfInScopeRelation(cb.query()); // for
                                                                             // saving
                                                                             // query-value.
        registerInScopeRelation(cb.query(), "ID", "ID", subQueryPropertyName);
    }

    public abstract String keepMyselfInScopeRelation(UrlQueueCQ subQuery);

    // ===================================================================================
    // Very Internal
    // =============
    // very internal (for suppressing warn about 'Not Use Import')
    protected String xabCB() {
        return UrlQueueCB.class.getName();
    }

    protected String xabCQ() {
        return UrlQueueCQ.class.getName();
    }

    protected String xabLSO() {
        return LikeSearchOption.class.getName();
    }

    protected String xabSSQS() {
        return HpSSQSetupper.class.getName();
    }
}