/*
 * Copyright 2004-2013 the Seasar Foundation and the Others.
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
package org.seasar.robot.db.bsbhv.cursor;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.seasar.dbflute.jdbc.CursorHandler;
import org.seasar.robot.db.exbhv.cursor.AccessResultDiffCursor;

/**
 * The cursor handler of AccessResultDiff.
 * 
 * @author DBFlute(AutoGenerator)
 */
public abstract class BsAccessResultDiffCursorHandler implements CursorHandler {

    /**
     * Handle the cursor.
     * 
     * @param rs
     *            The cursor (result set) for the query, which has first
     *            pointer. (NotNull)
     * @return The result object of handling process. (NullAllowed)
     * @throws SQLException
     */
    @Override
    public Object handle(final ResultSet rs) throws SQLException {
        return fetchCursor(createTypeSafeCursor(rs));
    }

    /**
     * Create the type-safe cursor.
     * 
     * @param rs
     *            The cursor (result set) for the query, which has first
     *            pointer. (NotNull)
     * @return The created type-safe cursor. (NotNull)
     * @throws SQLException
     */
    protected AccessResultDiffCursor createTypeSafeCursor(final ResultSet rs)
            throws SQLException {
        final AccessResultDiffCursor cursor = new AccessResultDiffCursor();
        cursor.accept(rs);
        return cursor;
    }

    /**
     * Fetch the cursor.
     * 
     * @param cursor
     *            The type-safe cursor for the query, which has first pointer.
     *            (NotNull)
     * @return The result object of handling process. (NullAllowed)
     * @throws SQLException
     */
    protected abstract Object fetchCursor(AccessResultDiffCursor cursor)
            throws SQLException;
}
