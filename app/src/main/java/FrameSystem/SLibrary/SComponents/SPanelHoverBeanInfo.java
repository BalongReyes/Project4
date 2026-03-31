package FrameSystem.SLibrary.SComponents;

import FrameSystem.SLibrary.SComponents.SPanel;
import FrameSystem.SLibrary.SComponents.SPanelHover;
import java.beans.*;
import java.util.*;

public class SPanelHoverBeanInfo extends SimpleBeanInfo {
    
    @Override
    public PropertyDescriptor[] getPropertyDescriptors() {
        try {
            List<PropertyDescriptor> descriptors = new ArrayList<>();
            BeanInfo superBeanInfo = Introspector.getBeanInfo(SPanel.class);
            descriptors.addAll(Arrays.asList(superBeanInfo.getPropertyDescriptors()));

            PropertyDescriptor hoverBackgroundColor = new PropertyDescriptor("hoverBackgroundColor", SPanelHover.class);
            hoverBackgroundColor.setValue("category", "Hover Settings");
            
            descriptors.add(hoverBackgroundColor);
            return descriptors.toArray(new PropertyDescriptor[0]);
        } catch (IntrospectionException e) {
            e.printStackTrace();
            return super.getPropertyDescriptors();
        }
    }
}