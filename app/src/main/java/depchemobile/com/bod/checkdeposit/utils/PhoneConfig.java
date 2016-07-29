package depchemobile.com.bod.checkdeposit.utils;
import android.app.Activity;

/**
 * Created by Rony Diaz on 23/02/16.
 */
public class PhoneConfig {

    private String _ip;
    private String _he;
    private String _wi;
    private String _us;

    public PhoneConfig(Activity activity) {
        set_ip(Utiles.getConfig_IP(activity));
        set_he(Utiles.getConfig_Height(activity));
        set_wi(Utiles.getConfig_Width(activity));
        set_us(Utiles.getConfig_userAgent(activity));
    }

    public String get_ip() {
        return _ip;
    }

    public void set_ip(String _ip) {
        this._ip = _ip;
    }

    public String get_he() {
        return _he;
    }

    public void set_he(String _he) {
        this._he = _he;
    }

    public String get_wi() {
        return _wi;
    }

    public void set_wi(String _wi) {
        this._wi = _wi;
    }

    public String get_us() {
        return _us;
    }

    public void set_us(String _us) {
        this._us = _us;
    }
}