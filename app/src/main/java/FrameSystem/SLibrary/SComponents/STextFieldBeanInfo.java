package FrameSystem.SLibrary.SComponents;

import FrameSystem.SLibrary.SComponents.STextField;
import java.beans.*;
import java.util.*;
import javax.swing.JTextField;

public class STextFieldBeanInfo extends SimpleBeanInfo {
    
    @Override
    public PropertyDescriptor[] getPropertyDescriptors() {
        try {
            List<PropertyDescriptor> descriptors = new ArrayList<>();
            BeanInfo superBeanInfo = Introspector.getBeanInfo(JTextField.class);
            PropertyDescriptor[] superDescriptors = superBeanInfo.getPropertyDescriptors();

            for (PropertyDescriptor pd : superDescriptors) {
                switch (pd.getName()) {
                    case "border", "toolTipText" -> pd.setHidden(true);
                    case "font" -> pd.setValue("category", "Typography");
                    case "caretColor" -> pd.setValue("category", "Colors");
                }
            }
            descriptors.addAll(Arrays.asList(superDescriptors));

            PropertyDescriptor hint = new PropertyDescriptor("hint", STextField.class);
            PropertyDescriptor hintColor = new PropertyDescriptor("hintColor", STextField.class);
            PropertyDescriptor hintOffest = new PropertyDescriptor("hintOffest", STextField.class);

            hint.setValue("category", "Hint Settings");
            hintColor.setValue("category", "Hint Settings");
            hintOffest.setValue("category", "Hint Settings");

            descriptors.addAll(Arrays.asList(hint, hintColor, hintOffest));
            return descriptors.toArray(new PropertyDescriptor[0]);
        } catch (IntrospectionException e) {
            e.printStackTrace();
            return super.getPropertyDescriptors();
        }
    }
}