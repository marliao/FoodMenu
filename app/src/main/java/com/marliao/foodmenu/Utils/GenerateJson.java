package com.marliao.foodmenu.Utils;

/**
 * 生成Json字符串的工具类
 */
public class GenerateJson {
    /**
     * 获取菜谱列表的Json字符串
     * @param typeid    分类编号
     * @return
     */
    public static String generateMenus(int typeid){
        return "{\"typeid\":\""+typeid+"\",\"startid\":\"1\",\"pagesize\":\"20\"}";
    }

    /**
     * 获取菜谱详情的Json字符串
     * @param menuid    菜品编号
     * @return
     */
    public String generatemenuDetail(int menuid){
        return "{\"menuid\":"+menuid+"}";
    }

    /**
     * 菜谱的评价按钮的Json字符串
     * @param menuid    菜品编号
     * @param Like  是否喜欢该菜品（yes/no）
     * @return
     */
    public static String generateSupport(int menuid,String Like){
        return "{\"menuid\":"+menuid+",\"like\":\""+Like+"\"}";
    }

    /**
     * 用户发布评论的Json字符串
     * @param menuid    菜品编号
     * @param Comment   用户的评价
     * @return
     */
    public static String generatePostComment(int menuid,String Comment){
        return "{\"menuid\":"+menuid+",\"comment\":\""+Comment+"@\",\"region\":\"安徽六安\"}}";
    }

    /**
     * 获取其他用户评价的Json字符串
     * @param menuid
     * @return
     */
    public static String generateComment(int menuid){
        return "{\"menuid\":"+menuid+"}";
    }

}
