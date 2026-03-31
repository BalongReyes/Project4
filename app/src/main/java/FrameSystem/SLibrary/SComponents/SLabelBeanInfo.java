package FrameSystem.SLibrary.SComponents;

import java.beans.*;
import java.util.*;
import javax.swing.JLabel;

public class SLabelBeanInfo extends SimpleBeanInfo {

    @Override
    public PropertyDescriptor[] getPropertyDescriptors() {
        try {
            List<PropertyDescriptor> descriptors = new ArrayList<>();
            BeanInfo superBeanInfo = Introspector.getBeanInfo(JLabel.class);
            PropertyDescriptor[] superDescriptors = superBeanInfo.getPropertyDescriptors();

            // Filter out properties we want to override without mutating them
            for (PropertyDescriptor pd : superDescriptors) {
                switch (pd.getName()) {
                    case "border", "toolTipText", "font", "background" -> {} // Skip these
                    default -> descriptors.add(pd);
                }
            }

            // Create fresh descriptors for the overrides
            PropertyDescriptor border = new PropertyDescriptor("border", SLabel.class);
            border.setHidden(true);
            PropertyDescriptor toolTipText = new PropertyDescriptor("toolTipText", SLabel.class);
            toolTipText.setHidden(true);
            PropertyDescriptor font = new PropertyDescriptor("font", SLabel.class);
            font.setValue("category", "Typography");
            PropertyDescriptor background = new PropertyDescriptor("background", SLabel.class);
            
            descriptors.addAll(Arrays.asList(border, toolTipText, font, background));

            // Custom properties
            PropertyDescriptor scaledIcon = new PropertyDescriptor("scaledIcon", SLabel.class);
            PropertyDescriptor iconSize = new PropertyDescriptor("iconSize", SLabel.class);
            scaledIcon.setValue("category", "Icon Settings");
            iconSize.setValue("category", "Icon Settings");

            descriptors.addAll(Arrays.asList(scaledIcon, iconSize));
            return descriptors.toArray(new PropertyDescriptor[0]);
        } catch (IntrospectionException e) {
            e.printStackTrace();
            return super.getPropertyDescriptors();
        }
    }
}