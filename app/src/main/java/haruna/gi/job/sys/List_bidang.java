package haruna.gi.job.sys;

public class List_bidang {
    private String id_bidang, urai;

    public List_bidang() {
    }

    public List_bidang(String id_bidang, String urai) {
        this.id_bidang = id_bidang;
        this.urai = urai;
    }

    public String getId() {
        return id_bidang;
    }

    public void setId(String id_bidang) {
        this.id_bidang = id_bidang;
    }

    public String getUrai() {
        return urai;
    }

    public void setNama(String urai) {
        this.urai = urai;
    }
}
