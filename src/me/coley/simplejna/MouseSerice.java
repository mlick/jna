package me.coley.simplejna;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.kevinsawicki.http.HttpRequest;
import com.sun.jna.platform.win32.BaseTSD;
import com.sun.jna.platform.win32.WinDef;
import me.coley.simplejna.hook.mouse.MouseEventReceiver;
import me.coley.simplejna.hook.mouse.MouseHookManager;
import me.coley.simplejna.hook.mouse.struct.MouseButtonType;
import org.junit.Assert;
import org.junit.Test;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import static me.coley.simplejna.Mouse.MOUSEEVENTF_LEFTDOWN;
import static me.coley.simplejna.Mouse.MOUSEEVENTF_LEFTUP;


/*
 * https://e.dituhui.com/app
 */
public class MouseSerice extends MouseHookManager {


    public int getRandowm() {
        Random random = new Random();
        return random.nextInt(2) + 25;
    }

    @Test
    public void testRandowm() throws InterruptedException {

        for (int i = 0; i < 100; i++) {
            System.out.println(getRandowm());
        }
    }

    @Test
    public void testScroll() throws InterruptedException {
        TimeUnit.SECONDS.sleep(3);
//        Press:289:208  //打开载入行政区划
        Mouse.mouseToXYAndLeftClick(289, 208);

        //
//        Mouse.mouseToXYAndLeftDbClicks(
//                269, 321//第1排
//                , 269, 343    //第2排
//                , 269, 371   // 第3排
//                , 269, 398   // 第4排
//                , 269, 427   // 第5排
//        );

        //第一次 滑动5个
        Mouse.mouseDrag(345, 321, 42);
//        Mouse.mouseToXYAndLeftDbClicks(
//                269, 321//第1排
//                , 269, 343    //第2排
//                , 269, 371   // 第3排
//                , 269, 398   // 第4排
//                , 269, 427   // 第5排
//        );

        //第二次 滑动5个
        Mouse.mouseDrag(345, 427, 42);
//        Mouse.mouseToXYAndLeftDbClicks(
//                269, 321//第1排
//                , 269, 343    //第2排
//                , 269, 371   // 第3排
//                , 269, 398   // 第4排
//                , 269, 427   // 第5排
//        );

//        Mouse.mouseToXYAndLeftClicks(269, 456
//                , 269, 486
//                , 269, 511
//                , 269, 534
//                , 269, 558
//                , 269, 587
//                , 269, 610
//                , 269, 642
//                , 269, 671
//                , 269, 696);

//        TimeUnit.SECONDS.sleep(2);

//        for (int i = 0; i < 20; i++) {
////            Mouse.mouseDrag(30);
//            int cx = 272;
//            int cy = 315 + i * getRandowm();
//            System.out.println("dbclick=>" + cx + "," + cy);
//            Mouse.mouseToXYAndLeftClick(cx, cy, true);
////            Mouse.mouseMove(0, getRandowm());
////            Mouse.mouseLeftClick(-1, -1);
////            Mouse.mouseLeftClick(-1, -1);
//        }

//        Mouse.mouseToXYAndLeftClick(1057, 216);
    }

    @Test
    public void testWhell() throws InterruptedException {
        Mouse.mouseWheel(-120 * 2);

    }

    @Test
    public void openXZQH() throws InterruptedException {
        // 打开行政区划
        TimeUnit.SECONDS.sleep(3);
//        Press:289:208  //打开载入行政区划
        Mouse.mouseToXYAndLeftClick(289, 208);
//        Press:287:314  //点击北京   第一栏
        Mouse.mouseToXYAndLeftClick(287, 314);
//        Press:403:343  //点击北京   第二栏
        Mouse.mouseToXYAndLeftClick(403, 343);

//        Press:524:340  //点击东城区 第三区
        Mouse.mouseToXYAndLeftClick(524, 340, true);
        for (int i = 1; i < 10; i++) {
            Mouse.mouseToXYAndLeftClick(524, 340 + (i * getRandowm()), true);
        }
//        Press:1060:216 //点击退出载入行政区划
//        Mouse.mouseToXYAndLeftClick(1060, 216);
        //Press:1033,681 //导入
        Mouse.mouseToXYAndLeftClick(1033, 681);

        getPage();

        //Press:795,315 //确定
        Mouse.mouseToXYAndLeftClick(795, 315);

        deleteData();
    }


    @Test
    public void importData() throws InterruptedException {

        // 打开行政区划
        TimeUnit.SECONDS.sleep(2);

        Mouse.mouseToXYAndLeftClicks(-1613, 207,
                -1344, 317,
                -1210, 342,
                -1084, 344,
                -1083, 396);
    }

    @Test
    public void deleteData() throws InterruptedException {// 执行删除操作
//        Assert.assertNotEquals("当前数据量为0", getPagesNums(), 0);
        Mouse.mouseToXYAndLeftClicks(457, 162, 883, 469, 911, 599, 546, 170, 611, 121, 806, 343, 1042, 108);

        if (getPagesNums() != 0) {
            deleteData();
        }
        System.out.println("删除执行操作完毕!!!");
    }

    @Test
    public void getPage() throws InterruptedException {
        int sum = 0;
        int count = 0, reCount = 0;
        while (true) {
            int s = getPagesNums();
            if (s == 0)
                break;
            count++;
            System.out.println("第" + count + "次检测,共有" + s + "条");
            //逻辑循环 3次重复就退出
            if (sum == s) {
                reCount++;
                System.out.println("第" + reCount + "次重复检测");
                if (reCount > 3) {
                    break;
                }
            }
            //将值赋予变量sum
            sum = s;
            TimeUnit.SECONDS.sleep(2);
        }
        System.out.println("退出检测");
    }

    private int getPagesNums() {
        int sum = 0;
        String url = "https://saasapi.dituhui.com/saas/baseData/AreaPoint/getDatasByPage?" +
                "token=3da3ace68f6b4242b1cfebe7bc2f9467" +
                "&layerId=8a9adb8164b837a30164dbe30ab755e0" +
                "&layerCode=001&dataType=1&pageNumber=1&pageSize=500&isLeft=true";
        String b = HttpRequest.get(url, true).body();
        System.out.println("获取到的内容:=>" + b);
        JSONObject j = JSON.parseObject(b);
        if ("S001".equals(j.getString("code"))) {
            sum = j.getJSONObject("result").getJSONArray("dataList").size();
        }
        System.out.println("获取到的条数=>" + sum);
        return sum;
    }

    //以下鼠标事件码，由系统提供
    private static int MOUSEEVENT_LEFTDOWN = 0x2;//左键按下
    private static int MOUSEEVENT_LEFTUP = 0x4;//左键释放，左键按下与左键释放组合就是一个有意义的集合，那么就代表着一次左键点击
    private static int MOUSEEVENT_MIDDLEDOWN = 0x20;//中键按下
    private static int MOUSEEVENT_MIDDLEUP = 0x40;//中键释放
    private static int MOUSEEVENT_RIGHTDOWN = 0x8;//右键按下
    private static int MOUSEEVENT_RIGHTUP = 0x10;//右键释放
    private static int MOUSEEVENT_MOVE = 0x1;//鼠标移动，表示相对于上次移动dx,dy。若与下面一个组合，表示鼠标移到dx,dy绝对坐标处
    private static int MOUSEEVENT_ABSOLUTE = 0x8000;//指定dx,dy为绝对坐标值，若未指定，则为上次鼠标坐标值的偏移量
    private static int MOUSEEVENT_WHELL = 0x800;//鼠标滚轮，

    static int afterX = 0, afterY = 0;

    public static void main(String[] args) throws InterruptedException {

        MouseSerice mouseSerice = new MouseSerice();
        MouseEventReceiver mer = new MouseEventReceiver(mouseSerice) {

            @Override
            public boolean onMousePress(MouseButtonType type, WinDef.HWND hwnd, WinDef.POINT info) {
//                boolean isLeft = type == MouseButtonType.LEFT_DOWN;
//                if (isLeft) {
//                    System.out.println("Left mouse button has been pressed!");
//                }
                System.out.println("Press: " + info.x + "," + info.y + " spaceY:" + Math.abs(info.y - afterY));

                afterX = info.x;
                afterY = info.y;
                return false;
            }

            @Override
            public boolean onMouseRelease(MouseButtonType type, WinDef.HWND hwnd, WinDef.POINT info) {
                return false;
            }

            @Override
            public boolean onMouseScroll(boolean down, WinDef.HWND hwnd, WinDef.POINT info) {
//                System.out.println("Scroll:" + info.x + ":" + info.y);
                return false;
            }

            @Override
            public boolean onMouseMove(WinDef.HWND hwnd, WinDef.POINT info) {
                return false;
            }
        };
        mouseSerice.hook(mer);


//        Mouse.mouseMiddleClick();
//        for (int i = 0; i < 10; i++) {
//            TimeUnit.SECONDS.sleep(2);
//            Mouse.mouseToXYAndLeftClick(13, 36);
//        }
    }


}
