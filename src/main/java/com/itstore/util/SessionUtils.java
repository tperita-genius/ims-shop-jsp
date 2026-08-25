package com.itstore.util;

import javax.servlet.http.HttpSession;
import java.util.function.Supplier;

public class SessionUtils {

    /**
     * 從 Session 取得指定型別的物件；若不存在，透過 defaultSupplier 建立預設物件並存入 Session。
     *
     * @param session         HttpSession 實例
     * @param key             Session 的鍵名
     * @param targetType      目標型別的 Class
     * @param defaultSupplier 預設物件的建構方法（例如 ArrayList::new）
     * @param <T>             回傳的型別
     * @return 存在或新建的物件實例
     */
    public static <T> T getOrCreate(
            HttpSession session, 
            String key, 
            Class<T> targetType, 
            Supplier<T> defaultSupplier) {
        
        Object obj = session.getAttribute(key);

        // 若 Session 內存在且型別相符，安全轉型回傳
        if (targetType.isInstance(obj)) {
            return targetType.cast(obj);
        }

        // 若為 null 或型別不符，產出新物件並存入 Session
        T newInstance = defaultSupplier.get();
        session.setAttribute(key, newInstance);
        return newInstance;
    }

    /**
     * 單純取得值，若不存在則回傳指定的預設值（不主動寫回 Session）
     */
    public static <T> T getOrDefault(
            HttpSession session, 
            String key, 
            Class<T> targetType, 
            T defaultValue) {
        
        Object obj = session.getAttribute(key);
        if (targetType.isInstance(obj)) {
            return targetType.cast(obj);
        }
        return defaultValue;
    }
}