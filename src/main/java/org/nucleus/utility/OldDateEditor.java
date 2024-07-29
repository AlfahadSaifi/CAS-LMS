package org.nucleus.utility;


import org.springframework.web.bind.annotation.ControllerAdvice;

import java.beans.PropertyEditorSupport;
import java.sql.Date;

@ControllerAdvice
public class OldDateEditor extends PropertyEditorSupport {

    @Override
    public void setAsText(String date) {
        if (date != null && !date.isEmpty())
            setValue(Date.valueOf(date));
        else
            setValue(null);
    }
}