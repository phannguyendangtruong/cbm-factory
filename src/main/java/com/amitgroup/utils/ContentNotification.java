package com.amitgroup.utils;

import org.springframework.stereotype.Component;

@Component
public class ContentNotification {
    public static final String CREATE_WORK_NOTIFICATION = "%s vừa thêm bạn vào một công việc %s";
    public static final String CONFIRM_NOTIFICATION = "%s vừa xác nhận công việc %s";
    public static final String REJECT_NOTIFICATION = "%s vừa từ chối công việc %s";
    public static final String FINISH_NOTIFICATION = "%s đã đánh dấu hoàn thành công việc %s";
}
