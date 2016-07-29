package depchemobile.com.bod.checkdeposit.utils;

/**
 * Created by Rony Diaz on 08/03/16.
 */
public class AndroidDeploy {

    private String fechaDeploy;
    private String versionSVN;
    private String obervaciones;

    public AndroidDeploy() {

    }


    public String getFechaDeploy() {
        return fechaDeploy;
    }

    public void setFechaDeploy(String fechaDeploy) {
        this.fechaDeploy = fechaDeploy;
    }

    public String getVersionSVN() {
        return versionSVN;
    }

    public void setVersionSVN(String versionSVN) {
        this.versionSVN = versionSVN;
    }

    public String getObervaciones() {
        return obervaciones;
    }

    public void setObervaciones(String obervaciones) {
        this.obervaciones = obervaciones;
    }
}
