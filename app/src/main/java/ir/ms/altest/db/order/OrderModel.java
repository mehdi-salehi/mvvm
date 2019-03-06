package ir.ms.altest.db.order;


import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "orders")
public class OrderModel {
    @PrimaryKey(autoGenerate = true)
    private long id;

    @Nullable
    private long pid;

    @Nullable
    private String name;

    @Nullable
    private String lat;

    @Nullable
    private String lng;

    @Nullable
    private int status;

    public OrderModel() {

    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof OrderModel && ((OrderModel) obj).getId() == getId();
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
    public String getLat() {
        return lat;
    }

    public void setLat(@Nullable String lat) {
        this.lat = lat;
    }

    @Nullable
    public String getLng() {
        return lng;
    }

    public void setLng(@Nullable String lng) {
        this.lng = lng;
    }

    @Nullable
    public int getStatus() {
        return status;
    }

    public void setStatus(@Nullable int status) {
        this.status = status;
    }

    @Nullable
    public long getPid() {
        return pid;
    }

    public void setPid(@Nullable long pid) {
        this.pid = pid;
    }
}
