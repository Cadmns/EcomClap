package io.realm;


import android.util.JsonReader;
import android.util.JsonToken;
import io.realm.RealmObject;
import io.realm.exceptions.RealmException;
import io.realm.exceptions.RealmMigrationNeededException;
import io.realm.internal.ColumnType;
import io.realm.internal.ImplicitTransaction;
import io.realm.internal.LinkView;
import io.realm.internal.RealmObjectProxy;
import io.realm.internal.Table;
import io.realm.internal.TableOrView;
import io.realm.internal.android.JsonUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import techlab.digital.com.ecommclap.model.realmDbModel.CategoryRealmDb;

public class CategoryRealmDbRealmProxy extends CategoryRealmDb
    implements RealmObjectProxy {

    private static long INDEX_ID;
    private static long INDEX_NAME;
    private static long INDEX_SLUG;
    private static long INDEX_DESCRIPTION;
    private static long INDEX_IMAGE;
    private static Map<String, Long> columnIndices;
    private static final List<String> FIELD_NAMES;
    static {
        List<String> fieldNames = new ArrayList<String>();
        fieldNames.add("id");
        fieldNames.add("name");
        fieldNames.add("slug");
        fieldNames.add("description");
        fieldNames.add("image");
        FIELD_NAMES = Collections.unmodifiableList(fieldNames);
    }

    @Override
    public int getId() {
        realm.checkIfValid();
        return (int) row.getLong(INDEX_ID);
    }

    @Override
    public void setId(int value) {
        realm.checkIfValid();
        row.setLong(INDEX_ID, (long) value);
    }

    @Override
    public String getName() {
        realm.checkIfValid();
        return (java.lang.String) row.getString(INDEX_NAME);
    }

    @Override
    public void setName(String value) {
        realm.checkIfValid();
        row.setString(INDEX_NAME, (String) value);
    }

    @Override
    public String getSlug() {
        realm.checkIfValid();
        return (java.lang.String) row.getString(INDEX_SLUG);
    }

    @Override
    public void setSlug(String value) {
        realm.checkIfValid();
        row.setString(INDEX_SLUG, (String) value);
    }

    @Override
    public String getDescription() {
        realm.checkIfValid();
        return (java.lang.String) row.getString(INDEX_DESCRIPTION);
    }

    @Override
    public void setDescription(String value) {
        realm.checkIfValid();
        row.setString(INDEX_DESCRIPTION, (String) value);
    }

    @Override
    public String getImage() {
        realm.checkIfValid();
        return (java.lang.String) row.getString(INDEX_IMAGE);
    }

    @Override
    public void setImage(String value) {
        realm.checkIfValid();
        row.setString(INDEX_IMAGE, (String) value);
    }

    public static Table initTable(ImplicitTransaction transaction) {
        if (!transaction.hasTable("class_CategoryRealmDb")) {
            Table table = transaction.getTable("class_CategoryRealmDb");
            table.addColumn(ColumnType.INTEGER, "id");
            table.addColumn(ColumnType.STRING, "name");
            table.addColumn(ColumnType.STRING, "slug");
            table.addColumn(ColumnType.STRING, "description");
            table.addColumn(ColumnType.STRING, "image");
            table.addSearchIndex(table.getColumnIndex("id"));
            table.setPrimaryKey("id");
            return table;
        }
        return transaction.getTable("class_CategoryRealmDb");
    }

    public static void validateTable(ImplicitTransaction transaction) {
        if (transaction.hasTable("class_CategoryRealmDb")) {
            Table table = transaction.getTable("class_CategoryRealmDb");
            if (table.getColumnCount() != 5) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Field count does not match - expected 5 but was " + table.getColumnCount());
            }
            Map<String, ColumnType> columnTypes = new HashMap<String, ColumnType>();
            for (long i = 0; i < 5; i++) {
                columnTypes.put(table.getColumnName(i), table.getColumnType(i));
            }

            columnIndices = new HashMap<String, Long>();
            for (String fieldName : getFieldNames()) {
                long index = table.getColumnIndex(fieldName);
                if (index == -1) {
                    throw new RealmMigrationNeededException(transaction.getPath(), "Field '" + fieldName + "' not found for type CategoryRealmDb");
                }
                columnIndices.put(fieldName, index);
            }
            INDEX_ID = table.getColumnIndex("id");
            INDEX_NAME = table.getColumnIndex("name");
            INDEX_SLUG = table.getColumnIndex("slug");
            INDEX_DESCRIPTION = table.getColumnIndex("description");
            INDEX_IMAGE = table.getColumnIndex("image");

            if (!columnTypes.containsKey("id")) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Missing field 'id'");
            }
            if (columnTypes.get("id") != ColumnType.INTEGER) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Invalid type 'int' for field 'id'");
            }
            if (table.getPrimaryKey() != table.getColumnIndex("id")) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Primary key not defined for field 'id'");
            }
            if (!table.hasSearchIndex(table.getColumnIndex("id"))) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Index not defined for field 'id'");
            }
            if (!columnTypes.containsKey("name")) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Missing field 'name'");
            }
            if (columnTypes.get("name") != ColumnType.STRING) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Invalid type 'String' for field 'name'");
            }
            if (!columnTypes.containsKey("slug")) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Missing field 'slug'");
            }
            if (columnTypes.get("slug") != ColumnType.STRING) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Invalid type 'String' for field 'slug'");
            }
            if (!columnTypes.containsKey("description")) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Missing field 'description'");
            }
            if (columnTypes.get("description") != ColumnType.STRING) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Invalid type 'String' for field 'description'");
            }
            if (!columnTypes.containsKey("image")) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Missing field 'image'");
            }
            if (columnTypes.get("image") != ColumnType.STRING) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Invalid type 'String' for field 'image'");
            }
        } else {
            throw new RealmMigrationNeededException(transaction.getPath(), "The CategoryRealmDb class is missing from the schema for this Realm.");
        }
    }

    public static String getTableName() {
        return "class_CategoryRealmDb";
    }

    public static List<String> getFieldNames() {
        return FIELD_NAMES;
    }

    public static Map<String,Long> getColumnIndices() {
        return columnIndices;
    }

    public static CategoryRealmDb createOrUpdateUsingJsonObject(Realm realm, JSONObject json, boolean update)
        throws JSONException {
        CategoryRealmDb obj = null;
        if (update) {
            Table table = realm.getTable(CategoryRealmDb.class);
            long pkColumnIndex = table.getPrimaryKey();
            if (!json.isNull("id")) {
                long rowIndex = table.findFirstLong(pkColumnIndex, json.getLong("id"));
                if (rowIndex != TableOrView.NO_MATCH) {
                    obj = new CategoryRealmDbRealmProxy();
                    obj.realm = realm;
                    obj.row = table.getUncheckedRow(rowIndex);
                }
            }
        }
        if (obj == null) {
            obj = realm.createObject(CategoryRealmDb.class);
        }
        if (!json.isNull("id")) {
            obj.setId((int) json.getInt("id"));
        }
        if (json.has("name")) {
            if (json.isNull("name")) {
                obj.setName("");
            } else {
                obj.setName((String) json.getString("name"));
            }
        }
        if (json.has("slug")) {
            if (json.isNull("slug")) {
                obj.setSlug("");
            } else {
                obj.setSlug((String) json.getString("slug"));
            }
        }
        if (json.has("description")) {
            if (json.isNull("description")) {
                obj.setDescription("");
            } else {
                obj.setDescription((String) json.getString("description"));
            }
        }
        if (json.has("image")) {
            if (json.isNull("image")) {
                obj.setImage("");
            } else {
                obj.setImage((String) json.getString("image"));
            }
        }
        return obj;
    }

    public static CategoryRealmDb createUsingJsonStream(Realm realm, JsonReader reader)
        throws IOException {
        CategoryRealmDb obj = realm.createObject(CategoryRealmDb.class);
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("id") && reader.peek() != JsonToken.NULL) {
                obj.setId((int) reader.nextInt());
            } else if (name.equals("name")) {
                if (reader.peek() == JsonToken.NULL) {
                    obj.setName("");
                    reader.skipValue();
                } else {
                    obj.setName((String) reader.nextString());
                }
            } else if (name.equals("slug")) {
                if (reader.peek() == JsonToken.NULL) {
                    obj.setSlug("");
                    reader.skipValue();
                } else {
                    obj.setSlug((String) reader.nextString());
                }
            } else if (name.equals("description")) {
                if (reader.peek() == JsonToken.NULL) {
                    obj.setDescription("");
                    reader.skipValue();
                } else {
                    obj.setDescription((String) reader.nextString());
                }
            } else if (name.equals("image")) {
                if (reader.peek() == JsonToken.NULL) {
                    obj.setImage("");
                    reader.skipValue();
                } else {
                    obj.setImage((String) reader.nextString());
                }
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return obj;
    }

    public static CategoryRealmDb copyOrUpdate(Realm realm, CategoryRealmDb object, boolean update, Map<RealmObject,RealmObjectProxy> cache) {
        if (object.realm != null && object.realm.getPath().equals(realm.getPath())) {
            return object;
        }
        CategoryRealmDb realmObject = null;
        boolean canUpdate = update;
        if (canUpdate) {
            Table table = realm.getTable(CategoryRealmDb.class);
            long pkColumnIndex = table.getPrimaryKey();
            long rowIndex = table.findFirstLong(pkColumnIndex, object.getId());
            if (rowIndex != TableOrView.NO_MATCH) {
                realmObject = new CategoryRealmDbRealmProxy();
                realmObject.realm = realm;
                realmObject.row = table.getUncheckedRow(rowIndex);
                cache.put(object, (RealmObjectProxy) realmObject);
            } else {
                canUpdate = false;
            }
        }

        if (canUpdate) {
            return update(realm, realmObject, object, cache);
        } else {
            return copy(realm, object, update, cache);
        }
    }

    public static CategoryRealmDb copy(Realm realm, CategoryRealmDb newObject, boolean update, Map<RealmObject,RealmObjectProxy> cache) {
        CategoryRealmDb realmObject = realm.createObject(CategoryRealmDb.class, newObject.getId());
        cache.put(newObject, (RealmObjectProxy) realmObject);
        realmObject.setId(newObject.getId());
        realmObject.setName(newObject.getName() != null ? newObject.getName() : "");
        realmObject.setSlug(newObject.getSlug() != null ? newObject.getSlug() : "");
        realmObject.setDescription(newObject.getDescription() != null ? newObject.getDescription() : "");
        realmObject.setImage(newObject.getImage() != null ? newObject.getImage() : "");
        return realmObject;
    }

    static CategoryRealmDb update(Realm realm, CategoryRealmDb realmObject, CategoryRealmDb newObject, Map<RealmObject, RealmObjectProxy> cache) {
        realmObject.setName(newObject.getName() != null ? newObject.getName() : "");
        realmObject.setSlug(newObject.getSlug() != null ? newObject.getSlug() : "");
        realmObject.setDescription(newObject.getDescription() != null ? newObject.getDescription() : "");
        realmObject.setImage(newObject.getImage() != null ? newObject.getImage() : "");
        return realmObject;
    }

    @Override
    public String toString() {
        if (!isValid()) {
            return "Invalid object";
        }
        StringBuilder stringBuilder = new StringBuilder("CategoryRealmDb = [");
        stringBuilder.append("{id:");
        stringBuilder.append(getId());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{name:");
        stringBuilder.append(getName());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{slug:");
        stringBuilder.append(getSlug());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{description:");
        stringBuilder.append(getDescription());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{image:");
        stringBuilder.append(getImage());
        stringBuilder.append("}");
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public int hashCode() {
        String realmName = realm.getPath();
        String tableName = row.getTable().getName();
        long rowIndex = row.getIndex();

        int result = 17;
        result = 31 * result + ((realmName != null) ? realmName.hashCode() : 0);
        result = 31 * result + ((tableName != null) ? tableName.hashCode() : 0);
        result = 31 * result + (int) (rowIndex ^ (rowIndex >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoryRealmDbRealmProxy aCategoryRealmDb = (CategoryRealmDbRealmProxy)o;

        String path = realm.getPath();
        String otherPath = aCategoryRealmDb.realm.getPath();
        if (path != null ? !path.equals(otherPath) : otherPath != null) return false;;

        String tableName = row.getTable().getName();
        String otherTableName = aCategoryRealmDb.row.getTable().getName();
        if (tableName != null ? !tableName.equals(otherTableName) : otherTableName != null) return false;

        if (row.getIndex() != aCategoryRealmDb.row.getIndex()) return false;

        return true;
    }

}
