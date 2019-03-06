package ir.ms.altest.db.product;


import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "products")
public class ProductModel {
    @PrimaryKey
    private long id;

    @Nullable
    private String name;

    @Nullable
    private long cat;

    @Nullable
    private String cat_name;

    @Ignore
    public ProductModel(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public ProductModel() {

    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof ProductModel && ((ProductModel) obj).getId() == getId();
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

    @Nullable
    public long getCat() {
        return cat;
    }

    public void setCat(@Nullable long cat) {
        this.cat = cat;
    }

    @Nullable
    public String getCat_name() {
        return cat_name;
    }

    public void setCat_name(@Nullable String cat_name) {
        this.cat_name = cat_name;
    }
}
