package org.nucleus.utility;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

import java.beans.PropertyEditorSupport;
import java.sql.Date;

@ControllerAdvice
public class DateEditor {

    @InitBinder
    public void binder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String date) throws IllegalArgumentException {
                setValue(date != null && date.length() != 0 ? Date.valueOf(date) : null);
            }
        });
    }
}
