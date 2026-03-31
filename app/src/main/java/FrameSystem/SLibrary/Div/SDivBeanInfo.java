package FrameSystem.SLibrary.Div;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;

/**
 * BeanInfo for SnDivPanel.
 *
 * Groups all CSS-mapped properties into logical categories that appear
 * as sections inside the NetBeans Form Editor property sheet:
 *
 * Shorthand      — convenience setters (padding, borderRadius, …)
 * Box Model      — paddingTop/Right/Bottom/Left
 * Background     — background (inherited), backgroundOpacity
 * Border Radius  — per-corner radii
 * Border Top/Right/Bottom/Left — per-side width / color / style
 * Box Shadow     — shadow toggle and all shadow params
 * Display        — BLOCK vs FLEX
 * Flex           — flex direction, justify, align, wrap, gap
 * Overflow       — overflow / overflowX / overflowY
 * Visual         — opacity
 * Sizing         — preferredSize / minimumSize / maximumSize (inherited)
 * Typography     — font (inherited)
 * Hover          — hoverEnabled, hoverBackground, hoverBorderColor
 */
public class SDivBeanInfo extends SimpleBeanInfo {

    @Override
    public PropertyDescriptor[] getPropertyDescriptors() {
        try {
            List<PropertyDescriptor> descriptors = new ArrayList<>();

            // ---- Inherit and tweak JPanel properties ------------------------------------------------

            BeanInfo superInfo = Introspector.getBeanInfo(JPanel.class);
            for (PropertyDescriptor pd : superInfo.getPropertyDescriptors()) {
                switch (pd.getName()) {

                    // Hide noisy / internal properties
                    case "border",
                         "layout",
                         "opaque",
                         "toolTipText",
                         "name" -> pd.setHidden(true);

                    // Keep background visible and uncategorised so NetBeans shows
                    // it alongside our other colour properties
                    case "background" -> {
                        pd.setHidden(false);
                        pd.setExpert(false);
                        pd.setValue("category", "Background");
                    }

                    // Tuck sizing into their own section
                    case "preferredSize",
                         "minimumSize",
                         "maximumSize" -> pd.setValue("category", "Sizing");

                    // Typography
                    case "font" -> pd.setValue("category", "Typography");
                }
                descriptors.add(pd);
            }

            // ---- Shorthand ------------------------------------------------------------------

            descriptors.add(prop("padding",        "Shorthand"));
            descriptors.add(prop("borderRadius",   "Shorthand"));
            descriptors.add(prop("borderWidth",    "Shorthand"));
            descriptors.add(prop("borderColor",    "Shorthand"));
            descriptors.add(prop("borderStyleAll", "Shorthand"));

            // ---- Box Model (Padding) --------------------------------------------------------

            descriptors.add(prop("paddingTop",    "Box Model"));
            descriptors.add(prop("paddingRight",  "Box Model"));
            descriptors.add(prop("paddingBottom", "Box Model"));
            descriptors.add(prop("paddingLeft",   "Box Model"));

            // ---- Background -----------------------------------------------------------------

            descriptors.add(prop("backgroundOpacity", "Background"));

            // ---- Border Radius --------------------------------------------------------------

            descriptors.add(prop("borderTopLeftRadius",     "Border Radius"));
            descriptors.add(prop("borderTopRightRadius",    "Border Radius"));
            descriptors.add(prop("borderBottomRightRadius", "Border Radius"));
            descriptors.add(prop("borderBottomLeftRadius",  "Border Radius"));

            // ---- Border — Top ---------------------------------------------------------------

            descriptors.add(prop("borderTopWidth", "Border Top"));
            descriptors.add(prop("borderTopColor", "Border Top"));
            descriptors.add(prop("borderTopStyle", "Border Top"));

            // ---- Border — Right -------------------------------------------------------------

            descriptors.add(prop("borderRightWidth", "Border Right"));
            descriptors.add(prop("borderRightColor", "Border Right"));
            descriptors.add(prop("borderRightStyle", "Border Right"));

            // ---- Border — Bottom ------------------------------------------------------------

            descriptors.add(prop("borderBottomWidth", "Border Bottom"));
            descriptors.add(prop("borderBottomColor", "Border Bottom"));
            descriptors.add(prop("borderBottomStyle", "Border Bottom"));

            // ---- Border — Left --------------------------------------------------------------

            descriptors.add(prop("borderLeftWidth", "Border Left"));
            descriptors.add(prop("borderLeftColor", "Border Left"));
            descriptors.add(prop("borderLeftStyle", "Border Left"));

            // ---- Box Shadow -----------------------------------------------------------------

            descriptors.add(prop("boxShadow",        "Box Shadow"));
            descriptors.add(prop("boxShadowOffsetX", "Box Shadow"));
            descriptors.add(prop("boxShadowOffsetY", "Box Shadow"));
            descriptors.add(prop("boxShadowBlur",    "Box Shadow"));
            descriptors.add(prop("boxShadowSpread",  "Box Shadow"));
            descriptors.add(prop("boxShadowColor",   "Box Shadow"));
            descriptors.add(prop("boxShadowOpacity", "Box Shadow"));

            // ---- Display --------------------------------------------------------------------

            descriptors.add(prop("display", "Display"));

            // ---- Flex -----------------------------------------------------------------------

            descriptors.add(prop("flexDirection",  "Flex"));
            descriptors.add(prop("justifyContent", "Flex"));
            descriptors.add(prop("alignItems",     "Flex"));
            descriptors.add(prop("flexWrap",       "Flex"));
            descriptors.add(prop("gap",            "Flex"));
            descriptors.add(prop("rowGap",         "Flex"));
            descriptors.add(prop("columnGap",      "Flex"));

            // ---- Overflow -------------------------------------------------------------------

            descriptors.add(prop("overflow",  "Overflow"));
            descriptors.add(prop("overflowX", "Overflow"));
            descriptors.add(prop("overflowY", "Overflow"));

            // ---- Visual ---------------------------------------------------------------------

            descriptors.add(prop("opacity", "Visual"));
            
            // ---- Hover ----------------------------------------------------------------------

            descriptors.add(prop("hoverEnabled",     "Hover"));
            descriptors.add(prop("hoverBackground",  "Hover"));
            descriptors.add(prop("hoverBorderColor", "Hover"));

            return descriptors.toArray(new PropertyDescriptor[0]);

        } catch (IntrospectionException e) {
            e.printStackTrace();
            return super.getPropertyDescriptors();
        }
    }

    /**
     * Builds a PropertyDescriptor for a property of SDiv and assigns it
     * to the given category string (shown as a section header in NetBeans).
     */
    private PropertyDescriptor prop(String propertyName, String category)
            throws IntrospectionException {
        PropertyDescriptor pd = new PropertyDescriptor(propertyName, SDiv.class);
        pd.setValue("category", category);
        return pd;
    }
}