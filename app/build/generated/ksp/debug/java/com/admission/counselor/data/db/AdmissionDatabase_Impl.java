package com.admission.counselor.data.db;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.FtsTableInfo;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class AdmissionDatabase_Impl extends AdmissionDatabase {
  private volatile CourseDao _courseDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `courses` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT NOT NULL, `duration` TEXT NOT NULL, `fee` TEXT NOT NULL, `deadline` TEXT NOT NULL, `eligibility` TEXT NOT NULL, `highlights` TEXT NOT NULL, `why_join` TEXT NOT NULL)");
        db.execSQL("CREATE VIRTUAL TABLE IF NOT EXISTS `courses_fts` USING FTS4(`name` TEXT NOT NULL, `duration` TEXT NOT NULL, `fee` TEXT NOT NULL, `deadline` TEXT NOT NULL, `eligibility` TEXT NOT NULL, `highlights` TEXT NOT NULL, `why_join` TEXT NOT NULL, content=`courses`)");
        db.execSQL("CREATE TRIGGER IF NOT EXISTS room_fts_content_sync_courses_fts_BEFORE_UPDATE BEFORE UPDATE ON `courses` BEGIN DELETE FROM `courses_fts` WHERE `docid`=OLD.`rowid`; END");
        db.execSQL("CREATE TRIGGER IF NOT EXISTS room_fts_content_sync_courses_fts_BEFORE_DELETE BEFORE DELETE ON `courses` BEGIN DELETE FROM `courses_fts` WHERE `docid`=OLD.`rowid`; END");
        db.execSQL("CREATE TRIGGER IF NOT EXISTS room_fts_content_sync_courses_fts_AFTER_UPDATE AFTER UPDATE ON `courses` BEGIN INSERT INTO `courses_fts`(`docid`, `name`, `duration`, `fee`, `deadline`, `eligibility`, `highlights`, `why_join`) VALUES (NEW.`rowid`, NEW.`name`, NEW.`duration`, NEW.`fee`, NEW.`deadline`, NEW.`eligibility`, NEW.`highlights`, NEW.`why_join`); END");
        db.execSQL("CREATE TRIGGER IF NOT EXISTS room_fts_content_sync_courses_fts_AFTER_INSERT AFTER INSERT ON `courses` BEGIN INSERT INTO `courses_fts`(`docid`, `name`, `duration`, `fee`, `deadline`, `eligibility`, `highlights`, `why_join`) VALUES (NEW.`rowid`, NEW.`name`, NEW.`duration`, NEW.`fee`, NEW.`deadline`, NEW.`eligibility`, NEW.`highlights`, NEW.`why_join`); END");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'a4a7f05e0599e03d3597d1aacda53f34')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `courses`");
        db.execSQL("DROP TABLE IF EXISTS `courses_fts`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TRIGGER IF NOT EXISTS room_fts_content_sync_courses_fts_BEFORE_UPDATE BEFORE UPDATE ON `courses` BEGIN DELETE FROM `courses_fts` WHERE `docid`=OLD.`rowid`; END");
        db.execSQL("CREATE TRIGGER IF NOT EXISTS room_fts_content_sync_courses_fts_BEFORE_DELETE BEFORE DELETE ON `courses` BEGIN DELETE FROM `courses_fts` WHERE `docid`=OLD.`rowid`; END");
        db.execSQL("CREATE TRIGGER IF NOT EXISTS room_fts_content_sync_courses_fts_AFTER_UPDATE AFTER UPDATE ON `courses` BEGIN INSERT INTO `courses_fts`(`docid`, `name`, `duration`, `fee`, `deadline`, `eligibility`, `highlights`, `why_join`) VALUES (NEW.`rowid`, NEW.`name`, NEW.`duration`, NEW.`fee`, NEW.`deadline`, NEW.`eligibility`, NEW.`highlights`, NEW.`why_join`); END");
        db.execSQL("CREATE TRIGGER IF NOT EXISTS room_fts_content_sync_courses_fts_AFTER_INSERT AFTER INSERT ON `courses` BEGIN INSERT INTO `courses_fts`(`docid`, `name`, `duration`, `fee`, `deadline`, `eligibility`, `highlights`, `why_join`) VALUES (NEW.`rowid`, NEW.`name`, NEW.`duration`, NEW.`fee`, NEW.`deadline`, NEW.`eligibility`, NEW.`highlights`, NEW.`why_join`); END");
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsCourses = new HashMap<String, TableInfo.Column>(8);
        _columnsCourses.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCourses.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCourses.put("duration", new TableInfo.Column("duration", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCourses.put("fee", new TableInfo.Column("fee", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCourses.put("deadline", new TableInfo.Column("deadline", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCourses.put("eligibility", new TableInfo.Column("eligibility", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCourses.put("highlights", new TableInfo.Column("highlights", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCourses.put("why_join", new TableInfo.Column("why_join", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysCourses = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesCourses = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoCourses = new TableInfo("courses", _columnsCourses, _foreignKeysCourses, _indicesCourses);
        final TableInfo _existingCourses = TableInfo.read(db, "courses");
        if (!_infoCourses.equals(_existingCourses)) {
          return new RoomOpenHelper.ValidationResult(false, "courses(com.admission.counselor.data.db.CourseEntity).\n"
                  + " Expected:\n" + _infoCourses + "\n"
                  + " Found:\n" + _existingCourses);
        }
        final HashSet<String> _columnsCoursesFts = new HashSet<String>(7);
        _columnsCoursesFts.add("name");
        _columnsCoursesFts.add("duration");
        _columnsCoursesFts.add("fee");
        _columnsCoursesFts.add("deadline");
        _columnsCoursesFts.add("eligibility");
        _columnsCoursesFts.add("highlights");
        _columnsCoursesFts.add("why_join");
        final FtsTableInfo _infoCoursesFts = new FtsTableInfo("courses_fts", _columnsCoursesFts, "CREATE VIRTUAL TABLE IF NOT EXISTS `courses_fts` USING FTS4(`name` TEXT NOT NULL, `duration` TEXT NOT NULL, `fee` TEXT NOT NULL, `deadline` TEXT NOT NULL, `eligibility` TEXT NOT NULL, `highlights` TEXT NOT NULL, `why_join` TEXT NOT NULL, content=`courses`)");
        final FtsTableInfo _existingCoursesFts = FtsTableInfo.read(db, "courses_fts");
        if (!_infoCoursesFts.equals(_existingCoursesFts)) {
          return new RoomOpenHelper.ValidationResult(false, "courses_fts(com.admission.counselor.data.db.CourseFtsEntity).\n"
                  + " Expected:\n" + _infoCoursesFts + "\n"
                  + " Found:\n" + _existingCoursesFts);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "a4a7f05e0599e03d3597d1aacda53f34", "47f895ba326340e7de4b2a74355d3de1");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(1);
    _shadowTablesMap.put("courses_fts", "courses");
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "courses","courses_fts");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `courses`");
      _db.execSQL("DELETE FROM `courses_fts`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(CourseDao.class, CourseDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public CourseDao courseDao() {
    if (_courseDao != null) {
      return _courseDao;
    } else {
      synchronized(this) {
        if(_courseDao == null) {
          _courseDao = new CourseDao_Impl(this);
        }
        return _courseDao;
      }
    }
  }
}
