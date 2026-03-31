package FrameSystem.SLibrary.SComponents;

import FrameSystem.SLibrary.SComponents.SPanel;
import FrameSystem.SLibrary.SComponents.SPanelActivatable;
import java.beans.*;
import java.util.*;

public class SPanelActivatableBeanInfo extends SimpleBeanInfo {

    @Override
    public PropertyDescriptor[] getPropertyDescriptors() {
        try {
            List<PropertyDescriptor> descriptors = new ArrayList<>();
            BeanInfo superBeanInfo = Introspector.getBeanInfo(SPanel.class);
            descriptors.addAll(Arrays.asList(superBeanInfo.getPropertyDescriptors()));

            PropertyDescriptor active = new PropertyDescriptor("active", SPanelActivatable.class);
            PropertyDescriptor activeBackgroundColor = new PropertyDescriptor("activeBackgroundColor", SPanelActivatable.class);
            PropertyDescriptor inactiveBackgroundColor = new PropertyDescriptor("inactiveBackgroundColor", SPanelActivatable.class);

            active.setValue("category", "State Settings");
            activeBackgroundColor.setValue("category", "Colors");
            inactiveBackgroundColor.setValue("category", "Colors");

            descriptors.addAll(Arrays.asList(active, activeBackgroundColor, inactiveBackgroundColor));
            return descriptors.toArray(new PropertyDescriptor[0]);
        } catch (IntrospectionException e) {
            e.printStackTrace();
            return super.getPropertyDescriptors();
        }
    }
}