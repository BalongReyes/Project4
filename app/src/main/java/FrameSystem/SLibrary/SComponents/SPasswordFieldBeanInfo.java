package FrameSystem.SLibrary.SComponents;

import FrameSystem.SLibrary.SComponents.SPasswordField;
import java.beans.*;
import java.util.*;
import javax.swing.JPasswordField;

public class SPasswordFieldBeanInfo extends SimpleBeanInfo {
    
    @Override
    public PropertyDescriptor[] getPropertyDescriptors() {
        try {
            List<PropertyDescriptor> descriptors = new ArrayList<>();
            BeanInfo superBeanInfo = Introspector.getBeanInfo(JPasswordField.class);
            PropertyDescriptor[] superDescriptors = superBeanInfo.getPropertyDescriptors();

            for (PropertyDescriptor pd : superDescriptors) {
                switch (pd.getName()) {
                    case "border", "toolTipText" -> pd.setHidden(true);
                    case "font" -> pd.setValue("category", "Typography");
                    case "caretColor" -> pd.setValue("category", "Colors");
                }
            }
            descriptors.addAll(Arrays.asList(superDescriptors));

            PropertyDescriptor hint = new PropertyDescriptor("hint", SPasswordField.class);
            PropertyDescriptor hintColor = new PropertyDescriptor("hintColor", SPasswordField.class);
            PropertyDescriptor offsetHint = new PropertyDescriptor("offsetHint", SPasswordField.class);

            hint.setValue("category", "Hint Settings");
            hintColor.setValue("category", "Hint Settings");
            offsetHint.setValue("category", "Hint Settings");

            descriptors.addAll(Arrays.asList(hint, hintColor, offsetHint));
            return descriptors.toArray(new PropertyDescriptor[0]);
        } catch (IntrospectionException e) {
            e.printStackTrace();
            return super.getPropertyDescriptors();
        }
    }
}