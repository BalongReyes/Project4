package FrameSystem.SLibrary.SComponents;

import java.beans.*;
import java.util.*;

public class SLabelHoverBeanInfo extends SimpleBeanInfo {

    @Override
    public PropertyDescriptor[] getPropertyDescriptors() {
        try {
            List<PropertyDescriptor> descriptors = new ArrayList<>();
            BeanInfo superBeanInfo = Introspector.getBeanInfo(SLabel.class);
            PropertyDescriptor[] superDescriptors = superBeanInfo.getPropertyDescriptors();

            for (PropertyDescriptor pd : superDescriptors) {
                if (pd.getName().equals("background")) {
                    continue; // Skip without mutating
                }
                descriptors.add(pd);
            }

            // Explicitly hide background for Hover using a new descriptor
            PropertyDescriptor background = new PropertyDescriptor("background", SLabelHover.class);
            background.setHidden(true); 
            descriptors.add(background);

            PropertyDescriptor defaultColor = new PropertyDescriptor("defaultColor", SLabelHover.class);
            PropertyDescriptor hoverColor = new PropertyDescriptor("hoverColor", SLabelHover.class);
            PropertyDescriptor radius = new PropertyDescriptor("radius", SLabelHover.class);

            defaultColor.setValue("category", "Colors");
            hoverColor.setValue("category", "Colors");
            radius.setValue("category", "Rounded Corners");

            descriptors.addAll(Arrays.asList(defaultColor, hoverColor, radius));
            return descriptors.toArray(new PropertyDescriptor[0]);
        } catch (IntrospectionException e) {
            e.printStackTrace();
            return super.getPropertyDescriptors();
        }
    }
}