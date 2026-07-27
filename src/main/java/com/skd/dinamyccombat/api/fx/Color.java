package com.skd.dinamyccombat.api.fx;

public record Color(float r, float g, float b, float a) {

    public static final Color WHITE = new Color(1.0F, 1.0F, 1.0F, 1.0F);
    public static final Color BLACK = new Color(0.0F, 0.0F, 0.0F, 1.0F);
    public static final Color RED = new Color(1.0F, 0.0F, 0.0F, 1.0F);
    public static final Color GREEN = new Color(0.0F, 1.0F, 0.0F, 1.0F);
    public static final Color BLUE = new Color(0.0F, 0.0F, 1.0F, 1.0F);

    public Color(float r, float g, float b) {
        this(r, g, b, 1.0F);
    }

    public static Color fromRGB(int red, int green, int blue) {
        return new Color(red / 255.0F, green / 255.0F, blue / 255.0F);
    }

    public static Color fromRGBA(int red, int green, int blue, int alpha) {
        return new Color(red / 255.0F, green / 255.0F, blue / 255.0F, alpha / 255.0F);
    }

    public static Color fromARGB(int argb) {
        int a = (argb >> 24) & 0xFF;
        int r = (argb >> 16) & 0xFF;
        int g = (argb >> 8) & 0xFF;
        int b = argb & 0xFF;
        return fromRGBA(r, g, b, a);
    }

    public int toARGB() {
        int alpha = Math.round(a * 255.0F);
        int red = Math.round(r * 255.0F);
        int green = Math.round(g * 255.0F);
        int blue = Math.round(b * 255.0F);
        return (alpha << 24) | (red << 16) | (green << 8) | blue;
    }

    public int toRGBA() {
        int red = Math.round(r * 255.0F);
        int green = Math.round(g * 255.0F);
        int blue = Math.round(b * 255.0F);
        int alpha = Math.round(a * 255.0F);
        return (red << 24) | (green << 16) | (blue << 8) | alpha;
    }
}
