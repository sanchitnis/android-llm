package com.admission.counselor.data.db;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.room.CoroutinesRoom;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.coroutines.Continuation;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class CourseDao_Impl implements CourseDao {
  private final RoomDatabase __db;

  public CourseDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
  }

  @Override
  public Object searchCourses(final String query,
      final Continuation<? super List<CourseEntity>> $completion) {
    final String _sql = "\n"
            + "        SELECT courses.* \n"
            + "        FROM courses \n"
            + "        JOIN courses_fts ON courses.id = courses_fts.rowid \n"
            + "        WHERE courses_fts MATCH ? \n"
            + "        LIMIT 3\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, query);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<CourseEntity>>() {
      @Override
      @NonNull
      public List<CourseEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "duration");
          final int _cursorIndexOfFee = CursorUtil.getColumnIndexOrThrow(_cursor, "fee");
          final int _cursorIndexOfDeadline = CursorUtil.getColumnIndexOrThrow(_cursor, "deadline");
          final int _cursorIndexOfEligibility = CursorUtil.getColumnIndexOrThrow(_cursor, "eligibility");
          final int _cursorIndexOfHighlights = CursorUtil.getColumnIndexOrThrow(_cursor, "highlights");
          final int _cursorIndexOfWhyJoin = CursorUtil.getColumnIndexOrThrow(_cursor, "why_join");
          final List<CourseEntity> _result = new ArrayList<CourseEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final CourseEntity _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpDuration;
            _tmpDuration = _cursor.getString(_cursorIndexOfDuration);
            final String _tmpFee;
            _tmpFee = _cursor.getString(_cursorIndexOfFee);
            final String _tmpDeadline;
            _tmpDeadline = _cursor.getString(_cursorIndexOfDeadline);
            final String _tmpEligibility;
            _tmpEligibility = _cursor.getString(_cursorIndexOfEligibility);
            final String _tmpHighlights;
            _tmpHighlights = _cursor.getString(_cursorIndexOfHighlights);
            final String _tmpWhy_join;
            _tmpWhy_join = _cursor.getString(_cursorIndexOfWhyJoin);
            _item = new CourseEntity(_tmpId,_tmpName,_tmpDuration,_tmpFee,_tmpDeadline,_tmpEligibility,_tmpHighlights,_tmpWhy_join);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getFallbackCourses(final Continuation<? super List<CourseEntity>> $completion) {
    final String _sql = "SELECT * FROM courses LIMIT 5";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<CourseEntity>>() {
      @Override
      @NonNull
      public List<CourseEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "duration");
          final int _cursorIndexOfFee = CursorUtil.getColumnIndexOrThrow(_cursor, "fee");
          final int _cursorIndexOfDeadline = CursorUtil.getColumnIndexOrThrow(_cursor, "deadline");
          final int _cursorIndexOfEligibility = CursorUtil.getColumnIndexOrThrow(_cursor, "eligibility");
          final int _cursorIndexOfHighlights = CursorUtil.getColumnIndexOrThrow(_cursor, "highlights");
          final int _cursorIndexOfWhyJoin = CursorUtil.getColumnIndexOrThrow(_cursor, "why_join");
          final List<CourseEntity> _result = new ArrayList<CourseEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final CourseEntity _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpDuration;
            _tmpDuration = _cursor.getString(_cursorIndexOfDuration);
            final String _tmpFee;
            _tmpFee = _cursor.getString(_cursorIndexOfFee);
            final String _tmpDeadline;
            _tmpDeadline = _cursor.getString(_cursorIndexOfDeadline);
            final String _tmpEligibility;
            _tmpEligibility = _cursor.getString(_cursorIndexOfEligibility);
            final String _tmpHighlights;
            _tmpHighlights = _cursor.getString(_cursorIndexOfHighlights);
            final String _tmpWhy_join;
            _tmpWhy_join = _cursor.getString(_cursorIndexOfWhyJoin);
            _item = new CourseEntity(_tmpId,_tmpName,_tmpDuration,_tmpFee,_tmpDeadline,_tmpEligibility,_tmpHighlights,_tmpWhy_join);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
