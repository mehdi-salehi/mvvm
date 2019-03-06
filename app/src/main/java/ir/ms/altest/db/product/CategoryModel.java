package ir.ms.altest.db.product;


import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "cats")
public class CategoryModel {
    @PrimaryKey
    private long id;

    @Nullable
    private String name;

    @Ignore
    public CategoryModel(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public CategoryModel() {

    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof CategoryModel && ((CategoryModel) obj).getId() == getId();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Nullable
    public String getName() {
        return name;
    }

    public void setName(@Nullable String name) {
        this.name = name;
    }

}
