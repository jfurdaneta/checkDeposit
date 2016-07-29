package com.special.ResideMenu;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * User: special
 * Date: 13-12-10
 * Time: 下午11:05
 * Mail: specialcyci@gmail.com
 */
public class ResideMenuItem extends LinearLayout {

    /**
     * menu item  icon
     */
    private ImageView iv_icon;
    private TextView tv_title;
    private Boolean isSubmenu;
    private String titleString;
    private boolean eneable;

    public ResideMenuItem(Context context) {
        super(context);
        isSubmenu = false;
        initViews(context);
        eneable = true;
    }

    public ResideMenuItem(Context context, int icon, int title) {
        super(context);
        isSubmenu = false;
        initViews(context);
        iv_icon.setImageResource(icon);
        getTv_title().setText(title);
    }

    public ResideMenuItem(Context context, int icon, String title) {
        super(context);
        isSubmenu = false;
        initViews(context);
        iv_icon.setImageResource(icon);
        getTv_title().setText(title);

    }

    public ResideMenuItem(Context context, String title, Boolean subMenu) {
        super(context);
        isSubmenu = subMenu;
        initViews(context);
        iv_icon.setImageResource(-1);
        getTv_title().setText(title);

    }

    private void initViews(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (isSubmenu == false) {
            inflater.inflate(R.layout.residemenu_item, this);
        } else {
            inflater.inflate(R.layout.residemenu_submenu_item, this);
        }

        iv_icon = (ImageView) findViewById(R.id.iv_icon);
        setTv_title((TextView) findViewById(R.id.tv_title));

    }

    /**
     * set the icon color;
     *
     * @param icon
     */
    public void setIcon(int icon) {
        iv_icon.setImageResource(icon);
    }

    /**
     * set the title with resource
     * ;
     *
     * @param title
     */
    public void setTitle(int title) {
        getTv_title().setText(title);
    }

    /**
     * set the title with string;
     *
     * @param title
     */
    public void setTitle(String title) {
        getTv_title().setText(title);
    }

    public void setIsSubmenu(Boolean isSubmenu) {
        this.isSubmenu = isSubmenu;
    }

    /**
     * menu item  title
     */
    public TextView getTv_title() {
        return tv_title;
    }

    public void setTv_title(TextView tv_title) {
        this.tv_title = tv_title;
    }

    public String getTitleString() {
        return titleString;
    }

    public void setTitleString(String titleString) {
        this.titleString = titleString;
    }

    public boolean isEneable() {
        return eneable;
    }

    public void setEneable(boolean eneable) {
        this.eneable = eneable;
    }
}
