package com.dreslab.www.drethan_newapp.Helperfiles;

/**
 * Created by Michael on 7/26/2016.
 */
public class NavigateTools {
    private String toolName,description;
    private int resIcon;

    public NavigateTools(String toolName, String description, int resIcon) {
        this.toolName = toolName;
        this.description = description;
        this.resIcon = resIcon;
    }

    public String getToolName() {
        return toolName;
    }

    public String getDescription() {
        return description;
    }

    public int getResIcon() {
        return resIcon;
    }

    public void setToolName(String toolName) {
        this.toolName = toolName;
    }

    public void setResIcon(int resIcon) {
        this.resIcon = resIcon;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
