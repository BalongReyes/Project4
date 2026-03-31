package FrameSystem.SLibrary.Div;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Path2D;
import java.beans.BeanProperty;
import java.beans.JavaBean;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import EventSystem.Interface.InnerListener; // Added InnerListener Import

/**
 * SDiv - A CSS div-like JPanel that supports the full CSS box model
 * via Java Beans getters/setters, ready for the NetBeans Form Editor.
 *
 * Supported CSS concepts:
 * Box Model  : paddingTop/Right/Bottom/Left
 * Background : background (inherited), backgroundOpacity, hoverBackground
 * Border     : per-side width, color, style (SOLID/DASHED/DOTTED/NONE), hoverBorderColor
 * Radius     : per-corner borderTopLeftRadius, etc.
 * Box Shadow : offset, blur, spread, color, opacity
 * Display    : BLOCK (null layout) or FLEX (built-in FlexLayout)
 * Flex       : flexDirection, justifyContent, alignItems, flexWrap, gap, rowGap, columnGap
 * Overflow   : VISIBLE or HIDDEN (clips children; respects border-radius)
 * Opacity    : overall component opacity
 * Hover      : hoverEnabled, hoverBackground, hoverBorderColor
 */
@JavaBean(description = "A CSS div-like JPanel with full styling support")
public class SDiv extends JPanel implements InnerListener { // Implemented InnerListener

// ======================================================================================================================
// Enums
// ======================================================================================================================

    public enum Display        { BLOCK, FLEX }
    public enum FlexDirection  { ROW, COLUMN, ROW_REVERSE, COLUMN_REVERSE }
    public enum JustifyContent { FLEX_START, FLEX_END, CENTER, SPACE_BETWEEN, SPACE_AROUND, SPACE_EVENLY }
    public enum AlignItems     { FLEX_START, FLEX_END, CENTER, STRETCH }
    public enum FlexWrap       { NOWRAP, WRAP }
    public enum Overflow       { VISIBLE, HIDDEN }
    public enum BorderStyle    { NONE, SOLID, DASHED, DOTTED }

// ======================================================================================================================
// Fields
// ======================================================================================================================

    // Padding
    private int paddingTop    = 0;
    private int paddingRight  = 0;
    private int paddingBottom = 0;
    private int paddingLeft   = 0;

    // Background
    private float backgroundOpacity = 1f;

    // Border Radius (per corner)
    private int borderTopLeftRadius     = 0;
    private int borderTopRightRadius    = 0;
    private int borderBottomRightRadius = 0;
    private int borderBottomLeftRadius  = 0;

    // Border Width (per side)
    private int borderTopWidth    = 0;
    private int borderRightWidth  = 0;
    private int borderBottomWidth = 0;
    private int borderLeftWidth   = 0;

    // Border Color (per side)
    private Color borderTopColor    = Color.BLACK;
    private Color borderRightColor  = Color.BLACK;
    private Color borderBottomColor = Color.BLACK;
    private Color borderLeftColor   = Color.BLACK;

    // Border Style (per side)
    private BorderStyle borderTopStyle    = BorderStyle.SOLID;
    private BorderStyle borderRightStyle  = BorderStyle.SOLID;
    private BorderStyle borderBottomStyle = BorderStyle.SOLID;
    private BorderStyle borderLeftStyle   = BorderStyle.SOLID;

    // Box Shadow
    private boolean boxShadow        = false;
    private int     boxShadowOffsetX = 0;
    private int     boxShadowOffsetY = 4;
    private int     boxShadowBlur    = 8;
    private int     boxShadowSpread  = 0;
    private Color   boxShadowColor   = Color.BLACK;
    private float   boxShadowOpacity = 0.25f;

    // Display / Flex
    private Display        display        = Display.BLOCK;
    private FlexDirection  flexDirection  = FlexDirection.ROW;
    private JustifyContent justifyContent = JustifyContent.FLEX_START;
    private AlignItems     alignItems     = AlignItems.FLEX_START;
    private FlexWrap       flexWrap       = FlexWrap.NOWRAP;
    private int            gap            = 0;
    private int            rowGap         = -1;   // -1 = fall back to gap
    private int            columnGap      = -1;   // -1 = fall back to gap

    // Overflow
    private Overflow overflow  = Overflow.VISIBLE;
    private Overflow overflowX = Overflow.VISIBLE;
    private Overflow overflowY = Overflow.VISIBLE;

    // Opacity
    private float opacity = 1f;
    
    // Hover State
    private boolean hoverEnabled = false;
    private boolean isHovered = false;
    private Color hoverBackground = null;
    private Color hoverBorderColor = null;

    // We store the hover listener so we can attach it to children added dynamically
    private MouseAdapter hoverAdapter;

    // Computed shadow insets (updated by syncInsets)
    private int shadowInsetTop    = 0;
    private int shadowInsetRight  = 0;
    private int shadowInsetBottom = 0;
    private int shadowInsetLeft   = 0;

// ======================================================================================================================
// Constructor
// ======================================================================================================================

    public SDiv() {
        super(null);       // null layout = BLOCK mode
        setOpaque(false);  // we paint ourselves
        syncInsets();
        
        // Hover Listener implementation
        hoverAdapter = new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (hoverEnabled) {
                    isHovered = true;
                    if (getCursor().getType() == Cursor.DEFAULT_CURSOR) {
                        setCursor(new Cursor(Cursor.HAND_CURSOR));
                    }
                    repaint();
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (hoverEnabled) {
                    // Make sure the mouse actually left the boundaries of this component and its children
                    if (!contains(e.getPoint())) {
                        isHovered = false;
                        if (getCursor().getType() == Cursor.HAND_CURSOR) {
                            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                        }
                        repaint();
                    }
                }
            }
        };

        // Attach listener using InnerListener method
        addInnerListeners(hoverAdapter);
    }

// ======================================================================================================================
// InnerListener Implementation
// ======================================================================================================================

    @Override
    public void addInnerListeners(MouseListener listener) {
        super.addMouseListener(listener);
        synchronized(getTreeLock()) {
            int i = getComponentCount() - 1;
            if(i < 0) return;
            for(; i >= 0; i--) {
                Component c = getComponent(i);
                if(c == null) continue;
                if(c instanceof InnerListener innerListener) {
                    innerListener.addInnerListeners(listener);
                } else {
                    c.addMouseListener(listener);
                }
            }
        }
    }

    /** * Override addImpl to ensure dynamically added children also get the hover listener.
     */
    @Override
    protected void addImpl(Component comp, Object constraints, int index) {
        super.addImpl(comp, constraints, index);
        if (hoverAdapter != null) {
            if (comp instanceof InnerListener innerListener) {
                innerListener.addInnerListeners(hoverAdapter);
            } else {
                comp.addMouseListener(hoverAdapter);
            }
        }
    }

// ======================================================================================================================
// Hover Setters & Getters
// ======================================================================================================================

    @BeanProperty(preferred = true, visualUpdate = true, description = "Enable hover interactions")
    public void setHoverEnabled(boolean v) { this.hoverEnabled = v; }
    public boolean isHoverEnabled() { return hoverEnabled; }

    @BeanProperty(preferred = true, visualUpdate = true, description = "Background color on hover")
    public void setHoverBackground(Color v) { this.hoverBackground = v; repaint(); }
    public Color getHoverBackground() { return hoverBackground; }

    @BeanProperty(preferred = true, visualUpdate = true, description = "Border color on hover (applies to all sides)")
    public void setHoverBorderColor(Color v) { this.hoverBorderColor = v; repaint(); }
    public Color getHoverBorderColor() { return hoverBorderColor; }

// ======================================================================================================================
// Shorthand Setters  (mirrors CSS shorthand properties)
// ======================================================================================================================

    /** Sets all four padding sides to the same value. */
    @BeanProperty(preferred = true, visualUpdate = true, description = "Sets all padding sides equally")
    public void setPadding(int padding) {
        this.paddingTop = this.paddingRight = this.paddingBottom = this.paddingLeft = padding;
        syncInsets();
    }
    public int getPadding() { return paddingTop; }

    // ---

    /** Sets all four corner radii to the same value. */
    @BeanProperty(preferred = true, visualUpdate = true, description = "Sets all border-radius corners equally")
    public void setBorderRadius(int radius) {
        this.borderTopLeftRadius = this.borderTopRightRadius =
        this.borderBottomRightRadius = this.borderBottomLeftRadius = radius;
        repaint();
    }
    public int getBorderRadius() { return borderTopLeftRadius; }

    // ---

    /** Sets all four border widths to the same value. */
    @BeanProperty(preferred = true, visualUpdate = true, description = "Sets all border widths equally")
    public void setBorderWidth(int width) {
        this.borderTopWidth = this.borderRightWidth =
        this.borderBottomWidth = this.borderLeftWidth = width;
        repaint();
    }
    public int getBorderWidth() { return borderTopWidth; }

    // ---

    /** Sets all four border colors to the same value. */
    @BeanProperty(preferred = true, visualUpdate = true, description = "Sets all border colors equally")
    public void setBorderColor(Color color) {
        this.borderTopColor = this.borderRightColor =
        this.borderBottomColor = this.borderLeftColor = color;
        repaint();
    }
    public Color getBorderColor() { return borderTopColor; }

    // ---

    /** Sets all four border styles to the same value. */
    @BeanProperty(preferred = true, visualUpdate = true, description = "Sets all border styles equally")
    public void setBorderStyleAll(BorderStyle style) {
        this.borderTopStyle = this.borderRightStyle =
        this.borderBottomStyle = this.borderLeftStyle = style;
        repaint();
    }
    public BorderStyle getBorderStyleAll() { return borderTopStyle; }

// ======================================================================================================================
// Padding
// ======================================================================================================================

    @BeanProperty(preferred = true, visualUpdate = true, description = "Top padding (px)")
    public void setPaddingTop(int v)    { this.paddingTop    = v; syncInsets(); }
    public int getPaddingTop()          { return paddingTop; }

    @BeanProperty(preferred = true, visualUpdate = true, description = "Right padding (px)")
    public void setPaddingRight(int v)  { this.paddingRight  = v; syncInsets(); }
    public int getPaddingRight()        { return paddingRight; }

    @BeanProperty(preferred = true, visualUpdate = true, description = "Bottom padding (px)")
    public void setPaddingBottom(int v) { this.paddingBottom = v; syncInsets(); }
    public int getPaddingBottom()       { return paddingBottom; }

    @BeanProperty(preferred = true, visualUpdate = true, description = "Left padding (px)")
    public void setPaddingLeft(int v)   { this.paddingLeft   = v; syncInsets(); }
    public int getPaddingLeft()         { return paddingLeft; }

// ======================================================================================================================
// Background
// ======================================================================================================================

    @Override
    @BeanProperty(preferred = true, visualUpdate = true, description = "Background fill color")
    public void setBackground(Color bg) { super.setBackground(bg); }

    @BeanProperty(preferred = true, visualUpdate = true, description = "Background opacity 0.0 – 1.0")
    public void setBackgroundOpacity(float v) {
        this.backgroundOpacity = clamp01(v);
        repaint();
    }
    public float getBackgroundOpacity() { return backgroundOpacity; }

// ======================================================================================================================
// Border Radius
// ======================================================================================================================

    @BeanProperty(preferred = true, visualUpdate = true, description = "Top-left corner radius (px)")
    public void setBorderTopLeftRadius(int v)     { this.borderTopLeftRadius     = v; repaint(); }
    public int getBorderTopLeftRadius()            { return borderTopLeftRadius; }

    @BeanProperty(preferred = true, visualUpdate = true, description = "Top-right corner radius (px)")
    public void setBorderTopRightRadius(int v)    { this.borderTopRightRadius    = v; repaint(); }
    public int getBorderTopRightRadius()           { return borderTopRightRadius; }

    @BeanProperty(preferred = true, visualUpdate = true, description = "Bottom-right corner radius (px)")
    public void setBorderBottomRightRadius(int v) { this.borderBottomRightRadius = v; repaint(); }
    public int getBorderBottomRightRadius()        { return borderBottomRightRadius; }

    @BeanProperty(preferred = true, visualUpdate = true, description = "Bottom-left corner radius (px)")
    public void setBorderBottomLeftRadius(int v)  { this.borderBottomLeftRadius  = v; repaint(); }
    public int getBorderBottomLeftRadius()         { return borderBottomLeftRadius; }

// ======================================================================================================================
// Border — Top
// ======================================================================================================================

    @BeanProperty(preferred = true, visualUpdate = true, description = "Top border width (px)")
    public void setBorderTopWidth(int v)         { this.borderTopWidth = v; repaint(); }
    public int getBorderTopWidth()               { return borderTopWidth; }

    @BeanProperty(preferred = true, visualUpdate = true, description = "Top border color")
    public void setBorderTopColor(Color v)       { this.borderTopColor = v; repaint(); }
    public Color getBorderTopColor()             { return borderTopColor; }

    @BeanProperty(preferred = true, visualUpdate = true, description = "Top border style")
    public void setBorderTopStyle(BorderStyle v) { this.borderTopStyle = v; repaint(); }
    public BorderStyle getBorderTopStyle()       { return borderTopStyle; }

// ======================================================================================================================
// Border — Right
// ======================================================================================================================

    @BeanProperty(preferred = true, visualUpdate = true, description = "Right border width (px)")
    public void setBorderRightWidth(int v)         { this.borderRightWidth = v; repaint(); }
    public int getBorderRightWidth()               { return borderRightWidth; }

    @BeanProperty(preferred = true, visualUpdate = true, description = "Right border color")
    public void setBorderRightColor(Color v)       { this.borderRightColor = v; repaint(); }
    public Color getBorderRightColor()             { return borderRightColor; }

    @BeanProperty(preferred = true, visualUpdate = true, description = "Right border style")
    public void setBorderRightStyle(BorderStyle v) { this.borderRightStyle = v; repaint(); }
    public BorderStyle getBorderRightStyle()       { return borderRightStyle; }

// ======================================================================================================================
// Border — Bottom
// ======================================================================================================================

    @BeanProperty(preferred = true, visualUpdate = true, description = "Bottom border width (px)")
    public void setBorderBottomWidth(int v)         { this.borderBottomWidth = v; repaint(); }
    public int getBorderBottomWidth()               { return borderBottomWidth; }

    @BeanProperty(preferred = true, visualUpdate = true, description = "Bottom border color")
    public void setBorderBottomColor(Color v)       { this.borderBottomColor = v; repaint(); }
    public Color getBorderBottomColor()             { return borderBottomColor; }

    @BeanProperty(preferred = true, visualUpdate = true, description = "Bottom border style")
    public void setBorderBottomStyle(BorderStyle v) { this.borderBottomStyle = v; repaint(); }
    public BorderStyle getBorderBottomStyle()       { return borderBottomStyle; }

// ======================================================================================================================
// Border — Left
// ======================================================================================================================

    @BeanProperty(preferred = true, visualUpdate = true, description = "Left border width (px)")
    public void setBorderLeftWidth(int v)         { this.borderLeftWidth = v; repaint(); }
    public int getBorderLeftWidth()               { return borderLeftWidth; }

    @BeanProperty(preferred = true, visualUpdate = true, description = "Left border color")
    public void setBorderLeftColor(Color v)       { this.borderLeftColor = v; repaint(); }
    public Color getBorderLeftColor()             { return borderLeftColor; }

    @BeanProperty(preferred = true, visualUpdate = true, description = "Left border style")
    public void setBorderLeftStyle(BorderStyle v) { this.borderLeftStyle = v; repaint(); }
    public BorderStyle getBorderLeftStyle()       { return borderLeftStyle; }

// ======================================================================================================================
// Box Shadow
// ======================================================================================================================

    @BeanProperty(preferred = true, visualUpdate = true, description = "Enable drop shadow")
    public void setBoxShadow(boolean v) {
        this.boxShadow = v;
        syncInsets();
    }
    public boolean isBoxShadow() { return boxShadow; }

    @BeanProperty(preferred = true, visualUpdate = true, description = "Shadow horizontal offset (px, can be negative)")
    public void setBoxShadowOffsetX(int v) { this.boxShadowOffsetX = v; syncInsets(); }
    public int getBoxShadowOffsetX()        { return boxShadowOffsetX; }

    @BeanProperty(preferred = true, visualUpdate = true, description = "Shadow vertical offset (px, can be negative)")
    public void setBoxShadowOffsetY(int v) { this.boxShadowOffsetY = v; syncInsets(); }
    public int getBoxShadowOffsetY()        { return boxShadowOffsetY; }

    @BeanProperty(preferred = true, visualUpdate = true, description = "Shadow blur radius (px, >= 0)")
    public void setBoxShadowBlur(int v) { this.boxShadowBlur = Math.max(0, v); syncInsets(); }
    public int getBoxShadowBlur()        { return boxShadowBlur; }

    @BeanProperty(preferred = true, visualUpdate = true, description = "Shadow spread radius (px, can be negative)")
    public void setBoxShadowSpread(int v) { this.boxShadowSpread = v; syncInsets(); }
    public int getBoxShadowSpread()        { return boxShadowSpread; }

    @BeanProperty(preferred = true, visualUpdate = true, description = "Shadow color")
    public void setBoxShadowColor(Color v) { this.boxShadowColor = v; repaint(); }
    public Color getBoxShadowColor()        { return boxShadowColor; }

    @BeanProperty(preferred = true, visualUpdate = true, description = "Shadow opacity 0.0 – 1.0")
    public void setBoxShadowOpacity(float v) { this.boxShadowOpacity = clamp01(v); repaint(); }
    public float getBoxShadowOpacity()        { return boxShadowOpacity; }

// ======================================================================================================================
// Display
// ======================================================================================================================

    @BeanProperty(preferred = true, visualUpdate = true, description = "BLOCK = null layout, FLEX = flex layout")
    public void setDisplay(Display v) {
        this.display = v;
        updateLayout();
    }
    public Display getDisplay() { return display; }

// ======================================================================================================================
// Flex
// ======================================================================================================================

    @BeanProperty(preferred = true, visualUpdate = true, description = "Direction of the flex main axis")
    public void setFlexDirection(FlexDirection v) {
        this.flexDirection = v;
        if (display == Display.FLEX) revalidate();
    }
    public FlexDirection getFlexDirection() { return flexDirection; }

    @BeanProperty(preferred = true, visualUpdate = true, description = "Distributes space along the main axis (justify-content)")
    public void setJustifyContent(JustifyContent v) {
        this.justifyContent = v;
        if (display == Display.FLEX) revalidate();
    }
    public JustifyContent getJustifyContent() { return justifyContent; }

    @BeanProperty(preferred = true, visualUpdate = true, description = "Aligns children on the cross axis (align-items)")
    public void setAlignItems(AlignItems v) {
        this.alignItems = v;
        if (display == Display.FLEX) revalidate();
    }
    public AlignItems getAlignItems() { return alignItems; }

    @BeanProperty(preferred = true, visualUpdate = true, description = "Controls whether children wrap onto multiple lines")
    public void setFlexWrap(FlexWrap v) {
        this.flexWrap = v;
        if (display == Display.FLEX) revalidate();
    }
    public FlexWrap getFlexWrap() { return flexWrap; }

    @BeanProperty(preferred = true, visualUpdate = true, description = "Gap between flex items (px) — applies to both axes unless overridden")
    public void setGap(int v) { this.gap = v; if (display == Display.FLEX) revalidate(); }
    public int getGap()        { return gap; }

    @BeanProperty(preferred = true, visualUpdate = true, description = "Row gap override (px). Set to -1 to inherit from gap")
    public void setRowGap(int v) { this.rowGap = v; if (display == Display.FLEX) revalidate(); }
    public int getRowGap()        { return rowGap; }

    @BeanProperty(preferred = true, visualUpdate = true, description = "Column gap override (px). Set to -1 to inherit from gap")
    public void setColumnGap(int v) { this.columnGap = v; if (display == Display.FLEX) revalidate(); }
    public int getColumnGap()        { return columnGap; }

// ======================================================================================================================
// Overflow
// ======================================================================================================================

    @BeanProperty(preferred = true, visualUpdate = true, description = "Overflow on both axes")
    public void setOverflow(Overflow v) { this.overflow = v; repaint(); }
    public Overflow getOverflow()        { return overflow; }

    @BeanProperty(preferred = true, visualUpdate = true, description = "Horizontal overflow")
    public void setOverflowX(Overflow v) { this.overflowX = v; repaint(); }
    public Overflow getOverflowX()        { return overflowX; }

    @BeanProperty(preferred = true, visualUpdate = true, description = "Vertical overflow")
    public void setOverflowY(Overflow v) { this.overflowY = v; repaint(); }
    public Overflow getOverflowY()        { return overflowY; }

// ======================================================================================================================
// Opacity
// ======================================================================================================================

    @BeanProperty(preferred = true, visualUpdate = true, description = "Component opacity 0.0 – 1.0")
    public void setOpacity(float v) { this.opacity = clamp01(v); repaint(); }
    public float getOpacity()        { return opacity; }

// ======================================================================================================================
// Hidden overrides — keeps NetBeans property sheet clean
// ======================================================================================================================

    @Override
    @BeanProperty(hidden = true)
    public void setBorder(javax.swing.border.Border border) {
        // Intentionally blocked — border insets are managed by syncInsets()
    }

    @Override
    @BeanProperty(hidden = true)
    public void setLayout(java.awt.LayoutManager mgr) {
        // Intentionally blocked — layout is managed by setDisplay()
    }

    @Override
    @BeanProperty(hidden = true)
    public void setOpaque(boolean opaque) {
        super.setOpaque(opaque);
    }

    @Override
    @BeanProperty(hidden = true)
    public void setName(String name) { super.setName(name); }

    @Override
    @BeanProperty(hidden = true)
    public void setToolTipText(String text) { super.setToolTipText(text); }

// ======================================================================================================================
// Internal helpers
// ======================================================================================================================

    /**
     * Recomputes EmptyBorder insets that reserve space for shadow bleed + user padding.
     * Must be called any time shadow params or padding values change.
     */
    private void syncInsets() {
        if (boxShadow) {
            int extent = boxShadowBlur + Math.max(0, boxShadowSpread);
            shadowInsetTop    = Math.max(0, extent - boxShadowOffsetY);
            shadowInsetBottom = Math.max(0, extent + boxShadowOffsetY);
            shadowInsetLeft   = Math.max(0, extent - boxShadowOffsetX);
            shadowInsetRight  = Math.max(0, extent + boxShadowOffsetX);
        } else {
            shadowInsetTop = shadowInsetRight = shadowInsetBottom = shadowInsetLeft = 0;
        }

        super.setBorder(new EmptyBorder(
            shadowInsetTop    + paddingTop,
            shadowInsetLeft   + paddingLeft,
            shadowInsetBottom + paddingBottom,
            shadowInsetRight  + paddingRight
        ));
        revalidate();
        repaint();
    }

    /** Swaps the layout manager based on the current display value. */
    private void updateLayout() {
        if (display == Display.FLEX) {
            super.setLayout(new DivFlexLayout());
        } else {
            super.setLayout(null);
        }
        revalidate();
        repaint();
    }

    private float clamp01(float v) { return Math.max(0f, Math.min(1f, v)); }
    private int   clamp255(float v){ return Math.max(0, Math.min(255, (int)(v * 255))); }

// ======================================================================================================================
// Painting
// ======================================================================================================================

    /**
     * Paints background and borders inside the shadow-inset region.
     * Shadow itself is also drawn here, before the background, so it appears behind.
     */
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        try {
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,   RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_RENDERING,      RenderingHints.VALUE_RENDER_QUALITY);
            g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);

            if (opacity < 1f) {
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
            }

            // Panel body rect — inside shadow inset, does NOT account for padding
            int x = shadowInsetLeft;
            int y = shadowInsetTop;
            int w = getWidth()  - shadowInsetLeft - shadowInsetRight;
            int h = getHeight() - shadowInsetTop  - shadowInsetBottom;

            if (w <= 0 || h <= 0) return;

            // Draw box shadow first so it sits behind the background
            if (boxShadow) paintBoxShadow(g2, x, y, w, h);

            // Apply Hover Background
            Color bg = (isHovered && hoverBackground != null) ? hoverBackground : getBackground();
            if (bg != null && backgroundOpacity > 0f) {
                g2.setColor(new Color(bg.getRed(), bg.getGreen(), bg.getBlue(), clamp255(backgroundOpacity)));
                g2.fill(buildRoundedRect(x, y, w, h,
                    borderTopLeftRadius, borderTopRightRadius,
                    borderBottomRightRadius, borderBottomLeftRadius));
            }

            // Borders
            paintBorders(g2, x, y, w, h);

        } finally {
            g2.dispose();
        }
    }

    /**
     * Clips children to the inner content area when overflow is HIDDEN.
     * This preserves the shadow and background from being clipped.
     */
    @Override
    protected void paintChildren(Graphics g) {
        boolean clipNeeded = overflow  == Overflow.HIDDEN
                          || overflowX == Overflow.HIDDEN
                          || overflowY == Overflow.HIDDEN;

        if (!clipNeeded) {
            super.paintChildren(g);
            return;
        }

        Graphics2D g2   = (Graphics2D) g;
        Shape savedClip = g2.getClip();

        // Inner content rect (inside shadow insets AND border widths)
        int bT = borderTopWidth, bR = borderRightWidth, bB = borderBottomWidth, bL = borderLeftWidth;
        int cx = shadowInsetLeft + bL;
        int cy = shadowInsetTop  + bT;
        int cw = getWidth()  - shadowInsetLeft - shadowInsetRight  - bL - bR;
        int ch = getHeight() - shadowInsetTop  - shadowInsetBottom - bT - bB;

        if (overflow == Overflow.HIDDEN) {
            // Clip to rounded content rect
            g2.clip(buildRoundedRect(cx, cy, cw, ch,
                Math.max(0, borderTopLeftRadius     - bL),
                Math.max(0, borderTopRightRadius    - bR),
                Math.max(0, borderBottomRightRadius - bR),
                Math.max(0, borderBottomLeftRadius  - bL)));
        } else {
            // Axis-specific clip
            int clipX = overflowX == Overflow.HIDDEN ? cx : 0;
            int clipY = overflowY == Overflow.HIDDEN ? cy : 0;
            int clipW = overflowX == Overflow.HIDDEN ? cw : getWidth();
            int clipH = overflowY == Overflow.HIDDEN ? ch : getHeight();
            g2.clip(new Rectangle(clipX, clipY, clipW, clipH));
        }

        super.paintChildren(g);
        g2.setClip(savedClip);
    }

// ======================================================================================================================
// Shadow painting
// ======================================================================================================================

    private void paintBoxShadow(Graphics2D g2, int cx, int cy, int cw, int ch) {
        int ox     = boxShadowOffsetX;
        int oy     = boxShadowOffsetY;
        int blur   = boxShadowBlur;
        int spread = boxShadowSpread;
        Color sc   = boxShadowColor;

        // Base shadow rect (offset + spread applied)
        int sx = cx + ox - spread;
        int sy = cy + oy - spread;
        int sw = cw + spread * 2;
        int sh = ch + spread * 2;

        // Draw blur layers from outermost (most transparent) to innermost (most opaque)
        for (int i = blur; i >= 0; i--) {
            float t     = blur == 0 ? 1f : (float)(blur - i) / blur;
            float alpha = boxShadowOpacity * t;
            if (alpha <= 0f) continue;

            g2.setColor(new Color(sc.getRed(), sc.getGreen(), sc.getBlue(), clamp255(alpha)));

            int expand = blur - i;
            g2.fill(buildRoundedRect(
                sx - expand, sy - expand,
                sw + expand * 2, sh + expand * 2,
                borderTopLeftRadius     + expand,
                borderTopRightRadius    + expand,
                borderBottomRightRadius + expand,
                borderBottomLeftRadius  + expand
            ));
        }
    }

// ======================================================================================================================
// Border painting
// ======================================================================================================================

    private void paintBorders(Graphics2D g2, int x, int y, int w, int h) {
        // Swap border colors if we are hovered and a hover border color is defined
        Color tColor = (isHovered && hoverBorderColor != null) ? hoverBorderColor : borderTopColor;
        Color rColor = (isHovered && hoverBorderColor != null) ? hoverBorderColor : borderRightColor;
        Color bColor = (isHovered && hoverBorderColor != null) ? hoverBorderColor : borderBottomColor;
        Color lColor = (isHovered && hoverBorderColor != null) ? hoverBorderColor : borderLeftColor;

        if (borderTopWidth    > 0 && borderTopStyle    != BorderStyle.NONE)
            paintSide(g2, x, y, w, h, Side.TOP,    borderTopWidth,    tColor, borderTopStyle);
        if (borderRightWidth  > 0 && borderRightStyle  != BorderStyle.NONE)
            paintSide(g2, x, y, w, h, Side.RIGHT,  borderRightWidth,  rColor, borderRightStyle);
        if (borderBottomWidth > 0 && borderBottomStyle != BorderStyle.NONE)
            paintSide(g2, x, y, w, h, Side.BOTTOM, borderBottomWidth, bColor, borderBottomStyle);
        if (borderLeftWidth   > 0 && borderLeftStyle   != BorderStyle.NONE)
            paintSide(g2, x, y, w, h, Side.LEFT,   borderLeftWidth,   lColor, borderLeftStyle);
    }

    private enum Side { TOP, RIGHT, BOTTOM, LEFT }

    /**
     * Paints one border side. We:
     * 1. Clip to the half-panel toward that side so each side's color stays on its own edge.
     * 2. Configure a Stroke matching the requested style (solid / dashed / dotted).
     * 3. Draw the full rounded outline inset by half the stroke width.
     */
    private void paintSide(Graphics2D g2, int x, int y, int w, int h,
                            Side side, int bw, Color bc, BorderStyle bs) {

        Shape savedClip = g2.getClip();

        // Clip region for this side
        Shape sideClip = switch (side) {
            case TOP    -> new Rectangle(x,                y,               w,          h / 2 + bw);
            case BOTTOM -> new Rectangle(x,                y + h / 2 - bw, w,          h / 2 + bw);
            case LEFT   -> new Rectangle(x,                y,               w / 2 + bw, h          );
            case RIGHT  -> new Rectangle(x + w / 2 - bw,  y,               w / 2 + bw, h          );
        };
        g2.clip(sideClip);

        // Build stroke
        float bwf  = bw;
        float[] dash = switch (bs) {
            case DASHED -> new float[]{ bwf * 3f, bwf * 2f };
            case DOTTED -> new float[]{ bwf,       bwf      };
            default     -> null;
        };

        Stroke prevStroke = g2.getStroke();
        g2.setStroke(dash != null
            ? new BasicStroke(bwf, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 0, dash, 0)
            : new BasicStroke(bwf, BasicStroke.CAP_BUTT,  BasicStroke.JOIN_MITER));

        // Draw outline inset by half the stroke width so the stroke sits on the body edge
        float half = bwf / 2f;
        int r = (int) half;
        g2.setColor(bc);
        g2.draw(buildRoundedRectF(
            x + half, y + half, w - bwf, h - bwf,
            Math.max(0, borderTopLeftRadius     - r),
            Math.max(0, borderTopRightRadius    - r),
            Math.max(0, borderBottomRightRadius - r),
            Math.max(0, borderBottomLeftRadius  - r)
        ));

        g2.setStroke(prevStroke);
        g2.setClip(savedClip);
    }

// ======================================================================================================================
// Geometry helpers
// ======================================================================================================================

    /** Builds a rounded rect shape with per-corner radii (integer coords). */
    private Shape buildRoundedRect(int x, int y, int w, int h, int tl, int tr, int br, int bl) {
        if (tl == 0 && tr == 0 && br == 0 && bl == 0) {
            return new Rectangle(x, y, w, h);
        }
        return buildRoundedRectF(x, y, w, h, tl, tr, br, bl);
    }

    /** Builds a rounded rect shape with per-corner radii (float coords). */
    private Shape buildRoundedRectF(float x, float y, float w, float h, int tl, int tr, int br, int bl) {
        Path2D.Float p = new Path2D.Float();

        // Top edge — left to right
        p.moveTo(x + tl, y);
        p.lineTo(x + w - tr, y);
        if (tr > 0) p.quadTo(x + w,     y,       x + w,     y + tr);
        else        p.lineTo( x + w,     y);

        // Right edge — top to bottom
        p.lineTo(x + w, y + h - br);
        if (br > 0) p.quadTo(x + w,     y + h,   x + w - br, y + h);
        else        p.lineTo( x + w,     y + h);

        // Bottom edge — right to left
        p.lineTo(x + bl, y + h);
        if (bl > 0) p.quadTo(x,         y + h,   x,          y + h - bl);
        else        p.lineTo( x,         y + h);

        // Left edge — bottom to top
        p.lineTo(x, y + tl);
        if (tl > 0) p.quadTo(x,         y,        x + tl,    y);
        else        p.lineTo( x,         y);

        p.closePath();
        return p;
    }

// ======================================================================================================================
// Flex Layout — inner class
// ======================================================================================================================

    /**
     * A minimal Flexbox layout manager.
     */
    private class DivFlexLayout implements LayoutManager2 {

        // ---- Layout ---------------------------------------------------------------------------------

        @Override
        public void layoutContainer(Container parent) {
            List<Component> children = visibleChildren(parent);
            if (children.isEmpty()) return;

            Insets ins = parent.getInsets();
            int availW = parent.getWidth()  - ins.left - ins.right;
            int availH = parent.getHeight() - ins.top  - ins.bottom;
            if (availW <= 0 || availH <= 0) return;

            boolean isRow     = flexDirection == FlexDirection.ROW     || flexDirection == FlexDirection.ROW_REVERSE;
            boolean isReverse = flexDirection == FlexDirection.ROW_REVERSE || flexDirection == FlexDirection.COLUMN_REVERSE;

            int effColGap = columnGap >= 0 ? columnGap : gap;
            int effRowGap = rowGap    >= 0 ? rowGap    : gap;
            int mainGap   = isRow ? effColGap : effRowGap;
            int crossGap  = isRow ? effRowGap : effColGap;

            int mainAvail  = isRow ? availW : availH;
            int crossAvail = isRow ? availH : availW;

            List<List<Component>> lines;
            if (flexWrap == FlexWrap.WRAP) {
                lines = wrapIntoLines(children, mainAvail, mainGap, isRow);
            } else {
                lines = List.of(isReverse ? reversed(children) : children);
            }

            layoutLines(lines, ins.left, ins.top, mainAvail, crossAvail, mainGap, crossGap, isRow, isReverse);
        }

        // ---- Line wrapping --------------------------------------------------------------------------

        private List<List<Component>> wrapIntoLines(List<Component> children,
                                                     int mainAvail, int gap, boolean isRow) {
            List<List<Component>> lines   = new ArrayList<>();
            List<Component>       current = new ArrayList<>();
            int usedMain = 0;

            for (Component c : children) {
                Dimension pref = c.getPreferredSize();
                int cMain  = isRow ? pref.width : pref.height;
                int needed = current.isEmpty() ? cMain : usedMain + gap + cMain;

                if (!current.isEmpty() && needed > mainAvail) {
                    lines.add(new ArrayList<>(current));
                    current.clear();
                    usedMain = 0;
                }
                current.add(c);
                usedMain = current.size() == 1 ? cMain : usedMain + gap + cMain;
            }
            if (!current.isEmpty()) lines.add(current);
            return lines;
        }

        // ---- Multi-line layout ----------------------------------------------------------------------

        private void layoutLines(List<List<Component>> lines,
                                  int originX, int originY,
                                  int mainAvail, int crossAvail,
                                  int mainGap, int crossGap,
                                  boolean isRow, boolean isReverse) {

            int n            = lines.size();
            int totalCrossGap = crossGap * Math.max(0, n - 1);
            int crossPerLine  = n > 0 ? (crossAvail - totalCrossGap) / n : crossAvail;

            int crossPos = isRow ? originY : originX;

            for (List<Component> line : lines) {
                List<Component> ordered = isReverse ? reversed(line) : line;
                layoutOneLine(ordered, originX, originY, crossPos,
                              mainAvail, crossPerLine, mainGap, isRow);
                crossPos += crossPerLine + crossGap;
            }
        }

        // ---- Single-line layout ---------------------------------------------------------------------

        private void layoutOneLine(List<Component> items,
                                    int originX, int originY, int crossPos,
                                    int mainAvail, int crossSize,
                                    int mainGap, boolean isRow) {
            int n = items.size();
            if (n == 0) return;

            // Total preferred size along the main axis (includes inter-item gaps)
            int totalMain = 0;
            for (Component c : items) {
                Dimension pref = c.getPreferredSize();
                totalMain += isRow ? pref.width : pref.height;
            }
            totalMain += mainGap * (n - 1);

            int free = mainAvail - totalMain;

            // Start position and effective spacing based on justifyContent
            int startMain;
            int between;   // spacing to add between consecutive items (on top of mainGap)

            switch (justifyContent) {
                case FLEX_END -> {
                    startMain = free;
                    between   = mainGap;
                }
                case CENTER -> {
                    startMain = free / 2;
                    between   = mainGap;
                }
                case SPACE_BETWEEN -> {
                    startMain = 0;
                    between   = n > 1 ? mainGap + free / (n - 1) : mainGap;
                }
                case SPACE_AROUND -> {
                    int sp = n > 0 ? free / n : 0;
                    startMain = sp / 2;
                    between   = mainGap + sp;
                }
                case SPACE_EVENLY -> {
                    int sp = free / (n + 1);
                    startMain = sp;
                    between   = mainGap + sp;
                }
                default -> {  // FLEX_START
                    startMain = 0;
                    between   = mainGap;
                }
            }

            int mainPos = (isRow ? originX : originY) + startMain;

            for (Component c : items) {
                Dimension pref  = c.getPreferredSize();
                int cMain  = isRow ? pref.width  : pref.height;
                int cCross;
                int cCrossPos;

                // Cross-axis size and position based on alignItems
                switch (alignItems) {
                    case FLEX_END -> {
                        cCross    = isRow ? pref.height : pref.width;
                        cCrossPos = crossPos + crossSize - cCross;
                    }
                    case CENTER -> {
                        cCross    = isRow ? pref.height : pref.width;
                        cCrossPos = crossPos + (crossSize - cCross) / 2;
                    }
                    case STRETCH -> {
                        cCross    = crossSize;
                        cCrossPos = crossPos;
                    }
                    default -> {  // FLEX_START
                        cCross    = isRow ? pref.height : pref.width;
                        cCrossPos = crossPos;
                    }
                }

                if (isRow) {
                    c.setBounds(mainPos, cCrossPos, cMain, cCross);
                } else {
                    c.setBounds(cCrossPos, mainPos, cCross, cMain);
                }

                mainPos += cMain + between;
            }
        }

        // ---- Preferred / minimum / maximum sizes ----------------------------------------------------

        @Override
        public Dimension preferredLayoutSize(Container parent) {
            List<Component> children = visibleChildren(parent);
            if (children.isEmpty()) return new Dimension(0, 0);

            Insets ins    = parent.getInsets();
            boolean isRow = flexDirection == FlexDirection.ROW || flexDirection == FlexDirection.ROW_REVERSE;
            int effMainGap = isRow
                ? (columnGap >= 0 ? columnGap : gap)
                : (rowGap    >= 0 ? rowGap    : gap);

            int totalMain = 0;
            int maxCross  = 0;

            for (Component c : children) {
                Dimension pref = c.getPreferredSize();
                totalMain += isRow ? pref.width  : pref.height;
                maxCross   = Math.max(maxCross, isRow ? pref.height : pref.width);
            }
            totalMain += effMainGap * Math.max(0, children.size() - 1);

            int prefW = ins.left + ins.right  + (isRow ? totalMain : maxCross);
            int prefH = ins.top  + ins.bottom + (isRow ? maxCross  : totalMain);
            return new Dimension(prefW, prefH);
        }

        @Override
        public Dimension minimumLayoutSize(Container parent) { return new Dimension(0, 0); }

        @Override
        public Dimension maximumLayoutSize(Container target) {
            return new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
        }

        // ---- Required LayoutManager2 methods --------------------------------------------------------

        @Override public void addLayoutComponent(String name, Component comp) {}
        @Override public void removeLayoutComponent(Component comp) {}
        @Override public void addLayoutComponent(Component comp, Object constraints) {}
        @Override public float getLayoutAlignmentX(Container target) { return 0.5f; }
        @Override public float getLayoutAlignmentY(Container target) { return 0.5f; }
        @Override public void invalidateLayout(Container target) {}

        // ---- Utilities ------------------------------------------------------------------------------

        private List<Component> visibleChildren(Container parent) {
            List<Component> list = new ArrayList<>();
            for (int i = 0; i < parent.getComponentCount(); i++) {
                Component c = parent.getComponent(i);
                if (c.isVisible()) list.add(c);
            }
            return list;
        }

        private <T> List<T> reversed(List<T> list) {
            List<T> out = new ArrayList<>(list);
            Collections.reverse(out);
            return out;
        }
    }
}