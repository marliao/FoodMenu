package com.marliao.foodmenu.Utils;

import android.view.LayoutInflater;

import com.marliao.foodmenu.db.doman.Comment;
import com.marliao.foodmenu.db.doman.Comments;
import com.marliao.foodmenu.db.doman.FoodMenu;
import com.marliao.foodmenu.db.doman.Menu;
import com.marliao.foodmenu.db.doman.MenuDetail;
import com.marliao.foodmenu.db.doman.Ptime;
import com.marliao.foodmenu.db.doman.Sort;
import com.marliao.foodmenu.db.doman.Steps;
import com.marliao.foodmenu.db.doman.Types;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Json数据解析工具类
 */
public class ResolveJson {
    /**
     * 菜谱分类解析
     *
     * @param jsonStr
     * @return 返回Sort（java Bean）：result，Types集合
     * @throws JSONException
     */
    public static Sort resolveSort(String jsonStr) throws JSONException {
        Sort sort = new Sort();
        JSONObject jsonSort = new JSONObject(jsonStr);
        String result = jsonSort.getString("result");
        sort.setResult(result);
        JSONArray types = jsonSort.getJSONArray("types");
        List<Types> typesList = new ArrayList<>();
        for (int i = 0; i < types.length(); i++) {
            Types type = new Types();
            JSONObject jsonType = types.getJSONObject(i);
            type.setTypepic(jsonType.getString("typepic"));
            type.setDescription(jsonType.getString("description"));
            type.setTypeid(jsonType.getInt("typeid"));
            type.setTypename(jsonType.getString("typename"));
            typesList.add(type);
        }
        sort.setTypesList(typesList);
        return sort;
    }

    /**
     * 菜谱列表解析
     * 返回FoodMenu  result，menu集合
     *
     * @param jsonStr
     * @return 返回
     * @throws JSONException
     */
    public static FoodMenu resolveFoodMenu(String jsonStr) throws JSONException {
        FoodMenu foodMenu = new FoodMenu();
        JSONObject jsonMenu = new JSONObject(jsonStr);
        String result = jsonMenu.getString("result");
        foodMenu.setResult(result);
        JSONArray menus = jsonMenu.getJSONArray("menus");
        List<Menu> menuList = new ArrayList<>();
        for (int i = 0; i < menus.length(); i++) {
            Menu menu = new Menu();
            JSONObject jsonMenus = menus.getJSONObject(i);
            menu.setSpic(jsonMenus.getString("spic"));
            menu.setAssistmaterial(jsonMenus.getString("assistmaterial"));
            menu.setNotlikes(jsonMenus.getInt("notlikes"));
            menu.setMenuname(jsonMenus.getString("menuname"));
            menu.setAbstracts(jsonMenus.getString("abstracts"));
            menu.setMainmaterial(jsonMenus.getString("mainmaterial"));
            menu.setMenuid(jsonMenus.getInt("menuid"));
            menu.setTypeid(jsonMenus.getInt("typeid"));
            menu.setLikes(jsonMenus.getInt("likes"));
            menuList.add(menu);
        }
        foodMenu.setMenuList(menuList);
        return foodMenu;
    }

    /**
     * 菜谱详情的解析
     *
     * @param jsonStr
     * @return 返回MenuDetail   result，menu，Steps集合
     * @throws JSONException
     */
    public static MenuDetail resolveMenuDetail(String jsonStr) throws JSONException {
        MenuDetail menuDetail = new MenuDetail();
        JSONObject jsonMenu = new JSONObject(jsonStr);
        String result = jsonMenu.getString("result");
        menuDetail.setResult(result);
        JSONObject menuJSONObject = jsonMenu.getJSONObject("menu");
        Menu menu = new Menu();
        menu.setSpic(menuJSONObject.getString("spic"));
        menu.setAssistmaterial(menuJSONObject.getString("assistmaterial"));
        menu.setNotlikes(menuJSONObject.getInt("notlikes"));
        menu.setMenuname(menuJSONObject.getString("menuname"));
        menu.setAbstracts(menuJSONObject.getString("abstracts"));
        menu.setMainmaterial(menuJSONObject.getString("mainmaterial"));
        menu.setMenuid(menuJSONObject.getInt("menuid"));
        menu.setTypeid(menuJSONObject.getInt("typeid"));
        menu.setLikes(menuJSONObject.getInt("likes"));
        menuDetail.setMenu(menu);
        JSONArray steps = jsonMenu.getJSONArray("steps");
        List<Steps> stepsList = new ArrayList<>();
        for (int i = 0; i < steps.length(); i++) {
            Steps step = new Steps();
            JSONObject jsonSteps = steps.getJSONObject(i);
            step.setStepid(jsonSteps.getInt("stepid"));
            step.setDescription(jsonSteps.getString("description"));
            step.setMenuid(jsonSteps.getInt("menuid"));
            step.setPic(jsonSteps.getString("pic"));
            stepsList.add(step);
        }
        menuDetail.setStepsList(stepsList);
        return menuDetail;
    }

    /**
     * 解析用户评论的工具类
     *
     * @param jsonStr
     * @return
     * @throws JSONException
     */
    public static Comments resolveComments(String jsonStr) throws JSONException {
        Comments comments = new Comments();
        JSONObject jsonObject = new JSONObject(jsonStr);
        comments.setResult(jsonObject.getString("result"));
        JSONArray jsonComment = jsonObject.getJSONArray("comments");
        List<Comment> commentList = new ArrayList<>();
        for (int i = 0; i < jsonComment.length(); i++) {
            Comment comment = new Comment();
            comment.setMenuid(jsonComment.getJSONObject(i).getInt("menuid"));
            comment.setRegion(jsonComment.getJSONObject(i).getString("region"));
            JSONObject jsonPtime = jsonComment.getJSONObject(i).getJSONObject("ptime");
            Ptime ptime = new Ptime();
            ptime.setDate(jsonPtime.getString("date"));
            ptime.setHours(jsonPtime.getString("hours"));
            ptime.setSeconds(jsonPtime.getString("seconds"));
            ptime.setMonth(jsonPtime.getString("month"));
            ptime.setNanos(jsonPtime.getString("nanos"));
            ptime.setTimezoneOffset(jsonPtime.getString("timezoneOffset"));
            ptime.setYear(jsonPtime.getString("year"));
            ptime.setMinutes(jsonPtime.getString("minutes"));
            ptime.setTime(jsonPtime.getString("time"));
            ptime.setDay(jsonPtime.getString("day"));
            comment.setPtime(ptime);
            commentList.add(comment);
        }
        comments.setCommentList(commentList);
        return comments;
    }

    /**
     * 解析发布评论后服务器返回的Json数据的工具类
     * @param jsonStr
     * @return
     * @throws JSONException
     */
    public static String resolveResponseComment(String jsonStr) throws JSONException {
        JSONObject jsonObject = new JSONObject(jsonStr);
        String result = jsonObject.getString("result");
        return result;
    }

}
